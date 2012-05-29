package com.dexels.navajo.server.listener.nql;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLContext;
import com.dexels.navajo.client.nql.OutputCallback;
import com.dexels.navajo.document.NavajoException;

public class NqlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1365612001727053259L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(NqlServlet.class);
	
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String server = req.getParameter("server");
		String query = req.getParameter("query");
		String ping = req.getParameter("ping");

		if (ping != null) {
			if (!checkPing(username, password, req, resp)) {
				throw new ServletException("ping failed.");
			}
			return;
		}
		NQLContext nc = new NQLContext();
		nc.setupClient(server, username, password, req.getServerName(),
				req.getServerPort(), req.getContextPath(),"/PostmanLegacy");

		nc.setCallback(new OutputCallback() {

			public void setOutputType(String mime) {
				resp.setContentType(mime);
			}

			public void setContentLength(long l) {
				resp.setContentLength((int) l);
				resp.setHeader("Accept-Ranges", "none");
				resp.setHeader("Connection", "close");
			}

			public OutputStream getOutputStream() {
				try {
					return resp.getOutputStream();
				} catch (IOException e) {
					logger.error("Error: ", e);
					return null;
				}
			}
		});
		try {
			nc.executeCommand(query);
		} catch (ClientException e) {
			logger.error("Error: ", e);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
		// String
	}

	private boolean checkPing(String username, String password,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write(
				"Hi " + username + " looks like we're in business!\nPING OK\n");
		return true;
	}

}
