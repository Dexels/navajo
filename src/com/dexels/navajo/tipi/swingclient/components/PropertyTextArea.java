package com.dexels.navajo.tipi.swingclient.components;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

public class PropertyTextArea extends JTextArea implements PropertyControlled   {
  private String textValue;
  private Property initProperty = null;
  private String toolTipText = "";
 
  private BoundedLengthDocument myDocument = new BoundedLengthDocument();


  public void setProperty(Property p){
    myDocument.setProperty(p);
    initProperty = p;
    textValue = (String)p.getTypedValue();
    String currentText = getText();
    updateText(p, currentText);
  }

private void updateText(Property p, String currentText) {
      if((toolTipText = p.getDescription()) != null){
        setToolTipText(toolTipText);
      }else{
        toolTipText = p.getName();
        setToolTipText(toolTipText);
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
          this.addFocusListener(new java.awt.event.FocusAdapter() {
          @Override
    	public void focusGained(FocusEvent e) {
          }
          @Override
    	public void focusLost(FocusEvent e) {
        	    textValue = getText();
        	    System.err.println("MEMO FIELD: "+textValue);
        	    if(textValue != null){
        	      initProperty.setValue(textValue);
        	    }
         }
        });
        
        InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK), "ShiftEnterReleased");  
        am.put("ShiftEnterReleased", new KeyEventHandler(this, "ShiftEnterReleased"));
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK), "ShiftTabReleased");  
        am.put("ShiftTabReleased", new KeyEventHandler(this, "ShiftTabReleased"));
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

  @Override
public Dimension getMinimumSize() {
      return getPreferredSize();
  }


}
