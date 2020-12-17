/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
