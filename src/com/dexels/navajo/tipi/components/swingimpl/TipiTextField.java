package com.dexels.navajo.tipi.components.swingimpl;

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
public class TipiTextField
    extends TipiSwingComponentImpl {
  private TipiSwingTextField myField;
  public TipiTextField() {
  }

  public Container createContainer() {
    myField = new TipiSwingTextField(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myField;
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("text")) {
      myField.setText(object.toString());
    }
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      System.err.println("Retrieving text:" + myField.getText());
      return myField.getText();
    }
    return super.getComponentValue(name);
  }
}