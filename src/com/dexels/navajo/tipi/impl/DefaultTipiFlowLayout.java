package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import java.awt.*;

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

  public void createLayout(TipiContext context, Tipi t, XMLElement def, Navajo n) throws com.dexels.navajo.tipi.TipiException {
    super.createLayout(context,t,def,n);
  }

  public LayoutManager getLayout() {
    return myFlow;
  }

  public Object parseConstraint(String text) {
    System.err.println("Ignoring constraints for flowlayout");
    return null;
  }

  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented yet. But I should.");
  }
}