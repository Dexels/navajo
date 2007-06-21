package com.dexels.navajo.server.listener.http;

import java.io.*;
import java.io.InputStreamReader;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
//import com.sun.xml.internal.bind.v2.util.FatalAdapter;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

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

  public static final String COMPRESS_GZIP = "gzip";
  public static final String COMPRESS_JZLIB = "jzlib";
  public static final String COMPRESS_NONE = "";
  
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

	  Dispatcher dis = null;
	  BufferedReader r = null;
	  BufferedWriter out = null;
	  try {

		  Navajo in = null;
		  
		  if ( sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {			 
			  r = new BufferedReader(new InputStreamReader(new ZInputStream(request.getInputStream())));
		  } else if ( sendEncoding != null && sendEncoding.equals(COMPRESS_GZIP)) {
			 r = new BufferedReader(new InputStreamReader(new java.util.zip.GZIPInputStream(request.getInputStream()), "UTF-8")); 
		  }
		  else {
			  r = new BufferedReader(request.getReader());
		  }

		  in = NavajoFactory.getInstance().createNavajo(r);
		  r.close();
		  r = null;
		  
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
						  recvEncoding, pT, ( recvEncoding != null && ( recvEncoding.equals(COMPRESS_GZIP) || recvEncoding.equals(COMPRESS_JZLIB))), 
						  ( sendEncoding != null && ( sendEncoding.equals(COMPRESS_GZIP) || sendEncoding.equals(COMPRESS_JZLIB))), 
						  request.getContentLength(), created));
		  
		  response.setContentType("text/xml; charset=UTF-8");
		  
		  if ( recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {		
			  response.setHeader("Content-Encoding", COMPRESS_JZLIB);
			  out = new BufferedWriter(new OutputStreamWriter(new ZOutputStream(response.getOutputStream(), JZlib.Z_BEST_SPEED), "UTF-8"));
		  } else if ( recvEncoding != null && recvEncoding.equals(COMPRESS_GZIP)) {
			  response.setHeader("Content-Encoding", COMPRESS_GZIP);
			  out = new BufferedWriter(new OutputStreamWriter(new java.util.zip.GZIPOutputStream(response.getOutputStream()), "UTF-8"));
		  }
		  else {
			  out = new BufferedWriter(response.getWriter());
		  }
		  
		  outDoc.write(out);
		  out.flush();
		  out.close();
		  out = null;
		  
		  System.err.println(outDoc.getHeader().getAttribute("accessId") + ":" + in.getHeader().getRPCName() + "(" + in.getHeader().getRPCUser() + "):" + ( System.currentTimeMillis() - start ) + " ms. (st=" + 
				  ( outDoc.getHeader().getAttribute("serverTime") + ",rpt=" + outDoc.getHeader().getAttribute("requestParseTime") + ",at=" + outDoc.getHeader().getAttribute("authorisationTime") + ",pt=" + 
						  outDoc.getHeader().getAttribute("processingTime") + ",tc=" + outDoc.getHeader().getAttribute("threadCount") + ",cpu=" + outDoc.getHeader().getAttribute("cpuload") +  ")" + " (" + sendEncoding + "/" + recvEncoding + ")" ));
	  }
	  catch (Throwable e) {
		  e.printStackTrace(System.err);
		  dumHttp(request);
		  if ( e instanceof  FatalException ) {
			  FatalException fe = (FatalException) e;
			  if ( fe.getMessage().equals("500.13")) {
				  // Server too busy.
				  throw new ServletException("500.13");
			  }
		  }
		  throw new ServletException(e);
	  }
	  finally {
		  dis = null;
		  if (r!=null) {
			  try {
				  r.close();
			  } catch (Exception e) {
				  // NOT INTERESTED.
			  }
		  }
		  if (out != null) {
			  try {
				  out.close();
			  } catch (Exception e) {
				  // NOT INTERESTED.
			  }
		  }
	  }
  }
}