package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.net.*;
import tipi.*;
import com.dexels.navajo.tipi.components.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiProgressBar extends TipiComponent  {
  private JProgressBar myProgressBar = null;
//  public TipiProgressBar() {
//    initContainer();
//  }
//  public void addToContainer(Component c, Object constraints) {
//    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
//  }
//  public void removeFromContainer(Component c) {
//    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
//  }

  public Container createContainer() {
    myProgressBar = new JProgressBar();
    myProgressBar.setMinimum(0);
    myProgressBar.setMaximum(100);
    myProgressBar.setValue(0);
    myProgressBar.setStringPainted(true);
//    myProgressBar.setString("");
    TipiHelper th = new SwingTipiHelper();
    th.initHelper(this);
    addHelper(th);
    return myProgressBar;
  }

 public Container getContainer() {
   return myProgressBar;
 }

 public void setComponentValue(String name, Object object) {
   super.setComponentValue(name,object);
    if (name.equals("text")) {
      myProgressBar.setString((String)object);
    }
    if (name.equals("value")) {
      int value = (int)Float.parseFloat(""+object);
      myProgressBar.setValue(value);
      myProgressBar.setString(""+value+"%");

    }
    if (name.equals("orientation")) {
      String or = (String)object;
      if("horizontal".equals(or)){
        myProgressBar.setOrientation(JProgressBar.HORIZONTAL);
      }
      if("vertical".equals(or)){
        myProgressBar.setOrientation(JProgressBar.VERTICAL);
      }
    }
    if (name.equals("indeterminate")) {
      myProgressBar.setIndeterminate(((Boolean)object).booleanValue());
      if (!((Boolean)object).booleanValue()) {
        myProgressBar.setMinimum(0);
        myProgressBar.setMaximum(100);
      }

    }

  }

  public Object getComponentValue(String name) {
     if (name.equals("text")) {
       return myProgressBar.getString();
     }
     if (name.equals("value")) {
       return new Integer(myProgressBar.getValue());
     }
     if (name.equals("orientation")) {
       int orientation = myProgressBar.getOrientation();
       switch (orientation) {
         case JProgressBar.HORIZONTAL:
           return "horizontal";
         case JProgressBar.VERTICAL:
           return "vertical";
       }
      }
     return super.getComponentValue(name);
   }

}
