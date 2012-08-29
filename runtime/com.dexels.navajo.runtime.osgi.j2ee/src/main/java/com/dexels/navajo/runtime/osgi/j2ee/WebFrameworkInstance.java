package com.dexels.navajo.runtime.osgi.j2ee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.osgi.runtime.FrameworkInstance;

public class WebFrameworkInstance extends FrameworkInstance {

	private final ServletContext context;
	private ServiceRegistration<ServletContext> servletContextRegistration;
//	private ServiceRegistration<ContextIdentifier> servletContextIdentifierRegistration = null;
	private final static String BUNDLEDIR = "WEB-INF/bundles/";
	

	
	public WebFrameworkInstance(ServletContext context) {
		super(context.getRealPath(BUNDLEDIR));
		log("init WebFrameworkInstance called",null);
		this.context = context;
	}


	@Override
	protected void doStart(String directive) throws Exception {
		super.doStart(directive);
		log("super called",null);
		if (context != null) {
			log("Setting "+"org.osgi.framework.BundleContext"+" : "+getBundleContext(),null);
			context.setAttribute("org.osgi.framework.BundleContext",getBundleContext());
		}
		try {
			log("Reg client tracker",null);
			LocalClientTracker lct = new LocalClientTracker(framework.getBundleContext(), context);
			lct.open();
			log("Reg client tracker opened",null);

			servletContextRegistration = (ServiceRegistration<ServletContext>) framework.getBundleContext().registerService(ServletContext.class.getName(), context, null);
			log("servlet cr registered",null);
//			servletContextIdentifierRegistration = (ServiceRegistration<ContextIdentifier>) framework.getBundleContext().registerService(ContextIdentifier.class.getName(), new ServletContextIdentifier(context), null);
//			log("servlet ci registered",null);
//			log("**************** ContextIdentifier registered",null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
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
	
	@Override
	protected void doStop() throws Exception {
		log("**** SHUTTING DOWN WEBCONTEXT OF OSGI ****", null);
		// TODO Null check?
		if(servletContextRegistration==null) {
			log("Problem while deregistering servletContext", null);
		} else {
			servletContextRegistration.unregister();
		}
//		if(servletContextIdentifierRegistration==null) {
//			log("Problem while deregistering servletContextIdentifier", null);
//		} else {
//			servletContextIdentifierRegistration.unregister();
//		}
		if (this.context != null) {
			this.context.removeAttribute("org.osgi.framework.BundleContext");
		}
		log("**** SHUTTING DOWN WEBCONTEXT OF OSGI COMPLETE ****", null);
		super.doStop();
	}


	@SuppressWarnings({ "rawtypes"})
	@Override
	protected Map createConfig() throws Exception {
		Map<String, Object> config = super.createConfig();
		Properties props = new Properties();
//		logger.warn("PARENT CONFIG: ");
//		for (Entry<String,Object> e : config.entrySet()) {
//			logger.warn("Key: "+e.getKey()+" Value: >"+e.getValue()+"<");
//		}
//		logger.warn("END OF CONFIG");
		
//		props.load(getResource("default.properties"));
		String path = context.getRealPath("WEB-INF/framework.properties");
		try {
			FileInputStream fis = new FileInputStream(path);
			props.load(fis);
			fis.close();
			for (Object key : props.keySet()) {
				String value = (String) props.get(key);
				config.put(key.toString(), value);
//				System.err.println("putting: "+key+" value: "+value);
			}		
			
		} catch (IOException e) {
			context.log("Error reading framework.properties at: "+path, e);
		}

		
		File javaTemp = new File(System.getProperty("java.io.tmpdir"));
		File withContext = new File(javaTemp,context.getContextPath());
		File bundles = new File(withContext,"bundles");
		
		config.put("bundleTmpPath", bundles.getAbsolutePath());
		config.put("org.osgi.framework.storage", bundles.getAbsolutePath());
		
		config.put("SYSTEMBUNDLE_ACTIVATORS_PROP",
				Arrays.asList(new ProvisionActivator(this.context)));
		return config;
	}

	
}
