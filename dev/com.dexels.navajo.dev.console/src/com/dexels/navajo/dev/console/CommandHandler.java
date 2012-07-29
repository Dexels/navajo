package com.dexels.navajo.dev.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;

public class CommandHandler {
	private final static Logger logger = LoggerFactory
			.getLogger(CommandHandler.class);
	private BundleCreator bundleCreator = null;
	private final Collection<ServiceRegistration<?>> registeredCommands = new ArrayList<ServiceRegistration<?>>();
	private BundleContext bundleContext;
	
	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	public void activate(ComponentContext context) {
		this.bundleContext = context.getBundleContext();
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
	}

	private void registerCommand(Object c, String command) {
		Dictionary<String,String> dd = new Hashtable<String,String>();
		dd.put("osgi.command.scope", "navajo");
		dd.put("osgi.command.function", command);
		ServiceRegistration<?> sr = bundleContext.registerService(c.getClass().getName(), c,dd );
		registeredCommands.add(sr);
		System.err.println("registered: "+command+" with class: "+c.getClass().getName());
	}

	public void deactivate() {
		for (ServiceRegistration<?> sr : registeredCommands) {
			sr.unregister();
		}
	}
}
