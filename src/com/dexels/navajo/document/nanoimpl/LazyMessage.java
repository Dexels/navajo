package com.dexels.navajo.document.nanoimpl;

//import com.dexels.navajo.swingclient.components.lazy.*;
import com.dexels.navajo.document.lazy.*;
import com.dexels.navajo.document.*;
//import com.dexels.navajo.nanodocument.*;

public interface LazyMessage extends Message {
  public void setRequest(String service, Navajo m);
//  public void loadMessage(int t, Message m);
  public Message getMessage(int index);
  public Message getLocalMessage(int index);
  public int getChildMessageCount();
  public void setResponseMessageName(String s);
//  public int getStartIndex();
//  public int getEndIndex();
  public int getShown();
  public int getRemaining();
  public int getTotal();
  public void addMessageListener(MessageListener ml);
  public void startUpdateThread();
  public int getLoadedCount();
}