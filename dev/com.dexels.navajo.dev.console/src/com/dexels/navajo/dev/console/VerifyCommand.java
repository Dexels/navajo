package com.dexels.navajo.dev.console;


import java.util.ArrayList;
import java.util.List;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;

@Command(scope = "navajo", name = "verify", description="Verifies scripts")
public class VerifyCommand extends OsgiCommandSupport {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(VerifyCommand.class);
	
	private BundleCreator bundleCreator = null;

	
	public VerifyCommand() {
	}
	
	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}
    @Argument(index = 0, name = "arg", 
            description = "The command argument", 
            required = false, multiValued = false)
  String script = null;

	/**
	 * 
	 * @param bundleCreator the bundle creator to remove
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	public void verify(String script) throws Exception {
		System.err.println("Doing verify!");
		this.script = script;
		doExecute();
	}
	@Override
	protected Object doExecute() throws Exception {
		System.out.println("-------------->");
		try {
			if(script.equals("/")) {
				script = "";
			}
			List<String> success = new ArrayList<String>();
			List<String> failed = new ArrayList<String>();
			this.bundleCreator.verifyScript(script, failed, success);
			for (String fail : failed) {
				System.out.println("Verification error: "+fail);
			}
			System.out.println("Verified ok: "+success.size()+" bundles");
			System.out.println("Failed: "+failed.size()+" bundles");

		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
		return null;
	}
}
