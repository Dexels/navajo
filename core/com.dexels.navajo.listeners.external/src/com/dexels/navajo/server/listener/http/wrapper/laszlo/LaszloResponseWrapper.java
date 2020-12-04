/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.listener.http.wrapper.laszlo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.server.listener.http.wrapper.NavajoResponseWrapper;

public class LaszloResponseWrapper implements NavajoResponseWrapper {

	@Override
	public void processResponse(HttpServletRequest originalRequest,
			Navajo indoc, Navajo outDoc, HttpServletResponse originalResponse)
			throws IOException {

		String sendEncoding = originalRequest.getHeader("Accept-Encoding");
		boolean useSendCompression = ((sendEncoding != null) && (sendEncoding
				.indexOf("zip") != -1));

		// Navajo indoc = wrappedRequest.getInputNavajo();
		// Navajo outDoc = wrappedResponse.getResponseNavajo();

		// long sendStart = System.currentTimeMillis();
		if (useSendCompression) {
			originalResponse.setContentType("text/xml; charset=UTF-8");
			originalResponse.setHeader("Content-Encoding", "gzip");
			java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(
					originalResponse.getOutputStream());

			Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(
					outDoc, indoc.getHeader().getRPCName());
			XMLDocumentUtils.write(laszlo, new OutputStreamWriter(gzipout),
					false);
			gzipout.close();
		} else {
			originalResponse.setContentType("text/xml; charset=UTF-8");
			OutputStream out = originalResponse.getOutputStream();
			Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(
					outDoc, indoc.getHeader().getRPCName());
			XMLDocumentUtils.write(laszlo, new OutputStreamWriter(out), false);
			out.close();
		}
	}

}
