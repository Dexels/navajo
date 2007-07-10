package com.dexels.navajo.client;

/**
 * Title:        Navajo<p>useC
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.dexels.navajo.client.NavajoHTMLClient;
import com.dexels.navajo.document.*;

class Identification {

    String username;
    String password;
    //int id;
}

  /**
   * Servlet that provides a Web interface for calling webservices
   */
public class HTMLClientServlet extends HttpServlet {

    // Initialize global variables

    public static final String RESOURCE_TML2HTML_XSL = "resource/tml2html.xsl";
	/**
	 * 
	 */
	private static final long serialVersionUID = -6572470344508932724L;
	String navajoServer = "";
    String rpcUser = "";
    String rpcPwd = "";
    boolean secure = false;
    String keystore = "";
    String passphrase = "";
    String xslFile = "";
    String servletName = "";
    // Translation table
    Hashtable translation = new Hashtable();
    boolean useCompression = false;

    public void init(ServletConfig config) throws ServletException {

        servletName = config.getServletName();

        super.init(config);

        navajoServer = config.getInitParameter("navajo_server");

        rpcUser = config.getInitParameter("user");
        rpcPwd = config.getInitParameter("password");
        secure = config.getInitParameter("enable_https").equals("yes");
        keystore = config.getInitParameter("keystore");
        passphrase = config.getInitParameter("passphrase");
        if (config.getInitParameter("use_compression") != null)
          useCompression = config.getInitParameter("use_compression").equals("true");
        xslFile = config.getInitParameter("xslFile");

   
    }

    private void setNoCache(HttpServletRequest request, HttpServletResponse response) {


      if (request.getProtocol().compareTo("HTTP/1.0") == 0) {
        response.setHeader("Pragma", "no-cache");
      }
      else if (request.getProtocol().compareTo("HTTP/1.1") == 0) {
        response.setHeader("Cache-Control", "no-cache");
      }
        //System.err.println("PROTOCOL: " + request.getProtocol());

      response.setDateHeader("Expires", 0);

    }

    public void writeMessage(Navajo tb, String message) throws IOException {
        if (tb != null) {// Util.debugLog("Navajo Message: " + message + ":");
            // Util.debugLog("-------------------------------------------------");
            // tb.getMessageBuffer().write(System.out);
            // Util.debugLog("-------------------------------------------------");
        }
    }

    private void aanMelden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList messages = null, actions = null;
//        String result;
        String command;
        NavajoHTMLClient gc = null;
        Navajo tbMessage = null;
        Identification ident = null;
        String username = "";
        String password = "";

        //PrintWriter out = response.getWriter();

        String sendEncoding = request.getHeader("Accept-Encoding");
        boolean useGzipEncoding = ((sendEncoding != null) && (sendEncoding.indexOf("gzip") != -1));
        //System.err.println("in aanMelden(),  USEGZIPENCODING = " + useGzipEncoding + "command = " + request.getParameter("command"));

        setNoCache(request, response);
        response.setContentType("text/html");
        Writer out = null;
        if (useGzipEncoding) {
            response.setHeader("Content-Encoding", "gzip");
            
            OutputStream os = new java.util.zip.GZIPOutputStream(response.getOutputStream());
            out = new OutputStreamWriter(os);

        } else {
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            out = new OutputStreamWriter(os);
            response.setContentType("text/html");
        }
//        java.util.zip.GZIPOutputStream gzipout = null;
//        java.io.Writer writer = null;

//        if (useGzipEncoding) {
//        } else {
//          writer = response.getWriter();
//        }


        // Retrieve Navajo Message
        HttpSession session = request.getSession(true);

        tbMessage = (Navajo) session.getAttribute("NAVAJO_MESSAGE");
        ident = (Identification) session.getAttribute("IDENT");

        Navajo resultDoc = null;

