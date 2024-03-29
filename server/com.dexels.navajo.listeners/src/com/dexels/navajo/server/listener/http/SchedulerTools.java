/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
				Class<?> cc = Class.forName(schedulerName);
				TmlScheduler scheduler = (TmlScheduler) cc.getDeclaredConstructor().newInstance();
				SchedulerRegistry.setScheduler(scheduler);
				scheduler.initialize(context);
				logger.info("Scheduler initialized.");
				context.setAttribute("tmlScheduler", scheduler);
				return scheduler;
			} catch (Exception e) {
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
