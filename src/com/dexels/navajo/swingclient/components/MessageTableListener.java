package com.dexels.navajo.swingclient.components;
import com.dexels.navajo.document.*;

public interface MessageTableListener {
  public void tableLoaded(Message m);
  public void rowInserted(Message m);
  public void rowUpdated(Message m);
  public void rowDeleted(Message m);
  public void rowSelected(Message m);
}