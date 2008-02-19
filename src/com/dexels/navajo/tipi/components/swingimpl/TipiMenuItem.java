package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMenuItem
    extends TipiSwingComponentImpl {
  private TipiSwingMenuItem myItem;

  private boolean iAmEnabled = true;


  public Object createContainer() {
    myItem = new TipiSwingMenuItem();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myItem;
  }

  /**
   * Sort of legacy. Don't really know what to do with this one.
   */
   public void eventStarted(TipiExecutable te, Object event) {
    if (Container.class.isInstance(getContainer())) {
      runSyncInEventThread(new Runnable() {
        public void run() {
//          enabled = ( (Container) getContainer()).isEnabled();
          getSwingContainer().setEnabled(false);
        }
      });
    }
  }
  public void eventFinished(TipiExecutable te, Object event) {
    if (Container.class.isInstance(getContainer())) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (Container) getContainer()).setEnabled(iAmEnabled);
        }
      });
    }
  }
}
