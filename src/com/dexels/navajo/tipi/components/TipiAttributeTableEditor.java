package com.dexels.navajo.tipi.components;

import java.awt.Component;
import javax.swing.*;
import java.util.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.event.ChangeEvent;
import com.dexels.navajo.tipi.TipiValue;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.TreePath;
import com.dexels.navajo.tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiAttributeTableEditor implements TableCellEditor, ListSelectionListener, ActionListener {
  private ArrayList myListeners = new ArrayList();
  private JTable myTable;
  private Object myValue;
  private JComponent myLastComponent;
  private TipiValue myLastValue;
  private JComboBox myComboBox;
  private JTextField myTextField;
  private TipiAttributeTableExternalSelectionCell myExternalSelectionCell;
  private TipiComponent myComponent = null;


  public TipiAttributeTableEditor() {
  }

  public Component getTableCellEditorComponent(JTable parm1, Object value, boolean isSelected, int row, int column) {
    myTable = parm1;
    Object liveValue = null;
    if(TipiValue.class.isInstance(value)){
      TipiValue tv = (TipiValue)value;

      if (!tv.getDirection().equals("in")) {
        System.err.println("Getting live value for name: "+tv.getName());
        liveValue = myComponent.getValue(tv.getName());
      }
     myLastValue = tv;
      String type = tv.getType();
      System.err.println("Jep we have a TipiValue of type: " + type + " at row: " + row + " with value: " + tv.getValue() );
      System.err.println("Live value: "+liveValue);
      if(type.equals("selection")){
        myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        myLastComponent = myComboBox;
        if (liveValue!=null) {
          myComboBox.setSelectedItem(liveValue);
        } else {
          myComboBox.setSelectedItem(tv.getValue());
        }

        return myComboBox;
      }
      if(type.equals("string") || type.equals("integer") || type.equals("resource")){
        myTextField = new JTextField();
        if (liveValue!=null) {
          myTextField.setText(liveValue.toString());
        } else {
          myTextField.setText(tv.getValue());
        }
        myLastComponent = myTextField;
        return myTextField;
      }
      if(type.equals("boolean")){
        Vector v = new Vector();
        v.addElement(new Boolean(true));
        v.addElement(new Boolean(false));
        myComboBox = new JComboBox(v);
        myLastComponent = myComboBox;
        if (liveValue!=null) {
           myComboBox.setSelectedItem(liveValue);
         } else {
           myComboBox.setSelectedItem(tv.getValue());
         }
        return myComboBox;
      }
      if(type.equals("border") || type.equals("font") || type.equals("path") || type.equals("messagepath") || type.equals("tipipath") || type.equals("componentpath") || type.equals("propertypath") || type.equals("attriutepath") || type.equals("color")){
        myExternalSelectionCell = new TipiAttributeTableExternalSelectionCell();
        myExternalSelectionCell.addActionListener(this);
        myExternalSelectionCell.setType(type);
        myLastComponent = myExternalSelectionCell;
        if (liveValue!=null) {
          myExternalSelectionCell.setText(liveValue.toString());
        } else {
          myExternalSelectionCell.setText(tv.getValue());
        }
        return myExternalSelectionCell;
      }
      myTextField = new JTextField();
      myTextField.setText(tv.getValue());
      myLastComponent = myTextField;
      myLastComponent.setEnabled(!"out".equals(tv.getDirection()));
      if (liveValue!=null) {
        myTextField.setText(liveValue.toString());
      } else {
        myTextField.setText(tv.getValue());
      }
      return myTextField;
    }else{
      if (value!=null) {
        JLabel l = new JLabel();
        l.setText(value.toString());
        return l;
      } else {
        JLabel l = new JLabel();
        l.setText("No value");
        return l;
      }

    }
  }

  public void setTipiComponent(TipiComponent tc) {
    myComponent = tc;
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
    if(myLastComponent != null && myLastValue != null){
      if(JTextField.class.isInstance(myLastComponent)){
        JTextField tf = (JTextField)myLastComponent;
//        myLastValue.setValue(tf.getText());
        myComponent.setValue(myLastValue.getName(),tf.getText());
      }
      if(JComboBox.class.isInstance(myLastComponent)){
        if(!myLastValue.getType().equals("selection") && !myLastValue.getType().equals("boolean")){
          System.err.println("WTF: We have a combobox, but the type is not correct!");
          myLastComponent = null;
          myLastValue = null;
          return false;
        }
        JComboBox cb = (JComboBox)myLastComponent;
        Object item = cb.getSelectedItem();
        if(item != null){
//          myLastValue.setValue(item.toString());
          myComponent.setValue(myLastValue.getName(),item.toString());
        }
      }
      if(JLabel.class.isInstance(myLastComponent)){
        JLabel l = (JLabel)myLastComponent;
//        myLastValue.setValue(l.getText());
        myComponent.setValue(myLastValue.getName(),l.getText());
      }
      if(TipiAttributeTableExternalSelectionCell.class.isInstance(myLastComponent)){
        TipiAttributeTableExternalSelectionCell l = (TipiAttributeTableExternalSelectionCell)myLastComponent;
//        myLastValue.setValue(l.toString());
        myComponent.setValue(myLastValue.getName(),l.toString());

      }
    }
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
  public void valueChanged(ListSelectionEvent parm1) {
    // NIMP
  }
  public void actionPerformed(ActionEvent e) {
    System.err.println("You clicked on a TipiAttributeTableExternalSelectionCell");
    TipiAttributeTableExternalSelectionCell l = (TipiAttributeTableExternalSelectionCell)myLastComponent;
    String lt = l.toString();

    if(l.getType().equals("color")){
      Color c;
      try{
        c = Color.decode(lt);
      }catch(Exception ex){
        System.err.println("Whoops... using white: [" + lt + "]");
        c = Color.white;
      }
      JColorChooser chooser = new JColorChooser();
      Color col = chooser.showDialog(myTable, "Select color", c);
      if(col == null){
        col = SystemColor.control;
      }
      l.setText("#" + Integer.toHexString(col.getRGB()).substring(2));
    }
    if(l.getType().equals("path") || l.getType().equals("tipipath")){
      l.setText(getPath());
    }
  }

  private String getPath(){
    TipiComponentInstanceTreeDialog tid = new TipiComponentInstanceTreeDialog();
    tid.setLocationRelativeTo(myTable);
    tid.setSize(new Dimension(300, 400));
    tid.setModal(true);
    tid.show();
    return tid.getPath();
  }
  public void setSelectedComponent(TipiComponent tc) {
    myComponent = tc;
  }


}