package com.dexels.navajo.osgi.console;
import java.util.Collection;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.parser.FunctionInterface;

public class FunctionListCommand implements CommandProvider {

	private final BundleContext bundleContext;
	
	private final static Logger logger = LoggerFactory
			.getLogger(FunctionListCommand.class);
	
	public FunctionListCommand(BundleContext bc) {
		this.bundleContext = bc;
	}
	
	@Override
	public String getHelp() {
		StringBuffer buffer = new StringBuffer(); 
        buffer.append("---Navajo---\n");
        buffer.append("functions [function name]\n");
        buffer.append("adapters [adapter name]\n");
        return buffer.toString();
	}
	
	 public void _functions(CommandInterpreter ci) {
		 String serviceFilter = null;
		 String filter = ci.nextArgument();
		 if(filter!=null) {
			 serviceFilter="(functionName="+filter+")";
		 }
		 ci.println("filter: "+filter);
		 try {
			Collection<ServiceReference<FunctionInterface>> result = bundleContext.getServiceReferences(FunctionInterface.class, serviceFilter);
			for (ServiceReference<FunctionInterface> serviceReference : result) {
				String functionName = (String) serviceReference.getProperty("functionName");
				FunctionDefinition fd = (FunctionDefinition) serviceReference.getProperty("functionDefinition");
				ci.println(""+functionName);
				ci.println("\t"+fd.getDescription());
//				}
			}
		 } catch (InvalidSyntaxException e) {
			 logger.error("Error: ", e);
		 }
	 }
		 public void _adapters(CommandInterpreter ci) {
			 String serviceFilter = null;
			 String filter = ci.nextArgument();
			 if(filter!=null) {
				 serviceFilter="(adapterNames="+filter+")";
			 } else {
				 // check for null?
			 }
			 ci.println("filter: "+filter);
			 try {
				Collection<ServiceReference<Object>> result = bundleContext.getServiceReferences(Object.class, serviceFilter);
				for (ServiceReference<Object> serviceReference : result) {
//					String functionName = (String) serviceReference.getProperty("functionName");
					String name = (String) serviceReference.getProperty("adapterName");
					String adapterClass = (String) serviceReference.getProperty("adapterClass");
//					String[] keys = serviceReference.getPropertyKeys();
//					for (String string : keys) {
//						ci.println("key: "+string);
//					}
					ci.println(""+name+" class: "+adapterClass);
//					ci.println("\t"+fd.toString());
//					}
				}
			 } catch (InvalidSyntaxException e) {
				 logger.error("Error: ", e);
			 }

	 }
}
