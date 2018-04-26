package com.dexels.navajo.resource.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.digest.HmacUtils;
import org.eclipse.jetty.http.HttpMethod;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.resource.http.HttpElement;
import com.dexels.navajo.resource.http.HttpResource;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class ResourceComponent implements HttpResource {

	private final static Logger logger = LoggerFactory
			.getLogger(ResourceComponent.class);
	private String url;
	
	private JettyClient client = null;
	private String authorization;
	private Optional<String> secret;
	private String deployment;
	
	public void activate(Map<String, Object> settings) throws Exception {
		client = new JettyClient();
//		logger.debug("Activating HTTP connector with: " + settings);
		String u = (String) settings.get("url");
		this.authorization = (String) settings.get("authorization");
		this.secret = Optional.ofNullable((String) settings.get("secret"));
//		this.expire = Integer.parseInt( Optional.ofNullable((String) settings.get("expire")).orElse("3600"));
		this.url = u.endsWith("/") ? u : u+"/";
	}

	public void deactivate() throws Exception {
		logger.debug("Deactivating HTTP connector");
		if(client!=null) {
			JettyClient c = client;
			client = null;
			c.close();
		}
		this.authorization = null;
		this.secret = Optional.empty();
		this.url = null;
		
	}

	

	public synchronized void setRepositoryInstance(RepositoryInstance instance) {
		deployment = instance.getDeployment();
		logger.info("Attaching repository instance, bound to deployment: {}",deployment);
	}

	public synchronized void clearRepositoryInstance(RepositoryInstance instance) {
		deployment = null;
	}
	
	
	@Override
	public Single<ReactiveReply> put(String tenant, String bucket, String id, String type, Publisher<byte[]> data) {
		logger.info("Putting: {} type: {}",assembleURL(tenant,bucket, id),type);
		return client.callWithBody(assembleURL(tenant,bucket, id), 
					r->r.header("Authorization", this.authorization)
						.method(HttpMethod.PUT)
				,Flowable.fromPublisher(data).doOnNext(b->logger.debug("Bytes detected:"+b.length)),type)
				.firstOrError();
	}

	@Override
	public Flowable<byte[]> get(String tenant, String bucket, String id) {
		String callingUrl = assembleURL(tenant,bucket, id);
		logger.info("Calling url: "+callingUrl);
		return client.callWithoutBody(callingUrl, r->r.header("Authorization", this.authorization))
			.toFlowable()
			.compose(client.responseStream());
	}

	@Override
	public Single<ReactiveReply> delete(String tenant, String bucket, String id) {
		return client.callWithoutBody(assembleURL(tenant,bucket, id), r->r.header("Authorization", this.authorization).method(HttpMethod.DELETE));
	}
	
	@Override
	public Single<ReactiveReply> head(String tenant, String bucket, String id) {
		return client.callWithoutBody(assembleURL(tenant,bucket, id), 
				r->r.header("Authorization", this.authorization)
					.method(HttpMethod.HEAD)
				);
	}


	private String assembleURL(String tenant, String bucket, String id) {
		String u = this.url+resolveBucket(tenant, bucket)+"/"+id;
		logger.debug("Assembling: "+u);
		return u;
	}
	
	private String resolveBucket(String tenant, String bucket) {
		String u = tenant+"-"+deployment+"-"+bucket;
		logger.debug("Resolved bucket: "+u);
		return u;
	}

	@Override
	public Flowable<HttpElement> list(String tenant, String bucket) {
		// todo
		return null;
	}

	@Override
	public String getURL() {
		return this.url;
	}

	@Override
	public Publisher<byte[]> flowBinary(Binary bin, final int bufferSize) {
	    InputStream is = bin.getDataAsStream();
	    return Flowable.generate(new Consumer<Emitter<byte[]>>() {
	        @Override
	        public void accept(Emitter<byte[]> emitter) throws Exception {
	            byte[] buffer = new byte[bufferSize];
	            int count = is.read(buffer);
	            if (count == -1) {
	                emitter.onComplete();
	            } else if (count < bufferSize) {
	                emitter.onNext(Arrays.copyOf(buffer, count));
	            } else {
	                emitter.onNext(buffer);
	            }
	        }
	    });
	}

	@Override
	public String expiringURL(String tenant, String bucket, String id, long expire) {
		long unixTimestamp = Instant.now().getEpochSecond()+expire;
		logger.info("Assembling url to {} in {} lasting {} seconds into the future", id, bucket, expire);
		long exp = unixTimestamp+expire;
		String totalURL = assembleURL(tenant,bucket, id)+"?expires="+exp+"&sig="+sign(resolveBucket(tenant, bucket), id,exp);
		logger.debug("URL: "+totalURL);
		return totalURL;
	}
	private String sign(String bucket, String id, long expirationTime) {
		if(!secret.isPresent()) {
			throw new IllegalArgumentException("Http component has no secret. Can't make expiring URL's");
		}
		if(bucket.endsWith("/")) {
			throw new IllegalArgumentException("'bucket' should not have trailing slashes. Value: "+bucket);
		}
		if(bucket.startsWith("/")) {
			throw new IllegalArgumentException("'bucket' should not have leading slashes. Value: "+bucket);
		}
		if(id.startsWith("/")) {
			throw new IllegalArgumentException("'id' should not have leading slashes. Value: "+bucket);
		}
		
		String path = Long.toString(expirationTime)+"/"+bucket+"/"+id;
		logger.debug("Signing path: "+path);
		String encoded = HmacUtils.hmacSha1Hex(this.secret.get(), path);

		logger.debug("Encoded: "+encoded+" encoding path: "+path+" -> secret: "+this.secret.get()+" -> result: "+encoded);
		return encoded;
	}
	
	@Override
	public Binary lazyBinary(String tenant, String bucket, String id, long expire) throws IOException {
		URL u = new URL(expiringURL(tenant, bucket, id,expire));
		return new Binary(u, true);
	}
}
