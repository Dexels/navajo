package com.dexels.navajo.server.listener.http.wrapper.laszlo;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.server.listener.http.wrapper.NavajoRequestWrapper;

public class LaszloRequestWrapper implements NavajoRequestWrapper {

	
	private final static Logger logger = LoggerFactory
			.getLogger(LaszloRequestWrapper.class);
	
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
		logger.info("=============== NAVAJO REQUEST ===============");
		try {
			in.write(System.err);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		logger.info("===========END  NAVAJO REQUEST ===============");

		return in;
	}

}
