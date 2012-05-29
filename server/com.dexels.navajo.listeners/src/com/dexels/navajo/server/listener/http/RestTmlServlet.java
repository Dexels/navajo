package com.dexels.navajo.server.listener.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

@Deprecated
/**
 * Replaced by NqlServlet
 */
public class RestTmlServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 8550857560638790482L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(RestTmlServlet.class);
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		callDirect(req, resp);
	}

	private final void callDirect(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String service = request.getParameter("service");
//		String type = request.getParameter("type");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		logger.info(">in callDirect(): service = " + service
				+ ", username = " + username + " class: "
				+ getClass().getName());

		if (service == null) {

			// logger.info("Empty service specified, request originating from "
			// + request.getRemoteHost());
			logger.info("thread = " + Thread.currentThread().hashCode());
			logger.info("path = " + request.getPathInfo());
			logger.info("query = " + request.getQueryString());
			logger.info("protocol = " + request.getProtocol());
			logger.info("agent = " + request.getRemoteUser());
			logger.info("uri = " + request.getRequestURI());
			logger.info("method = " + request.getMethod());
			logger.info("contenttype = " + request.getContentType());
			logger.info("scheme = " + request.getScheme());
			logger.info("server = " + request.getServerName());
			logger.info("port = " + request.getServerPort());
			logger.info("contentlength = " + request.getContentLength());
			Enumeration<String> enm = request.getHeaderNames();
			while (enm.hasMoreElements()) {
				String key = enm.nextElement();
				String header = request.getHeader(key);
				logger.info(">>" + key + "=" + header);
			}
			return;
		}

		Navajo tbMessage;
		try {
			tbMessage = constructFromRequest(request);
			Header header = constructHeader(tbMessage, service, username,
					password, -1);
			tbMessage.addHeader(header);
			LocalClient lc = (LocalClient) getServletContext().getAttribute("localClient");
			if(lc==null) {
				response.sendError(500,"No navajocontext configured (in NavajoFilterServlet)");
				return;
			}			
			Navajo resultMessage = lc.call(tbMessage);
			response.setContentType("text/xml");
			ServletOutputStream outputStream = response.getOutputStream();

			java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(
					outputStream, "UTF-8");
			response.setContentType("text/xml; charset=UTF-8");
			writeOutput(resultMessage, out, service);
			// resultMessage.write(out);
			out.flush();
			out.close();

		} catch (NavajoException e) {
			logger.error("Error: ", e);
		} catch (FatalException e) {
			logger.error("Error: ", e);
		}

	}

	protected void writeOutput(Navajo resultMessage,
			java.io.OutputStreamWriter out, String serviceName)
			throws NavajoException {
		Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(
				resultMessage, "navajoDataSource");

		XMLDocumentUtils.write(laszlo, out, false);
	}

	protected Header constructHeader(Navajo tbMessage, String service,
			String username, String password, long expirationInterval) {
		return NavajoFactory.getInstance().createHeader(tbMessage, service,
				username, password, expirationInterval);
	}

	private final Navajo constructFromRequest(HttpServletRequest request)
			throws NavajoException {

		Navajo result = NavajoFactory.getInstance().createNavajo();

		Enumeration<String> all = request.getParameterNames();

		// Construct TML document from request parameters.
		while (all.hasMoreElements()) {
			String parameter = all.nextElement().toString();

			if (parameter.indexOf("/") != -1) {

				StringTokenizer typedParameter = new StringTokenizer(parameter,
						"|");

				String propertyName = typedParameter.nextToken();
				
				String type = (typedParameter.hasMoreTokens() ? typedParameter
						.nextToken() : Property.STRING_PROPERTY);

				String value = request.getParameter(parameter);

				Message msg = com.dexels.navajo.mapping.MappingUtils
						.getMessageObject(parameter, null, false, result,
								false, "", -1);
				String propName = com.dexels.navajo.mapping.MappingUtils
						.getStrippedPropertyName(propertyName);
				Property prop = null;

				if (propName.indexOf(":") == -1) {
					prop = NavajoFactory.getInstance().createProperty(result,
							propName, type, value, 0, "", Property.DIR_IN);
					msg.addProperty(prop);
				} else {
					StringTokenizer selProp = new StringTokenizer(propName, ":");
					propertyName = selProp.nextToken();
					selProp.nextToken();

					prop = msg.getProperty(propertyName);
					if (prop == null) {
						prop = NavajoFactory.getInstance().createProperty(
								result, propertyName, "+", "", Property.DIR_IN);
						msg.addProperty(prop);
					} else {
						prop.setType(Property.SELECTION_PROPERTY);
						prop.setCardinality("+");
					}

					StringTokenizer allValues = new StringTokenizer(value, ",");
					while (allValues.hasMoreTokens()) {
						String val = allValues.nextToken();
						Selection sel = NavajoFactory.getInstance()
								.createSelection(result, val, val, true);
						prop.addSelection(sel);
					}
				}

			}
		}
		return result;
	}
}
