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
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;
import com.dexels.navajo.html.HTMLutils;


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

    public String generateHTMLFromMessage(Navajo tbMessage, ArrayList messages, ArrayList actions, String clientName)
            throws NavajoException {

        StringBuffer result = new StringBuffer();
        String sub = "";

        if (actions != null)
            result.append("<FORM action=" + clientName + " method=post>\n");

        sub = generateHTMLFromMessage2(0, tbMessage, messages, actions, clientName);
        result.append(sub);

        if (actions != null)
            result.append(HTMLutils.generateActions(actions));

        if (actions != null)
            result.append("</FORM>\n");

        return result.toString();
    }

    private String generateHTMLFromMessage2(int level, Navajo tbMessage,
            ArrayList messages, ArrayList actions,
            String clientName)
            throws NavajoException {

        StringBuffer result = new StringBuffer();
        int i, mIndex;
        String description, value, name, cardinality, length;
        ArrayList nested = null;

        // createNodes(tbMessage, messages);

        // Util.debugLog("in generateHTMLFromMessage(): initial messages: " + messages.size());

        for (mIndex = 0; mIndex < messages.size(); mIndex++) {
            // Util.debugLog("trying to read message: " + mIndex);
            n = (Message) messages.get(mIndex);
            // String messageName = n.getName();
            String messageName = n.getFullMessageName();
            // Util.debugLog("Message name: " + messageName);

            ArrayList list = n.getAllProperties();

            // Util.debugLog("Found " + list.size() + " properties.");

            // NodeList list = n.getChildNodes();
            // First show all nested messages.
            nested = n.getAllMessages();
            // Util.debugLog("Found " + nested.size() + " messages...");

            if (messageName.equals("error")) {
                result = generateErrorMessage(n);
            } else {
                result.append("<HR>");
                int fontSize = 6;

                switch (level) {
                case 0:
                    fontSize = 6;
                    break;

                case 1:
                    fontSize = 5;
                    break;

                case 2:
                    fontSize = 3;
                    break;

                case 3:
                    fontSize = 2;
                    break;

                case 4:
                    fontSize = 1;
                    break;

                default:
                    fontSize = 1;
                    break;
                }
                result.append("<font size=" + fontSize + ">" + messageName + "</font><BR>");
                String subResult = "";

                if ((nested != null) && (nested.size() > 0)) {
                    result.append(generateHTMLFromMessage2(level + 1, tbMessage, nested, actions, clientName));
                }
                result.append("<TABLE BGCOLOR=\"#a3e100\">");
                for (i = 0; i < list.size(); i++) {
                    Property e = (Property) list.get(i);
                    String direction = e.getDirection();

                    if (e.getType().equals("label")) {
                        description = e.getDescription();
                        value = e.getValue();
                        result.append(HTMLutils.generateOutputField(description, value) + "\n");
                    } else if ((e.getType().equals("string"))
                            || (e.getType().equals("integer"))
                            || (e.getType().equals("float"))
                            || (e.getType().equals(Property.PASSWORD_PROPERTY))) {
                        description = e.getDescription();
                        value = e.getValue();
                        name = e.getName();

                        length = e.getLength() + "";
                        String parameterName = messageName
                                + Navajo.MESSAGE_SEPARATOR + name;

                        // Util.debugLog("Processing property: " + name);
                        if (direction.equals(Property.DIR_IN)) {
                            if (e.getType().equals(Property.PASSWORD_PROPERTY))
                                result.append(HTMLutils.generatePasswordField(description, parameterName, length, value) + "\n");
                            else
                                result.append(HTMLutils.generateInputField(description, parameterName, length, value) + "\n");
                        } else {
                            result.append(HTMLutils.generateOutputField(description, value) + "\n");
                        }
                    } else if (e.getType().equals("boolean")) {
                        description = e.getDescription();
                        value = e.getValue();
                        name = e.getName();
                        String parameterName = messageName
                                + Navajo.MESSAGE_SEPARATOR + name;

                        result.append(HTMLutils.generateCheckboxField(description, parameterName, "0") + "\n");
                    } else if (e.getType().equals("selection")) {
                        cardinality = e.getCardinality();
                        description = e.getDescription();
                        name = e.getName();
                        ArrayList v = null;

                        if (direction.equals(Property.DIR_IN)) {
                            try {
                                v = e.getAllSelections();
                            } catch (NavajoException tbe) {}
                            // Vector v = tbMessage.getSelectionOptions(name);
                            String parameterName = messageName
                                    + Navajo.MESSAGE_SEPARATOR + name;

                            if (cardinality.equals("1"))
                                result.append(HTMLutils.generateSelectList(description, parameterName, v) + "\n");
                            else
                                result.append(HTMLutils.generateCheckBoxList(description, parameterName, v) + "\n");
                        } else {
                            v = e.getAllSelectedSelections();
                            result.append("<TR><TD><I>" + description + "</I></TD><TD><I>" + name + "</I></TD></TR>\n");
                            result.append("<TR><TD></TD><TD><UL>");
                            for (int q = 0; q < v.size(); q++) {
                                Selection sel = (Selection) v.get(q);

                                result.append("<LI>" + sel.getName() + ":" + sel.getValue());
                            }
                            result.append("</UL></TD></TR>");
                        }
                        result.append("<BR>\n");
                    } else if (e.getType().equals(Property.DATE_PROPERTY)) {
                        description = e.getDescription();
                        value = e.getValue();
                        name = e.getName();
                        String parameterName = messageName
                                + Navajo.MESSAGE_SEPARATOR + name;

                        try {
                            if (direction.equals(Property.DIR_IN))
                                result.append(HTMLutils.generateDateInputField(description, parameterName, value));
                            else
                                result.append(HTMLutils.generateDateOutputField(description, value));
                        } catch (java.text.ParseException pe) {
                            throw new NavajoException(pe.toString());
                        }
                    } else if (e.getType().equals(Property.POINTS_PROPERTY)) {// String image = "<IMG SRC=\"GraphServlet?width=200&height=200&message="+messageName+"&property="+e.getName()+"\"><BR><BR>";
                        // result.append(image);
                    }

                }
                result.append("</TABLE>");
            }
        }

        result.append("<HR>");

        return result.toString();
    }

    public String generateHTMLFromMessage3(Navajo tbMessage,
            ArrayList messages,
            ArrayList actions,
            String clientName,
            boolean setter,
            String xslFile) throws NavajoException {

        String result = "";
        StringWriter text = new java.io.StringWriter();

        XMLDocumentUtils.toXML(tbMessage.getMessageBuffer(), null, null, new StreamResult(text));

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
            if (setter)
                result = XMLDocumentUtils.transform(getDocIn(), xsl);
            else
                result = XMLDocumentUtils.transform(tbMessage.getMessageBuffer(), xsl);

        } catch (TransformerConfigurationException e) {
            System.out.println(e);
            System.out.println("A TransformerConfigurationException occured: " + e);
        } catch (TransformerException e) {
            System.out.println(e);
            System.out.println("A TransformerException occured: " + e);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
            System.out.println("A ParserConfigurationException occured: " + e);
        } catch (java.io.IOException e) {
            System.out.println(e);
            System.out.println("An IOException occured: " + e);
        }
        return result;
    }
}

