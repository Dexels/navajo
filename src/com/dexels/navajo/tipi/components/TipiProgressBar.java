package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
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

public class TipiProgressBar extends SwingTipiComponent  {
  private JProgressBar myProgressBar = null;
  public TipiProgressBar() {
    initContainer();
  }

  public Container createContainer() {
    myProgressBar = new JProgressBar();
    myProgressBar.setMinimum(0);
    myProgressBar.setMaximum(100);
    myProgressBar.setValue(0);
    myProgressBar.setStringPainted(true);
    myProgressBar.setString("");
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
      myProgressBar.setValue(Integer.parseInt((String)object));
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
      myProgressBar.setIndeterminate(object.equals("true"));
    }

  }
}
