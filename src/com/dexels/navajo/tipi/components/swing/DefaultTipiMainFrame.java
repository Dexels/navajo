package com.dexels.navajo.tipi.components.swing;

import java.awt.event.*;
import com.dexels.navajo.tipi.*;

import java.awt.*;
import javax.swing.*;
import tipi.*;
import java.net.*;
import com.dexels.navajo.tipi.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiMainFrame extends JFrame implements TopLevel, Designable {

//  TipiContext c;
  BorderLayout borderLayout1 = new BorderLayout();
  private TipiComponent me;
  private boolean gridFlag = false;
  private boolean selected = false;

  public DefaultTipiMainFrame(TipiComponent me) {
    try {
      this.me = me;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {

    setVisible(false);
    this.getContentPane().setLayout(borderLayout1);
    this.addWindowListener(new MainFrame_this_windowAdapter(this));
  }

  public void paint(Graphics g){
    super.paint(g);
    Color old = g.getColor();
    if(gridFlag){
      me.paintGrid(getRootPane(), g);
    }
    if(selected){
      me.highLight(getRootPane(), g);
    }
    g.setColor(old);
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

  public void setTipiMenubar(TipiMenubar tm){
    setJMenuBar((JMenuBar)tm.getContainer());
  }
  public void showGrid(boolean value) {
    gridFlag = value;
  }
  public boolean isGridShowing() {
    return gridFlag;
  }
  public void setHighlighted(boolean value) {
    selected = value;
  }
  public boolean isHighlighted() {
    return selected;
  }
}

class MainFrame_this_windowAdapter extends java.awt.event.WindowAdapter {
  DefaultTipiMainFrame adaptee;

  MainFrame_this_windowAdapter(DefaultTipiMainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}