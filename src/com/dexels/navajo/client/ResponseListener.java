package com.dexels.navajo.client;
import com.dexels.navajo.document.*;

/**
 * <p>Title: ResponseListener</p>
 * <p>Description: The responselistener is used for calling asynchronous webservices</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */
public interface ResponseListener {
  public void receive(Navajo n, String method, String id);
  public void handleException(Exception e);
  public void setWaiting(boolean b);
//  public String getIdentifier();
}
