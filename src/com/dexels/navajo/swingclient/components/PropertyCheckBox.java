package com.dexels.navajo.swingclient.components;
import com.dexels.navajo.document.*;
import java.awt.event.*;

//import com.dexels.sportlink.client.swing.components.*;

public class PropertyCheckBox extends BaseCheckBox implements ChangeMonitoring, PropertyControlled, Ghostable {
  private Property myProperty = null;
  private boolean ghosted = false;
  private boolean enabled = true;

  public PropertyCheckBox() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Property getProperty() {
    return myProperty;
  }

  public void update(){
    if (myProperty!=null) {
      myProperty.setValue(isSelected()?"true":"false");
    }
    setChanged(true);
  }

  public void setProperty(Property p) {
    myProperty = p;
//    this_actionPerformed(null);
    if (p==null) {
      return;
    }

    setSelected(myProperty.getValue().equals("true"));
//    ((Boolean)myProperty.getTypedValue()).booleanValue());
    setEnabled(p.isDirIn());
//    setChanged(false);
  }

  private void jbInit() throws Exception {
    this.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  void this_actionPerformed(ActionEvent e) {
    System.err.println("Checkbox event fired!");
    if (myProperty!=null) {
      myProperty.setValue(isSelected()?"true":"false");
//       setSelected(((String)myProperty.getValue()).equals("true"));
    }
    setChanged(true);
//    fireStateChanged();
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