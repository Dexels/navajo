package com.dexels.navajo.listeners.camel;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.NavajoCamelComponent;
import com.dexels.navajo.camel.consumer.NavajoCamelConsumer;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.listener.http.continuation.TmlContinuationServlet;

public class CamelContinuationServlet extends TmlContinuationServlet {


	private static final long serialVersionUID = 5842758562516620994L;
	private NavajoCamelComponent component;
	private boolean active = false;
	private String endPointId;
	private NavajoCamelConsumer consumer;
	
	
	public CamelContinuationServlet() {
		System.err.println("Constructing!");
	}
	
	private final static Logger logger = LoggerFactory
			.getLogger(CamelContinuationServlet.class);

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		resp.getWriter().write("hoempapa");
//		
//		if (active) {
//			consumer.process(tr)
//			CamelEndpoint ce = component.getEndpoint(endPointId);
//			Exchange e = ce.createFakeNavajoExchange();
//		} else {
//			logger.warn("Problem: not active");
//		}
//	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TmlRunnable tr = prepareRunnable(req, resp);
		if(active) {
			try {
				consumer.process(tr);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			CamelEndpoint ce =  component.getEndpoint(endPointId);
//			ce.createNavajoExchange(tr);
		} else {
			logger.warn("Problem: not active");
			tr.abort("whoops");
		}
	}
	
	public void activate(Map<String,Object> params) {
		this.active = true;
		if(params!=null) {
			this.endPointId = (String) params.get("endPointId");
		} else {
			logger.warn("No params!");
			this.endPointId = "default";
		}
	}

	public void deactivate() {
		this.active = false;
	}

	
	public void setNavajoCamelComponent(NavajoCamelComponent comp) {
		this.component = comp;
	}

	public void clearNavajoCamelComponent(NavajoCamelComponent comp) {
		this.component = null;
	}

	public void setConsumer(NavajoCamelConsumer consumer) {
		this.consumer = consumer;
	}

	public void clearNavajoCamelComponent(NavajoCamelConsumer consumer) {
		this.consumer = null;
	}
}
