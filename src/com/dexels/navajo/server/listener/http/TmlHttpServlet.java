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
        //System.out.println("configurationPath = " + configurationPath);

        // get logger configuration as DOM
        Document configDOM = null;
        try {
          configDOM = XMLDocumentUtils.createDocument(new java.net.URL(configurationPath).openStream(), false);
        } catch (Exception e) {
          throw new ServletException(e);
        }

        Navajo configFile = new com.dexels.navajo.document.jaxpimpl.NavajoImpl(configDOM); //NavajoFactory.getInstance().createNavajo(configDOM);
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

    private Navajo constructFromRequest(HttpServletRequest request) throws
        NavajoException {

      Navajo result = NavajoFactory.getInstance().createNavajo();

      Enumeration all = request.getParameterNames();

      // Construct TML document from request parameters.
      while (all.hasMoreElements()) {
        String parameter = all.nextElement().toString();

        if (parameter.indexOf("/") != -1) {

          StringTokenizer typedParameter = new StringTokenizer(parameter, "|");

          String propertyName = typedParameter.nextToken();
          // Check for date property.
          // TODO...
          // Check for array message.
          // TODO...

          String type = (typedParameter.hasMoreTokens() ?
                         typedParameter.nextToken() : Property.STRING_PROPERTY);

          String value = request.getParameter(parameter);

          Message msg = com.dexels.navajo.mapping.MappingUtils.getMessageObject(
              parameter, null,
              false, result, false, "", -1);
          String propName = com.dexels.navajo.mapping.MappingUtils.
              getStrippedPropertyName(propertyName);
          Property prop = null;

          if (propName.indexOf(":") == -1) {
            prop = NavajoFactory.getInstance().createProperty(result, propName,
                type, value, 0, "", Property.DIR_IN);
            msg.addProperty(prop);
          }
          else {
            StringTokenizer selProp = new StringTokenizer(propName, ":");
            propertyName = selProp.nextToken();
            String selectionField = selProp.nextToken();

            prop = msg.getProperty(propertyName);
            if (prop == null) {
              prop = NavajoFactory.getInstance().createProperty(result,
                  propertyName, "+", "", Property.DIR_IN);
              msg.addProperty(prop);
            }
            else {
              prop.setType(Property.SELECTION_PROPERTY);
              prop.setCardinality("+");
            }

            StringTokenizer allValues = new StringTokenizer(value, ",");
            while (allValues.hasMoreTokens()) {
              String val = allValues.nextToken();
              Selection sel = NavajoFactory.getInstance().createSelection(result,
                  val, val, true);
              prop.addSelection(sel);
            }
          }

        }
      }

      result.write(System.err);

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
                //System.out.println("invalid expiration interval: " + expiration);
            }
        }
        ServletOutputStream outStream = response.getOutputStream();
        java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(outStream, "UTF-8");

        // PrintWriter out = response.getWriter();
        response.setContentType("text/xml; charset=UTF-8");

        Navajo tbMessage = null;


        try {
            Dispatcher dis = new Dispatcher(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader());

            tbMessage = constructFromRequest(request);
            Header header = NavajoFactory.getInstance().createHeader(tbMessage, service, username, password,
                                                                     expirationInterval);
            tbMessage.addHeader(header);
            Navajo resultMessage = dis.handle(tbMessage);
            out.write(resultMessage.toString());
        } catch (Exception ce) {
            ce.printStackTrace();
            System.err.println(ce.getMessage());
        }
        out.close();

    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
      callDirect(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        long start = System.currentTimeMillis();

        try {

            Navajo in = null;

            String sendEncoding = request.getHeader("Accept-Encoding");
            String recvEncoding = request.getHeader("Content-Encoding");

            boolean useSendCompression = ((sendEncoding != null) && (sendEncoding.indexOf("zip") != -1));
            boolean useRecvCompression = ((recvEncoding != null) && (recvEncoding.indexOf("zip") != -1));

            if (useRecvCompression) {
              java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
              in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(unzip));
            } else {
              in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(request.getInputStream()));
            }

            Header header = in.getHeader();
            if (header == null) {
              throw new ServletException("Empty header");
            }

            // Create dispatcher object.
            Dispatcher dis = new Dispatcher(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader());

            // Check for certificate.
            Object certObject = request.getAttribute("javax.servlet.request.X509Certificate");

            String rpcUser = header.getRPCUser();

            /**
             * Set the request data header of the incoming message.
             */
            header.setRequestData(request.getRemoteAddr(), request.getRemoteHost());

            // Call Dispatcher with parsed TML document as argument.
            Navajo outDoc = dis.handle(in, certObject);

            if (useSendCompression) {
              response.setContentType("text/xml; charset=UTF-8");
              response.setHeader("Content-Encoding", "gzip");
              java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(response.getOutputStream());
              outDoc.write(gzipout);
              gzipout.close();
            } else {
              //System.err.println("SEND USING UTF-8");
              response.setContentType("text/xml; charset=UTF-8");
              OutputStream out = (OutputStream) response.getOutputStream();
              outDoc.write(out);
              out.close();
            }
            logger.log(Priority.DEBUG, "sendNavajoDocument(): Done");

            long end = System.currentTimeMillis();
            double pT = (end - start)/1000.0;

            logger.log(Priority.INFO, request.getRemoteAddr() +
                  " " + request.getRemoteHost() + " " + header.getRPCName() +
                  " " + header.getRPCUser() + " processing time: " + pT + " secs.");

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
