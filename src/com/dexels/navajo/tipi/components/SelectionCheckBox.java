package com.dexels.navajo.tipi.components;

import javax.swing.JCheckBox;
import com.dexels.navajo.document.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SelectionCheckBox extends JCheckBox {

  Selection mySelection;

  public SelectionCheckBox() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setSelection(Selection s){
    mySelection = s;
    this.setText(s.getName());
  }
  private void jbInit() throws Exception {
    this.addItemListener(new SelectionCheckBox_this_itemAdapter(this));
  }

  void this_itemStateChanged(ItemEvent e) {
    mySelection.setSelected(isSelected());
  }

}

class SelectionCheckBox_this_itemAdapter implements java.awt.event.ItemListener {
  SelectionCheckBox adaptee;

  SelectionCheckBox_this_itemAdapter(SelectionCheckBox adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.this_itemStateChanged(e);
  }
}