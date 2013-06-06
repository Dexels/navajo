package com.dexels.navajo.server.listener.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.InflaterInputStream;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 *
 * $Id$
 *
 */

/**
 * This servlet handles HTTP POST requests. The HTTP POST body is assumed to
 * contain a TML document. The TML document is processed by the dispatcher the
 * resulting TML document is send back as a reply.
 * 
 */

public class TmlHttpServlet extends BaseNavajoServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7121511406958498528L;

	public static final String COMPRESS_NONE = "";

	public static final String DEFAULT_SERVER_XML = "config/server.xml";

	private final static Logger logger = LoggerFactory
			.getLogger(TmlHttpServlet.class);
	
	
	private final static Logger statLogger = LoggerFactory
			.getLogger("stats");
	
	private static boolean streamingMode = true;
	private static long logfileIndex = 0;
	private static long bytesWritten = 0;

	private BundleContext bundleContext;

	public TmlHttpServlet() {
	}

	public void activate(BundleContext bc) {
		logger.debug("Activating legacy postman");
		setBundleContext(bc);
	}

	public void deactivate() {
		logger.debug("Dectivating legacy postman");
	}

	
	/**
	 * @param serviceName  
	 */
	protected void writeOutput(Navajo resultMessage,
			java.io.OutputStreamWriter out, String serviceName)
			throws NavajoException {
		resultMessage.write(out);
	}

	public static final Navajo constructFromRequest(HttpServletRequest request)
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
		
		String service = request.getParameter("service");
		String type = request.getParameter("type");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username == null) {
			username = "empty";
			password = "";
		}

		if ((type == null) || (type.equals(""))) {
			type = "xml";
		}

		if (password == null) {
			password = "";

		}
		long expirationInterval = -1;
		String expiration = request.getParameter("expiration");

		if ((expiration == null) || (expiration.equals(""))) {
			expirationInterval = -1;
		} else {
			try {
				expirationInterval = Long.parseLong(expiration);
			} catch (Exception e) {
				// System.out.println("invalid expiration interval: " +
				// expiration);
			}
		}

		Header h = NavajoFactory.getInstance().createHeader(result, service,
				username, password, expirationInterval);
		result.addHeader(h);
		return result;
	}

	protected static Header constructHeader(Navajo tbMessage, String service,
			String username, String password, long expirationInterval) {
		return NavajoFactory.getInstance().createHeader(tbMessage, service,
				username, password, expirationInterval);
	}

	private final void callDirect(HttpServletRequest request,
			HttpServletResponse response)  {

		String service = request.getParameter("service");
		if (service == null) {

			logRequestParams(request);
			return;
		}
		
		// PrintWriter out = response.getWriter();

		Navajo tbMessage = null;
		DispatcherInterface dis = null;
		dis = DispatcherFactory.getInstance();
		try {

			tbMessage = constructFromRequest(request);
			Navajo resultMessage = handleTransaction(dis, tbMessage, null, null);
			sendResponse(request, response, resultMessage);

		} catch (Exception ce) {
			logger.error("Error: ", ce);
		} finally {
			dis = null;
		}
	}

	private void logRequestParams(HttpServletRequest request) {
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

	private static void sendResponse(HttpServletRequest request,
			HttpServletResponse response, Navajo resultMessage)  {
		ServletOutputStream outputStream = null;

		try {
			String dataPath = request.getParameter("dataPath");
			outputStream = response.getOutputStream();
			if (dataPath != null) {
				Property bin = resultMessage.getProperty(dataPath);
				if (bin == null) {
					java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(
							outputStream, "UTF-8");
					response.setContentType("text/xml; charset=UTF-8");
					resultMessage.write(out);
					out.flush();
					out.close();
				} else {
					// Will throw cce when not a binary?
					if (bin.getTypedValue() instanceof Binary) {
						Binary b = (Binary) bin.getTypedValue();
						response.setContentType(b.getMimeType());
						if (b.getLength() > 0) {
							response.setContentLength((int) b.getLength());
							response.setHeader("Accept-Ranges", "none");
							response.setHeader("Connection", "close");
						}
						copyResource(outputStream, b.getDataAsStream());
					} else {
						outputStream.write(bin.getValue().getBytes());
					}
					outputStream.flush();
				}
			} else {
				java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(
						outputStream, "UTF-8");
				response.setContentType("text/xml; charset=UTF-8");
				resultMessage.write(out);
				out.flush();
				out.close();
			}
		} catch (NavajoException e) {
			logger.error("Error handling response: ",e);
		} catch (IOException e) {
			logger.error("Error handling response: ",e);
		} finally {
			if(outputStream!=null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.warn("Stream closing problem", e);
				}
			}
		}
	}

	/**
	 * URL based webservice requests.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// Check for streamingmode toggle.
		if (request.getParameter("streaming") != null
				&& request.getParameter("streaming").equals("no")) {
			setStreamingMode(false);
			PrintWriter pw = new PrintWriter(response.getWriter());
			pw.write("Switched off streaming mode");
			pw.close();
		} else if (request.getParameter("streaming") != null) {
			setStreamingMode(true);
			PrintWriter pw = new PrintWriter(response.getWriter());
			pw.write("Switched on streaming mode");
			pw.close();
		} else {
			callDirect(request, response);
		}
	}

	private static void setStreamingMode(boolean b) {
		streamingMode = b;
	}

	/**
	 * Handle a request.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		MDC.clear();
		Date created = new java.util.Date();
		long start = created.getTime();

		String sendEncoding = request.getHeader("Accept-Encoding");
		String recvEncoding = request.getHeader("Content-Encoding");

		if(sendEncoding!=null) {
			MDC.put("Accept-Encoding", sendEncoding);
		}
		if(recvEncoding!=null) {
			MDC.put("Content-Encoding", recvEncoding);
		}
		DispatcherInterface dis = null;
		BufferedReader r = null;
		BufferedWriter out = null;
		try {

			Navajo in = null;

			if (streamingMode) {
				if (sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {
					r = new BufferedReader(new java.io.InputStreamReader(
							new InflaterInputStream(request.getInputStream())));
				} else if (sendEncoding != null
						&& sendEncoding.equals(COMPRESS_GZIP)) {
					r = new BufferedReader(new java.io.InputStreamReader(
							new java.util.zip.GZIPInputStream(
									request.getInputStream()), "UTF-8"));
				} else {
					r = new BufferedReader(request.getReader());
				}
				in = NavajoFactory.getInstance().createNavajo(r);
				r.close();
				r = null;
			} else {
				logger.info("Warning: Using non-streaming mode for "
						+ request.getRequestURI() + ", file written: "
						+ logfileIndex + ", total size: " + bytesWritten);
				InputStream is = request.getInputStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				copyResource(bos, is);
				is.close();
				bos.close();
				byte[] bytes = bos.toByteArray();
				try {
					if (sendEncoding != null
							&& sendEncoding.equals(COMPRESS_JZLIB)) {
						r = new BufferedReader(new java.io.InputStreamReader(
								new InflaterInputStream(
										new ByteArrayInputStream(bytes))));
					} else if (sendEncoding != null
							&& sendEncoding.equals(COMPRESS_GZIP)) {
						r = new BufferedReader(new java.io.InputStreamReader(
								new java.util.zip.GZIPInputStream(
										new ByteArrayInputStream(bytes)),
								"UTF-8"));
					} else {
						r = new BufferedReader(new java.io.InputStreamReader(
								new ByteArrayInputStream(bytes)));
					}
					in = NavajoFactory.getInstance().createNavajo(r);
					if (in == null) {
						throw new Exception("Invalid Navajo");
					}
					r.close();
					r = null;
				} catch (Throwable t) {
					// Write request to temp file.
					File f = DispatcherFactory.getInstance().getTempDir();

					if (f != null) {
						bytesWritten += bytes.length;
						logfileIndex++;
						FileOutputStream fos = new FileOutputStream(new File(f,
								"request-" + logfileIndex));
						copyResource(fos, new ByteArrayInputStream(bytes));
						fos.close();
						PrintWriter fw = new PrintWriter(new FileWriter(
								new File(f, "exception-" + logfileIndex)));
						t.printStackTrace(fw);
						fw.flush();
						fw.close();
					}

					dumHttp(request, logfileIndex, f);
					throw new ServletException(t);
				}
			}

			long stamp = System.currentTimeMillis();
			int pT = (int) (stamp - start);

			if (in == null) {
				throw new ServletException("Invalid request.");
			}

			Header header = in.getHeader();
			if (header == null) {
				throw new ServletException("Empty Navajo header.");
			}

			dis = DispatcherFactory.getInstance();
			if (dis == null) {
				System.err
						.println("SERIOUS: No dispatcher found. The navajo context did not initialize properly, check the logs to find out why!");
				return;
			}
			// Check for certificate.
			Object certObject = request
					.getAttribute("javax.servlet.request.X509Certificate");

			// Call Dispatcher with parsed TML document as argument.
			ClientInfo clientInfo = new ClientInfo(request.getRemoteAddr(),
					"unknown", recvEncoding, pT,
					(recvEncoding != null && (recvEncoding
							.equals(COMPRESS_GZIP) || recvEncoding
							.equals(COMPRESS_JZLIB))),
					(sendEncoding != null && (sendEncoding
							.equals(COMPRESS_GZIP) || sendEncoding
							.equals(COMPRESS_JZLIB))),
					request.getContentLength(), created);

			Navajo outDoc = handleTransaction(dis, in, certObject, clientInfo);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Accept-Ranges", "none");
			response.setHeader("Connection", "close");

			if (recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {
				response.setHeader("Content-Encoding", COMPRESS_JZLIB);
				out = new BufferedWriter(new OutputStreamWriter(
						new DeflaterOutputStream(response.getOutputStream()), "UTF-8"));
			} else if (recvEncoding != null
					&& recvEncoding.equals(COMPRESS_GZIP)) {
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

			if ( in.getHeader() != null
					&& outDoc.getHeader() != null
					&& !Dispatcher.isSpecialwebservice(in.getHeader()
							.getRPCName())) {
				statLogger.info("("
						+ dis.getApplicationId()
						+ "): "
						+ new java.util.Date()
						+ ": "
						+ outDoc.getHeader().getHeaderAttribute("accessId")
						+ ":"
						+ in.getHeader().getRPCName()
						+ "("
						+ in.getHeader().getRPCUser()
						+ "):"
						+ (System.currentTimeMillis() - start)
						+ " ms. (st="
						+ (outDoc.getHeader().getHeaderAttribute("serverTime")
								+ ",rpt="
								+ outDoc.getHeader().getHeaderAttribute(
										"requestParseTime")
								+ ",at="
								+ outDoc.getHeader().getHeaderAttribute(
										"authorisationTime")
								+ ",pt="
								+ outDoc.getHeader().getHeaderAttribute(
										"processingTime")
								+ ",tc="
								+ outDoc.getHeader().getHeaderAttribute(
										"threadCount")
								+ ",cpu="
								+ outDoc.getHeader().getHeaderAttribute(
										"cpuload") + ")" + " (" + sendEncoding
								+ "/" + recvEncoding + ")"));
			}

			out = null;

		} catch (Throwable e) {
			logger.error("Error: ", e);
			dumHttp(request, -1, null);
			if (e instanceof FatalException) {
				FatalException fe = (FatalException) e;
				if (fe.getMessage().equals("500.13")) {
					// Server too busy.
					throw new ServletException("500.13", e);
				}
			}
			throw new ServletException(e);
		} finally {
			dis = null;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Navajo handleTransaction(DispatcherInterface dis, Navajo in,
			Object certObject, ClientInfo clientInfo) throws FatalException {
		if(this.bundleContext!=null) {
			// OSGi environment:
			ServiceReference sr = bundleContext.getServiceReference(LocalClient.class.getName());
			if(sr==null) {
				logger.warn("Resolving localclient service failed. Falling back to regular.");
				return fallbackHandleTransaction(dis, in, certObject, clientInfo);
			}
			LocalClient lc =  (LocalClient)bundleContext.getService(sr);
			Navajo nn = lc.handleInternal(in, certObject, clientInfo);
			bundleContext.ungetService(sr);
			lc = null;
			return nn;
		}
		return fallbackHandleTransaction(dis, in, certObject, clientInfo);
	}

	private Navajo fallbackHandleTransaction(DispatcherInterface dis, Navajo in,
			Object certObject, ClientInfo clientInfo) throws FatalException {
		if(dis==null) {
			throw new FatalException("Navajo configuration problem: No dispatcher available.");
		}
		Navajo outDoc = dis.removeInternalMessages(dis.handle(in, certObject,clientInfo));
		return outDoc;
	}
	
	public void setBundleContext(BundleContext bc) {
		this.bundleContext = bc;
	}
}