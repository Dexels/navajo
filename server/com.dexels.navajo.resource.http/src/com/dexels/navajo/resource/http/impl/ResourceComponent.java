package com.dexels.navajo.resource.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

	private final static Logger logger = LoggerFactory.getLogger(ResourceComponent.class);
	
	private static final long DEFAULT_TIMEOUT = 5 * 60L;       // 5 minutes
	private static final long DEFAULT_IDLE_TIMEOUT = 1 * 60L;  // 1 minute
	
	private String url;
    private String publicUrl;
	private JettyClient client = null;
	private String authorization;
	private Optional<String> secret;
	private String deployment;
	private long timeout = DEFAULT_TIMEOUT;
	private long idle_timeout = DEFAULT_IDLE_TIMEOUT;
	
	public void activate(Map<String, Object> settings) throws Exception {
		client = new JettyClient();
		String url = (String) settings.get("url");
		String publicurl = (String) settings.get("publicurl");
		this.authorization = (String) settings.get("authorization");
		this.secret = Optional.ofNullable((String) settings.get("secret"));
		
		if (settings.containsKey("timeout_ms")) {
		    timeout = Long.parseLong((String)settings.get("timeout_ms"));
		}
		
		if (settings.containsKey("timeout_idle_ms")) {
		    idle_timeout = Long.parseLong((String)settings.get("timeout_idle_ms"));
        }
		
		this.url = url.endsWith("/") ? url : url+"/";
		if (publicurl != null) {
		    this.publicUrl = publicurl.endsWith("/") ? publicurl : publicurl+"/";
		} else {
		    this.publicUrl = url.endsWith("/") ? url : url+"/";
		}
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
						.idleTimeout(idle_timeout, TimeUnit.MILLISECONDS)
						.timeout(timeout, TimeUnit.MILLISECONDS)
				,Flowable.fromPublisher(data)
				,type)
				.firstOrError();
	}

	@Override
	public Flowable<byte[]> get(String tenant, String bucket, String id) {
		String callingUrl = assembleURL(tenant,bucket, id);
		return client.callWithoutBody(callingUrl, r->r.header("Authorization", this.authorization))
                .timeout(timeout, TimeUnit.MILLISECONDS)
    			.toFlowable()
    			.compose(client.responseStream());
	}

	@Override
	public Single<ReactiveReply> delete(String tenant, String bucket, String id) {
		return client.callWithoutBody(assembleURL(tenant,bucket, id), r->r.header("Authorization", this.authorization)
		        .idleTimeout(idle_timeout, TimeUnit.MILLISECONDS)
                .timeout(timeout, TimeUnit.MILLISECONDS)
		        .method(HttpMethod.DELETE));
	}
	
	@Override
	public Single<ReactiveReply> head(String tenant, String bucket, String id) {
		return client.callWithoutBody(assembleURL(tenant,bucket, id), 
				r->r.header("Authorization", this.authorization)
                    .timeout(timeout, TimeUnit.MILLISECONDS)
					.method(HttpMethod.HEAD)
				);
	}


	private String assembleURL(String tenant, String bucket, String id) {
		String u = this.url+resolveBucket(tenant, bucket)+"/"+id;
//		logger.debug("Assembling: "+u);
		return u;
	}
	private String assemblePublicURL(String tenant, String bucket, String id) {
        String u = this.publicUrl+resolveBucket(tenant, bucket)+"/"+id;
//      logger.debug("Assembling: "+u);
        return u;
    }
	
	private String resolveBucket(String tenant, String bucket) {
		String u = tenant+"-"+deployment+"-"+bucket;
//		logger.debug("Resolved bucket: "+u);
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
		long exp = unixTimestamp+expire;
		String totalURL = assemblePublicURL(tenant,bucket, id)+"?expires="+exp+"&sig="+sign(resolveBucket(tenant, bucket), id,exp);
	    //logger.debug("Assembled public url {} to {} in {} lasting {} seconds into the future", totalURL, id, bucket, expire);

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
		String encoded = HmacUtils.hmacSha1Hex(this.secret.get(), path);
		return encoded;
	}
	
	@Override
	public Binary lazyBinary(String tenant, String bucket, String id, long expire) throws IOException {
		URL u = new URL(expiringURL(tenant, bucket, id,expire));
		return new Binary(u, true);
	}

	@Override
	public Flowable<HttpElement> bucketList(String tenant, String bucket) {
//		JSON.parseObjectNodes(input)
		return null;
	}
}
