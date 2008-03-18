package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.util.Map.Entry;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
import java.awt.*;
import javax.swing.border.*;

import com.dexels.navajo.tipi.swingclient.*;

//import com.dexels.navajo.document.nanoimpl.*;


/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public abstract class BaseComboBox
    extends JComboBox
    implements ChangeMonitoring, Validatable {
  ResourceBundle res;
  DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
  private String myName = "";
  private Map myMap = null;

  //========================================================================
  private ArrayList myConditionRuleIds = new ArrayList(); //!!

  // This is only implemented in Base(Password)Field and BaseCombobox!!!!!!!

  public BaseComboBox() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.TextLabels");
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
	public boolean isOpaque() {
		return true;
	}
  public void loadCombobox(Property p) {
    try {
      defaultComboBoxModel = new DefaultComboBoxModel();
      ArrayList a = p.getAllSelections();
      //removeAllItems();
      Selection selectedSelection = null;
      for (int i = 0; i < a.size(); i++) {
        Selection s = (Selection) a.get(i);
        defaultComboBoxModel.addElement(s);
        selectedSelection = (s.isSelected() ? s : selectedSelection);
      }
      setModel(defaultComboBoxModel);

      if (selectedSelection != null) {
        setToValue(selectedSelection);
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
        setToValue(s);
      }
    }
  }

  public void loadFromMap(Map m) {
    defaultComboBoxModel.removeAllElements();
    Iterator it = m.entrySet().iterator();
    while (it.hasNext()) {
      Entry e = (Entry) it.next();
      String value = (String) e.getKey();
      String label = (String) e.getValue();
      Selection s = NavajoFactory.getInstance().createSelection(null, label, value, false);
      addSelection(s);
    }
  }

  public void loadFromResourceBundle(ResourceBundle res) {
    Enumeration en = res.getKeys();
    while (en.hasMoreElements()) {
      String key = (String) en.nextElement();
      String value = res.getString(key);
      Selection s = NavajoFactory.getInstance().createSelection(null, value, key, false);
      addSelection(s);
    }

  }

  public void setLabel(String s) {
    myName = res.getString(s);
    setToolTipText(res.getString(s));
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

  public void addProperty(Property p) {
    System.err.println("Property added to combo box. I am unsure if this is wise");
    defaultComboBoxModel.addElement(p);
  }

  public void addSelection(Selection s) {
    defaultComboBoxModel.addElement(s);
  }

  private final void jbInit() throws Exception {
    this.setPreferredSize(new Dimension(125, ComponentConstants.PREFERRED_HEIGHT));
    this.setModel(defaultComboBoxModel);
    // Install the custom key selection manager
    this.setKeySelectionManager(new MyKeySelectionManager());
    this.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        this_focusGained(e);
      }

      public void focusLost(FocusEvent e) {
        this_focusLost(e);
      }
    });
  }

// This key selection manager will handle selections based on multiple keys.
  class MyKeySelectionManager
      implements JComboBox.KeySelectionManager {
    long lastKeyTime = 0;
    String pattern = "";

    public int selectionForKey(char aKey, ComboBoxModel model) {
// Find index of selected item
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

       // Get the current time
      long curTime = System.currentTimeMillis();

      // If last key was typed less than 500 ms ago, append to current pattern
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

  void this_focusGained(FocusEvent e) {
//    UserInterface ui = SwingClient.getUserInterface();
    setValidationState(Validatable.VALID);
//
//    if (ui != null) {
//      ui.setStatusText(myName);
//    }
  }

  void this_focusLost(FocusEvent e) {
  }

  private boolean changed = false;

  public void resetChanged() {
    changed = false;
  }

  public boolean hasChanged() {
    return changed;
  }

  public void setChanged(boolean b) {
    changed = b;
//    System.err.println("Changed -> "+b);
  }

  public void setToValue(Object o) {
    super.setSelectedItem(o);
    changed = false;
//    System.err.println("Changed -> false");
  }

  void this_actionPerformed(ActionEvent e) {
//    Selection s = (Selection)getSelectedItem();
  }

  public void addValidationRule(int state) {
    //NI
  }

  public void addConditionRuleId(String id) {
    // We only remember one.
    myConditionRuleIds.add(id);

  }

  public ArrayList getConditionRuleIds() {
    return myConditionRuleIds;
  }

}
