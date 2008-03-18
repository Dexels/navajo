package com.dexels.navajo.server.listener.soap;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.w3c.dom.*;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.FatalException;

public class TmlSoapServlet extends HttpServlet {


	private String configurationPath = "";
	public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

	public TmlSoapServlet() {}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		configurationPath = config.getInitParameter("configuration");
		System.setProperty(DOC_IMPL,QDSAX);   
	}

	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Document docIn = null;

		System.err.println("in SOAPServlet doPost()");
		try {


			String soapAction = request.getHeader("SOAPAction");
			if (soapAction == null) {
				soapAction = "";
			}
			soapAction = soapAction.replaceAll("\"", "");

			System.err.println("SOAPAction is " + soapAction );

			// Parse incoming SOAP request.
			docIn = XMLDocumentUtils.createDocument(request.getInputStream(), false);
			docIn.getDocumentElement().normalize();

			System.err.println("Request");
			XMLDocumentUtils.write(docIn, new OutputStreamWriter(System.err), false);

			Node header = XMLutils.findNode(docIn, "Header");
			String navajoUser = "";
			String navajoPwd = "";
			if ( header != null ) {
				// find authentication node.
				Node a = XMLutils.findNode(header, "authentication");
				if  ( a != null ) {
					Element authentication = (Element) a;
					navajoUser = authentication.getAttribute("username");
					navajoPwd = authentication.getAttribute("password");
				}
			}
			
			StringWriter w = new StringWriter();
			XMLDocumentUtils.write(docIn, w, false);
			
			Document tmlIn = XMLDocumentUtils.transformToDocument(docIn, new File(Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/soap/xml2tml.xsl"));
			
			w = new StringWriter();
			XMLDocumentUtils.write(tmlIn, w, false);
			
			Navajo navajoIn = NavajoFactory.getInstance().createNavajo(new java.io.StringReader(w.toString()));
			System.err.println("Navajo XML:");
			
			// What to do with username/password information???
			// Create special thingy in SOAP header??
			Header h = NavajoFactory.getInstance().createHeader(navajoIn, soapAction, navajoUser, navajoPwd, -1);
			navajoIn.addHeader(h);

			navajoIn.write(System.err);




			// Create dispatcher object and handle request.
			Dispatcher dis = Dispatcher.getInstance(configurationPath,null, new com.dexels.navajo.server.FileInputStreamReader(), "??");
			Navajo navajoOut = dis.handle(navajoIn);

			// Check for errors.
			if ( navajoOut.getMessage("error") != null ) {
				// Generate SOAP fault message.

			}

			// Transform TML response to SOAP response.
			javax.xml.parsers.DocumentBuilderFactory builderFactory  = DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document between = builder.parse(new java.io.StringBufferInputStream(navajoOut.toString()));
			Document docOut = XMLDocumentUtils.transformToDocument(between, new File(Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/soap/tml2xml.xsl"));

			// Get output stream.
			response.setContentType("text/xml; charset=UTF-8");
			OutputStream out = (OutputStream) response.getOutputStream();

			// Send SOAP response to outputstream.
			XMLDocumentUtils.toXML(docOut, null, null, new StreamResult(out));

		} catch (NavajoException e) {
			e.printStackTrace();
			new ServletException(e);
		
		} catch (SAXException e) {
			e.printStackTrace();
			new ServletException(e);
		}

		catch (ParserConfigurationException e) {
			e.printStackTrace();
			new ServletException(e);
		}
		
		catch (TransformerConfigurationException e) {
			e.printStackTrace();
			new ServletException(e);
		}
		
		catch (TransformerException e) {
			e.printStackTrace();
			new ServletException(e);
		}
		
		catch (FatalException e) {
			e.printStackTrace();
			new ServletException(e);
		}
	}
	
	public static void main(String [] args) throws Exception {
		
		Document docIn = XMLDocumentUtils.createDocument(new FileInputStream("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/match/InitUpdateMatch2.xml"), false);
		//Document docIn = XMLDocumentUtils.createDocument(new FileInputStream("/home/arjen/projecten/Navajo/untitled1.xml"), false);
		Document tmlIn = XMLDocumentUtils.transformToDocument(docIn, new File("/home/arjen/projecten/Navajo/soap/tml2xml.xsl"));
		System.err.println("Transformed XML");
		StringWriter w = new StringWriter();
		XMLDocumentUtils.write(tmlIn, w, false);
		System.err.println(w.toString());
		
	}
}