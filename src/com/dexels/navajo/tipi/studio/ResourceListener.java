package com.dexels.navajo.tipi.studio;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ResourceListener {
  public void resourceAdded(String id);
  public void resourceRemoved(String id);
  public void resourceSelected(String id);
  public void resourceChanged(String id);
}
