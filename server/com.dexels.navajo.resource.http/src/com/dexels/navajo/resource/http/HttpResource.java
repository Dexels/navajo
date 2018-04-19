package com.dexels.navajo.resource.http;

import java.io.IOException;
import java.util.Optional;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.document.types.Binary;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface HttpResource {
	
	

	public Single<ReactiveReply> put(String bucket, String id, String type, Publisher<byte[]> data);
	public Flowable<byte[]> get(String bucket, String id);
	public Single<Integer> delete(String bucket, String id);
	public Flowable<HttpElement> list(String bucket);

	default Single<ReactiveReply> put(String bucket, String id, Binary data) {
		// todo Null guessed content types?
		Logger logger = LoggerFactory.getLogger(HttpResource.class);

		logger.info("Size of binary: "+data.getLength());
		String type = Optional.ofNullable(data.getMimeType()).orElse(data.guessContentType());
		return put(bucket,id,type,this.flowBinary(data, 100));
	}
	default public Binary getBinary(String bucket, String id) throws IOException {
		Binary result = new Binary();
		result.startBinaryPush();
		Flowable.fromPublisher(get(bucket,id))
			.blockingForEach(e->result.pushContent(e));
		result.finishPushContent();
		return result;
	}

	
	default public Iterable<HttpElement> listBlocking(String bucket) {
		return Flowable.fromPublisher(list(bucket))
				.blockingIterable();
	}
	
	public Publisher<byte[]> flowBinary(Binary bin, final int bufferSize);


	
	public String getURL();
	
}
