package com.dexels.navajo.tipi.components;

import java.awt.Component;
import javax.swing.*;
import java.util.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.event.ChangeEvent;
import com.dexels.navajo.tipi.TipiValue;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiAttributeTableEditor implements TableCellEditor {
  private ArrayList myListeners = new ArrayList();
  private JTable myTable;
  private Object myValue;
  private JComponent myComponent;

  public TipiAttributeTableEditor() {
  }

  public Component getTableCellEditorComponent(JTable parm1, Object value, boolean isSelected, int row, int column) {
    myTable = parm1;
    if(row == 0 && Boolean.class.isInstance(value)){
      boolean sel = ((Boolean)value).booleanValue();
      JCheckBox c = new JCheckBox();
      c.setSelected(sel);
      myComponent = c;
      return myComponent;
    }
    if(row == 1){
      return new JLabel(value.toString());
    }
    if(row ==2){
      if(column == 0 && Boolean.class.isInstance(value)){
      boolean sel = ((Boolean)value).booleanValue();
      JCheckBox myCheckBox = new JCheckBox();
      myCheckBox.setSelected(sel);
      return myCheckBox;
    }
    if(column == 1 && value != null){
      JLabel myLabel = new JLabel();
      myLabel.setText(value.toString());
      return myLabel;
    }
    if(column == 2 && TipiValue.class.isInstance(value)){
      TipiValue tv = (TipiValue)value;
      String type = tv.getType();
      System.err.println("Jep we have a TipiValue of type: " + type + " at row: " + row + " with value: " + tv.getValue() );
      if(type.equals("selection")){
        JComboBox myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        return myComboBox;
      }
      if(type.equals("string") || type.equals("integer")){
        JLabel myLabel = new JLabel();
        myLabel.setText(tv.getValue());
      }
      if(type.equals("boolean")){
        Vector v = new Vector();
        v.addElement(new Boolean(true));
        v.addElement(new Boolean(false));
        JComboBox myComboBox = new JComboBox(v);
        myComboBox = new JComboBox(v);
        return myComboBox;
      }
      JLabel myLabel = new JLabel();
      myLabel.setText(tv.getValue());
      return myLabel;
    }
    JLabel myLabel = new JLabel();
    myLabel.setText("?");
    return myLabel;

    }
    return new JTextField(value.toString());
  }

  public Object getCellEditorValue() {
    return myValue;
  }

  public boolean isCellEditable(EventObject parm1) {
    return true;
  }

  public boolean shouldSelectCell(EventObject parm1) {
    return true;
  }

  public boolean stopCellEditing() {
    for (int i = 0;i < myListeners.size();i++) {
      CellEditorListener ce = (CellEditorListener)myListeners.get(i);
      ce.editingStopped(new ChangeEvent(myTable));
    }
    return true;
  }

  public void cancelCellEditing() {
    for (int i = 0; i < myListeners.size(); i++) {
      CellEditorListener ce = (CellEditorListener)myListeners.get(i);
      ce.editingCanceled(new ChangeEvent(myTable));
    }
  }

  public void addCellEditorListener(CellEditorListener parm1) {
    myListeners.add(parm1);
  }

  public void removeCellEditorListener(CellEditorListener parm1) {
    myListeners.remove(parm1);
  }

}