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

  public TipiAttributeTableEditor() {
  }

  public Component getTableCellEditorComponent(JTable parm1, Object value, boolean isSelected, int row, int column) {
    myTable = parm1;
    if(TipiValue.class.isInstance(value)){
      TipiValue tv = (TipiValue)value;
      myLastValue = tv;
      String type = tv.getType();
      System.err.println("Jep we have a TipiValue of type: " + type + " at row: " + row + " with value: " + tv.getValue() );
      if(type.equals("selection")){
        myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        myLastComponent = myComboBox;
        return myComboBox;
      }
      if(type.equals("string") || type.equals("integer") || type.equals("resource")){
        myTextField = new JTextField();
        myTextField.setText(tv.getValue());
        myLastComponent = myTextField;
        return myTextField;
      }
      if(type.equals("boolean")){
        Vector v = new Vector();
        v.addElement(new Boolean(true));
        v.addElement(new Boolean(false));
        myComboBox = new JComboBox(v);
        myLastComponent = myComboBox;
        return myComboBox;
      }
      if(type.equals("border") || type.equals("font") || type.equals("path") || type.equals("messagepath") || type.equals("tipipath") || type.equals("componentpath") || type.equals("propertypath") || type.equals("attriutepath") || type.equals("color")){
        myExternalSelectionCell = new TipiAttributeTableExternalSelectionCell();
        myExternalSelectionCell.addActionListener(this);
        myExternalSelectionCell.setType(type);
        myLastComponent = myExternalSelectionCell;
        myExternalSelectionCell.setText(tv.getValue());
        return myExternalSelectionCell;
      }
      myTextField = new JTextField();
      myTextField.setText(tv.getValue());
      myLastComponent = myTextField;
      myLastComponent.setEnabled(!"out".equals(tv.getDirection()));
      return myTextField;
    }else{
      JLabel l = new JLabel();
      l.setText(value.toString());
      return l;
    }
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
        myLastValue.setValue(tf.getText());
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
          myLastValue.setValue(item.toString());
        }
      }
      if(JLabel.class.isInstance(myLastComponent)){
        JLabel l = (JLabel)myLastComponent;
        myLastValue.setValue(l.getText());
      }
      if(TipiAttributeTableExternalSelectionCell.class.isInstance(myLastComponent)){
        TipiAttributeTableExternalSelectionCell l = (TipiAttributeTableExternalSelectionCell)myLastComponent;
        myLastValue.setValue(l.toString());
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
    TreePath treePath = tid.getPath();

    //Temporarily implementation Als implemented in InstantiationPanel
    String sp = treePath.toString();
    sp = sp.substring(6, sp.length()-1);
    System.err.println("sp_cut: " + sp);

    StringTokenizer tok = new StringTokenizer(sp, ",");
    String path = "tipi:/";
    while(tok.hasMoreTokens()){
      path = path +"/" + tok.nextToken().trim();
    }
    return path;
  }


}