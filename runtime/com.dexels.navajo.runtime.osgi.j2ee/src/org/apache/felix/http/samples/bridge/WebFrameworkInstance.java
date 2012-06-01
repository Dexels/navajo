package org.apache.felix.http.samples.bridge;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.osgi.runtime.FrameworkInstance;

public class WebFrameworkInstance extends FrameworkInstance {

	private final ServletContext context;
	private ServiceRegistration<ServletContext> servletContextRegistration;
	private final static String BUNDLEDIR = "WEB-INF/bundles/";

	
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
		servletContextRegistration.unregister();
		if (this.context != null) {
			this.context.removeAttribute("org.osgi.framework.BundleContext");
		}
		super.doStop();
	}


	@Override
	protected Map createConfig() throws Exception {
		Map<String, Object> config = super.createConfig();
		config.put("SYSTEMBUNDLE_ACTIVATORS_PROP",
				Arrays.asList(new ProvisionActivator(this.context)));

		return config;
	}

	
}
