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
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.security.*;
import javax.servlet.http.*;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.TrustManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.HttpsURLConnection;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.KeyManager;
import com.dexels.navajo.client.serverasync.*;

class MyX509TrustManager
    implements X509TrustManager {
  public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    return null;
  }
  public void checkClientTrusted(
      java.security.cert.X509Certificate[] certs, String authType) {
  }
  public void checkServerTrusted(
      java.security.cert.X509Certificate[] certs, String authType) {
  }
}

public class NavajoClient
    implements ClientInterface {
  public static final int DIRECT_PROTOCOL = 0;
  public static final int HTTP_PROTOCOL = 1;
  private String host = null;
  private String username = null;
  private String password = null;


  private long timeStamp = 0;

  // docOut contains the outgoing Xml document
  //private Document docOut;
  private String DTD_FILE = "file:/home/arjen/projecten/Navajo/dtd/tml.dtd";
  // Standard option: use HTTP protocol.
  private int protocol = HTTP_PROTOCOL;
  private Map propertyMap = new HashMap();
  private boolean useLazyMessaging = true;
  private ErrorResponder myResponder;
  private boolean setSecure = false;
  private ArrayList myActivityListeners = new ArrayList();
  private SSLSocketFactory sslFactory = null;
  private Map serviceCache = new HashMap();
  private Map cachedServiceNameMap = new HashMap();

  private Map asyncRunnerMap = new HashMap();


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
// ADDED:
  public void init(URL config) throws ClientException {
    // not implemented
  }
  public String getUsername() {
    return username;
  }
  public String getPassword() {
//    System.err.println("Getting password: "+password);
    return password;
  }
  public String getServerUrl() {
    return host;
  }
  public void setUsername(String s) {
    username = s;
  }
  public void setServerUrl(String url) {
    host = url;
  }
  public void setPassword(String pw) {
    password = pw;
  }

  public void addCachedService(String service){
    cachedServiceNameMap.put(service, service);
  }

  public void removeCachedService(String service){
    cachedServiceNameMap.remove(service);
    serviceCache.remove(service);
  }

  public Navajo doSimpleSend(Navajo out, String method) throws ClientException {
    if (username == null) {
      throw new ClientException(1, 1, "No username set!");
    }
    if (password == null) {
      throw new ClientException(1, 1, "No password set!");
    }
    if (host == null) {
      throw new ClientException(1, 1, "No host set!");
    }
    System.err.println("------> Calling service: " + method);
//    try {
//      out.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
    return doSimpleSend(out, host, method, username, password, -1, false);
  }
  /**
   *
   * @param keystore InputStream to keystore resource.
   * @param passphrase passphrase to keystore resource.
   * @param useSecurity if true TLS security is enabled.
   * @throws ClientException
   */
  public void setSecure(InputStream keystore, String passphrase,
                        boolean useSecurity) throws ClientException {
    setSecure = useSecurity;
    if (sslFactory == null) {
      try {
        SSLContext ctx = SSLContext.getInstance("TLS");
        // Generate the KeyManager (for client auth to server)
        KeyManager[] km = null;
        // Load the '.keystore' file
        KeyStore ks = KeyStore.getInstance("JKS");
        char[] password = passphrase.toCharArray();
        ks.load(keystore, password);
        // Generate KeyManager from factory and loaded keystore
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, password);
        km = kmf.getKeyManagers();
        TrustManager[] tm = null;
        // Generate TrustManager from factory and keystore
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);
        tm = tmf.getTrustManagers();
        ctx.init(km, tm, null);
        sslFactory = ctx.getSocketFactory();
      }
      catch (Exception e) {
        e.printStackTrace();
        throw new ClientException( -1, -1, e.getMessage());
      }
    }
  }
  /**
   *
   * @param keystore fully specified filename of the keystore.
   * @param passphrase password needed to use the keystore.
   * @param useSecurity set this to true if secure communications using certificates must be enabled.
   */
  public void setSecure(String keystore, String passphrase, boolean useSecurity) throws
      ClientException {
    setSecure = useSecurity;
    if (keystore == null) {
      throw new ClientException( -1, -1, "Empty keystore specified: null");
    }
    File f = new File(keystore);
    if (!f.exists()) {
      throw new ClientException( -1, -1,
                                "Could not find certificate store: " + keystore);
    }
    if (setSecure) {
      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
      System.setProperty("java.protocol.handler.pkgs",
                         "com.sun.net.ssl.internal.www.protocol");
      System.setProperty("javax.net.ssl.trustStore", keystore);
      System.setProperty("javax.net.ssl.keyStore", keystore);
      System.setProperty("javax.net.ssl.keyStorePassword", passphrase);
    }
  }
  /**
   * Do a transation with the Navajo Server (name) using
   * a Navajo Message Structure (TMS) compliant XML document.
   */
  public final BufferedInputStream doTransaction(String name, Navajo d,
                                              boolean useCompression) throws
      IOException, ClientException, NavajoException {
    URL url;
    timeStamp = System.currentTimeMillis();

    if (setSecure) {
      url = new URL("https://" + name);
    }
    else {
      url = new URL("http://" + name);
    }
    System.err.println("in doTransaction: opening url: " + url.toString());
    URLConnection con = null;
    if (sslFactory == null) {
      con = (HttpURLConnection) url.openConnection();
    }
    else {
      HttpsURLConnection urlcon = (HttpsURLConnection) url.openConnection();
      urlcon.setSSLSocketFactory(sslFactory);
      con = urlcon;
    }
    con.setDoOutput(true);
    con.setDoInput(true);
    con.setUseCaches(false);
    con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
    // Verstuur bericht
    if (useCompression) {
      con.setRequestProperty("Accept-Encoding", "gzip");
      con.setRequestProperty("Content-Encoding", "gzip");
      java.util.zip.GZIPOutputStream out = new java.util.zip.GZIPOutputStream(
          con.getOutputStream());
      d.write(out);
      out.close();
      long tt = System.currentTimeMillis() - timeStamp;
      System.err.println("Sending request took: "+tt+" millisec");
      timeStamp = System.currentTimeMillis();
    }
    else {
      try {
        d.write(con.getOutputStream());
        long tt = System.currentTimeMillis() - timeStamp;
        System.err.println("Sending request took: "+tt+" millisec");
        timeStamp = System.currentTimeMillis();
      }
      catch (java.net.NoRouteToHostException nrthe) {
        throw new ClientException( -1, 20,
                                  "Could not connect to URI: " + name + ", check your connection");
      }
      catch (java.net.SocketException se) {
        se.printStackTrace();
        throw new ClientException( -1, 21,
            "Could not connect to network, check your connection");
      }
    }
    // Lees bericht
    BufferedInputStream in = null;
    //System.err.println("content type = " + con.getContentType()+" using compression: "+useCompression);
    //System.err.println("content encoding = " + con.getContentEncoding());
    if (useCompression) {
      java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(
          con.getInputStream());
      in = new BufferedInputStream(unzip);
    }
    else {
      in = new BufferedInputStream(con.getInputStream());
    }
    long tt = System.currentTimeMillis() - timeStamp;
    System.err.println("Executing script took: "+tt+" millisec");
    timeStamp = System.currentTimeMillis();

    return in;
  }
  public Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval) throws ClientException {
    return doSimpleSend(out, server, method, user, password, expirationInterval, false);
  }
  public Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval, boolean useCompression) throws ClientException {
    // NOTE: prefix persistence key with method, because same Navajo object could be used as a request
    // for multiple methods!
    String cacheKey = method + out.persistenceKey();
    if(serviceCache.get(cacheKey) != null && cachedServiceNameMap.get(method) != null){
      System.err.println("--> Returning cached WS");
      return (Navajo)serviceCache.get(cacheKey);
    }
    fireActivityChanged(true, method);
    Header header = out.getHeader();
    if (header == null) {
      header = NavajoFactory.getInstance().createHeader(out, method, user,
          password, expirationInterval);
      out.addHeader(header);
    }
    else {
      header.setRPCName(method);
      header.setRPCUser(user);
      header.setRPCPassword(password);
      header.setExpirationInterval(expirationInterval);
    }
    try {

      if (protocol == HTTP_PROTOCOL) {
        System.err.println("Starting transaction");
        Header h = out.getHeader();
        //System.err.println("HUser: " + h.getRPCUser());
        //System.err.println("HPass: " + h.getRPCPassword());
        //System.err.println("Header: " + h.toString());
        BufferedInputStream in = doTransaction(server, out, useCompression);
        Navajo n = NavajoFactory.getInstance().createNavajo(in);
        long tt = System.currentTimeMillis() - timeStamp;
        //System.err.println("Creating navajo took: "+tt+" millisec");
        timeStamp = System.currentTimeMillis();

        if (myResponder != null) {
          myResponder.check(n);
        }
        fireActivityChanged(false, method);
        if(cachedServiceNameMap.get(method) != null){
          serviceCache.put(cacheKey, n);
        }
        return n;
      }
      else {
        throw new ClientException( -1, -1, "Unknown protocol: " + protocol);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      fireActivityChanged(false, method);
      throw new ClientException( -1, -1, e.getMessage());
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
   * @deprecated
   *
   */
  protected Navajo doMethod(String method, String user, String password,
                            Navajo message, String server, boolean secure,
                            String keystore, String passphrase,
                            long expirationInterval, HttpServletRequest request,
                            boolean stripped, boolean checkMethod,
                            boolean useCompression) throws NavajoException,
      ClientException {
    int j;
    Navajo out = NavajoFactory.getInstance().createNavajo();
    if (message.getMessageBuffer() != null) {
      // Find the required messages for the given rpcName
      ArrayList req = null;
      if (checkMethod) {
        Method dummy = message.getMethod(method);
        if (dummy != null) {
          req = dummy.getRequiredMessages();
        }
        if ( (req != null) && (req.size() > 0)) {
          for (j = 0; j < req.size(); j++) {
            if (message.getMessage( (String) req.get(j)) != null) {
              out.importMessage(message.getMessage( (String) req.get(j)));
            }
          }
        }
      }
      else {
        req = message.getAllMessages();
        for (int k = 0; k < req.size(); k++) {
          Message msg = (Message) req.get(k);
          out.importMessage(msg);
        }
      }
    }
    Navajo docIn = null;
    try {
      if (protocol == HTTP_PROTOCOL) {
        BufferedInputStream in = doTransaction(server, out, useCompression);
        docIn = NavajoFactory.getInstance().createNavajo(in);
      }
      else {
        throw new ClientException( -1, -1, "Unknown protocol: " + protocol);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      throw NavajoFactory.getInstance().createNavajoException(
          "An error occured in doMethod(): " + e.getMessage());
    }
    finally {}
    return docIn;
  }
  protected Navajo doMethod(String method, String user, String password,
                            Navajo message,
                            boolean secure, String keystore, String passphrase,
                            long expirationInterval, HttpServletRequest request,
                            boolean useCompression) throws NavajoException,
      ClientException {
    return doMethod(method, user, password, message, secure, keystore,
                    passphrase,
                    expirationInterval, request, false, useCompression);
  }
  /**
   *
   * @param method
   * @param user
   * @param password
   * @param message
   * @param server
   * @param secure
   * @param keystore
   * @param passphrase
   * @param expirationInterval
   * @param request
   * @param useCompression
   * @return
   * @throws NavajoException
   * @throws ClientException
   *
   * @deprecated
   */
  protected Navajo doMethod(String method, String user, String password,
                            Navajo message, String server,
                            boolean secure, String keystore, String passphrase,
                            long expirationInterval, HttpServletRequest request,
                            boolean useCompression) throws NavajoException,
      ClientException {
    return doMethod(method, user, password, message, server, secure, keystore,
                    passphrase, expirationInterval,
                    request, false, false, useCompression);
  }
  protected Navajo doMethod(String method, String user, String password,
                            Navajo message,
                            boolean secure, String keystore, String passphrase,
                            long expirationInterval, HttpServletRequest request,
                            boolean stripped, boolean useCompression) throws
      NavajoException, ClientException {
    String server = message.getMethod(method).getServer();
    if (server.equals("")) {
      throw NavajoFactory.getInstance().createNavajoException(
          "No server found for RPC: " + method);
    }
    if (message == null) {
      throw NavajoFactory.getInstance().createNavajoException(
          "doMethod(): empty Navajo message");
    }
    return doMethod(method, user, password, message, server, secure, keystore,
                    passphrase, expirationInterval,
                    request, stripped, false, useCompression);
  }
  public void doAsyncSend(final Navajo in, final String method,
                          final ResponseListener response,
                          final String responseId) throws ClientException {
    doAsyncSend(in, method, response, responseId, null);
  }
  public void doAsyncSend(final Navajo in, final String method,
                          final ResponseListener response,
                          final ConditionErrorHandler v) throws ClientException {
    doAsyncSend(in, method, response, "", v);
  }
  public void doAsyncSend(final Navajo in, final String method,
                          final ResponseListener response,
                          final String responseId,
                          final ConditionErrorHandler v) throws ClientException {
    Thread t = new Thread(new Runnable() {
      public void run() {
        try {
          Navajo n = null;
          if (v == null) {
            n = doSimpleSend(in, method);
          }
          else {
            n = doSimpleSend(in, method, v);
          }
          if (response != null) {
            response.receive(n, method, responseId);
          }
        }
        catch (ClientException ex) {
          ex.printStackTrace();
          if (response != null) {
            response.setWaiting(false);
            response.handleException(ex);
          }
        }
      }
    });
    t.start();
  }
//  public void doAsyncSend(Navajo in, String method, ResponseListener response, ConditionErrorHandler v) throws ClientException;
//  public void doAsyncSend(Navajo in, String method, ResponseListener response, String responseId, ConditionErrorHandler v) throws ClientException;
  public int getPending() {
    System.err.println("getPending Dummy. This client has no asynchronous calls, so it will always return 0  ");
    return 0;
  }
  public LazyMessage doLazySend(Message request, String service,
                                String responseMsgName, int startIndex,
                                int endIndex) {
    // is this one used?
    throw new UnsupportedOperationException(
        "Lazy message are not yet supported in the implementation!");
  }
//  public LazyMessage doLazySend(Navajo request, String service, String responseMsgName, int startIndex, int endIndex) {
//    throw new UnsupportedOperationException("Lazy message are not yet supported in the implementation!");
//  }
//  public LazyMessage doLazySend(Navajo request, String service, String responseMsgName, int startIndex, int endIndex) throws ClientException;
  public LazyMessage doLazySend(Navajo n, String service,
                                String lazyMessageName, int startIndex,
                                int endIndex) throws ClientException {
//    System.err.println("Entering lazy send: " + service);
//    System.err.println("Entering path: " + lazyMessageName);
//    System.err.println("Start index: " + startIndex);
//    System.err.println("Start end: " + endIndex);
    n.addLazyMessagePath(lazyMessageName, startIndex, endIndex);
    Navajo reply = doSimpleSend(n, service);
//    System.err.println("Navajo returned: ");
//    try {
//      reply.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
    Message m = reply.getMessage(lazyMessageName);
    if (m == null) {
//      System.err.println(n.toXml().toString());
      return null;
    }
    if (!LazyMessage.class.isInstance(m)) {
      System.err.println("No lazy result returned after lazy send!");
      return (LazyMessage) m;
    }
    else {
      LazyMessage l = (LazyMessage) m;
//      System.err.println("My totals are: "+l.getTotal());
      l.setResponseMessageName(lazyMessageName);
      l.setRequest(service, n);
      return l;
    }
  }
  public Navajo createLazyNavajo(Navajo request, String service,
                                 String lazyPath, int startIndex, int endIndex) throws
      ClientException {
    return null;
  }
  public Navajo performLazyUpdate(Navajo request, int startIndex, int endIndex) throws
      ClientException {
    return null;
  }
  public boolean useLazyMessaging() {
    return useLazyMessaging;
  }
  public void setUseLazyMessaging(boolean b) {
    useLazyMessaging = b;
  }
  public Message doSimpleSend(String method, String messagePath) throws
      ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method,
                        messagePath);
  }
  public Message doSimpleSend(Navajo n, String method, String messagePath) throws
      ClientException {
    return doSimpleSend(n, method).getMessage(messagePath);
  }
  public Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v) throws
      ClientException {
    Navajo result = doSimpleSend(n, method);
    checkValidation(result, v);
    return result;
  }
  private void checkValidation(Navajo result, ConditionErrorHandler v) {
    Message conditionErrors = result.getMessage("ConditionErrors");
    if (conditionErrors != null) {
      v.checkValidation(conditionErrors);
    }
  }
  public Navajo doSimpleSend(String method) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method);
  }
  public void setClientProperty(String key, Object value) {
    propertyMap.put(key, value);
  }
  public Object getClientProperty(String key) {
    return propertyMap.get(key);
  }
  public void setErrorHandler(ErrorResponder e) {
    myResponder = e;
  }
  public void displayException(Exception e) {
    if (myResponder != null) {
      myResponder.check(e);
    }
  }
  public void addActivityListener(ActivityListener al) {
    myActivityListeners.add(al);
  }
  public void removeActivityListener(ActivityListener al) {
    myActivityListeners.remove(al);
  }
  protected void fireActivityChanged(boolean b, String service) {
    for (int i = 0; i < myActivityListeners.size(); i++) {
      ActivityListener current = (ActivityListener) myActivityListeners.get(i);
      current.setWaiting(b, service);
    }
  }
  public static void main(String[] args) throws Exception {
    SSLContext ctx = SSLContext.getInstance("TLS");
    // Generate the KeyManager (for client auth to server)
    KeyManager[] km = null;
    // Load the '.keystore' file
    KeyStore ks = KeyStore.getInstance("JKS");
    char[] password = "kl1p_g31t".toCharArray();
    //FileInputStream fis = new FileInputStream
    //    ("/home/arjen/BBFW63X.keystore");
    URL res = NavajoClient.class.getClassLoader().getResource("BBFW63X.keystore");
    InputStream fis = res.openStream();
    ks.load(fis, password);
    // Generate KeyManager from factory and loaded keystore
    KeyManagerFactory kmf = KeyManagerFactory
        .getInstance("SunX509");
    kmf.init(ks, password);
    km = kmf.getKeyManagers();
    TrustManager[] tm = null;
    // Generate TrustManager from factory and keystore
    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(ks);
    tm = tmf.getTrustManagers();
    ctx.init(km, tm, null);
    //Socket s = ctx.getSocketFactory().createSocket("mail.dexels.com", 443);
    //InputStream i = s.getInputStream();
    //int c = i.read();
    //System.err.println("c = " + c);
    //i.close();
    //s.close();
    URL url = new URL("https://mail.dexels.com/sportlink/knvb/");
    HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
    HttpsURLConnection urlcon = (HttpsURLConnection) url.openConnection();
    urlcon.connect();
  }
  public void doServerAsyncSend(Navajo in, String method, ServerAsyncListener listener, String clientId, int pollingInterval) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    ServerAsyncRunner sar = new ServerAsyncRunner(this,in,method,listener,clientId,pollingInterval);
    String serverId = sar.startAsync();
    registerAsyncRunner(serverId, sar);
//    sar.run();
  }

   private void registerAsyncRunner(String id, ServerAsyncRunner sar) {
    asyncRunnerMap.put(id,sar);
  }

  public void deRegisterAsyncRunner(String id) {
    asyncRunnerMap.remove(id);
  }

  private ServerAsyncRunner getAsyncRunner(String id) {
    return (ServerAsyncRunner)asyncRunnerMap.get(id);
  }

  public void finalizeAsyncRunners(){
    try{
      System.err.println(
          "------------------------------------------>> Finalizing asyncrunners....");
      Iterator it = asyncRunnerMap.keySet().iterator();
      while (it.hasNext()) {
        String id = (String) it.next();
        ServerAsyncRunner sar = getAsyncRunner(id);
        sar.killServerAsyncSend();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void killServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    System.err.println("Looking for asyncRunner: "+serverId);
    if (sar!=null) {
      sar.killServerAsyncSend();
    } else {
      System.err.println("Not found!");
    }
  }
  public void pauseServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    if (sar!=null) {
      sar.resumeServerAsyncSend();
    }
  }
  public void resumeServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    if (sar!=null) {
      sar.resumeServerAsyncSend();
    }
  }
}
