package com.dexels.navajo.tipi.impl;

import java.awt.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiFlowLayout extends DefaultTipiLayout {

  private FlowLayout myFlow = new FlowLayout();

  public DefaultTipiFlowLayout() {
  }

  public void createLayout() {
    FlowLayout layout = new FlowLayout();
    setLayout(layout);
  }

//  public void createLayout(TipiContext context, Tipi t, XMLElement def, Navajo n) throws com.dexels.navajo.tipi.TipiException {
//    super.createLayout(context,t,def,n);
//  }
//
//  public LayoutManager getLayout() {
//    return myFlow;
//  }

  public Object parseConstraint(String text) {
    return null;
  }

//  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
//  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented yet. But I should.");
  }
}