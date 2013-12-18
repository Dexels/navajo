package com.dexels.navajo.osgi;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuthBackend {
	public boolean verifyRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String responseType = req.getParameter("response_type");
		if(responseType==null || !responseType.equals("code")) {
			fail("Bad response_type",resp);
		}
		return true;
	}

	private void fail(String message, HttpServletResponse resp) throws IOException {
		resp.sendError(400, message);
	}
	
	
}
