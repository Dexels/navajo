package com.dexels.navajo.server.listener.http.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public class NavajoGetFilter extends BaseNavajoPostmanFilter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		System.err.println("NavajoGetFilter active!");
		if(servletRequest.getMethod().equals("GET")) {
			Navajo req;
			try {
				req = callDirect(servletRequest, (HttpServletResponse)response);
//				req.se
				delegateToPostman(req, request, response, chain);
			} catch (NavajoException e) {
				e.printStackTrace();
				throw new ServletException("Error calling direct:", e);
			}			
		} else {
			chain.doFilter(request, response);
		}
	}
	
	protected Header constructHeader(Navajo tbMessage, String service, String username, String password, long expirationInterval) {
		return NavajoFactory.getInstance().createHeader(tbMessage,service, username, password, expirationInterval);
	}

	
	// Copied from TmlServlet, removed the actual service call to the Dispatcher
	private final Navajo callDirect(HttpServletRequest request,
			HttpServletResponse response) throws
			ServletException, IOException, NavajoException {

		String service = request.getParameter("service");
		String type = request.getParameter("type");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.err.println("in callDirect(): service = " + service + ", username = " + username);

		if (service == null) {

			//System.err.println("Empty service specified, request originating from " + request.getRemoteHost());
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
			Enumeration enm = request.getHeaderNames();
			while (enm.hasMoreElements()) {
				String key = (String) enm.nextElement();
				String header = request.getHeader(key);
				System.err.println(">>" + key + "=" + header);
			}
			return null;
		}

		if (username == null) {
			username = "empty";
			password = "";
		}

		if ( (type == null) || (type.equals(""))) {
			type = "xml";

		}

		if (password == null) {
			password = "";

		}
		long expirationInterval = -1;
		String expiration = request.getParameter("expiration");

		if ( (expiration == null) || (expiration.equals(""))) {
			expirationInterval = -1;
		}
		else {
			try {
				expirationInterval = Long.parseLong(expiration);
			}
			catch (Exception e) {
				//System.out.println("invalid expiration interval: " + expiration);
			}
		}

		Navajo requestNavajo = null;

		requestNavajo = constructFromGetRequest(request);
		Header header =  constructHeader(requestNavajo, service, username, password, expirationInterval);
		requestNavajo.addHeader(header);
		return requestNavajo;
	}
	

	// Straight copy from TmlHttpServlet
	private final Navajo constructFromGetRequest(HttpServletRequest request) throws NavajoException  {

		Navajo result = NavajoFactory.getInstance().createNavajo();

		Enumeration all = request.getParameterNames();

		// Construct TML document from request parameters.
		while (all.hasMoreElements()) {
			String parameter = all.nextElement().toString();

			if (parameter.indexOf("/") != -1) {

				StringTokenizer typedParameter = new StringTokenizer(parameter, "|");

				String propertyName = typedParameter.nextToken();
				// Check for date property.
				// TODO...
				// Check for array message.
				// TODO...

				String type = (typedParameter.hasMoreTokens() ?
						typedParameter.nextToken() : Property.STRING_PROPERTY);

				String value = request.getParameter(parameter);

				Message msg = com.dexels.navajo.mapping.MappingUtils.getMessageObject(
						parameter, null,
						false, result, false, "", -1);
				String propName = com.dexels.navajo.mapping.MappingUtils.
				getStrippedPropertyName(propertyName);
				Property prop = null;

				if (propName.indexOf(":") == -1) {
					prop = NavajoFactory.getInstance().createProperty(result, propName,
							type, value, 0, "", Property.DIR_IN);
					msg.addProperty(prop);
				}
				else {
					StringTokenizer selProp = new StringTokenizer(propName, ":");
					propertyName = selProp.nextToken();
					String selectionField = selProp.nextToken();

					prop = msg.getProperty(propertyName);
					if (prop == null) {
						prop = NavajoFactory.getInstance().createProperty(result,
								propertyName, "+", "", Property.DIR_IN);
						msg.addProperty(prop);
					}
					else {
						prop.setType(Property.SELECTION_PROPERTY);
						prop.setCardinality("+");
					}

					StringTokenizer allValues = new StringTokenizer(value, ",");
					while (allValues.hasMoreTokens()) {
						String val = allValues.nextToken();
						Selection sel = NavajoFactory.getInstance().createSelection(result,
								val, val, true);
						prop.addSelection(sel);
					}
				}

			}
		}
		return result;
	}

}
