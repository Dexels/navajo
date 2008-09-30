package com.dexels.navajo.server.listener.http;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
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

   /**
	 * 
	 */
	private static final long serialVersionUID = 7121511406958498528L;
	
	protected String configurationPath = "";
	protected String rootPath = null;

  public TmlHttpServlet() {}

  public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
  public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
  public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
  public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

  public static final String COMPRESS_GZIP = "gzip";
  public static final String COMPRESS_JZLIB = "jzlib";
  
  
 public static final String COMPRESS_NONE = "";
  
 public static final String DEFAULT_SERVER_XML = "config/server.xml";

 private static boolean streamingMode = true; 
 private static long logfileIndex = 0;
 private static long bytesWritten = 0;
 
 protected final DispatcherInterface initDispatcher() throws NavajoException {

	 if (configurationPath!=null) {
		 // Old SKOOL. Path provided, notify the dispatcher by passing a null DEFAULT_SERVER_XML
		 return DispatcherFactory.getInstance(configurationPath, null, new com.dexels.navajo.server.FileInputStreamReader());
	 } else {
		 return DispatcherFactory.getInstance(rootPath, DEFAULT_SERVER_XML, new com.dexels.navajo.server.FileInputStreamReader());
	 }

 }
 
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    configurationPath = config.getInitParameter("configuration");
    // Check whether defined bootstrap webservice is present.
    String bootstrapService = config.getInitParameter("bootstrap_service");
    String bootstrapUser = config.getInitParameter("bootstrap_user");
    String bootstrapPassword = config.getInitParameter("bootstrap_password");
    
    System.setProperty(DOC_IMPL,QDSAX);
    System.err.println("Configuration path: "+configurationPath);
   
    boolean verified = false;

    URL configUrl;
    InputStream is = null;
    try {
		configUrl = new URL(configurationPath);
	    is = configUrl.openStream();
	    verified = true;
	} catch (MalformedURLException e) {
		//e.printStackTrace(System.err);
	} catch (IOException e) {
	} finally {
		if(is!=null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    if( configurationPath==null || "".equals(configurationPath)|| !verified) {
    	rootPath = config.getServletContext().getRealPath("");
    }
    System.err.println("Resolved Configuration path: "+configurationPath);
    System.err.println("Resolved Root path: "+rootPath);

    // Startup Navajo instance.
    try {
    	DispatcherInterface d = initDispatcher();
    	Navajo n = NavajoFactory.getInstance().createNavajo();
    	if ( bootstrapService == null ) {
    		Header h = NavajoFactory.getInstance().createHeader(n, MaintainanceRequest.METHOD_NAVAJO_PING, "", "", -1);
    		n.addHeader(h);
    	} else {
    		Header h = NavajoFactory.getInstance().createHeader(n, bootstrapService, bootstrapUser, bootstrapPassword, -1);
    		n.addHeader(h);
    	}
    	d.handle(n);
    	System.err.println("NAVAJO INSTANCE " +  d.getNavajoConfig().getInstanceName() + " BOOTSTRAPPED BY " + n.getHeader().getRPCName());
    } catch (Exception e) {
    	e.printStackTrace(System.err);
    }
    
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

  protected final void dumHttp(HttpServletRequest request, long index, File dir) {
		// Dump stuff.
		if (request != null) {
			StringBuffer sb = new StringBuffer();

			sb.append("HTTP DUMP (" + request.getRemoteAddr() + "/"
					+ request.getRequestURI());
			Enumeration e = request.getHeaderNames();
			while (e.hasMoreElements()) {
				String headerName = (String) e.nextElement();
				sb.append(headerName + "=" + request.getHeader(headerName) + "\n");
			}
			try {

				if (dir != null) {
					FileWriter fw = new FileWriter(new File(dir, "httpdump-"
							+ index));
					fw.write(sb.toString());
					fw.close();
				} else {
					System.err.println(sb.toString());
				}
			} catch (IOException ioe) {
				ioe.printStackTrace(System.err);
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
    	username = "empty";
    	password = "";
      //logger.log(Priority.FATAL, "Empty service specified, request originating from " + request.getRemoteHost());
      //System.err.println("Empty service specified, request originating from " + request.getRemoteHost());
//      return;
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
    
    ServletOutputStream outputStream = response.getOutputStream();

    // PrintWriter out = response.getWriter();
 
    Navajo tbMessage = null;
    DispatcherInterface dis = null;

    try {
	   dis = initDispatcher();
    		  	  
      tbMessage = constructFromRequest(request);
      Header header = NavajoFactory.getInstance().createHeader(tbMessage,service, username, password,expirationInterval);
      tbMessage.addHeader(header);
      Navajo resultMessage = dis.handle(tbMessage);
      //System.err.println(resultMessage.toString());
      //resultMessage.write(out);
      String dataPath = request.getParameter("dataPath");
      if(dataPath!=null) {
    	  Property bin = resultMessage.getProperty(dataPath);
    	  if(bin==null ) {
        	  java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(outputStream,"UTF-8");
        	  response.setContentType("text/xml; charset=UTF-8");
        	  resultMessage.write(out);
        	  out.flush();
        	  out.close();
    	  } else {
    	     // Will throw cce when not a binary?
    		  if ( bin.getTypedValue() instanceof Binary ) {
    			  Binary b = (Binary) bin.getTypedValue();
    			  response.setContentType(b.guessContentType());
    			  copyResource(outputStream, b.getDataAsStream());
    		  } else {
    			 outputStream.write(bin.getValue().getBytes());
    		  }
    		  outputStream.flush();
    	  }
      } else {
    	  java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(outputStream,"UTF-8");
    	  response.setContentType("text/xml; charset=UTF-8");
    	  resultMessage.write(out);
    	  out.flush();
    	  out.close();
     }
      
      
    }
    catch (Exception ce) {
      ce.printStackTrace();
      System.err.println(ce.getMessage());
    }
    finally {
    	outputStream.close();
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

	  // Check for streamingmode toggle.
	  if ( request.getParameter("streaming") != null && request.getParameter("streaming").equals("no")) {
		  streamingMode = false;
		  PrintWriter pw = new PrintWriter(response.getWriter());
		  pw.write("Switched off streaming mode");
		  pw.close();
	  } else if ( request.getParameter("streaming") != null ) {
		  streamingMode = true;
		  PrintWriter pw = new PrintWriter(response.getWriter());
		  pw.write("Switched on streaming mode");
		  pw.close();
	  } else {
		  callDirect(request, response);
	  }
  }

  protected final void copyResource(OutputStream out, InputStream in){
	  BufferedInputStream bin = new BufferedInputStream(in);
	  BufferedOutputStream bout = new BufferedOutputStream(out);
	  byte[] buffer = new byte[1024];
	  int read = -1;
	  boolean ready = false;
	  while (!ready) {
		  try {
			  read = bin.read(buffer);
			  if ( read > -1 ) {
				  bout.write(buffer,0,read);
			  }
		  } catch (IOException e) {
		  }
		  if ( read <= -1) {
			  ready = true;
		  }
	  }
	  try {
		  bin.close();
		  bout.flush();
		  bout.close();
	  } catch (IOException e) {

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
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

	  Date created = new java.util.Date();
	  long start = created.getTime();

	  String sendEncoding = request.getHeader("Accept-Encoding");
	  String recvEncoding = request.getHeader("Content-Encoding");

	  DispatcherInterface dis = null;
	  BufferedReader r = null;
	  BufferedWriter out = null;
	  try {

		  Navajo in = null;
		  
		 
		  if (streamingMode) {
			  if ( sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {			 
				  r = new BufferedReader(new java.io.InputStreamReader(new ZInputStream(request.getInputStream())));
			  } else if ( sendEncoding != null && sendEncoding.equals(COMPRESS_GZIP)) {
				  r = new BufferedReader(new java.io.InputStreamReader(new java.util.zip.GZIPInputStream(request.getInputStream()), "UTF-8")); 
			  }
			  else {
				  r = new BufferedReader(request.getReader());
			  }
			  in = NavajoFactory.getInstance().createNavajo(r);
			  r.close();
			  r = null;
		  } else {
			  System.err.println("Warning: Using non-streaming mode for " + request.getRequestURI() + ", file written: " + logfileIndex + ", total size: " + bytesWritten);
			  InputStream is = request.getInputStream();
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
			  copyResource(bos, is);
			  is.close();
			  bos.close();
			  byte [] bytes = bos.toByteArray();
			  try {
				  if ( sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {			 
					  r = new BufferedReader(new java.io.InputStreamReader(new ZInputStream(new ByteArrayInputStream(bytes))));
				  } else if ( sendEncoding != null && sendEncoding.equals(COMPRESS_GZIP)) {
					  r = new BufferedReader(new java.io.InputStreamReader(new java.util.zip.GZIPInputStream(new ByteArrayInputStream(bytes)), "UTF-8")); 
				  }
				  else {
					  r = new BufferedReader(new java.io.InputStreamReader(new ByteArrayInputStream(bytes)));
				  }
				  in = NavajoFactory.getInstance().createNavajo(r);
				  if ( in == null ) {
					  throw new Exception("Invalid Navajo");
				  }
				  r.close();
				  r = null;
			  } catch (Throwable t) {
				  // Write request to file.
				  File f = new File(getServletContext().getRealPath("tmp"));
				  if ( !f.exists() ) {
					  f.mkdirs();
				  }
				  bytesWritten += bytes.length;
				  logfileIndex++;
				  FileOutputStream fos = new FileOutputStream(new File(f, "request-" + logfileIndex));
				  copyResource(fos, new ByteArrayInputStream(bytes));
				  fos.close();
				  PrintWriter fw = new PrintWriter(new FileWriter(new File(f, "exception-" + logfileIndex)));
				  t.printStackTrace(fw);
				  fw.flush();
				  fw.close();
				  dumHttp(request, logfileIndex, f);
				  throw new ServletException(t);
			  }
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

		  dis = initDispatcher();
		  
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
		 
		  if ( in != null && in.getHeader() != null && outDoc != null && outDoc.getHeader() != null && !Dispatcher.isSpecialwebservice(in.getHeader().getRPCName())) {
			  System.err.println("(" + dis.getApplicationId() + "): " + outDoc.getHeader().getHeaderAttribute("accessId") + ":" + in.getHeader().getRPCName() + "(" + in.getHeader().getRPCUser() + "):" + ( System.currentTimeMillis() - start ) + " ms. (st=" + 
					  ( outDoc.getHeader().getHeaderAttribute("serverTime") + ",rpt=" + outDoc.getHeader().getHeaderAttribute("requestParseTime") + ",at=" + outDoc.getHeader().getHeaderAttribute("authorisationTime") + ",pt=" + 
							  outDoc.getHeader().getHeaderAttribute("processingTime") + ",tc=" + outDoc.getHeader().getHeaderAttribute("threadCount") + ",cpu=" + outDoc.getHeader().getHeaderAttribute("cpuload") +  ")" + " (" + sendEncoding + "/" + recvEncoding + ")" ));
		  }
		  
		  out = null;
		  
	  }
	  catch (Throwable e) {
		  e.printStackTrace(System.err);
		  dumHttp(request, -1, null);
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