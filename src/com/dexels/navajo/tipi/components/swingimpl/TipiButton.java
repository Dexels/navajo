package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;
import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiButton
    extends TipiSwingComponentImpl {
  private TipiSwingButton myButton;
  public Container createContainer() {
    myButton = new TipiSwingButton(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myButton;
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("text")) {
      myButton.setText( (String) object);
    }
    if (name.equals("icon")) {
      myButton.setIcon(getIcon( (URL) object));
    }
  }

  private ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myButton.getText();
    }
    return super.getComponentValue(name);
  }
}