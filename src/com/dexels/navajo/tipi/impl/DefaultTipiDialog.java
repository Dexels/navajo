package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiDialog extends DefaultTipiRootPane {
  private boolean disposed = false;

  public DefaultTipiDialog() {
  }
  public Container createContainer() {
    RootPaneContainer r = getContext().getDefaultTopLevel().getTopLevel();
    JDialog d = null;
    if (Frame.class.isInstance(r)) {
      d = new JDialog((Frame)r);
    } else {
      d = new JDialog( (Dialog) r);
    }
    createWindowListener(d);
    return d;
  }

  private void dialog_windowClosing(WindowEvent e) {
    System.err.println("Window closing called!");
   JDialog d =(JDialog)e.getSource();
    try {
      performTipiEvent("onWindowClosed", e);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
    myContext.disposeTipiComponent(this);
    disposed = true;
  }

  protected void createWindowListener(JDialog d) {
    d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    d.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dialog_windowClosing(e);
      }
      public void windowClosed(WindowEvent e) {
        dialog_windowClosing(e);
      }

    });
  }

  public LayoutManager getContainerLayout() {
    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
    return ((JDialog)getContainer()).getContentPane().getLayout();
  }
  public void setContainerLayout(LayoutManager layout) {
    ((JDialog)getContainer()).getContentPane().setLayout(layout);
  }
  public void addToContainer(Component c, Object constraints) {
    if (c!=null) {
      ( (JDialog) getContainer()).getContentPane().add(c, constraints);
    }
   }

   public void removeFromContainer(Component c) {
     if (c!=null) {
       ((JDialog)getContainer()).getContentPane().remove(c);

     }

}

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("modal")) {
      ((JDialog)getContainer()).setModal(((Boolean)object).booleanValue());
    }
    if (name.equals("background")) {
      ((JDialog)getContainer()).getContentPane().setBackground((Color)object);
    }
    if (name.equals("decorated")) {
      ((JDialog)getContainer()).setUndecorated(!((Boolean)object).booleanValue());
    }

  }
  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
    if("isShowing".equals(name)){
      return new Boolean (((JDialog)getContainer()).isVisible());
    }
    return super.getComponentValue(name);
  }
  protected void setJMenuBar(JMenuBar s) {
      ((JDialog)getContainer()).setJMenuBar(s);
  }

  protected void setTitle(String s) {
    ((JDialog)getContainer()).setTitle(s);
  }
  protected void setIcon(ImageIcon im) {
    System.err.println("setIcon for dialog ignored!");
  }
  protected void setBounds(Rectangle r) {
    getContainer().setBounds(r);
  }

  protected Rectangle getBounds() {
    return getContainer().getBounds();
   }
  public void setVisible(boolean b) {
    if (b) {
      ((JDialog)getContainer()).setVisible(b);
    }

//    ((JDialog)getContainer()).set
  }
  public void disposeComponent() {
    getContainer().setVisible(false);
    super.disposeComponent();
  }
  protected synchronized void performComponentMethod(String name,TipiComponentMethod compMeth) {
    super.performComponentMethod(name,compMeth);
    if (name.equals("show")) {
      // If modal IT WILL BLOCK HERE
      ((JDialog)getContainer()).setLocationRelativeTo((Component)myContext.getTopLevel());
     SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          ((JDialog)getContainer()).setVisible(true);
        }
      });
       // Any code beyond this point will be executed after the dialog has been closed.
    }
    if (name.equals("hide")) {
      ((JDialog)getContainer()).setVisible(false);
//      System.err.println("Hide dialog: Disposing dialog!");
//       disposeComponent();
//       TipiContext.getInstance().disposeTipi(this);
    }
    if (name.equals("dispose")) {
      System.err.println("Hide dialog: Disposing dialog!");
      myContext.disposeTipiComponent(this);
      disposed = true;
    }
  }
  public void setContainerVisible(boolean b) {
    // do nothing
//    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
//    super.setContainerVisible(b);
  }

//  public void loadData(Navajo n, TipiContext tc) throws com.dexels.navajo.tipi.TipiException {
//    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
//    super.loadData(n,tc);
//    ((JDialog)getContainer()).pack();
//  }

}