package com.dexels.navajo.client.impl;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.client.*;
import java.net.URL;
import java.util.*;

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
//   public DirectNavajoClient(String configurationPath) throws NavajoException {
//     dispatcher = new Dispatcher(configurationPath);
//   }
  public synchronized int getPending() {
    return myRunner.getPending();
  }

  public Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval) throws ClientException {
    return doSimpleSend(out, server, method, user, password, expirationInterval, false);
  }

  public Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval, boolean useCompression) throws
      ClientException {
    Navajo reply = null;
    try {
      Header header = NavajoFactory.getInstance().createHeader(out, method, user, password, expirationInterval);
      out.addHeader(header);
      reply = dispatcher.handle(out);
      if (myErrorResponder!=null) {
        myErrorResponder.check(reply);
      }
    }
    catch (FatalException ex) {
      ex.printStackTrace();
      return null;
    }
    return reply;
  }

  public void setClientProperty(String key, Object value){
    propertyMap.put(key,value);
  }
  public Object getClientProperty(String key) {
    return propertyMap.get(key);
  }

  public Navajo doSimpleSend(Navajo n, String service) throws ClientException {
    return doSimpleSend(n, "", service, "", "", -1, false);
  }

  public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler v) throws ClientException {
    throw new UnsupportedOperationException("doSimpleSend with ConditionErrorHandler not implemented in direct version");
  }

  public Message doSimpleSend(Navajo n, String service, String messagePath) throws ClientException {
    return doSimpleSend(n,service).getMessage(messagePath);
  }

  public void init(URL config) throws ClientException {
    try {

      dispatcher = new Dispatcher(config, new com.dexels.navajo.server.ClassloaderInputStreamReader());
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

  public void setUsername(String s) {
    throw new UnsupportedOperationException("No need to set username in DirectClient!");
  }

  public void setServerUrl(String url) {
    throw new UnsupportedOperationException("No need to set server url in DirectClient!");
  }

  public void setPassword(String pw) {
    throw new UnsupportedOperationException("No need to set password in DirectClient!");
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          String responseId) throws ClientException {
    myRunner.enqueueRequest(in, method, response, responseId);
  }
  public LazyMessage doLazySend(Message request, String service, String responseMsgName, int startIndex, int endIndex) {
    throw new UnsupportedOperationException("Lazy message are not supported in the direct implementation!");
  }
  public LazyMessage doLazySend(Navajo request, String service, String responseMsgName, int startIndex, int endIndex) {
    throw new UnsupportedOperationException("Lazy message are not yet supported in the implementation!");
  }
  public Navajo createLazyNavajo(Navajo request, String service,String lazyPath, int startIndex, int endIndex) throws ClientException {
    throw new UnsupportedOperationException("Lazy message are not supported in the direct implementation!");
  }

  public Navajo performLazyUpdate(Navajo request, int startIndex, int endIndex) throws ClientException {
    throw new UnsupportedOperationException("Lazy message are not supported in the direct implementation!");
  }


  public Message doSimpleSend(String method,String messagePath) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(),messagePath).getMessage(messagePath);
  }
  public Navajo doSimpleSend(String method) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(),method);
  }
  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                           ConditionErrorHandler v) throws ClientException {
     throw new UnsupportedOperationException("doAsync not implemented in direct version");
  }
  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          String responseId, ConditionErrorHandler v) throws ClientException {
    throw new UnsupportedOperationException("doAsync not implemented in direct version");
  }
  public void setErrorHandler(ErrorResponder e) {
    myErrorResponder = e;
  }
  public void displayException(Exception e) {
    if (myErrorResponder!=null) {
      myErrorResponder.check(e);
    }

  }

}
