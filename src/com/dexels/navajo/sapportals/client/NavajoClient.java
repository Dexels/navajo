package com.dexels.navajo.sapportals.client;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author
 * @version 1.0
 */

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.*;
import java.net.*;
import com.dexels.navajo.xml.XMLutils;

import javax.xml.parsers.*;


public class NavajoClient {

    private static DocumentBuilder builder = null;

    static {
        try {
            System.out.println("Try if JAXP works");
            javax.xml.parsers.DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

            // javax.xml.parsers.DocumentBuilderFactory builderFactory =  new org.apache.xerces.jaxp.DocumentBuilderFactoryImpl();
            // javax.xml.parsers.DocumentBuilderFactory builderFactory = new org.apache.crimson.jaxp.DocumentBuilderFactoryImpl();
            builder = builderFactory.newDocumentBuilder();
            System.out.println("builder = " + builder);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println("Could not find XSLT factory, using system default");
        }
    }

    /**
     * Do a transation with the Navajo Server (name) using
     * a Navajo Message Structure (TMS) compliant XML document.
     */
    private static BufferedInputStream doTransaction(String name, Document d, boolean secure, String keystore, String passphrase)
            throws IOException, NavajoException {
        URL url;

        try {
            url = new URL("http://" + name);

            URLConnection con = url.openConnection();

            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-type", "text/plain");

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            String dString = com.dexels.navajo.xml.XMLDocumentUtils.toString(d);

            // System.out.println("Request doc = " + dString);

            writer.write(dString);
            writer.close();

            // Lees bericht
            BufferedInputStream in = new BufferedInputStream(con.getInputStream());

            return in;
        } catch (Exception te) {
            te.printStackTrace(System.out);
            return null;
        }
    }

    public static Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval) {

        // If Navajo == null, create empty Navajo object.
        if (out == null) {
            Document d = builder.newDocument();
            Element body = (Element) d.createElement(Navajo.BODY_DEFINITION);

            d.appendChild(body);
            out = new Navajo(d);
        }
        Document docOut = out.getMessageBuffer();
        Element body = (Element) XMLutils.findNode(docOut, Navajo.BODY_DEFINITION);
        Element header = out.createHeader(docOut, method, user, password, expirationInterval, null);

        body.appendChild(header);
        try {

            BufferedInputStream in = doTransaction(server, docOut, false, "", "");
            Document docIn = builder.parse(in);

            return new Navajo(docIn);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
