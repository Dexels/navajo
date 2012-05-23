package com.dexels.navajo.server.listener.http.wrapper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;

public interface NavajoRequestWrapper {
	public Navajo processRequestFilter(HttpServletRequest request)
			throws ServletException, IOException;

}
