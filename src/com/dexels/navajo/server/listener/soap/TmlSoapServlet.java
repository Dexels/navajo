package com.dexels.navajo.server.listener.soap;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;


import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.*;
import com.dexels.navajo.logger.*;
import com.dexels.navajo.server.Dispatcher;

public class TmlSoapServlet extends HttpServlet {


  private String configurationPath = "";

   private static Logger logger = Logger.getLogger( TmlSoapServlet.class );

   public TmlSoapServlet() {}

   public void init(ServletConfig config) throws ServletException {
       super.init(config);

       configurationPath = config.getInitParameter("configuration");

       Document configDOM = null;
       try {
         configDOM = XMLDocumentUtils.createDocument(new java.net.URL(configurationPath).openStream(), false);
       } catch (Exception e) {
         throw new ServletException(e);
       }

       Navajo configFile = new com.dexels.navajo.document.jaxpimpl.NavajoImpl(configDOM);
       Message m = configFile.getMessage("server-configuration/parameters");

       Element loggerConfig =
             (Element) configDOM.getElementsByTagName( "log4j:configuration" ).item( 0 );

       if ( loggerConfig == null ) {
           ServletException e =
             new ServletException( "logging subsystem log4j is not configured, check server.xml");
               throw ( e );
       }
       DOMConfigurator.configure( loggerConfig );

       logger.log( Priority.INFO, "started logging" );
   }

  public void doGet(HttpServletRequest request,
           HttpServletResponse response) throws IOException, ServletException {

   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

     Document docIn = null;

     try {

      String soapAction = request.getParameter("SOAPAction");
      if (soapAction == null)
        soapAction = "";

      // Parse incoming SOAP request.
      docIn = XMLDocumentUtils.createDocument(request.getInputStream(), false);
      docIn.getDocumentElement().normalize();

      // Transform to TML.
      Document tmlIn = XMLDocumentUtils.transformToDocument(docIn, new File("/home/arjen/projecten/Navajo/soap/xml2tml.xsl"));
      Navajo navajoIn = NavajoFactory.getInstance().createNavajo(tmlIn);
      navajoIn.getHeader().setRPCName(soapAction);

      // Create dispatcher object and handle request.
      Dispatcher dis = Dispatcher.getInstance(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader(), "??");
      Navajo navajoOut = dis.handle(navajoIn);

      // Transform TML response to SOAP response.
      Document docOut = XMLDocumentUtils.transformToDocument((Document) navajoOut.getMessageBuffer(), new File("/home/arjen/projecten/Navajo/soap/tml2xml.xsl"));

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