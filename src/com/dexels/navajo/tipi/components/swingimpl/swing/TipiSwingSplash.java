package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingSplash
    extends JWindow
    implements Runnable {
  JLabel imageLabel = new JLabel();
  Thread t;
  ImageIcon img;
  JProgressBar jProgressBar1 = new JProgressBar();
  private String myIconName;
  public TipiSwingSplash(String iconName) {
    myIconName = iconName;
    try {
      jbInit();
      t = new Thread(this);
      jProgressBar1.setForeground(Color.decode("#213075"));
      jProgressBar1.setBorderPainted(true);
      jProgressBar1.setIndeterminate(true);
      jProgressBar1.setStringPainted(true);
      jProgressBar1.setString("");
      this.
          t.start();
      setCentered();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void run() {
    try {
    }
    catch (Exception e) {
      this.hide();
    }
  }

  public void setInfoText(String info) {
    jProgressBar1.setString(info);
  }

  private void setCentered() {
    pack();
    Toolkit t = Toolkit.getDefaultToolkit();
    Dimension d = t.getScreenSize();
    if (img != null) {
      int x = (int) ( (d.getWidth() / 2) - (img.getIconWidth() / 2));
      int y = (int) ( (d.getHeight() / 2) - (img.getIconHeight() / 2));
      this.setLocation(x, y);
    }
  }

  private void jbInit() throws Exception {
    this.addWindowStateListener(new DefaultTipiSplash_this_windowStateAdapter(this));
    img = new ImageIcon(getClass().getClassLoader().getResource(myIconName));
    imageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
    jProgressBar1.setBorder(BorderFactory.createLineBorder(Color.black));
    imageLabel.setDebugGraphicsOptions(0);
    imageLabel.addComponentListener(new DefaultTipiSplash_imageLabel_componentAdapter(this));
    if (img != null) {
      imageLabel.setIcon(img);
      this.setSize(img.getIconWidth(), img.getIconHeight() + jProgressBar1.getHeight());
    }
    this.getContentPane().add(imageLabel, BorderLayout.CENTER);
    this.getContentPane().add(jProgressBar1, BorderLayout.SOUTH);
  }

  void this_windowStateChanged(WindowEvent e) {
    //System.err.println("Closing");
    System.exit(0);
  }

  void imageLabel_componentHidden(ComponentEvent e) {
    //System.err.println("Closing...");
    t = null;
    System.exit(0);
  }
}

class DefaultTipiSplash_this_windowStateAdapter
    implements java.awt.event.WindowStateListener {
  TipiSwingSplash adaptee;
  DefaultTipiSplash_this_windowStateAdapter(TipiSwingSplash adaptee) {
    this.adaptee = adaptee;
  }

  public void windowStateChanged(WindowEvent e) {
    adaptee.this_windowStateChanged(e);
  }
}

class DefaultTipiSplash_imageLabel_componentAdapter
    extends java.awt.event.ComponentAdapter {
  TipiSwingSplash adaptee;
  DefaultTipiSplash_imageLabel_componentAdapter(TipiSwingSplash adaptee) {
    this.adaptee = adaptee;
  }

  public void componentHidden(ComponentEvent e) {
    adaptee.imageLabel_componentHidden(e);
  }
}
