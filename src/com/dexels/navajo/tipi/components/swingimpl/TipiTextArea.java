package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.*;
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
public class TipiTextArea
    extends TipiSwingComponentImpl {
  public TipiTextArea() {
  }

  public Object createContainer() {
    TipiSwingTextArea t = new TipiSwingTextArea(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return t;
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("text")) {
      ( (JTextArea) getContainer()).setText( (String) object);
    }
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return ( (JTextArea) getContainer()).getText();
    }
    return super.getComponentValue(name);
  }
}