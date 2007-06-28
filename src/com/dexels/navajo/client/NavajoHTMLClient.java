/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

import java.io.*;
import java.util.*;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.*;

import org.w3c.dom.Document;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;
import com.dexels.navajo.client.html.HTMLutils;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * The NavajoHTMLClient interfaces between HTML forms and Navajo Message objects
 */

public class NavajoHTMLClient extends NavajoClient {

    // private Vector nodes;
    //private Message n;

    public NavajoHTMLClient(String dtdFile) {
        super(dtdFile);
    }

    public NavajoHTMLClient(int protocol) {
        super(protocol);
    }

    // NOTE: readHTMLForm() ASSUMES THAT ALL PROPERTY NAMES MAP UNIQUELY TO FORM FIELD NAMES!!!!!
    public String readHTMLForm(Navajo tbMessage, HttpServletRequest request) throws NavajoException {
//        System.err.println("BEFORE READING FORM CREATED FORM--------------------");
//        tbMessage.write(System.err);
//        System.err.println("BEFORE READING FORM END OF FORM --------------------");
        String s =  HTMLutils.readHTMLForm(tbMessage, request);
//        System.err.println("CREATED FORM--------------------");
//        tbMessage.write(System.err);
//        System.err.println("END OF FORM --------------------");
        return s;
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

    /**
     * Write everything to the supplied writer. Don't close it, and don't buffer it.
     * @param tbMessage
     * @param messages
     * @param actions
     * @param clientName
     * @param xslFile
     * @param out
     * @throws NavajoException
     */
    public void generateHTMLFromMessage(Navajo tbMessage,
                                          ArrayList messages,
                                          ArrayList actions,
                                          String clientName,
                                          String xslFile, Writer out) throws NavajoException {

//        String result = "";
//        StringWriter text = new java.io.StringWriter();

        

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
//              StringWriter sw = new StringWriter();
              Transformer  transformer =  javax.xml.transform.TransformerFactory.newInstance().newTransformer(new StreamSource(xsl));

              transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
              if (tbMessage.getMessageBuffer() instanceof Document) {
                transformer.transform(new DOMSource((Document) tbMessage.getMessageBuffer()), new StreamResult(out));
                } else {
//                  System.err.println("File: "+xslFile);10
//              System.err.println("STR: "+tbMessage.toString());
//              System.err.println();
                      if (tbMessage instanceof BaseNavajoImpl) {
                        BaseNavajoImpl bni = (BaseNavajoImpl)tbMessage;
                        
                        Reader reader = bni.createReader();
                        transformer.transform(new StreamSource(reader), new StreamResult(out));
                        reader.close();
                      } else {
                        transformer.transform(new StreamSource(new StringReader(tbMessage.toString())), new StreamResult(out));
                      }
                      
            }
              
              tbMessage.write(out);

//              result = sw.toString();


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            System.out.println(e);
            System.out.println("A TransformerConfigurationException occured: " + e);
        } catch (TransformerException e) {
            e.printStackTrace();
            System.out.println(e);
            System.out.println("A TransformerException occured: " + e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
  
//        return result;
    }
}

