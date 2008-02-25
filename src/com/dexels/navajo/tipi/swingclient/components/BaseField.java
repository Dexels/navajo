package com.dexels.navajo.tipi.swingclient.components;

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
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.validation.*;


/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BaseField extends JTextField implements ChangeMonitoring, Validatable{
  ResourceBundle res;

//  private boolean isManagingFocus = true;
  private String myName = "";
//  private String myText = "";
  private int validationState = VALID;
  private ArrayList rules = new ArrayList();
//  private Document myDocument = null;
  private Message validationMsg = null;
  private InputValidator iv = null;
  private ArrayList myConditionRuleIds = new ArrayList();

  private boolean changed = false;
  public BaseField() {
//    myDocument = getDocument();
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

    //System.err.println("==================================> SetEnabled called in BaseField: " + this.getText());

    super.setEnabled(b);
    //setBackground(b?Color.white:Color.lightGray);
  }

  private final void jbInit() throws Exception {


    this.setPreferredSize(new Dimension(4, ComponentConstants.PREFERRED_HEIGHT));
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
//        SwingClient.getUserInterface().setStatusText(myName);
        selectAll();
      }
    });
  }


  public void resetChanged() {
    changed = false;
  }

  public boolean hasChanged() {
//    System.err.println("CHANGED: "+getClass().getName()+" "+changed);
    return changed;
  }

  public void setChanged(boolean b) {
    revalidate();
    changed = b;
  }

  /**
   * This method will check if the current text in the field has been changed compared to
   * the value of the property. It can be invoked multiple times, it will never clear the
   * 'changed' flag.
   * You should always call this method BEFORE updating the property 
   * (something like initProperty.setValue(getText())
   * @param p The property to check against. It is usually initProperty, from PropertyField
   */
  public void updateChanged(Property p) {
      // already changed, nothing to check.
      if (changed) {
        return;
      }
      if (p==null) {
        return;
      }
      if ("".equals(getText()) && p.getValue()==null) {
        return;
      }
      if (!getText().equals(p.getValue())) {
          
        setChanged(true);
    }
  }

  public boolean isValidated() {
    return getValidationState()==VALID;
  }

  public void setValidationState(int state){
    validationState = state;
    switch (validationState){
      case VALID:
        this.setBackground(Color.white);
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
//    System.err.println("Adding ConditionRule: " + id);
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
    if(validationMsg!=null && validationMsg.getName().equals("ConditionErrors")){
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
      int code = Integer.parseInt((String)conditionCode.getValue());
      for(int j=0;j<rules.size();j++){
        int rule = ((Integer)rules.get(j)).intValue();
        if(rule == code){
          setValidationState(INVALID);
          System.err.println("-------------------------------------------------------------------------------==>> I am invalid!! : " + this);
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
    if(iv != null && !iv.doValidate(getText())){
      setValidationState(INVALID);
      return false;
    }else{
      setValidationState(VALID);
      return true;
    }
  }

//  public void setIsManagingFocus(boolean value){
//    isManagingFocus = value;
//  }
//
  public boolean isManagingFocus(){
    return false;
  }
//


  public static void main(String args[]){
    JFrame f = new JFrame("aap");
    f.setSize(800,600);
    BaseField bf = new BaseField();


    bf.setText("klopgeestgeit");
    bf.setPreferredSize(new Dimension(80, 20));
    f.getContentPane().setLayout(new FlowLayout());
    f.getContentPane().add(bf);
    f.getContentPane().add(new JButton("aap"));
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setVisible(false);
  }


}
