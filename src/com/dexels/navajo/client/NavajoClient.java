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
import java.net.*;
import java.security.*;
import java.util.*;
import javax.net.ssl.*;
import javax.servlet.http.*;

import com.dexels.navajo.client.serverasync.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.client.impl.*;

class MyX509TrustManager implements X509TrustManager {
  public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    return null;
  }

  public final void checkClientTrusted(
      java.security.cert.X509Certificate[] certs, String authType) {
  }

  public final void checkServerTrusted(
      java.security.cert.X509Certificate[] certs, String authType) {
  }
}

public  class NavajoClient
    implements ClientInterface {
  public static final int DIRECT_PROTOCOL = 0;
  public static final int HTTP_PROTOCOL = 1;
  private String host = null;
  private String username = null;
  private String password = null;
  private HashMap globalMessages = new HashMap();

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
  private String keystore, passphrase;
  private Map asyncRunnerMap = new HashMap();

  /**
   * Initialize a NavajoClient object with an empty XML message buffer.
   */
  public NavajoClient(String dtdFile) {
    this.DTD_FILE = "file:" + dtdFile;
  }

  public String getClientName() {
    return "http";
  }

  public NavajoClient() {}

  public NavajoClient(int protocol) {
    this.protocol = protocol;
  }

// ADDED:
  public final void init(URL config) throws ClientException {
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

  public final void setUsername(String s) {
    username = s;
  }

  public final void setServerUrl(String url) {
    host = url;
  }

  public final void setPassword(String pw) {
    password = pw;
  }

  public final void addCachedService(String service) {
    cachedServiceNameMap.put(service, service);
  }

  public final void removeCachedService(String service) {
    cachedServiceNameMap.remove(service);
    serviceCache.remove(service);
  }

  public final void clearCache() {
     serviceCache.clear();
  }

  public final void clearCache(String service) {
     serviceCache.remove(service);
  }

  public final void addGlobalMessage(Message m) {
    globalMessages.remove(m.getName());
    globalMessages.put(m.getName(), m);
  }

  public boolean removeGlobalMessage(Message m) {
    return globalMessages.remove(m.getName()) != null;
  }

  public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v, long expirationInterval) throws
       ClientException {
     v.clearConditionErrors();
     Navajo result = doSimpleSend(n, method,expirationInterval);
     checkValidation(result, v);
     return result;
   }


  public final Navajo doSimpleSend(Navajo out, String method) throws ClientException {
    return doSimpleSend(out, method, -1);
  }

  public final Navajo doSimpleUrlSend(URL u, Navajo n) throws ClientException, IOException {
    NavajoHttpUrlConnection hhuc = (NavajoHttpUrlConnection)u.openConnection();
    return hhuc.doTransaction(n);
  }

  public final Navajo doSimpleSend(Navajo out, String method, long expirationInterval) throws ClientException {
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
    return doSimpleSend(out, host, method, username, password, expirationInterval, false);
  }

  public final void setSecure(String keystore, String passphrase, boolean useSecurity) throws ClientException {
    try {
      setSecure(new FileInputStream(new File(keystore)), passphrase, useSecurity);
    } catch (java.io.FileNotFoundException fnfe) {
      fnfe.printStackTrace(System.err);
      throw new ClientException(-1, -1, fnfe.getMessage());
    }
  }

  /**
   *
   * @param keystore InputStream to keystore resource.
   * @param passphrase passphrase to keystore resource.
   * @param useSecurity if true TLS security is enabled.
   * @throws ClientException
   */
  public final void setSecure(InputStream keystore, String passphrase, boolean useSecurity) throws ClientException {
    setSecure = useSecurity;
    System.err.println("------------------------------------------------>>>>>> Calling latest VERSION OF setScure!?");
    if (sslFactory == null) {

               try {
                   SSLContext ctx;
                   KeyManagerFactory kmf;
                   KeyStore ks;
                   char[] passphraseArray = passphrase.toCharArray();
                   ctx = SSLContext.getInstance("TLS");
                   kmf = KeyManagerFactory.getInstance("SunX509");
                   ks = KeyStore.getInstance("JKS");
                   ks.load(keystore, passphraseArray);
                   kmf.init(ks, passphraseArray);
                   ctx.init(kmf.getKeyManagers(), new MyX509TrustManager[]{new MyX509TrustManager()}, null);
                   sslFactory = ctx.getSocketFactory();
               } catch (Exception e) {
                   throw new ClientException(-1, -1, e.getMessage());
               }

               setSecure = useSecurity;
               this.passphrase = passphrase;

    }
  }




  public URLConnection createUrlConnection(URL url) throws IOException{
//    URL url;
//    if (setSecure) {
//      url = new URL("https://" + name);
//    }
//    else {
//      url = new URL("http://" + name);
//    }
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
    return con;
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
      System.err.println("Sending request took: " + tt + " millisec");
    }
    else {
      try {
        d.write(con.getOutputStream());
        long tt = System.currentTimeMillis() - timeStamp;
        System.err.println("Sending request took: " + tt + " millisec");
        timeStamp = System.currentTimeMillis();
      }
      catch (java.net.NoRouteToHostException nrthe) {
        throw new ClientException( -1, 20,
                                  "Could not connect to URI: " + name +
                                  ", check your connection");
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
    System.err.println("Executing script took: " + tt + " millisec");
    timeStamp = System.currentTimeMillis();

    return in;
  }

  public final Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval) throws ClientException {
    return doSimpleSend(out, server, method, user, password, expirationInterval, false);
  }


  //   navajo://frank:aap@192.0.0.1/InitUpdateMember

//  public final Navajo doUrlSend(Navajo out, String url) {
//    URLStreamHandler u;
//  }

  public final Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval,
                             boolean useCompression) throws ClientException {
    // NOTE: prefix persistence key with method, because same Navajo object could be used as a request
    // for multiple methods!
    String cacheKey = "";

    if (cachedServiceNameMap.get(method) != null) {
      cacheKey = method + out.persistenceKey();
      if (serviceCache.get(cacheKey) != null) {
        System.err.println("---------------------------------------------> Returning cached WS");
        return (Navajo) serviceCache.get(cacheKey);
      }
    }
    fireActivityChanged(true, method);
    Header header = out.getHeader();
    if (header == null) {
      header = NavajoFactory.getInstance().createHeader(out, method, user, password, expirationInterval);
      out.addHeader(header);
    }
    else {
      header.setRPCName(method);
      header.setRPCUser(user);
      header.setRPCPassword(password);
      header.setExpirationInterval(expirationInterval);
    }
    // ========= Adding globalMessages
    Iterator entries = globalMessages.entrySet().iterator();
    while (entries.hasNext()) {
      Map.Entry entry = (Map.Entry) entries.next();
      Message global = (Message) entry.getValue();
      try {
        out.addMessage(global);
      }
      catch (Exception e) {
        e.printStackTrace();
        System.err.println("Could not add globals, proceeding");
      }
    }

    try {

      if (protocol == HTTP_PROTOCOL) {
        Header h = out.getHeader();

        //==================================================================

        BufferedInputStream in = doTransaction(server, out, useCompression);
        Navajo n = NavajoFactory.getInstance().createNavajo(in);

        //StringWriter sw = new StringWriter();
        //n.write(sw);
        //System.err.println(method + "("+ Thread.currentThread().toString() +" : " + sw.toString().substring(0,Math.min(sw.toString().length(), 800)));

        //===================================================================
        long tt = System.currentTimeMillis() - timeStamp;
        timeStamp = System.currentTimeMillis();

        if (myResponder != null) {
          myResponder.check(n);
          myResponder.checkForAuthentication(n);
          myResponder.checkForAuthorization(n);
        }
        fireActivityChanged(false, method);

        if (cachedServiceNameMap.get(method) != null) {
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
  protected final Navajo doMethod(String method, String user, String password,
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

  protected final Navajo doMethod(String method, String user, String password,
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
  protected final Navajo doMethod(String method, String user, String password,
                            Navajo message, String server,
                            boolean secure, String keystore, String passphrase,
                            long expirationInterval, HttpServletRequest request,
                            boolean useCompression) throws NavajoException,
      ClientException {
    return doMethod(method, user, password, message, server, secure, keystore,
                    passphrase, expirationInterval,
                    request, false, false, useCompression);
  }

  protected final Navajo doMethod(String method, String user, String password,
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

  public final void doAsyncSend(final Navajo in, final String method,
                          final ResponseListener response,
                          final String responseId) throws ClientException {
    doAsyncSend(in, method, response, responseId, null);
  }

  public final void doAsyncSend(final Navajo in, final String method,
                          final ResponseListener response,
                          final ConditionErrorHandler v) throws ClientException {
    doAsyncSend(in, method, response, "", v);
  }

  public final void doAsyncSend(final Navajo in, final String method,
                          final ResponseListener response,
                          final String responseId,
                          final ConditionErrorHandler v) throws ClientException {
//    System.err.println("Making new asyncsend METHOD: " + method + ", ID: " +
//                       responseId + ", LISTENER: " + response.getIdentifier());
    Thread t = new Thread(new Runnable() {

      final Navajo nc = in;
      final ResponseListener rc = response;
      final String mc = method;
      final String ic = responseId;

      public final void run() {
//        System.err.println("Starting new asyncsend METHOD: " + method +
//                           ", ID: " + responseId + ", LISTENER: " +
//                           response.getIdentifier());
        try {
          final Navajo n;
          if (v == null) {
            n = doSimpleSend(nc, mc);
          }
          else {
            n = doSimpleSend(nc, mc, v);
          }
          StringWriter sw = new StringWriter();
          n.write(sw);
          //System.err.println("ASYNCDSS ("+ Thread.currentThread().toString() +")returned: " + sw.toString().substring(0,Math.min(sw.toString().length(), 800)) + " for mc: " + mc + ", " + method);

          if (response != null) {
            rc.receive(n, mc, ic);
          }
        }
        catch (Throwable ex) {
          ex.printStackTrace();
          if (rc != null) {
            rc.setWaiting(false);
            rc.handleException( (Exception) ex);
          }
        }
      }
    });
//    t.run();
    t.start();
  }

//  public final void doAsyncSend(Navajo in, String method, ResponseListener response, ConditionErrorHandler v) throws ClientException;
//  public final void doAsyncSend(Navajo in, String method, ResponseListener response, String responseId, ConditionErrorHandler v) throws ClientException;
  public final int getPending() {
    System.err.println("getPending Dummy. This client has no asynchronous calls, so it will always return 0  ");
    return 0;
  }

  public final LazyMessage doLazySend(Message request, String service,
                                String responseMsgName, int startIndex,
                                int endIndex, int total) {
    // is this one used?
    throw new UnsupportedOperationException(
        "Lazy message are not yet supported in the implementation!");
  }

//  public LazyMessage doLazySend(Navajo request, String service, String responseMsgName, int startIndex, int endIndex) {
//    throw new UnsupportedOperationException("Lazy message are not yet supported in the implementation!");
//  }
//  public LazyMessage doLazySend(Navajo request, String service, String responseMsgName, int startIndex, int endIndex) throws ClientException;


  public final LazyMessage doLazySend(Navajo n, String service, String lazyMessageName, int startIndex, int endIndex, int total) throws ClientException {
//    System.err.println("Entering lazy send: " + service);
//    System.err.println("Entering path: " + lazyMessageName);
//    System.err.println("Start index: " + startIndex);
//    System.err.println("Start end: " + endIndex);

    n.addLazyMessagePath(lazyMessageName, startIndex, endIndex, total);
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

  public final LazyMessage doLazySend(Navajo n, String service, String lazyMessageName, int startIndex, int endIndex, int total, ConditionErrorHandler v) throws ClientException {
  n.addLazyMessagePath(lazyMessageName, startIndex, endIndex, total);
  Navajo reply = doSimpleSend(n, service, v);
  Message m = reply.getMessage(lazyMessageName);
  if (m == null) {
    return null;
  }
  if (!LazyMessage.class.isInstance(m)) {
    System.err.println("No lazy result returned after lazy send!");
    return (LazyMessage) m;
  }
  else {
    LazyMessage l = (LazyMessage) m;
    l.setResponseMessageName(lazyMessageName);
    l.setRequest(service, n);
    return l;
  }
}


  public final Navajo createLazyNavajo(Navajo request, String service,
                                 String lazyPath, int startIndex, int endIndex) throws
      ClientException {
    return null;
  }

  public final Navajo performLazyUpdate(Navajo request, int startIndex, int endIndex) throws
      ClientException {
    return null;
  }

  public final boolean useLazyMessaging() {
    return useLazyMessaging;
  }

  public final void setUseLazyMessaging(boolean b) {
    useLazyMessaging = b;
  }

  public final Message doSimpleSend(String method, String messagePath) throws
      ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method,
                        messagePath);
  }

  public final Message doSimpleSend(Navajo n, String method, String messagePath) throws
      ClientException {
    return doSimpleSend(n, method).getMessage(messagePath);
  }

  public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v) throws
      ClientException {
    v.clearConditionErrors();
    Navajo result = doSimpleSend(n, method);
    checkValidation(result, v);
    return result;
  }

  private final void checkValidation(Navajo result, ConditionErrorHandler v) {
    Message conditionErrors = result.getMessage("ConditionErrors");
    if (conditionErrors != null) {
      v.checkValidation(conditionErrors);
    }
  }

  public final Navajo doSimpleSend(String method) throws ClientException {
   return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method);
 }

  public final Navajo doSimpleSend(String method, long expirationInterval) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method, expirationInterval);
  }

  public final void setClientProperty(String key, Object value) {
    propertyMap.put(key, value);
  }

  public final Object getClientProperty(String key) {
    return propertyMap.get(key);
  }

  public final ErrorResponder getErrorHandler() {
    return myResponder;
  }

  public final void setErrorHandler(ErrorResponder e) {
    myResponder = e;
  }

  public final void displayException(Exception e) {
    if (myResponder != null) {
      myResponder.check(e);
    }
  }

  public final void addActivityListener(ActivityListener al) {
    myActivityListeners.add(al);
  }

  public final void removeActivityListener(ActivityListener al) {
    myActivityListeners.remove(al);
  }

  protected void fireActivityChanged(boolean b, String service) {
    for (int i = 0; i < myActivityListeners.size(); i++) {
      ActivityListener current = (ActivityListener) myActivityListeners.get(i);
      current.setWaiting(b, service);
    }
  }

  public static void main(String [] args) throws Exception {
    NavajoClient nc = new NavajoClient();
    nc.setSecure("/home/arjen/BBKY84H.keystore", "kl1p_g31t", true);
    Navajo aap = nc.doSimpleSend(NavajoFactory.getInstance().createNavajo(), "slwebsvr2.sportlink.enovation.net:10443/sportlink/knvb/servlet/Postman", "InitExternalInsertMember", "BBKY84H", "", -1);
  }


  public final void doServerAsyncSend(Navajo in, String method,
                                      ServerAsyncListener listener, String clientId,
                                      int pollingInterval) throws ClientException {
    ServerAsyncRunner sar = new ServerAsyncRunner(this, in, method, listener,clientId, pollingInterval);
    String serverId = sar.startAsync();
    registerAsyncRunner(serverId, sar);

  }

  private final void registerAsyncRunner(String id, ServerAsyncRunner sar) {
    asyncRunnerMap.put(id, sar);
  }

  public final void deRegisterAsyncRunner(String id) {
    asyncRunnerMap.remove(id);
  }

  private final ServerAsyncRunner getAsyncRunner(String id) {
    return (ServerAsyncRunner) asyncRunnerMap.get(id);
  }

  public final void finalizeAsyncRunners() {
    try {
      System.err.println(
          "------------------------------------------>> Finalizing asyncrunners....");
      Iterator it = asyncRunnerMap.keySet().iterator();
      while (it.hasNext()) {
        String id = (String) it.next();
        ServerAsyncRunner sar = getAsyncRunner(id);
        sar.killServerAsyncSend();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void killServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    System.err.println("Looking for asyncRunner: " + serverId);
    if (sar != null) {
      sar.killServerAsyncSend();
    }
    else {
      System.err.println("Not found!");
    }
  }

  public final void pauseServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    if (sar != null) {
      sar.resumeServerAsyncSend();
    }
  }

  public final void resumeServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    if (sar != null) {
      sar.resumeServerAsyncSend();
    }
  }


}
