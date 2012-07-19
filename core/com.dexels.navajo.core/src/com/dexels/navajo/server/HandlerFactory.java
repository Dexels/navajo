package com.dexels.navajo.server;

import java.util.HashMap;
import java.util.Map;

import navajo.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerFactory {

	

	private final static Logger logger = LoggerFactory
			.getLogger(HandlerFactory.class);
	private final static Map<String,ServiceHandler> handlerRepository = new HashMap<String, ServiceHandler>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ServiceHandler createHandler(String handler, NavajoConfigInterface navajoConfig, Access access)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		//logger.info("handlerRepository contains: "+handlerRepository.size()+" handlers!");
		ServiceHandler registeredHandler = handlerRepository.get(handler);
		if(registeredHandler!=null) {
			
			//logger.info("Using handler locally cached : "+registeredHandler.toString()+".");
			registeredHandler.setInput(access);
			return registeredHandler;
		}

		// TODO refactor to use dependency injection
		BundleContext bc = Version.getDefaultBundleContext();
		if(bc!=null) {
			ServiceReference[] dt = null;
			try {
				dt = bc.getServiceReferences(ServiceHandler.class.getName(),"(handlerName="+handler+")");
			} catch (InvalidSyntaxException e) {
				logger.error("Malformed service filter: "+"(handlerName="+handler+")",e);
			}
			if(dt!=null && !(dt.length==0)) {
				ServiceReference d = dt[0];
				ServiceHandler ssh = (ServiceHandler)bc.getService(d);
				registerHandler(handler, ssh);
				return ssh;
			}
		}
		Class<? extends ServiceHandler> c;

		if (access.betaUser) {
			c = (Class<? extends ServiceHandler>) navajoConfig.getBetaClassLoader().getClass(handler);
		} else {
			c = (Class<? extends ServiceHandler>) navajoConfig.getClassloader().getClass(handler);
		}

		ServiceHandler sh = c.newInstance();

		sh.setInput(access);
		return sh;
	}
	
    public static void registerHandler(String className, ServiceHandler r) {
    	handlerRepository.put(className, r);
    }
}
