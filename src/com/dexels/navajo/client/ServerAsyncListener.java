package com.dexels.navajo.client;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ServerAsyncListener {
  public void serviceStarted(String id);
  public void setProgress(String id, int d);
  public void receiveServerAsync(Navajo n, String method, String serverId, String clientId);
  public void handleException(Exception e);
}