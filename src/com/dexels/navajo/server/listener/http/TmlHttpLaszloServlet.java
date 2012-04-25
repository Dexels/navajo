package com.dexels.navajo.server.listener.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.server.ClientInfo;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;

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
 * @slightly deprecated
 * @deprecated
 * 
 *             Use NavajoFilterServlet based solution now
 * 
 */

@Deprecated
public final class TmlHttpLaszloServlet extends TmlHttpServlet {

	private static final long serialVersionUID = -6716143312109383514L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TmlHttpLaszloServlet.class);
	
	
	public TmlHttpLaszloServlet() {
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		super.doGet(request, response);
	}

	@Override
	protected void writeOutput(Navajo resultMessage,
			java.io.OutputStreamWriter out, String serviceName)
			throws NavajoException {
		// resultMessage.write(out);

		Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(
				resultMessage, serviceName);
		StringWriter sw = new StringWriter();
		XMLDocumentUtils.write(laszlo, sw, false);
		System.err.println("Laszlo created: " + sw.toString());
		try {
			out.write(sw.toString());
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
		// XMLDocumentUtils.write(laszlo,out,false);
	}

	/**
	 * Handle a request.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public final void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Date created = new java.util.Date();
		long start = created.getTime();

		// System.err.println("in doPost() thread = " +
		// Thread.currentThread().hashCode() + ", " + request.getContentType() +
		// ",
		// " + request.getContentLength() + ", " + request.getMethod() + ", " +
		// request.getRemoteUser());
		String sendEncoding = request.getHeader("Accept-Encoding");
		String recvEncoding = request.getHeader("Content-Encoding");
		boolean useSendCompression = ((sendEncoding != null) && (sendEncoding
				.indexOf("zip") != -1));
		boolean useRecvCompression = ((recvEncoding != null) && (recvEncoding
				.indexOf("zip") != -1));

		DispatcherInterface dis = null;
		BufferedInputStream is = null;
		try {

			Navajo in = null;

			if (useRecvCompression) {
				java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(
						request.getInputStream());
				is = new BufferedInputStream(unzip);
				in = NavajoLaszloConverter.createNavajoFromLaszlo(is);
			} else {
				is = new BufferedInputStream(request.getInputStream());
				in = NavajoLaszloConverter.createNavajoFromLaszlo(is);
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

			String serviceName = header.getRPCName();
			// Create dispatcher object.
			// TODO I don't really understand what's happening here
			dis = DispatcherFactory.getInstance(configurationPath, null,
					new com.dexels.navajo.server.FileInputStreamReader(), null);

//			String servletContextRootPath = getServletContext().getRealPath("");

			// Check for certificate.
			Object certObject = request
					.getAttribute("javax.servlet.request.X509Certificate");

			// Call Dispatcher with parsed TML document as argument.
			// System.err.println("Dispatching now!");
			Header h = in.getHeader();
			// h.write(System.err);

			Navajo outDoc = dis.handle(in, certObject,
					new ClientInfo(request.getRemoteAddr(), "unknown",
							recvEncoding, pT, useRecvCompression,
							useSendCompression, request.getContentLength(),
							created));
			// outDoc.write(System.err);

			if (h != null
					&& outDoc != null
					&& outDoc.getHeader() != null
					&& !Dispatcher.isSpecialwebservice(in.getHeader()
							.getRPCName())) {
				System.err.println("LASZLOSERVLET: ("
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

//			long sendStart = System.currentTimeMillis();
			if (useSendCompression) {
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Content-Encoding", "gzip");
				java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(
						response.getOutputStream());

				Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(
						outDoc, serviceName);
				XMLDocumentUtils.write(laszlo, new OutputStreamWriter(gzipout),
						false);
				gzipout.close();
			} else {
				response.setContentType("text/xml; charset=UTF-8");
				OutputStream out = response.getOutputStream();
				Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(
						outDoc, serviceName);
				XMLDocumentUtils.write(laszlo, new OutputStreamWriter(out),
						false);
				out.close();
			}
		} catch (Throwable e) {
			throw new ServletException(e);
		}
	}

	public static void main(String[] args) throws ClientException, IOException {
		System.setProperty("com.dexels.navajo.DocumentImplementation",
				"com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
		NavajoFactory.getInstance().setExpressionEvaluator(
				new DefaultExpressionEvaluator());
		NavajoClientFactory.getClient().setServerUrl(
				"penelope1.dexels.com/sportlink/test/knvb/Comet");
		NavajoClientFactory.getClient().setUsername("ROOT");
		NavajoClientFactory.getClient().setPassword("R20T");
		Navajo init = NavajoClientFactory.getClient().doSimpleSend(
				"club/InitSearchClubs");
		init.getProperty("ClubSearch/ClubName").setValue("veld");
		NavajoLaszloConverter.dumpNavajoLaszloStyle(init, "kip.xml",
				"club/InitSearchClubs");

		StringWriter sw = new StringWriter();
		NavajoLaszloConverter.writeBirtXml(init.getMessage("ClubSearch"), sw);
		System.err.println(sw.toString());

		Navajo n = NavajoClientFactory.getClient().doSimpleSend(init,
				"club/ProcessSearchClubs");
		NavajoLaszloConverter.dumpNavajoLaszloStyle(n, "aap.xml",
				"club/ProcessSearchClubs");

		init = NavajoClientFactory.getClient().doSimpleSend(
				"club/InitUpdateClub");
		init.getProperty("Club/ClubIdentifier").setValue("BBFW63X");
		n = NavajoClientFactory.getClient().doSimpleSend(init,
				"club/ProcessQueryClub");
		NavajoLaszloConverter.dumpNavajoLaszloStyle(n, "noot.xml",
				"club/ProcessQueryClub");

		System.err.println("Ok, done");

	}

}