package com.dexels.navajo.tipi.impl;

import java.awt.*;

import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.studio.tree.*;
import com.dexels.navajo.tipi.*;
import javax.swing.tree.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiComponentTree extends SwingTipiComponent {
  private TipiComponentTree myContainer = null;
  public DefaultTipiComponentTree() {
  }
  public Container createContainer() {
    myContainer = new TipiComponentTree() ;
    setStandardComponentTree();
    return myContainer;
  }

  public void setStandardComponentTree() {
    myContainer.setStandardComponentTree();
  }
  public Object getComponentValue(String name) {
    if (name.equals("selected")) {
      return getSelectedPath();
    }
    return super.getComponentValue(name);
  }

  private String getSelectedPath() {
    TreePath tp = myContainer.getSelectionPath();
    if (tp==null) {
     return null;
    }
    TipiComponent tc = (TipiComponent)tp.getLastPathComponent();
    if (tc==null) {
      return null;
    }
    return tc.getPath();

  }
}