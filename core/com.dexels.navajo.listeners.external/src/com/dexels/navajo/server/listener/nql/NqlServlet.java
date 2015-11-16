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
import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.client.context.NavajoRemoteContext;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;
import com.dexels.navajo.document.NavajoException;

public class NqlServlet extends HttpServlet {

	private static final long serialVersionUID = -1365612001727053259L;
	private NqlContextApi nqlContext;
	private ClientContext clientContext;
	
	private final static Logger logger = LoggerFactory
			.getLogger(NqlServlet.class);

	public NqlContextApi getNqlContext() {
		return nqlContext;
	}

	public void setNqlContext(NqlContextApi nqlContext) {
		this.nqlContext = nqlContext;
	}
	public void clearNqlContext(NqlContextApi nqlContext) {
		this.nqlContext = null;
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public void clearClientContext(ClientContext clientContext) {
		this.clientContext = null;
	}

	
	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String server = req.getParameter("server");
		String query = req.getParameter("query");
		String ping = req.getParameter("ping");
		String tenant = req.getParameter("tenant");
		
		if (tenant == null) {
		    // Fall back to header
		    tenant = req.getHeader("X-Navajo-Instance");
		}
		
		if (ping != null) {
			if (!checkPing(username, resp)) {
				throw new ServletException("ping failed.");
			}
			return;
		}
		NavajoRemoteContext nrc =  new NavajoRemoteContext();
		nrc.setupClient(server, username, password, req.getServerName(),
				req.getServerPort(), req.getContextPath(),"/PostmanLegacy");

		
		NqlContextApi nc = getNqlContext();//new NQLContext();
		nc.setNavajoContext(getClientContext());
		
		try {
			nc.executeCommand(query,tenant,username, password, 
					new OutputCallback() {

				@Override
				public void setOutputType(String mime) {
					resp.setContentType(mime);
				}

				@Override
				public void setContentLength(long l) {
					resp.setContentLength((int) l);
					resp.setHeader("Accept-Ranges", "none");
					resp.setHeader("Connection", "close");
				}

				@Override
				public OutputStream getOutputStream() {
					try {
						return resp.getOutputStream();
					} catch (IOException e) {
						logger.error("Error: ", e);
						return null;
					}
				}
			});
		} catch (ClientException e) {
			logger.error("Error: ", e);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
		// String
	}

	private boolean checkPing(String username, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().write(
				"Hi " + username + " looks like we're in business!\nPING OK\n");
		return true;
	}

	public void activate() {
		logger.info("Activating NQL Servlet");
	}

	public void deactivate() {
		logger.info("Deactivating NQL Servlet");
	}

}
