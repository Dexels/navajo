package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class PropertyField
    extends JTextField
    implements PropertyControlled, FocusListener {

  protected String textValue;
  protected Property initProperty = null;
  private String forcedAlignment = null;
  
  public PropertyField() {
	  this.addFocusListener(this);
//	  this.setPreferredSize(new Dimension(4, ComponentConstants.PREFERRED_HEIGHT));
//	  setBackground(Color.green);
//	  setOpaque(false);
  }
  
  public boolean isManagingFocus(){
	    return false;
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
	  String old = forcedAlignment;
	  forcedAlignment = align;
	  if("left".equals(align)) {
		  setHorizontalAlignment(SwingConstants.LEFT);
	  }
	  if("right".equals(align)) {
		  setHorizontalAlignment(SwingConstants.RIGHT);
	  }
	  if("center".equals(align)) {
		  setHorizontalAlignment(SwingConstants.CENTER);
	  }
	  if(align!=null) {
		  if(!align.equals(old)) {
			  firePropertyChange("forcedAlignment", old, align);
		  }
	  }
  }

  
//public Dimension getMinimumSize() {
//      return getPreferredSize();
//  }
  public void setProperty(Property p) {
    if (p == null) {
      return;
    }
    initProperty = p;
      setDescription();
    setEditable(p.isDirIn());
  }
  
//	public boolean isOpaque() {
//		return true;
//	}
	
  protected void setDescription() {

    if (initProperty == null) {
      return;
    }

      String desc = initProperty.getDescription();
      String name = initProperty.getName();
      if (desc != null && !desc.equals("")) {
        setToolTipText(desc);
      }
      else {
        setToolTipText(name);
      }
    }


  public void focusLost(FocusEvent e) {
    textValue = getText();
    if (initProperty != null && !initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
      initProperty.setValue(textValue);
    }
  }

  public void focusGained(FocusEvent e) {
    if (isEditable()) {
      setDescription();
    }
    selectAll();
  }

  public void update() {
    if (initProperty == null) {
      return;
    }
    textValue = getText();
    if (textValue != null && !initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
      initProperty.setValue(textValue);
    }
  }

}
