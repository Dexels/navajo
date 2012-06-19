package com.dexels.navajo.runtime.osgi.j2ee;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.osgi.runtime.FrameworkInstance;
import com.dexels.navajo.runtime.homecontext.ContextIdentifier;

public class WebFrameworkInstance extends FrameworkInstance {

	private final ServletContext context;
	private ServiceRegistration<ServletContext> servletContextRegistration;
	private ServiceRegistration<ContextIdentifier> servletContextIdentifierRegistration;
	private final static String BUNDLEDIR = "WEB-INF/bundles/";
	

	private final static Logger logger = LoggerFactory
			.getLogger(WebFrameworkInstance.class);
	
	public WebFrameworkInstance(ServletContext context) {
		super(context.getRealPath(BUNDLEDIR));
		this.context = context;
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void doStart() throws Exception {
		super.doStart();
		if (context != null) {
			log("Setting "+"org.osgi.framework.BundleContext"+" : "+getBundleContext(),null);
			context.setAttribute("org.osgi.framework.BundleContext",getBundleContext());
		}
		LocalClientTracker lct = new LocalClientTracker(framework.getBundleContext(), context);
		lct.open();

		servletContextRegistration = (ServiceRegistration<ServletContext>) framework.getBundleContext().registerService(ServletContext.class.getName(), context, null);
		servletContextIdentifierRegistration = (ServiceRegistration<ContextIdentifier>) framework.getBundleContext().registerService(ContextIdentifier.class.getName(), new ContextIdentifier() {
			@Override
			public String getContextPath() {
				logger.info("Identifying current context as: "+context.getContextPath());
				return context.getContextPath();
			}
		}, null);
		logger.info("ContextIdentifier registered");
	}

	protected void log(String message, Throwable cause) {
		
		
		if (context == null) {
			System.err.println("NO CONTEXT: " + message);
			if (cause != null) {
				cause.printStackTrace();
			}
			return;
		}
		this.context.log(context.getContextPath() + ": " + message, cause);
		System.err.println("> " + message);
		if (cause != null) {
			cause.printStackTrace();
		}
	}
	
	protected void doStop() throws Exception {
		log("**** SHUTTING DOWN WEBCONTEXT OF OSGI ****", null);
		servletContextRegistration.unregister();
		servletContextIdentifierRegistration.unregister();
		if (this.context != null) {
			this.context.removeAttribute("org.osgi.framework.BundleContext");
		}
		super.doStop();
		log("**** SHUTTING DOWN WEBCONTEXT OF OSGI COMPLETE ****", null);
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map createConfig() throws Exception {
		Map<String, Object> config = super.createConfig();
		Properties props = new Properties();
		logger.warn("PARENT CONFIG: ");
		for (Entry<String,Object> e : config.entrySet()) {
			logger.warn("Key: "+e.getKey()+" Value: >"+e.getValue()+"<");
		}
		logger.warn("END OF CONFIG");
		
//		props.load(getResource("default.properties"));
		String path = context.getRealPath("WEB-INF/framework.properties");
		try {
			FileInputStream fis = new FileInputStream(path);
			props.load(fis);
			fis.close();
			for (Object key : props.keySet()) {
				String value = (String) props.get(key);
				config.put(key.toString(), value);
				System.err.println("putting: "+key+" value: "+value);
			}		
			
		} catch (IOException e) {
			context.log("Error reading framework.properties at: "+path, e);
		}


		config.put("SYSTEMBUNDLE_ACTIVATORS_PROP",
				Arrays.asList(new ProvisionActivator(this.context)));
		System.err.println("Final map: "+config);
		logger.warn("ACTUAL CONFIG: ");
		for (Entry<String,Object> e : config.entrySet()) {
			logger.warn("Key: "+e.getKey()+" Value: >"+e.getValue()+"<");
		}
		logger.warn("END OF CONFIG");

		return config;
	}

	
}
