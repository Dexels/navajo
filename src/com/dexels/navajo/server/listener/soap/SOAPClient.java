package com.dexels.navajo.server.listener.soap;

import java.io.*;

import javax.xml.messaging.Endpoint;
import javax.xml.soap.*;
import java.net.URL;
import javax.mail.internet.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

//import org.dom4j.*;
//import javax.xml.messaging.Endpoint;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class SOAPClient {

   static final String SIMPLE_SAMPLE_URI = "http://localhost/sport-tester/servlet/SOAPMan/" ;

    public static void main(String args[]) {

        try {

            URL endpoint=new URL(SIMPLE_SAMPLE_URI);
            
            Endpoint ep = new Endpoint(SIMPLE_SAMPLE_URI);
            
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = scf.createConnection();

            MessageFactory mf = MessageFactory.newInstance();

            // Create a message from the message factory.
            SOAPMessage msg = mf.createMessage();

            SOAPPart soapPart=msg.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();


            // create dummy message
            SOAPBody body = envelope.getBody();

            body.addChildElement(envelope.createName("GetReply" , "jaxm",
            "http://sun.com/jaxm/someuri/")).addChildElement("name").addTextNode("sampletest");

            msg.setContentDescription("text/xml");
            msg.saveChanges();

            System.err.println("Sending message to URL: "+ endpoint);

/**
 * Temporarily removed by Frank.
 */

            SOAPMessage reply = connection.call(msg, ep);

            System.err.println("Sent message is logged in \"sent.msg\"");

            FileOutputStream sentFile = new FileOutputStream("sent.msg");
            msg.writeTo(sentFile);
            sentFile.close();

            System.out.println("Received reply from: "+endpoint);

            // Display
            boolean displayResult=true;
            if( displayResult ) {
                // Document source, do a transform.
                System.out.println("Result:");
                TransformerFactory tFact=TransformerFactory.newInstance();
                Transformer transformer = tFact.newTransformer();
                //Source src=reply.getSOAPPart().getContent();
                //StreamResult result=new StreamResult( System.out );
                //transformer.transform(src, result);
                //System.out.println();
            }

            connection.close();

        } catch(Throwable e) {
            e.printStackTrace();
        }
    }
}