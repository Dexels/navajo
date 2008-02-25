package com.dexels.navajo.tipi.swingclient.components;
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

  private final void jbInit() throws Exception {
    this.setOpaque(false);
    this.setText("");
    this.setHorizontalTextPosition(SwingConstants.LEADING);
    this.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  public void resetChanged() {
    changed = false;
  }

  public boolean hasChanged() {
//    System.err.println("Checking haschanged: "+changed+" I am "+hashCode());
    return changed;
  }

  public void setChanged(boolean b) {
    changed = b;
  }

  private final void this_actionPerformed(ActionEvent e) {
    changed = true;
  }
  public void setSelected(boolean b) {
    super.setSelected( b);
    changed = false;
  }
}
