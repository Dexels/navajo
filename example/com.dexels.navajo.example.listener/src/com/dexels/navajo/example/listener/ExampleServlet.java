package com.dexels.navajo.example.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dexels.navajo.document.*;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class ExampleServlet extends HttpServlet {

	private static final long serialVersionUID = 7798880709165548039L;
	

	private final static Logger logger = LoggerFactory
			.getLogger(ExampleServlet.class);
	private LocalClient myClient = null;
	private boolean isActive = false;
	
	
	
	@SuppressWarnings("unused")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String serviceName = req.getParameter("service");
		if(serviceName==null) {
			resp.sendError(500, "No service parameter supplied");
		}
		if(isActive) {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Header h = NavajoFactory.getInstance().createHeader(n, serviceName, "demo", "demo", -1);
			n.addHeader(h);
			logger.info("Calling service: "+serviceName);
			try {
				long time = System.currentTimeMillis();
				Navajo response = myClient.call(n);
				long diff = System.currentTimeMillis() - time;
				resp.setContentType("text/plain");
				resp.getWriter().write("Service: "+serviceName+" took: "+diff+" millis.");
			} catch (FatalException e) {
				resp.sendError(500, "Error calling service: "+serviceName);
			}
		} else {
			logger.info("Not active!");
		}
	}

	public void activate() {
		logger.info("Example servlet activated");
		isActive = true;
	}
	
	public void deactivate() {
		logger.info("Goodbye!");
		isActive = false;
	}
	
	public void setClient(LocalClient lc) {
		this.myClient = lc;
	}

	/**
	 * @param lc the client to clear 
	 */
	public void clearClient(LocalClient lc) {
		this.myClient = null;
	}

}
