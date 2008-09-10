package com.dexels.navajo.tipi.swingclient.components;


import java.awt.*;
import javax.swing.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.components.*;
import javax.swing.event.*;
import java.beans.PropertyVetoException;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.mousegestures.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BaseWindow extends JInternalFrame implements MouseGestureListener {

  protected JPanel mainPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  private Component myOldGlassPane = null;
  MouseGestureParser mgp;
  ImageIcon myIcon = new ImageIcon(UserInterface.class.getResource("images/logo_mini.gif"));
  protected boolean embryo = true;

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

  public BaseGlassPane createGlassPane() {
    SportlinkBusyPanel bg = new SportlinkBusyPanel();
//    mgp = new MouseGestureParser(bg);
//    mgp.addMouseGestureListener(this);
    return bg;
  }

  public BaseGlassPane getBaseGlassPane() {
    if (getRootPane().getGlassPane()==null) {
      return createGlassPane();
    }
    if (!BaseGlassPane.class.isInstance(getRootPane().getGlassPane())) {
      myOldGlassPane = getRootPane().getGlassPane();
      BaseGlassPane bg = createGlassPane();
      getRootPane().setGlassPane(bg);
      return bg;
    }
    return (BaseGlassPane)getRootPane().getGlassPane();
  }

  public void windowHasClosed() {
  }

  private final void jbInit() throws Exception {
    mainPanel.setLayout(borderLayout1);
    mainPanel.setBackground(SystemColor.controlLtHighlight);

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
  @Override
protected void paintComponent(Graphics parm1) {
    super.paintComponent( parm1);
  }
  public void showWindow() {
    SwingClient.getUserInterface().addFrame(this);
  }

  void this_internalFrameClosed(InternalFrameEvent e) {
    try {
      closeWindow();
    }
    catch (PropertyVetoException ex) {
    }
  }

  public boolean isEmbryo() {
   return embryo;
 }

 public void deactivateFrame(){
 }

  public void setEmbryo(boolean embryo) {
    this.embryo = embryo;
  }


}
