package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import tipi.*;
import java.net.*;
import java.beans.*;
import javax.swing.event.*;
import com.dexels.navajo.tipi.components.swing.TipiSwingWindow;
import com.dexels.navajo.tipi.components.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DefaultTipiWindow
//    extends DefaultTipi {
    extends DefaultTipi {
  private TipiSwingWindow myWindow;
  private String myMenuBar = "";
  private String myTitle;
  public Container createContainer() {
    System.err.println("\nEXECUTING CREATECONTAINER============================");
    myWindow = new TipiSwingWindow(this);
    TipiHelper th = new SwingTipiHelper();
    th.initHelper(this);
    addHelper(th);
    myWindow.addInternalFrameListener(new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent l) {
        myWindow_internalFrameClosed(l);
      }
    });
    myWindow.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    return myWindow;
  }

  public Object getComponentValue(String name) {
    if ("visible".equals(name)) {
      return new Boolean(myWindow.isVisible());
    }
//    if("title".equals(name)){
//      return myWindow.getTitle();
//    }
    Rectangle r = myWindow.getBounds();
    if (name.equals("x")) {
      return new Integer(r.x);
    }
    if (name.equals("y")) {
      return new Integer(r.y);
    }
    if (name.equals("w")) {
      return new Integer(r.width);
    }
    if (name.equals("h")) {
      return new Integer(r.height);
    }
    if (name.equals("iconifiable")) {
      return new Boolean(myWindow.isIconifiable());
    }
    if (name.equals("maximizable")) {
      return new Boolean(myWindow.isMaximizable());
    }
    if (name.equals("closable")) {
      return new Boolean(myWindow.isClosable());
    }
    if (name.equals("resizable")) {
      return new Boolean(myWindow.isResizable());
    }
    return super.getComponentValue(name);
  }

  private void myWindow_internalFrameClosed(InternalFrameEvent l) {
//    TipiContext.getInstance().disposeTipi(this);
  }

  public void addToContainer(Component c, Object constraints) {
    ( (JInternalFrame) getContainer()).getContentPane().add(c, constraints);
  }

  public void removeFromContainer(Component c) {
    ( (JInternalFrame) getContainer()).getContentPane().remove(c);
  }

  public void setContainerLayout(LayoutManager layout) {
    ( (JInternalFrame) getContainer()).getContentPane().setLayout(layout);
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    JInternalFrame jj = (JInternalFrame) getContainer();
    if (name.equals("iconifiable")) {
      boolean b = ( (Boolean) object).booleanValue();
      jj.setIconifiable(b);
    }
    if (name.equals("background")) {
      //System.err.println("Setting background of JInternalFrame to: " + object.toString());
      jj.setBackground( (Color) object);
    }
    if (name.equals("maximizable")) {
      boolean b = ( (Boolean) object).booleanValue();
      jj.setMaximizable(b);
    }
    if (name.equals("closable")) {
      boolean b = ( (Boolean) object).booleanValue();
      jj.setClosable(b);
    }
    if (name.equals("resizable")) {
      boolean b = ( (Boolean) object).booleanValue();
      jj.setResizable(b);
    }
    if (name.equals("selected")) {
      boolean b = ( (Boolean) object).booleanValue();
      try {
        jj.setSelected(b);
      }
      catch (PropertyVetoException ex) {
        System.err.println("Tried to select a window, but someone did not agree");
        ex.printStackTrace();
      }
      if (name.equals("visible")) {
        getContainer().setVisible( ( (Boolean) object).booleanValue());
      }
    }
    Rectangle r = getBounds();
    if (name.equals("menubar")) {
      try {
        if (object == null || object.equals("")) {
          System.err.println("null menu bar. Not instantiating");
          return;
        }
        myMenuBar = (String) object;
        XMLElement instance = new CaseSensitiveXMLElement();
        instance.setName("component-instance");
        instance.setAttribute("name", (String) object);
        instance.setAttribute("id", (String) object);
//        TipiComponent tm = myContext.instantiateComponent(instance);
        TipiComponent tm = addAnyInstance(myContext, instance, null);
        setJMenuBar( (JMenuBar) tm.getContainer());
      }
      catch (TipiException ex) {
        ex.printStackTrace();
        setJMenuBar(null);
        myMenuBar = "";
      }
    }
    if (name.equals("x")) {
      r.x = ( (Integer) object).intValue();
    }
    if (name.equals("y")) {
      r.y = ( (Integer) object).intValue();
    }
    if (name.equals("w")) {
      r.width = ( (Integer) object).intValue();
    }
    if (name.equals("h")) {
      r.height = ( (Integer) object).intValue();
    }
    if (name.equals("title")) {
      myTitle = object.toString();
      setTitle(myTitle);
    }
    if (name.equals("icon")) {
      setIcon(myContext.getIcon( (URL) object));
    }
    setBounds(r);
  }

  protected void setTitle(String s) {
    myWindow.setTitle(s);
  }

  protected void setBounds(Rectangle r) {
    myWindow.setBounds(r);
  }

  protected Rectangle getBounds() {
    return myWindow.getBounds();
  }

  protected void setIcon(ImageIcon ic) {
    myWindow.setFrameIcon(ic);
  }

  protected void setJMenuBar(JMenuBar ic) {
    myWindow.setJMenuBar(ic);
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth) {
    if (name.equals("iconify")) {
      try {
        myWindow.setIcon(true);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if (name.equals("maximize")) {
      JInternalFrame jj = (JInternalFrame) getContainer();
      jj.setMaximizable(true);
      try {
        jj.setMaximum(true);
        // This might throw an exception.. don't worry.. can't help it.
      }
      catch (PropertyVetoException ex1) {
        //ex1.printStackTrace();
      }
    }
    if (name.equals("restore")) {
      JInternalFrame jj = (JInternalFrame) getContainer();
      jj.setMaximizable(true);
      try {
        jj.setMaximum(false);
        // This might give an exception.. don't worry.. can't help it.
      }
      catch (PropertyVetoException ex1) {
        //ex1.printStackTrace();
      }
    }
    //    super.performComponentMethod( name,  invocation,  compMeth);
  }

  public boolean isReusable() {
    return true;
  }

  public void reUse() {
//    if (myParent!=null) {
//      myParent.addToContainer();
//    }
    myWindow.setVisible(true);
  }
}
