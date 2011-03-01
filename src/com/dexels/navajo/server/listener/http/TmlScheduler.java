package com.dexels.navajo.server.listener.http;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.listeners.Scheduler;
import com.dexels.navajo.listeners.TmlRunnable;

/**
 * 
 * @author frank
 *
 */
public interface TmlScheduler extends Scheduler {


	/**
	 * Called first upon creation. Put any initialization code in this method.
	 * We pass the servlet so you can retrieve other initParameters.

	 */
	public void initialize(HttpServlet servlet);
	
	/**
	 * Called first when a request arrives, only the headers have been loaded, and you should not
	 * retrieve the input stream or assume that any post data is available.
	 * 
	 * Return true if the request should be accepted and processed further.
	 * @param request
	 * @return
	 */
	public boolean preCheckRequest(HttpServletRequest request);
		
	public void removeTmlRunnable(HttpServletRequest req);
	
	public void addTmlRunnable(HttpServletRequest req, TmlRunnable run);
	
	public TmlRunnable getTmlRunnable(HttpServletRequest req);

}
