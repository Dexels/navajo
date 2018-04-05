package com.dexels.navajo.client.stream;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.reactive.client.ContentChunk;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.reactivestreams.Publisher;

import io.reactivex.Flowable;

public class ReactiveReply {

	public final ReactiveResponse response;
	public final Flowable<ContentChunk> content;

	public ReactiveReply(ReactiveResponse response, Publisher<ContentChunk> content) {
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
}
