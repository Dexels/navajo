package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import javax.swing.event.*;
import javax.swing.SwingUtilities;

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
  }


  public boolean isActive() {
    return amIActive;
  }

  public void setActive(final boolean state) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        amIActive = state;
        setComponentValue("indeterminate", new Boolean(amIActive));
        if (amIActive) {
          setComponentValue("text", "busy");
        }
        else {
          setComponentValue("text", "ready");
        }
      }
    });
  }

  /**
   * createContainer
   *
   * @return Object
   * @todo Implement this
   *   com.dexels.navajo.tipi.components.core.TipiComponentImpl method
   */
  public Object createContainer() {
    Object o = super.createContainer();
    myContext.addTipiActivityListener(this);
    return o;
  }
}
