package com.dexels.navajo.server.listener.smtp;


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
import org.dexels.servlet.smtp.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.util.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import org.dexels.grus.DbConnectionBroker;
import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import javax.servlet.*;
import javax.xml.transform.stream.StreamResult;


/**
 * This servlet is based on the org.dexels.servlet.smtp package which implements "SMTP servlets" in the same
 * manner that "HTTP servlets" are implemented in the javax.servlet.http package.
 *
 * The servlet receives an incoming mail (SmtpServletRequest) and sends outgoing mail (SmtpServletResponse).
 * The SmtpServletRequest contains a TML document which is processed by the dispatcher.
 * The resulting TML document is send back with the SmtpServletResponse.
 */

public class TmlSmtpServlet extends org.dexels.servlet.smtp.SmtpServlet {

    public TmlSmtpServlet() {}

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Util.debugLog(this, "In TmlHttpServlet Servlet");
    }

    // public  Navajo parseReceivedDocument(InputStream in) throws NavajoException
    // {
    // try {
    // Document doc = null;
    // Util.debugLog("About to create XML document");
    // // Parse and validate incoming XML document.
    // doc = XmlDocument.createXmlDocument(in, false);
    // Util.debugLog("Created");
    // doc.getDocumentElement().normalize();
    // Util.debugLog("Parsed");
    // // DEBUG
    // doc.write(System.out);
    // return new Navajo(doc);
    // } catch (SAXException saxe) {
    // saxe.printStackTrace();
    // throw new NavajoException(saxe.getMessage());
    // } catch (IOException ioe) {
    // ioe.printStackTrace();
    // throw new NavajoException(ioe.getMessage());
    // }
    // }
    public Navajo parseReceivedDocument(ServletInputStream in) throws NavajoException {
        Document doc = null;

        Util.debugLog("About to create XML document");
        // Parse and validate incoming XML document.
        doc = XMLDocumentUtils.createDocument(in, false);

        Util.debugLog("Created");
        doc.getDocumentElement().normalize();
        Util.debugLog("Parsed");
        // DEBUG
        XMLDocumentUtils.toXML(doc, null, null, new StreamResult(System.out));
        return NavajoFactory.getInstance().createNavajo(doc);
    }

    public void doSend(SmtpServletRequest req, SmtpServletResponse res) throws ServletException, IOException {

        try {
            // Call Postman with parsed TML document.
            Util.debugLog("Deliver called");
            Util.debugLog("Servlet name = " + this.getServletInfo());
            Util.debugLog("Content type = " + req.getContentType());
            Util.debugLog("Remote address = " + req.getRemoteAddr());

            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(req.getReader());
            String line = "";
            int i = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.equals(""))
                    buffer.append(line);
            }
            Util.debugLog("buffer = " + buffer.toString());
            // Navajo in = parseReceivedDocument(new java.io.StringBufferInputStream(buffer.toString()));
            // Navajo in = parseReceivedDocument(buffer.toString());
            // input parameter for method parseReceivedDocument changed from String to ServletInputStream -
            Navajo in = parseReceivedDocument(req.getInputStream());
            // Call Dispatcher with parsed TML document as argument.
            Dispatcher dis = new Dispatcher("", new com.dexels.navajo.server.FileInputStreamReader());
            Navajo outDoc = dis.handle(in);
            OutputStream out = (OutputStream) res.getOutputStream();
            outDoc.write(out);
            out.close();
            Util.debugLog(this, "sendNavajoDocument(): Done");
            res.setSubject(in.getHeader().getRPCName());
            res.setContentType("text/xml");
        } catch (FatalException e) {
            throw new ServletException(e);
        } catch (NavajoException te) {
            throw new ServletException(te);
        }
    }
}
