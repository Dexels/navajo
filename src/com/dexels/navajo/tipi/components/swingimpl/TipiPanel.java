package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiPanel
    extends TipiSwingDataComponentImpl {
  public Container createContainer() {
    TipiSwingPanel tsp = new TipiSwingPanel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return tsp;
  }

  public void addToContainer(Object c, Object constraints) {
    getContainer().add((Component)c, constraints);
  }

  public void removeFromContainer(Object c) {
    getContainer().remove((Component)c);
  }

  public void setComponentValue(String name, Object value) {
    if ("enabled".equals(name)) {
      getContainer().setEnabled(value.equals("true"));
    }
    super.setComponentValue(name, value);
  }
}