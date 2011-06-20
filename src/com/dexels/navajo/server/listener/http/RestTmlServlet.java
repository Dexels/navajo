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
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.FatalException;

@Deprecated
/**
 * Replaced by NqlServlet
 */
public class RestTmlServlet extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8550857560638790482L;

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

		System.err.println(">in callDirect(): service = " + service
				+ ", username = " + username + " class: "
				+ getClass().getName());

		if (service == null) {

			// System.err.println("Empty service specified, request originating from "
			// + request.getRemoteHost());
			System.err.println("thread = " + Thread.currentThread().hashCode());
			System.err.println("path = " + request.getPathInfo());
			System.err.println("query = " + request.getQueryString());
			System.err.println("protocol = " + request.getProtocol());
			System.err.println("agent = " + request.getRemoteUser());
			System.err.println("uri = " + request.getRequestURI());
			System.err.println("method = " + request.getMethod());
			System.err.println("contenttype = " + request.getContentType());
			System.err.println("scheme = " + request.getScheme());
			System.err.println("server = " + request.getServerName());
			System.err.println("port = " + request.getServerPort());
			System.err.println("contentlength = " + request.getContentLength());
			Enumeration<String> enm = request.getHeaderNames();
			while (enm.hasMoreElements()) {
				String key = enm.nextElement();
				String header = request.getHeader(key);
				System.err.println(">>" + key + "=" + header);
			}
			return;
		}

		Navajo tbMessage;
		try {
			tbMessage = constructFromRequest(request);
			Header header = constructHeader(tbMessage, service, username,
					password, -1);
			tbMessage.addHeader(header);
			DispatcherInterface dis = DispatcherFactory.getInstance();
			if (dis == null) {
				throw new ServletException("Navajo Server not initialized!");
			}
			Navajo resultMessage = dis.removeInternalMessages(dis
					.handle(tbMessage));
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
			e.printStackTrace();
		} catch (FatalException e) {
			e.printStackTrace();
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
