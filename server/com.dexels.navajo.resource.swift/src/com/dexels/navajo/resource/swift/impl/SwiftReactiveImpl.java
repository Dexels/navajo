package com.dexels.navajo.resource.swift.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import org.eclipse.jetty.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.resource.binarystore.BinaryStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class SwiftReactiveImpl implements BinaryStore {

	private static final ObjectMapper mapper = new ObjectMapper();
	private JettyClient client;
	private String accessToken;
	private String tenantId;
	private String container;
	private URL endpointURL;
	private String endpoint;

	
	private final static Logger logger = LoggerFactory.getLogger(SwiftReactiveImpl.class);

	
	@Override
	public Binary resolve(BinaryDigest digest) {
		String path = "/v1/"+tenantId+"/"+container+"/"+digestToPath(digest);
		return client.callWithoutBodyToStream(endpoint+path, e->e)
				.doOnError(e->e.printStackTrace())
				.toObservable()
				.lift(StreamDocument.createBinary())
				.blockingFirst();
				
	}

	private void ensureContainerExists() {
		String path = "/v1/"+tenantId+"/"+container;
		Map<String, String> result =  request(path, HttpMethod.GET).blockingGet();
		String codeString = result.get("code");
		int code = Integer.parseInt(codeString);
		if(code>=200 && code < 300) {
			return;
		}
//		createContainer();
	}
	
	public void createContainer() {
		String path = "/v1/"+tenantId+"/"+container;
		byte[] res =client.callWithBodyToStream(endpoint+path,e->e.method(HttpMethod.PUT),Flowable.just(new byte[]{}),"text/plain")
			.doOnError(e->e.printStackTrace())
			.blockingFirst();
		logger.info("Result: "+new String(res));
	}

	@Override
	public void store(Binary b) {
		String path = "/v1/"+tenantId+"/"+container+"/"+digestToPath(b.getDigest());
		Map<String, String> result = requestWithBinaryBody(path,HttpMethod.PUT,b,1024).blockingGet();
		logger.info("Result: "+result);
		String code = result.get("code");
		logger.info("Code: "+code);
	}

	@Override
	public void delete(BinaryDigest b) {
		Map<String,String> result = request(digestToPath(b), HttpMethod.DELETE)
				.blockingGet();
		result.get("code");
	}


	@Override
	public Map<String, String> metadata(BinaryDigest b) {
		return request(digestToPath(b), HttpMethod.HEAD).blockingGet();
	}

	public void activate(Map<String,Object> settings) throws Exception {
		String endpoint = (String) settings.get("endpoint");
		String username = (String) settings.get("username");
		String apiKey = (String) settings.get("apiKey");
		String container = (String) settings.get("container");
		configure(endpoint, username, apiKey, container);
	}
	
	public void configure(String endpoint, String username, String apiKey, String container) throws Exception {
		ObjectNode authNode = authenticate(endpoint, username, apiKey);
        
		Optional<String> swiftEndpoint = findObjectStoreURL(authNode,false);
		this.container = container;
		this.tenantId = findTenantId(authNode).get();
        this.accessToken = getAuthToken(authNode);
        logger.info("Swift endpoint: "+swiftEndpoint.get());
        this.client = createClientFromURL(swiftEndpoint.get());
        ensureContainerExists();
	}

	// HEAD / DELETE
	private Single<Map<String,String>> request(String path, HttpMethod method) {
		return this.client.callWithoutBody(endpoint+path, e->e.header("X-Auth-Token", accessToken)
				.method(method)
				)
			.doOnSuccess(e->logger.info("Some kind of response: "+e))
			.map(reply->reply.responseHeaders());
	}

//	private Single<byte[]> requestWithResponse(String path, HttpMethod method) {
//		return client.callWithoutBody(endpoint+path, e->e.method(method)
//				.header("X-Auth-Token", accessToken)
//			);
//	}

	private Single<Map<String,String>> requestWithBinaryBody(String path, HttpMethod method, Binary input, int bufferSize) {
		Flowable<byte[]> body =  StreamDocument.streamBinary(input, bufferSize);
		return requestWithBody(path, method, body).firstOrError();
	}

	private Flowable<Map<String, String>> requestWithBody(String path, HttpMethod method, Flowable<byte[]> body) {

		return client.callWithBody(endpoint+path, e->e.method(method).header("X-Auth-Token", accessToken), body, "application/octet-stream")
				.map(reply->reply.responseHeaders())
				
				;

		//				.toObservable();
//		return this.client
//				.createRequest(method, path)
//			    .addHeader("X-Auth-Token", accessToken)
//			    .writeBytesContent(body)
//			    .asObservable()
//				.map(this::responseHeaders);
	}

	private String digestToPath(BinaryDigest digest) {
		String path = digest.hex();
		return path.substring(0, 2) + "/" + path;
	}
	
	private static String getAuthToken(ObjectNode authNode) {
		return authNode.get("access").get("token").get("id").asText();
	}

	private static Optional<String> findObjectStoreURL(ObjectNode authNode, boolean internal) {
		ArrayNode services = (ArrayNode) authNode.get("access").get("serviceCatalog");
		for (JsonNode jsonNode : services) {
			ObjectNode e = (ObjectNode)jsonNode;
			if("object-store".equals( e.get("type").asText())) {
				logger.info("FOUND!");
				return Optional.of(((ArrayNode)e.get("endpoints")).get(0).get(internal?"internalURL":"publicURL").asText());
			}
		}
		return Optional.empty();
	}
	
	private static Optional<String> findTenantId(ObjectNode authNode) {
		ArrayNode services = (ArrayNode) authNode.get("access").get("serviceCatalog");
		for (JsonNode jsonNode : services) {
			ObjectNode e = (ObjectNode)jsonNode;
			if("object-store".equals( e.get("type").asText())) {
				logger.info("FOUND!");
				return Optional.of(((ArrayNode)e.get("endpoints")).get(0).get("tenantId").asText());
			}
		}
		return Optional.empty();
	}
	private ObjectNode authenticate(String endpoint, String username, String apiKey)
			throws JsonProcessingException {

		
		ObjectNode node = mapper.createObjectNode();
		ObjectNode auth = mapper.createObjectNode();
		ObjectNode passwordCredentials = mapper.createObjectNode();
		passwordCredentials.put("username", username);
		passwordCredentials.put("apiKey", apiKey);
		auth.set("RAX-KSKEY:apiKeyCredentials", passwordCredentials);
		node.set("auth", auth);

        ObjectNode authNode = client 
        		.callWithBodyToStream(endpoint+"/v2.0/tokens", e->e.header("Content-Type", "application/json").method(HttpMethod.POST), Flowable.just(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node).getBytes()), "application/json")
        		.map(a->new String(a))
        		.reduce(new StringBuffer(),(sb,s)->sb.append(s))
        		.map(sb->extractKey(sb.toString()))
        	.blockingGet();
		return authNode;
	}

	private JettyClient createClientFromURL(String endpoint) throws Exception {
		JettyClient client = new JettyClient();
		this.endpoint = endpoint;
//		try {
//			endpointURL = new URL(endpoint);
//		} catch (Exception e) {
//			throw new UnsupportedOperationException("Unsupported URL: "+endpoint,e);
//		}
//		boolean secure = "https".equals(endpointURL.getProtocol());
//		String host = endpointURL.getHost();
//		int port = endpointURL.getPort();
//		if(port==-1) {
//			switch (endpointURL.getProtocol()) {
//			case "https":
//				port = 443;
//				break;
//			case "http":
//				port = 80;
//				break;
//			default:
//				throw new UnsupportedOperationException("Unsupported protocol: "+endpointURL.getProtocol());
//			}
//		}
		return client;
	}
	
	private static ObjectNode extractKey(String response) {
		ObjectNode node;
		try {
			node = (ObjectNode) mapper.readTree(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return node;
		
	}

//    private static SSLEngine defaultSSLEngineForClient(String host, int port) {
//        try {
//        		SSLContext sslCtx = SSLContext.getDefault();
//		    SSLEngine sslEngine = sslCtx.createSSLEngine(host, port);
//			sslEngine.setUseClientMode(true);
//			return sslEngine;
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			return null;
//		}
//    }
}
