package com.dexels.navajo.swingclient.components;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.components.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.swingclient.*;
import javax.swing.event.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BaseWindow extends JInternalFrame {

  protected JPanel mainPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
//  private BaseGlassPane myGlassPane = new BaseGlassPane();
  ImageIcon myIcon = new ImageIcon(UserInterface.class.getResource("images/app-icon2.gif"));
  public BaseWindow() {
    super();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void commit() {
  }

  public void setCursor(int type) {
    this.getContentPane().setCursor(Cursor.getPredefinedCursor(type));
  }

  public void closeWindow() {
    SwingClient.getUserInterface().closeWindow(this);
    setVisible(false);
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

  public void windowHasClosed() {
  }

  private void jbInit() throws Exception {
    mainPanel.setLayout(borderLayout1);
    mainPanel.setBackground(SystemColor.controlLtHighlight);
    this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
      public void internalFrameClosed(InternalFrameEvent e) {
        this_internalFrameClosed(e);
      }
    });
    getContentPane().add(mainPanel);
    this.setClosable(true);
    this.setIconifiable(true);
    this.setMaximizable(true);
    this.setResizable(true);
    this.setFrameIcon(myIcon);
  }
  protected void paintComponent(Graphics parm1) {
    super.paintComponent( parm1);
  }
  public void showWindow() {
    SwingClient.getUserInterface().addFrame(this);
  }

  void this_internalFrameClosed(InternalFrameEvent e) {
    closeWindow();
  }

}