package com.dexels.navajo.client.impl;

import com.dexels.navajo.document.*;
import java.util.ArrayList;
import com.dexels.navajo.client.*;

//import com.dexels.sportlink.client.swing.*;
//import com.dexels.navajo.swingclient.components.*;
public class NavajoAsyncRunner
    extends Thread {
//  private Navajo myCommand;
//  private String myService;
//  private NavajoClient myClient;
//  private ResponseListener myCallback = null;
//  private String callBackId = null;
  private ClientInterface myClient;
  private boolean alive = true;
  private ArrayList pending = new ArrayList();
  public NavajoAsyncRunner(ClientInterface c) {
    myClient = c;
  }


  public synchronized void enqueueRequest(Navajo n, String service,
                                          ResponseListener callback, String id) {
    QueueEntry qe = new QueueEntry(n, service, callback, id);
    pending.add(qe);
    interrupt();
  }

  public synchronized void enqueueRequest(Navajo n, String service,
                                          ResponseListener callback, ConditionErrorHandler h) {
    QueueEntry qe = new QueueEntry(n, service, callback, h);
    pending.add(qe);
    interrupt();
  }

  public synchronized void enqueueRequest(Navajo n, String service,
                                          ResponseListener callback, String responseId, ConditionErrorHandler h) {
    QueueEntry qe = new QueueEntry(n, service, callback, responseId, h);
    pending.add(qe);
    interrupt();
  }



  private synchronized void performCall(Navajo n, String method,
                                        ResponseListener res,
                                        String id) {
    try {
      Navajo reply = myClient.doSimpleSend(n, method);
//      System.err.println("RECEIVING NAVAJO: ");
//      try {
//        n.write(System.err);
//      }
//      catch (NavajoException ex1) {
//        ex1.printStackTrace();
//      }
      res.receive(reply, method, id);
    }
    catch (ClientException ex) {
      res.handleException(ex);
      ex.printStackTrace();
    }
  }

  private synchronized void performCall(Navajo n, String method,
                                        ResponseListener res,
                                        ConditionErrorHandler h) {
    try {
      Navajo reply = myClient.doSimpleSend(n, method, h);
      res.receive(reply, method, "");
    }
    catch (ClientException ex) {
      res.handleException(ex);
      ex.printStackTrace();
    }
  }

  private synchronized void performCall(Navajo n, String method, ResponseListener res,String responseId, ConditionErrorHandler h) {
    try {
      Navajo reply = myClient.doSimpleSend(n, method, h);
      res.receive(reply, method, responseId);
    }
    catch (ClientException ex) {
      res.handleException(ex);
      ex.printStackTrace();
    }
  }



  public void kill() {
    System.err.println(
        "Killing the client might be unwise, as it can never be revived again.");
    alive = false;
    interrupt();
  }

  public synchronized int getPending() {
    return pending.size();
  }

  private synchronized QueueEntry serveQueueEntry() {
    QueueEntry qe = (QueueEntry) pending.get(0);
    pending.remove(0);
    return qe;
  }

  public void run() {
    while (alive) {
//      System.err.println("Running...");
      while (pending.size() > 0) {
//        System.err.println("Serving, " + pending.size() + " calls in queue..");
//        for (int i = 0; i < pending.size(); i++) {
//          QueueEntry current = (QueueEntry)pending.get(i);
//          System.err.println("Call# "+i+current.getMethod());
//        }

//        System.err.println("Entry found!");
        QueueEntry qe = serveQueueEntry();

        // Be very careful
        if(qe.getResponseId() != null && qe.getConditionErrorHandler() == null){
          performCall(qe.getNavajo(), qe.getMethod(), qe.getResponseListener(),  qe.getResponseId());
        }else if(qe.getResponseId() != null && qe.getConditionErrorHandler() != null){
          performCall(qe.getNavajo(), qe.getMethod(), qe.getResponseListener(),  qe.getResponseId(), qe.getConditionErrorHandler());
        }else{
          performCall(qe.getNavajo(), qe.getMethod(), qe.getResponseListener(),  qe.getConditionErrorHandler());
        }

      }
      try {
        sleep(10000);
      }
      catch (InterruptedException ex1) {
      }
    }
  }
}