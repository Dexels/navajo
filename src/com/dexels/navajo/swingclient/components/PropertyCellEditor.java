package com.dexels.navajo.swingclient.components;

import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.dexels.navajo.nanodocument.*;

import java.util.*;

public class PropertyCellEditor  implements TableCellEditor, ListSelectionListener {

  private Property myProperty = null;
//  private JTable myTable = null;
  private int x = -1;
  private int y = -1;
  private boolean selected = false;

  private ArrayList myListeners = new ArrayList();

  private String myPropertyType = null;
  private PropertyBox myPropertyBox = null;
  private PropertyField myPropertyField = null;
  private PropertyCheckBox myPropertyCheckBox = null;
  private DatePropertyField myDatePropertyField = null;

  private JComponent lastComponent = null;
  private MessageTable myTable = null;

  private int lastRow = -1;
  private int lastColumn = -1;
  private boolean wasSelected = false;

  public PropertyCellEditor() {
  }
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    System.err.println("DOES SOMEONE NEED AN EDITOR?");
    myTable = (MessageTable)table;
    lastRow = row;
    lastColumn = column;
    wasSelected = isSelected;

    if (Property.class.isInstance(value)) {
      myProperty = (Property)value;
      myPropertyType = (String)myProperty.getType();
      if (myPropertyType.equals(Property.SELECTION_PROPERTY)) {
        if (myPropertyBox==null) {
          myPropertyBox = new PropertyBox();
          myPropertyBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
              System.err.println("COMBOBOX FIRED TOWARDS EDITOR");
            }
          }
          );
        }
        myPropertyBox.setProperty(myProperty);
        lastComponent = myPropertyBox;
        setComponentColor(myPropertyBox,isSelected,row,column);
        return myPropertyBox;
        /** @todo etc */
      }
      if (myPropertyType.equals(Property.BOOLEAN_PROPERTY)) {
        if (myPropertyCheckBox==null) {
          myPropertyCheckBox = new PropertyCheckBox();
          myPropertyCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
              System.err.println("CHECKBOX FIRED TOWARDS EDITOR");
            }
          }
          );
        }
        myPropertyCheckBox.setProperty(myProperty);
        lastComponent = myPropertyCheckBox;
        setComponentColor(myPropertyCheckBox,isSelected,row,column);
        System.err.println("RETURNING PROPERTY EDITOR");
        return myPropertyCheckBox;
      }

      if (myPropertyType.equals(Property.DATE_PROPERTY)) {
        if (myDatePropertyField==null) {
          myDatePropertyField = new DatePropertyField();
        }
        myDatePropertyField.setProperty(myProperty);
        lastComponent = myDatePropertyField;
        setComponentColor(myDatePropertyField,isSelected,row,column);
        return myDatePropertyField;
      }

      if (myPropertyField==null) {
        myPropertyField = new PropertyField();
      }
      myPropertyField.setProperty(myProperty);
      lastComponent = myPropertyField;
      setComponentColor(myPropertyField,isSelected,row,column);
      return myPropertyField;

    }
      if (myPropertyField==null) {
        myPropertyField = new PropertyField();
      }
    System.err.println("Oh dear, strange property type...");
    myPropertyField.setEditable(false);
//    myPropertyField.setName("unloaded_property");
    setComponentColor(myPropertyField,isSelected,row,column);
    myPropertyField.setText("..");
    return myPropertyField;
  }

  private void setComponentColor(Component c, boolean isSelected, int row, int column) {
    if (c==null) {
      return;
    }
    JComponent cc = (JComponent)c;
    cc.setBorder(null);
//    if (isSelected) {
      c.setBackground(new Color(220,220,255));
//    } else {
//      if (row%2==0) {
//        c.setBackground(Color.white);
//      } else {
//        c.setBackground(new Color(240,240,240));
//      }
//    }
  }

//  private fireUpdate(

  public void cancelCellEditing() {
    for (int i = 0; i < myListeners.size(); i++) {
      CellEditorListener ce = (CellEditorListener)myListeners.get(i);
      ce.editingCanceled(new ChangeEvent(myTable));
    }
  }

  public boolean stopCellEditing() {
    ((PropertyControlled)lastComponent).update();
    if (!wasSelected) {
      myTable.setRowSelectionInterval(lastRow,lastRow);
    }
    for (int i = 0;i < myListeners.size();i++) {
      CellEditorListener ce = (CellEditorListener)myListeners.get(i);
      ce.editingStopped(new MessageTableChangeEvent(myTable, lastRow, lastColumn));
    }

    return true;
  }
  public Object getCellEditorValue() {
    return myProperty;
  }
  public boolean isCellEditable(EventObject parm1) {
    return true;
  }
  public void valueChanged(ListSelectionEvent e) {
    /**@todo: Implement this javax.swing.event.ListSelectionListener method*/
    throw new java.lang.UnsupportedOperationException("Method valueChanged() not yet implemented.");
  }
  public boolean shouldSelectCell(EventObject parm1) {
    return true;
  }
  public void addCellEditorListener(CellEditorListener ce) {
    myListeners.add(ce);
  }
  public void removeCellEditorListener(CellEditorListener ce) {
    myListeners.remove(ce);
  }
}