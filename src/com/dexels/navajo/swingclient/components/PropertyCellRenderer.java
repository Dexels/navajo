package com.dexels.navajo.swingclient.components;

import javax.swing.table.*;
import java.awt.*;
import javax.swing.*;
import java.util.EventObject;
import javax.swing.event.CellEditorListener;
import com.dexels.navajo.nanodocument.*;

import javax.swing.tree.TreeCellRenderer;
//import com.dexels.sportlink.client.swing.components.treetable.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class PropertyCellRenderer implements TableCellRenderer {

  private String myPropertyType = null;
  private PropertyBox myPropertyBox = null;
  private PropertyField myPropertyField = null;
  private PropertyCheckBox myPropertyCheckBox = null;
  private DatePropertyField myDatePropertyField = null;
  private Property myProperty = null;


  public PropertyCellRenderer() {

  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (Property.class.isInstance(value)) {
//      System.err.println("Yes, a property");
      myProperty = (Property)value;
      myPropertyType = (String)myProperty.getType();
//      System.err.println("my prop: "+myProperty.getName());
      if (myPropertyType.equals(Property.SELECTION_PROPERTY)) {
        if (myPropertyBox==null) {
          myPropertyBox = new PropertyBox();
        }
        myPropertyBox.setProperty(myProperty);
        setComponentColor(myPropertyBox,isSelected,row,column,false);
        return myPropertyBox;
        /** @todo etc */
      }
      if (myPropertyType.equals(Property.BOOLEAN_PROPERTY)) {
        if (myPropertyCheckBox==null) {
          myPropertyCheckBox = new PropertyCheckBox();
        }
        myPropertyCheckBox.setProperty(myProperty);
        setComponentColor(myPropertyCheckBox,isSelected,row,column,false);
        return myPropertyCheckBox;
      }

      if (myPropertyType.equals(Property.DATE_PROPERTY)) {
        if (myDatePropertyField==null) {
          myDatePropertyField = new DatePropertyField();
        }
        myDatePropertyField.setProperty(myProperty);
        setComponentColor(myDatePropertyField,isSelected,row,column,false);
        return myDatePropertyField;
      }

      if (myPropertyField==null) {
        myPropertyField = new PropertyField();
      }
      myPropertyField.setProperty(myProperty);
      setComponentColor(myPropertyField,isSelected,row,column,false);
      return myPropertyField;

    }
//    System.err.println("Oh dear, strange property type...");
    if (myPropertyField==null) {
      myPropertyField = new PropertyField();
    }
    myPropertyField.setEditable(false);
    Property temp = Navajo.createProperty(new Navajo(),"tmp",Property.UNKNOWN_PROPERTY,"...",1,"",Property.DIR_IN);
    //    myProperty.setType(Property.UNKNOWN_PROPERTY);
    myPropertyField.setProperty(temp);
//    myPropertyField.setName("unloaded_property");
    setComponentColor(myPropertyField,isSelected,row,column,true);
    myPropertyField.setText("..");
    return myPropertyField;
  }


  private void setComponentColor(Component c, boolean isSelected, int row, int column, boolean loading) {
    /** @todo Check this a bit */
    JComponent cc = (JComponent)c;
    cc.setBorder(null);
    if (isSelected) {
      c.setBackground(new Color(220,220,255));
    } else {
      if (loading) {
        if (row%2==0) {
          c.setBackground(new Color(255,230,230));
        } else {
          c.setBackground(new Color(255,200,200));
        }
//        c.setBackground(Color.pink);
      } else {
        if (row%2==0) {
          c.setBackground(Color.white);
        } else {
          c.setBackground(new Color(240,240,240));
        }
      }
    }
  }
//  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
//    JTreeTable.TreeTableCellRenderer tt;
//  }
}