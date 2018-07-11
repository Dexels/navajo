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
		String resource = (String) getOperand(0);
		if(resource==null) {
			throw new TMLExpressionException("No resource defined in GetBinaryFromStore");
		}
		String bucket = (String) getOperand(1);
		if(bucket==null) {
			throw new TMLExpressionException("No bucket defined in GetBinaryFromStore");
		}
		Object idObj = getOperand(2);
		String id = null;
		if (idObj instanceof String) {
		    id = (String) idObj;
		} else {
		    id = idObj.toString();
		}
		if(id==null) {
			throw new TMLExpressionException("No id defined in GetBinaryFromStore");
		}
		Integer expiration = (Integer) super.getOperand(3);
        if(expiration==null) {
            throw new TMLExpressionException("No expiration defined in GetBinaryFromStore");
        }
		
		Access access = this.getAccess();
		String tenant = access.getTenant();

		HttpResourceFactory instance = HttpResourceFactory.getInstance();
		if(instance==null) {
			throw new TMLExpressionException("No HttpResourceFactory found in GetBinaryFromStore");
		}
		HttpResource httpResource = instance.getHttpResource(resource);
		if(httpResource==null) {
			throw new TMLExpressionException("HttpResource: "+resource+" not found in GetBinaryFromStore");
		}
		return httpResource.expiringURL(tenant, bucket, id,expiration);
	}
}
