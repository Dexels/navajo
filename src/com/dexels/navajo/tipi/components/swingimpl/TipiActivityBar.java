package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;

import javax.swing.*;

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
    extends TipiLabel
    implements TipiActivityListener {
  private boolean amIActive = false;
  private ImageIcon busyIcon = null;
  private ImageIcon freeIcon = null;
  public TipiActivityBar() {
  }

  public boolean isActive() {
    return amIActive;
  }

  public void setActive(final boolean state) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        amIActive = state;
//        setComponentValue("indeterminate", new Boolean(amIActive));
        if (amIActive) {
          ( (JLabel) getSwingContainer()).setIcon(busyIcon);
        }
        else {
          ( (JLabel) getSwingContainer()).setIcon(freeIcon);
        }
      }
    });
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("freeicon")) {
      freeIcon = getIcon( (URL) object);
    }
    if (name.equals("busyicon")) {
      busyIcon = getIcon( (URL) object);
    }
  }

  public void setActiveThreads(int i) {
    setComponentValue("text", "Active operations: " + i);
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
