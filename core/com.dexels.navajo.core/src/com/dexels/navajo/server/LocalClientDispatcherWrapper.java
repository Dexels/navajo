package com.dexels.navajo.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.api.NavajoServerContext;

public class LocalClientDispatcherWrapper implements LocalClient {

	private DispatcherInterface dispatcherInterface;
	private NavajoServerContext serverContext;
	private BundleCreator bundleCreator;
	

	private final static Logger logger = LoggerFactory
			.getLogger(LocalClientDispatcherWrapper.class);
	private String user;
	private String pass;
	
	public LocalClientDispatcherWrapper() {
//		this.dispatcherInterface = di;
	}

	public void setBundleCreator(BundleCreator bc) {
		this.bundleCreator = bc;
	}
	
	public void clearBundleCreator(BundleCreator bc) {
		this.bundleCreator = null;
	}
	
	@Override
	public Navajo call(Navajo n) throws FatalException {
		if(n.getHeader().getRPCUser()==null || n.getHeader().getRPCUser().equals("")) {
			n.getHeader().setRPCUser(user);
		}
		if(n.getHeader().getRPCPassword()==null || n.getHeader().getRPCPassword().equals("")) {
			n.getHeader().setRPCPassword(pass);
		}
		DispatcherFactory.getInstance().setBundleCreator(bundleCreator);
		return DispatcherFactory.getInstance().handle(n);
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
		return dispatcherInterface.getApplicationId();
	}

	
	public void setContext(NavajoServerContext nsc) {
		logger.info("LocalClient linked to context");
		this.serverContext = nsc;
		this.dispatcherInterface = nsc.getDispatcher();
	}

	public void removeContext(NavajoServerContext nsc) {
		this.serverContext = null;
	}
	
	public void activate(Map<String,String> properties) {
//		Dictionary properties =  cc.getProperties();
		user = properties.get("user");
		pass = properties.get("password");
	}
	public void modified(Map<String,String> properties) {
		user = properties.get("user");
		pass = properties.get("password");
	}
	public void deactivate() {
		
	}


}
