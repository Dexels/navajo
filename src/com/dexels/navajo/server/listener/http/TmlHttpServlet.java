package com.dexels.navajo.server.listener.http;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.dexels.navajo.document.*;
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
 * This servlet handles HTTP POST requests.
 * The HTTP POST body is assumed to contain a TML document.
 * The TML document is processed by the dispatcher the resulting TML document is send back as a reply.
 *
 */

public class TmlHttpServlet extends HttpServlet {

  protected String configurationPath = "";

  public TmlHttpServlet() {}

  public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
  public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
  public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
  public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    configurationPath = config.getInitParameter("configuration");
    System.setProperty(DOC_IMPL,QDSAX);

  }

  public void destroy() {
    System.err.println("In TmlHttpServlet destroy()");
    // Kill Dispatcher.
    Dispatcher.killMe();
  }

  protected void finalize() {
    System.err.println("In TmlHttpServlet finalize(), thread = " + Thread.currentThread().hashCode());
    //logger.log(Priority.INFO, "In TmlHttpServlet finalize()");
  }

  private void dumHttp(HttpServletRequest request) {
	  // Dump stuff.
	  if ( request != null ) {
		  System.err.println("HTTP DUMP (" + request.getRemoteAddr() + "/" + request.getRequestURI() );
		  Enumeration e = request.getHeaderNames();
		  while ( e.hasMoreElements() ) {
			  String headerName = (String) e.nextElement();
			  System.err.println(headerName + "=" + request.getHeader(headerName));
		  }  
	  } else {
		  System.err.println("EMPTY REQUEST OBJECT!!");
	  }
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
      
      //System.err.println("Empty service specified, request originating from " + request.getRemoteHost());
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
      Enumeration enm = request.getHeaderNames();
      while (enm.hasMoreElements()) {
        String key = (String) enm.nextElement();
        String header = request.getHeader(key);
        System.err.println(">>" + key + "=" + header);
      }
      return;
    }

    if (username == null) {
      //logger.log(Priority.FATAL, "Empty service specified, request originating from " + request.getRemoteHost());
      //System.err.println("Empty service specified, request originating from " + request.getRemoteHost());
      return;
    }

    if ( (type == null) || (type.equals(""))) {
      type = "xml";

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
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

	  Date created = new java.util.Date();
	  long start = created.getTime();

	  String sendEncoding = request.getHeader("Accept-Encoding");
	  String recvEncoding = request.getHeader("Content-Encoding");
	  boolean useSendCompression = ( (sendEncoding != null) && (sendEncoding.indexOf("zip") != -1));
	  boolean useRecvCompression = ( (recvEncoding != null) && (recvEncoding.indexOf("zip") != -1));

	  Dispatcher dis = null;
	  BufferedInputStream is = null;
	  Reader r = null;
	  try {

		  Navajo in = null;

		  if (useRecvCompression) {
			  java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(request.getInputStream());
			  is = new BufferedInputStream(unzip);
			  in = NavajoFactory.getInstance().createNavajo(is);
		  }
		  else {
			  r = request.getReader();
			  in = NavajoFactory.getInstance().createNavajo(r);
			  r.close();
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
				  new ClientInfo(request.getRemoteAddr(), "unknown",
						  recvEncoding, pT, useRecvCompression, useSendCompression, request.getContentLength(), created));

		  if (useSendCompression) {
			  response.setContentType("text/xml; charset=UTF-8");
			  response.setHeader("Content-Encoding", "gzip");
			  java.util.zip.GZIPOutputStream gzipout = new java.util.zip.GZIPOutputStream(response.getOutputStream());
			  outDoc.write(gzipout);
			  gzipout.close();
		  }
		  else {
			  response.setContentType("text/xml; charset=UTF-8");
			  Writer out = (Writer) response.getWriter();
			  outDoc.write(out);
			  out.close();
		  }
	  }
	  catch (Throwable e) {
		  dumHttp(request);
		  throw new ServletException(e);
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
		  if (r!=null) {
			  r.close();
		  }
	  }
  }
}