package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiActivityBar
    extends TipiProgressBar
    implements TipiActivityListener {
  private boolean amIActive = false;
  public TipiActivityBar() {
    TipiContext.getInstance().addTipiActivityListener(this);
  }

  public boolean isActive() {
    return amIActive;
  }

  public void setActive(boolean state) {
    amIActive = state;
    setComponentValue("indeterminate", new Boolean(amIActive));
    if (amIActive) {
      setComponentValue("text", "busy");
    }
    else {
      setComponentValue("text", "ready");
    }
  }
}