package com.dexels.navajo.client.impl;

import com.dexels.navajo.document.*;
import java.util.ArrayList;
import com.dexels.navajo.client.*;

//import com.dexels.sportlink.client.swing.*;
//import com.dexels.navajo.swingclient.components.*;

public class NavajoAsyncRunner
    extends Thread {

  private Navajo myCommand;
  private String myService;
//  private NavajoClient myClient;
//  private ResponseListener myCallback = null;
  private String callBackId = null;
  private ClientInterface myClient;
  private boolean alive = true;
  private ArrayList pending = new ArrayList();

  public NavajoAsyncRunner(ClientInterface c) {
    myClient = c;
  }

  public void enqueueRequest(Navajo n, String service,
                             ResponseListener callback, String id) {
    QueueEntry qe = new QueueEntry(n, service, callback, id);
    pending.add(qe);
    interrupt();
  }

  private void performCall(Navajo n, String method, ResponseListener res,
                           String id) {
    try {
      Navajo reply = myClient.doSimpleSend(n, method);
      res.receive(reply, method, id);
    }
    catch (ClientException ex) {
      ex.printStackTrace();
    }
  }

  public void kill() {
    System.err.println(
        "Killing the client might be unwise, as it can never be revived again.");
    alive = false;
    interrupt();
  }

  public int getPending() {
    return pending.size();
  }

  public void run() {
    while (alive) {
      System.err.println("Running...");
      while (pending.size() > 0) {
        System.err.println("Serving, "+pending.size()+" calls in queue..");
        System.err.println("Entry found!");
        QueueEntry qe = (QueueEntry) pending.get(0);
        pending.remove(0);
        performCall(qe.getNavajo(), qe.getMethod(), qe.getResponseListener(),
                    qe.getResponseId());
      }

      try {
        sleep(10000);
      }
      catch (InterruptedException ex1) {
        System.err.println("Woken up!");
      }
    }
  }
}