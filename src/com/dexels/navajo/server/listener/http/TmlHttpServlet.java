package com.dexels.navajo.server.listener.http;

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
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;

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

    public TmlHttpServlet() {}

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        configurationPath = config.getInitParameter("configuration");
        System.out.println("configurationPath = " + configurationPath);

        // get logger configuration as DOM
        Document configDOM = null;
        try {
          configDOM = XMLDocumentUtils.createDocument(configurationPath);
        } catch (Exception e) {
          throw new ServletException(e);
        }

        Navajo configFile = NavajoFactory.getInstance().createNavajo(configDOM);
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

    public void destroy() {
        logger.log(Priority.INFO, "In TmlHttpServlet destroy()");
    }

    public void finalize() {
        logger.log(Priority.INFO, "In TmlHttpServlet finalize()");
    }

    private Navajo constructFromRequest(HttpServletRequest request) throws NavajoException {

        Navajo result = NavajoFactory.getInstance().createNavajo();

        Enumeration all = request.getParameterNames();

        // Construct TML document from request parameters.
        while (all.hasMoreElements()) {
            String parameter = all.nextElement().toString();

            if (parameter.indexOf("/") != -1) {
                String value = request.getParameter(parameter);
                Message msg = com.dexels.navajo.mapping.XmlMapperInterpreter.getMessageObject(parameter, null,
                                                                          false, result, false, "");
                String propName = com.dexels.navajo.mapping.XmlMapperInterpreter.getStrippedPropertyName(parameter);
                Property prop = null;

                if (propName.indexOf(":") == -1) {
                    prop = NavajoFactory.getInstance().createProperty(result, propName, Property.STRING_PROPERTY, value, 0, "", Property.DIR_IN);
                    msg.addProperty(prop);
                } else {
                    StringTokenizer selProp = new StringTokenizer(propName, ":");
                    String propertyName = selProp.nextToken();
                    String selectionField = selProp.nextToken();
                    Selection sel = NavajoFactory.getInstance().createSelection(result, value, value, true);

                    prop = msg.getProperty(propertyName);
                    if (prop == null) {
                        prop = NavajoFactory.getInstance().createProperty(result, propertyName, "1", "", Property.DIR_IN);
                        msg.addProperty(prop);
                    }
                    prop.addSelection(sel);
                }

            }
        }
        return result;
    }

    private void callDirect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String service = request.getParameter("service");
        String type = request.getParameter("type");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ((type == null) || (type.equals("")))
            type = "xml";

        if (username == null)
          username = "";

        if (password == null)
          password = "";

        long expirationInterval = -1;
        String expiration = request.getParameter("expiration");

        if ((expiration == null) || (expiration.equals(""))) {
            expirationInterval = -1;
        } else {
            try {
                expirationInterval = Long.parseLong(expiration);
            } catch (Exception e) {
                System.out.println("invalid expiration interval: " + expiration);
            }
        }
        ServletOutputStream outStream = response.getOutputStream();
        java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(outStream, "UTF-8");

        // PrintWriter out = response.getWriter();
        response.setContentType("text/xml; charset=UTF-8");

        Navajo tbMessage = null;


        try {
            Dispatcher dis = new Dispatcher(configurationPath);

            tbMessage = constructFromRequest(request);
            Header header = NavajoFactory.getInstance().createHeader(tbMessage, service, username, password,
                                                                     expirationInterval);
            tbMessage.addHeader(header);
            Navajo resultMessage = dis.handle(tbMessage);
            out.write(resultMessage.toString());
        } catch (Exception ce) {
            System.err.println(ce.getMessage());
        }
        out.close();

    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
      callDirect(request, response);
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

            Navajo in = null;

            String sendEncoding = request.getHeader("Accept-Encoding");
            String recvEncoding = request.getHeader("Content-Encoding");
            System.out.println("send encoding = " + sendEncoding);
            System.out.println("recv encoding = " + recvEncoding);
            boolean useSendCompression = ((sendEncoding != null) && (sendEncoding.indexOf("zip") != -1));
            boolean useRecvCompression = ((recvEncoding != null) && (recvEncoding.indexOf("zip") != -1));
            System.out.println("useSendCompression = " + useSendCompression);
            System.out.println("useRecvCompression = " + useRecvCompression);

            if (useRecvCompression) {
              java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
              in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(unzip));
            } else {
              in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(request.getInputStream()));
            }
            Header header = in.getHeader();
            if (header == null)
              throw new ServletException("Empty header");

            logger.log(Priority.INFO, request.getRemoteAddr() +
                       " " + request.getRemoteHost() + " " + header.getRPCName() +
                       " " + header.getRPCUser());

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
                //logger.log(Priority.WARN, "No or invalid certificate found");
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

            String rpcUser = header.getRPCUser();

            /**
             * Set the request data header of the incoming message.
             */
            header.setRequestData(request.getRemoteAddr(), request.getRemoteHost());

            if ((cert != null) && Dispatcher.doMatchCN()
                    && (!CN.equals(rpcUser)) && !rpcUser.equals("ANONYMOUS")) {
                logger.log(Priority.ERROR, "CN name and rpcUser don't match");
                throw new ServletException("Unauthorized access");
            }
            // System.err.println("After log user, open connections: " + myBroker.getUseCount());

            // Call Dispatcher with parsed TML document as argument.
            Navajo outDoc = dis.handle(in);

            logger.log(Priority.DEBUG, "sendNavajoDocument(): about to send XML");

            if (useSendCompression) {
              response.setHeader("Content-Encoding", "gzip");
              java.util.zip.GZIPOutputStream out = new java.util.zip.GZIPOutputStream(response.getOutputStream());
              outDoc.write(out);
              out.close();
            } else {
              OutputStream out = (OutputStream) response.getOutputStream();
              outDoc.write(out);
              out.close();
            }
            logger.log(Priority.DEBUG, "sendNavajoDocument(): Done");

        } catch (FatalException e) {
            logger.log(Priority.INFO, "Received request from " + request.getRemoteAddr() +
                       "(" + request.getRemoteHost() + "): invalid request");
            logger.log(Priority.FATAL, e.getMessage());
            throw new ServletException(e);
        } catch (NavajoException te) {
            logger.log(Priority.INFO, "Received request from " + request.getRemoteAddr() +
                       "(" + request.getRemoteHost() + "): invalid request");
            logger.log(Priority.ERROR, te.getMessage());
            throw new ServletException(te);
        }

    }
}
