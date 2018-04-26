package com.dexels.navajo.resource.http.function;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.resource.http.HttpResource;
import com.dexels.navajo.resource.http.HttpResourceFactory;
import com.dexels.navajo.script.api.Access;

public class GetBinaryFromStore extends FunctionInterface{

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		String resource = (String) super.getOperand(0);
		if(resource==null) {
			throw new TMLExpressionException("No resource defined in GetBinary");
		}
		String bucket = (String) super.getOperand(1);
		if(bucket==null) {
			throw new TMLExpressionException("No bucket defined in GetBinary");
		}
		String id = (String) super.getOperand(2);
		if(id==null) {
			throw new TMLExpressionException("No id defined in GetBinary");
		}
		Access access = this.getAccess();
		if(access==null) {
			throw new TMLExpressionException("No access defined in GetBinary");
		}
		String tenant = access.getTenant();
		if(tenant==null) {
			throw new TMLExpressionException("No tenant defined in GetBinary");
		}
		Integer expiration = (Integer) super.getOperand(3);
		if(expiration==null) {
			throw new TMLExpressionException("No expiration defined in GetBinary");
		}

		HttpResourceFactory instance = HttpResourceFactory.getInstance();
		if(instance==null) {
			throw new TMLExpressionException("No HttpResourceFactory found in GetBinary");
		}
		HttpResource httpResource = instance.getHttpResource(resource);
		if(httpResource==null) {
			throw new TMLExpressionException("HttpResource: "+resource+" found in GetBinary");
		}
		return httpResource.expiringURL(tenant, bucket, id,expiration);
	}
}
