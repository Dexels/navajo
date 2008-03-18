package com.dexels.navajo.tipi.swingclient.components;
import com.dexels.navajo.document.*;
import java.awt.event.*;
import java.util.ResourceBundle;

import com.dexels.navajo.tipi.swingclient.*;

import java.util.MissingResourceException;
import java.awt.*;

//import com.dexels.sportlink.client.swing.components.*;

public final class PropertyCheckBox extends BaseCheckBox implements ChangeMonitoring, PropertyControlled, Ghostable {
  private Property myProperty = null;
  private boolean ghosted = false;
  private boolean enabled = true;
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
    }
    catch(Exception e) {
//      e.printStackTrace();
    }
  }

  public void gainFocus(){
    // gar nichts
  }

//  protected void printComponent(Graphics g) {
//    Color cc = g.getColor();
//    g.setColor(Color.white);
//    g.fillRect(0,0,getWidth(),getHeight());
//    g.setColor(cc);
//    Color c = getBackground();
//    setBackground(Color.white);
//    super.printComponent(g);
//    setBackground(c);
//  }

  public final Property getProperty() {
    return myProperty;
  }

  public final void update(){
    if (myProperty!=null) {
      myProperty.setValue(isSelected()?"true":"false");
    }
    setChanged(true);
  }

  public boolean isOpaque() {
	  return false;
  }
  public final void setProperty(Property p) {
    myProperty = p;
//    this_actionPerformed(null);
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
//       setSelected(((String)myProperty.getValue()).equals("true"));
    }
    setChanged(true);
//    fireStateChanged();
  }

  public final boolean isGhosted() {
    return ghosted;
  }

  public final void setGhosted(boolean g) {
//    System.err.println("--> Setting cb to ghosted!!");
    ghosted = g;
    super.setEnabled(enabled && (!ghosted));
  }

  public final void setEnabled(boolean e) {
    enabled = e;
//    System.err.println("--> Setting enabled: " + e);
    super.setEnabled(enabled && (!ghosted));
  }
}
