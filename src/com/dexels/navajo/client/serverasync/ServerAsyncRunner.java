package com.dexels.navajo.client.serverasync;

import com.dexels.navajo.document.*;
import com.dexels.navajo.client.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class ServerAsyncRunner
    extends Thread {
  private ClientInterface myClientInterface = null;
  private Navajo myNavajo = null;
  private Navajo myResult = null;
  private String myMethod = null;
  private ServerAsyncListener myListener = null;
  private String myClientId = null;
  private int myPollingInterval = -1;
  private int maxIter = 2;
  private boolean iterate = true;

  public ServerAsyncRunner(ClientInterface client, Navajo in, String method,
                           ServerAsyncListener listener, String clientId,
                           int pollingInterval) {
    myClientInterface = client;
    myNavajo = in;
    myMethod = method;
    myListener = listener;
    myClientId = clientId;
    myPollingInterval = pollingInterval;
  }

  private synchronized Navajo doSimpleSend(Navajo n, String method) throws ClientException {
   return myClientInterface.doSimpleSend(n, method);
  }

//  private synchronized void setResultNavajo(Navajo n) {
//    myResult = n;
//  }
  public void run() {
//    Navajo result = null;
//    int i = 0;
    try {
      while (isIterating()) {
        if (myPollingInterval > 0) {
          Navajo temp = doSimpleSend(myNavajo,myMethod);
          Header head = temp.getHeader();
          if (head == null) {
            System.err.println(
                "Received no header. returning and killing thread");
            throw new ClientException( -1, -1, "No async header!");
          }
          if (isFinished(temp)) {
            // Really dont know what I should pass to getCallBackPointer
            myListener.receiveServerAsync(temp, myMethod,
                                          head.getCallBackPointer(null),
                                          myClientId);
            myClientInterface.deRegisterAsyncRunner(myClientId);
            return;
          }
          else {
            if (myListener != null) {
              myListener.setProgress(head.getCallBackPointer(null),
                                     head.getCallBackProgress());
            }
          }
          System.err.println("Start sleep");
          try {
            sleep(myPollingInterval);
          }
          catch (InterruptedException ex1) {
            System.err.println("Interrupted. Continuing with next iteration.");
          }
          System.err.println("End sleep");
        }
      }
    }
    catch (ClientException ex) {
      System.err.println("oooops");
      myListener.handleException(ex);
      return;
    }
    if (myListener != null) {
       myListener.handleException(new ClientException(-1,-1,"Operation aborted"));
     }
  }


  private synchronized boolean isIterating() {
    return iterate;
  }

  private synchronized void setIterating(boolean b) {
    iterate = b;
  }

  public String startAsync() throws ClientException {
    myResult = myClientInterface.doSimpleSend(myNavajo, myMethod);
    Header resultHead = myResult.getHeader();
    if (resultHead != null) {
      if (myListener != null) {
        myListener.serviceStarted(resultHead.getCallBackPointer(null));
        start();
        return resultHead.getCallBackPointer(null);
      }
    }
    else {
      System.err.println("Received no header. returning and killing thread");
      throw new ClientException( -1, -1, "No async header!");
    }
    return null;
  }
  private boolean isFinished(Navajo n) {
    Header h = n.getHeader();
    if (h == null) {
      return true;
    }
    return h.isCallBackFinished();
  }
  private double getProgress(Navajo n) {
    Header h = n.getHeader();
    if (h == null) {
      return -1;
    }
    return h.getCallBackProgress();
  }

  public synchronized void killServerAsyncSend() throws ClientException {
    myResult.getHeader().setCallBackInterrupt("kill");
    setIterating(false);
    interrupt();
  }
  public synchronized void pauseServerAsyncSend() throws ClientException {
  }
  public synchronized void resumeServerAsyncSend() throws ClientException {
  }


}