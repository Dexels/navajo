package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.text.*;

import java.text.*;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.document.*;
import javax.swing.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

/**
 * Copy paste from money prop. field
 */

public final class PercentagePropertyField extends PropertyField {
  private boolean isEditing = false;
//  private Property myProperty = null;
  private Percentage myValue = null;
  protected Document myDocument = null;

  public PercentagePropertyField() {
    super();
	myDocument =  new PercentageNumberDocument();
	setDocument(myDocument);
  try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  final void doFocusLost(FocusEvent e) {
    if (!isEditing) {
      return;
    }
    isEditing = false;
    ((PercentageNumberDocument)getDocument()).setFocused(false);

    try{
      String text = getText();
      if (text == null || "".equals(text) || "-".equals(text)) {
        return;
      }
      System.err.println("BRAAAA: "+text);
      DecimalFormat numberInstance = (DecimalFormat) DecimalFormat.getInstance();
      numberInstance.setGroupingUsed(true);
      numberInstance.getDecimalFormatSymbols().setDecimalSeparator(',');
      Number value = numberInstance.parse(text);
      
      double doubleValue = value.doubleValue();
      Percentage perc = new Percentage(doubleValue/100);
      myValue = perc;
      getProperty().setValue(perc);
      System.err.println("FO_PERC: "+perc.formattedString()+" val: "+perc.doubleValue());
      setText(perc.formattedString());
    }catch(Exception ex){
      ex.printStackTrace();
    }


  }
  final void myDocument_changedUpdate(DocumentEvent e) {

  }

  final void myDocument_insertUpdate(DocumentEvent e) {
  }

  final void myDocument_removeUpdate(DocumentEvent e) {
  }


  public void update() {
//	  doFocusLost(null);
  }
  
  final void this_focusGained(FocusEvent e){
    if (isEditing) {
      return;
    }
//    System.err.println("---------------->> FOCUSGAINED!");
    isEditing = true;
    Number value;
    try {
      ( (PercentageNumberDocument) getDocument()).setFocused(true);
      value = NumberFormat.getPercentInstance().parse(getText());
    }
    catch (ParseException ex) {
//      ex.printStackTrace();
      System.err.println("Parse error");
      setText("");
      return;
    }
      Percentage mon = new Percentage(value.doubleValue()*100);
      DecimalFormat nf = (DecimalFormat)DecimalFormat.getInstance();
      nf.getDecimalFormatSymbols().setDecimalSeparator(',');
       String myVal = nf.format(mon.doubleValue());

      setText(myVal);
      setChanged(true);
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

    if (!(p.getType().equals(Property.PERCENTAGE_PROPERTY)||p.getType().equals(Property.EXPRESSION_PROPERTY))) {
      try {
        System.err.println("PROPERTY: " + p.getFullPropertyName() + " is not of percentage type! Value: "+p.getValue() + ", type: " + p.getType());
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
      //Toolkit.getDefaultToolkit().beep();
    }
    Object val = p.getTypedValue();
    Percentage perc = null;
    if (val==null) {
      setText("0");
      perc = new Percentage(0);
    } else {
//      System.err.println("In percentage: "+val.toString()+" ::: "+val.getClass());
      if (!Percentage.class.isInstance(val)) {
//        System.err.println("Wrong type in percentagefield. Found: "+val.getClass()+" instead of Percentage!");
        if (String.class.isInstance(val)) {

            try {
              double doubleVal = Double.parseDouble( (String) val);
              perc = new Percentage(doubleVal/100);
            }
            catch (NumberFormatException ex2) {
              perc = new Percentage(0);
            }
          } else {
             perc = new Percentage(0);
          }

      } else {
        perc = (Percentage)val;
        
        myValue = perc;
      }

    }
//      setValue(mon);
    try {
      myDocument.remove(0, myDocument.getLength());
      myDocument.insertString(0, "" + perc.formattedString(), null);
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
  }





  protected final void jbInit() throws Exception {
    myDocument.addDocumentListener(new javax.swing.event.DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
        myDocument_changedUpdate(e);
      }
      public void insertUpdate(DocumentEvent e) {
        myDocument_insertUpdate(e);
      }
      public void removeUpdate(DocumentEvent e) {
        myDocument_removeUpdate(e);
      }
    });
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

/**
 * Copy paste from money prop. field
 */

final class PercentageNumberDocument extends PlainDocument {
  boolean hasFocus = false;

  public final void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
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
    DecimalFormat nf = (DecimalFormat)DecimalFormat.getPercentInstance();
    char decSep = nf.getDecimalFormatSymbols().getDecimalSeparator();
    for (int i = 0; i < result.length; i++) {
      if (Character.isDigit(source[i]) || (i==0 && source[i] == '-') || source[i]==decSep || source[i]=='.') {
        result[j++] = source[i];
      }
    }
    super.insertString(offs, new String(result, 0, j), a);
  }

  private final void insertNonFocusString(int offs, String str, AttributeSet a) throws BadLocationException {
     super.insertString(offs, str, a);
  }


  public final void setFocused(boolean b) {
    hasFocus = b;
  }
}
