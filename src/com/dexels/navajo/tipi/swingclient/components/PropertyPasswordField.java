package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.*;
import java.awt.*;
//import com.dexels.sportlink.client.swing.components.validation.*;
//import com.dexels.navajo.document.nanoimpl.*;
//import com.dexels.sportlink.client.swing.components.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.validation.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class PropertyPasswordField extends BasePasswordField implements PropertyControlled, Ghostable {

  public String textValue;
  public Property initProperty = null;
  public ResourceBundle localResource;
  public String toolTipText = "";
  //ConditionErrorParser cep = new ConditionErrorParser();
  private boolean ghosted = false;
  private boolean enabled = true;

  public PropertyPasswordField() {
    try {
      String propMap = System.getProperty("com.dexels.navajo.propertyMap");
	if(propMap != null){
        localResource = ResourceBundle.getBundle(propMap);
      }
    }
    catch(Exception e) {
    	System.err.println("No property permission.");
    }
    try {
		jbInit();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

  }

  public Property getProperty() {
    return initProperty;
  }

  public void gainFocus(){
    // gar nichts
  }


  public void checkValidation(Message msg) {
    try{
      //setValidationState(BaseField.VALID);
      super.checkValidation(msg);
      if (getProperty() != null) {
        ConditionErrorParser cep = new ConditionErrorParser();
        ArrayList failures = cep.getFailures(msg);
        if(failures != null){
          for (int i = 0; i < failures.size(); i++) {
            String failedPropName = (String) failures.get(i);
//            System.err.println("myProp    : " + getProperty().getFullPropertyName());
//            System.err.println("failedProp: " + failedPropName);
            if (failedPropName.equals(getProperty().getFullPropertyName())) {
              this.setValidationState(BaseField.INVALID);
              return;
            }
          }
        }
      }
      this.setValidationState(BaseField.VALID);
    }catch(Exception e){
      e.printStackTrace();
    }
  }


  public void setProperty(Property p){
    if (p==null) {
      //System.err.println("Setting to null property. Ignoring");
      return;
    }

    initProperty = p;
    textValue = (String)p.getTypedValue();

    // Trim the value
    if(textValue != null){
      textValue = textValue.trim();
    }

    setText(textValue);
    setEnabled(p.isDirIn());
    setEditable(p.isDirIn());
      setBackground(p.isDirIn()?Color.white:SystemColor.control);

    try{
      if(localResource != null){
        toolTipText = localResource.getString(p.getName());
      }
      setToolTipText(toolTipText);
    }catch(MissingResourceException e){
      if((toolTipText = p.getDescription()) != null){
        setToolTipText(toolTipText);
      }else{
        toolTipText = p.getName();
        setToolTipText(toolTipText);
      }

    }
    setChanged(false);
  }

  private final void jbInit() throws Exception {
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
//    SwingClient.getUserInterface().setStatusText(toolTipText);
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
//    initProperty.setMessageName(name);  // What is this, remove..
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
