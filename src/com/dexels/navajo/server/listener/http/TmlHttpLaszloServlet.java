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

import org.w3c.dom.Document;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
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
 */

public final class TmlHttpLaszloServlet extends TmlHttpServlet {

	public TmlHttpLaszloServlet() {
	}
	
	
	

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		super.doGet(request, response);
	}
	
	@Override

	protected void writeOutput(Navajo resultMessage, java.io.OutputStreamWriter out, String serviceName) throws NavajoException {
//		resultMessage.write(out);

		Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(resultMessage,serviceName);
		StringWriter sw = new StringWriter();
		XMLDocumentUtils.write(laszlo,sw,false);
		System.err.println("Laszlo created: "+sw.toString());
		try {
			out.write(sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		XMLDocumentUtils.write(laszlo,out,false);
	}


	/**
	 * Handle a request.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Date created = new java.util.Date();
		long start = created.getTime();

		// System.err.println("in doPost() thread = " +
		// Thread.currentThread().hashCode() + ", " + request.getContentType() + ",
		// " + request.getContentLength() + ", " + request.getMethod() + ", " +
		// request.getRemoteUser());
		String sendEncoding = request.getHeader("Accept-Encoding");
		String recvEncoding = request.getHeader("Content-Encoding");
		boolean useSendCompression = ((sendEncoding != null) && (sendEncoding.indexOf("zip") != -1));
		boolean useRecvCompression = ((recvEncoding != null) && (recvEncoding.indexOf("zip") != -1));

		DispatcherInterface dis = null;
		BufferedInputStream is = null;
		try {

			Navajo in = null;

			if (useRecvCompression) {
				java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
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
			dis = DispatcherFactory.getInstance(configurationPath,null, new com.dexels.navajo.server.FileInputStreamReader(),null);
			
			 String servletContextRootPath = getServletContext().getRealPath("");

			// Check for certificate.
			Object certObject = request.getAttribute("javax.servlet.request.X509Certificate");

			// Call Dispatcher with parsed TML document as argument.
//			System.err.println("Dispatching now!");
			Header h = in .getHeader();
			//h.write(System.err);
			
			Navajo outDoc = dis.handle(in, certObject, new ClientInfo(request.getRemoteAddr(), "unknown", recvEncoding, pT, useRecvCompression, useSendCompression, request.getContentLength(), created));
//			outDoc.write(System.err);
			
			if ( h != null && outDoc != null && outDoc.getHeader() != null && !Dispatcher.isSpecialwebservice(in.getHeader().getRPCName())) {
				System.err.println("LASZLOSERVLET: (" + dis.getApplicationId() + "): " + new java.util.Date() + ": " + outDoc.getHeader().getHeaderAttribute("accessId") + ":" + in.getHeader().getRPCName() + "(" + in.getHeader().getRPCUser() + "):" + ( System.currentTimeMillis() - start ) + " ms. (st=" + 
						( outDoc.getHeader().getHeaderAttribute("serverTime") + ",rpt=" + outDoc.getHeader().getHeaderAttribute("requestParseTime") + ",at=" + outDoc.getHeader().getHeaderAttribute("authorisationTime") + ",pt=" + 
								outDoc.getHeader().getHeaderAttribute("processingTime") + ",tc=" + outDoc.getHeader().getHeaderAttribute("threadCount") + ",cpu=" + outDoc.getHeader().getHeaderAttribute("cpuload") +  ")" + " (" + sendEncoding + "/" + recvEncoding + ")" ));
			}
			  
	        long sendStart = System.currentTimeMillis();
			if (useSendCompression) {
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Content-Encoding", "gzip");
				java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(response.getOutputStream());

				Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(outDoc,serviceName);
				XMLDocumentUtils.write(laszlo, new OutputStreamWriter(gzipout), false);
				gzipout.close();
			} else {
				response.setContentType("text/xml; charset=UTF-8");
				OutputStream out = (OutputStream) response.getOutputStream();
				Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(outDoc,serviceName);
				XMLDocumentUtils.write(laszlo, new OutputStreamWriter(out),false);
				out.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}


	public static void main(String[] args) {
		System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		NavajoClientFactory.getClient().setServerUrl("ficus:3000/sportlink/knvb/servlet/Postman");
		NavajoClientFactory.getClient().setUsername("ROOT");
		NavajoClientFactory.getClient().setPassword("");
		try {
			Message init = NavajoClientFactory.getClient().doSimpleSend("club/InitSearchClubs", "ClubSearch");
			init.getProperty("ClubName").setValue("veld");
			Navajo n = NavajoClientFactory.getClient().doSimpleSend(init.getRootDoc(), "club/ProcessSearchClubs");
			NavajoLaszloConverter.dumpNavajoLaszloStyle(n,"c:/aap.xml","club/ProcessSearchClubs");
			
			init = NavajoClientFactory.getClient().doSimpleSend("club/InitUpdateClub", "Club");
			init.getProperty("ClubIdentifier").setValue("BBFW63X");
			n = NavajoClientFactory.getClient().doSimpleSend(init.getRootDoc(), "club/ProcessQueryClub");
			NavajoLaszloConverter.dumpNavajoLaszloStyle(n,"c:/noot.xml","club/ProcessQueryClub");

			// ----------------------------------------------

//			Node root = d.getFirstChild();
//			Navajo nav = NavajoFactory.getInstance().createNavajo();
//			if (root != null) {
//				String rpc_name = root.getNodeName();
//				rpc_name = rpc_name.replaceAll("_", "/");
//				Node tml = root.getFirstChild();
//
//				String rpc_usr = ((Element) tml).getAttribute("rpc_usr");
//				String rpc_pwd = ((Element) tml).getAttribute("rpc_pwd");
//
//				Header h = NavajoFactory.getInstance().createHeader(nav, rpc_name, rpc_usr, rpc_pwd, -1);
//				nav.addHeader(h);
//
//				NodeList children = tml.getChildNodes();
//				for (int i = 0; i < children.getLength(); i++) {
//					Node noot = children.item(i);
//					t.createMessageFromLaszlo(noot, nav, null);
//				}
//			}
//			nav.write(System.err);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}