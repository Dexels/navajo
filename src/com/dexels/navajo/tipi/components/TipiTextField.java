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

public class TipiTextField extends SwingTipiComponent {
  private JTextField myField;
  public TipiTextField() {
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void removeFromContainer(Component c) {
    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
  }
  public Container createContainer() {
    myField = new JTextField("apenoot");
    return myField;
  }
  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name,object);
    if (name.equals("text")) {
      myField.setText((String)object);
    }

  }
  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      System.err.println("Retrieving text:"+myField.getText());
      return myField.getText();
    }
    return super.getComponentValue(name);
  }

}