package com.dexels.navajo.dev.console;
import java.util.Collection;

import org.apache.felix.service.command.CommandSession;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class AdapterListCommand {

	private final BundleContext bundleContext;

	public AdapterListCommand(BundleContext bc) {
		this.bundleContext = bc;
	}
	
	 public void adapters(CommandSession session) {
		 this.adapters(session,null);
	 }
	 
	public void adapters(CommandSession session, String filter) {
			 String serviceFilter = null;
			 if(filter!=null) {
				 serviceFilter="(adapterNames="+filter+")";
			 }
			 try {
				Collection<ServiceReference<Object>> result = bundleContext.getServiceReferences(Object.class, serviceFilter);
				for (ServiceReference<Object> serviceReference : result) {
					String name = (String) serviceReference.getProperty("adapterName");
					String adapterClass = (String) serviceReference.getProperty("adapterClass");
					session.getConsole().println(""+name+" class: "+adapterClass);
				}
			 } catch (InvalidSyntaxException e) {
				 e.printStackTrace(session.getConsole());
			}

	 }
}
