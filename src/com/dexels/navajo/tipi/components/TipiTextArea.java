package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiTextArea extends SwingTipiComponent {
  public TipiTextArea() {
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Yeah right");
  }
  public void setContainerLayout(LayoutManager layout) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiBase abstract method*/
  }
  public Container createContainer() {
    return new JTextArea();
  }
  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name,object);
    if (name.equals("text")) {
      ((JTextArea)getContainer()).setText((String)object);
    }

  }
  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return ((JTextArea)getContainer()).getText();
    }
    return super.getComponentValue(name);
  }

}