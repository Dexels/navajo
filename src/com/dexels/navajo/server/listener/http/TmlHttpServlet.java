package com.dexels.navajo.server.listener.http;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.ClientInfo;

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

public final class TmlHttpServlet extends HttpServlet {

  private String configurationPath = "";

  public TmlHttpServlet() {}



  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    configurationPath = config.getInitParameter("configuration");

  }

  public void destroy() {
    System.err.println("In TmlHttpServlet destroy()");
    // Kill Dispatcher.
    Dispatcher.killMe();
  }

  public void finalize() {
    System.err.println("In TmlHttpServlet finalize(), thread = " + Thread.currentThread().hashCode());
    //logger.log(Priority.INFO, "In TmlHttpServlet finalize()");
  }

  private final Navajo constructFromRequest(HttpServletRequest request) throws
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
    return result;
  }

  private final void callDirect(HttpServletRequest request,
                                HttpServletResponse response) throws
      ServletException, IOException {

    String service = request.getParameter("service");
    String type = request.getParameter("type");
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    System.err.println("in callDirect(): service = " + service + ", username = " + username);

    if (service == null) {
      
      System.err.println("Empty service specified, request originating from " + request.getRemoteHost());
      System.err.println("thread = " + Thread.currentThread().hashCode());
      System.err.println("path = " + request.getPathInfo());
      System.err.println("query = " + request.getQueryString());
      System.err.println("protocol = " + request.getProtocol());
      System.err.println("agent = " + request.getRemoteUser());
      System.err.println("uri = " + request.getRequestURI());
      System.err.println("method = " + request.getMethod());
      System.err.println("contenttype = " + request.getContentType());
      System.err.println("scheme = " + request.getScheme());
      System.err.println("server = " + request.getServerName());
      System.err.println("port = " + request.getServerPort());
      System.err.println("contentlength = " + request.getContentLength());
      Enumeration enum = request.getHeaderNames();
      while (enum.hasMoreElements()) {
        String key = (String) enum.nextElement();
        String header = request.getHeader(key);
        System.err.println(">>" + key + "=" + header);
      }
      return;
    }

    if (username == null) {
      //logger.log(Priority.FATAL, "Empty service specified, request originating from " + request.getRemoteHost());
      System.err.println("Empty service specified, request originating from " + request.getRemoteHost());
      return;
    }

    if ( (type == null) || (type.equals(""))) {
      type = "xml";

    }
    if (username == null) {
      username = "";

    }
    if (password == null) {
      password = "";

    }
    long expirationInterval = -1;
    String expiration = request.getParameter("expiration");

    if ( (expiration == null) || (expiration.equals(""))) {
      expirationInterval = -1;
    }
    else {
      try {
        expirationInterval = Long.parseLong(expiration);
      }
      catch (Exception e) {
        //System.out.println("invalid expiration interval: " + expiration);
      }
    }
    
    java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(response.getOutputStream(),"UTF-8");

    // PrintWriter out = response.getWriter();
    response.setContentType("text/xml; charset=UTF-8");

    Navajo tbMessage = null;
    Dispatcher dis = null;

    try {
      dis = Dispatcher.getInstance(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader(),
    		  request.getServerName() + request.getRequestURI());
    		  	  
      tbMessage = constructFromRequest(request);
      Header header = NavajoFactory.getInstance().createHeader(tbMessage,service, username, password,expirationInterval);
      tbMessage.addHeader(header);
      Navajo resultMessage = dis.handle(tbMessage);
      //System.err.println(resultMessage.toString());
      //resultMessage.write(out);
      resultMessage.write(out);
      
    }
    catch (Exception ce) {
      ce.printStackTrace();
      System.err.println(ce.getMessage());
    }
    finally {
      out.close();
      dis = null;
    }
  
  }

  /**
   * URL based webservice requests.
   *
   * @param request
   * @param response
   * @throws IOException
   * @throws ServletException
   */
  public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
  
        callDirect(request, response);

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

    //System.err.println("in doPost() thread = " + Thread.currentThread().hashCode() + ", " + request.getContentType() + ", " + request.getContentLength() + ", " + request.getMethod() + ", " + request.getRemoteUser());
    String sendEncoding = request.getHeader("Accept-Encoding");
    String recvEncoding = request.getHeader("Content-Encoding");
    boolean useSendCompression = ( (sendEncoding != null) && (sendEncoding.indexOf("zip") != -1));
    boolean useRecvCompression = ( (recvEncoding != null) && (recvEncoding.indexOf("zip") != -1));

    Dispatcher dis = null;
    BufferedInputStream is = null;
    try {

      Navajo in = null;
      
      if (useRecvCompression) {
        java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
        is = new BufferedInputStream(unzip);
        in = NavajoFactory.getInstance().createNavajo(is);
      }
      else {
    	is = new BufferedInputStream(request.getInputStream());
        in = NavajoFactory.getInstance().createNavajo(is);
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
      dis = Dispatcher.getInstance(new java.net.URL(configurationPath), 
    		  new com.dexels.navajo.server.FileInputStreamReader(),
    		  request.getServerName() + request.getRequestURI()
      );
     
      // Check for certificate.
      Object certObject = request.getAttribute( "javax.servlet.request.X509Certificate");

      // Call Dispatcher with parsed TML document as argument.
      Navajo outDoc = dis.handle(in, certObject, 
    		  new ClientInfo(request.getRemoteAddr(), request.getRemoteHost(),
          recvEncoding, pT, useRecvCompression, useSendCompression, request.getContentLength(), created));

      long sendStart = System.currentTimeMillis();
      if (useSendCompression) {
        response.setContentType("text/xml; charset=UTF-8");
        response.setHeader("Content-Encoding", "gzip");
        java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(response.getOutputStream());
        outDoc.write(gzipout);
        gzipout.close();
      }
      else {
        response.setContentType("text/xml; charset=UTF-8");
        OutputStream out = (OutputStream) response.getOutputStream();
        outDoc.write(out);
        out.close();
      }
      System.err.println("Sending response for " + in.getHeader().getRPCName() + " took " + 
    		  (System.currentTimeMillis() - sendStart)/1000.0 + " secs.");

    }
    catch (FatalException e) {
//      logger.log(Priority.INFO,
//                 "Received request from " + request.getRemoteAddr() +
//                 ": invalid request");
//      logger.log(Priority.FATAL, e.getMessage());
      throw new ServletException(e);
    }
    catch (NavajoException te) {
//      logger.log(Priority.INFO,
//                 "Received request from " + request.getRemoteAddr() +
//                 "): invalid request");
//      logger.log(Priority.ERROR, te.getMessage());
      throw new ServletException(te);
    }
    finally {
      dis = null;
      if ( is != null ) {
    	  try {
    		  is.close();
    	  } catch (Exception e) {
    		  // NOT INTERESTED.
    	  }
      }
      //finishedServing();
    }
  }
}