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

public class StopwatchTimeField extends PropertyField {

  private Document myDocument = null;
  private String format = null;

  public StopwatchTimeField() {
    super();
    myDocument = new StopwatchTimeDocument();
    setDocument(myDocument);
  }
  public final void focusLost(FocusEvent e) {

    try{
      if (getText() == null || "".equals(getText().trim())) {
        setChanged(true);
        getProperty().setValue((StopwatchTime) null);
        return;
      }
      StopwatchTime ct = new StopwatchTime(getText(), getProperty().getSubType());
      StopwatchTime oldTime = (StopwatchTime)getProperty().getTypedValue();
      if (!ct.equals(oldTime)) {
        setChanged(true);
        getProperty().setValue(ct);
      }
      setText(ct.toString());
    }catch(Exception ex){
      setText("");
      if (getProperty()!=null) {
        getProperty().setValue((StopwatchTime)null);
      }
    }
  }

  public final void focusGained(FocusEvent e){
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
    setText(getProperty().getTypedValue().toString());
  }

  public final void setProperty(Property p) {
    super.setProperty(p);
    setFormatText();
  }

  public final void update() {
    focusLost(null);
  }
}

final class StopwatchTimeDocument extends PlainDocument {

  public final void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    char[] source = str.toCharArray();
     char[] result = new char[source.length];
     int j = 0;
     for (int i = 0; i < result.length; i++) {
       if ((Character.isDigit(source[i]) || source[i]==':') || source[i]==',' && (getLength()+j)< 12) {
         result[j++] = source[i];
       }
       else {
       }
     }
     super.insertString(offs, new String(result, 0, j), a);
  }

}
