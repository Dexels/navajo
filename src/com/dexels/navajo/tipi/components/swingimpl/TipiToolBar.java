package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiToolBar
    extends TipiSwingDataComponentImpl {
  private int orientation = TipiSwingToolBar.HORIZONTAL;
  public Object createContainer() {
    TipiSwingToolBar ts = new TipiSwingToolBar(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    ts.setMinimumSize(new Dimension(0,0));
    return ts;
  }

  private void setOrientation(String o) {
    if ("horizontal".equals(o)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingToolBar) getContainer()).setOrientation(TipiSwingToolBar.HORIZONTAL);
        }
      });
    }
    if ("vertical".equals(o)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingToolBar) getContainer()).setOrientation(TipiSwingToolBar.VERTICAL);
        }
      });
    }
  }

  private void setFloatable(Boolean b){
    ( (TipiSwingToolBar) getContainer()).setFloatable(b.booleanValue());
  }

  public void setComponentValue(final String name, final Object object) {
    super.setComponentValue(name, object);
    runSyncInEventThread(new Runnable() {
      public void run() {
        if ("orientation".equals(name)) {
          setOrientation( (String) object);
        }
        if("floatable".equals(name)){
          setFloatable((Boolean) object);
        }
      }
    });
  }
}
