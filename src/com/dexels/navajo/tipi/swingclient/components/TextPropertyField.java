package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.event.*;
import javax.swing.text.*;
import com.dexels.navajo.document.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class TextPropertyField extends PropertyField {
  private BoundedLengthDocument myDocument = new BoundedLengthDocument();
  public TextPropertyField() {
    setDocument(myDocument);
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        lostFocus(e);
      }
      
      public void focusGained(FocusEvent e) {
    	  selectAll();
      }
    });
   
  }
  public void focusLost(FocusEvent e) {
    // overridden, to fix the klopgeest-aap bug
  }

  private final void lostFocus(FocusEvent e){
    //System.err.println("---------------->> Vuur!");
    try{
      if (getText() == null) {
        System.err.println("GetText() null");
        return;
      }

      if (initProperty!=null) {
        //System.err.println("initProperty is niet nulll hoor..: " + getText() + " = " + initProperty.getValue());
        if(!getText().equals(initProperty.getValue())){
          initProperty.setValue(getText());
          String s = initProperty.getValue();
          if (!s.equals(getText())) {
            setText(s);
          }
//          System.err.println("Setting changed for textprop");
          setChanged(true);
        }
      }
    }catch(Exception ex){
      ex.printStackTrace();
    }

  }

  public final void update() {
//    System.err.println("Update in property");
    lostFocus(null);
  }

  public void setProperty(Property p) {
    myDocument.setProperty(p);
    if(p != null){
      if (!(p.getType().equals(Property.STRING_PROPERTY) || p.getType().equals(Property.EXPRESSION_PROPERTY) || p.getType().equals(Property.MEMO_PROPERTY)|| p.getType().equals(Property.TIPI_PROPERTY))) {
        //Toolkit.getDefaultToolkit().beep();
        try {
          System.err.println("PROPERTY: " + p.getFullPropertyName() + " is not of string type! Value: "+p.getValue()+"");
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
      }
      else {
        // Toch maar eerst kijken wat nu de waarde is voordat je hem gewoon weer op zn oude (getrimde) waarde zet?
        Object o = p.getTypedValue();
        if (o!=null && !(o instanceof String)) {
          System.err.println("Problems setting property");
          try {
            System.err.println("Name: " + p.getFullPropertyName());
          }
          catch (NavajoException ex1) {
            ex1.printStackTrace();
          }
        }
        if (o!=null) {
          textValue = (String) o.toString();
        } else {
          textValue = "";
        }
        if (p.getLength() >= 1) {
          if (textValue != null) {
            textValue = textValue.trim();
            if (textValue.length() > p.getLength()) {
              textValue = textValue.substring(0, p.getLength());
              p.setValue(textValue);
            }
          } else {
         	        	  
          }
        }

        // Reset selected text, fixing JDK-problem?
        setSelectionStart(0);
        setSelectionEnd(0);
        setCaretPosition(0);

        setText(textValue);

      }
      String caps = p.getSubType("capitalization");
//      System.err.println("TextPropField: " + p.getName() + " capitalization: " + caps) ;
      if(caps != null){
        setCapitalizationMode(caps);
      }

    }
    super.setProperty(p);
//    update();
  }

  public final void updateProperty() {
    if (initProperty==null) {
      return;
    }
    String value = initProperty.getValue();
    try {
      myDocument.remove(0,myDocument.getLength());
      myDocument.insertString(0, value, null);
    }
    catch (BadLocationException ex) {
      ex.printStackTrace();
    }
  }

  public void setText(String s) {
	  String old = getText();
	  try {
      myDocument.remove(0, myDocument.getLength());
      myDocument.insertString(0, s, null);
    }
    catch (BadLocationException ex) {
      System.err.println("Error setting text: "+s+" in propertyfield");
      //Toolkit.getDefaultToolkit().beep();
    }
 //   firePropertyChange("text", old,s);
    super.setText(s);
  }

  public final void setCapitalizationMode(String mode) {
    myDocument.setCapitalizationMode(mode);
  }
  public final String getCapitalizationMode() {
    return myDocument.getCapitalizationMode();
  }

}

