package com.dexels.navajo.tipi.impl;

import java.awt.*;

import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.studio.tree.*;

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

}