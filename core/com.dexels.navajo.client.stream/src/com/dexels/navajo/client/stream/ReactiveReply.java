package com.dexels.navajo.client.stream;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.reactive.client.ContentChunk;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.reactivestreams.Publisher;
import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class ReactiveReply {

	public final ReactiveResponse response;
	public final Flowable<ContentChunk> content;

	public ReactiveReply(ReactiveResponse response, Publisher<ContentChunk> content, Consumer<byte[]> receivedReporter) {
		this.response = response;
		this.content = Flowable.fromPublisher(content);
	}

	public Map<String,String> responseHeaders() {
		Map<String,String> rest = new HashMap<>();
		HttpFields headers = response.getHeaders();
		
		headers.getFieldNamesCollection().stream().forEach(ee->{
			rest.put(ee, headers.get(ee) );
		});
		return rest;
	}
	
	public ImmutableMessage toMessage() {
		// TODO don't need to recreate the map. Fix the Map<String,Object> signature
		Map<String,String> r = responseHeaders();
		Map<String, String> types = new HashMap<>();
		Map<String, Object> values = new HashMap<>();
		r.keySet().forEach(e->{
			values.put(e, r.get(e));
			types.put(e, "string");
		});
		ImmutableMessage headers = ImmutableFactory.create(values, types);
		return ImmutableFactory.empty().with("status", response.getStatus(), Property.INTEGER_PROPERTY).withSubMessage("headers", headers);
		
	}
	
	public int status() {
		return response.getStatus();
	}
}
