package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface PropertyComponent {
  public String getPropertyName();

  public void addTipiEventListener(TipiEventListener listener);

  public void addTipiEvent(TipiEvent te);
}