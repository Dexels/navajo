package com.dexels.navajo.script.api;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


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
	public void initialize(ServletContext servlet);

	/**
	 * Called first when a request arrives, only the headers have been loaded,
	 * and you should not retrieve the input stream or assume that any post data
	 * is available.
	 * 
	 * Return true if the request should be accepted and processed further.
	 * 
	 * @param request
	 * @return
	 */
	public boolean preCheckRequest(HttpServletRequest request);

}
