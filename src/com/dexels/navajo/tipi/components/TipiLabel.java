package com.dexels.navajo.tipi.components;


import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.components.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
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

public class TipiLabel extends TipiComponent {
  private TipiSwingLabel myLabel = null;
//  public TipiLabel() {
//    initContainer();
//  }
//  public void addToContainer(Component c, Object constraints) {
//    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
//  }
//  public void removeFromContainer(Component c) {
//    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
//  }

  public Container createContainer() {
    myLabel = new TipiSwingLabel(this);
    TipiHelper th = new SwingTipiHelper();
    th.initHelper(this);
    addHelper(th);
    return myLabel;
  }

 public Container getContainer() {
   return myLabel;
 }

 public void setComponentValue(String name, Object object) {
   super.setComponentValue(name,object);
    if (name.equals("text")) {
      myLabel.setText(""+object);
    }
    if (name.equals("icon")) {
      myLabel.setIcon(myContext.getIcon((URL) object));
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