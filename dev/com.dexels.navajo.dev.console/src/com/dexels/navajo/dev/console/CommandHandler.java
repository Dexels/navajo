package com.dexels.navajo.dev.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.script.api.LocalClient;

public class CommandHandler {
	private final static Logger logger = LoggerFactory
			.getLogger(CommandHandler.class);
	private BundleCreator bundleCreator = null;
	private final Collection<ServiceRegistration<?>> registeredCommands = new ArrayList<ServiceRegistration<?>>();
	private BundleContext bundleContext;
	
	private LocalClient localClient;
	private NqlContextApi nqlContext;
	
	public void setNqlContext(NqlContextApi n) {
		this.nqlContext = n;
	}

	/**
	 * 
	 * @param n the nql context to clear
	 */
	public void clearNqlContext(NqlContextApi n) {
		this.nqlContext = null;
	}

	
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

	public void setLocalClient(LocalClient lc) {
		localClient  = lc;
	}

	/**
	 *
	 * @param lc the local client to clear
	 */
	public void clearLocalClient(LocalClient lc) {
		localClient  = null;
	}
	
	public void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		logger.info("Command handler in business");

		CompileCommand c = new CompileCommand();
		c.setBundleCreator(bundleCreator);
		registerCommand(c,"compile");

		CheckCommand check = new CheckCommand();
		check.setBundleCreator(bundleCreator);
		registerCommand(check,"check");
	
		LoadCommand load = new LoadCommand();
		load.setBundleCreator(bundleCreator);
		registerCommand(load,"loadbundle");

		FunctionListCommand func = new FunctionListCommand(bundleContext);
		registerCommand(func,"functions");

		AdapterListCommand adapter = new AdapterListCommand(bundleContext);
		registerCommand(adapter,"adapters");

		ScriptListCommand script = new ScriptListCommand(bundleContext);
		registerCommand(script,"scripts");
		
		VerifyCommand verify = new VerifyCommand();
		verify.setBundleCreator(bundleCreator);
		registerCommand(verify,"verify");
		
		CallCommand cc = new CallCommand();
		cc.setLocalClient(localClient);
		registerCommand(cc, "call");
		
		NqlCommand nql = new NqlCommand();
		nql.setNqlContext(nqlContext);
		registerCommand(nql,"nql");

		
		LoginCommand login = new LoginCommand();
		login.setNqlContext(nqlContext);
		registerCommand(login,"login");
		
		SharedStore_ls ls = new SharedStore_ls();
		registerCommand(ls, "ls");
		
		SharedStore_cd changedir = new SharedStore_cd();
		registerCommand(changedir, "cd");
		
		SharedStore_cat cat = new SharedStore_cat();
		registerCommand(cat, "cat");
		
		SharedStore_get get = new SharedStore_get();
		registerCommand(get, "get");
		
		SharedStore_pwd pwd = new SharedStore_pwd();
		registerCommand(pwd, "pwd");
		
		SharedStore_rm rm = new SharedStore_rm();
		registerCommand(rm, "rm");
		
		SharedStore_sharedstore sharedstore = new SharedStore_sharedstore();
		registerCommand(sharedstore, "sharedstore");
		
		NavajoStatusCommand navajoStatus = new NavajoStatusCommand();
		registerCommand(navajoStatus, "status");
		

	}

	private void registerCommand(Object c, String command) {
		Dictionary<String,String> dd = new Hashtable<String,String>();
		dd.put("osgi.command.scope", "navajo");
		dd.put("osgi.command.function", command);
		ServiceRegistration<?> sr = bundleContext.registerService(c.getClass().getName(), c,dd );
		registeredCommands.add(sr);
		logger.info("registered: "+command+" with class: "+c.getClass().getName());

	}

	public void deactivate() {
		for (ServiceRegistration<?> sr : registeredCommands) {
			sr.unregister();
		}
	}
}
