package com.dexels.navajo.swingclient.components;

import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.*;
import java.awt.*;
//import com.dexels.sportlink.client.swing.components.validation.*;
import com.dexels.navajo.swingclient.*;

//import com.dexels.sportlink.client.swing.components.*;
import javax.swing.*;
import com.dexels.navajo.document.nanoimpl.*;
import javax.swing.text.*;
import javax.swing.event.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class PropertyField extends BaseField implements PropertyControlled, Ghostable {

  private String textValue;
  private boolean ghosted = false;
  private Property initProperty = null;
  ResourceBundle res;
  private String toolTipText = "";
//  ConditionErrorParser cep = new ConditionErrorParser();
  private boolean enabled = true;
  Document myDoc = null;
  public PropertyField() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.properties");
      myDoc = new PlainDocument();
     this.setDocument(myDoc);
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
//    System.err.println("SETTING PROPERTY STATE!!!");
    setValidationState(BaseField.VALID);
    if (p==null) {
      System.err.println("Setting to null property. Ignoring");
      return;
    }

    initProperty = p;
    textValue = (String)p.getValue();

    // Trim the value
    if(textValue != null){
      textValue = textValue.trim();
    }

    setText(textValue);

    if(getValidationState() == BaseField.VALID){
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
    setEditable(p.isDirIn());

    setChanged(false);
  }

  private void jbInit() throws Exception {
    myDoc.addDocumentListener(new javax.swing.event.DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        myDoc_insertUpdate(e);
      }
      public void removeUpdate(DocumentEvent e) {
        myDoc_removeUpdate(e);
      }
      public void changedUpdate(DocumentEvent e) {
        myDoc_changedUpdate(e);
      }
    });
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
    textValue = getText();
    if (initProperty!=null) {
//      System.err.println("Setting value of property to: "+textValue);
      initProperty.setValue(textValue);
    }
  }

  public void this_focusGained(FocusEvent e) {
//    System.err.println("PF focuslost");
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
//    System.err.println("Updating...");
    if (initProperty==null) {
      return;
    }
    textValue = getText();
//    System.err.println("Setting value of property to: "+textValue);
    if(textValue != null){
      initProperty.setValue(textValue);
    }
  }

  public void setValidationMessageName(String name){
/** @todo Fix this one again */
//    initProperty.setMessageName(name);
  }


  public void checkValidation(Message msg){
    setValidationState(BaseField.VALID);
  }

  public boolean isGhosted() {
    return ghosted;
  }

  public void setGhosted(boolean g) {
    ghosted = g;
//    System.err.println("Setting enabled for field: "+(enabled && (!ghosted)));
    super.setEnabled(enabled && (!ghosted));
  }

  public void setEnabled(boolean e) {
    enabled = e;
    super.setEnabled(enabled && (!ghosted));
  }

  void myDoc_changedUpdate(DocumentEvent e) {
    update();
  }

  void myDoc_insertUpdate(DocumentEvent e) {
    update();
  }

  void myDoc_removeUpdate(DocumentEvent e) {
    update();
  }


}
