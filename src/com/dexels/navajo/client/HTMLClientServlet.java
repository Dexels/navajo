package com.dexels.navajo.client;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.transform.stream.StreamResult;

import com.dexels.navajo.client.NavajoHTMLClient;
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;
import com.dexels.navajo.util.*;

class Identification {

  String username;
  String password;
  int id;
}

public class HTMLClientServlet extends HttpServlet {

  //Initialize global variables
  ResourceBundle koopsomProperties = null;
  String navajoServer = "";
  String rpcUser = "";
  String rpcPwd = "";
  boolean secure = false;
  String keystore = "";
  String passphrase = "";
  String xslFile = "";
  // Translation table
  Hashtable translation = new Hashtable();

  public void init(ServletConfig config) throws ServletException {

    Util.debugLog("HTMLClient: init() called");
    super.init(config);
    koopsomProperties = ResourceBundle.getBundle("htmlclient");
    navajoServer = koopsomProperties.getString("navajo_server");
    Util.debugLog("navajo_server: " + navajoServer);
    rpcUser = koopsomProperties.getString("user");
    rpcPwd = koopsomProperties.getString("password");
    secure = koopsomProperties.getString("enable_https").equals("yes");
    keystore = koopsomProperties.getString("keystore");
    passphrase = koopsomProperties.getString("passphrase");
    xslFile = koopsomProperties.getString("xslFile");

    if (secure)
      Util.debugLog("NavajoClient: USING SECURE CONNECTION WITH CLIENT AUTHENTICATION");

  }

  private void setNoCache(HttpServletRequest request, HttpServletResponse response) {

    //if (request.getProtocol().compareTo("HTTP/1.0") == 0) {
    //  response.setHeader("Pragma", "no-cache");
    //} else if (request.getProtocol().compareTo("HTTP/1.1") == 0) {
    //  response.setHeader("Cache-Control", "no-cache");
    //}
    //response.setDateHeader("Expires", 0);
 }

  public void writeMessage(Navajo tb, String message) throws IOException {
    if (tb != null) {
       //Util.debugLog("Navajo Message: " + message + ":");
       //Util.debugLog("-------------------------------------------------");
       //tb.getMessageBuffer().write(System.out);
       //Util.debugLog("-------------------------------------------------");
    }
  }

  private void aanMelden(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
    ArrayList messages = null, actions = null;
    String result;
    String command;
    NavajoHTMLClient gc = null;
    Navajo tbMessage = null;
    Identification ident = null;
    String username = "";
    String password = "";

    PrintWriter out = response.getWriter();
    setNoCache(request, response);
    response.setContentType("text/html");

    // Retrieve Navajo Message
    HttpSession session = request.getSession(true);
    Util.debugLog("Session is : " + session);

    tbMessage = (Navajo) session.getAttribute("NAVAJO_MESSAGE");
    ident = (Identification) session.getAttribute("IDENT");

    if (request.getParameter("command") != null && request.getParameter("command").equals("navajo_logon_send")) {

      // Read form.
      username = request.getParameter("identification"+Navajo.MESSAGE_SEPARATOR+"username");
      password = request.getParameter("identification"+Navajo.MESSAGE_SEPARATOR+"password");
      boolean freefield = false;
      String service = request.getParameter("services"+Navajo.MESSAGE_SEPARATOR+"all_services");
      if (service == null)  {
        freefield = true;
        service = request.getParameter("services"+Navajo.MESSAGE_SEPARATOR+"service");
      }

      try {
        tbMessage.getMessage("identification").getProperty("username").setValue(username);
        tbMessage.getMessage("identification").getProperty("password").setValue(password);
        if (!freefield)
          tbMessage.getMessage("services").getProperty("all_services").getSelection(service).setSelected(true);
        else
          tbMessage.getMessage("services").getProperty("service").setValue(service);

        gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);
        gc.doMethod("navajo_logon_send", "ANONYMOUS", "ANONYMOUS", tbMessage, navajoServer, secure, keystore, passphrase, request);

        Message error = tbMessage.getMessage("error");
        if (error != null) {
          int level = Integer.parseInt(error.getProperty("level").getValue());
          int code = Integer.parseInt(error.getProperty("code").getValue());
          String messageString = error.getProperty("message").getValue();
          out.println("NOT AUTHORISED");
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
      response.sendRedirect("NavajoClient?command="+service);
      return;
    } else {
      try {
        tbMessage = new Navajo();

        gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);
        gc.doMethod("navajo_logon", "ANONYMOUS", "ANONYMOUS", tbMessage, navajoServer, secure, keystore, passphrase, request);

        messages = tbMessage.getCurrentMessages();
        actions = tbMessage.getCurrentActions();

        // transform TML message to HTML format
        result = gc.generateHTMLFromMessage3(tbMessage, messages, actions, "NavajoClient", false, xslFile);
        out.println(result);

        session.setAttribute("NAVAJO_MESSAGE", tbMessage);
      } catch (Exception e) {
        throw new ServletException(e);
      }
      return;
    }
  }

