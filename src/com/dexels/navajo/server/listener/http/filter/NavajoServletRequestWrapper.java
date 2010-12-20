package com.dexels.navajo.server.listener.http.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public class NavajoServletRequestWrapper extends HttpServletRequestWrapper {

	protected final Navajo input;
	
	public NavajoServletRequestWrapper(Navajo input, HttpServletRequest request) {
		super(request);
		this.input = input;
	}

	@Override
	public String getMethod() {
		return "POST";
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		try {
			return new NavajoServletInputStream(input);
		} catch (NavajoException e) {
			e.printStackTrace();
			return super.getInputStream();
		}
	}
	
	private static class NavajoServletInputStream extends javax.servlet.ServletInputStream {
		private ByteArrayInputStream myStream;

		public NavajoServletInputStream(Navajo n) throws NavajoException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			n.write(baos);
			myStream = new ByteArrayInputStream(baos.toByteArray());
		}

		public int read() {
			return myStream.read();
		}
	}
}
