package com.dexels.navajo.dev.console;


import java.util.ArrayList;
import java.util.List;

import org.apache.felix.service.command.CommandSession;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;

public class LoadCommand {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(LoadCommand.class);
	
	private BundleCreator bundleCreator = null;

	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}


	public void loadbundle(CommandSession session, String script) {
		session.getConsole().println("-------------->");
		try {
			if(script.equals("/")) {
				script = "";
			}
			List<String> success = new ArrayList<String>();
			List<String> failed = new ArrayList<String>();
//			this.bundleCreator.installAllBundles("",script,);
			this.bundleCreator.installBundles(script, failed, success);
			for (String fail : failed) {
				session.getConsole().println("Installation error: "+fail);
			}
			session.getConsole().println("Installed: "+success.size()+" bundles");

		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}
}
