package com.dexels.navajo.server.soap;

import javax.xml.soap.*;
import javax.xml.messaging.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import org.w3c.dom.*;

import javax.xml.transform.*;

import javax.naming.*;

import org.apache.commons.logging.*;



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
             *  <SOAP-ENV:Header>
 *   <a:authentication xmlns:a="http://www.dexels.com/xsd/authentication">
 *     <a:username>NAVAJOUSER</a:username>
 *     <a:password>NAVAJOPASSWORD</a:password>
 *     <a:service>NAME_OF_THE_WS</a:service>
 *   </a:authentication>
 * </SOAP-ENV:Header>
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
public class SOAPServlet extends javax.servlet.http.HttpServlet {

    static MessageFactory fac = null;

    static {
        try {
            fac = MessageFactory.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    };

    static Log
        logger = LogFactory.getFactory().getInstance("Samples/Simple");

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        System.out.println("IN SOAPServlet init()");
    }

    // This is the application code for handling the message.. Once the
    // message is received the application can retrieve the soap part, the
    // attachment part if there are any, or any other information from the
    // message.

    public SOAPMessage onMessage(SOAPMessage message) {
        System.out.println("On message called in receiving servlet");
        try {
            System.out.println("Here's the message: ");
            message.writeTo(System.out);

            SOAPMessage msg = fac.createMessage();

            SOAPEnvelope env = msg.getSOAPPart().getEnvelope();

            env.getBody()
                .addChildElement(env.createName("Response"))
                .addTextNode("This is a response");

            return msg;
        } catch(Exception e) {
            logger.error("Error in processing or replying to a message", e);
            return null;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            Document in = com.dexels.navajo.xml.XMLDocumentUtils.createDocument(request.getInputStream(), false);
            String input = com.dexels.navajo.xml.XMLDocumentUtils.toString(in);
            System.out.println("RECEIVED SOAP REQUEST:");
            System.out.println(input);
            java.io.PrintWriter writer = response.getWriter();
            writer.write(input);
            writer.close();
        } catch (Exception e) {

        }
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
