package com.dexels.navajo.resource.http.function;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.resource.http.HttpResource;
import com.dexels.navajo.resource.http.HttpResourceFactory;
import com.dexels.navajo.script.api.Access;

public class ExistsBinaryInStore extends FunctionInterface {

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		String resource = (String) super.getOperand(0);
		if (resource == null) {
			throw new TMLExpressionException("No resource defined in ExistsBinaryInStore");
		}
		String bucket = (String) super.getOperand(1);
		if (bucket == null) {
			throw new TMLExpressionException("No bucket defined in ExistsBinaryInStore");
		}
		String id = (String) super.getOperand(2);
		if (id == null) {
			throw new TMLExpressionException("No id defined in ExistsBinaryInStore");
		}
		Access access = this.getAccess();
		if (access == null) {
			throw new TMLExpressionException("No access defined in ExistsBinaryInStore");
		}
		String tenant = access.getTenant();
		if (tenant == null) {
			throw new TMLExpressionException("No tenant defined in ExistsBinaryInStore");
		}
		HttpResourceFactory instance = HttpResourceFactory.getInstance();
		if (instance == null) {
			throw new TMLExpressionException("No HttpResourceFactory found in ExistsBinaryInStore");
		}
		HttpResource httpResource = instance.getHttpResource(resource);
		if (httpResource == null) {
			throw new TMLExpressionException("HttpResource: " + resource + " found in ExistsBinaryInStore");
		}

		return httpResource.head(tenant, bucket, id).blockingGet().status() < 400;
	}
}
