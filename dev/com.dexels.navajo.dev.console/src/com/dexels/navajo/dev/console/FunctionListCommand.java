package com.dexels.navajo.dev.console;
import java.util.Collection;

import org.apache.felix.service.command.CommandSession;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.parser.FunctionInterface;

public class FunctionListCommand {

	private final BundleContext bundleContext;

	public FunctionListCommand(BundleContext bc) {
		this.bundleContext = bc;
	}
	
	 public void functions(CommandSession session) {
		 this.functions(session, null);
	 }
	 
	public void functions(CommandSession session, String filter) {
		 String serviceFilter = null;
		 if(filter!=null) {
			 serviceFilter="(functionName="+filter+")";
		 }
		 session.getConsole().println("filter: "+filter);
		 try {
			Collection<ServiceReference<FunctionInterface>> result = bundleContext.getServiceReferences(FunctionInterface.class, serviceFilter);
			for (ServiceReference<FunctionInterface> serviceReference : result) {
				String functionName = (String) serviceReference.getProperty("functionName");
				FunctionDefinition fd = (FunctionDefinition) serviceReference.getProperty("functionDefinition");
				session.getConsole().println(""+functionName);
				session.getConsole().println("\t"+fd.getDescription());
//				}
			}
		 } catch (InvalidSyntaxException e) {
			e.printStackTrace(session.getConsole());
		}
	 }
		 public void adapters(CommandSession session, String filter) {
			 String serviceFilter = null;
//			 String filter = ci.nextArgument();
			 if(filter!=null) {
				 serviceFilter="(adapterNames="+filter+")";
			 } else {
				 // check for null?
			 }
//			 ci.println("filter: "+filter);
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
