package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.LayoutManager;
import com.dexels.navajo.tipi.tipixml.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiGridBagLayout extends DefaultTipiLayout {

//  GridBagLayout layout = null;

  protected void setValue(String name, TipiValue tv) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiLayout abstract method*/
  }



  protected Object parseConstraint(String text) {
    DefaultTipiGridBagConstraints gt = new DefaultTipiGridBagConstraints(text);
    return gt;
  }

  public void createLayout() {
    setLayout(new GridBagLayout());
  }

  public Object createDefaultConstraint(int index) {
    return new DefaultTipiGridBagConstraints(0,index,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(1,1,1,1),0,0);
  }


}