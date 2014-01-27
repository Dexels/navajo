package com.dexels.navajo.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class LocalClientDispatcherWrapper implements LocalClient {

	private DispatcherInterface dispatcherInterface;
	

	private final static Logger logger = LoggerFactory
			.getLogger(LocalClientDispatcherWrapper.class);
	private String user;
	private String pass;
	
	public LocalClientDispatcherWrapper() {
	}

	public void setDispatcher(DispatcherInterface di) {
		this.dispatcherInterface = di;
	}

	public void clearDispatcher(DispatcherInterface di) {
		this.dispatcherInterface = null;
	}


	@Override
	public Navajo call(Navajo n) throws FatalException {
		if(n.getHeader().getRPCUser()==null || n.getHeader().getRPCUser().equals("")) {
			n.getHeader().setRPCUser(user);
		}
		if(n.getHeader().getRPCPassword()==null || n.getHeader().getRPCPassword().equals("")) {
			n.getHeader().setRPCPassword(pass);
		}
		return dispatcherInterface.handle(n);
	}
	

	@Override
	public Navajo call(String instance, Navajo n) throws FatalException {
		return dispatcherInterface.handle(n,instance,null,null);
	}



	public Navajo callWithoutAuth(Navajo n) throws FatalException {
		return dispatcherInterface.handle(n,true);
	}
	
	@Override
	public Navajo generateAbortMessage(String reason) throws FatalException {
		Navajo outDoc = dispatcherInterface.generateErrorMessage(
				null, reason, -1, 0, null);
		return outDoc;
	}

	@Override
	public Navajo handleCallback(String instance, Navajo n, String callback) {
	    Navajo result	= dispatcherInterface.handleCallbackPointers(n);
	    return result;
	}

	@Override
	public Navajo handleInternal(String instance, Navajo in, Object cert, ClientInfo clientInfo) throws FatalException {
		Navajo outDoc = dispatcherInterface.removeInternalMessages(dispatcherInterface.handle(in, instance, cert,clientInfo));
		if(outDoc==null) {
			logger.error("handleInternal seems to have failed, as outDoc is null.");
		}
		return outDoc;
	}

	@Override
	public boolean isSpecialWebservice(String name) {
		return Dispatcher.isSpecialwebservice(name);
	}

	@Override
	public String getApplicationId() {
		return dispatcherInterface.getApplicationId();
	}


	public void activate(Map<String,String> properties) {
//		Dictionary properties =  cc.getProperties();
		try {
			user = properties.get("user");
			pass = properties.get("password");
		} catch (Throwable e) {
			logger.error("Activation failed");
		}
	}
	public void modified(Map<String,String> properties) {
		try {
			user = properties.get("user");
			pass = properties.get("password");
		} catch (Throwable e) {
			logger.error("Activation failed");
		}
	}
	public void deactivate() {
		try {
			logger.info("Deactivate");
		} catch (Throwable e) {
			logger.error("Activation failed");
		}

	}

}
