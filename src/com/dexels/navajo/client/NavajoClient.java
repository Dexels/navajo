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
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.xml.*;
import com.dexels.navajo.util.Util;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;
import javax.servlet.http.HttpServletRequest;

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

class MyX509TrustManager implements com.sun.net.ssl.X509TrustManager {

    /*************************************************************************************************/
    public boolean isClientTrusted(java.security.cert.X509Certificate[] chain) {
        return true;
    }

    /*************************************************************************************************/
    public boolean isServerTrusted(java.security.cert.X509Certificate[] chain) {
        return true;
    }

    /*************************************************************************************************/
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    /*************************************************************************************************/
}


public class NavajoClient {

    public static final int DIRECT_PROTOCOL = 0;
    public static final int HTTP_PROTOCOL = 1;

    // docIn contains the incoming Xml document
    private Navajo docIn;
    // docOut contains the outgoing Xml document
    //private Document docOut;

    private String DTD_FILE = "file:/home/arjen/projecten/Navajo/dtd/tml.dtd";

    // Standard option: use HTTP protocol.
    private int protocol = HTTP_PROTOCOL;

    /**
     * Initialize a NavajoClient object with an empty XML message buffer.
     */
    public NavajoClient(String dtdFile) {
        this.DTD_FILE = "file:" + dtdFile;
    }

    public NavajoClient() {}

    public NavajoClient(int protocol) {
        this.protocol = protocol;
    }

    private void setSecure(String keystore, String passphraseString) throws ClientException {

        try {
            System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
            Util.debugLog("------------> USING SECURE SOCKET LAYER <-----------------------");
            com.sun.net.ssl.KeyManagerFactory kmf;
            KeyStore ks;

            char[] passphrase = passphraseString.toCharArray();
            com.sun.net.ssl.X509TrustManager tm = new MyX509TrustManager();
            com.sun.net.ssl.KeyManager[] km = null;
            com.sun.net.ssl.TrustManager[]tma = {tm};

            // For client authentication (CA):
            // SSLContext sc = SSLContext.getInstance("TLS");
            // Without CA:
            com.sun.net.ssl.SSLContext sc = com.sun.net.ssl.SSLContext.getInstance("SSL");

            kmf = com.sun.net.ssl.KeyManagerFactory.getInstance("SunX509");
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keystore), passphrase);
            kmf.init(ks, passphrase);

            // With client authentication:
            // sc.init(kmf.getKeyManagers(), tma, null);
            // Without client authentication:
            sc.init(km, tma, new java.security.SecureRandom());

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
    protected BufferedInputStream doTransaction(String name, Navajo d,
                                                boolean secure, String keystore, String passphrase,
                                                boolean useCompression)
            throws IOException, ClientException, NavajoException {
        URL url;

        if (secure) {
            setSecure(keystore, passphrase);
            url = new URL("https://" + name);
            Util.debugLog("-----------> Opened secure connection to: " + name);
        } else {
            url = new URL("http://" + name);
        }

        URLConnection con = url.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestProperty("Content-type", "text/plain");

        // Verstuur bericht
        if (useCompression) {
          con.setRequestProperty("Accept-Encoding", "gzip");
          con.setRequestProperty("Content-Encoding", "gzip");
          java.util.zip.GZIPOutputStream out = new java.util.zip.GZIPOutputStream(con.getOutputStream());
          d.write(out);
          out.close();
        }
        else {
          d.write(con.getOutputStream());
        }

        // Lees bericht
        BufferedInputStream in = null;
        if (useCompression) {
              java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(con.getInputStream());
              in = new BufferedInputStream(unzip);
            }  else {
              in = new BufferedInputStream(con.getInputStream());
            }

        return in;
    }

    public Navajo doSimpleSend(Navajo out, String server, String method, String user, String password,
                              long expirationInterval) throws ClientException {
        return doSimpleSend(out,server,method,user,password,expirationInterval,false);
    }

