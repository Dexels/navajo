package com.dexels.navajo.tipi.studio;

import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ResourceListener {
  public void resourceAdded(String id, TipiEvent event);
  public void resourceRemoved(String id, TipiEvent event);
  public void resourceSelected(String id, TipiEvent event);
  public void resourceChanged(String id, TipiEvent event);
}
