package com.dexels.navajo.server;

import java.io.*;
import java.sql.Connection;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.xml.transform.stream.StreamResult;

import com.dexels.navajo.util.*;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;

import org.w3c.dom.Document;

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
 * This servlet handles HTTP POST requests.
 * The HTTP POST body is assumed to contain a TML document.
 * The TML document is processed by the dispatcher the resulting TML document is send back as a reply.
 *
 */

public class TmlHttpServlet extends HttpServlet {

  private String configurationPath = "";

  public TmlHttpServlet() {
  }

  public void init(ServletConfig config) throws ServletException
    {
      super.init(config);
      Util.debugLog(this, "In TmlHttpServlet Servlet");
      configurationPath = config.getInitParameter("configuration");
      System.out.println("configurationPath = " + configurationPath);
    }

  public void destroy()
  {
    Util.debugLog(this, "In PostmanServlet destroy()");
  }

  public void finalize()
  {
    Util.debugLog(this, "In TmlHttpServlet finalize()");
  }

  public void doGet(HttpServletRequest request,
		      HttpServletResponse response) throws IOException, ServletException
  {

       response.setContentType("text/html");
       PrintWriter out = response.getWriter();
       out.println("<html> HTTP-GET method is not supported </html>");

  }

  private String getDNAttribute(String subject, String attribute) {
    boolean found = false;
    String result = "";

    StringTokenizer tok = new StringTokenizer(subject, ",");
    while (tok.hasMoreElements() && !found) {
      String pair = tok.nextToken();
      Util.debugLog(this, "Found pair: " + pair);
      StringTokenizer tok2 = new StringTokenizer(pair, "=");
      String attr = tok2.nextToken();
      String value = tok2.nextToken();
      Util.debugLog(this, attr+"/"+value);
      if (attr.equals(attribute)) {
        found = true;
        result = value;
      }
    }
    return result;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
     try {

        Util.debugLog("in TMLHTTPSERVLET");
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
          String header = (String) headers.nextElement();
          Util.debugLog(header + ":" + request.getHeader(header));
        }
        Navajo in = Util.parseReceivedDocument(new BufferedInputStream(request.getInputStream()));

        // Create dispatcher object.
        System.out.println("About to create dispatcher");
        Dispatcher dis = new Dispatcher(configurationPath);

        // Check for certificate.
        javax.security.cert.X509Certificate cert = null;
        String certs = (String) request.getAttribute("javax.servlet.request.X509Certificate");
        Util.debugLog(this, "Certificate: " + certs);
        try {
// inputstream
//           cert = javax.security.cert.X509Certificate.getInstance(new StringBufferInputStream(certs));
           cert = javax.security.cert.X509Certificate.getInstance(new ByteArrayInputStream(certs.getBytes()));
        } catch (Exception e) {
            Util.debugLog(this, e.getMessage());
        }

        String subjectDN = "";
        String CN = "";
        if (cert != null) {
          Util.debugLog(this, "Got certificate");
          subjectDN = cert.getSubjectDN().getName();
          Util.debugLog(this, "Subject: " + subjectDN);
          CN = getDNAttribute(subjectDN, "CN");
          Util.debugLog(this, "CN: " + CN);
        }

        String rpcUser = Dispatcher.getRPCUser(in);

        if ((cert != null) && Dispatcher.doMatchCN() && (!CN.equals(rpcUser)) && !rpcUser.equals("ANONYMOUS")) {
          Util.debugLog(this, "CN name and rpcUser don't match");
          throw new ServletException("Unauthorized access");
        }
        //System.err.println("After log user, open connections: " + myBroker.getUseCount());

        // Call Dispatcher with parsed TML document as argument.
        Navajo outDoc = dis.handle(in);
        OutputStream out = (OutputStream) response.getOutputStream();
        Document xml = outDoc.getMessageBuffer();
        Util.debugLog(this, "sendNavajoDocument(): about to send XML");

        XMLDocumentUtils.toXML(xml,null,null,new StreamResult( out ));

     out.close();
        Util.debugLog(this, "sendNavajoDocument(): Done");
     } catch (FatalException e) {
       throw new ServletException(e);
     } catch (NavajoException te) {
       throw new ServletException(te);
     }
  }
}
