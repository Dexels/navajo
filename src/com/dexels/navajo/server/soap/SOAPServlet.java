package com.dexels.navajo.server.soap;

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
import com.dexels.navajo.server.Dispatcher;
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
 * The SOAP servlet receives and sends an XML format that is more common
 * SOAP based webservices than TML.
 *
 * a tml document:
 *
 * <message name="X">
 *   <property name="Y" value="Z" type="string" direction="in" ....>
 *   <property name="ENUM" type="selection">
 *      <option name="AAP" value="0"/>
 *      <option name="NOOT" value="1"/>
 *
 * will be translated to:
 * <X>
 *   <Y>
 *   Z
 *   </Y>
 *   <ENUM>
 *     [0|1]
 *   </ENUM>
 *
 * and an accompanying XSD (XML Schema):
 *
 * <xsd:element name="X">
 *    <xsd:element name="Y">
 *      <xsd:value type="xs:string">
 *    <xsd:element name="ENUM">
 *      <xsd:enumeration value="0">
 *      <xsd:enumeration value="1">
 *
 *
 * (or someting like that... Note that the direction attribute gets lost!)
 *
 * Furthermore, a WSDL description and a UDDI registration could be generated.
 *
 * Typically a SOAP webservice is called by the following URL:
 *
 * localhost/soap/NameOfTheWebservice
 *
 * This get's translated to something like this:
 *
 * localhost/servlet/TmlSoapServlet?name=NameOfTheWebservice
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
public class SOAPServlet extends javax.xml.messaging.JAXMServlet implements ReqRespListener {

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
            SOAPBody body = message.getSOAPPart().getEnvelope().getBody();
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

    private void extractMessage(Navajo out, SOAPElement element, SOAPEnvelope ev) {
        String name = element.getElementName().getLocalName();
        if (name.equals("message")) {
            System.out.println("message = " + name);
            //Message msg = Message.create(out, element.getAttributeValue(ev.createName("name")));
            //out.addMessage(msg);
        } else {
          System.out.println(name);
        }
    }

    private Navajo createNavajo(SOAPElement tml, SOAPEnvelope ev) throws NavajoException {
      Navajo out = new Navajo();
      Iterator all = tml.getChildElements();
      while (all.hasNext()) {
        //extractMessage(out, (SOAPElement) all.next());
      }
      return out;
    }

    public static void main(String args[]) throws Exception {
      SOAPMessage soapOut = fac.createMessage();
      SOAPEnvelope envelope = soapOut.getSOAPPart().getEnvelope();
      SOAPBody body = envelope.getBody();
      SOAPElement tml = body.addChildElement(envelope.createName("tml"));
      SOAPElement message = tml.addChildElement(envelope.createName("message"));
      message.addAttribute(envelope.createName("name"), "input");
      System.out.println(((SOAPElement) body.getChildElements().next()).getElementName().getQualifiedName());
      //Navajo out = new TmlSoapServlet().createNavajo(((SOAPElement) body.getChildElements().next()));
    }
}
