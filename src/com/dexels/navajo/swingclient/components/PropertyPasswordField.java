package com.dexels.navajo.swingclient.components;

import com.dexels.navajo.nanodocument.*;
import java.awt.event.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.*;
import java.awt.*;
//import com.dexels.sportlink.client.swing.components.validation.*;
import com.dexels.navajo.swingclient.*;
//import com.dexels.sportlink.client.swing.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class PropertyPasswordField extends BasePasswordField implements PropertyControlled, Ghostable {

  private String textValue;
  private PropertyImpl initProperty = null;
  ResourceBundle res;
  private String toolTipText = "";
  //ConditionErrorParser cep = new ConditionErrorParser();
  private boolean ghosted = false;
  private boolean enabled = true;

  public PropertyPasswordField() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.properties");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Property getProperty() {
    return initProperty;
  }

  public void setProperty(Property p){
    if (p==null) {
      System.err.println("Setting to null property. Ignoring");
      return;
    }

    initProperty = (PropertyImpl)p;
    textValue = (String)p.getValue();

    // Trim the value
    if(textValue != null){
      textValue = textValue.trim();
    }

    setText(textValue);
    try{
      toolTipText = res.getString(p.getName());
      setToolTipText(toolTipText);
    }catch(MissingResourceException e){
      toolTipText = p.getName();
      setToolTipText(toolTipText);
    }
    setChanged(false);
  }

  private void jbInit() throws Exception {
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        this_focusLost(e);
      }
      public void focusGained(FocusEvent e) {
        this_focusGained(e);
      }
    });
  }

  public void this_focusLost(FocusEvent e) {
    textValue = new String(getPassword());
    if (initProperty!=null) {
      initProperty.setValue(textValue);
    }

  }

  public void this_focusGained(FocusEvent e) {
    if(isEditable()){
      setValidationState(BaseField.VALID);
    }
    Component c = getParent();
    if(BasePanel.class.isInstance(c)){
      BasePanel parentPanel = (BasePanel)c;
      parentPanel.setFocus();
    }
    SwingClient.getUserInterface().setStatusText(toolTipText);
  }

  public void update(){
    if (initProperty==null) {
      return;
    }
    textValue = new String(getPassword());
    if(textValue != null){
      initProperty.setValue(textValue);
    }
  }

  public void setValidationMessageName(String name){
    initProperty.setMessageName(name);
  }


  public void checkValidation(Message msg){
    /*if(initProperty != null){
      String myName = initProperty.getFullPropertyName();
      System.err.println("Checking for: " + myName);
      ArrayList errors = cep.getFailures(msg);
      for(int i=0;i<errors.size();i++){
        String current = (String)errors.get(i);
        System.err.println("Failures: " + current);
        if((current.indexOf(myName) > -1)){
          setValidationState(BaseField.INVALID);
          return;
        }
      }
    }else{
      System.err.println("Warning uninitialized property");
    }*/
    setValidationState(BaseField.VALID);
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