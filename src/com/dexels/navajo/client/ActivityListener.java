package com.dexels.navajo.client;

import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ActivityListener extends Serializable {
  public void setWaiting(boolean b, String service, int asyncQueueSize, int activeThreadCount, long millis);
}
