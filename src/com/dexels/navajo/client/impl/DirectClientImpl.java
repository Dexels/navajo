package com.dexels.navajo.client.impl;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.client.*;
import java.net.URL;
import java.util.*;
import java.io.InputStream;
import com.dexels.navajo.server.Dispatcher;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DirectClientImpl
    implements ClientInterface {
//  private ThreadGroup myThreadGroup = new ThreadGroup("navajothreads");
  private NavajoAsyncRunner myRunner;
  public DirectClientImpl() {
    myRunner = new NavajoAsyncRunner(this);
    myRunner.start();
  }

  private Map propertyMap = new HashMap();
  private Dispatcher dispatcher;
  private ErrorResponder myErrorResponder;
  private ArrayList myActivityListeners = new ArrayList();
  private Map cachedServicesNameMap = new HashMap();
  private Map serviceCache = new HashMap();
  private Map globalMessages = new HashMap();
//   public DirectNavajoClient(String configurationPath) throws NavajoException {
//     dispatcher = new Dispatcher(configurationPath);
//   }

  public void finalizeAsyncRunners(){
   // hiep hoi..
  }

  public synchronized int getPending() {
    return myRunner.getPending();
  }

  public void addCachedService(String service){
    cachedServicesNameMap.put(service, service);
  }

  public void removeCachedService(String service){
    cachedServicesNameMap.remove(service);
    serviceCache.remove(service);
  }

  public final void clearCache() {
    serviceCache.clear();
 }

 public final void clearCache(String service) {
    serviceCache.remove(service);
 }

  public final Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval) throws ClientException {
    return doSimpleSend(out, server, method, user, password, expirationInterval, false);
  }

  public final Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval, boolean useCompression) throws ClientException {
    fireActivityChanged(true, method);
    String cacheKey = out.persistenceKey();
    Navajo reply = (Navajo)serviceCache.get(cacheKey);
    if(reply != null && cachedServicesNameMap.get(method) != null){
        System.err.println("Returning cached WS from DirectClient");
        return reply;
      }
    try {

      this.setDocumentGlobals(out);

      Header header = NavajoFactory.getInstance().createHeader(out, method,
          user, password, expirationInterval);
      out.addHeader(header);

    // ========= Adding globalMessages
    Iterator entries = globalMessages.entrySet().iterator();
    while(entries.hasNext()){
      Map.Entry entry = (Map.Entry)entries.next();
      Message global = (Message)entry.getValue();
      try{
        out.addMessage(global);
      }catch(Exception e){
         e.printStackTrace();
         System.err.println("Could not add globals, proceeding");
      }
    }

      reply = dispatcher.handle(out);
      if (myErrorResponder != null) {
        myErrorResponder.check(reply);
      }
    }
    catch (FatalException ex) {
      ex.printStackTrace();
      fireActivityChanged(false, method);
      return null;
    }
    fireActivityChanged(false, method);
    if(cachedServicesNameMap.get(method) != null){
      serviceCache.put(cacheKey, reply);
    }
    return reply;
  }

  private void setDocumentGlobals(final Navajo doc) throws ClientException {

    try {
      final Message paramMsg = NavajoFactory.getInstance().createMessage(
          doc, this.GLOBALSNAME);
      doc.addMessage(paramMsg);
      final Properties props = System.getProperties();
      final Set keys = props.keySet();
      final Iterator iter = keys.iterator();
      while (iter.hasNext()) {
        final String name = (String) iter.next();
        if (name.startsWith(this.GLOBALSPREFIX)) {
          final String propName = name.substring(this.GLOBALSPREFIX.length());
          final String value = (String) props.get(name);
          final Property p = NavajoFactory.getInstance().createProperty(doc,
              propName, Property.STRING_PROPERTY,
              value, value.length(), "",
              Property.DIR_IN);
          paramMsg.addProperty(p);
        }
      }
    }
    catch (NavajoException ex) {
      throw new ClientException(99, 97, ex.toString());
    }

  }

  public void setSecure(String keystore, String storepass, boolean useSecurity) {
    // Well waddujaknow,..
  }

  public void setSecure(InputStream keystore, String storepass,
                        boolean useSecurity) {
    // Well waddujaknow,..
  }

  public void setClientProperty(String key, Object value) {
    propertyMap.put(key, value);
  }

  public Object getClientProperty(String key) {
    return propertyMap.get(key);
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

  public final Navajo doSimpleSend(Navajo n, String service, long expirationInterval) throws ClientException {
    return doSimpleSend(n, "", service, "", "", expirationInterval, false);
  }


  public final Navajo doSimpleSend(Navajo n, String service) throws ClientException {
    return doSimpleSend(n, "", service, "", "", -1, false);
  }

  public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v) throws
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

  public Message doSimpleSend(Navajo n, String service, String messagePath) throws
      ClientException {
    return doSimpleSend(n, service).getMessage(messagePath);
  }

  public void init(URL config) throws ClientException {
    try {

      dispatcher = new Dispatcher(config,
                                  new com.dexels.navajo.server.
                                  ClassloaderInputStreamReader());
      dispatcher.setUseAuthorisation(false);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
      throw new ClientException(1, 1, ex.getMessage());
    }
  }

  public String getUsername() {
    return "no_user";
  }

  public String getPassword() {
    return "no_password";
  }

  public String getServerUrl() {
    return "directclient";
  }

  public void addGlobalMessage(Message m){
    globalMessages.remove(m.getName());
    globalMessages.put(m.getName(), m);
  }

  public boolean removeGlobalMessage(Message m){
    return globalMessages.remove(m.getName()) != null;
  }

  public void setUsername(String s) {
    throw new UnsupportedOperationException(
        "No need to set username in DirectClient!");
  }

  public void setServerUrl(String url) {
    throw new UnsupportedOperationException(
        "No need to set server url in DirectClient!");
  }

  public void setPassword(String pw) {
    throw new UnsupportedOperationException(
        "No need to set password in DirectClient!");
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          String responseId) throws ClientException {
    myRunner.enqueueRequest(in, method, response, responseId);
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          ConditionErrorHandler v) throws ClientException {
    myRunner.enqueueRequest(in, method, response, v);
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          String responseId, ConditionErrorHandler v) throws
      ClientException {
    myRunner.enqueueRequest(in, method, response, responseId, v);
  }

  public LazyMessage doLazySend(Message request, String service,
                                String responseMsgName, int startIndex,
                                int endIndex, int total) {
    throw new UnsupportedOperationException(
        "Lazy message are not supported in the direct implementation!");
  }

  public LazyMessage doLazySend(Navajo request, String service,
                                String responseMsgName, int startIndex,
                                int endIndex, int total) {
    throw new UnsupportedOperationException(
        "Lazy message are not yet supported in the implementation!");
  }

  public Navajo createLazyNavajo(Navajo request, String service,
                                 String lazyPath, int startIndex, int endIndex) throws
      ClientException {
    throw new UnsupportedOperationException(
        "Lazy message are not supported in the direct implementation!");
  }

  public Navajo performLazyUpdate(Navajo request, int startIndex, int endIndex) throws
      ClientException {
    throw new UnsupportedOperationException(
        "Lazy message are not supported in the direct implementation!");
  }

  public Message doSimpleSend(String method, String messagePath) throws
      ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), messagePath).
        getMessage(messagePath);
  }

  public final Navajo doSimpleSend(String method, long expirationInterval) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method, expirationInterval);
  }

  public final Navajo doSimpleSend(String method) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method);
  }

  public void setErrorHandler(ErrorResponder e) {
    myErrorResponder = e;
  }

  public void displayException(Exception e) {
    if (myErrorResponder != null) {
      myErrorResponder.check(e);
    }

  }
  public void doServerAsyncSend(Navajo in, String method, ServerAsyncListener listener, String clientId, int pollingInterval) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method doServerAsyncSend() not yet implemented.");
  }
  public void killServerAsyncSend(String serverId) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method killServerAsyncSend() not yet implemented.");
  }
  public void pauseServerAsyncSend(String serverId) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method pauseServerAsyncSend() not yet implemented.");
  }
  public void resumeServerAsyncSend(String serverId) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method resumeServerAsyncSend() not yet implemented.");
  }
  public void deRegisterAsyncRunner(String id) {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method resumeServerAsyncSend() not yet implemented.");
  }

}
