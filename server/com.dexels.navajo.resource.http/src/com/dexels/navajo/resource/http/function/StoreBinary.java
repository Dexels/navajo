package com.dexels.navajo.resource.http.function;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.resource.http.HttpResource;
import com.dexels.navajo.resource.http.HttpResourceFactory;
import com.dexels.navajo.script.api.Access;

public class StoreBinary extends FunctionInterface{

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		String resource = (String) super.getOperand(0);
		if(resource==null) {
			throw new TMLExpressionException("No resource defined in PutBinary");
		}
		String bucket = (String) super.getOperand(1);
		if(bucket==null) {
			throw new TMLExpressionException("No bucket defined in PutBinary");
		}
		Binary binary = (Binary) super.getOperand(2);
		if(binary==null) {
			throw new TMLExpressionException("No binary defined in PutBinary");
		}
		Access access = this.getAccess();
		if(access==null) {
			throw new TMLExpressionException("No access defined in PutBinary");
		}
		String tenant = access.getTenant();
		if(tenant==null) {
			throw new TMLExpressionException("No tenant defined in PutBinary");
		}
		HttpResourceFactory instance = HttpResourceFactory.getInstance();
		if(instance==null) {
			throw new TMLExpressionException("No HttpResourceFactory found in PutBinary");
		}
		HttpResource httpResource = instance.getHttpResource(resource);
		if(httpResource==null) {
			throw new TMLExpressionException("HttpResource: "+resource+" found in PutBinary");
		}
		return httpResource.put(tenant, bucket, binary.getHexDigest(), binary).blockingGet().status();
	}

}
