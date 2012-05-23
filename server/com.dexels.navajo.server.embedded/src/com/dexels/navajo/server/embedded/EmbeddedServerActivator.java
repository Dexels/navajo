package com.dexels.navajo.server.embedded;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.embedded.impl.ServerInstanceImpl;

public class EmbeddedServerActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.err.println("Starting server activator");
		ServerInstanceImpl sii = new ServerInstanceImpl(null);
		int port = sii.startServer("/Users/frank/NavajoEnterprise_2_4_6/");
		String ss = "localhost:"+port+"/Postman";
		ClientInterface ci = NavajoClientFactory.getClient();
		ci.setServerUrl(ss);
		ci.setUsername("aap");
		ci.setPassword("noor");
		Navajo res = ci.doSimpleSend("InitNavajoDemo");
		res.write(System.err);
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

}
