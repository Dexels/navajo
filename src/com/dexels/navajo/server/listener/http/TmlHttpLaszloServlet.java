package com.dexels.navajo.server.listener.http;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.server.*;

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

		Dispatcher dis = null;
		BufferedInputStream is = null;
		try {

			Navajo in = null;

			if (useRecvCompression) {
				java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
				is = new BufferedInputStream(unzip);
				in = createNavajoFromLaszlo(is);
			} else {
				is = new BufferedInputStream(request.getInputStream());
				in = createNavajoFromLaszlo(is);
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

			// Create dispatcher object.
			dis = Dispatcher.getInstance(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader(), request.getServerName() + request.getRequestURI());

			// Check for certificate.
			Object certObject = request.getAttribute("javax.servlet.request.X509Certificate");

			// Call Dispatcher with parsed TML document as argument.
//			System.err.println("Dispatching now!");
			Navajo outDoc = dis.handle(in, certObject, new ClientInfo(request.getRemoteAddr(), request.getRemoteHost(), recvEncoding, pT, useRecvCompression, useSendCompression, request.getContentLength(), created));
//			outDoc.write(System.err);
			long sendStart = System.currentTimeMillis();
			if (useSendCompression) {
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Content-Encoding", "gzip");
				java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(response.getOutputStream());

				Document laszlo = createLaszloFromNavajo(outDoc);
				XMLDocumentUtils.write(laszlo, new OutputStreamWriter(gzipout), false);
				gzipout.close();
			} else {
				response.setContentType("text/xml; charset=UTF-8");
				OutputStream out = (OutputStream) response.getOutputStream();
				Document laszlo = createLaszloFromNavajo(outDoc);
				XMLDocumentUtils.write(laszlo, new OutputStreamWriter(out), false);
				out.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	private Navajo createNavajoFromLaszlo(BufferedInputStream is) {
		Navajo n = null;
		try {
			Document doc = XMLDocumentUtils.createDocument(is, false);

//			System.err.println("Received: " + XMLDocumentUtils.toString(doc));

			Node root = doc.getFirstChild();
			n = NavajoFactory.getInstance().createNavajo();
			if (root != null) {

				Node tml = root.getFirstChild();

				String rpc_name = ((Element) tml).getAttribute("rpc_name");
				rpc_name = rpc_name.replaceAll("_", "/");
				String rpc_usr = ((Element) tml).getAttribute("rpc_usr");
				String rpc_pwd = ((Element) tml).getAttribute("rpc_pwd");

				Header h = NavajoFactory.getInstance().createHeader(n, rpc_name, rpc_usr, rpc_pwd, -1);
				n.addHeader(h);
				NodeList children = tml.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node noot = children.item(i);
					createMessageFromLaszlo(noot, n, null);
				}
			}
//			System.err.println("Created navajo: ");
//			n.write(System.err);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	private Document createLaszloFromNavajo(Navajo in) {
		Document doc = XMLDocumentUtils.createDocument();
		try {
//			System.err.println("Creating laszlo for:");
//			in.write(System.err);

			String nodeName = in.getHeader().getRPCName();
			nodeName = nodeName.replaceAll("/", "_");
			Element root = doc.createElement(nodeName);
			doc.appendChild(root);
			Element tml = doc.createElement("tml");
			tml.setAttribute("rpc_usr", in.getHeader().getRPCUser());
			tml.setAttribute("rpc_pwd", in.getHeader().getRPCPassword());
			tml.setAttribute("rpc_name", in.getHeader().getRPCName());
			root.appendChild(tml);
			ArrayList l = in.getAllMessages();
			for (int i = 0; i < l.size(); i++) {
				appendMessage((Message) l.get(i), tml, doc);
			}
//			System.err.println("Created doc: " + XMLDocumentUtils.toString(doc));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	private void appendMessage(Message m, Element e, Document d) {
		try{
		if (m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) {
			Element row = d.createElement("row");
			ArrayList allProp = m.getAllProperties();
			for (int j = 0; j < allProp.size(); j++) {
				Property cp = (Property) allProp.get(j);
				if(cp.getType().equals(Property.SELECTION_PROPERTY)){
					Element prop = d.createElement(cp.getName());
					ArrayList sel = cp.getAllSelections();
					for(int k=0;k<sel.size();k++){
						Selection s = (Selection)sel.get(k); 
						Element option = d.createElement("option");
						option.setAttribute("value", s.getValue());
						option.setAttribute("name", s.getName());
						option.setAttribute("selected", ""+s.isSelected());
						prop.appendChild(option);
					}
					row.appendChild(prop);
				}
				row.setAttribute(cp.getName(), cp.getValue());
			}
			e.appendChild(row);
		}
		if (m.getType().equals(Message.MSG_TYPE_ARRAY)) {
			Element array = d.createElement("a_" + m.getName());
			e.appendChild(array);
			for (int i = 0; i < m.getArraySize(); i++) {
				Message row = m.getMessage(i);
				appendMessage(row, array, d);
			}
		}
		if (m.getType().equals(Message.MSG_TYPE_SIMPLE) || m.getType().equals(Message.MSG_TYPE)) {
			Element mes = d.createElement("m_" + m.getName());
			ArrayList allMes = m.getAllMessages();
			for (int k = 0; k < allMes.size(); k++) {
				Message cm = (Message) allMes.get(k);
				appendMessage(cm, mes, d);
			}
			appendProperties(m, mes, d);
			e.appendChild(mes);
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void createMessageFromLaszlo(Node node, Navajo n, Message msg) {
		try {
			String name = node.getNodeName().substring(2);
			String type = Message.MSG_TYPE_SIMPLE;
			if (node.getNodeName().startsWith("a_")) {
				type = Message.MSG_TYPE_ARRAY;
			}
			Message m = NavajoFactory.getInstance().createMessage(n, name, type);

			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node cn = nl.item(i);
				if (cn.getNodeName().startsWith("p_")) {
					createPropertyFromLaszlo(cn, n, m);
				} else if (cn.getNodeName().startsWith("m_") || cn.getNodeName().startsWith("a_")) {
					createMessageFromLaszlo(cn, n, m);
				} else if (cn.getNodeName().equals("row")) {
					createMessageFromRow(cn, n, m);
				}
			}

			if (msg == null) {
				n.addMessage(m);
			} else {
				msg.addMessage(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSelectionFromLaszlo(Element cn, Navajo n, Property p){
		try{
		String name = cn.getAttribute("name");
		String value = cn.getAttribute("value");
		String selected = cn.getAttribute("selected");
		Selection s = NavajoFactory.getInstance().createSelection(n, name, value, "true".equals(selected));
		p.addSelection(s);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void createPropertyFromLaszlo(Node cn, Navajo n, Message m) {
		try {
			Element elm = (Element) cn;
			String name = elm.getNodeName().substring(2);
			String length = elm.getAttribute("length");
			String type = elm.getAttribute("type");
			String direction = elm.getAttribute("direction");
			String value = elm.getAttribute("value");
			String description = elm.getAttribute("description");

			int l = 0;
			try {
				l = Integer.parseInt(length);
			} catch (Exception e) {
			}
			if(type.equals(Property.SELECTION_PROPERTY)){
				Property p = NavajoFactory.getInstance().createProperty(n, name, "1", description, direction);
				NodeList options = elm.getChildNodes();
				for (int i = 0; i < options.getLength(); i++) {
					Node option = (Element)options.item(i);
					if(option.getNodeName().startsWith("option")){
						createSelectionFromLaszlo((Element)option, n, p);
					}
				}
				m.addProperty(p);
			}else{
				Property p = NavajoFactory.getInstance().createProperty(n, name, type, value, l, description, direction);
				m.addProperty(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createMessageFromRow(Node cn, Navajo n, Message m) {
		try {
			Message row = NavajoFactory.getInstance().createMessage(n, m.getName(), Message.MSG_TYPE_ARRAY_ELEMENT);
			Element elm = (Element) cn;
			NamedNodeMap properties = elm.getAttributes();
			for (int i = 0; i < properties.getLength(); i++) {
				Node prop = properties.item(i);
				String name = prop.getNodeName();
				String value = prop.getNodeValue();
				Property p = NavajoFactory.getInstance().createProperty(n, name, Property.STRING_PROPERTY, value, 0, "", "in");
				row.addProperty(p);
			}
			
			NodeList selProps = elm.getChildNodes();
			for(int j = 0;j < selProps.getLength(); j++){
				Element prop = (Element)selProps.item(j);
				String name = prop.getNodeName();
				Property p = NavajoFactory.getInstance().createProperty(n, name, "1", "", "in");
				
				NodeList options = prop.getChildNodes();
				for(int k = 0; k < options.getLength(); k++){
					Element op = (Element)options.item(k);
					String op_name = op.getAttribute("name");
					String op_value = op.getAttribute("value");
					String op_selected = op.getAttribute("selected");
					Selection s = NavajoFactory.getInstance().createSelection(n, op_name, op_value, "true".equals(op_selected));
					p.addSelection(s);
				}
				row.addProperty(p);
			}
			m.addMessage(row);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void appendProperties(Message m, Element e, Document d) {
		try {
			ArrayList allProp = m.getAllProperties();
			for (int i = 0; i < allProp.size(); i++) {
				Property current = (Property) allProp.get(i);

				Element prop = d.createElement("p_" + current.getName());
				prop.setAttribute("value", current.getValue());
				prop.setAttribute("description", current.getDescription());
				prop.setAttribute("direction", current.getDirection());
				prop.setAttribute("type", current.getType());
				prop.setAttribute("length", "" + current.getLength());

				if (current.getType().equals(Property.SELECTION_PROPERTY)) {
					ArrayList sel = current.getAllSelections();
					for(int j=0;j<sel.size();j++){
						Selection s = (Selection)sel.get(j); 
						Element option = d.createElement("option");
						option.setAttribute("value", s.getValue());
						option.setAttribute("name", s.getName());
						option.setAttribute("selected", ""+s.isSelected());
						prop.appendChild(option);
					}
				}
				e.appendChild(prop);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		NavajoClientFactory.getClient().setServerUrl("ficus:3000/sportlink/knvb/servlet/Postman");
		NavajoClientFactory.getClient().setUsername("ROOT");
		NavajoClientFactory.getClient().setPassword("");
		try {
			Message init = NavajoClientFactory.getClient().doSimpleSend("matchform/InitGetCurrentMatches", "User");
			init.getProperty("UserId").setValue("BBFW63X");
			init.getProperty("PinCode").setValue("8567");
			Navajo n = NavajoClientFactory.getClient().doSimpleSend(init.getRootDoc(), "matchform/ProcessQueryCurrentMatches");

			// n.write(System.err);
			TmlHttpLaszloServlet t = new TmlHttpLaszloServlet();
			Document d = t.createLaszloFromNavajo(n);

			String s = XMLDocumentUtils.toString(d);
			// System.err.println(s);

			// ----------------------------------------------

			Node root = d.getFirstChild();
			Navajo nav = NavajoFactory.getInstance().createNavajo();
			if (root != null) {
				String rpc_name = root.getNodeName();
				rpc_name = rpc_name.replaceAll("_", "/");
				Node tml = root.getFirstChild();

				String rpc_usr = ((Element) tml).getAttribute("rpc_usr");
				String rpc_pwd = ((Element) tml).getAttribute("rpc_pwd");

				Header h = NavajoFactory.getInstance().createHeader(nav, rpc_name, rpc_usr, rpc_pwd, -1);
				nav.addHeader(h);

				NodeList children = tml.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node noot = children.item(i);
					t.createMessageFromLaszlo(noot, nav, null);
				}
			}
			nav.write(System.err);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}