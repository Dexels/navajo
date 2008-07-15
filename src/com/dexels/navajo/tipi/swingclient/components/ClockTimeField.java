package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.text.*;
import com.dexels.navajo.document.types.*;
import java.util.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class ClockTimeField extends PropertyField {

  private Document myDocument = null;
//  private Property myProperty = null;
  private boolean showSeconds = true;
  public ClockTimeField() {
    super();
    myDocument = new ClockTimeDocument();
    setDocument(myDocument);
//    myDocument.addDocumentListener(new DocumentListener() {
//      public void changedUpdate(DocumentEvent e) {
//        setChanged(true);
//      }
//      public void insertUpdate(DocumentEvent e) {
//        setChanged(true);
//      }
//      public void removeUpdate(DocumentEvent e) {
//        setChanged(true);
//      }
//    });
  }
  public final void focusLost(FocusEvent e) {

    try{
      if (getText() == null || "".equals(getText().trim())) {
        setChanged(true);
        getProperty().setValue((ClockTime) null);
        return;
      }
      ClockTime ct = new ClockTime(getText());
      ClockTime oldTime = (ClockTime)getProperty().getTypedValue();
      if (!ct.equals(oldTime)) {
        setChanged(true);
        getProperty().setValue(ct);
      }
      setText(ct.toString());
    }catch(Exception ex){
      setText("");
      if (getProperty()!=null) {
        getProperty().setValue((ClockTime)null);
      }
    }
  }

  public final void focusGained(FocusEvent e){

  }

//  public Property getProperty() {
//    return myProperty;
//  }

  public final void showSeconds(boolean b) {

    showSeconds = b;
    ((ClockTimeDocument)getDocument()).showSeconds(b);
    if (getProperty()==null) {
      return;
    }
    if (getProperty().getTypedValue()!=null) {
      setFormatText();
    }
  }


  private final void setFormatText() {
    if (getProperty()==null) {
      setText("");
      return;
    }
    if (getProperty().getTypedValue()==null) {
      setText("");
      return;
    }
    if (getProperty().getTypedValue().toString() == null) {
      setText("");
      return;
    }
    if (showSeconds) {
      setText(getProperty().getTypedValue().toString());
    } else {
      setText(getProperty().getTypedValue().toString().substring(0,5));
    }

  }

  public final void setProperty(Property p) {
	  if(p!=null) {
		    if ("true".equals(p.getSubType("showseconds"))) {
				showSeconds(true);
			} else {
				showSeconds(false);
			}
	  }
    super.setProperty(p);
	
//    System.err.println("Setting text = "+p.getTypedValue().toString());
    
    setFormatText();
  }

  public final void update() {
    focusLost(null);
  }



}

final class ClockTimeDocument extends PlainDocument {

  private boolean showSeconds = true;

  public final void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    char[] source = str.toCharArray();
     char[] result = new char[source.length];
     int j = 0;
     for (int i = 0; i < result.length; i++) {
       if ((Character.isDigit(source[i]) || source[i]==':') && (getLength()+j)<(showSeconds?8:5)) {
         result[j++] = source[i];
       }
       else {
       }
     }
     super.insertString(offs, new String(result, 0, j), a);
  }

  public final void showSeconds(boolean b) {
    showSeconds = b;
  }
}
