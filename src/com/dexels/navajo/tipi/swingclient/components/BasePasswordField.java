package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.document.*;
import javax.swing.text.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BasePasswordField extends JPasswordField  implements ChangeMonitoring, Validatable {

  
  private String myName = "";
  private String myText = "";
  private int validationState = VALID;
  private ArrayList rules = new ArrayList();
  private Document myDocument = null;
  private Message validationMsg = null;
  private InputValidator iv = null;
  private ArrayList myConditionRuleIds = new ArrayList();
  private boolean changed = false;
  public BasePasswordField() {
    myDocument = getDocument();
    this.setMinimumSize(new Dimension(4, ComponentConstants.PREFERRED_HEIGHT));
    try {
		jbInit();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
  }



  private final void jbInit() throws Exception {
    myDocument.addDocumentListener(new javax.swing.event.DocumentListener() {
      public void insertUpdate(DocumentEvent e) {
    	    setChanged(true);
   }
      public void removeUpdate(DocumentEvent e) {
    	    setChanged(true);
   }
      public void changedUpdate(DocumentEvent e) {
    	    setChanged(true);
   }
    });
     this.addFocusListener(new java.awt.event.FocusAdapter() {
      @Override
	public void focusGained(FocusEvent e) {
    	   selectAll();
     }
     
    });
  }

  public void resetChanged() {
    changed = false;
  }

  public boolean hasChanged() {
     return changed;
  }
  @Override
public void setText(String t) {
    myText = t;
    super.setText( t);
    changed = false;
  }

  public void setChanged(boolean b) {
    revalidate();
    changed = b;
  }

  public void setValidationState(int state){
    validationState = state;
    switch (validationState){
      case VALID:
        //myConditionRuleIds.clear();
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

  public void addConditionRuleId(String id){
    // We only remember one.
    myConditionRuleIds.add(id);

  }

  public ArrayList getConditionRuleIds(){
    return myConditionRuleIds;
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
    ArrayList conditions = msg.getAllMessages();
    for(int i=0;i<conditions.size();i++){
      Message current = (Message) conditions.get(i);
      Property conditionCode = current.getProperty("Id");
      int code = Integer.parseInt(conditionCode.getValue());
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
