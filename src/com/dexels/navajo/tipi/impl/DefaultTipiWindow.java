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

  public Container createContainer() {
    myWindow = new JInternalFrame();
    myWindow.addInternalFrameListener(new InternalFrameAdapter() {
      public void internalFrameClosing(InternalFrameEvent l) {
        myWindow_internalFrameClosed(l);
      }
    });
    myWindow.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    return myWindow;
  }

  public Object getComponentValue(String name) {
    if("visible".equals(name)){
      return new Boolean (myWindow.isVisible());
    }
//    if("title".equals(name)){
//      return myWindow.getTitle();
//    }


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
    if (name.equals("iconifiable")) {
      boolean b = ((Boolean)object).booleanValue();
      jj.setIconifiable(b);
    }
    if (name.equals("background")) {
      //System.err.println("Setting background of JInternalFrame to: " + object.toString());
      jj.setBackground(parseColor( (String) object));
    }
    if (name.equals("maximizable")) {
      boolean b = ((Boolean)object).booleanValue();
      jj.setMaximizable(b);
    }
    if (name.equals("closable")) {
      boolean b = ((Boolean)object).booleanValue();
      jj.setClosable(b);
    }
    if (name.equals("resizable")) {
      boolean b = ((Boolean)object).booleanValue();
      jj.setResizable(b);
    }

    if (name.equals("selected")) {
      boolean b = ((Boolean)object).booleanValue();
      try {
        jj.setSelected(b);
      }
      catch (PropertyVetoException ex) {
        System.err.println("Tried to select a window, but someone did not agree");
        ex.printStackTrace();
      }
      if (name.equals("visible")) {
        getContainer().setVisible(((Boolean)object).booleanValue());
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
    if(name.equals("maximize")){
      JInternalFrame jj = (JInternalFrame) getContainer();
      jj.setMaximizable(true);
      try {
        jj.setMaximum(true);
        // This will might an exception.. don't worry.. can't help it.
      }
      catch (PropertyVetoException ex1) {
        //ex1.printStackTrace();
        }
    }
    if(name.equals("restore")){
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
