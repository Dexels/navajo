package com.dexels.navajo.script.api;

import javax.servlet.ServletException;


public interface SchedulableServlet {
	public TmlScheduler getTmlScheduler() throws ServletException;
}
