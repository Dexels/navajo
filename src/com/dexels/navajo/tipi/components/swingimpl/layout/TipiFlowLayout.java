package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFlowLayout
    extends TipiLayoutImpl {
  private FlowLayout myFlow = new FlowLayout();
  public TipiFlowLayout() {
  }

  public void createLayout() {
    FlowLayout layout = new FlowLayout();
    setLayout(layout);
  }

  public Object parseConstraint(String text, int index) {
    return null;
  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented yet. But I should.");
  }
}
