package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jitu.rx.servlet.ObservableServlet;

import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;

public class StreamServlet extends HttpServlet {

	private static final long serialVersionUID = -8542457605577199300L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("Ok!");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ObservableXmlFeeder xmlParser = new ObservableXmlFeeder();
		ObservableNavajoParser navajoParser = new ObservableNavajoParser(parseHeaders(req));

		Observable<ByteBuffer> in = ObservableServlet.create(req.getInputStream());
		Observable<Void> out = ObservableServlet.create(resp.getOutputStream());
		in.flatMap(xmlParser::feed);
	}
	
	
	private static  Map<String, Object> parseHeaders(HttpServletRequest req) {
		Map<String,Object> result = new HashMap<>();
		Enumeration<String> header = req.getHeaderNames();
		while(header.hasMoreElements()) {
			String key = header.nextElement();
			result.put(key, req.getHeader(key));
		}
		return result;
	}

	
}
