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
public class TipiLabel
    extends TipiSwingComponentImpl {
  private TipiSwingLabel myLabel = null;

  public Container createContainer() {
    myLabel = new TipiSwingLabel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myLabel;
  }

  public Container getContainer() {
    return myLabel;
  }
  private ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("text")) {
      myLabel.setText("" + object);
    }
    if (name.equals("icon")) {
      myLabel.setIcon(getIcon( (URL) object));
    }
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myLabel.getText();
    }
    if (name.equals("icon")) {
      return myLabel.getIcon();
    }
    return super.getComponentValue(name);
  }
}