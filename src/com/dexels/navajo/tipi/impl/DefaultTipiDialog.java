package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiDialog extends DefaultTipiRootPane {
  public DefaultTipiDialog() {
  }
  public Container createContainer() {
    RootPaneContainer r = getContext().getTopLevel();
    if (Frame.class.isInstance(r)) {
      return new JDialog((Frame)r);
    }
    return new JDialog((Dialog)r);

  }
  public LayoutManager getContainerLayout() {
    /**@todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method*/
    return super.getContainerLayout();
  }
  public void setContainerLayout(LayoutManager layout) {
    ((JDialog)getContainer()).getContentPane().setLayout(layout);
  }
  public void addToContainer(Component c, Object constraints) {
    ((JDialog)getContainer()).getContentPane().add(c,constraints);
   }
  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("modal")) {
      ((JDialog)getContainer()).setModal(((String)object).equals("true"));
    }
    if (name.equals("decorated")) {
      System.err.println("Setting decorated to: "+!((String)object).equals("true"));
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

  public void  setVisible(boolean b) {
    ((JDialog)getContainer()).setVisible(b);

  }
}