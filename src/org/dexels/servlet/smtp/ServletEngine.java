package org.dexels.servlet.smtp;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.xml.transform.stream.*;

import com.dexels.navajo.xml.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

import com.dexels.navajo.document.Navajo;


/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class ServletEngine extends Thread {

    private Hashtable servlets = new Hashtable();
    private HashSet loaded = new HashSet();
    private static Hashtable servletClasses = new Hashtable();
    private Hashtable contextParameters = new Hashtable();
    private String myServletName;
    private Configuration myConfiguration;
    private int poolInterval = 10000;
    private SmtpServletContext myContext;

    private static final String SERVER_INFO = "Mail Servlet Engine 0.1 (Copyright 2002 Dexels)";

    public ServletEngine(String servletName, Configuration configuration, SmtpServletContext context) {
        this.myServletName = servletName;
        this.myConfiguration = configuration;
        this.myContext = context;
    }

    private String getElementValue(Node n, String defaultValue) {
        String value = defaultValue;

        if (n != null) {
            Node child = n.getLastChild();

            if (child != null)
                value = child.getNodeValue();
            // System.out.println(n.getNodeName() + ": value = " + value);
        }

        return value;
    }

    private Configuration readConfiguration(Node n) {
        return readConfiguration(n, null);
    }

    private Configuration readConfiguration(Node n, Configuration defaults) {

        Configuration config = new Configuration();

        config.host = getElementValue(Utils.findNode(n, "host"), (defaults == null) ? "" : defaults.host);
        config.username = getElementValue(Utils.findNode(n, "username"), (defaults == null) ? "" : defaults.username);
        config.password = getElementValue(Utils.findNode(n, "password"), (defaults == null) ? "" : defaults.password);
        config.folder = getElementValue(Utils.findNode(n, "folder"), (defaults == null) ? "" : defaults.folder);
        config.sender = getElementValue(Utils.findNode(n, "sender"), (defaults == null) ? "" : defaults.sender);
        config.subject = getElementValue(Utils.findNode(n, "subject"), (defaults == null) ? "" : defaults.subject);
        config.recipient = getElementValue(Utils.findNode(n, "recipient"), (defaults == null) ? "" : defaults.recipient);
        config.identification = getElementValue(Utils.findNode(n, "identification"), (defaults == null) ? "" : defaults.identification);
        config.deleteMail = getElementValue(Utils.findNode(n, "delete-mail"), (defaults == null) ? "" : (defaults.deleteMail) ? "yes" : "no").equals("yes");
        return config;
    }

    private ServletContext readConfiguration() {

        SmtpServletContext context = new SmtpServletContext();

        try {
            Document d = null;
            FileInputStream input = new FileInputStream(new File("mail.xml"));

            d = XMLDocumentUtils.createDocument(input, false);

            d.getDocumentElement().normalize();
            Node body = d.getFirstChild();

            // Read pool-interval.
            Node poolIntervalNode = Utils.findNode(body, "pool-interval");

            if (poolIntervalNode != null)
                this.poolInterval = Integer.parseInt(poolIntervalNode.getFirstChild().getNodeValue());
            // System.out.println("pool interval = " + poolInterval);

            // Read defaults.
            Node defaultNode = Utils.findNode(body, "defaults");
            Configuration defaults = readConfiguration(defaultNode);

            // Read servlets.
            NodeList list = body.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);

                // System.out.println(node);
                if (node.getNodeName().equals("servlet")) {
                    Configuration configuration = readConfiguration(node, defaults);
                    Node c1 = Utils.findNode(node, "servlet-name");
                    Node c2 = Utils.findNode(node, "servlet-class");
                    String name = c1.getFirstChild().getNodeValue();
                    String className = c2.getFirstChild().getNodeValue();

                    System.out.println(name + ": " + className);
                    configuration.className = className;
                    servlets.put(name, configuration);
                }
            }

            // Read context parameters.
            list = body.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);

                if (node.getNodeName().equals("context-param")) {
                    Node c1 = Utils.findNode(node, "param-name");
                    Node c2 = Utils.findNode(node, "param-value");
                    String name = c1.getFirstChild().getNodeValue();
                    String value = c2.getFirstChild().getNodeValue();

                    contextParameters.put(name, value);
                }
            }
            context.contextParameters = contextParameters;

        } catch (com.dexels.navajo.document.NavajoException tbe) {
            tbe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        return context;
    }

    public synchronized SmtpServlet preload(String servletName, Configuration configuration) throws Exception {
        System.out.println("preloading servlet " + servletName);
        Class c = Class.forName(configuration.className);
        SmtpServlet servlet = (SmtpServlet) c.newInstance();

        servlet.name = servletName;
        SmtpServletConfig config = new SmtpServletConfig();

        config.name = servletName;
        config.host = configuration.host;
        config.username = configuration.username;
        config.password = configuration.password;
        config.sender = configuration.sender;
        config.identification = configuration.identification;
        config.recipient = configuration.recipient;
        servlet.init(config);
        servletClasses.put(servletName, servlet);
        loaded.add(servletName);
        return servlet;
    }

    public void run() {
        try {
            service(this.myServletName, this.myConfiguration, this.myContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service(String servletName, Configuration configuration, SmtpServletContext context) throws Exception {

        Session session = null;
        Properties properties = System.getProperties();

        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", configuration.host);
        session = Session.getInstance(properties, null);
        SmtpListener listener = new SmtpListener(session, configuration);

        while (true) {
            // System.out.println("Servicing servlet " + servletName);

            SmtpServlet servlet = null;

            // Check if servlet already loaded.
            if (!loaded.contains(servletName)) {
                servlet = preload(servletName, configuration);
                servlet.context = context;
                System.out.println("servlet.context = " + servlet.context);
                context.setServlets(servletClasses);
            } else {
                servlet = (SmtpServlet) servletClasses.get(servletName);
            }

            // Get all messages for this servlet.
            ArrayList messages = listener.getMessages(configuration);

            if (messages != null) {
                for (int i = 0; i < messages.size(); i++) {
                    Message message = (Message) messages.get(i);
                    SmtpServletRequest request = new SmtpServletRequest();

                    request.setMessage(message);
                    SmtpServletResponse response = new SmtpServletResponse();

                    // Set default values for response object:
                    response.setHost(configuration.host);
                    // Default behavior: send message back to sender.
                    response.setRecipient(request.getRemoteAddr());
                    response.setFrom(configuration.identification);
                    servlet.service(request, response);
                    SmtpSender.send(session, response);
                }
            }
            Thread.sleep(this.poolInterval);
            // System.out.println(servletName + " (names): " + this.servlets.isEmpty());
            // System.out.println(servletName + " (classes): " + this.servletClasses);
        }
    }

    public static void main(String args[]) throws Exception {
        ServletEngine engine = new ServletEngine("", null, null);
        SmtpServletContext context = (SmtpServletContext) engine.readConfiguration();

        context.serverInfo = engine.SERVER_INFO;
        Enumeration all = engine.servlets.keys();

        // Start servlet threads.
        while (all.hasMoreElements()) {
            String servletName = (String) all.nextElement();
            Configuration configuration = (Configuration) engine.servlets.get(servletName);

            new ServletEngine(servletName, configuration, context).start();
        }
    }
}
