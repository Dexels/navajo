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
    extends DefaultTipiRootPane {
  private JInternalFrame myWindow;
//  private int x, y, w, h;
//  public DefaultTipiWindow() {
//    initContainer();
//   }
  public Container createContainer() {
//    System.err.println("\n\nAbout to create an internal frame....\n\n");
    myWindow = new JInternalFrame();
    myWindow.addInternalFrameListener(new InternalFrameAdapter() {
//      public void internalFrameOpened(InternalFrameEvent l) {
//        myWindow_internalFrameClosed(l);
//      }
      public void internalFrameClosing(InternalFrameEvent l) {
        myWindow_internalFrameClosed(l);
      }
//      public void internalFrameClosed(InternalFrameEvent l) {
//        myWindow_internalFrameClosed(l);
//      }
//      void internalFrameIconified(InternalFrameEvent)
//      void internalFrameDeiconified(InternalFrameEvent)
    });
    myWindow.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    return myWindow;
  }

  private void myWindow_internalFrameClosed(InternalFrameEvent l) {
//    System.err.println("\n\nFRAME EVENT!\n\n");
    TipiContext.getInstance().disposeTipi(this);
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
//    Rectangle r = jj.getBounds();
//    if (name.equals("x")) {
//      r.x = Integer.parseInt( (String) object);
//    }
//    if (name.equals("y")) {
//     r.y = Integer.parseInt( (String) object);
//   }
//   if (name.equals("w")) {
//     r.width = Integer.parseInt( (String) object);
//   }
//   if (name.equals("h")) {
//     r.height = Integer.parseInt( (String) object);
//   }
    if (name.equals("iconifiable")) {
      boolean b = object.equals("true");
      jj.setIconifiable(b);
    }
    if (name.equals("background")) {
      System.err.println("Setting background of JInternalFrame to: " + object.toString());
      jj.setBackground(parseColor( (String) object));
    }
    if (name.equals("maximizable")) {
      boolean b = object.equals("true");
      jj.setMaximizable(b);
    }
    if (name.equals("closable")) {
      boolean b = object.equals("true");
      jj.setClosable(b);
    }
    if (name.equals("resizable")) {
      boolean b = object.equals("true");
      jj.setResizable(b);
    }
    if (name.equals("selected")) {
      boolean b = object.equals("true");
      try {
        jj.setSelected(b);
      }
      catch (PropertyVetoException ex) {
        System.err.println("Tried to select a window, but someone did not agree");
        ex.printStackTrace();
      }
      if (name.equals("visible")) {
        getContainer().setVisible(object.equals("true"));
      }
    }
//   if (name.equals("title")) {
//    jj.setTitle((String)object);
//  }
//  if (name.equals("icon")) {
//    String icon = (String)object;
//          try{
//            URL i = new URL(icon);
//            ImageIcon ic = new ImageIcon(i);
//            jj.setFrameIcon(ic);
//          }catch(Exception e){
//             URL t = MainApplication.class.getResource(icon);
//             if(t!=null){
//               ImageIcon ii = new ImageIcon(t);
//               jj.setFrameIcon(ii);
//             }
//          }
//  }
//   jj.setBounds(r);
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

  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
    if (name.equals("iconify")) {
      try {
        myWindow.setIcon(true);
      }
      catch (Exception ex) {
        ex.printStackTrace();
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
