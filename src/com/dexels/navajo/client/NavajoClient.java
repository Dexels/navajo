/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;
import com.dexels.navajo.util.Util;
//import com.dexels.navajo.server.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

import com.sun.net.ssl.*;
import com.sun.net.ssl.HttpsURLConnection;
import com.sun.net.ssl.HttpsURLConnection;

/**
 * This class implements a generic Navajo client. This class offers
 * the following services: <BR>
 * <OL>
 * <LI> It controls the XML message buffer (see Navajo class)
 * <LI> It adds an XML action buffer
 * <LI> It facilitates the correct communication with the Server
 * <LI> It offers an interface to an application that whishes to
 *    use the Navajo services
 * </OL>
 * <BR>
 * IMPORTANT NOTE FOR USE WITH HTTP PROTOCOL (OR OTHER STATELESS
 * PROTOCOLS): <BR>
 * For a proper functioning of this class it is important that
 * instances are coupled to sessions. The state of object instances
 * can be stored either by storing the entire object instance
 * or the action buffer (actionBuffer) and the message buffer (docBuffer).
 * The object state can be restored by restoring the entire
 * object instance or calling the NavajoClient(Document actionBuffer,
 * Document docBuffer) constructor.
 *
 * @author Arjen Schoneveld (Dexels/Brentfield)
 * @version 0.1 (11/7/2000)
 *
 */

class MyX509TrustManager implements X509TrustManager
{
/*************************************************************************************************/
public boolean isClientTrusted(java.security.cert.X509Certificate[] chain)
{
  return true;
}
/*************************************************************************************************/
public boolean isServerTrusted(java.security.cert.X509Certificate[] chain)
{
  return true;
}
/*************************************************************************************************/
public java.security.cert.X509Certificate[] getAcceptedIssuers()
{
  return null;
}
/*************************************************************************************************/
}


public class NavajoClient {

    public static final int DIRECT_PROTOCOL = 0;
    public static final int HTTP_PROTOCOL = 1;

    // docIn contains the incoming Xml document
    private Document docIn;
    // docOut contains the outgoing Xml document
    private Document docOut;

    private String DTD_FILE = "file:/home/arjen/projecten/Navajo/dtd/tml.dtd";

    // Standard option: use HTTP protocol.
    private int protocol = HTTP_PROTOCOL;

    /**
     * Initialize a NavajoClient object with an empty XML message buffer.
     */
    public NavajoClient(String dtdFile) {
      this.DTD_FILE = "file:" + dtdFile;
    }

    public NavajoClient() {

    }

    public NavajoClient(int protocol) {
        this.protocol = protocol;
    }

