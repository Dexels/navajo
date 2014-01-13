package com.dexels.navajo.dev.console;


import java.io.FileNotFoundException;
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

	public void check(@Descriptor(value = "The path of the script") String script,@Descriptor(value = "The current tenant")  String tenant) throws FileNotFoundException {
		Date installed = bundleCreator.getBundleInstallationDate(script,tenant);
		Date modified = bundleCreator.getScriptModificationDate(script,tenant);
		Date compiled = bundleCreator.getCompiledModificationDate(script,tenant);
		System.out.println("Modified at: "+modified);
		System.out.println("Compiled at: "+compiled);
		System.out.println("Installed at: "+installed);
	}
}
