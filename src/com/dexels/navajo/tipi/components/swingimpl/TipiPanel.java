package com.dexels.navajo.tipi.components.swingimpl;

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
public class TipiPanel
    extends TipiSwingDataComponentImpl {
  public Object createContainer() {
    TipiSwingPanel tsp = new TipiSwingPanel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return tsp;
  }

  public void setComponentValue(String name, Object value) {
    if ("enabled".equals(name)) {
      getSwingContainer().setEnabled(value.equals("true"));
    }
    super.setComponentValue(name, value);
  }
}