package com.dexels.navajo.resource.http.adapter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.document.types.Binary;
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

	public String storeBinary(Binary b, String resource, String bucket, boolean force) throws IOException {
		String hexDigest = b.getHexDigest();
		String tenant = access.getTenant();
		ReactiveReply result = HttpResourceFactory.getInstance().getHttpResource(resource)
			.put(tenant,bucket, hexDigest,b)
			.filter(reply->{ 
				boolean doInsert = reply.status()!=404 || force;
				logger.debug("Result of insert: "+reply.status());
				return doInsert;
			})
//			.flatMap(status->HttpResourceFactory.getInstance().getHttpResource(resource).put(bucket, hexDigest, b).toMaybe())
			.toSingle()
			.blockingGet();
		
		logger.info("Stored binary with status: {}",result.status());
		if(result.status()>=400) {
			throw new IOException("Error inserting binary into resource: "+resource+" bucket: "+bucket+" status: "+result.status()+" headers: "+result.responseHeaders());
		}
		return hexDigest;
	}
	
	public boolean headBinary(String hexDigest, String resource, String bucket) throws IOException {
		String tenant = access.getTenant();
		ReactiveReply result = HttpResourceFactory.getInstance().getHttpResource(resource)
			.head(tenant,bucket, hexDigest)
//			.filter(reply->reply.status()!=404 || force)
			.blockingGet();
		
		logger.info("HEAD binary with status: {} headers: {}",result.status(),"\n -> headers: "+result.responseHeaders());
		
		if(result.status()>=400) {
			return false;
		}
		return true;
	}

	public ReactiveReply deleteBinary(String hexDigest, String resource, String bucket, boolean force) throws IOException {
		String tenant = access.getTenant();
		ReactiveReply result = HttpResourceFactory.getInstance().getHttpResource(resource)
			.delete(tenant,bucket, hexDigest)
//			.filter(reply->reply.status()!=404 || force)
			.blockingGet();
		
		logger.info("Deleted binary with status: {}",result.status());
		return result;
	}

	public String temporaryURL(String binaryHash, String resource, String bucket, long expiration) throws IOException {
		return HttpResourceFactory.getInstance().getHttpResource(resource).expiringURL(access.getTenant(), bucket, binaryHash,expiration);
	}

//	public String temporaryURL(Binary binary, String resource, String bucket, long expiration) throws IOException {
//		return temporaryURL(binary.getHexDigest(), resource, bucket, expiration);
//	}
//	
	public void setExpiration(Object expiration) {
		System.err.println("Setting expiration: "+expiration);
		if(expiration instanceof Long) {
			this.expiration = (Long)expiration;
			return;
		}

		if(expiration instanceof Integer) {
			this.expiration = (Integer)expiration;
			return;
		}
		logger.info("Error setting expiration: Weird type: "+expiration.getClass());
	}

	public void setBinary(Binary binary) {
		this.binary = binary;
	}

	public void setBinaryHash(String binaryHash) {
		this.binaryHash = binaryHash;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	private long expiration;
	private Binary binary;
	private String binaryHash;
	private String resource;
	private String bucket;
	private String putResult;
    private int deleteResult = -1;


	
	public String getTemporaryURL() throws IOException {
		String hash = binaryHash!=null ? binaryHash : binary.getHexDigest();
		return temporaryURL(hash, resource, bucket, expiration);
	}
	

	public String getPutResult() throws IOException {
	    if (this.putResult == null) {
	        setPutBinary(true);
        }
	    return putResult;
		
	}

	public void setPutBinary(boolean ignore) throws IOException {
	    this.putResult = storeBinary(this.binary, this.resource,this.bucket, false);
    }
	
	public int getDeleteResult() throws IOException {
	    if (this.deleteResult < 0) {
	        setDeleteBinary(true);
	    }
	    return this.deleteResult;
        
	}
	
	public void setDeleteBinary(boolean ignore) throws IOException {
        String hash = binaryHash != null ? binaryHash : binary.getHexDigest();
        ReactiveReply reply = deleteBinary(hash, this.resource, this.bucket, false);
        this.deleteResult = reply.status();
    }

	
	public boolean getHeadResult() throws IOException {
		String hash = binaryHash!=null ? binaryHash : binary.getHexDigest();
		return headBinary(hash,  this.resource,this.bucket);
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
