package com.dexels.navajo.tipi;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiEventListener {
  public boolean performTipiEvent(String eventtype, Object source) throws TipiException;
}