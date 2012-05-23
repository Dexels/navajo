package com.dexels.navajo.server;

import java.util.HashMap;
import java.util.Map;

public class HandlerFactory {

	private final static Map<String,ServiceHandler> handlerRepository = new HashMap<String, ServiceHandler>();

	public static ServiceHandler createHandler(String handler, NavajoConfigInterface navajoConfig, Access access)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		ServiceHandler registeredHandler = handlerRepository.get(handler);
		if(registeredHandler!=null) {
			registeredHandler.setInput(access);
			return registeredHandler;
		}
		Class<? extends ServiceHandler> c;

		if (access.betaUser) {
			c = navajoConfig.getBetaClassLoader().getClass(handler);
		} else {
			c = navajoConfig.getClassloader().getClass(handler);
		}

		ServiceHandler sh = c.newInstance();

		sh.setInput(access);
		return sh;
	}
	
    public static void registerHandler(String className, ServiceHandler r) {
    	handlerRepository.put(className, r);
    }
}
