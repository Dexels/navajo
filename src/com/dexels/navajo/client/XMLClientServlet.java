package com.dexels.navajo.client;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

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

public class XMLClientServlet extends HttpServlet {

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

        Util.debugLog("XMLClientServlet: init() called");
        servletName = config.getServletName();
        Util.debugLog("this servlet is named " + servletName);
        super.init(config);
        navajoServer = config.getInitParameter("navajo_server");
        Util.debugLog("navajo_server: " + navajoServer);
        rpcUser = config.getInitParameter("user");
        rpcPwd = config.getInitParameter("password");
        secure = config.getInitParameter("enable_https").equals("yes");
        keystore = config.getInitParameter("keystore");
        passphrase = config.getInitParameter("passphrase");

        if (config.getInitParameter("use_compression") != null)
          useCompression = config.getInitParameter("use_compression").equals("true");
        xslFile = config.getInitParameter("xslFile");
    }

    // Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private Navajo constructFromRequest(HttpServletRequest request) throws NavajoException {

        Navajo result = new Navajo();

        Enumeration all = request.getParameterNames();
        // Construct TML document from request parameters.
        while (all.hasMoreElements()) {

            String parameter = all.nextElement().toString();

            if (parameter.indexOf("/") != -1) {
                String value = request.getParameter(parameter);

                Message msg = com.dexels.navajo.mapping.XmlMapperInterpreter.getMessageObject(parameter, null,
                                                                          false, result, false);
                String propName = com.dexels.navajo.mapping.XmlMapperInterpreter.getStrippedPropertyName(parameter);
                Property prop = null;

                if (propName.indexOf(":") == -1) {
                    prop = Property.create(result, propName, Property.STRING_PROPERTY, value, 0, "", Property.DIR_IN);
                    msg.addProperty(prop);
                } else {
                    StringTokenizer selProp = new StringTokenizer(propName, ":");
                    String propertyName = selProp.nextToken();
                    String selectionField = selProp.nextToken();
                    Selection sel = Selection.create(result, value, value, true);

                    prop = msg.getProperty(propertyName);
                    if (prop == null) {
                        prop = Property.create(result, propertyName, "1", "", Property.DIR_IN);
                        msg.addProperty(prop);
                    }
                    prop.addSelection(sel);
                }
            }
        }

        return result;

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList messages = null, actions = null;
        String result;
        String command;
        NavajoHTMLClient gc = null;
        Navajo tbMessage = null;
        Identification ident = null;
        String username = "";
        String password = "";
        boolean setter = false;

        Util.debugLog("IN MAIN(), command: " + request.getParameter("command"));


        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        gc = new NavajoHTMLClient(NavajoClient.HTTP_PROTOCOL);

        try {
                setter = true;
                tbMessage = constructFromRequest(request);
                System.out.println(tbMessage.toString());
        } catch (NavajoException e) {
                e.printStackTrace();
                throw new ServletException(e);
      }


        if (request.getParameter("command") != null) {
            try {
                command = (String) request.getParameter("command");

                try {
                    gc.doMethod(command, username, password, tbMessage, navajoServer, secure,
                                keystore, passphrase, -1, request, false);
                } catch (com.dexels.navajo.client.ClientException ce) {
                    System.out.println(ce.getMessage());
                }
                messages = tbMessage.getCurrentMessages();
                actions = tbMessage.getCurrentActions();
                // transform TML message to XML format using XSLT stylesheet "xslFile".
                result = gc.generateHTMLFromMessage3(tbMessage, messages, actions, servletName, setter, xslFile);
                out.println(result);


            } catch (NavajoException e) {
                e.printStackTrace();
                out.println("An error occured: " + e);
            }

        } else {
          out.println("command expected");
        }

    }
}