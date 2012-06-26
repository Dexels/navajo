package com.dexels.navajo.example.adapter;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.server.LocalClientDispatcherWrapper;
public class Activator implements BundleActivator {

	private static BundleContext context;
	private final static Logger logger = LoggerFactory
			.getLogger(Activator.class);

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		
		fi.clearFunctionNames();

		ExampleAdapterLibrary library = new ExampleAdapterLibrary();
		fi.injectExtension(library);
		try {
			for(String adapterName: fi.getAdapterNames(library)) {

				String adapterClass = fi.getAdapterClass(adapterName,library);
				Class<?> c = Class.forName(adapterClass);

				 Dictionary<String, Object> props = new Hashtable<String, Object>();
				 props.put("adapterName", adapterName);
				 props.put("adapterClass", c.getName());
				 System.err.println("registering: "+adapterName);
				 if(adapterClass!=null) {
					context.registerService(Class.class.getName(), c, props);
				}
			}
		} catch (Throwable e) {
			logger.error("Error starting navajo core bundle.",e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
