package com.dexels.navajo.server.listener.http;

public interface SchedulableServlet {
	public void setTmlScheduler(TmlScheduler s);

	public TmlScheduler getTmlScheduler();
}
