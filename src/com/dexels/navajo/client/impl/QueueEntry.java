package com.dexels.navajo.client.impl;
import com.dexels.navajo.document.*;

import com.dexels.navajo.client.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class QueueEntry {
  private Navajo myNavajo = null;
  private String myMethod = null;
  private ResponseListener myResponseListener = null;
  private String myResponseId;
  private ConditionErrorHandler myHandler;

  public QueueEntry(Navajo n, String method, ResponseListener res, String responseId) {
    myNavajo = n;
    myMethod = method;
    myResponseListener = res;
    myResponseId = responseId;
  }

  public QueueEntry(Navajo n, String method, ResponseListener res, String responseId, ConditionErrorHandler h) {
    myNavajo = n;
    myMethod = method;
    myResponseListener = res;
    myResponseId = responseId;
    myHandler = h;
  }


  public QueueEntry(Navajo n, String method, ResponseListener res, ConditionErrorHandler h) {
    myNavajo = n;
    myMethod = method;
    myResponseListener = res;
    myHandler = h;
  }

  public ConditionErrorHandler getConditionErrorHandler(){
    return myHandler;
  }

  public String getMethod() {
    return myMethod;
  }

  public Navajo getNavajo() {
    return myNavajo;
  }

  public ResponseListener getResponseListener() {
    return myResponseListener;
  }

  public String getResponseId() {
    return myResponseId;
  }
}
