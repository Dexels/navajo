package com.dexels.navajo.swingclient.components;
import javax.swing.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
import java.awt.*;
//import com.dexels.sportlink.client.swing.components.*;

public class BaseCheckBox extends JCheckBox implements ChangeMonitoring {

  private boolean changed = false;
  public BaseCheckBox() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setHorizontalTextPosition(SwingConstants.LEADING);
    this.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  public boolean hasChanged() {
    System.err.println("Checking haschanged: "+changed+" I am "+hashCode());
    return changed;
  }

  public void setChanged(boolean b) {
    System.err.println("Checkbox: Setting changed to: "+b+" I am: "+hashCode());
    changed = b;
  }

  private void this_actionPerformed(ActionEvent e) {
    changed = true;
  }
  public void setSelected(boolean b) {
    super.setSelected( b);
    changed = false;
  }
}