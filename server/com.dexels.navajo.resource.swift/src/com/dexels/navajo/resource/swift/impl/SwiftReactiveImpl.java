package com.dexels.navajo.resource.swift.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.resource.binarystore.BinaryStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;

public class SwiftReactiveImpl implements BinaryStore {

	private static final ObjectMapper mapper = new ObjectMapper();
	private HttpClient<ByteBuf, ByteBuf> client;
	private String accessToken;
	private String tenantId;
	private String container;
	private URL endpointURL;

	
	private final static Logger logger = LoggerFactory.getLogger(SwiftReactiveImpl.class);

	
	@Override
	public Binary resolve(BinaryDigest digest) {
		String path = "/v1/"+tenantId+"/"+container+"/"+digestToPath(digest);
		return requestWithResponse(path, HttpMethod.GET)
				.onErrorResumeNext(e->Observable.just(new Binary()))
				.toBlocking()
				.first();
	}

	private void ensureContainerExists() {
		String path = "/v1/"+tenantId+"/"+container;
		Map<String, String> result =  request(path, HttpMethod.GET).toBlocking().first();
		String codeString = result.get("code");
		int code = Integer.parseInt(codeString);
		if(code>=200 && code < 300) {
			return;
		}
//		createContainer();
	}
	
	public void createContainer() {
		String path = "/v1/"+tenantId+"/"+container;
//		try {
//			URL u = new URL( this.endpointURL.getProtocol()+"://"+this.endpointURL.getHost()+path);
//			HttpURLConnection uc = (HttpURLConnection) u.openConnection();
//			uc.setRequestMethod("PUT");
//			uc.addRequestProperty("X-Auth-Token", accessToken);
//			int tt = uc.getResponseCode();
//			System.err.println("TT: "+tt);
//			System.err.println("u: "+u);
//		} catch (MalformedURLException e1) {
//			e1.printStackTrace();
//		} catch (ProtocolException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		Map<String, String> result =  requestWithBody(path, HttpMethod.PUT,Observable.just(new byte[]{}))
				.doOnError(e->e.printStackTrace())
				.toBlocking()
				.first();
		System.err.println("Result: "+result);
	}

	@Override
	public void store(Binary b) {
		String path = "/v1/"+tenantId+"/"+container+"/"+digestToPath(b.getDigest());
		Map<String, String> result = requestWithBinaryBody(path,HttpMethod.PUT,b,1024).toBlocking().first();
		System.err.println("Result: "+result);
		String code = result.get("code");
		System.err.println("Code: "+code);
	}

	@Override
	public void delete(BinaryDigest b) {
		Map<String,String> result = request(digestToPath(b), HttpMethod.DELETE)
				.toBlocking()
				.first();
		result.get("code");
	}


	@Override
	public Map<String, String> metadata(BinaryDigest b) {
		return request(digestToPath(b), HttpMethod.HEAD).toBlocking().first();
	}

	public void activate(Map<String,Object> settings) throws IOException {
		String endpoint = (String) settings.get("endpoint");
		String username = (String) settings.get("username");
		String apiKey = (String) settings.get("apiKey");
		String container = (String) settings.get("container");
		configure(endpoint, username, apiKey, container);
	}
	
	public void configure(String endpoint, String username, String apiKey, String container) throws IOException {
		ObjectNode authNode = authenticate(endpoint, username, apiKey);
        
		Optional<String> swiftEndpoint = findObjectStoreURL(authNode,false);
		this.container = container;
		this.tenantId = findTenantId(authNode).get();
        this.accessToken = getAuthToken(authNode);
        System.err.println("Swift endpoint: "+swiftEndpoint.get());
        this.client = createClientFromURL(swiftEndpoint.get());
        ensureContainerExists();
	}

//	public static void main(String[] args) throws JsonProcessingException, IOException {
//		String endpoint = "https://identity.api.rackspacecloud.com/v2.0";
//		String username = System.getenv("SWIFTUSER");
//		String apiKey = System.getenv("SWIFTAPIKEY");
//		String container = "knvb-develop";
////		"/v1/"+tenantId.get()+"/"
//		SwiftReactiveImpl instance = new SwiftReactiveImpl();
//		instance.configure(endpoint, username, apiKey, container);
//		ObjectNode authNode = authenticate(endpoint, username, apiKey);
//        
//		Optional<String> swiftEndpoint = findObjectStoreURL(authNode,false);
//        Optional<String> tenantId = findTenantId(authNode);
//        String accessToken = getAuthToken(authNode);
//		if(!swiftEndpoint.isPresent()) {
//			System.err.println("Catalog: "+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authNode.get("access").get("serviceCatalog")));
//			throw new FileNotFoundException("No cloud files found in endpoint catalog");
//		}
//		authNode.get("access").get("serviceCatalog");
//
//        Binary content = getBinaryByPath(container+"/CLUBLOGO/1013507", swiftEndpoint, tenantId, accessToken)
//        		.toBlocking().first();
//        System.err.println("Content:\n"+content.getLength()+" type: "+content.getMimeType());
//	}

