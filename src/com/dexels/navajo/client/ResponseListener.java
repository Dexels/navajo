package com.dexels.navajo.client;
import com.dexels.navajo.document.*;

public interface ResponseListener {

  public void receive(Navajo n, String id);
  public void setWaiting(boolean b);
}