        if (request.getParameter("command") != null && request.getParameter("command").equals("navajo_logon_send") && tbMessage != null) {

            // Read form.
            username = request.getParameter("identification" + Navajo.MESSAGE_SEPARATOR + "username");
            password = request.getParameter("identification" + Navajo.MESSAGE_SEPARATOR + "password");
            boolean freefield = false;
            String service = request.getParameter("services" + Navajo.MESSAGE_SEPARATOR + "all_services");

            if (service == null) {
                freefield = true;
                service = request.getParameter("services" + Navajo.MESSAGE_SEPARATOR + "service");
            }

            try {
                tbMessage.getMessage("identification").getProperty("username").setValue(username);
                tbMessage.getMessage("identification").getProperty("password").setValue(password);
                if (!freefield)
                    tbMessage.getMessage("services").getProperty("all_services").getSelection(service).setSelected(true);
                else
                    tbMessage.getMessage("services").getProperty("service").setValue(service);

                gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);
                gc.setLocaleCode("nl");
                
                //resultDoc = gc.doMethod("navajo_logon_send", "ANONYMOUS", "ANONYMOUS", tbMessage, navajoServer, false, "", "", -1, request,
                //                        false, false, useCompression);

                resultDoc = gc.doSimpleSend(tbMessage, navajoServer, "navajo_logon_send", "ANONYMOUS", "ANONYMOUS", -1, useCompression, false);
                //resultDoc.write(System.err);                
                Message error = resultDoc.getMessage("error");

                if (error != null) {
//                    int level = Integer.parseInt(error.getProperty("level").getValue());
//                    int code = Integer.parseInt(error.getProperty("code").getValue());
//                    String messageString = error.getProperty("message").getValue();

                    out.write("NOT AUTHORISED");
                    out.close();
                    return;
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
            ident = new Identification();
            ident.username = username;
            ident.password = password;
            session.setAttribute("IDENT", ident);
            //System.err.println("REDIRECTING TO COMMAND =" + service);
            response.sendRedirect(servletName + "?command=" + service);
            return;
        } else {
            try {
                tbMessage = NavajoFactory.getInstance().createNavajo();
                
                gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);
                gc.setLocaleCode("nl");
                
                //resultDoc = gc.doMethod("navajo_logon", "ANONYMOUS", "ANONYMOUS", tbMessage, navajoServer, false, "", "", -1, request,
                //                        false, false, useCompression);

                //System.err.println("BEFORE CALLING navajo_logon");

                resultDoc = gc.doSimpleSend(tbMessage, navajoServer, "navajo_logon", "ANONYMOUS", "ANONYMOUS", -1, useCompression, false);
 
                //System.err.println("AFTER CALLING navajo_logon");
                messages = resultDoc.getAllMessages();
                actions = resultDoc.getAllMethods();

                // transform TML message to HTML format
                
//                if (useGzipEncoding) {
                InputStream xslStream = null;
                if (xslFile==null || !new File(xslFile).exists()) {
					xslFile = getServletContext().getRealPath(RESOURCE_TML2HTML_XSL);
				}
                
                    gc.generateHTMLFromMessage(resultDoc, messages, actions, servletName, xslFile,out);
                    out.flush();
                    out.close();
//                } else {
//                  
//                    writer.write(result);
//                  writer.close();
//                }
//
                session.setAttribute("NAVAJO_MESSAGE", resultDoc);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            return;
        }
    }

    private void afMelden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        session.invalidate();

        response.sendRedirect(servletName + "?command=navajo_logon");
    }


    // Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	if(navajoServer==null) {
    		resolveNavajoServer(request);
    	}
        ArrayList messages = null, actions = null;
        String result;
        String command;
        NavajoHTMLClient gc = null;
        Navajo tbMessage = null;
        Identification ident = null;
        String username = "";
        String password = "";

        boolean useGzipEncoding = false;

        // Determine whether client's browser accepts gzip encoding
        String sendEncoding = request.getHeader("Accept-Encoding");
        useGzipEncoding = ((sendEncoding != null) && (sendEncoding.indexOf("gzip") != -1));
        //System.err.println("USEGZIPENCODING = " + useGzipEncoding);

        // Retrieve Navajo Message
        HttpSession session = request.getSession(true);

        // Login check.
        ident = (Identification) session.getAttribute("IDENT");
        tbMessage = (Navajo) session.getAttribute("NAVAJO_MESSAGE");
        //tbMessage = NavajoFactory.getInstance().createNavajo();
        if ((ident == null) && request.getParameter("command") == null) {
            afMelden(request, response);
            return;
        }
        if ((ident == null) || request.getParameter("command") == null ||(request.getParameter("command").equals("navajo_logon_send"))) {
            aanMelden(request, response);
            return;
        }



        if (request.getParameter("command").equals("afmelden")) {
            afMelden(request, response);
            return;
        }

        //PrintWriter out = response.getWriter();

        setNoCache(request, response);
        response.setContentType("text/html");

        if (useGzipEncoding) {
          response.setHeader("Content-Encoding", "gzip");
        }

