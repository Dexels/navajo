package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SwingTipiContext extends TipiContext {

  private TipiSwingSplash splash;


  public SwingTipiContext() {
    setDefaultTopLevel(new TipiScreen());
    getDefaultTopLevel().setContext(this);

  }

  public synchronized void setWaiting(boolean b) {
    for (int i = 0; i < rootPaneList.size(); i++) {
      TipiComponent tc = (TipiComponent) rootPaneList.get(i);
      ( (Container) tc.getContainer()).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
    }
    for (int j = 0; j < myActivityListeners.size(); j++) {
      TipiActivityListener tal = (TipiActivityListener) myActivityListeners.get(j);
      tal.setActive(b);
    }
  }
  public void clearTopScreen() {
    ((TipiScreen)topScreen).clearTopScreen();
  }
  public void setSplashInfo(String info) {
    if (splash != null) {
      splash.setInfoText(info);
    }
  }

  public void setSplashVisible(boolean b) {
    if (splash!=null) {
      splash.setVisible(b);
    }
  }
  public void setSplash(Object s) {
    splash = (TipiSwingSplash)s;
  }

}
