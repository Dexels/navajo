package navajoextension;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class AbstractCoreExtension extends com.dexels.navajo.version.AbstractVersion {
	
	private final Set<ServiceRegistration> registrations = new HashSet<ServiceRegistration>();
//	private ServiceRegistration registration;

	

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		super.stop(bc);
		deregisterAll();
	}

	private void deregisterAll() {
		for (ServiceRegistration sr : registrations) {
//			final String property = (String) sr.getReference().getProperty("functionName");
//			logger.info("Deregistering: "+property);
			sr.unregister();
		}
	}
	
	private void registerAllFunctions(ExtensionDefinition extensionDef) throws TMLExpressionException {
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.clearFunctionNames();
		fi.injectExtension(extensionDef);
		final Set<String> functionNames = fi.getFunctionNames(extensionDef);
		logger.debug("Function names: "+functionNames);
		for (String functionName : functionNames) {
			FunctionDefinition fd = fi.getDef(extensionDef,functionName);
			 registerFunction(context, fi, functionName, fd,extensionDef);
		}
	}
	
	private void registerAllAdapters(ExtensionDefinition extensionDef) {
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.clearFunctionNames();
		fi.injectExtension(extensionDef);
		for(String adapterName: fi.getAdapterNames(extensionDef)) {
			fi.getAdapterConfig(extensionDef).get(adapterName);
			String adapterClass = fi.getAdapterClass(adapterName,extensionDef);
			Class<?> c = null;
			
			try {
				c = Class.forName(adapterClass,true,this.getClass().getClassLoader());
				 Dictionary<String, Object> props = new Hashtable<String, Object>();
				 props.put("adapterName", adapterName);
				 props.put("adapterClass", c.getName());
				 props.put("type", "adapter");

				if(adapterClass!=null) {
					context.registerService(Class.class.getName(), c, props);
				}
			} catch (Exception e) {
				logger.error("Error loading class for adapterClass: "+adapterClass,e);
			}
			
		}
	}
/**
 * Register both adapters and
 * @param extensionDef
 */
	protected void registerAll(ExtensionDefinition extensionDef)  {
		try {
			registerAllAdapters(extensionDef);
			registerAllFunctions(extensionDef);
		} catch (Throwable e) {
			logger.error("Error registering functions / adapters",e);
		}
	}

	private void registerFunction(BundleContext bundleContext,FunctionFactoryInterface fi,
			String functionName, FunctionDefinition fd, ExtensionDefinition extensionDef) {
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		if(functionName==null) {
			logger.warn("Can not register function: No functionName supplied");
			return;
		}
		if(fd==null) {
			logger.warn("Can not register function: {} as the FuncitonDefinition is null", functionName);
			return;
		}
		props.put("functionName", functionName);
		props.put("description", fd.getDescription());
		String[][] inputParams = fd.getInputParams();
		if(inputParams!=null) {
			props.put("input", inputParams);
		}
		
		final String[] resultParam = fd.getResultParam();
		if(resultParam!=null) {
			props.put("output", resultParam);
		}

		props.put("type", "function");
//		logger.debug("registering function: {}",functionName);
		Class<? extends FunctionInterface> clz;
		ServiceRegistration registration;
		try {
			clz = (Class<? extends FunctionInterface>) Class.forName(fd.getObject(),true,extensionDef.getClass().getClassLoader());
//			logger.debug("Registering functionclass: {} context: {}"+ functionName, clz.getName(),extensionDef.getClass().getName());
			fd.setFunctionClass(clz);
			if(bundleContext!=null) {
				registration = bundleContext.registerService(
						FunctionDefinition.class,
						fd,
						props);
				registrations.add(registration);
			}
		} catch (ClassNotFoundException e) {
			logger.error("Error registering service: {}",functionName,e);
		}
//		registration = context.registerService(
//				FunctionInterface.class.getName(),
//				fi.instantiateFunctionClass(fd, getClass().getClassLoader()),
//				props);
	}
}
