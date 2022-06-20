/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.listener.http.wrapper.laszlo;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.server.listener.http.wrapper.NavajoRequestWrapper;

public class LaszloRequestWrapper implements NavajoRequestWrapper {

	@Override
	public Navajo processRequestFilter(HttpServletRequest request)
			throws IOException, ServletException {
		Navajo in = null;
		// String sendEncoding = request.getHeader("Accept-Encoding");
		String recvEncoding = request.getHeader("Content-Encoding");
		// boolean useSendCompression = ((sendEncoding != null) &&
		// (sendEncoding.indexOf("zip") != -1));
		boolean useRecvCompression = ((recvEncoding != null) && (recvEncoding
				.indexOf("zip") != -1));
		BufferedInputStream is = null;

		if (useRecvCompression) {
			java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(
					request.getInputStream());
			is = new BufferedInputStream(unzip);
			in = NavajoLaszloConverter.createNavajoFromLaszlo(is);
		} else {
			is = new BufferedInputStream(request.getInputStream());
			in = NavajoLaszloConverter.createNavajoFromLaszlo(is);
		}

		return in;
	}

}
