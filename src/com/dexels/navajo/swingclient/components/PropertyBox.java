package com.dexels.navajo.swingclient.components;
import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.components.*;
import com.dexels.navajo.swingclient.*;

public class PropertyBox extends BaseComboBox implements PropertyControlled, Ghostable {
  ResourceBundle res;

  private boolean ghosted = false;
  private boolean enabled = true;
  private Property myProperty = null;
  private Property myValueProperty = null;
  public PropertyBox() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.properties");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Property getProperty() {
    if (myValueProperty!=null) {
      return myValueProperty;
    }
    return myProperty;
  }

  public void update(){
    // required method
  }

  public void loadProperty(Property p) {
    if (p.getType().equals(Property.SELECTION_PROPERTY)) {
      myProperty = p;
      loadCombobox(p);
    } else {
      System.err.println("Attempting to load property box from non-selection property");
    }
    String toolTipText;
    try{
      if (res!=null) {
        toolTipText = res.getString(p.getName());
        setToolTipText(toolTipText);
      }
      else {
        toolTipText = p.getName();
        setToolTipText(toolTipText);
      }

    }catch(MissingResourceException e){
      toolTipText = p.getName();
      setToolTipText(toolTipText);
    }
  }

  public void setEditable(boolean b) {
    setEnabled(b);
  }

  public void setProperty(Property p) {
    if (p==null) {
      System.err.println("Resetting property to null.");
      myValueProperty = null;
      return;
    }

    if (p.getType().equals(Property.SELECTION_PROPERTY)) {
      //System.err.println("Reseting property box to a selection property: ");
      loadProperty(p);
      return;
//      myProperty = p;
//      loadCombobox(p);
    } else {
      if (myProperty == null) {
        System.err.println("Setting property to propertyBox without loading first!");
        //return;
      }
      myValueProperty = p;
      System.err.println("Value: "+(String)p.getValue());

      if(p.getValue() != null){
        setToKey(((String)p.getValue()).trim());
      }

      setEditable(p.isEditable());
    }
  }

  private void jbInit() throws Exception {
    this.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        this_itemStateChanged(e);
      }
    });
  }

  private void setSelectionProperty() {
    Selection s = (Selection)getSelectedItem();
    if (s==null) {
      return;
    }

    for (int i = 0; i < getItemCount(); i++) {
      Selection current = (Selection)getItemAt(i);
      if (current == s) {
        current.setSelected(true);
      }
      else {
        current.setSelected(false);
      }
    }

  }

  private void setValueProperty() {
    myValueProperty.setValue(getSelectedId());
    System.out.println("SetTO: " + getSelectedId());
  }

  public Selection getSelectedSelection() {
    Object o = super.getSelectedItem();
    if (Selection.class.isInstance(o)) {
      return (Selection)o;
    }
    System.err.println("Error: Can not return selection from box: Not of type Selection");
    return null;

  }

  public void setSelection(Selection s) {
    removeAllItems();
    addSelection(s);
    this.setToValue(s);
  }

  void this_itemStateChanged(ItemEvent e) {
    if (myProperty==null) {
      System.err.println("Property box changed before it was set!");
      //return;
    }
    if (myValueProperty==null) {
      setSelectionProperty();
    } else {
      setValueProperty();
    }
    setChanged(true);
  }

  public boolean isGhosted() {
    return ghosted;
  }

  public void setGhosted(boolean g) {
    ghosted = g;
    super.setEnabled(enabled && (!ghosted));
  }

  public void setEnabled(boolean e) {
    enabled = e;
    super.setEnabled(enabled && (!ghosted));
  }

}