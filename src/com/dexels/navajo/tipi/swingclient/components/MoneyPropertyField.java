package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.text.*;
import java.text.*;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.document.*;

import javax.swing.*;

import java.awt.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 * @deprecated
 */

public class MoneyPropertyField extends PropertyField {
  private boolean isEditing = false;
//  private Property myProperty = null;
//  private Money myValue = null;
  protected Document myDocument = null;
  protected boolean readOnly = false;

  public MoneyPropertyField() {
	myDocument =  new MoneyNumberDocument();
	setDocument(myDocument);
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  final void doFocusLost(FocusEvent e) {
//    System.err.println("FOCUS LOST, IN MONEY!!!");
    if (!isEditing) {
      return;
    }
    isEditing = false;
    ((MoneyNumberDocument)getDocument()).setFocused(false);


    System.err.println("MY TEXT: "+getText());
      Money m = new Money(getText());
      getProperty().setAnyValue(m);
      setText(m.formattedString());
    updateColor(m);
  }
  public final void setReadOnly(boolean b) {
	    readOnly = b;
	  }
  
//  public String getText() {
//	  System.err.println("IN GETTEXT MONEY FIELD....: "+super.getText());
//	  Thread.dumpStack();
//	  return super.getText();
//  }
//  
//  public void setText(String s) {
//	  System.err.println("Setting text in money: "+s);
//	  Thread.dumpStack();
//	  super.setText(s);
//	  
//  }
//  final void myDocument_changedUpdate(DocumentEvent e) {
//
//  }
//
//  final void myDocument_insertUpdate(DocumentEvent e) {
//  }
//
//  final void myDocument_removeUpdate(DocumentEvent e) {
//  }
//

//  final public void update() {
//	//  super.update();
//    //System.err.println("Update in moneyproperty");
//    //isEditing = true;
//    doFocusLost(null);
//      if(getProperty() != null){
//        if ("".equals(getText())) {
//          getProperty().setValue(new Money());
//        } else {
//        getProperty().setValue(myValue);
//        }
//      }
//  }
  public void update() {
	  doFocusLost(null);
  }

  final void this_focusGained(FocusEvent e){
    if (isEditing) {
      return;
    }
    
    if(getProperty()==null) {
    	return;
    }
    System.err.println("---------------->> FOCUSGAINED!");
    isEditing = true;
    Money mm = (Money) getProperty().getTypedValue();
    if(mm==null) {
    	setText("-");
    } else {
        setText(mm.editingString());
    }
      selectAll();
  }

  public final void setProperty(Property p) {
//    super.setProperty(p);
	     if(getForcedAlignment()==null) {
	    	  setHorizontalAlignment(JTextField.RIGHT);
	      }
    if (p == null) {
      return;
    }
    initProperty = p;

    if (!(p.getType().equals(Property.MONEY_PROPERTY)||p.getType().equals(Property.EXPRESSION_PROPERTY))) {
      try {
        System.err.println("PROPERTY: " + p.getFullPropertyName() + " is not of money type! Value: "+p.getValue() + ", type: " + p.getType());
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
      //Toolkit.getDefaultToolkit().beep();
    }
    Object val = p.getTypedValue();
    Money mon = (Money)val;
//    System.err.println("Mon: "+mon);
//    if (mon!=null) {
//      System.err.println("str: "+mon.toString());
//      System.err.println("formstr: "+mon.formattedString());
//    } else {
//      System.err.println("oops!");
//    }
//    myValue = mon;
    if (val==null) {
      setText("0");
      mon = new Money(0);
    }
//      setValue(mon);
    try {
      myDocument.remove(0, myDocument.getLength());
      myDocument.insertString(0, "" + mon.formattedString(), null);
//      setColumns(myDocument.getLength());
    }
    catch (BadLocationException ex1) {
      ex1.printStackTrace();
//      System.err.println("Oh dear");
    }
    String toolTipText = "";
    setEditable(p.isDirIn());
    //setEnabled(p.isDirIn());
    try{
      if(res != null){
        toolTipText = res.getString(p.getName());
        this.setToolTipText(toolTipText);
      }
    }catch(MissingResourceException e){
      toolTipText = p.getDescription();
      if(toolTipText != null && !toolTipText.equals("")){
        this.setToolTipText(toolTipText);
      }else{
        this.setToolTipText(p.getName());
      }
    }
    //super.setProperty(p);
    updateColor(mon);
  }

private void updateColor(Money value) {
	setOriginalForeground(value.doubleValue()<0?Color.RED:Color.BLACK);
    setOriginalDisabledColor(value.doubleValue()<0?Color.PINK.darker():Color.GRAY);

    setForeground(getOriginalForeground());
    setDisabledTextColor(getOriginalDisabledColor());
}

public void focusLost(FocusEvent e) {
	// this is actually important!
  }



  protected void jbInit() throws Exception {

    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        doFocusLost(e);
      }
      public void focusGained(FocusEvent e){
        this_focusGained(e);
      }
    });
  }

}

class MoneyNumberDocument extends PlainDocument {
  boolean hasFocus = false;

  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    if (hasFocus) {
      insertFocusString(offs,str,a);
    } else {
      insertNonFocusString(offs,str,a);
    }
  }

  private final void insertFocusString(int offs, String str, AttributeSet a) throws BadLocationException {
    char[] source = str.toCharArray();
    char[] result = new char[source.length];
    int j = 0;
    DecimalFormat nf = (DecimalFormat)DecimalFormat.getCurrencyInstance();
    char decSep = nf.getDecimalFormatSymbols().getDecimalSeparator();
    for (int i = 0; i < result.length; i++) {
      if (Character.isDigit(source[i]) || (i==0 && source[i] == '-') || source[i]==decSep || source[i]=='.') {
        result[j++] = source[i];
      }
      else {
//        Toolkit.getDefaultToolkit().beep();
//        System.err.println("Invalid char (not numeric) in string:  "+str);
      }
    }
//    System.err.println("MONEY: "+ new String(result, 0, j));
    super.insertString(offs, new String(result, 0, j), a);
  }
  private final void insertNonFocusString(int offs, String str, AttributeSet a) throws BadLocationException {
     super.insertString(offs, str, a);

  }


  public void setFocused(boolean b) {
    hasFocus = b;
  }
}
