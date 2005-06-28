package com.dexels.navajo.server.listener.http;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.log4j.xml.*;
import org.w3c.dom.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;
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

  private final static Logger logger = Logger.getLogger(TmlHttpServlet.class);

  public static int threadCount = 0;
  public static int maxThreadCount = 75;

  public static Object mutex1 = new Object();
  //public static HashSet activeWebservice = new HashSet();

  public TmlHttpServlet() {}


  private final boolean startServing() throws ServletException {
    synchronized (mutex1) {
      threadCount++;
      if (threadCount > maxThreadCount) {
        threadCount--;
        System.err.println("REACHED MAXIMUM SIMULTANEOUS REQUEST (" + maxThreadCount + ") IN TmlHttpServlet");
        // Dump all active threads:
        System.err.println("Access set dump:");
        Iterator s = Dispatcher.accessSet.iterator();
        while (s.hasNext()) {
        	Access a = (Access) s.next();
        	System.err.println("----------------------------------------------------------------");
        	System.err.println(a.rpcName + "(" + a.rpcUser + "), request: ");
        	try {
        		a.getCompiledScript().inDoc.write(System.err);
        		//a.getInDoc().write(System.err);
        	} catch (Throwable t) {
        		System.err.println(t.getMessage());
        	}
        	System.err.println("----------------------------------------------------------------");
        }
        return false;
      }
    }
    System.err.println("Current TmlHttpServlet thread count: " + threadCount);
    return true;
  }

  private final void finishedServing() {
    synchronized (mutex1) {
      threadCount--;
    }
  }

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    configurationPath = config.getInitParameter("configuration");
    String maxT = config.getInitParameter("maxthreads");
    if (maxT != null) {
      try {
        maxThreadCount = Integer.parseInt(maxT);
        System.err.println("MAXIMUM THREAD COUNT IS " + maxThreadCount);
      }
      catch (Exception e) {
        // not a valid integer.
        maxThreadCount = 75;
      }
    }
    //System.out.println("configurationPath = " + configurationPath);

    // get logger configuration as DOM
    Document configDOM = null;
    try {
      configDOM = XMLDocumentUtils.createDocument(new java.net.URL(configurationPath).openStream(), false);
    }
    catch (Exception e) {
      throw new ServletException(e);
    }

    Navajo configFile = new com.dexels.navajo.document.jaxpimpl.NavajoImpl(configDOM);
    //NavajoFactory.getInstance().createNavajo(configDOM);
    Message m = configFile.getMessage("server-configuration/parameters");

    Element loggerConfig =
        (Element) configDOM.getElementsByTagName("log4j:configuration").item(0);

    if (loggerConfig == null) {
      ServletException e =
          new ServletException(
          "logging subsystem log4j is not configured, check server.xml");
      throw (e);
    }
    DOMConfigurator.configure(loggerConfig);

    logger.log(Priority.INFO, "started logging");
  }

  public void destroy() {
    logger.log(Priority.INFO, "In TmlHttpServlet destroy()");
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
      logger.log(Priority.FATAL, "Empty service specified, request originating from " + request.getRemoteHost());
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
      logger.log(Priority.FATAL, "Empty service specified, request originating from " + request.getRemoteHost());
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
    ServletOutputStream outStream = response.getOutputStream();
    java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(outStream,"UTF-8");

    // PrintWriter out = response.getWriter();
    response.setContentType("text/xml; charset=UTF-8");

    Navajo tbMessage = null;
    Dispatcher dis = null;

    try {
      dis = new Dispatcher(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader());
      tbMessage = constructFromRequest(request);
      Header header = NavajoFactory.getInstance().createHeader(tbMessage,service, username, password,expirationInterval);
      tbMessage.addHeader(header);
      //tbMessage.write(System.err);
      Navajo resultMessage = dis.handle(tbMessage);
      out.write(resultMessage.toString());
    }
    catch (Exception ce) {
      ce.printStackTrace();
      System.err.println(ce.getMessage());
    }
    finally {
      dis = null;
    }
    outStream.close();

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
    // Check if request can be served.
    if (startServing()) {
      try {
        callDirect(request, response);
      }
      finally {
        finishedServing();
      }
    }
  }

  private Navajo createTooBusyMessage() {
      try {
        return Dispatcher.generateErrorMessage(null, "Server too busy", 999,
                                               999, null);
      }
      catch (FatalException ex) {
        ex.printStackTrace(System.err);
        return null;
      }
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

    // Check if request can be served.
    if (!startServing()) {
     // Construct server too busy error message.
     response.setContentType("text/xml; charset=UTF-8");
     Navajo d = createTooBusyMessage();
     if (d != null) {
      try {
        if (useRecvCompression) {
          response.setContentType("text/xml; charset=UTF-8");
          response.setHeader("Content-Encoding", "gzip");
          java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(response.getOutputStream());
          d.write(gzipout);
          gzipout.close();
        } else {
          response.setContentType("text/xml; charset=UTF-8");
          OutputStream out = (OutputStream) response.getOutputStream();
          d.write(out);
          out.close();
        }
      }
      catch (NavajoException ex) {
        ex.printStackTrace(System.err);
      }
     }
     return;
    }

    Dispatcher dis = null;

    try {

      Navajo in = null;

      if (useRecvCompression) {
        java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
        in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(unzip));
      }
      else {
        in = NavajoFactory.getInstance().createNavajo(new BufferedInputStream(request.getInputStream()));
      }

      long stamp = System.currentTimeMillis();
      int pT = (int) (stamp - start);

      if (in == null) {
        logger.log(Priority.WARN,
                   "POSSIBLE SECURITY VIOLATION: INVALID REQUEST FROM " +
                   request.getRemoteHost() + "(" + request.getRemoteAddr() +
                   "); " + request.getRemoteUser());
        throw new ServletException("Invalid request.");
      }

      Header header = in.getHeader();
      if (header == null) {
        throw new ServletException("Empty Navajo header.");
      }

      // Create dispatcher object.
      dis = new Dispatcher(new java.net.URL(configurationPath), new com.dexels.navajo.server.FileInputStreamReader());
      dis.setServerId(request.getServerName() + request.getRequestURI());
      // Check for certificate.
      Object certObject = request.getAttribute( "javax.servlet.request.X509Certificate");

      // Call Dispatcher with parsed TML document as argument.
      Navajo outDoc = dis.handle(in, certObject, new ClientInfo(request.getRemoteAddr(), request.getRemoteHost(),
          recvEncoding, pT, useRecvCompression, useSendCompression, request.getContentLength(), created, threadCount));

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

    }
    catch (FatalException e) {
      logger.log(Priority.INFO,
                 "Received request from " + request.getRemoteAddr() +
                 ": invalid request");
      logger.log(Priority.FATAL, e.getMessage());
      throw new ServletException(e);
    }
    catch (NavajoException te) {
      logger.log(Priority.INFO,
                 "Received request from " + request.getRemoteAddr() +
                 "): invalid request");
      logger.log(Priority.ERROR, te.getMessage());
      throw new ServletException(te);
    }
    finally {
      dis = null;
      finishedServing();
    }
  }
}