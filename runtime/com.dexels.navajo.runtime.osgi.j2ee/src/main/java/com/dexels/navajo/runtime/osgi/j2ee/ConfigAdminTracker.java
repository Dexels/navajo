package com.dexels.navajo.runtime.osgi.j2ee;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings({ "rawtypes", "unchecked" })

public class ConfigAdminTracker extends ServiceTracker {

	private final String contextPath;
	private final String servletContextPath;
	private final String installationPath;

//	private final static Logger logger = LoggerFactory
//			.getLogger(ConfigAdminTracker.class);
	

    public ConfigAdminTracker(BundleContext bundleContext,String contextPath,String servletContextPath, String installationPath) 
            throws Exception
        {
        	super(bundleContext, ConfigurationAdmin.class.getName() , null);
           	this.contextPath = contextPath;
    		this.servletContextPath = servletContextPath;
    		this.installationPath = installationPath;
        }


    
    public Object addingService(ServiceReference ref)
    {
//    	logger.info("Some kind of service detected: "+ref);
    	ConfigurationAdmin service = (ConfigurationAdmin)super.addingService(ref);
        if (service instanceof ConfigurationAdmin) {
//        	logger.info("Injecting config!");
        	ConfigurationAdmin ca = (ConfigurationAdmin)service;
        	try {
				injectConfig(ca);
			} catch (IOException e) {
//				logger.error("Unable to inject config data! ", e);
				e.printStackTrace();
			}
        }

        return service;
    }
	private void injectConfig(ConfigurationAdmin ca) throws IOException {
		Configuration c = ca.createFactoryConfiguration("navajo.server.context.factory");
		Dictionary<String, String> d = new Hashtable<String,String>();
		d.put("contextPath", contextPath);
		d.put("servletContextPath", servletContextPath);
		d.put("installationPath", installationPath);
		c.update(d);
	
	}
    @Override
    public void removedService(ServiceReference ref, Object service)
    {
        if (service instanceof ConfigurationAdmin) {
//        	logger.info("Config admin is leaving?!");
        }

        super.removedService(ref, service);
    }
}
