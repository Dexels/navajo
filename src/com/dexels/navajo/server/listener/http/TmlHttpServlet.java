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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.ClientInfo;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.FatalException;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

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
	private static boolean streamingMode = true;
	private static long logfileIndex = 0;
	private static long bytesWritten = 0;

	public TmlHttpServlet() {
	}

	protected void finalize() {
		System.err.println("In TmlHttpServlet finalize(), thread = "
				+ Thread.currentThread().hashCode());
		try {
			super.finalize();
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	protected void writeOutput(Navajo resultMessage,
			java.io.OutputStreamWriter out, String serviceName)
			throws NavajoException {
		resultMessage.write(out);
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

	protected Header constructHeader(Navajo tbMessage, String service,
			String username, String password, long expirationInterval) {
		return NavajoFactory.getInstance().createHeader(tbMessage, service,
				username, password, expirationInterval);
	}

	private final void callDirect(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String service = request.getParameter("service");
		String type = request.getParameter("type");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.err.println("in callDirect(): service = " + service
				+ ", username = " + username);

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

		if (username == null) {
			username = "empty";
			password = "";
			// logger.log(Priority.FATAL,
			// "Empty service specified, request originating from " +
			// request.getRemoteHost());
			// System.err.println("Empty service specified, request originating from "
			// + request.getRemoteHost());
			// return;
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

		ServletOutputStream outputStream = response.getOutputStream();

		// PrintWriter out = response.getWriter();

		Navajo tbMessage = null;
		DispatcherInterface dis = null;

		try {
			dis = initDispatcher();

			tbMessage = constructFromRequest(request);
			Header header = constructHeader(tbMessage, service, username,
					password, expirationInterval);
			tbMessage.addHeader(header);
			Navajo resultMessage = dis.removeInternalMessages(dis
					.handle(tbMessage));
			// System.err.println(resultMessage.toString());
			// resultMessage.write(out);
			String dataPath = request.getParameter("dataPath");
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

		} catch (Exception ce) {
			logger.error("Error: ", ce);
		} finally {
			outputStream.close();
			dis = null;
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
			streamingMode = false;
			PrintWriter pw = new PrintWriter(response.getWriter());
			pw.write("Switched off streaming mode");
			pw.close();
		} else if (request.getParameter("streaming") != null) {
			streamingMode = true;
			PrintWriter pw = new PrintWriter(response.getWriter());
			pw.write("Switched on streaming mode");
			pw.close();
		} else {
			callDirect(request, response);
		}
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

		Date created = new java.util.Date();
		long start = created.getTime();

		String sendEncoding = request.getHeader("Accept-Encoding");
		String recvEncoding = request.getHeader("Content-Encoding");

		DispatcherInterface dis = null;
		BufferedReader r = null;
		BufferedWriter out = null;
		try {

			Navajo in = null;

			if (streamingMode) {
				if (sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {
					r = new BufferedReader(new java.io.InputStreamReader(
							new ZInputStream(request.getInputStream())));
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
				System.err.println("Warning: Using non-streaming mode for "
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
								new ZInputStream(
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

			dis = initDispatcher();
			if (dis == null) {
				System.err
						.println("SERIOUS: No dispatcher found. The navajo context did not initialize properly, check the logs to find out why!");
				return;
			}
			// Check for certificate.
			Object certObject = request
					.getAttribute("javax.servlet.request.X509Certificate");

			// Call Dispatcher with parsed TML document as argument.
			Navajo outDoc = dis.removeInternalMessages(dis.handle(
					in,
					certObject,
					new ClientInfo(request.getRemoteAddr(), "unknown",
							recvEncoding, pT,
							(recvEncoding != null && (recvEncoding
									.equals(COMPRESS_GZIP) || recvEncoding
									.equals(COMPRESS_JZLIB))),
							(sendEncoding != null && (sendEncoding
									.equals(COMPRESS_GZIP) || sendEncoding
									.equals(COMPRESS_JZLIB))), request
									.getContentLength(), created)));

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Accept-Ranges", "none");
			response.setHeader("Connection", "close");

			if (recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {
				response.setHeader("Content-Encoding", COMPRESS_JZLIB);
				out = new BufferedWriter(new OutputStreamWriter(
						new ZOutputStream(response.getOutputStream(),
								JZlib.Z_BEST_SPEED), "UTF-8"));
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

			if (in != null
					&& in.getHeader() != null
					&& outDoc != null
					&& outDoc.getHeader() != null
					&& !Dispatcher.isSpecialwebservice(in.getHeader()
							.getRPCName())) {
				System.err.println("("
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
					throw new ServletException("500.13",e);
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
}