package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class DefaultTipiRootPane
    extends DefaultTipi {
  protected RootPaneContainer myRootPaneContainer;
  protected abstract void setBounds(Rectangle r);
  private String myTitle;
  private String myIcon;
  private String myMenuBar="";
  protected abstract Rectangle getBounds();

  protected abstract void setIcon(ImageIcon ic);
  protected abstract void setTitle(String s);
  protected abstract void setJMenuBar(JMenuBar s);

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    Rectangle r = getBounds();
    if (name.equals("menubar")) {
      try {
        System.err.println("Adding menu bar!!!");
        myMenuBar = (String)object;
        XMLElement instance = new CaseSensitiveXMLElement();
        instance.setName("component-instance");
        instance.setAttribute("name",(String)object);
        instance.setAttribute("id",(String)object);
//        TipiComponent tm = myContext.instantiateComponent(instance);
        TipiComponent tm = addAnyInstance(myContext,instance,null);
        setJMenuBar( (JMenuBar) tm.getContainer());
      }
      catch (TipiException ex) {
        ex.printStackTrace();
        setJMenuBar(null);
        myMenuBar = "";
      }
    }
    if (name.equals("x")) {
      r.x = ((Integer) object).intValue();
    }
    if (name.equals("y")) {
      r.y = ((Integer) object).intValue();
    }
    if (name.equals("w")) {
        r.width = ( (Integer) object).intValue();
    }
    if (name.equals("h")) {
      r.height = ((Integer) object).intValue();
    }
    if (name.equals("title")) {
      myTitle = object.toString();
      setTitle(myTitle);
    }
    if (name.equals("icon")) {
      myIcon = (String) object;
      ImageIcon ic = myContext.getIcon(myIcon);
      setIcon(ic);
    }
      setBounds(r);
  }

  public Object getComponentValue(String name) {
    if (name.equals("x")) {
      return new Integer(this.getContainer().getX());
    }
    if (name.equals("y")) {
      return new Integer(this.getContainer().getY());
    }
    if (name.equals("w")) {
      return new Integer(this.getContainer().getWidth());
    }
    if (name.equals("h")) {
      return new Integer(this.getContainer().getHeight());
    }
    if (name.equals("title")) {
      return myTitle;
    }
    if (name.equals("icon")) {
      return myIcon;
    }
    if (name.equals("menubar")) {
      return myMenuBar;
    }
    return super.getComponentValue(name);
  }
}