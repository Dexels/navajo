package com.dexels.navajo.server.listener.http.continuation;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.listener.http.SchedulerTools;
import com.dexels.navajo.server.listener.http.TmlScheduler;

public class ServletListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		TmlScheduler ts = SchedulerTools.createScheduler(servletContext);
		servletContext.setAttribute("tmlScheduler", ts);
		DispatcherInterface di = DispatcherFactory.getInstance();
		servletContext.setAttribute("dispatcherInterface", di);
		
	}

}
