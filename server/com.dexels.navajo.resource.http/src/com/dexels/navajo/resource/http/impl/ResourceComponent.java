package com.dexels.navajo.resource.http.impl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jetty.http.HttpMethod;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.http.HttpElement;
import com.dexels.navajo.resource.http.HttpResource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.Emitter;
import io.reactivex.functions.Consumer;

public class ResourceComponent implements HttpResource {

	private final static Logger logger = LoggerFactory
			.getLogger(ResourceComponent.class);
	private String url;
	
	private JettyClient client = null;
	private String authorization;
	
	public void activate(Map<String, Object> settings) throws Exception {
		client = new JettyClient();
	
		logger.debug("Activating HTTP connector with: " + settings);
		for (Entry<String, Object> e : settings.entrySet()) {
			logger.debug("key: " + e.getKey() + " value: " + e.getValue());
		}
		String u = (String) settings.get("url");
		this.authorization = (String) settings.get("authorization");
		this.url = u.endsWith("/") ? u : u+"/";
	}

	public void deactivate() throws Exception {
		logger.debug("Deactivating HTTP connector");
		if(client!=null) {
			JettyClient c = client;
			client = null;
			c.close();
		}
	}

	@Override
	public Single<ReactiveReply> put(String bucket, String id, String type, Publisher<byte[]> data) {
		logger.info("Putting: {} type: {}",this.url+bucket+"/"+id,type);
		return client.callWithBody(this.url+bucket+"/"+id, 
					r->r.header("Authorization", this.authorization)
						.method(HttpMethod.PUT)
				,Flowable.fromPublisher(data),type)
				.firstOrError();
	}

	@Override
	public Flowable<byte[]> get(String bucket, String id) {
		String callingUrl = this.url+bucket+"/"+id;
		logger.info("Calling url: "+callingUrl);
		return client.callWithoutBody(callingUrl, r->r.header("Authorization", this.authorization))
			.toFlowable()
			.compose(client.responseStream());
	}

	@Override
	public Single<Integer> delete(String bucket, String id) {
		return client.callWithoutBody(this.url+bucket+"/"+id, r->r.header("Authorization", this.authorization))
				.map(e->e.status());
	}

	@Override
	public Flowable<HttpElement> list(String bucket) {
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

}
