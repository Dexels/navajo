package com.dexels.navajo.server.listener.http;

import javax.servlet.http.HttpServlet;

import com.dexels.navajo.server.listener.http.schedulers.DummyScheduler;

public class SchedulerTools {


	@SuppressWarnings("unchecked")
	public static TmlScheduler createScheduler(HttpServlet servlet) {
		String schedulerName = servlet.getInitParameter("schedulerClass");
		if(schedulerName!=null) {
			try {
				Class<TmlScheduler> cc = (Class<TmlScheduler>) Class.forName(schedulerName);
				TmlScheduler scheduler = cc.newInstance();
				scheduler.initialize(servlet);
				return scheduler;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return new DummyScheduler();
	}
	
}
