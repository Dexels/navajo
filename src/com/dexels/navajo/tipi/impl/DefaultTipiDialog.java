package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.dexels.navajo.tipi.tipixml.*;

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
    RootPaneContainer r = getContext().getTopLevel();
    JDialog d = null;
    if (Frame.class.isInstance(r)) {
      d = new JDialog((Frame)r);
    } else {
      d = new JDialog( (Dialog) r);
    }
    return d;
  }

  private void dialog_windowClosing(WindowEvent e) {
    if (disposed) {
      return;
    }
   JDialog d =(JDialog)e.getSource();
  try {
    performAllEvents(TipiEvent.TYPE_ONWINDOWCLOSED, e);
  }
  catch (TipiException ex) {
    ex.printStackTrace();
  }
  d.setVisible(false);
   myContext.disposeTipi(this);
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
    ((JDialog)getContainer()).getContentPane().add(c,constraints);
   }

   public void removeFromContainer(Component c) {
  ((JDialog)getContainer()).getContentPane().remove(c);
}

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("modal")) {
      ((JDialog)getContainer()).setModal(((String)object).equals("true"));
    }
    if (name.equals("background")) {
      ((JDialog)getContainer()).getContentPane().setBackground(parseColor((String)object));
    }
    if (name.equals("decorated")) {
      ((JDialog)getContainer()).setUndecorated(!((String)object).equals("true"));
    }

  }
  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
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
  public void setParent(TipiComponent parent) {
    super.setParent(parent);
//    ((JDialog)getContainer()).set
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
  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
    super.performComponentMethod(name,invocation,compMeth);
    if (name.equals("show")) {
      // If modal IT WILL BLOCK HERE
      ((JDialog)getContainer()).setLocationRelativeTo((Component)myContext.getTopLevel());
      ((JDialog)getContainer()).setVisible(true);
      // Any code beyond this point will be executed after the dialog has been closed.
    }
    if (name.equals("hide")) {
      ((JDialog)getContainer()).setVisible(false);
    }


  }

}