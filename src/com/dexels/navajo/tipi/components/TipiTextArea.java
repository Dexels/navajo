package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.components.swing.*;
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
//  public void addToContainer(Component c, Object constraints) {
//    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
//  }
//  public void removeFromContainer(Component c) {
//    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
//  }
//  public void setContainerLayout(LayoutManager layout) {
//    /**@todo Implement this com.dexels.navajo.tipi.TipiBase abstract method*/
//  }
  public Container createContainer() {
    TipiSwingTextArea t = new TipiSwingTextArea(this);
    TipiHelper th = new SwingTipiHelper();
    th.initHelper(this);
    addHelper(th);
    return t;
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