package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import tipi.*;
import javax.swing.JInternalFrame;
import com.dexels.navajo.tipi.studio.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScreen extends DefaultTipiRootPane {
  private RootPaneContainer myFrame = null;

  public DefaultTipiScreen() {
  }

  public Container createContainer() {
    System.err.println("IN TOPLEVEL!");
//    System.err.println("My classpath: ");
//    System.getProperties().list(System.out);
    switch (TipiContext.getInstance().getUIMode()) {
      case TipiContext.UI_MODE_APPLET:
        myFrame = TipiContext.getInstance().getTopLevel();
//        myFrame = new DefaultTipiPanel();
        return (Container)myFrame;
      case TipiContext.UI_MODE_STUDIO:
        myFrame = new PreviewFrame();
        return myFrame.getContentPane();
//        break;
      case TipiContext.UI_MODE_FRAME:
        myFrame = new DefaultTipiMainFrame();
        return (Container)myFrame;
//        break;
    }
    return null;
//    if(TipiContext.getInstance().getInternalMode()){
//      myFrame = new PreviewFrame();
//      return (Container)myFrame;
//    }else{
//      myFrame = new DefaultTipiMainFrame();
//      return (Container)myFrame;
//    }
  }

  public void addToContainer(Component c, Object constraints) {
    myFrame.getContentPane().add(c, constraints);
  }

  protected void setBounds(Rectangle r) {
    ((Container)myFrame).setBounds(r);
//    System.err.println("FrameSize: "+r);
//    myFrame.setSize(r.getSize());
  }

  protected Rectangle getBounds() {
     return ((Container)myFrame).getBounds();
   }

 protected void setIcon(ImageIcon ic) {
   if (ic==null) {
     return;
   }

   if(JInternalFrame.class.isInstance(myFrame)){
     ((JInternalFrame)myFrame).setFrameIcon(ic);
   }
   if(JFrame.class.isInstance(myFrame)){
     ((JFrame)myFrame).setIconImage(ic.getImage());
   }

 }

 protected void setTitle(String s) {
   if(JInternalFrame.class.isInstance(myFrame)){
     ((JInternalFrame)myFrame).setTitle(s);
   }
   if(JFrame.class.isInstance(myFrame)){
     ((JFrame)myFrame).setTitle(s);
   }
 }
  public void setContainerLayout(LayoutManager layout) {
    myFrame.getContentPane().setLayout(layout);
  }

  public void setComponentValue(String name, Object object) {
    if (name.equals("fullscreen") && "true".equals(object)) {
      ((Container)myFrame).setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
    super.setComponentValue(name, object);
  }

  protected void setJMenuBar(JMenuBar s) {
    if(JInternalFrame.class.isInstance(myFrame)){
      ((JInternalFrame)myFrame).setJMenuBar(s);
    }
    if(JFrame.class.isInstance(myFrame)){
      ((JFrame)myFrame).setJMenuBar(s);
    }
//    ((MenuContainer)myFrame).setJMenuBar(s);
  }

}