package com.dexels.navajo.tipi.components.swingimpl.layout;

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
public class TipiVerticalLayout
    extends TipiLayoutImpl {
//  GridBagLayout layout = null;
  protected void setValue(String name, TipiValue tv) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiLayout abstract method*/
  }

  protected Object parseConstraint(String text,int index) {
//    TipiSwingGridBagConstraints gt = new TipiSwingGridBagConstraints(text);
    return createDefaultConstraint(index);
  }

  public void createLayout() {
    setLayout(new GridBagLayout());
  }

  public Object createDefaultConstraint(int index) {
    return new TipiSwingGridBagConstraints(0, index, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 0);
  }
}
