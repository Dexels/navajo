package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.dexels.navajo.document.*;

public class FloatPropertyField extends PropertyField implements PropertyControlled {
  protected Document myDocument = null;
  protected boolean readOnly = false;

  public FloatPropertyField() {

    try {
      myDocument = new FloatNumberDocument();
      setDocument(myDocument);
      jbInit();
      if(getForcedAlignment()==null) {
    	  setHorizontalAlignment(SwingConstants.RIGHT);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  @Override
public void setProperty(Property p) {

    initProperty = p;
    if(p == null) {
      return;
    }

    if(!(p.getType().equals(Property.FLOAT_PROPERTY) || p.getType().equals(Property.EXPRESSION_PROPERTY))) {
      //Toolkit.getDefaultToolkit().beep();
    }
    Object val = p.getTypedValue();
    String inText = null;
    if(val == null) {
    	inText = "";
    } else {
        inText = val.toString();
    }
    
    try {
        String text = myDocument.getText(0,myDocument.getLength());
        if(!text.equals("")) {
            double d = Double.parseDouble(text);
            text = ""+d;
        }
        if(text.equals(inText)) {
//        	System.err.println("Californiasoep");
        } else {
            myDocument.remove(0, myDocument.getLength());
            myDocument.insertString(0, "" + val, null);
        }
    } catch(BadLocationException ex1) {
      ex1.printStackTrace();
//      System.err.println("Oh dear");
    }
    String toolTipText = "";
    setEditable(p.isDirIn());
    super.setProperty(p);
  }

  protected void jbInit() throws Exception {
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
      @Override
	public void focusLost(FocusEvent e) {
        moneyFocusLost(e);
      }

      @Override
	public void focusGained(FocusEvent e) {
        this_focusGained(e);
      }
    });
  }

  void this_focusGained(FocusEvent e) {
    //aap
  }


//  public Property getProperty(){
//    if(initProperty.getType().equals(Property.EXPRESSION_PROPERTY)){
//      Thread.dumpStack();
//    }
//    return super.getProperty();
//  }

  final void moneyFocusLost(FocusEvent e) {
    try {
//      updateChanged(initProperty);
      if(getText() == null || "".equals(getText())) {
        return;
      }
      double d = Double.parseDouble(getText());
      if(initProperty != null) {
        initProperty.setValue(d);
//        setChanged(true);
      }
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void myDocument_changedUpdate(DocumentEvent e) {

  }

  void myDocument_insertUpdate(DocumentEvent e) {
    if(initProperty != null) {
      if(!initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
      }
      if(getText() != null && !getText().equals("")) {
//        setValidationState(Validatable.VALID);
      }
    }
  }

  void myDocument_removeUpdate(DocumentEvent e) {
    if(initProperty == null) {
      return;
    }

    if(!initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
//      updateChanged(initProperty);
//      initProperty.setValue(getText());
    }

  }

  public final void setReadOnly(boolean b) {
    readOnly = b;
  }

  @Override
public void update() {
//    super.update();
//    try {
//      commitEdit();
//    }
//    catch (ParseException ex) {
//      System.err.println("Parse problem.");
//      return;
//    }

    if((initProperty == null) || readOnly) {
      return;
    }
//    this.setFormatterFactory(JFormattedTextField);
    if(!"".equals(getText())) {
      double d = Double.parseDouble(getText());

//     System.err.println("Proceeding with update: "+getValue());
      if (initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
        return;
      }
//      updateChanged(initProperty);
      initProperty.setValue(Double.toString(d));
//      if (getText() != null && !getText().equals("")) {
//        setValidationState(Validatable.VALID);
//      }
    }
  }

@Override
public void focusLost(FocusEvent e) {
	// a REAL klopgeest aap bug
}
}


final class FloatNumberDocument extends PlainDocument {

  @Override
public final void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    char[] source = str.toCharArray();
    char[] result = new char[source.length];
    int j = 0;
//    boolean dot = false;
//    String oldVal = super.getText(0,getLength());
//    String start = oldVal.substring(0,offs);
//    String end = oldVal.substring(offs,oldVal.length());
//    String total = start+str+end;
//    System.err.println("OldVal: "+oldVal);
//    System.err.println("Start: "+start);
//    System.err.println("End: "+end);
//    System.err.println("Total: "+total);
    for(int i = 0;i < result.length;i++) {
      if(Character.isDigit(source[i])) {
        result[j++] = source[i];
      } else if(source[i] == ',' || source[i] == '.') {
//        if (dot == true) {
//          System.err.println("Second dot encountered:  " + str);
//        }
//        else {
//          dot = true;
        result[j++] = '.';
//        }
      } else {
//        Toolkit.getDefaultToolkit().beep();
//        System.err.println("Invalid char (not numeric) in string:  "+str);
      }
    }
    super.insertString(offs, new String(result, 0, j), a);
  }

  public static void main(String[] args){
    Double.parseDouble("4,3");
  }

}



//class WholeNumberDocument extends PlainDocument {
//  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
//    char[] source = str.toCharArray();
//    char[] result = new char[source.length];
//    int j = 0;
//    for (int i = 0; i < result.length; i++) {
//      if (Character.isDigit(source[i]) || source[i]=='.') {
//        result[j++] = source[i];
//      }
//      else {
//        Toolkit.getDefaultToolkit().beep();
//        System.err.println("Invalid char (not numeric) in string:  "+str);
//      }
//    }
//    super.insertString(offs, new String(result, 0, j), a);
//  }
//}
