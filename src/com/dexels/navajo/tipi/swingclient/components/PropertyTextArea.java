package com.dexels.navajo.tipi.swingclient.components;


import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.tipi.swingclient.*;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.border.*;
import javax.swing.event.*;

import java.awt.*;
//import com.dexels.sportlink.client.swing.components.*;

public class PropertyTextArea extends BaseTextArea implements PropertyControlled, Ghostable  {
  private String textValue;
  private Property initProperty = null;
  ResourceBundle res;
  private String toolTipText = "";
  private boolean ghosted = false;
  private boolean enabled = true;
  private BoundedLengthDocument myDocument = new BoundedLengthDocument();


  public void setProperty(Property p){
    myDocument.setProperty(p);
    initProperty = p;
    textValue = (String)p.getTypedValue();
    String currentText = getText();
    updateText(p, currentText);
  }

private void updateText(Property p, String currentText) {
   try{
      if(res != null){
        toolTipText = res.getString(p.getName());
        setToolTipText(toolTipText);
      }
    }catch(MissingResourceException e){
      if((toolTipText = p.getDescription()) != null){
        setToolTipText(toolTipText);
      }else{
        toolTipText = p.getName();
        setToolTipText(toolTipText);
      }

    }
    setEditable(p.isDirIn());
    if(!currentText.equals(textValue)) {
    	setText(textValue);
    }
}

  public Property getProperty() {
    return initProperty;
  }

  public PropertyTextArea() {
    setDocument(myDocument);
    try {
      if(System.getProperty("com.dexels.navajo.propertyMap") != null){
        res = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
      }
      jbInit();
    }
    catch(Exception e) {
 //     e.printStackTrace();
    }
  }

  public void gainFocus(){
    // affe
  }

  public void update(){
    if (initProperty==null) {
      return;
    }
    textValue = getText();
    if(textValue != null){
      initProperty.setValue(textValue);
    }
  }

  public Dimension getMinimumSize() {
      return getPreferredSize();
  }
  
  private final void jbInit() throws Exception {
//    setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        this_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        this_focusLost(e);
      }
    });
    
    InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
    ActionMap am = getActionMap();

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK), "ShiftEnterReleased");  
    am.put("ShiftEnterReleased", new KeyEventHandler(this, "ShiftEnterReleased"));
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK), "ShiftTabReleased");  
    am.put("ShiftTabReleased", new KeyEventHandler(this, "ShiftTabReleased"));

//    myDocument.addDocumentListener(new DocumentListener(){
//
//		public void changedUpdate(DocumentEvent arg0) {
//			this_focusLost(null);
//		}
//
//		public void insertUpdate(DocumentEvent arg0) {
//			this_focusLost(null);
//		}
//
//		public void removeUpdate(DocumentEvent arg0) {
//			myDocument.
//			this_focusLost(null);
//		}});
  }
  

  void this_focusGained(FocusEvent e) {
//    SwingClient.getUserInterface().setStatusText(toolTipText);
  }

  void this_focusLost(FocusEvent e) {
    textValue = getText();
    System.err.println("MEMO FIELD: "+textValue);
    if(textValue != null){
      initProperty.setValue(textValue);
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

}
