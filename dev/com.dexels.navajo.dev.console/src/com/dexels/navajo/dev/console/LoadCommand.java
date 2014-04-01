package com.dexels.navajo.dev.console;


import java.util.ArrayList;
import java.util.List;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;

public class LoadCommand extends ConsoleCommand {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(LoadCommand.class);
	
	private BundleCreator bundleCreator = null;

	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	/**
	 * @param bundleCreator the bundle creator to clear 
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	@Descriptor(value = "install a script with a certain path.") 
	public void loadbundle(CommandSession session, @Descriptor(value = "Force installation if the script is already installed") @Parameter(names = { "-f", "--force" }, presentValue = "true", absentValue = "false") boolean force,@Descriptor(value = "The path, prefix, or '/' to install everything")  String script) {
		try {
			
//			, @Descriptor(value ="The current tenant to assume, will use 'default' if unspecified") @Parameter(absentValue="default", names = {"-t","--tenant"}) String tenant
			String tenant = "default";
			final String extension = ".xml";
			System.out.println("Installing path: "+script+" for tenant: "+tenant);
			if(script.equals("/")) {
				script = "";
			}
			List<String> success = new ArrayList<String>();
			List<String> failed = new ArrayList<String>();
			List<String> skipped = new ArrayList<String>();
//			this.bundleCreator.installAllBundles("",script,);
			this.bundleCreator.installBundles(script,failed, success, skipped,force,extension);
			for (String fail : failed) {
				System.out.println("Installation error: "+fail);
			}
			System.out.println("Installed: "+success.size()+" bundles");
			System.out.println("Skipped: "+skipped.size()+" bundles");
			System.out.println("Failed: "+failed.size()+" bundles");

		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public String showUsage() {
		// TODO Auto-generated method stub
		return null;
	}
}
