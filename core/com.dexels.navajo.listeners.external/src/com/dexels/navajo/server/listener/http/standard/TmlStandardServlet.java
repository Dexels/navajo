/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.listener.http.standard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.listener.http.impl.BaseRequestImpl;
/**
 * Warning, doesn't work any more, as the TmlScheduler will never be set, continuations and TmlListener classic do work
 * @author frank
 *
 */
public class TmlStandardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3737722042000207161L;
	private TmlScheduler myScheduler = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final LocalClient lc = (LocalClient) getServletContext().getAttribute("localClient");
		if(lc==null) {
			resp.sendError(500, "No local client registered in servlet context");
			return;
		}		

		Object certObject = req
				.getAttribute("javax.servlet.request.X509Certificate");
		String recvEncoding = req.getHeader("Content-Encoding");
		String sendEncoding = req.getHeader("Accept-Encoding");
		AsyncRequest request = new BaseRequestImpl(req, resp, sendEncoding, recvEncoding, certObject,"default");
		

		TmlStandardRunner tr = new TmlStandardRunner(request, lc);
		getTmlScheduler().run(tr);
		// TODO broken? fix
//		getTmlScheduler().run(request.instantiateRunnable());
	}

	public TmlScheduler getTmlScheduler() {
		return myScheduler;
	}
}
