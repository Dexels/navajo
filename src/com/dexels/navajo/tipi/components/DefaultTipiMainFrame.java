package com.dexels.navajo.tipi.components;

import java.awt.event.*;
import com.dexels.navajo.tipi.*;

import java.awt.*;
import javax.swing.*;
import tipi.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiMainFrame extends JFrame implements TopLevel {

//  TipiContext c;
  BorderLayout borderLayout1 = new BorderLayout();

  public DefaultTipiMainFrame() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

//  public void load(TipiContext tc,URL u) {
//    try {
//      tc.parseURL(u);
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//    }
//
//  }
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.addWindowListener(new MainFrame_this_windowAdapter(this));
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

  public void setTipiMenubar(TipiMenubar tm){
    setJMenuBar((JMenuBar)tm);
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