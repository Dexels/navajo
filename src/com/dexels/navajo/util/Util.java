

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.util;


import java.io.*;
import java.util.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;

import utils.*;
import com.dexels.navajo.server.*;
import gnu.regexp.*;
import javax.xml.soap.*;
import javax.xml.transform.stream.StreamResult;


public class Util {

    private static ResourceBundle rb = null;
    private static int logLevel = 0;

    public static void debugLog(int level, String message) {// System.out.println("NAVAJO: " + message);
    }

    public static void debugLog(String message) {// System.out.println("NAVAJO: " + message);
    }

    public static void debugLog(Object o, String message) {// System.out.println("NAVAJO: FROM " + o.getClass().getName() + ": " + message);
    }

    public static Property getProperty(Message in, String name, boolean required)
            throws SystemException {

        Property prop = in.getProperty(name);

        if (required && (prop == null))
            throw new SystemException(SystemException.MISSING_PROPERTY, name);

        return prop;
    }

    public static boolean isRegularExpression(String s) {
        if ((s.indexOf("*") != -1) || (s.indexOf(".") != -1)
                || (s.indexOf("\\") != -1) || (s.indexOf("?") != -1)
                || (s.indexOf("[") != -1) || (s.indexOf("]") != -1)
                )
            return true;
        else
            return false;

    }

    public static Message getMessage(Navajo in, String name, boolean required)
            throws SystemException {
        Message msg = in.getMessage(name);

        if (required && (msg == null))
            throw new SystemException(SystemException.MISSING_MESSAGE, name);

        return msg;
    }

    public static String getPropertyValue(Property prop, boolean valueRequired)
            throws SystemException {
        if (prop == null)
            throw new SystemException(SystemException.MISSING_PROPERTY, "");

        String value = prop.getValue();

        if (valueRequired && value.equals(""))
            throw new SystemException(SystemException.MISSING_ATTRIBUTE_VALUE, prop.getName());

        return value;
    }

