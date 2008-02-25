package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;


/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class MultiSelectPropertyBox extends BaseComboBox
    implements PropertyControlled, Ghostable, Validatable {

  ResourceBundle myResource;

   private boolean ghosted = false;
   private boolean enabled = true;
   private Property myProperty = null;
   private Property myValueProperty = null;
   private Message validationMsg;
   private int myValidationState = Validatable.VALID;
   private ArrayList rules = new ArrayList();


   public MultiSelectPropertyBox() {
     try {
       try {
		if(System.getProperty("com.dexels.navajo.propertyMap") != null){
		     myResource = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
		   }
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       jbInit();
       setEditable(false);
       this.setRenderer(new MultiSelectBoxRenderer());
     }
     catch(Exception e) {
       e.printStackTrace();
     }
   }

   public void gainFocus(){
     // gar nichts
   }

   public final void setValidationMessage(Message msg) {
     validationMsg = msg;
   }
   public final void checkValidation(){
     if(validationMsg!=null && validationMsg.getName().equals("ConditionErrors")){
       checkValidation(validationMsg);
     }else{
       setValidationState(VALID);
     }
   }

   public final void checkValidation(Message msg){
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
   public final void addValidationRule(int state){
     System.out.println("Adding validation rule: " + state);
     rules.add(new Integer(state));
   }

   public final void clearValidationRules(){
     rules.clear();
   }

   public final void setValidationState(int state) {
     myValidationState = state;
     switch (myValidationState){
       case VALID:
   //      myConditionRuleIds.clear();
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

   public final Property getProperty() {
     if (myValueProperty!=null) {
       return myValueProperty;
     }
     return myProperty;
   }

   public final void update(){
     // required method
   }

   public final void loadProperty(Property p) {
     if (p.getType().equals(Property.SELECTION_PROPERTY)) {
       myProperty = p;
       try{
         ArrayList all = p.getAllSelectedSelections();
         for(int i=0;i<all.size();i++){
           Selection s = (Selection) all.get(i);
         }
       }catch(Exception e){
         e.printStackTrace();
       }

       loadCombobox(p);
     } else {
       System.err.println("Attempting to load property box from non-selection property");
     }
     setEnabled(p.isDirIn());
     if(p.isDirOut()){
       setForeground(Color.darkGray);
       setBackground(SystemColor.control);
     }
//    updateUI();
    String toolTipText;
     try{
       if (myResource!=null) {
         toolTipText = myResource.getString(p.getName());
         setToolTipText(toolTipText);
       }
       else {
         toolTipText = p.getName();
         setToolTipText(toolTipText);
       }

     }catch(MissingResourceException e){
       if((toolTipText = p.getDescription()) != null){
         setToolTipText(toolTipText);
       }else{
         toolTipText = p.getName();
         setToolTipText(toolTipText);
       }
     }
   }

   public final void setEditable(boolean b) {
     setEnabled(b);
   }

//  public void paint(Graphics g) {
//
//  }

   public final void setProperty(Property p) {
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

       if(p.getValue() != null){
         setToKey(((String)p.getValue()).trim());
       }
       setEnabled(p.isDirIn());
       if(p.isDirOut()){
         setForeground(Color.black);
         setBackground(SystemColor.control);
       }
//      updateUI();
//      setEditable(p.isDirIn());
     }
   }

   private final void jbInit() throws Exception {
     this.setRenderer(new PropertyCellRenderer());
     this.addItemListener(new java.awt.event.ItemListener() {
       public void itemStateChanged(ItemEvent e) {
//         this_itemStateChanged(e);
       }
     });
   }

   private final void setSelectionProperty() {
     Selection s = (Selection)getSelectedItem();
     if (s==null) {
       return;
     }
     for (int i = 0; i < getItemCount(); i++) {
       Selection current = (Selection)getItemAt(i);
       if (current != null && current.getValue() != null && current.getValue().equals(s.getValue())) {
         boolean b = current.isSelected();
         current.setSelected(!b);
       }
     }
//     setPopupVisible(true);

   }

   private final void setValueProperty() {
     myValueProperty.setValue(getSelectedId());
//    System.out.println("SetTO: " + getSelectedId());
   }

   public final Selection getSelectedSelection() {
     Object o = super.getSelectedItem();
     if (Selection.class.isInstance(o)) {
       return (Selection)o;
     }
     System.err.println("Error: Can not return selection from box: Not of type Selection");
     return null;

   }

   public final void setSelection(Selection s) {
     removeAllItems();
     addSelection(s);
     this.setToValue(s);
   }

   final void this_itemStateChanged(ItemEvent e) {
     if (myProperty==null) {
       System.err.println("Property box changed before it was set!");
       //return;
     }
     if (myValueProperty==null) {
       if(e.getStateChange() == 2){
         setSelectionProperty();
       }
     } else {
       setValueProperty();
     }
     setChanged(true);
   }

   public final boolean isGhosted() {
     return ghosted;
   }

   public final void setGhosted(boolean g) {
     ghosted = g;
     super.setEnabled(enabled && (!ghosted));
   }

   public final void setEnabled(boolean e) {
     enabled = e;
     super.setEnabled(enabled && (!ghosted));
   }


   public final int getValidationState() {
     return myValidationState;
   }

  /**
   * Causes the combo box to close its popup window.
   *
   * @todo Implement this javax.swing.JComboBox method
   */
  public void hidePopup() {
//    System.err.println("..errr..I'll hide u");
  }

}

