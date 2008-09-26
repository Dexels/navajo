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
public class TipiMenubar
    extends TipiSwingComponentImpl {
  private TipiSwingMenuBar myMenuBar;


  public void removeFromContainer(final Object c) {
	  super.removeFromContainer(c);
	  myMenuBar.repaint();
  }

  public Object createContainer() {
    myMenuBar = new TipiSwingMenuBar();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myMenuBar;
  }
}
