/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

import java.io.StringWriter;
import java.util.*;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.*;
import javax.xml.transform.*;

import org.w3c.dom.Document;

import com.dexels.navajo.document.*;
import com.dexels.navajo.client.html.HTMLutils;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

public class NavajoHTMLClient extends NavajoClient {

    // private Vector nodes;
    private Message n;

    public NavajoHTMLClient(String dtdFile) {
        super(dtdFile);
    }

    public NavajoHTMLClient(int protocol) {
        super(protocol);
    }

    // NOTE: readHTMLForm() ASSUMES THAT ALL PROPERTY NAMES MAP UNIQUELY TO FORM FIELD NAMES!!!!!
    public String readHTMLForm(Navajo tbMessage, HttpServletRequest request) throws NavajoException {
        return HTMLutils.readHTMLForm(tbMessage, request);
    }

    private StringBuffer generateErrorMessage(Message msg) {
        StringBuffer result = new StringBuffer();
        Property prop = null;

        prop = msg.getProperty("message");
        result.append("<H1> ERROR </H1><H2>" + prop.getValue() + "</H2>");
        prop = msg.getProperty("code");
        result.append("Code: " + prop.getValue() + "<BR>");
        prop = msg.getProperty("level");
        result.append("Severity level: " + prop.getValue());

        return result;
    }

    public String generateHTMLFromMessage(Navajo tbMessage,
                                          ArrayList messages,
                                          ArrayList actions,
                                          String clientName,
                                          String xslFile) throws NavajoException {

        String result = "";
        StringWriter text = new java.io.StringWriter();

        tbMessage.write(text);

        Message errMsg = tbMessage.getMessage("error");

        if (errMsg != null) {               // Format error message for HTML usage. Replace \n with </BR>
            Property prop = errMsg.getProperty("message");
            String value = prop.getValue();
            StringBuffer newValue = new StringBuffer(value.length());

            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == '\n')
                    newValue.append("</BR>");
                else
                    newValue.append(value.charAt(i));
            }
            prop.setValue(newValue.toString());
        }
        java.io.File xsl = new java.io.File(xslFile);

        try {

            // if aanvraag is filled, then only process the new TML Message, else
            // process all retrieved TML Messages
            // new StreamSource(new StringReader(elmnt.toString()))
              StringWriter sw = new StringWriter();
              Transformer  transformer =  javax.xml.transform.TransformerFactory.newInstance().newTransformer(new StreamSource(xsl));

              transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
              transformer.transform(new StreamSource(new StringReader(tbMessage.toString())), new StreamResult(sw));

              result = sw.toString();


        } catch (TransformerConfigurationException e) {
            System.out.println(e);
            System.out.println("A TransformerConfigurationException occured: " + e);
        } catch (TransformerException e) {
            System.out.println(e);
            System.out.println("A TransformerException occured: " + e);
        }
        return result;
    }
}

