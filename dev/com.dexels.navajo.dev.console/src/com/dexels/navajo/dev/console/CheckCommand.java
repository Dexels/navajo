package com.dexels.navajo.dev.console;


import java.util.Date;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.compiler.BundleCreator;

public class CheckCommand {
	
	private BundleCreator bundleCreator = null;
//	private final static Logger logger = LoggerFactory
//			.getLogger(CheckCommand.class);

	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	/**
	 * 
	 * @param bundleCreator the bundlecreator to clear
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}


	@Descriptor(value = "Check the modification dates of a certain script.") 

	public void check(CommandSession session, String script) {
		Date installed = bundleCreator.getBundleInstallationDate(script);
		Date modified = bundleCreator.getScriptModificationDate(script);
		Date compiled = bundleCreator.getCompiledModificationDate(script);
		session.getConsole().println("Modified at: "+modified);
		session.getConsole().println("Compiled at: "+compiled);
		session.getConsole().println("Installed at: "+installed);
	}
}
