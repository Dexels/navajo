package com.dexels.navajo.server.listener.http.wrapper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Navajo;

public interface NavajoResponseWrapper {
	/**
	 * @param originalRequest
	 * @param wrappedRequest
	 *            could be null, if no inputFilter has been supplied
	 * @param wrappedResponse
	 *            could be null, if no outputFilter has been supplied
	 * @param originalResponse
	 * @throws IOException
	 */
	public void processResponse(HttpServletRequest originalRequest,
			Navajo indoc, Navajo outdoc, HttpServletResponse originalResponse)
			throws IOException;

}
