package com.dexels.navajo.server;


import java.io.*;
import java.sql.Connection;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.xml.transform.stream.StreamResult;

import org.dexels.grus.DbConnectionBroker;

import org.w3c.dom.*;

import com.dexels.navajo.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

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

    private static Logger logger = Logger.getLogger( TmlHttpServlet.class );
    public static boolean useCompression = false;

    public TmlHttpServlet() {}

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        configurationPath = config.getInitParameter("configuration");
        System.out.println("configurationPath = " + configurationPath);

        // get logger configuration as DOM
        Document configDOM = null;
        try {
          configDOM = com.dexels.navajo.xml.XMLDocumentUtils.createDocument(configurationPath);
        } catch (Exception e) {
          throw new ServletException(e);
        }

        Navajo configFile = new Navajo(configDOM);
        Message m = configFile.getMessage("server-configuration/parameters");
        if (m != null) {
          if (m.getProperty("use_compression") != null)
            useCompression = m.getProperty("use_compression").getValue().equals("true");
        }
        System.out.println("COMPRESSION: " + useCompression);

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

    public void destroy() {
        logger.log(Priority.INFO, "In TmlHttpServlet destroy()");
    }

    public void finalize() {
        logger.log(Priority.INFO, "In TmlHttpServlet finalize()");
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html> HTTP-GET method is not supported </html>");

        String info = request.getRemoteAddr() + "\n" +
                      request.getRemoteHost() + "\n" +
                      request.getRemoteUser() + "\n" +
                      request.getRequestURI();
        logger.log(Priority.WARN, "Unauthorized GET access:\n" + info);

    }

    private String getDNAttribute(String subject, String attribute) {
        boolean found = false;
        String result = "";

        StringTokenizer tok = new StringTokenizer(subject, ",");

        while (tok.hasMoreElements() && !found) {
            String pair = tok.nextToken();

            logger.log(Priority.DEBUG, "Found pair: " + pair);
            StringTokenizer tok2 = new StringTokenizer(pair, "=");
            String attr = tok2.nextToken();
            String value = tok2.nextToken();

            logger.log(Priority.DEBUG, attr + "/" + value);
            if (attr.equals(attribute)) {
                found = true;
                result = value;
            }
        }
        return result;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {

            logger.log(Priority.INFO, "Received POST request from " + request.getRemoteAddr() + "(" + request.getRemoteHost() + ")");

            Navajo in = null;
            if (useCompression) {
              java.util.zip.ZipInputStream unzip = new java.util.zip.ZipInputStream(request.getInputStream());
              java.util.zip.ZipEntry zipEntry = unzip.getNextEntry();

              in = Util.parseReceivedDocument(new BufferedInputStream(unzip));
            } else {
              in = Util.parseReceivedDocument(new BufferedInputStream(request.getInputStream()));
            }


            // Create dispatcher object.
            Logger.getLogger (this.getClass()).log(Priority.DEBUG, "Parsed input, about to create dispatcher");
            Dispatcher dis = new Dispatcher(configurationPath);

            // Check for certificate.
            javax.security.cert.X509Certificate cert = null;
            String certs = (String) request.getAttribute("javax.servlet.request.X509Certificate");

            logger.log(Priority.DEBUG, "Certificate: " + certs);
            try {
                // inputstream
                // cert = javax.security.cert.X509Certificate.getInstance(new StringBufferInputStream(certs));
                cert = javax.security.cert.X509Certificate.getInstance(new ByteArrayInputStream(certs.getBytes()));
            } catch (Exception e) {
                logger.log(Priority.WARN, "No or invalid certificate found");
            }

            String subjectDN = "";
            String CN = "";

            if (cert != null) {
                logger.log(Priority.DEBUG, "Got certificate");
                subjectDN = cert.getSubjectDN().getName();
                logger.log(Priority.DEBUG, "Subject: " + subjectDN);
                CN = getDNAttribute(subjectDN, "CN");
                logger.log(Priority.DEBUG, "CN: " + CN);
            }

            String rpcUser = Dispatcher.getRPCUser(in);

            if ((cert != null) && Dispatcher.doMatchCN()
                    && (!CN.equals(rpcUser)) && !rpcUser.equals("ANONYMOUS")) {
                logger.log(Priority.ERROR, "CN name and rpcUser don't match");
                throw new ServletException("Unauthorized access");
            }
            // System.err.println("After log user, open connections: " + myBroker.getUseCount());

            // Call Dispatcher with parsed TML document as argument.
            Navajo outDoc = dis.handle(in);

            logger.log(Priority.DEBUG, "sendNavajoDocument(): about to send XML");

            if (useCompression) {
              java.util.zip.ZipOutputStream out = new java.util.zip.ZipOutputStream(response.getOutputStream());
              StringWriter w = new StringWriter();
              XMLDocumentUtils.toXML(outDoc.getMessageBuffer(), null, null, new StreamResult(w));
              out.putNextEntry(new java.util.zip.ZipEntry("message"));
              out.write(w.toString().getBytes(), 0, w.toString().length());
              out.closeEntry();
              out.close();
            } else {
              OutputStream out = (OutputStream) response.getOutputStream();
              XMLDocumentUtils.toXML(outDoc.getMessageBuffer(), null, null, new StreamResult(out));
              out.close();
            }
            logger.log(Priority.DEBUG, "sendNavajoDocument(): Done");

        } catch (FatalException e) {
            logger.log(Priority.FATAL, e.getMessage());
            throw new ServletException(e);
        } catch (NavajoException te) {
            logger.log(Priority.ERROR, te.getMessage());
            throw new ServletException(te);
        }

    }
}
