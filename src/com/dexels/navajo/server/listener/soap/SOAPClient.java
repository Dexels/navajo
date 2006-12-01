package com.dexels.navajo.server.listener.soap;

import java.io.*;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class SOAPClient {

   static final String SIMPLE_SAMPLE_URI = "http://ficus:3000/sportlink/knvb/servlet/SOAPMan" ;

    public static void main(String args[]) {

        try {

            URL endpoint=new URL(SIMPLE_SAMPLE_URI);
            
            //Endpoint ep = new Endpoint(SIMPLE_SAMPLE_URI);
            
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = scf.createConnection();

            MessageFactory mf = MessageFactory.newInstance();

            // Create a message from the message factory.
            SOAPMessage msg = mf.createMessage();

            SOAPPart soapPart= msg.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();


            // create dummy message
            SOAPBody body = envelope.getBody();

            body.addChildElement("Club").addChildElement("ClubIdentifier").addTextNode("BBFW63X");

            SOAPHeader h = msg.getSOAPHeader();
        
            h.addChildElement(envelope.createName("webservice", "navajo", "http://www.dexels.com/navajo"))
            	.addTextNode("club/ProcessQueryClub");
            
            msg.setContentDescription("text/xml");
            msg.saveChanges();

            
            msg.writeTo(System.err);
            
            System.err.println("Sending message to URL: "+ endpoint);

/**
 * Temporarily removed by Frank.
 */

            SOAPMessage reply = connection.call(msg, endpoint);

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
                Source src=reply.getSOAPPart().getContent();
                StreamResult result=new StreamResult( System.out );
                transformer.transform(src, result);
                System.out.println();
            }

            connection.close();

        } catch(Throwable e) {
            e.printStackTrace();
        }
    }
}