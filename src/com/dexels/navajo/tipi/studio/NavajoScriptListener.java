package com.dexels.navajo.tipi.studio;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface NavajoScriptListener {
  public void scriptCreated(String name);
  public void scriptLoaded(String name);
  public void scriptDeleted(String name);
  public void scriptCommitted(String name);
  public void scriptSelected(String name);

}
