package com.dexels.navajo.nanoclient;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;

public interface ResponseListener {

  public void receive(Navajo n, String id);
  public void setWaiting(boolean b);
}