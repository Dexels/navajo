package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
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

public class TipiLabel extends SwingTipiComponent {
  private JLabel myLabel = null;
  public TipiLabel() {
    initContainer();
  }

  public Container createContainer() {
    myLabel = new JLabel();
    return myLabel;
  }

 public Container getContainer() {
   return myLabel;
 }

 public void setComponentValue(String name, Object object) {
   super.setComponentValue(name,object);
    if (name.equals("text")) {
      myLabel.setText((String)object);
    }
    if (name.equals("icon")) {
      myLabel.setIcon(myContext.getIcon((String) object));
    }

  }
}