package com.dexels.navajo.client;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ActivityListener {
  public void setWaiting(boolean b, String service, int asyncQueueSize, int activeThreadCount, long millis);
}
