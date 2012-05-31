package org.apache.felix.http.samples.bridge;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.LocalClient;


public class LocalClientTracker extends ServiceTracker {
	
	//TODO Delete this class, never got it to work, no idea why. Rewrote it as a DS component.

	private final ServletContext servletContext;
	private BundleContext bundleContext;
	
	

	private final static Logger logger = LoggerFactory
			.getLogger(LocalClientTracker.class);
	
    public LocalClientTracker(BundleContext bundleContext, ServletContext servletContext)
            throws Exception
        {
            super(bundleContext, createFilter(bundleContext), null);
            this.servletContext = servletContext;
            this.bundleContext = bundleContext;
        }


	
    private static Filter createFilter(BundleContext context)
            throws Exception
        {
            StringBuffer str = new StringBuffer();
            str.append("("+Constants.OBJECTCLASS).append("=");
            str.append(LocalClient.class.getName()).append(")");
            return context.createFilter(str.toString());
        }
    public Object addingService(ServiceReference ref)
    {
    	LocalClient service = (LocalClient)super.addingService(ref);
        if (service instanceof LocalClient) {
        	if(servletContext!=null) {
                servletContext.setAttribute("localClient", service);
        	} else {
        		logger.error("Local client detected but no servlet context");
        	}
        }

        return service;
    }

    @Override
    public void removedService(ServiceReference ref, Object service)
    {
        if (service instanceof LocalClient) {
            servletContext.setAttribute("localClient", null);
        }

        super.removedService(ref, service);
    }
}
