package com.dexels.navajo.server;


import javax.xml.messaging.*;
import javax.xml.soap.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;
import java.sql.*;

import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import com.dexels.navajo.util.*;
import com.dexels.navajo.xml.*;
import com.dexels.navajo.document.*;
import org.dexels.grus.*;


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
 *
 * This servlet is entirely based on the standard Java APIs for SOAP messaging (JAX-M).
 *
 * This special servlet is used to handle SOAP requests.
 * Only the attachment parts of the SOAP request are assumed to contain TML documents.
 * All SOAP attachments are serially processed as TML requests by the dispatcher.
 * All TML responses are send back as SOAP attachments to the sender.
 */
public class TmlSoapServlet extends javax.xml.messaging.JAXMServlet implements ReqRespListener {

    private  static MessageFactory fac = null;

    static {
        try {
            fac = MessageFactory.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    };

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Util.debugLog(this, "In TmlSoapServlet Servlet");
    }

    public void destroy() {
        Util.debugLog(this, "In TmlSoapServlet destroy()");
    }

    public void finalize() {
        Util.debugLog(this, "In TmlSoapServlet finalize()");
    }

    /**
     * This method receives the SOAP message.
     * The attachment parts of the SOAP message are assumed to contain valid TML documents.
     * All the attached TML documents are processed and their responses are send back in the same order
     * as the corresponding requests.
     */
    public SOAPMessage onMessage(SOAPMessage message) {
        SOAPMessage soapOut = null;

        try {
            soapOut = fac.createMessage();
            Iterator iter = message.getAttachments();

            while (iter.hasNext()) {
                AttachmentPart attachment = (AttachmentPart) iter.next();
                String content = (String) attachment.getContent();
                ByteArrayInputStream bai = new ByteArrayInputStream(content.getBytes());
                Document xml = XMLDocumentUtils.createDocument(bai, false);

                if (XMLutils.findNode(xml, "tml") != null) {
                    Navajo doc = new Navajo(xml);
                    // Call Dispatcher with parsed TML document as argument.
                    Dispatcher dis = new Dispatcher("");
                    Navajo outDoc = dis.handle(doc);
                    AttachmentPart part = soapOut.createAttachmentPart();
                    Document outXml = outDoc.getMessageBuffer();
                    StringWriter outString = new StringWriter();

                    XMLDocumentUtils.toXML(outXml, null, null, new StreamResult(outString));
                    part.setContent(outString.toString(), "text/plain");
                    soapOut.addAttachmentPart(part);
                }
            }

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return soapOut;
    }
}
