package com.dexels.navajo.nanoclient;
import com.dexels.navajo.document.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.nanoimpl.*;

public class NavajoAsyncRunner extends Thread {

  private Navajo myCommand;
  private String myService;
//  private NavajoClient myClient;
  private ResponseListener myCallback = null;
  private String callBackId = null;

//  public NavajoAsyncRunner(Navajo n, String service, ThreadGroup tg) {
//    super(tg,"navajo_request");
//    myCommand = n;
//    myService = service;
//  }
//
//  public NavajoAsyncRunner(Navajo n, String service, ThreadGroup tg, ResponseListener callback) {
//    this(n,service,tg);
//    myCallback = callback;
//  }

  public NavajoAsyncRunner(Navajo n, String service, ThreadGroup tg, ResponseListener callback, String id) {
//    this(n,service,tg,callback);
    super(tg,"navajo_request");
    myCommand = n;
    myService = service;
    myCallback = callback;
    callBackId = id;
  }

  public void run() {
    if (myCallback!=null) {
      myCallback.setWaiting(true);
    }
    try {
      Navajo response = NavajoClient.getInstance().doSimpleSend(myCommand,myService);
      if (myCallback!=null) {
        myCallback.receive(response,callBackId);
        myCallback.setWaiting(super.getThreadGroup().activeCount()>2);
      } else {
        System.err.println("No callback, discarding response!");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    NavajoClient.getInstance().removeRunner(this);
  }
}