	// HEAD / DELETE
	private Observable<Map<String,String>> request(String path, HttpMethod method) {
		return this.client
			.createRequest(method, path)
		    .addHeader("X-Auth-Token", accessToken)
			.asObservable()
			.doOnNext(e->System.err.println("Some kind of response: "+e))
			.map(this::responseHeaders);
	}

	private Observable<Binary> requestWithResponse(String path, HttpMethod method) {
		return this.client
			.createRequest(method, path)
		    .addHeader("X-Auth-Token", accessToken)
			.asObservable()
			.lift(parseBinary());
	}

	private Observable<Map<String,String>> requestWithBinaryBody(String path, HttpMethod method, Binary input, int bufferSize) {
		Observable<byte[]> body = Observable.from(input.getDataAsIterable(bufferSize));
		return requestWithBody(path, method, body);
	}

	private Observable<Map<String, String>> requestWithBody(String path, HttpMethod method, Observable<byte[]> body) {
		return this.client
				.createRequest(method, path)
			    .addHeader("X-Auth-Token", accessToken)
			    .writeBytesContent(body)
			    .asObservable()
				.map(this::responseHeaders);
	}

	private String digestToPath(BinaryDigest digest) {
		String path = digest.hex();
		return path.substring(0, 2) + "/" + path;
	}
	
	private Map<String,String> responseHeaders(HttpClientResponse<ByteBuf> response) {
		Map<String,String> result = new HashMap<>();
		String resultCode = Integer.toString(response.getStatus().code());
		result.put("code", resultCode);
		for (String header : response.getHeaderNames()) {
			result.put(header, response.getHeader(header));
		} 
		int code = Integer.parseInt(resultCode); 
		if(code>400) {
			throw new RuntimeException("Error performing request. Response headers: "+result);
		}
		return result;
	}
	private Observable<Binary> getBinaryByPath(String path, Optional<String> swiftEndpoint, Optional<String> tenantId,
			String accessToken) {
		return createClientFromURL(swiftEndpoint.get())
		    .createGet("/v1/"+tenantId.get()+"/"+path)
		    .addHeader("X-Auth-Token", accessToken)
		    .lift(parseBinary())
		    ;
	}

	
	private static String getAuthToken(ObjectNode authNode) {
		return authNode.get("access").get("token").get("id").asText();
	}

