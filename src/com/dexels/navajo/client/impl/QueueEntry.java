package com.dexels.navajo.client.impl;
import com.dexels.navajo.document.*;
import java.util.*;
import com.dexels.navajo.client.ResponseListener;
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
  public QueueEntry(Navajo n, String method, ResponseListener res, String responseId) {
    myNavajo = n;
    myMethod = method;
    myResponseListener = res;
    myResponseId = responseId;
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
