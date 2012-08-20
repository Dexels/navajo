package com.dexels.navajo.dev.console;


import java.util.ArrayList;
import java.util.List;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;

public class VerifyCommand {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(VerifyCommand.class);
	
	private BundleCreator bundleCreator = null;

	
	public VerifyCommand() {
	}
	
	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	/**
	 * 
	 * @param bundleCreator the bundle creator to remove
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	@Descriptor(value = "Verify the script, to check if it is in runnable state")
	public void verify(CommandSession session, @Descriptor(value = "The path, prefix, or '/' to verify everything") String script) {
		session.getConsole().println("-------------->");
		try {
			if(script.equals("/")) {
				script = "";
			}
			List<String> success = new ArrayList<String>();
			List<String> failed = new ArrayList<String>();
			this.bundleCreator.verifyScript(script, failed, success);
			for (String fail : failed) {
				session.getConsole().println("Verification error: "+fail);
			}
			session.getConsole().println("Verified ok: "+success.size()+" bundles");
			session.getConsole().println("Failed: "+failed.size()+" bundles");

		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}
}
