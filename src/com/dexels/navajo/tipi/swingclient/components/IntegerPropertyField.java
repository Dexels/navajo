package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;

import com.dexels.navajo.document.*;
import java.awt.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public final class IntegerPropertyField
    extends PropertyField
    implements PropertyControlled, Validatable {
//  private Property myProperty;
  private WholeNumberDocument myDocument = null;

  
  private boolean longMode = false;
//  private int validationState = BaseField.VALID;
//  private ArrayList myConditionRuleIds = new ArrayList();
//  private ArrayList rules = new ArrayList();
//  private Message validationMsg;
  private ResourceBundle res;
  private boolean readOnly = false;

  public IntegerPropertyField() {
    try {
      if (System.getProperty("com.dexels.navajo.propertyMap") != null) {
        res = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
      }
    }
    catch (Exception e) {
    }
    try {
      myDocument = new WholeNumberDocument();
      setDocument(myDocument);
      jbInit();
      if(getForcedAlignment()==null) {
    	  setHorizontalAlignment(SwingConstants.RIGHT);
      }
      //      getDocument().addDocumentListener(new MyDocumentListener());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public IntegerPropertyField(boolean longMode) {
	  this();
	  this.longMode = true;
  }
  
  public void setLongMode(boolean longMode) {
	  this.longMode = longMode;
  }
  
  @Override
public final void setProperty(Property p) {

    if (p == null) {
      return;
    }

    super.setProperty(p);
//    setValue(new Integer(0));
    if (! (p.getType().equals(Property.LONG_PROPERTY) || p.getType().equals(Property.INTEGER_PROPERTY) || p.getType().equals(Property.EXPRESSION_PROPERTY))) {

      //Toolkit.getDefaultToolkit().beep();
    }
    Object val = p.getTypedValue();
    if (val == null) {
      setText("");
    }
    else {
    }
    setText("" + val);
    String toolTipText = "";
    setEditable(p.isDirIn());
    try {
      if (res != null) {
        toolTipText = res.getString(p.getName());
        this.setToolTipText(toolTipText);
      }
    }
    catch (MissingResourceException e) {
      toolTipText = p.getDescription();
      if (toolTipText != null && !toolTipText.equals("")) {
        this.setToolTipText(toolTipText);
      }
      else {
        this.setToolTipText(p.getName());
      }
    }
//    super.setProperty(p);
  }

  private final void jbInit() throws Exception {
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      @Override
	public void focusLost(FocusEvent e) {
        this_focusLost(e);
      }
    });
  }

  @Override
public final void focusLost(FocusEvent e) {
	  // No call to super. Good.
 }

  final void this_focusLost(FocusEvent e) {
//    try{
      updateChanged(initProperty);
    if (getText() == null || "".equals(getText()) && initProperty != null) {
      initProperty.setValue( (String)null);
      return;
    }
    try {
      if (initProperty != null) {
        //initProperty.setValue(getText());
    	  if (longMode) {
    	       initProperty.setAnyValue(new Long(Long.parseLong(getText())));
    	       		
		} else {
		       initProperty.setAnyValue(new Integer(Integer.parseInt(getText())));
		       
		}
     	  setChanged(true);
      }
    }
    catch (PropertyTypeException ex1) {
      if (longMode) {
    	  initProperty.setValue( (Long)null);
    	     		
	} else {
		 initProperty.setValue( (Integer)null);
	}
      setText("");
      Toolkit.getDefaultToolkit().beep();
      ex1.printStackTrace();
    }
  }

  @Override
public final void setGhosted(boolean b) {
    this.setEnabled(!b);
  }

  @Override
public final boolean isGhosted() {
    return!this.isEnabled();
  }

  public final void setReadOnly(boolean b) {
    readOnly = b;
  }

  @Override
public final void update() {
    super.update();
    if ( (initProperty == null) || readOnly) {
      return;
    }
    updateChanged(initProperty);
    try {
      initProperty.setValue(getText());

    }
    catch (PropertyTypeException ex1) {
      ex1.printStackTrace();
    }

    if (getText() != null && !getText().equals("")) {
      setValidationState(Validatable.VALID);
    }
  }

}

final class WholeNumberDocument
    extends PlainDocument {
  @Override
public final void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    char[] source = str.toCharArray();
    char[] result = new char[source.length];
    int j = 0;
    for (int i = 0; i < result.length; i++) {
      if (Character.isDigit(source[i]) || source[0] == '-') {
        result[j++] = source[i];
      }
    }
    super.insertString(offs, new String(result, 0, j), a);
  }
}