  private void afMelden(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException{
    HttpSession session = request.getSession(true);
    session.invalidate();

    response.sendRedirect("NavajoClient?command=navajo_logon");
  }

  private Navajo constructFromRequest(HttpServletRequest request) throws NavajoException  {

     Navajo result = new Navajo();

     Enumeration all = request.getParameterNames();
     while (all.hasMoreElements()) {
        String parameter = all.nextElement().toString();
        if (parameter.indexOf("/") != -1) {
            String value = request.getParameter(parameter);
            System.out.println("property = " + parameter + ", value = " + value);
            Message msg = com.dexels.navajo.mapping.XmlMapperInterpreter.getMessageObject(parameter, null, false, result);
            String propName = com.dexels.navajo.mapping.XmlMapperInterpreter.getStrippedPropertyName(parameter);
            Property prop = Property.create(result, propName, Property.STRING_PROPERTY, value, 0, "", Property.DIR_IN);
            msg.addProperty(prop);
        }
     }

     System.out.println("Constructed TML from URL request:");
     System.out.println(result.toString());
     return result;
  }

  private void callDirect(HttpServletRequest request, HttpServletResponse response, String username, String password)
              throws ServletException, IOException {

      String service = request.getParameter("service");

      PrintWriter out = response.getWriter();
      setNoCache(request, response);
      response.setContentType("text/xml");

      NavajoHTMLClient gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);

      Navajo tbMessage = null;
      try {
            tbMessage =constructFromRequest(request);

            Navajo resultMessage = gc.doSimpleSend(tbMessage, navajoServer, service, username, password);

            out.println(resultMessage.toString());
       } catch (Exception ce) {
            System.err.println(ce.getMessage());
      }

  }

   //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
    doPost(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {

      ArrayList messages = null, actions = null;
      String result;
      String command;
      NavajoHTMLClient gc = null;
      Navajo tbMessage = null;
      Identification ident = null;
      String username = "";
      String password = "";
      boolean setter = false;

      username = request.getParameter("username");
      password = request.getParameter("password");
      if ((username != null) && (password != null) && !username.equals("") && !password.equals("")) {
        // Direct call to webservice!
        callDirect(request, response, username, password);
        return;
      } else {
        username = "";
        password = "";
      }
      // Retrieve Navajo Message
      HttpSession session = request.getSession(true);
      Util.debugLog("Session is : " + session);

      // Login check.
      tbMessage = (Navajo) session.getAttribute("NAVAJO_MESSAGE");
      ident = (Identification) session.getAttribute("IDENT");

      Util.debugLog("IDENT: " + ident);

      if (ident == null) {
        aanMelden(request, response);
        return;
      }

      Util.debugLog("IN MAIN(), command: " + request.getParameter("command"));

      if (request.getParameter("command") == null) {
        afMelden(request, response);
        return;
      }

      if (request.getParameter("command").equals("afmelden")) {
        afMelden(request, response);
        return;
      }

      PrintWriter out = response.getWriter();
      setNoCache(request, response);
      response.setContentType("text/html");

      gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);

      if (tbMessage == null){
        try{
            tbMessage = new Navajo();
        } catch (NavajoException tbe){
         Util.debugLog(tbe.getMessage());
        }
      }else {
        try {
          setter=true;
          result = gc.readHTMLForm(tbMessage, request);
        } catch (NavajoException e) {
          Util.debugLog(e.toString());
          throw new ServletException(e);
        }
      }

      if (request.getParameter("command") != null) {
        try {
          command = (String) request.getParameter("command");

          try {
            gc.doMethod(command, ident.username, ident.password, tbMessage, navajoServer, secure, keystore, passphrase, request);
          } catch (com.dexels.navajo.client.ClientException ce) {
            System.err.println(ce.getMessage());
          }
          messages = tbMessage.getCurrentMessages();
          actions = tbMessage.getCurrentActions();

          // transform TML message to HTML format
          result = gc.generateHTMLFromMessage3(tbMessage, messages, actions, "NavajoClient", setter, xslFile);
          out.println(result);
          //put the whole TML message to html for debugging
          java.io.StringWriter text = new java.io.StringWriter();

          XMLDocumentUtils.toXML( tbMessage.getMessageBuffer(), null, null, new StreamResult( text ));

          out.println("Copyright(c) 2001 Dexels BV (Use view source to view the TML message)" + text);


        } catch (NavajoException e) {
         Util.debugLog(e.toString());
         out.println("An error occured: " + e);
        }

      } else {
        afMelden(request, response);
      }

      // Store Navajo Messsage
      session.setAttribute("NAVAJO_MESSAGE", tbMessage);
  }
}