    public Navajo doSimpleSend(Navajo out, String server, String method, String user, String password,
                              long expirationInterval, boolean useCompression) throws ClientException {

        Header header = NavajoFactory.getInstance().createHeader(out, method, user, password, expirationInterval);
        out.addHeader(header);
        try {
            if (protocol == HTTP_PROTOCOL) {
                BufferedInputStream in = doTransaction(server, out, false, "", "", useCompression);
                return NavajoFactory.getInstance().createNavajo(in);
            }
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
            String keystore, String passphrase, long expirationInterval, HttpServletRequest request,
            boolean stripped, boolean checkMethod, boolean useCompression)
            throws NavajoException, ClientException {
        int j;

        Util.debugLog("----------------------> NavajoClient: in doMethod(): secure = " + secure + " <-----------------------------------");

        Navajo out = NavajoFactory.getInstance().createNavajo();
        Header header = NavajoFactory.getInstance().createHeader(out, method, user, password, expirationInterval);
        out.addHeader(header);
        if (message.getMessageBuffer() != null) {
            // Find the required messages for the given rpcName
            ArrayList req = null;

            if (checkMethod) {
                Method dummy = message.getMethod(method);
                if (dummy != null)
                    req = dummy.getRequiredMessages();
                if ((req != null) && (req.size() > 0)) {
                    for (j = 0; j < req.size(); j++) {
                        if (message.getMessage((String) req.get(j)) != null) {
                            out.importMessage(message.getMessage((String) req.get(j)));
                        }
                    }
                }
            } else {
                req = message.getAllMessages();
                for (int k = 0; k < req.size(); k++) {
                    Message msg = (Message) req.get(k);
                    out.importMessage(msg);
                }
            }
        }

        try {

            if (protocol == HTTP_PROTOCOL) {
                BufferedInputStream in = doTransaction(server, out, secure, keystore, passphrase, useCompression);
                docIn = NavajoFactory.getInstance().createNavajo(in);
            }
            else
                throw new ClientException(-1, -1, "Unknown protocol: " + protocol);

            // Append the current docBuffer to keep all the messages
            if (message.getMessageBuffer() != null)
                message.appendDocBuffer(docIn.getMessageBuffer());
            else
                message = NavajoFactory.getInstance().createNavajo(docIn);

        } catch (IOException e) {
            e.printStackTrace();
            throw NavajoFactory.getInstance().createNavajoException("An error occured in doMethod(): " + e.getMessage());
        } finally {}
    }

    protected void doMethod(String method, String user, String password, Navajo message,
            boolean secure, String keystore, String passphrase, long expirationInterval, HttpServletRequest request,
            boolean useCompression)
            throws NavajoException, ClientException {
        doMethod(method, user, password, message, secure, keystore, passphrase, expirationInterval, request, false, useCompression);
    }

    protected void doMethod(String method, String user, String password, Navajo message, String server,
            boolean secure, String keystore, String passphrase, long expirationInterval, HttpServletRequest request,
            boolean useCompression)
            throws NavajoException, ClientException {
        doMethod(method, user, password, message, server, secure, keystore, passphrase, expirationInterval,
                request, false, false, useCompression);
    }

    protected void doMethod(String method, String user, String password, Navajo message,
            boolean secure, String keystore, String passphrase, long expirationInterval, HttpServletRequest request,
            boolean stripped, boolean useCompression)
            throws NavajoException, ClientException {
        String server = message.getMethod(method).getServer();

        if (server.equals(""))
            throw NavajoFactory.getInstance().createNavajoException("No server found for RPC: " + method);

        if (message == null)
            throw NavajoFactory.getInstance().createNavajoException("doMethod(): empty Navajo message");

        doMethod(method, user, password, message, server, secure, keystore, passphrase, expirationInterval,
                request, stripped, false, useCompression);
    }

    public Navajo getDocIn() {
        return docIn;
    }
}
