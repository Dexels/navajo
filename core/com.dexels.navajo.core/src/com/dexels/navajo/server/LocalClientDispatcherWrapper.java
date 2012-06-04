package com.dexels.navajo.server;

import java.util.Dictionary;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.api.NavajoServerContext;

public class LocalClientDispatcherWrapper implements LocalClient {

	private DispatcherInterface dispatcherInterface;
	private NavajoServerContext serverContext;
	

	private final static Logger logger = LoggerFactory
			.getLogger(LocalClientDispatcherWrapper.class);
	private String user;
	private String pass;
	
	public LocalClientDispatcherWrapper() {
//		this.dispatcherInterface = di;
	}

	@Override
	public Navajo call(Navajo n) throws FatalException {
		n.getHeader().setRPCUser(user);
		n.getHeader().setRPCPassword(pass);
		n.write(System.err);
		return dispatcherInterface.handle(n);
	}

	@Override
	public Navajo generateAbortMessage(String reason) throws FatalException {
		Navajo outDoc = DispatcherFactory.getInstance().generateErrorMessage(
				null, reason, -1, 0, null);
		return outDoc;
	}

	@Override
	public Navajo handleCallback(Navajo n, String callback) {
		return null;
	}

	@Override
	public Navajo handleInternal(Navajo in, Object cert, ClientInfo clientInfo) throws FatalException {
		Navajo outDoc = DispatcherFactory.getInstance().removeInternalMessages(
				DispatcherFactory.getInstance().handle(in, cert,
						clientInfo));
		return outDoc;
	}

	@Override
	public boolean isSpecialWebservice(String name) {
		return Dispatcher.isSpecialwebservice(name);
	}

	@Override
	public String getApplicationId() {
		return DispatcherFactory.getInstance().getApplicationId();
	}

	
	public void setContext(NavajoServerContext nsc) {
		logger.info("LocalClient linked to context");
		this.serverContext = nsc;
		this.dispatcherInterface = nsc.getDispatcher();
	}

	public void removeContext(NavajoServerContext nsc) {
		this.serverContext = null;
	}
	
	public void activate(ComponentContext cc) {
		Dictionary properties =  cc.getProperties();
		user = (String) properties.get("user");
		pass = (String) properties.get("password");
		logger.info(">>>>>>>>>LocalClient activated"+user+" / "+pass);
	}
	public void modified(ComponentContext cc) {
		Dictionary properties =  cc.getProperties();
		user = (String) properties.get("user");
		pass = (String) properties.get("password");
		logger.info(">>>>>>>>>LocalClient modified: "+user+" / "+pass);
	}
	public void deactivate(ComponentContext cc) {
		
		logger.info(">>>>>>>>>LocalClient DEactivated. No action taken");
	}


}