    public static Object getHashKey(Hashtable hash, Object findValue) {

        Object key;

        Enumeration keys = hash.keys();

        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            if (hash.get(key).equals(findValue))
                return key;
        }
        return null;
    }

    public static Navajo parseSOAPBody(SOAPBody body) throws NavajoException {

        Navajo doc = new Navajo();
        Document xml = doc.getMessageBuffer();

        Iterator iter = body.getChildElements();
        SOAPElement tml = null;

        if (iter.hasNext()) {
            tml = (SOAPElement) iter.next();
        } else {
            throw new NavajoException("Invalid Navajo message");
        }
        if (!tml.getElementName().getLocalName().equals("tml"))
            throw new NavajoException("Invalid Navajo message");

        xml.createElement("header");

        iter = tml.getChildElements();
        SOAPElement header = null;

        if (iter.hasNext()) {
            header = (SOAPElement) iter.next();
        } else {
            throw new NavajoException("Invalid Navajo message");
        }
        SOAPElement transaction = null;
        boolean found = false;

        while (!found) {}

        return doc;
    }

    public static Navajo parseReceivedDocument(BufferedInputStream in) throws NavajoException {
        // try {

        Document doc = null;

        Util.debugLog("About to create XML document");
        // Parse and validate incoming XML document.
        // doc = XmlDocument.createXmlDocument(in, false);
        doc = XMLDocumentUtils.createDocument(in, false);

        Util.debugLog("Created");
        doc.getDocumentElement().normalize();
        Util.debugLog("Parsed");
        // DEBUG
        // doc.write(System.out);
        // XMLDocumentUtils.toXML(doc,null,null,new StreamResult( System.out ));
        return new Navajo(doc);
        // } catch (SAXException saxe) {
        // saxe.printStackTrace();
        // throw new NavajoException(saxe.getMessage());
        // } catch (IOException ioe) {
        // ioe.printStackTrace();
        // throw new NavajoException(ioe.getMessage());
        // }
    }

    public static Navajo readNavajoFile(String fileName) throws java.io.IOException, NavajoException {

        FileInputStream input;
        Document d;
        Navajo outMessage = null;
        String fNaam;

        Util.debugLog(2, "Trying to read file: " + fileName);
        input = new FileInputStream(new File(fileName));

        d = XMLDocumentUtils.createDocument(input, false);
        d.getDocumentElement().normalize();

        Util.debugLog(2, "readNavajoFile(): Parsed XML document");
        outMessage = new Navajo(d);

        // XMLDocumentUtils.toXML(outMessage.getMessageBuffer(),null,null,new StreamResult(System.out) );
        return outMessage;

    }

    public static String formatDate(Date datum) {

        java.text.SimpleDateFormat formatter =
                new java.text.SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(datum);
    }

    /**
     * This function creates a date using year, month and day values.
     */
    public static Date getDate(String year, String month, String day) {

        Calendar datum = Calendar.getInstance();

        if ((year != null) && (month != null) && (day != null))
            datum.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));

        return datum.getTime();
    }

    /**
     * Convert a String to a long. Throw an exception if the String cannot be parsed.
     */
    public static long getLong(String value) throws UserException {

        long l;

        try {
            l = Long.parseLong(value);
        } catch (java.lang.NumberFormatException pe) {
            throw new UserException(UserException.NUMBER_FORMAT_ERROR, "Ongeldig getal: " + value);
        }

        return l;
    }

    /**
     * Convert a String to an integer. Throw an exception if the String cannot be parsed.
     */
    public static int getInt(String value) throws UserException {

        int l;

        try {
            l = Integer.parseInt(value);
        } catch (java.lang.NumberFormatException pe) {
            throw new UserException(UserException.NUMBER_FORMAT_ERROR, "Ongeldig getal: " + value);
        }

        return l;
    }

    /**
     * Convert a String to a double. Throw an exception if the String cannot be parsed.
     */
    public static double getDouble(String value) throws UserException {

        double l;

        try {
            l = Double.parseDouble(value);
        } catch (java.lang.NumberFormatException pe) {
            throw new UserException(UserException.NUMBER_FORMAT_ERROR, "Ongeldig getal: " + value);
        }

        return l;
    }

    /**
     * Convert a String to a Date. Throw an exception if the String cannot be parsed.
     */
    public static Date getDate(String datum) throws UserException {

        Date d = null;

        java.text.SimpleDateFormat parser = new java.text.SimpleDateFormat("yyyy-MM-dd");

        try {
            d = parser.parse(datum);
            return d;
        } catch (java.text.ParseException pe) {
            throw new UserException(UserException.DATE_FORMAT_ERROR, "Ongeldige datum: " + datum);
        }
    }

    /**
     * Log a message.
     */
    public static void logMessage(String message) {
        System.out.println(message);
    }

    public static String formatDouble(double d) {

        java.text.DecimalFormat decFive = new java.text.DecimalFormat("###.##");

        return decFive.format(d);

    }

    public static void elfProef(String nummer) throws UserException {

        boolean result = false;

        if (nummer.length() != 9)
            result = false;

        int total = 0;

        for (int i = 0; i < (nummer.length()); i++) {
            int digit = Integer.parseInt(nummer.charAt(i) + "");

            digit = (9 - i) * digit;
            total += digit;
        }

        if (total % 11 == 0)
            result = true;
        else
            result = false;

        if (!result)
            throw new UserException(UserException.INVALID_ACCOUNT_NR, "Ongeldig rekeningnummer");
    }

    public static boolean regMatch(String regularExpression, String a) throws UserException {
        try {
            RE re = new RE(regularExpression);

            return re.isMatch(a);
        } catch (gnu.regexp.REException re) {
            throw new UserException(-1, re.getMessage());
        }
    }

    public static void main(String args[]) throws Exception {
        RE re = new RE("message.*");
        boolean result = re.isMatch("message");

        System.out.println("result = " + result);
    }

}

