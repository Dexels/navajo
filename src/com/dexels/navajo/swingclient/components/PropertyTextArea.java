package com.dexels.navajo.swingclient.components;


import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.swingclient.*;
//import com.dexels.sportlink.client.swing.components.*;

public class PropertyTextArea extends BaseTextArea implements PropertyControlled, Ghostable  {
  private String textValue;
  private Property initProperty = null;
  ResourceBundle res;
  private String toolTipText = "";
  private boolean ghosted = false;
  private boolean enabled = true;


  public void setProperty(Property p){
    initProperty = p;
    textValue = (String)p.getValue();
    setText(textValue);
    try{
      toolTipText = res.getString(p.getName());
      setToolTipText(toolTipText);
    }catch(MissingResourceException e){
      toolTipText = p.getName();
      setToolTipText(toolTipText);
    }
    setEditable(p.isEditable());
  }

  public Property getProperty() {
    return initProperty;
  }

  public PropertyTextArea() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.properties");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void update(){
    if (initProperty==null) {
      return;
    }
    textValue = getText();
    if(textValue != null){
      initProperty.setValue(textValue);
    }
  }

  private void jbInit() throws Exception {
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
    SwingClient.getUserInterface().setStatusText(toolTipText);
  }

  void this_focusLost(FocusEvent e) {
    textValue = getText();
    if(textValue != null){
      initProperty.setValue(textValue);
    }
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