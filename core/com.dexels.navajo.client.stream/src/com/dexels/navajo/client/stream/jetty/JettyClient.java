package com.dexels.navajo.client.stream.jetty;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.reactive.client.ContentChunk;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.reactivestreams.Publisher;

import com.dexels.navajo.client.stream.ReactiveReply;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Single;

public class JettyClient {

	private final HttpClient httpClient = new HttpClient(new SslContextFactory());

	public JettyClient() throws Exception {
		httpClient.start();
	}

	public Single<ReactiveReply> callWithoutBody(String uri, Function<Request,Request> buildRequest) {
		return call(uri,buildRequest, Optional.empty(), Optional.empty()).firstOrError();
	}

	public Flowable<byte[]> callWithoutBodyToStream(String uri, Function<Request,Request> buildRequest) {
		return call(uri,buildRequest, Optional.empty(), Optional.empty())
				.compose(this.responseStream());
	}
	
	public Flowable<ReactiveReply> callWithBody(String uri, Function<Request,Request> buildRequest,Flowable<byte[]> requestBody,String requestContentType) {
		return call(uri,buildRequest,Optional.of(requestBody),Optional.of(requestContentType));
	}
	public Flowable<byte[]> callWithBodyToStream(String uri, Function<Request,Request> buildRequest,Flowable<byte[]> requestBody,String requestContentType) {
		return call(uri,buildRequest,Optional.of(requestBody),Optional.of(requestContentType))
				.compose(this.responseStream())
				
				;
	}
	public  Flowable<ReactiveReply> call(String uri,Function<Request,Request> buildRequest,Optional<Flowable<byte[]>> requestBody,Optional<String> requestContentType) {
//		Reque
		Request req = httpClient.newRequest(uri);
		req = buildRequest.apply(req);
		ReactiveRequest.Builder requestBuilder = ReactiveRequest.newBuilder(req);
		if(requestBody.isPresent()) {
			Publisher<ContentChunk> bb = requestBody.get()
//					.doOnNext(e->System.err.println("Bytes detected: "+new String(e)))
					.map(e->new ContentChunk(ByteBuffer.wrap(e)));
//					.doOnRequest(l->System.err.println("REqUESTED DATA: "+l))
//					.doOnComplete(()->System.err.println("CLIENT_SIDE_REQUEST_COMPLETE!!!!!>>>>>>>>>>>>>>>>>>"))
//					.doOnSubscribe(s->System.err.println("SUBSCRIBED TO INPUT"));
//					.doOnSubscribe(e->e.request(50));
					
					
//			System.err.println("Attaching request body");
;			requestBuilder = requestBuilder.content(ReactiveRequest.Content.fromPublisher(bb, requestContentType.get()));
		}
		ReactiveRequest request = requestBuilder.build();
		return Flowable.fromPublisher(request.response((response, content) -> Flowable.just(new ReactiveReply(response,content))));
	}

	public FlowableTransformer<ReactiveReply, byte[]> responseStream() {
		return single->single.flatMap(e->e.content).map(c->this.streamResponse(c)	).flatMap(e->e);
	}
	
	private Flowable<byte[]> streamResponse(ContentChunk chunk) {
		
		return Flowable.generate((Emitter<byte[]> emitter) -> {
            ByteBuffer buffer = chunk.buffer;
            if (buffer.hasRemaining()) {
                emitter.onNext(getByteArrayFromByteBuffer(buffer));
            } else {
                chunk.callback.succeeded();
                emitter.onComplete();
            }
        });
	}

	public void close() throws Exception {
		this.httpClient.stop();
	}
	


    private static byte[] getByteArrayFromByteBuffer(ByteBuffer byteBuffer) {
        byte[] bytesArray = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytesArray, 0, bytesArray.length);
        return bytesArray;
    }

}