    private void setSecure(String keystore, String passphraseString) throws ClientException {

      try {
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        Util.debugLog("------------> USING SECURE SOCKET LAYER <-----------------------");
        KeyManagerFactory kmf;
        KeyStore ks;

        char[] passphrase = passphraseString.toCharArray();
        X509TrustManager tm = new MyX509TrustManager();
        KeyManager [] km = null;
        TrustManager []tma = {tm};

        // For client authentication (CA):
        //SSLContext sc = SSLContext.getInstance("TLS");
        // Without CA:
        SSLContext sc = SSLContext.getInstance("SSL");

        kmf = KeyManagerFactory.getInstance("SunX509");
        ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(keystore), passphrase);
        kmf.init(ks, passphrase);

        // With client authentication:
        //sc.init(kmf.getKeyManagers(), tma, null);
        // Without client authentication:
        sc.init(km,tma,new java.security.SecureRandom());

        javax.net.ssl.SSLSocketFactory sf1 = sc.getSocketFactory();
        HttpsURLConnection.setDefaultSSLSocketFactory(sf1);
      } catch (Exception e) {
        throw new ClientException(-1, -1, e.getMessage());
      }
    }

    /**
     * Do a transation with the Navajo Server (name) using
     * a Navajo Message Structure (TMS) compliant XML document.
     */
    protected BufferedInputStream doTransaction(String name, Document d, boolean secure, String keystore, String passphrase)
	throws IOException, ClientException, NavajoException
    {
	    URL url;

        if (secure) {
          setSecure(keystore, passphrase);
      	  url = new URL("https://"+name);
          Util.debugLog("-----------> Opened secure connection to: " + name);
        } else {
          url = new URL("http://"+name);
        }

        URLConnection con = url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestProperty("Content-type", "text/plain");

	// Verstuur bericht
        XMLDocumentUtils.toXML(d,null,null,new StreamResult( con.getOutputStream() ));

	// Lees bericht
        BufferedInputStream in = new BufferedInputStream(con.getInputStream());

        return in;
    }

    public Navajo doSimpleSend(Navajo out, String server, String method, String user, String password) throws ClientException {

        Document docOut = out.getMessageBuffer();
        Element body = (Element) XMLutils.findNode(docOut, Navajo.BODY_DEFINITION);
        Element header = out.createHeader(docOut, method, user, password, null);
    	body.appendChild(header);
        try {
          if (protocol == HTTP_PROTOCOL) {
            BufferedInputStream in = doTransaction(server, docOut, false, "", "");
            docIn = XMLDocumentUtils.createDocument( in, false );
            docIn.getDocumentElement().normalize();
            return new Navajo(docIn);
          } //else if (protocol == DIRECT_PROTOCOL) {
            //Dispatcher d = new Dispatcher();
            //return d.handle(new Navajo(docOut));
          //}
          else
            throw new ClientException(-1, -1, "Unknown protocol: " + protocol);
        } catch (Exception e) {
          throw new ClientException(-1, -1, e.getMessage());
        }

    }

    /**
     * Execute an action that is eiher defined in the action buffer
     * or is otherwise an existing action known by the Navajo server
     * (i.e. an initial action to request a service). If the action
     * is defined in the action buffer the required messages for that
     * action are assembled from the message buffer in an output
     * XML document that is sent to the Navajo server. An input XML
     * document is received from the Navajo server from which the
     * messages are appended to the message buffer and an action buffer
     * is created based upon the defined actions in the input XML
     * document. The method returns a list of received messages that
     * can be processed by the client application.
     *
     * PRE CONDITION: Either of the following situations must be valid:
     * 1. Both action and message buffer are empty and the method is
     *    known at the Navajo server but does not require any messages
     *    as parameter.
     * 2. The action buffer and the message buffer exist. The method
     *    must be defined in the action buffer. The required messages
     *    must be present in the message buffer.
     *
     * POST CONDITION: Newly received messages are appended to the
     * message buffer. If there are any new actions received a
     * clean action buffer is created.
     *
     */

    protected void doMethod(String method, String user, String password,
                            Navajo message, String server, boolean secure,
                            String keystore, String passphrase, HttpServletRequest request,
                            boolean stripped)
                throws NavajoException, ClientException
    {
	    int j;
        Util.debugLog("----------------------> NavajoClient: in doMethod(): secure = " + secure + " <-----------------------------------");
	    docOut = XMLDocumentUtils.createDocument();
	    Element body = docOut.createElement(Navajo.BODY_DEFINITION);   //(Element)

        RequestHeader rh = null;
        if (request != null) {
          rh = new RequestHeader(request);
        }

        Element header = message.createHeader(docOut, method, user, password, rh);
        body.appendChild(header);
    	// Check if there exists a current XML document
        // and find rpcName therein
        Node messageBody = XMLutils.findNode(message.getMessageBuffer(), Navajo.BODY_DEFINITION);

	    if (message.getMessageBuffer() != null) {
	    // Find the required messages for the given rpcName
            ArrayList req = null;
            Method dummy = message.getMethod(method);
            if (dummy != null)
              req = dummy.getRequiredMessages();
            if ((req != null) && (req.size() > 0)) {
                for (j = 0; j < req.size(); j++) {
                        if (message.getMessage(messageBody, (String) req.get(j)) != null) {
                            Node n = docOut.importNode(message.getMessage(messageBody,(String) req.get(j), stripped),true);
                            body.appendChild(n);
                        }
                }
            }
    	}

    	docOut.appendChild(body);

        try {
            if (protocol == HTTP_PROTOCOL) {
              BufferedInputStream in = doTransaction(server, docOut, secure, keystore, passphrase);
              docIn = XMLDocumentUtils.createDocument(in,false);
              docIn.getDocumentElement().normalize();
              in.close();
            } //else if (protocol == DIRECT_PROTOCOL) {
              //Dispatcher d = new Dispatcher();
              //docIn = d.handle(new Navajo(docOut)).getMessageBuffer();
            //}
            else
              throw new ClientException(-1, -1, "Unknown protocol: " + protocol);

            // Append the current docBuffer to keep all the messages
            if (message.getMessageBuffer() != null)
            message.appendDocBuffer(docIn);
            else
            message.createDocBuffer(docIn);

        } catch (IOException e) {
               e.printStackTrace();
           throw new NavajoException("An error occured in doMethod(): " +
                           e.getMessage());
        //} catch (FatalException fe) {
        //   throw new NavajoException(fe.getMessage());
        } finally {

        }
    }

    protected void doMethod(String method, String user, String password, Navajo message,
                         boolean secure, String keystore, String passphrase, HttpServletRequest request)
                throws NavajoException, ClientException
    {
      doMethod(method, user, password, message, secure, keystore, passphrase, request, false);
    }

    protected void doMethod(String method, String user, String password, Navajo message, String server,
                         boolean secure, String keystore, String passphrase, HttpServletRequest request)
                throws NavajoException, ClientException
    {
      doMethod(method, user, password, message, server, secure, keystore, passphrase, request, false);
    }

    protected void doMethod(String method, String user, String password, Navajo message,
                         boolean secure, String keystore, String passphrase, HttpServletRequest request, boolean stripped)
                throws NavajoException, ClientException
    {
      String server = message.getMethod(method).getServer();
      if (server.equals(""))
        throw new NavajoException("No server found for RPC: " + method);

      if (message == null)
        throw new NavajoException("doMethod(): empty Navajo message");

      doMethod(method, user, password, message, server, secure, keystore, passphrase, request, stripped);
    }


    public Document getDocIn(){
      return docIn;
    }

    public static void main(String args[]) {




    }
}
