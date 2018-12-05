package com.dexels.navajo.resource.http.function;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.resource.http.HttpResource;
import com.dexels.navajo.resource.http.HttpResourceFactory;
import com.dexels.navajo.script.api.Access;

public class DeleteBinary extends FunctionInterface{

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		String resource = (String) super.getOperand(0);
		if(resource==null) {
			throw new TMLExpressionException("No resource defined in DeleteBinary");
		}
		String bucket = (String) super.getOperand(1);
		if(bucket==null) {
			throw new TMLExpressionException("No bucket defined in DeleteBinary");
		}
		String id = (String) super.getOperand(2);
		if(id==null) {
			throw new TMLExpressionException("No id defined in GetBinary");
		}
		Access access = this.getAccess();
		if(access==null) {
			throw new TMLExpressionException("No access defined in DeleteBinary");
		}
		String tenant = access.getTenant();
		if(tenant==null) {
			throw new TMLExpressionException("No tenant defined in DeleteBinary");
		}
		HttpResourceFactory instance = HttpResourceFactory.getInstance();
		if(instance==null) {
			throw new TMLExpressionException("No HttpResourceFactory found in DeleteBinary");
		}
		HttpResource httpResource = instance.getHttpResource(resource);
		if(httpResource==null) {
			throw new TMLExpressionException("HttpResource: "+resource+" found in DeleteBinary");
		}
		return httpResource.delete(tenant, bucket, id).blockingGet().status();
	}

}
