package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.validation.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.text.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class PropertyField
    extends BaseField
    implements PropertyControlled, Ghostable,FocusListener {

  protected String textValue;
  private boolean ghosted = false;
  protected Property initProperty = null;
  ResourceBundle localResource;
  private String toolTipText = "";
  private boolean enabled = true;
  private String forcedAlignment = null;
  
  public PropertyField() {
    try {
      if (System.getProperty("com.dexels.navajo.propertyMap") != null) {
        localResource = ResourceBundle.getBundle(System.getProperty(
            "com.dexels.navajo.propertyMap"));
      }

    }
    catch (Throwable e) {
     }
    jbInit();
  }
  public Property getProperty() {
    return initProperty;
  }

  public void resetProperty() {
    setProperty(getProperty());
  }

  public String getForcedAlignment() {
	  return forcedAlignment;
  }
  
  public void setForcedAlignment(String align) {
	 // System.err.println("PropertyField: align: "+align);
	  String old = forcedAlignment;
	  forcedAlignment = align;
	  if("left".equals(align)) {
		  setHorizontalAlignment(JTextField.LEFT);
	  }
	  if("right".equals(align)) {
		  setHorizontalAlignment(JTextField.RIGHT);
	  }
	  if("center".equals(align)) {
		  setHorizontalAlignment(JTextField.CENTER);
	  }
	  if(align!=null) {
		  if(!align.equals(old)) {
			  firePropertyChange("forcedAlignment", old, align);
		  }
	  }
  }
  public void gainFocus(){
    selectAll();
  }

  
  public Dimension getMinimumSize() {
      return getPreferredSize();
  }
  public void setProperty(Property p) {
    setValidationState(BaseField.VALID);
    if (p == null) {
      //System.err.println("Setting to null property. Ignoring");
      return;
    }
    initProperty = p;
//    textValue = (String) p.getValue();
//    setText(textValue);
    // Validation and enabled settings
    if (getValidationState() == BaseField.VALID) {
      setDescription();
    }
    setEditable(p.isDirIn());
    if (p.isDirOut()) {
      setForeground(Color.darkGray);
      setBackground(SystemColor.control);
    }
//    textValue = p.getValue();

    setChanged(false);
  }

  protected void setDescription() {

    if (initProperty == null) {
      return;
    }
    if (localResource != null) {
      try {
        String toolTipText = localResource.getString(initProperty.getName());
        setToolTipText(toolTipText);
      }
      catch (Exception e) {
        String desc = initProperty.getDescription();
        String name = initProperty.getName();
        if (desc != null && !desc.equals("")) {
          setToolTipText(desc);
        }
        else {
          setToolTipText(name);
        }
      }
    }
    else {
      String desc = initProperty.getDescription();
      String name = initProperty.getName();
      if (desc != null && !desc.equals("")) {
        setToolTipText(desc);
      }
      else {
        setToolTipText(name);
      }
    }
  }


  private Color originalForegroundColor = null;
  public void setOriginalForeground(Color c) {
    originalForegroundColor = c;
  }

  public Color getOriginalForeground() {
//  	return Color.green;
    return originalForegroundColor==null?Color.BLACK:originalForegroundColor;
  }
  private Color originalDisabledColor = null;
  public void setOriginalDisabledColor(Color c) {
    originalDisabledColor = c;
  }

  public Color getOriginalDisabledColor() {
//  	return Color.yellow;
    return originalDisabledColor==null?Color.GRAY:originalDisabledColor;
  }

  private final void jbInit() {
    this.addFocusListener(this);
  }

//  public void setDocument(Document d) {
//    Document doc = super.getDocument();
//    if (doc != null) {
//      doc.removeDocumentListener(this);
//    }
//    super.setDocument(d);
//    d.addDocumentListener(this);
//  }

  public void focusLost(FocusEvent e) {
    textValue = getText();
    if (initProperty != null && !initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
      initProperty.setValue(textValue);
    }
  }

  public void focusGained(FocusEvent e) {
    if (isEditable()) {
      setValidationState(BaseField.VALID);
      setDescription();
    }
    Component c = getParent();
    if (BasePanel.class.isInstance(c)) {
      BasePanel parentPanel = (BasePanel) c;
      parentPanel.setFocus();
    }
//    setCaretPosition(getText().length());
    selectAll();
//    SwingClient.getUserInterface().setStatusText(toolTipText);
  }

  public void update() {
//    try {
//      commitEdit();
//    }
//    catch (ParseException ex) {
//      System.err.println("Parse problem.");
//      return;
//    }
//    System.err.println("New value: "+getValue());



    if (initProperty == null) {
      return;
    }
    textValue = getText();
    if (textValue != null && !initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
      initProperty.setValue(textValue);
    }
  }

  public void updateProperty() {
  }

  public void setValidationMessageName(String name) {
    /** @todo Fix this one again, mmm.. needed? */
  }

  public void checkValidation(Message msg) {
    try {
      super.checkValidation(msg);
      if (getProperty() != null) {
        ConditionErrorParser cep = new ConditionErrorParser();
        ArrayList failures = cep.getFailures(msg);
        if (failures != null) {
          for (int i = 0; i < failures.size(); i++) {
            String failedPropName = (String) failures.get(i);
            if (failedPropName.equals(getProperty().getFullPropertyName())) {
              this.setValidationState(BaseField.INVALID);
              System.err.println("Failed Property: " + failedPropName);
              return;
            }
          }
        }
      }
      this.setValidationState(BaseField.VALID);
    }
    catch (Exception e) {
      e.printStackTrace();
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

//  public void changedUpdate(DocumentEvent e) {
//    //update();
//  }
//
//  public void insertUpdate(DocumentEvent e) {
//    //update();
//  }
//
//  public void removeUpdate(DocumentEvent e) {
//    //update();
//  }
}