//        java.util.zip.GZIPOutputStream gzipout = null;
//        java.io.PrintWriter writer = null;
        Writer out = null;
        
        if (useGzipEncoding) {
            OutputStream outs = new java.util.zip.GZIPOutputStream(response.getOutputStream());
            out = new OutputStreamWriter(outs);
        } else {
            OutputStream outs = new BufferedOutputStream(response.getOutputStream());
            out = new OutputStreamWriter(outs);
        }
            //outDoc.write(gzipout);

        gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);
        gc.setLocaleCode("nl");
         
        if (tbMessage == null) {
                tbMessage = NavajoFactory.getInstance().createNavajo();
        } else {
            try {
                gc.readHTMLForm(tbMessage, request);
                //System.err.println("READ HTML FORM: " + result);
            } catch (NavajoException e) {
                throw new ServletException(e);
            }
        }
//        if (tbMessage!=null) {
//            out.write("out message...");
//            try {
//            tbMessage.write(out);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//            out.write("end of out message...");
//        }

        Navajo resultDoc = null;
        if (request.getParameter("command") != null) {
            try {
                command = (String) request.getParameter("command");

                try {
                    //resultDoc = gc.doMethod(command, ident.username, ident.password, tbMessage, navajoServer, false, "", "", -1, request,
                    //                        false, true, useCompression);
                    //System.err.println("ABOUT TO REQUEST FOR WEBSERVICE: " + command);
                    //tbMessage.write(System.err);
             	
                	
                    resultDoc = gc.doSimpleSend(tbMessage, navajoServer, command, ident.username, ident.password, -1, useCompression, false);

                } catch (com.dexels.navajo.client.ClientException ce) {
                    System.err.println(ce.getMessage());
                }
                if ( resultDoc == null ) {
                	return;
                }
                
                messages = resultDoc.getAllMessages();
                actions = resultDoc.getAllMethods();

                //
                //tbMessage.appendDocBuffer(resultDoc.getMessageBuffer());
                tbMessage = resultDoc;
                
                // transform TML message to HTML format
                gc.generateHTMLFromMessage(resultDoc, messages, actions, servletName, xslFile,out);
//                if (useGzipEncoding) gzipout.write(result.getBytes()); else writer.write(result);
                // put the whole TML message to html for debugging
//                java.io.StringWriter text = new java.io.StringWriter();
                String refreshJavaScriptCode =
                    "<input type='hidden' value='N' name='Refresh'>\n" +
                    "<script language='Javascript'>\n" +
                    "if (document.all(\"Refresh\").value = \"N\")\n" +
                    "{\n" +
                    "document.all(\"Refresh\").value = \"Y\";\n" +
                    "history.go(0);\n" +
                    "}\n" +
                    "</script>";

                out.write("Copyright(c) 2002-2007 Dexels BV (Use view source to view the XTML message)");
//                resultDoc.write(text);
//                session.setAttribute("NAVAJO_MESSAGE",tbMessage);
                

            } catch (NavajoException e) {
                  out.write(("An error occured: " + e));
            }

        } else {
            afMelden(request, response);
        }
        
        
        // Store Navajo Messsage
        session.setAttribute("NAVAJO_MESSAGE", tbMessage);
        out.flush();
        out.close();
    }

	private void resolveNavajoServer(HttpServletRequest request) {
		navajoServer =  request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/Postman";

		System.err.println("requesT: "+navajoServer);
	}
    
//    public static void main(String[] args) {
//        
//    }
}
