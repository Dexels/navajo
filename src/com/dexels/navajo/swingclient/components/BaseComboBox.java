package com.dexels.navajo.swingclient.components;

import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
import java.awt.*;
import javax.swing.border.*;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.document.nanoimpl.*;


/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BaseComboBox extends JComboBox implements ChangeMonitoring {
  ResourceBundle res;
  DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
  private String myName="";
  private Map myMap = null;

  public BaseComboBox() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.TextLabels");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void loadCombobox(Property p) {
    try {
      ArrayList a = p.getAllSelections();
      removeAllItems();
      for (int i = 0; i < a.size(); i++) {
        Selection s = (Selection) a.get(i);
        defaultComboBoxModel.addElement(s);
        if (s.isSelected()) {
          setToValue(s);
        }

      }
    }
    catch (NavajoException ex) {
    }
  }
   public void loadCombobox(Message msg, String property) {
    Property p = msg.getProperty(property);
    loadCombobox(p);
  }

//  public void loadComboboxDirect(Message msg, String property) {
//    Property p = msg.getProperty(property);
//    ArrayList a = p.getAllSelections();
//    for (int i = 0; i < a.size(); i++) {
//      Selection s = (Selection)a.get(i);
//      s.setName(s.toString());
//      defaultComboBoxModel.addElement(s);
//    }
//  }

  public void setToKey(String key) {
    for (int i = 0; i < defaultComboBoxModel.getSize(); i++) {
      Selection s = (Selection)defaultComboBoxModel.getElementAt(i);
      if (s.getValue().equals(key)) {
        setToValue(s);
      }

    }

  }

  public void loadFromMap(Map m) {
    defaultComboBoxModel.removeAllElements();
    Iterator it = m.keySet().iterator();
    while (it.hasNext()) {
      String value = (String)it.next();
      String label = (String)m.get(value);
      Selection s =  NavajoFactory.getInstance().createSelection(null,label,value,false);
      addSelection(s);
    }
  }

  public void loadFromResourceBundle(ResourceBundle res) {
    Enumeration en = res.getKeys();
    while (en.hasMoreElements()) {
      String key = (String)en.nextElement();
      String value = res.getString(key);
      Selection s =  NavajoFactory.getInstance().createSelection(null,value,key,false);
      addSelection(s);
    }

  }

  public void setLabel(String s) {
    myName = res.getString(s);
    setToolTipText(res.getString(s));
  }

  public String getSelectedId() {
    Selection s = (Selection)getSelectedItem();
    if(s != null){
      return s.getValue();
    }else{
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

  private void jbInit() throws Exception {
    this.setPreferredSize(new Dimension(125, 25));
    this.setModel(defaultComboBoxModel);
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

  void this_focusGained(FocusEvent e) {
    UserInterface ui = SwingClient.getUserInterface();
    if (ui!=null) {
      ui.setStatusText(myName);
    }


  }

  void this_focusLost(FocusEvent e) {
    UserInterface ui = SwingClient.getUserInterface();
    if (ui!=null) {
      ui.setStatusText("");
    }
  }
  private boolean changed = false;

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
}
