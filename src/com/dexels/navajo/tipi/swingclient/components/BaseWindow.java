package com.dexels.navajo.tipi.swingclient.components;


import java.awt.*;
import java.beans.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.tipi.swingclient.*;

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
  private Component myOldGlassPane = null;
  ImageIcon myIcon = new ImageIcon(UserInterface.class.getResource("images/logo_mini.gif"));

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

  public void closeWindow() throws PropertyVetoException {
      setMaximum(false);
      setClosed(true);

    SwingClient.getUserInterface().closeWindow(this);
    setVisible(false);
  }

  public Component getOldGlassPane(){
    return myOldGlassPane;
  }

  public void mouseGestureReckognized(String id){
    System.err.println("BaseWindow got: " + id);
  }



  public void windowHasClosed() {
  }

  private final void jbInit() throws Exception {
    mainPanel.setLayout(borderLayout1);
//    mainPanel.setBackground(SystemColor.controlLtHighlight);

    getContentPane().add(mainPanel);
    this.setClosable(true);
    this.setIconifiable(true);
    this.setMaximizable(true);
    this.setResizable(true);
    this.setFrameIcon(myIcon);

    this.addInternalFrameListener(new InternalFrameAdapter() {
      @Override
	public void internalFrameDeactivated(InternalFrameEvent e){
        deactivateFrame();
      }
    });

  }


  void this_internalFrameClosed(InternalFrameEvent e) {
    try {
      closeWindow();
    }
    catch (PropertyVetoException ex) {
    }
  }

 public void deactivateFrame(){
 }
}
