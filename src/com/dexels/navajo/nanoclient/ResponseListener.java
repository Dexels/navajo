package com.dexels.navajo.nanoclient;
import com.dexels.navajo.nanodocument.*;

public interface ResponseListener {

  public void receive(Navajo n, String id);
  public void setWaiting(boolean b);
}