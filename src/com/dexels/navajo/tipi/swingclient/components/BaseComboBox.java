package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;



public abstract class BaseComboBox
    extends JComboBox
     {
  DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();

  public BaseComboBox() {
	    this.setPreferredSize(new Dimension(125, ComponentConstants.PREFERRED_HEIGHT));
	    this.setModel(defaultComboBoxModel);
	    // Install the custom key selection manager
	    this.setKeySelectionManager(new MyKeySelectionManager());
  }

  public void loadCombobox(Property p) {
    try {
      defaultComboBoxModel = new DefaultComboBoxModel();
      ArrayList<Selection> a = p.getAllSelections();
      Selection selectedSelection = null;
      for (int i = 0; i < a.size(); i++) {
        Selection s = a.get(i);
        defaultComboBoxModel.addElement(s);
        selectedSelection = (s.isSelected() ? s : selectedSelection);
      }
      setModel(defaultComboBoxModel);

      if (selectedSelection != null) {
    	  setSelectedItem(selectedSelection);
      }
      else {
        Selection s = (Selection) getSelectedItem();
        if (s != null) {
          s.setSelected(true);
        }
       }
    }
    catch (NavajoException ex) {
      ex.printStackTrace(System.err);
    }
  }

  public void loadCombobox(Message msg, String property) {
    Property p = msg.getProperty(property);
    loadCombobox(p);
  }

  public void setToKey(String key) {
    for (int i = 0; i < defaultComboBoxModel.getSize(); i++) {
      Selection s = (Selection) defaultComboBoxModel.getElementAt(i);
      if (s.getValue().equals(key)) {
    	  setSelectedItem(s);
      }
    }
  }


  public String getSelectedId() {
    Selection s = (Selection) getSelectedItem();
    if (s != null) {
      return s.getValue();
    }
    else {
      return null;
    }
  }


// This key selection manager will handle selections based on multiple keys.
  class MyKeySelectionManager
      implements JComboBox.KeySelectionManager {
    long lastKeyTime = 0;
    String pattern = "";

    public int selectionForKey(char aKey, ComboBoxModel model) {
      int selIx = 01;
      Object sel = model.getSelectedItem();
      if (sel != null) {
        for (int i = 0; i < model.getSize(); i++) {
          if (sel.equals(model.getElementAt(i))) {
            selIx = i;
            break;
          }
        }
      }

      long curTime = System.currentTimeMillis();

      if (curTime - lastKeyTime < 500) {
        pattern += ("" + aKey).toLowerCase();
      }
      else {
        pattern = ("" + aKey).toLowerCase();
      }

      // Save current time
      lastKeyTime = curTime;

      // Search forward from current selection
      for (int i = selIx + 1; i < model.getSize(); i++) {
        String s = model.getElementAt(i).toString().toLowerCase();
        if (s.startsWith(pattern)) {
          return i;
        }
      }

      // Search from top to current selection
      for (int i = 0; i < selIx; i++) {
        if (model.getElementAt(i) != null) {
          String s = model.getElementAt(i).toString().toLowerCase();
          if (s.startsWith(pattern)) {
            return i;
          }
        }
      }
      return -1;
    }
  }
}
