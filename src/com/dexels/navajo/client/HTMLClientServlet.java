package com.dexels.navajo.client;

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 */

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.dexels.navajo.client.NavajoHTMLClient;
import com.dexels.navajo.document.*;

class Identification {

    String username;
    String password;
    int id;
}


public class HTMLClientServlet extends HttpServlet {

    // Initialize global variables

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

    private void setNoCache(HttpServletRequest request, HttpServletResponse response) {// if (request.getProtocol().compareTo("HTTP/1.0") == 0) {
        // response.setHeader("Pragma", "no-cache");
        // } else if (request.getProtocol().compareTo("HTTP/1.1") == 0) {
        // response.setHeader("Cache-Control", "no-cache");
        // }
        // response.setDateHeader("Expires", 0);
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

        tbMessage = (Navajo) session.getAttribute("NAVAJO_MESSAGE");
        ident = (Identification) session.getAttribute("IDENT");

        Navajo resultDoc = null;

        if (request.getParameter("command") != null
                && request.getParameter("command").equals("navajo_logon_send")) {

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

                resultDoc = gc.doMethod("navajo_logon_send", "ANONYMOUS", "ANONYMOUS", tbMessage, navajoServer, false, "", "", -1, request,
                                        false, false, useCompression);

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
            response.sendRedirect(servletName + "?command=" + service);
            return;
        } else {
            try {
                tbMessage = NavajoFactory.getInstance().createNavajo();

                gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);

                resultDoc = gc.doMethod("navajo_logon", "ANONYMOUS", "ANONYMOUS", tbMessage, navajoServer, false, "", "", -1, request,
                                        false, false, useCompression);

                messages = resultDoc.getAllMessages();
                actions = resultDoc.getAllMethods();

                // transform TML message to HTML format
                result = gc.generateHTMLFromMessage(resultDoc, messages, actions, servletName, xslFile);
                out.println(result);

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

        ArrayList messages = null, actions = null;
        String result;
        String command;
        NavajoHTMLClient gc = null;
        Navajo tbMessage = null;
        Identification ident = null;
        String username = "";
        String password = "";

        // Retrieve Navajo Message
        HttpSession session = request.getSession(true);

        // Login check.
        ident = (Identification) session.getAttribute("IDENT");
        tbMessage = (Navajo) session.getAttribute("NAVAJO_MESSAGE");

        if ((ident == null) || (request.getParameter("command").equals("navajo_logon_send"))) {
            aanMelden(request, response);
            return;
        }

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

        if (tbMessage == null) {
                tbMessage = NavajoFactory.getInstance().createNavajo();
        } else {
            try {
                result = gc.readHTMLForm(tbMessage, request);
            } catch (NavajoException e) {

                throw new ServletException(e);
            }
        }

        Navajo resultDoc = null;
        if (request.getParameter("command") != null) {
            try {
                command = (String) request.getParameter("command");

                try {
                    resultDoc = gc.doMethod(command, ident.username, ident.password, tbMessage, navajoServer, false, "", "", -1, request,
                                            false, true, useCompression);

                } catch (com.dexels.navajo.client.ClientException ce) {
                    System.err.println(ce.getMessage());
                }
                messages = resultDoc.getAllMessages();
                actions = resultDoc.getAllMethods();

                tbMessage.appendDocBuffer(resultDoc.getMessageBuffer());

                // transform TML message to HTML format
                result = gc.generateHTMLFromMessage(resultDoc, messages, actions, servletName, xslFile);
                out.println(result);
                // put the whole TML message to html for debugging
                java.io.StringWriter text = new java.io.StringWriter();

                resultDoc.write(text);

                out.println("Copyright(c) 2002 Dexels BV (Use view source to view the TML message)" + text);

            } catch (NavajoException e) {

                out.println("An error occured: " + e);
            }

        } else {
            afMelden(request, response);
        }

        // Store Navajo Messsage
        session.setAttribute("NAVAJO_MESSAGE", tbMessage);
    }
}
