package com.dexels.navajo.dev.console;
import java.util.Collection;

import org.apache.felix.service.command.CommandSession;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class ScriptListCommand {

	private final BundleContext bundleContext;

	public ScriptListCommand(BundleContext bc) {
		this.bundleContext = bc;
	}
	
	 public void scripts(CommandSession session) {
		 this.scripts(session,null);
	 }
	 
	public void scripts(CommandSession session, String filter) {
			 String serviceFilter = null;
			 if(filter!=null) {
				 serviceFilter="(navajo.scriptName="+filter+")";
			 } else {
				 serviceFilter="(navajo.scriptName=*)";
			}
			 try {
				ServiceReference<?>[] result = bundleContext.getServiceReferences("com.dexels.navajo.server.CompiledScriptFactory", serviceFilter);
				for (ServiceReference<?> serviceReference : result) {
					String name = (String) serviceReference.getProperty("symbolicName");
					String adapterClass = (String) serviceReference.getProperty("adapterClass");
					session.getConsole().println(""+name+" class: (Not impl)");
				}
			 } catch (InvalidSyntaxException e) {
				 e.printStackTrace(session.getConsole());
			}

	 }
}
