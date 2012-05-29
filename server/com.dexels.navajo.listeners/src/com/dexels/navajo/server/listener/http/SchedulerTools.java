package com.dexels.navajo.server.listener.http;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.listener.http.schedulers.DummyScheduler;

public class SchedulerTools {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerTools.class);
	
	@SuppressWarnings("unchecked")
	public static TmlScheduler createScheduler(ServletContext context) {
		String schedulerName = context.getInitParameter("schedulerClass");
		if (schedulerName != null) {
			try {
				TmlScheduler injected = getSchedulerService(schedulerName);
				if(injected!=null) {
					logger.info("Returning scheduler service for: "+schedulerName);
					injected.initialize(context);
					return injected;
				}
				logger.info("No injected scheduler found. Falling back to Class.forName");
				Class<TmlScheduler> cc = (Class<TmlScheduler>) Class
						.forName(schedulerName);
				TmlScheduler scheduler = cc.newInstance();
				SchedulerRegistry.setScheduler(scheduler);
				scheduler.initialize(context);
				logger.info("Scheduler initialized.");
				return scheduler;
			} catch (ClassNotFoundException e) {
				logger.error("Error: ", e);
			} catch (InstantiationException e) {
				logger.error("Error: ", e);
			} catch (IllegalAccessException e) {
				logger.error("Error: ", e);
			} catch (InvalidSyntaxException e) {
				logger.error("Error: ", e);
			}
		}
		logger.info("Requested scheduler not found. Returning dummy scheduler.");
		return new DummyScheduler();
	}
//	
//	public static void inject(String name, TmlScheduler ts) {
//		injectedSchedulers.put(name, ts);
//	}

	private static TmlScheduler getSchedulerService(String schedulerName) throws InvalidSyntaxException {
		BundleContext bc =navajolisteners.Version.getDefaultBundleContext();
		if ( bc != null ) {
			Collection<ServiceReference<TmlScheduler>> bcc = bc.getServiceReferences(TmlScheduler.class, "(schedulerClass="+schedulerName+")");
			if(!bcc.isEmpty()) {
				return bc.getService( bcc.iterator().next());
			}
		}
		logger.info("No OSGi TmlScheduler found for: "+schedulerName);
		return null;
	}

}
