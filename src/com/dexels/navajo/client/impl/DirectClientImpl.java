package com.dexels.navajo.client.impl;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.client.*;
import java.net.URL;

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

  private Dispatcher dispatcher;
//   public DirectNavajoClient(String configurationPath) throws NavajoException {
//     dispatcher = new Dispatcher(configurationPath);
//   }
  public int getPending() {
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
//     System.err.println("SEnDING: ");
//    try {
//      out.write(System.out);
//    }
//    catch (NavajoException ex1) {
//      ex1.printStackTrace();
//    }
    try {
      Header header = NavajoFactory.getInstance().createHeader(out, method,
          user, password, expirationInterval);
      out.addHeader(header);
      reply = dispatcher.handle(out);
    }
    catch (FatalException ex) {
      ex.printStackTrace();
      return null;
    }
//     System.err.println("RECEIVED: ");
//    try {
//      out.write(System.out);
//    }
//    catch (NavajoException ex1) {
//      ex1.printStackTrace();
//    }
    return reply;
  }

  public Navajo doSimpleSend(Navajo n, String service) throws ClientException {
    return doSimpleSend(n, "", service, "", "", -1, false);
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
  }

  public void setServerUrl(String url) {
  }

  public void setPassword(String pw) {
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          String responseId) throws ClientException {
    myRunner.enqueueRequest(in, method, response, responseId);
    System.err.println("Async returning");
//    Navajo n = doSimpleSend(in,method);
//    na.start();
//    System.err.println("Finished starting async send");
//    response.receive(n,responseId);
  }
}