package com.dexels.navajo.jsp.proxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.InflaterInputStream;

public class ProxyServlet extends HttpServlet {

	private static final long serialVersionUID = 2618272459465144500L;

	private static final String COMPRESS_GZIP = "gzip";
	private static final String COMPRESS_JZLIB = "jzlib";
	
	private final static Logger logger = LoggerFactory
			.getLogger(ProxyServlet.class);

	private ClientInterface myClient;

	/**
	 * Handle a request.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		MDC.clear();
//		String service = request.getParameter("service");
		String acceptEncoding = request.getHeader("Accept-Encoding");
		String contentEncoding = request.getHeader("Content-Encoding");

		if(acceptEncoding!=null) {
			MDC.put("Accept-Encoding", acceptEncoding);
		}
		if(contentEncoding!=null) {
			MDC.put("Content-Encoding", contentEncoding);
		}
		BufferedReader r = null;
		BufferedWriter out = null;
		try {

			Navajo in = null;

				if (contentEncoding != null && contentEncoding.equals(COMPRESS_JZLIB)) {
					r = new BufferedReader(new java.io.InputStreamReader(
							new InflaterInputStream(request.getInputStream()),"UTF-8"));
				} else if (contentEncoding != null
						&& contentEncoding.equals(COMPRESS_GZIP)) {
					r = new BufferedReader(new java.io.InputStreamReader(
							new java.util.zip.GZIPInputStream(
									request.getInputStream()), "UTF-8"));
				} else {
					r = new BufferedReader(request.getReader());
				}
				in = NavajoFactory.getInstance().createNavajo(r);
				r.close();
				r = null;


			if (in == null) {
				throw new ServletException("Invalid request.");
			}

			Header header = in.getHeader();
			if (header == null) {
				throw new ServletException("Empty Navajo header.");
			}

			Navajo outDoc = doProxy( in);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Accept-Ranges", "none");
			// Why do we want this?
			//response.setHeader("Connection", "close");
			// TODO: support multiple accept encoding
			
			if (acceptEncoding != null && acceptEncoding.equals(COMPRESS_JZLIB)) {
				response.setHeader("Content-Encoding", COMPRESS_JZLIB);
				out = new BufferedWriter(new OutputStreamWriter(
						new DeflaterOutputStream(response.getOutputStream()), "UTF-8"));
			} else if (acceptEncoding != null
					&& acceptEncoding.equals(COMPRESS_GZIP)) {
				response.setHeader("Content-Encoding", COMPRESS_GZIP);
				out = new BufferedWriter(new OutputStreamWriter(
						new java.util.zip.GZIPOutputStream(
								response.getOutputStream()), "UTF-8"));
			} else {
				out = new BufferedWriter(response.getWriter());
			}

			outDoc.write(out);
			out.flush();
			out.close();

			out = null;

		} catch (Throwable e) {
			throw new ServletException(e);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception e) {
					// NOT INTERESTED.
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					// NOT INTERESTED.
				}
			}
		}
	}

	private Navajo doProxy(Navajo in) throws ClientException {
		Header h = in.getHeader();
		if(h==null) {
			logger.error("Call without header");
			return null;
		}
		String service = h.getRPCName();
		return myClient.doSimpleSend(in,service);
	}
	
	private String getApplicationAttribute(String key) {
		ServletContext servletContext = this.getServletContext();
		String value = null;
		if(servletContext!=null) {
			value = servletContext.getInitParameter(key);
		}
		if(value!=null) {
			return value;
		}
		value = System.getenv(key);
		if(value!=null) {
			return value;
		}
		return System.getProperty(key);
	}

	private void setupClient(String server, String username, String password) {
		NavajoClientFactory.resetClient();
		 myClient = NavajoClientFactory.getClient();
		 if (username == null) {
			username = "demo";
		}
		myClient.setUsername(username);
		if (password == null) {
			password = "demo";
		}
		myClient.setPassword(password);
		if (server == null) {
			logger.info("No server supplied.");
			return;
		}
		myClient.setServerUrl(server);		
		myClient.setRetryAttempts(0);
	}
	
	// We do NOT support array messages here. And property types are always String
	private void addProperty(Navajo in, String path, String value){
		StringTokenizer tok = new StringTokenizer(path, "/");
		int count  = tok.countTokens();
		int current = 1;
		Message currentMessage = null;
		
		while(tok.hasMoreElements()){
			String name = tok.nextToken();
			
			if(current == count){				// Property
				Property p = NavajoFactory.getInstance().createProperty(in, name, Property.STRING_PROPERTY, null, 128, "", Property.DIR_IN);
				p.setAnyValue(value);
				currentMessage.addProperty(p);
			} else {							// Message
				if(currentMessage != null){ 	// Look in message
					Message newMessage = currentMessage.getMessage(name);
					if(newMessage == null){
						newMessage = NavajoFactory.getInstance().createMessage(in, name);
						currentMessage.addMessage(newMessage);
					}
					currentMessage = newMessage;
				} else {						// Look in Navajo
					currentMessage = in.getMessage(name);
					if(currentMessage == null){
						currentMessage = NavajoFactory.getInstance().createMessage(in, name);
						in.addMessage(currentMessage);
					} 
				}
			}
			current++;
		}
	}

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String server = getApplicationAttribute("NavajoServer");
		String username = getApplicationAttribute("NavajoUser");
		String password = getApplicationAttribute("NavajoPassword");
		setupClient(server, username, password);
	}

}
