package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.persistence.impl.PersistenceManagerImpl;
import com.dexels.navajo.server.DispatcherFactory;

public class IsServiceCached extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		PersistenceManagerImpl pm = (PersistenceManagerImpl) DispatcherFactory.getInstance().getNavajoConfig().getPersistenceManager();
		String service = (String) getOperand(0);
		String values = (String) getOperand(1);
		return Boolean.valueOf(pm.isCached(service, values));
	}

	@Override
	public String remarks() {
		return "Function to check whether a service with optionally service key values is cached";
	}

	@Override
	public String usage() {
		return "IsServiceCached([service], [service key values])";
	}

}
