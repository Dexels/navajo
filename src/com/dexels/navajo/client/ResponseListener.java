package com.dexels.navajo.client;
import com.dexels.navajo.document.*;

public interface ResponseListener {

  public void receive(Navajo n, String method, String id);
  public void handleException(Exception e);
  public void setWaiting(boolean b);
//  public String getIdentifier();
}
