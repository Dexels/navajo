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
public class TipiToolBar
    extends TipiSwingComponentImpl {
  private int orientation = TipiSwingToolBar.HORIZONTAL;
  public void addToContainer(Object c, Object parm2) {
    getSwingContainer().add( (Component) c);
  }

  public void removeFromContainer(Object c) {
    getSwingContainer().remove( (Component) c);
  }

  public Object createContainer() {
    TipiSwingToolBar ts = new TipiSwingToolBar(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return ts;
  }

  private void setOrientation(String o) {
    if ("horizontal".equals(o)) {
      ( (TipiSwingToolBar) getContainer()).setOrientation(TipiSwingToolBar.HORIZONTAL);
    }
    if ("vertical".equals(o)) {
      ( (TipiSwingToolBar) getContainer()).setOrientation(TipiSwingToolBar.VERTICAL);
    }
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if ("orientation".equals(name)) {
      setOrientation( (String) object);
    }
  }
}