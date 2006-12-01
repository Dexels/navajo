package com.dexels.navajo.server.listener.soap;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Dispatcher;

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

      String soapAction = request.getParameter("SOAPAction");
      if (soapAction == null) {
        soapAction = "";
      }
      
      System.err.println("SOAPAction is " + soapAction );
      
      // Parse incoming SOAP request.
      docIn = XMLDocumentUtils.createDocument(request.getInputStream(), false);
      docIn.getDocumentElement().normalize();
      
      NodeList l = docIn.getElementsByTagName("SOAP-ENV:Header");
      if ( l == null || l.getLength() == 0) {
    	  return;
      }
      
      Element header = (Element) l.item(0);
      System.err.println("header = " + header.getTextContent());
      soapAction = header.getTextContent();
      
      // Transform to TML.
      System.err.println("Original XML:");
      StringWriter w = new StringWriter();
      XMLDocumentUtils.write(docIn, w);
      System.err.println(w.toString());
      
      Document tmlIn = XMLDocumentUtils.transformToDocument(docIn, new File("/home/orion/projects/Navajo/soap/xml2tml.xsl"));
      System.err.println("Transformed XML");
      w = new StringWriter();
      XMLDocumentUtils.write(tmlIn, w);
      System.err.println(w.toString());
      
      Navajo navajoIn = NavajoFactory.getInstance().createNavajo(new java.io.StringReader(w.toString()));
      System.err.println("Navajo XML:");
      Header h = NavajoFactory.getInstance().createHeader(navajoIn, soapAction, "ROOT", "", -1);
      navajoIn.addHeader(h);
      
      navajoIn.write(System.err);
      
      
     

      // Create dispatcher object and handle request.
      Dispatcher dis = Dispatcher.getInstance(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader(), "??");
      Navajo navajoOut = dis.handle(navajoIn);

      // Transform TML response to SOAP response.
      javax.xml.parsers.DocumentBuilderFactory builderFactory  = DocumentBuilderFactory.newInstance();
      javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
      Document between = builder.parse(new java.io.StringBufferInputStream(navajoOut.toString()));
      Document docOut = XMLDocumentUtils.transformToDocument(between, new File("/home/orion/projects/Navajo/soap/tml2xml.xsl"));

      // Get output stream.
      response.setContentType("text/xml; charset=UTF-8");
      OutputStream out = (OutputStream) response.getOutputStream();

      // Send SOAP response to outputstream.
      XMLDocumentUtils.toXML(docOut, null, null, new StreamResult(out));

     } catch (Exception e) {
       e.printStackTrace();
       new ServletException(e);
     }


   }
}