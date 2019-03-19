package navajoextension;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionDefinition;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;

import navajo.ExtensionDefinition;

public class AbstractCoreExtension extends com.dexels.navajo.version.AbstractVersion {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractCoreExtension.class);
	private final Set<ServiceRegistration<?>> registrations = new HashSet<>();

	@Override
	public void stop(BundleContext bc) throws Exception {
		super.stop(bc);
		deregisterAll();
	}

	private void deregisterAll() {
		for (ServiceRegistration<?> sr : registrations) {
			sr.unregister();
		}
	}
	
	private void registerAllFunctions(ExtensionDefinition extensionDef)  {
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.clearFunctionNames();
		fi.injectExtension(extensionDef);
		final Set<String> functionNames = fi.getFunctionNames(extensionDef);
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
				 Dictionary<String, Object> props = new Hashtable<>();
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

	@SuppressWarnings("unchecked")
	private void registerFunction(BundleContext bundleContext,FunctionFactoryInterface fi,
			String functionName, FunctionDefinition fd, ExtensionDefinition extensionDef) {
		Dictionary<String, Object> props = new Hashtable<>();
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
		Class<? extends FunctionInterface> clz;
		ServiceRegistration<FunctionDefinition> registration;
		try {
			clz = (Class<? extends FunctionInterface>) Class.forName(fd.getObject(),true,extensionDef.getClass().getClassLoader());
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
	}
}
