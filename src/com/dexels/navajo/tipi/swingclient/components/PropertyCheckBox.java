package com.dexels.navajo.tipi.swingclient.components;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

//import com.dexels.sportlink.client.swing.components.*;

public final class PropertyCheckBox extends JCheckBox implements  PropertyControlled {
  private Property myProperty = null;
 
  private ResourceBundle res;

  public PropertyCheckBox() {
    try {
      if(System.getProperty("com.dexels.navajo.propertyMap") != null){
        try {
          res = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
        }
        catch (Exception ex) {
          System.err.println("Whoops.. com.dexels.navajo.propertyMap resource not found in PropertyCheckbox");
        }
      }
      jbInit();
      this.setHorizontalTextPosition(SwingConstants.LEADING);
      
    }
    catch(Exception e) {
//      e.printStackTrace();
    }
  }



  public final Property getProperty() {
    return myProperty;
  }

  public final void update(){
    if (myProperty!=null) {
      myProperty.setAnyValue(isSelected());
    }
  }


  public final void setProperty(Property p) {
    myProperty = p;
    if (p==null) {
      return;
    }
    if (myProperty.getValue()!=null) {
      setEnabled(p.isDirIn());
      setSelected(myProperty.getValue().equals("true"));
    } else {
      setSelected(false);
      //setEnabled(false);
    }

    setSelected(myProperty.getValue()!=null && myProperty.getValue().equals("true"));
    String toolTipText = "";
    try{
      if(res != null){
        toolTipText = res.getString(p.getName());
      }
      this.setToolTipText(toolTipText);
    }catch(MissingResourceException e){
      toolTipText = p.getDescription();
      if(toolTipText != null && !toolTipText.equals("")){
        this.setToolTipText(toolTipText);
      }else{
        this.setToolTipText(p.getName());
      }
    }

//    ((Boolean)myProperty.getTypedValue()).booleanValue());

//    setChanged(false);

  }

  private final void jbInit() throws Exception {
    this.setOpaque(false);
    this.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  final void this_actionPerformed(ActionEvent e) {
    if (myProperty!=null) {
      myProperty.setValue(isSelected());
    }
  }


}
