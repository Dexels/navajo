package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.components.swing.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiButton
    extends SwingTipiComponent {

//  private TipiEvent myEvent = null;
//  private TipiContainer myParent = null;
//  private Navajo myNavajo = null;
//  private Tipi myTipi = null;
  private TipiSwingButton myButton;

  public TipiButton() {
    initContainer();
  }

  public Container getContainer() {
    return myButton;
  }

  public Container createContainer() {
    myButton = new TipiSwingButton(this);
    return myButton;
  }

//  public void setTipi(Tipi t) {
//    myTipi = t;
//  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("text")) {
      myButton.setText( (String) object);
    }
    if (name.equals("icon")) {
      myButton.setIcon( myContext.getIcon((URL) object));
    }
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myButton.getText();
    }
    return super.getComponentValue(name);
  }


}