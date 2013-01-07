package com.dexels.navajo.script.api;

import javax.servlet.ServletContext;


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



}
