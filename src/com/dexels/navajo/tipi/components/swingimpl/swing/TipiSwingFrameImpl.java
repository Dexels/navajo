package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingFrameImpl
    extends JFrame
    implements TopLevel, TipiDesignable, TipiSwingFrame {
//  TipiContext c;
  BorderLayout borderLayout1 = new BorderLayout();
  private TipiSwingDataComponentImpl me;
  private boolean gridFlag = false;
  private boolean selected = false;
  public TipiSwingFrameImpl(TipiSwingDataComponentImpl me) {
    try {
      this.me = me;
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    final TipiSwingFrameImpl tsf = this;
    final MainFrame_this_windowAdapter mtw = new MainFrame_this_windowAdapter(this);
    setVisible(false);
    tsf.getContentPane().setLayout(borderLayout1);
    tsf.addWindowListener(mtw);
  }

  public void paint(Graphics g) {
    super.paint(g);
    Color old = g.getColor();
//    if (gridFlag) {
//      me.paintGrid(getRootPane(), g);
//    }
    if (selected) {
      me.highLight(getRootPane(), g);
    }
    g.setColor(old);
  }

  public void setIconImage(ImageIcon i) {
    super.setIconImage(i.getImage());
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

  public void setTipiMenubar(final TipiMenubar tm) {
//    SwingUtilities.invokeLater(new Runnable() {
//      public void run() {
        setJMenuBar( (JMenuBar) tm.getContainer());
//      }
//    });
  }

  public void showGrid(boolean value) {
    gridFlag = value;
  }

  public boolean isGridShowing() {
    return gridFlag;
  }

  public void setModal(boolean b) {
    // ignored for frames
  }

  public void setHighlighted(boolean value) {
    selected = value;
  }

  public boolean isHighlighted() {
    return selected;
  }

}


class MainFrame_this_windowAdapter
    extends java.awt.event.WindowAdapter {
  TipiSwingFrameImpl adaptee;
  MainFrame_this_windowAdapter(TipiSwingFrameImpl adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
