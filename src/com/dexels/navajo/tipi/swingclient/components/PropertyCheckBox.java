package com.dexels.navajo.tipi.swingclient.components;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

//import com.dexels.sportlink.client.swing.components.*;

public final class PropertyCheckBox extends JCheckBox implements  PropertyControlled {
  private Property myProperty = null;
 

  public PropertyCheckBox() {
	    this.setOpaque(false);
	    this.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	      	if (myProperty!=null) {
	            myProperty.setValue(isSelected());
	          }
	      }
	    });
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
  }



}
