package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface TipiEventListener {
  public boolean performTipiEvent(String eventtype, Object source, boolean sync) throws TipiException;
  public void eventStarted(TipiEvent te, Object event);
  public void eventFinished(TipiEvent te, Object event);
}