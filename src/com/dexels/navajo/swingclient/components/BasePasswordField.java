package com.dexels.navajo.swingclient.components;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.document.*;
//import com.dexels.navajo.nanoclient.NavajoLoadable;
import java.beans.*;
import javax.swing.text.*;
import javax.swing.event.*;
//import com.dexels.sportlink.client.swing.components.validation.*;
import com.dexels.navajo.swingclient.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BasePasswordField extends JPasswordField  implements ChangeMonitoring, Validatable {

  ResourceBundle res;

  private String myName = "";
  private String myText = "";
  private int validationState = VALID;
  private ArrayList rules = new ArrayList();
  private Document myDocument = null;
  private Message validationMsg = null;
  private InputValidator iv = null;

  private boolean changed = false;
  public BasePasswordField() {
    myDocument = getDocument();
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.TextLabels");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setLabel(String s) {
    myName = res.getString(s);
    setToolTipText(res.getString(s));
  }

  public void setEnabled(boolean b) {
    super.setEnabled(b);
    //setBackground(b?Color.white:Color.lightGray);
  }

  private void jbInit() throws Exception {
    myDocument.addDocumentListener(new javax.swing.event.DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
        myDocument_insertUpdate(e);
      }
      public void removeUpdate(DocumentEvent e) {
        myDocument_removeUpdate(e);
      }
      public void changedUpdate(DocumentEvent e) {
        myDocument_changedUpdate(e);
      }
    });
    this.setPreferredSize(new Dimension(4, 25));
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
    SwingClient.getUserInterface().setStatusText(myName);
    this.selectAll();
  }

  void this_focusLost(FocusEvent e) {
//    if (!myText.equals(getText())) {
//      System.err.println("Setting changed = true");
//      changed = true;
//    }
//
//    SwingClient.getUserInterface().setStatusText("");
  }

//  public void init(Message msg) {
//  }
//  public void load(Message msg) {
//  }
//  public void store(Message msg) {
//  }
  public boolean hasChanged() {
    System.err.println("CHANGED: "+getClass().getName()+" "+changed);
    return changed;
  }
  public void setText(String t) {
    myText = t;
    super.setText( t);
    changed = false;
  }

  public void setChanged(boolean b) {
    revalidate();
    changed = b;
  }

  void myDocument_changedUpdate(DocumentEvent e) {
    setChanged(true);
  }

  void myDocument_insertUpdate(DocumentEvent e) {
    setChanged(true);
  }

  void myDocument_removeUpdate(DocumentEvent e) {
    setChanged(true);
  }

  public void setValidationState(int state){
    validationState = state;
    switch (validationState){
      case VALID:
        this.setBackground(Color.white);
//        System.err.println("============== Set to VALID =============");
        break;
      case INVALID:
        this.setBackground(Color.red);
        break;
      case EMPTY:
        this.setBackground(Color.blue);
        break;
      case ADJUSTED:
        this.setBackground(Color.yellow);
        break;
    }
  }
  //=======================================================================
  // Conditional errors validation
  //=======================================================================

  public void addValidationRule(int state){
    System.out.println("Adding validation rule: " + state);
    rules.add(new Integer(state));
  }

  public void clearValidationRules(){
    rules.clear();
  }

  public void setValidationMessage(Message msg){
    validationMsg = msg;
  }

  public void checkValidation(){
    if(validationMsg!=null && validationMsg.getName().equals("conditionerrors")){
      checkValidation(validationMsg);
    }else{
      setValidationState(VALID);
    }
  }

  public int getValidationState(){
    //System.err.println("Getting validation state: " + validationState);
    return validationState;
  }

  public void checkValidation(Message msg){
    System.err.println("Validating..");
    ArrayList conditions = msg.getAllMessages();
    for(int i=0;i<conditions.size();i++){
      Message current = (Message) conditions.get(i);
      Property conditionCode = current.getProperty("Id");
      int code = Integer.parseInt((String)conditionCode.getValue());
      for(int j=0;j<rules.size();j++){
        int rule = ((Integer)rules.get(j)).intValue();
        if(rule == code){
          setValidationState(INVALID);
          return;
        }
      }
    }
    setValidationState(VALID);
  }

  //============================================================================
  // Client-side validation
  //============================================================================

  public void setInputValidator(InputValidator inv){
    iv = inv;
  }

  public boolean doValidate(){
    if(iv != null && !iv.doValidate(new String(getPassword()))){
      setValidationState(INVALID);
      return false;
    }else{
      setValidationState(VALID);
      return true;
    }
  }

}