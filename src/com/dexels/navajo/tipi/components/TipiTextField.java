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

public class TipiTextField extends TipiComponent {
  public TipiTextField() {
  }
  public void addToContainer(Component c, Object constraints) {
  }
  public Container createContainer() {
    return new JTextField();
  }
  public void setComponentValue(String name, Object object) {
    if (name.equals("text")) {
      ((JTextField)getContainer()).setText((String)object);
    }

  }
  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return ((JTextField)getContainer()).getText();
    }
    return null;
  }

}