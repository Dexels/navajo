package com.dexels.navajo.swingclient.components;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.components.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.nanoclient.NavajoLoadable;
import com.dexels.navajo.swingclient.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BaseDialog extends JDialog implements NavajoLoadable {
  protected JPanel mainPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
//  BaseGlassPane myGlassPane = new BaseGlassPane();

  public BaseDialog() {
    super(SwingClient.getUserInterface().getMainFrame());
    try {
      jBinit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
//    getRootPane().setGlassPane(myGlassPane);
    try {
      jbInit();
    }

    catch(Exception e) {
      e.printStackTrace();
    }
  }

  void jBinit() throws Exception {
    getContentPane().add(mainPanel);
    mainPanel.setLayout(borderLayout1);
    this.setResizable(true);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public void closeWindow() {
    setVisible(false);
  }
  public void init(Message msg) {
  }
  public void load(Message msg) {
  }
  public void store(Message msg) {
  }
  public void insert(Message msg) {
  }
  public BaseGlassPane createGlassPane() {
    BaseGlassPane bg = new BaseGlassPane();
    getRootPane().setGlassPane(bg);
    return bg;
  }

  public BaseGlassPane getBaseGlassPane() {
    if (getRootPane().getGlassPane()==null) {
      return createGlassPane();
    }
    if (!BaseGlassPane.class.isInstance(getRootPane().getGlassPane())) {
      BaseGlassPane bg = createGlassPane();
      getRootPane().setGlassPane(bg);
      return bg;
    }
    return (BaseGlassPane)getRootPane().getGlassPane();
  }

  private void jbInit() throws Exception {
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosed(WindowEvent e) {
        this_windowClosed(e);
      }
    });
  }

  void this_windowClosed(WindowEvent e) {

  }

}