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

public interface ClientInterface {

  public Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval) throws ClientException;

  public Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval, boolean useCompression) throws
      ClientException;

  public Navajo doSimpleSend(Navajo out, String method) throws ClientException ;

  public void doAsyncSend(Navajo in, String method,ResponseListener response,String responseId) throws ClientException;

  public void init(String config) throws ClientException ;

  public String getUsername();

  public String getPassword();

  public String getServerUrl();

  public void setUsername(String s);

  public void setPassword(String pw);

  public void setServerUrl(String url);


  }