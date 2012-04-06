package com.dexels.navajo.server.listener.http;

import java.util.Collection;

import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.listeners.SchedulerRegistry;
import com.dexels.navajo.server.listener.http.schedulers.DummyScheduler;

public class SchedulerTools {

//	private static final  Map<String,TmlScheduler> injectedSchedulers = new HashMap<String, TmlScheduler>();

	private static final Logger logger = LoggerFactory.getLogger(SchedulerTools.class);
	
	@SuppressWarnings("unchecked")
	public static TmlScheduler createScheduler(HttpServlet servlet) {
		String schedulerName = servlet.getInitParameter("schedulerClass");
		if (schedulerName != null) {
			try {
				TmlScheduler injected = getSchedulerService(schedulerName);
				if(injected!=null) {
					logger.info("Returning scheduler service for: "+schedulerName);
					injected.initialize(servlet);
					return injected;
				}
				logger.info("No injected scheduler found. Falling back to Class.forName");
				Class<TmlScheduler> cc = (Class<TmlScheduler>) Class
						.forName(schedulerName);
				TmlScheduler scheduler = cc.newInstance();
				SchedulerRegistry.setScheduler(scheduler);
				scheduler.initialize(servlet);
				logger.info("Scheduler initialized.");
				return scheduler;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvalidSyntaxException e) {
				e.printStackTrace();
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
		Collection<ServiceReference<TmlScheduler>> bcc = bc.getServiceReferences(TmlScheduler.class, "(schedulerClass="+schedulerName+")");
		if(!bcc.isEmpty()) {
			return bc.getService( bcc.iterator().next());
		}
		logger.info("No TmlScheduler found for: "+schedulerName);
		return null;
	}

}
