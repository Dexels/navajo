package com.dexels.navajo.resource.http.adapter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.resource.http.HttpResourceFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class BinaryStoreAdapter implements Mappable {

	private Access access;
	
	private final static Logger logger = LoggerFactory.getLogger(BinaryStoreAdapter.class);


	@Override
	public void load(Access access) throws MappableException, UserException {
		this.access = access;
	}

	public ReactiveReply storeBinary(Binary b, String resource, String bucket, boolean force) throws IOException {
		String hexDigest = b.getHexDigest();
		String tenant = access.getTenant();
		ReactiveReply result = HttpResourceFactory.getInstance().getHttpResource(resource)
			.put(tenant,bucket, hexDigest,b)
			.filter(reply->{ 
				boolean doInsert = reply.status()!=404 || force;
				logger.info("Result of head: "+reply.status());
				return doInsert;
			})
//			.flatMap(status->HttpResourceFactory.getInstance().getHttpResource(resource).put(bucket, hexDigest, b).toMaybe())
			.toSingle()
			.blockingGet();
		
		logger.info("Stored binary with status: {}",result.status());
		if(result.status()>=400) {
			throw new IOException("Error inserting binary into resource: "+resource+" bucket: "+bucket+" status: "+result.status()+" headers: "+result.responseHeaders());
		}
		return result;
	}
	
	public boolean headBinary(Binary b, String resource, String bucket, boolean force) throws IOException {
		String hexDigest = b.getHexDigest();
		String tenant = access.getTenant();
		ReactiveReply result = HttpResourceFactory.getInstance().getHttpResource(resource)
			.delete(tenant,bucket, hexDigest)
			.filter(reply->reply.status()!=404 || force)
			.blockingGet();
		
		logger.info("HEAD binary with status: {}",result.status());
		
		if(result.status()>=400) {
			return false;
		}
		return true;
	}

	public ReactiveReply deleteBinary(Binary b, String resource, String bucket, boolean force) throws IOException {
		String hexDigest = b.getHexDigest();
		String tenant = access.getTenant();
		ReactiveReply result = HttpResourceFactory.getInstance().getHttpResource(resource)
			.delete(tenant,bucket, hexDigest)
			.filter(reply->reply.status()!=404 || force)
			.blockingGet();
		
		logger.info("Deleted binary with status: {}",result.status());
		
//		if(result.status()>=400) {
//			throw new IOException("Error inserting binary into resource: "+resource+" bucket: "+bucket);
//		}
		return result;
	}
	
	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

}
