package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
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

  public Object createContainer() {
    myField = new TipiSwingTextField(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myField;
  }

  public void setComponentValue(final String name, final Object object) {
    if (name.equals("text")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myField.setText(object.toString());
          return;
        }
      });
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      System.err.println("Retrieving text:" + myField.getText());
      return myField.getText();
    }
    return super.getComponentValue(name);
  }
}
