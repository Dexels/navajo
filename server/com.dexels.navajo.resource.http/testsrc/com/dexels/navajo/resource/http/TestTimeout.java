package com.dexels.navajo.resource.http;

import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.reactivestreams.Publisher;

import io.reactivex.Single;

public class TestTimeout {

	public TestTimeout() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// Create and start Jetty's HttpClient.
		HttpClient httpClient = new HttpClient();
		httpClient.start();

		// Create a request using the HttpClient APIs.
		Request request = httpClient.newRequest("http://localhost:1234/path");
		request = request.idleTimeout(3000, TimeUnit.MILLISECONDS)
			.timeout(5000, TimeUnit.MILLISECONDS);
		
		// Wrap the request using the API provided by this project.
		ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request).build();

		// Obtain a ReactiveStreams Publisher for the response, discarding the response content.
		Publisher<ReactiveResponse> publisher = reactiveRequest.response(ReactiveResponse.Content.discard());

		// Wrap the ReactiveStreams Publisher with RxJava.
		int status = Single.fromPublisher(publisher)
		        .map(ReactiveResponse::getStatus)
		        .doOnSuccess(s->System.err.println("Completed!"))
		        .doOnError(e->e.printStackTrace())
		        .blockingGet();		
		System.err.println("status: "+status);
	}
}
