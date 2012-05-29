package com.dexels.navajo.server.listener.http.standard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.listener.http.impl.BaseServiceRunner;

public class TmlStandardRunner extends BaseServiceRunner implements TmlRunnable {

	private Navajo inputDoc;
	private HashMap<String, Object> attributes = new HashMap<String, Object>();
	

	private final static Logger logger = LoggerFactory
			.getLogger(TmlStandardRunner.class);
	
	public TmlStandardRunner(HttpServletRequest request, Navajo inputDoc,
			HttpServletResponse response, String sendEncoding,
			String recvEncoding, Object cert) {
		super(request, response, sendEncoding, recvEncoding, cert);
		this.inputDoc = inputDoc;
		attributes.put("httpRequest", request);
	}

	public int getActiveCount() {
		return -1;
	}

	@Override
	public void endTransaction() throws IOException {
		response.getOutputStream().close();
	}

	@Override
	public InputStream getRequestInputStream() throws IOException {
		return request.getInputStream();
	}

	@Override
	public OutputStream getRequestOutputStream() throws IOException {
		throw new UnsupportedOperationException(
				"Request outputstreams are disabled on standard sychronous connectors");
	}

	@Override
	public Navajo getInputNavajo() throws IOException {
		return inputDoc;
	}

	@Override
	public void dumpBuffer() {
		logger.warn("Not implemented");
	}

	@Override
	public TmlRunnable getTmlRunnable() {
		return this;
	}

	@Override
	public void setResponseNavajo(Navajo n) {
		inputDoc = n;

	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

}
