package com.dexels.navajo.client;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import com.dexels.navajo.document.*;
import java.net.URL;

public interface ClientInterface {
  public Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval) throws ClientException;
  public Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval, boolean useCompression) throws
      ClientException;
  public Navajo doSimpleSend(Navajo out, String method) throws ClientException;
  public Message doSimpleSend(Navajo out, String method,String messagePath) throws ClientException;
  public Message doSimpleSend(String method,String messagePath) throws ClientException;
  public Navajo doSimpleSend(String method) throws ClientException;
  public void doAsyncSend(Navajo in, String method, ResponseListener response, String responseId) throws ClientException;
  public void doAsyncSend(Navajo in, String method, ResponseListener response, ConditionErrorHandler v) throws ClientException;
  public void doAsyncSend(Navajo in, String method, ResponseListener response, String responseId, ConditionErrorHandler v) throws ClientException;

  public Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v) throws ClientException;
  public void init(URL config) throws ClientException;
  public String getUsername();
  public String getPassword();
  public String getServerUrl();
  public void setUsername(String s);
  public void setPassword(String pw);
  public void setServerUrl(String url);
  public int getPending();
  public LazyMessage doLazySend(Message request, String service, String responseMsgName, int startIndex, int endIndex) throws ClientException;
  public LazyMessage doLazySend(Navajo request, String service, String responseMsgName, int startIndex, int endIndex) throws ClientException;
  public Navajo createLazyNavajo(Navajo request, String service,String lazyPath, int startIndex, int endIndex) throws ClientException;
  public Navajo performLazyUpdate(Navajo request, int startIndex, int endIndex) throws ClientException;
  public void setClientProperty(String key, Object value);
  public Object getClientProperty(String key);
  public void setErrorHandler(ErrorResponder e);
  public void displayException(Exception e);
  public void setSecure(String keystore, String storepass, boolean useSecurity);
}