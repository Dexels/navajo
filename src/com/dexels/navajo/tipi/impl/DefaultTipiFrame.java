package com.dexels.navajo.tipi.impl;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.components.swing.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DefaultTipiFrame
    extends DefaultTipi {
  private JFrame myFrame = null;
  private boolean fullscreen = false;
  private String myMenuBar="";
  public DefaultTipiFrame() {
  }

  public Container createContainer() {
    myFrame = new DefaultTipiMainFrame(this);
    TipiHelper th = new SwingTipiHelper();
    th.initHelper(this);
    addHelper(th);
//        myContext.setToplevel(myFrame);
    return (Container) myFrame;
  }

  public void addToContainer(Component c, Object constraints) {
//    System.err.println("Adding to frame. Contraints: "+constraints);
//    System.err.println("ComponentClass: "+c.getClass());
//    if (constraints!=null) {
//      System.err.println("ConstraintClass: "+constraints.getClass());
//    }
    myFrame.getContentPane().add(c, constraints);
  }

  public void removeFromContainer(Component c) {
    myFrame.getContentPane().remove(c);
  }

  protected void setBounds(Rectangle r) {
    myFrame.setBounds(r);
  }

  protected Rectangle getBounds() {
    return myFrame.getBounds();
  }

  protected void setIcon(ImageIcon ic) {
    if (ic == null) {
      return;
    }
    myFrame.setIconImage(ic.getImage());
  }

  protected void setTitle(String s) {
    myFrame.setTitle(s);
  }

  public void setContainerLayout(LayoutManager layout) {
    System.err.println("Setting frame layout to: " + layout.getClass());
    myFrame.getContentPane().setLayout(layout);
  }

  public void setComponentValue(String name, Object object) {
    if (name.equals("fullscreen") && ( (Boolean) object).booleanValue()) {
      System.err.println("Set to fullscreen");
      fullscreen = ( (Boolean) object).booleanValue();
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
      });
    }
    if (name.equals("visible")) {
      getContainer().setVisible(object.equals("true"));
      if (fullscreen) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            ( (JFrame) myFrame).setExtendedState(JFrame.MAXIMIZED_BOTH);
          }
        });
      }
    }
    if ("title".equals(name)) {
      this.setTitle( (String) object);
    }
    Rectangle r = getBounds();
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
    setBounds(r);
    if (name.equals("menubar")) {
      try {
        if (object==null || object.equals("")) {
          System.err.println("null menu bar. Not instantiating");
          return;
        }

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
    super.setComponentValue(name, object);
//    if (name.equals("centered") && ((Boolean)object).booleanValue()) {
//      ((JFrame)myFrame).setLocationRelativeTo(null);
//    }
  }

  protected void setJMenuBar(JMenuBar s) {
    ( (JFrame) myFrame).setJMenuBar(s);
  }
}