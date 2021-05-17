/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.resource.http;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.document.types.Binary;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface HttpResource {
	
	public static final int BUFFER_SIZE = 16384;
	

	public Single<ReactiveReply> put(String tenant, String bucket, String id, String type, Publisher<byte[]> data);
	public Flowable<byte[]> get(String tenant, String bucket, String id);
	public Single<ReactiveReply> head(String tenant, String bucket, String id);
	public Single<ReactiveReply> delete(String tenant, String bucket, String id);
	public Flowable<HttpElement> list(String tenant, String bucket);
	
	default Single<ReactiveReply> put(String tenant,String bucket, String id, Binary data) {
		// todo Null guessed content types?
		Logger logger = LoggerFactory.getLogger(HttpResource.class);

		logger.debug("Size of binary: "+data.getLength());
		String type = Optional.ofNullable(data.getMimeType()).orElse(data.guessContentType());
		return put(tenant,bucket,id,type,this.flowBinary(data, BUFFER_SIZE));
	}
	default public Binary getBinary(String tenant,String bucket, String id) throws IOException {
		Binary result = new Binary();
		result.startBinaryPush();
		Flowable.fromPublisher(get(tenant, bucket,id))
			.blockingForEach(e->result.pushContent(e));
		result.finishPushContent();
		return result;
	}

	
	default public Iterable<HttpElement> listBlocking(String tenant,String bucket) {
		return Flowable.fromPublisher(list(tenant, bucket))
				.blockingIterable();
	}
	
	public Publisher<byte[]> flowBinary(Binary bin, final int bufferSize);

	public Flowable<HttpElement> bucketList(String tenant, String bucket);
	
	public String getURL();
	public String expiringURL(String tenant, String bucket, String id, long expiration);
	public String expiringURL(String tenant, String bucket, String id, Date expire);
	public Binary lazyBinary(String tenant, String bucket, String id, long expire) throws IOException;
}
