package com.dexels.navajo.tipi.components;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiExportSortingPanel extends JPanel {
  JLabel availableColumnsLabel = new JLabel();
  JLabel exportedColumnsLabel = new JLabel();
  JButton addButton = new JButton();
  JButton removeButton = new JButton();
  JButton sortUpButton = new JButton();
  JButton sortDownButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JScrollPane jScrollPane2 = new JScrollPane();
  JList availableColumnsList = new JList();
  JList exportedColumnsList = new JList();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  private HashMap descIdMap = new HashMap();
  private HashMap descPropMap = new HashMap();

  public TipiExportSortingPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setMessage(Message msg){
    if (msg==null) {
      return;
    }
    if(msg.getType() == Message.MSG_TYPE_ARRAY && msg.getAllMessages().size() > 0){
      Message m = msg.getMessage(0);
      fillAvailableList(m);
    }else{
      fillAvailableList(msg);
    }
  }

  private void fillAvailableList(Message m){
    ArrayList props = m.getAllProperties();
    for(int i=0;i<props.size();i++){
      Property p = (Property)props.get(i);
      String name = p.getName();
      String description = p.getDescription();
      descIdMap.put(description, name);
      descPropMap.put(description, p);
      DefaultListModel am = (DefaultListModel)availableColumnsList.getModel();
      am.addElement(description);
    }
  }


  private void jbInit() throws Exception {
    availableColumnsList.setModel(new DefaultListModel());
    exportedColumnsList.setModel(new DefaultListModel());
    availableColumnsLabel.setText("Beschikbare kolommen");
    this.setLayout(gridBagLayout1);
    exportedColumnsLabel.setText("Te exporteren kolommen");
    addButton.setText(">>>");
    addButton.addActionListener(new TipiExportSortingPanel_addButton_actionAdapter(this));
    removeButton.setText("<<<");
    removeButton.addActionListener(new TipiExportSortingPanel_removeButton_actionAdapter(this));
    sortUpButton.setText("omhoog");
    sortUpButton.addActionListener(new TipiExportSortingPanel_sortUpButton_actionAdapter(this));
    sortDownButton.setText("omlaag");
    sortDownButton.addActionListener(new TipiExportSortingPanel_sortDownButton_actionAdapter(this));
    this.add(availableColumnsLabel,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 0, 0), 128, 0));
    this.add(exportedColumnsLabel,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 0, 0), 114, 0));
    this.add(addButton,        new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(removeButton,       new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(sortUpButton,    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(sortDownButton,    new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(jScrollPane1,    new GridBagConstraints(0, 1, 1, 2, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 0, 0), 0, 0));
    jScrollPane1.getViewport().add(availableColumnsList, null);
    this.add(jScrollPane2,   new GridBagConstraints(2, 1, 1, 2, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 0, 2), 0, 0));
    jScrollPane2.getViewport().add(exportedColumnsList, null);
  }

  void addButton_actionPerformed(ActionEvent e) {
    Object[] items = availableColumnsList.getSelectedValues();
    DefaultListModel em = (DefaultListModel)exportedColumnsList.getModel();
    DefaultListModel am = (DefaultListModel)availableColumnsList.getModel();
    for(int i=0;i<items.length;i++){
      am.removeElement(items[i]);
      em.addElement(items[i]);
    }
  }

  void removeButton_actionPerformed(ActionEvent e) {
    Object[] items = exportedColumnsList.getSelectedValues();
    DefaultListModel em = (DefaultListModel)exportedColumnsList.getModel();
    DefaultListModel am = (DefaultListModel)availableColumnsList.getModel();
    for(int i=0;i<items.length;i++){
      em.removeElement(items[i]);
      am.addElement(items[i]);
    }
  }

  void sortUpButton_actionPerformed(ActionEvent e) {
    int index = exportedColumnsList.getSelectedIndex();
    DefaultListModel vm = (DefaultListModel)exportedColumnsList.getModel();
    if(index > 0){
      String value = (String)exportedColumnsList.getSelectedValue();
      vm.removeElement(value);
      vm.insertElementAt(value, index -1);
      exportedColumnsList.setSelectedIndex(index-1);
    }
  }

  void sortDownButton_actionPerformed(ActionEvent e) {
      int index = exportedColumnsList.getSelectedIndex();
      DefaultListModel vm = (DefaultListModel)exportedColumnsList.getModel();
      if(index < vm.indexOf(vm.lastElement())){
        String value = (String)exportedColumnsList.getSelectedValue();
        vm.removeElement(value);
        vm.insertElementAt(value, index  +1);
        exportedColumnsList.setSelectedIndex(index+1);
      }
  }


  void cancelButton_actionPerformed(ActionEvent e) {
    this.hide();
  }

  public HashMap getDescriptionIdMap(){
    return descIdMap;
  }

  public HashMap getDescriptionPropertyMap(){
    return descPropMap;
  }

  public Vector getExportedPropertyDescriptions(){
    Vector v = new Vector();
    for(int i=0;i<exportedColumnsList.getModel().getSize();i++){
      String current = (String)exportedColumnsList.getModel().getElementAt(i);
      v.addElement(current);
    }
    return v;
  }

  public Vector getExportedPropertyNames(){
    Vector v = new Vector();
    for(int i=0;i<exportedColumnsList.getModel().getSize();i++){
      String current = (String)exportedColumnsList.getModel().getElementAt(i);
      v.addElement(descIdMap.get(current));
    }
    return v;
  }

}

class TipiExportSortingPanel_addButton_actionAdapter implements java.awt.event.ActionListener {
  TipiExportSortingPanel adaptee;

  TipiExportSortingPanel_addButton_actionAdapter(TipiExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.addButton_actionPerformed(e);
  }
}

class TipiExportSortingPanel_removeButton_actionAdapter implements java.awt.event.ActionListener {
  TipiExportSortingPanel adaptee;

  TipiExportSortingPanel_removeButton_actionAdapter(TipiExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.removeButton_actionPerformed(e);
  }
}

class TipiExportSortingPanel_sortUpButton_actionAdapter implements java.awt.event.ActionListener {
  TipiExportSortingPanel adaptee;

  TipiExportSortingPanel_sortUpButton_actionAdapter(TipiExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.sortUpButton_actionPerformed(e);
  }
}

class TipiExportSortingPanel_sortDownButton_actionAdapter implements java.awt.event.ActionListener {
  TipiExportSortingPanel adaptee;

  TipiExportSortingPanel_sortDownButton_actionAdapter(TipiExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.sortDownButton_actionPerformed(e);
  }
}