package com.dexels.navajo.listeners.camel;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.consumer.NavajoCamelConsumer;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.listener.http.continuation.TmlRunnableBuilder;

public class CamelContinuationServlet extends HttpServlet {


	private static final long serialVersionUID = 5842758562516620994L;
	private boolean active = false;
	private NavajoCamelConsumer consumer;
	private LocalClient localClient;

	private final static Logger logger = LoggerFactory
			.getLogger(CamelContinuationServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		TmlRunnable tr = TmlRunnableBuilder.prepareRunnable(req, resp, localClient);
		if(active) {
			try {
				consumer.process(tr);
			} catch (Exception e) {
				logger.error("Problem offering logger to Camel: ",e);
				tr.abort("whoops");
			}

		} else {
			logger.warn("Problem: not active");
			tr.abort("whoops");
		}
	}
	
	public void activate(Map<String,Object> params) {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}

	public void setConsumer(NavajoCamelConsumer consumer) {
		this.consumer = consumer;
	}

	public void clearNavajoCamelComponent(NavajoCamelConsumer consumer) {
		this.consumer = null;
	}
	
	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}
	
}
