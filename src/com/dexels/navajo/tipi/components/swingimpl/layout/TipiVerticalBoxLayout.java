package com.dexels.navajo.tipi.components.swingimpl.layout;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiVerticalBoxLayout extends TipiLayoutImpl {
  public TipiVerticalBoxLayout() {
  }
  protected void setValue(String name, TipiValue tv) {
    /**
     * Not necessaru
     */
  }
  protected Object parseConstraint(String text) {
    return null;
  }
  public void createLayout() throws com.dexels.navajo.tipi.TipiException {
    Container c = (Container)super.myComponent.getContainer();
    BoxLayout layout = new BoxLayout(c,BoxLayout.Y_AXIS);
 setLayout(layout);

    /**@todo Implement this com.dexels.navajo.tipi.internal.TipiLayout abstract method*/
  }

}