	private static Optional<String> findObjectStoreURL(ObjectNode authNode, boolean internal) {
		ArrayNode services = (ArrayNode) authNode.get("access").get("serviceCatalog");
		for (JsonNode jsonNode : services) {
			ObjectNode e = (ObjectNode)jsonNode;
			if("object-store".equals( e.get("type").asText())) {
				System.err.println("FOUND!");
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
				System.err.println("FOUND!");
				return Optional.of(((ArrayNode)e.get("endpoints")).get(0).get("tenantId").asText());
			}
		}
		return Optional.empty();
	}
	private ObjectNode authenticate(String endpoint, String username, String apiKey)
			throws JsonProcessingException {
		HttpClient<ByteBuf, ByteBuf> authClient = createClientFromURL(endpoint);

		
		ObjectNode node = mapper.createObjectNode();
		ObjectNode auth = mapper.createObjectNode();
		ObjectNode passwordCredentials = mapper.createObjectNode();
		passwordCredentials.put("username", username);
		passwordCredentials.put("apiKey", apiKey);
		auth.set("RAX-KSKEY:apiKeyCredentials", passwordCredentials);
		node.set("auth", auth);
//		System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));

        ObjectNode authNode = authClient 
        	.createPost("/v2.0/tokens")
		    .addHeader("Content-Type", "application/json")
		    .writeStringContent(Observable.just(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node)))
		    .doOnNext(re->System.err.println(":: "+re.toString()))
		    .concatMapEager(resp->resp.getContent().map(a->a.toString(Charset.defaultCharset())))
		    .reduce(new StringBuffer(),(sb,s)->sb.append(s))
        	.map(sb->extractKey(sb.toString()))
        	.toBlocking()
        	.first();
		return authNode;
	}

	private HttpClient<ByteBuf, ByteBuf> createClientFromURL(String endpoint) {
		try {
			endpointURL = new URL(endpoint);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unsupported URL: "+endpoint,e);
		}
		boolean secure = "https".equals(endpointURL.getProtocol());
		String host = endpointURL.getHost();
		int port = endpointURL.getPort();
		if(port==-1) {
			switch (endpointURL.getProtocol()) {
			case "https":
				port = 443;
				break;
			case "http":
				port = 80;
				break;
			default:
				throw new UnsupportedOperationException("Unsupported protocol: "+endpointURL.getProtocol());
			}
		}
		HttpClient<ByteBuf,ByteBuf> authClient = secure? HttpClient.newClient(host,port).unsafeSecure() : HttpClient.newClient(host, port);
//		HttpClient<ByteBuf,ByteBuf> authClient = secure? HttpClient.newClient(host,port).secure(defaultSSLEngineForClient(host,port)) : HttpClient.newClient(host, port);
		return authClient;
	}
	
	private Subscriber<Map<String,String>> createStoreEndpoint() {
		return new Subscriber<Map<String,String>>() {

			@Override
			public void onStart() {
				request(100);
				super.onStart();
			}

			@Override
			public void onCompleted() {
				
			}

			@Override
			public void onError(Throwable e) {
				logger.error("Error: ", e);
			}

			@Override
			public void onNext(Map<String, String> arg0) {
				
			}
		};
	}
	
	private static Operator<Binary,HttpClientResponse<ByteBuf>> parseBinary() {
		return new Operator<Binary,HttpClientResponse<ByteBuf>>(){

			@Override
			public Subscriber<? super HttpClientResponse<ByteBuf>> call(Subscriber<? super Binary> out) {
				return new Subscriber<HttpClientResponse<ByteBuf>>(){

					@Override
					public void onCompleted() {
//						out.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						out.onError(e);
					}

					@Override
					public void onNext(HttpClientResponse<ByteBuf> response) {
						Map<String,String> headers = new HashMap<>();
						for (String name : response.getHeaderNames()) {
							headers.put(name, response.getHeader(name));
						}
						System.err.println("Code: "+headers.get("code")+" all headers: "+headers+" status: "+response.getStatus());
						int cc = response.getStatus().code();
						if(cc>=400) {
							out.onError(new RuntimeException("Error code in HTTP: "+cc+" headers: "+headers));
							
						}
						Binary result = new Binary();
						result.startBinaryPush();
						result.setMimeType(response.getHeader("Content-Type"));
						response.getContent()
							.map(SwiftReactiveImpl::byteBufToByteArray)
							.subscribe(
								b->result.pushContent(b)
							, e->out.onError(e)
							, ()->{
								try {
									result.finishPushContent();
									out.onNext(result);
								} catch (Exception e) {
									out.onError(e);
								}
							}
							);
//						out.onNext(result);
					}};
			}};
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
	
	private static byte[] byteBufToByteArray(ByteBuf buf) {
		byte[] bytes = new byte[buf.readableBytes()];
		int readerIndex = buf.readerIndex();
		buf.getBytes(readerIndex, bytes);
		return bytes;
	}
    private static SSLEngine defaultSSLEngineForClient(String host, int port) {
        try {

        	SSLContext sslCtx = SSLContext.getDefault();
//			final SSLContext sslCtx = SSLContext.getInstance("TLS");
		    SSLEngine sslEngine = sslCtx.createSSLEngine(host, port);
			sslEngine.setUseClientMode(true);
			return sslEngine;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
    }
}
