package com.dexels.navajo.tipi.components;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import java.util.*;
import com.dexels.navajo.tipi.components.swing.TipiSwingPanel;
import com.dexels.navajo.tipi.TipiComponent;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyPanel extends JPanel {
  private Component currentComponent = null;
  private ConditionErrorParser cep = new ConditionErrorParser();
  private int labelWidth = 0;
  private int valign = JLabel.CENTER;
  private int halign = JLabel.LEADING;
//  private int height
  private int propertyWidth = 0;
  private boolean showLabel = true;
  private JLabel myLabel = null;
  BorderLayout borderLayout = new BorderLayout();
  private Map failedPropertyIdMap = null;
  private ResourceBundle res = null;


  public PropertyPanel() {
    try {
      res = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
    }
    catch (MissingResourceException ex) {
      System.err.println("No resourcemap found.");
    }

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void doLayout() {
    super.doLayout();
  }

  public void setPropertyComponent(Component c) {

    if (currentComponent == c) {
      return;
    }
    if (currentComponent!= null) {
      remove(currentComponent);
    }
    currentComponent = c;
    add(currentComponent,BorderLayout.CENTER);
  }

  public void setLabel(String s) {
    if (myLabel==null) {
      myLabel= new JLabel(s);
//      myLabel.setPreferredSize(new Dimension(100,20));
      add(myLabel,BorderLayout.WEST);
    } else {
      myLabel.setText(s);
    }
    if (labelWidth!=0) {
      setLabelIndent(labelWidth);
    }
    myLabel.setHorizontalAlignment(halign);
    myLabel.setVerticalAlignment(valign);
    myLabel.setVisible(showLabel);
  }

  public void showLabel(){
    showLabel = true;
    if(myLabel != null){
      myLabel.setVisible(showLabel);
    }
  }

  public void hideLabel() {
    showLabel = false;
    if(myLabel != null){
      remove(myLabel);
    }
    myLabel= null;
  }

  public void setVerticalLabelAlignment(int alignment) {
    valign = alignment;
    if (myLabel!=null) {
      myLabel.setVerticalAlignment(alignment);
    }
  }

  public void update(){
    // Bleh
  }

  public void setHorizontalLabelAlignment(int alignment) {
    halign = alignment;
    if (myLabel!=null) {
      myLabel.setHorizontalAlignment(alignment);
    }
  }

  public void checkForConditionErrors(Message msg){
    if(PropertyControlled.class.isInstance(currentComponent)){
      PropertyControlled pc = (PropertyControlled)currentComponent;
      String myName = pc.getProperty().getName();

      //System.err.println("Checking for: " + myName);

      ArrayList errors = cep.getFailures(msg);
      failedPropertyIdMap = cep.getFailedPropertyIdMap();
      for(int i=0;i<errors.size();i++){
        String current = (String)errors.get(i);
        String id = (String) failedPropertyIdMap.get(current);
//        System.err.println("Failures: " + current);
        if((current.indexOf(myName) > -1)){
          if(Validatable.class.isInstance(currentComponent)){
            Validatable f = (Validatable)currentComponent;
            f.setValidationState(BaseField.INVALID);
            f.setToolTipText(cep.getDescription(current));
            f.addConditionRuleId(id);
          }
//          if(IntegerPropertyField.class.isInstance(currentComponent)){  // Mmmm.. shouldn't be like this I guess
//            IntegerPropertyField f = (IntegerPropertyField)currentComponent;
//            f.setValidationState(BaseField.INVALID);
//            f.setToolTipText(cep.getDescription(current));
//            //f.setConditionRuleId(id);
//          }
          return;
        }
      }

    }
  }

  public void resetComponentValidationStateByRule(String id){
    //System.err.println("Looking if we should reset: " + id);
    if (failedPropertyIdMap != null && id != null) {
      Iterator it = failedPropertyIdMap.keySet().iterator();
      PropertyControlled pc = (PropertyControlled) currentComponent;
      String myName = pc.getProperty().getName();
      while (it.hasNext()) {
        String current = (String) it.next();
        if (current.indexOf(myName) > -1) {
          // I am invalid.
          String current_id = (String) failedPropertyIdMap.get(current);
          if (id.equals(current_id)) {
            if (Validatable.class.isInstance(currentComponent)) {
              Validatable f = (Validatable) currentComponent;
              f.setValidationState(BaseField.VALID);
              f.setToolTipText(getToolTipText(pc.getProperty()));
            }
            if (IntegerPropertyField.class.isInstance(currentComponent)) { // Mmmm.. shouldn't be like this I guess,..
              IntegerPropertyField f = (IntegerPropertyField) currentComponent;
              f.setValidationState(BaseField.VALID);
              f.setToolTipText(getToolTipText(pc.getProperty()));
            }
          }
        }
      }
    }
  }

  public String getToolTipText(Property p){
    String toolTip = "";
    if (p != null) {
      try {
        if (res != null) {
          toolTip = res.getString(p.getName());
          return toolTip;
        }else{
          toolTip = "unknown";
        }
      }
      catch (MissingResourceException e) {
        toolTip = p.getDescription();
        if (toolTip != null && !toolTip.equals("")) {
          return toolTip;
        }
        else {
          toolTip = p.getName();
          return toolTip;
        }
      }
    }else{
      toolTip = "unknown";
    }
    return toolTip;
  }

//  public void setSize(int x, int y) {
//    myLabel.setPreferredSize(new Dimension(x,y));
//  }

  public void setLabelIndent(int lindent) {
    labelWidth = lindent;
    if (myLabel==null) {
      return;
    }
    int height = myLabel.getPreferredSize().height;
    myLabel.setPreferredSize(new Dimension(lindent,height));
  }

  public int getLabelIndent() {
    return labelWidth;
  }

  public boolean isLabelVisible() {
    if (myLabel!=null) {
      return myLabel.isVisible();
    }
    return false;
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout);
//    myLabel = new JLabel(" ");
//    setPreferredSize(new Dimension(100,30));
  }
}