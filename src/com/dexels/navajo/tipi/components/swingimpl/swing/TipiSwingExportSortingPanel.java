package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingExportSortingPanel
    extends JPanel {
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
  private Map<String,String> descIdMap = new HashMap<String,String>();
  private Map<String,Property> descPropMap = new HashMap<String,Property>();
  public TipiSwingExportSortingPanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setMessage(Message msg) {
    if (msg == null) {
      return;
    }
    if (Message.MSG_TYPE_ARRAY.equals(msg.getType()) && msg.getArraySize() > 0) {
      Message m = msg.getMessage(0);
      fillAvailableList(m);
    }
    else {
      System.err.println("Filling with message itself");
      fillAvailableList(msg);
    }
  }

  private final void fillAvailableList(Message m) {
    List<Property> props = m.getAllProperties();
    for (int i = 0; i < props.size(); i++) {
      Property p = props.get(i);
      String name = p.getName();
      String description = p.getDescription();
      descIdMap.put(description, name);
      descPropMap.put(description, p);
      DefaultListModel am = (DefaultListModel) availableColumnsList.getModel();
      am.addElement(description);
    }
  }

  private ImageIcon getIcon(String name) {
    System.err.println("Getting icon: " + name);
    return new ImageIcon(getClass().getClassLoader().getResource(name));
  }

  private final void jbInit() throws Exception {
    availableColumnsList.setModel(new DefaultListModel());
    exportedColumnsList.setModel(new DefaultListModel());
    availableColumnsLabel.setText("Beschikbare kolommen");
    this.setLayout(gridBagLayout1);
    exportedColumnsLabel.setText("Te exporteren kolommen");
    addButton.addActionListener(new TipiExportSortingPanel_addButton_actionAdapter(this));
    removeButton.setText("");
    removeButton.addActionListener(new TipiExportSortingPanel_removeButton_actionAdapter(this));
    sortUpButton.setText("");
    sortUpButton.setIcon(getIcon("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_up.gif"));
    sortUpButton.setToolTipText("Omhoog");
    sortUpButton.addActionListener(new TipiExportSortingPanel_sortUpButton_actionAdapter(this));
    sortDownButton.setToolTipText("omlaag");
    sortDownButton.setText("");
    sortDownButton.setIcon(getIcon("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_down.gif"));
    sortDownButton.addActionListener(new TipiExportSortingPanel_sortDownButton_actionAdapter(this));
    addButton.setToolTipText("Toevoegen");
    removeButton.setToolTipText("Verwijderen");
    addButton.setText("");
    addButton.setIcon(getIcon("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_right.gif"));
    removeButton.setIcon(getIcon("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_left.gif"));
    this.add(availableColumnsLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 0, 0), 128, 0));
    this.add(exportedColumnsLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 0, 0), 114, 0));
    this.add(addButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(removeButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(sortUpButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(sortDownButton, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 0, 2), 0, 0));
    this.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 2, 1.0, 1.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 0, 0), 0, 0));
    jScrollPane1.getViewport().add(availableColumnsList, null);
    this.add(jScrollPane2, new GridBagConstraints(2, 1, 1, 2, 1.0, 1.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 0, 2), 0, 0));
    jScrollPane2.getViewport().add(exportedColumnsList, null);
  }

  void addButton_actionPerformed(ActionEvent e) {
    Object[] items = availableColumnsList.getSelectedValues();
    DefaultListModel em = (DefaultListModel) exportedColumnsList.getModel();
    DefaultListModel am = (DefaultListModel) availableColumnsList.getModel();
    for (int i = 0; i < items.length; i++) {
      am.removeElement(items[i]);
      em.addElement(items[i]);
    }
  }

  void removeButton_actionPerformed(ActionEvent e) {
    Object[] items = exportedColumnsList.getSelectedValues();
    DefaultListModel em = (DefaultListModel) exportedColumnsList.getModel();
    DefaultListModel am = (DefaultListModel) availableColumnsList.getModel();
    for (int i = 0; i < items.length; i++) {
      em.removeElement(items[i]);
      am.addElement(items[i]);
    }
  }

  void sortUpButton_actionPerformed(ActionEvent e) {
    int index = exportedColumnsList.getSelectedIndex();
    DefaultListModel vm = (DefaultListModel) exportedColumnsList.getModel();
    if (index > 0) {
      String value = (String) exportedColumnsList.getSelectedValue();
      vm.removeElement(value);
      vm.insertElementAt(value, index - 1);
      exportedColumnsList.setSelectedIndex(index - 1);
    }
  }

  void sortDownButton_actionPerformed(ActionEvent e) {
    int index = exportedColumnsList.getSelectedIndex();
    DefaultListModel vm = (DefaultListModel) exportedColumnsList.getModel();
    if (index < vm.indexOf(vm.lastElement())) {
      String value = (String) exportedColumnsList.getSelectedValue();
      vm.removeElement(value);
      vm.insertElementAt(value, index + 1);
      exportedColumnsList.setSelectedIndex(index + 1);
    }
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  public Map<String,String> getDescriptionIdMap() {
    return descIdMap;
  }

  public Map<String,Property> getDescriptionPropertyMap() {
    return descPropMap;
  }

  public List<String> getExportedPropertyNames() {
	  List<String> v = new ArrayList<String>();
    for (int i = 0; i < exportedColumnsList.getModel().getSize(); i++) {
      String current = (String) exportedColumnsList.getModel().getElementAt(i);
      v.add(descIdMap.get(current));
    }
    return v;
  }
}

class TipiExportSortingPanel_addButton_actionAdapter
    implements java.awt.event.ActionListener {
  TipiSwingExportSortingPanel adaptee;
  TipiExportSortingPanel_addButton_actionAdapter(TipiSwingExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.addButton_actionPerformed(e);
  }
}

class TipiExportSortingPanel_removeButton_actionAdapter
    implements java.awt.event.ActionListener {
  TipiSwingExportSortingPanel adaptee;
  TipiExportSortingPanel_removeButton_actionAdapter(TipiSwingExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.removeButton_actionPerformed(e);
  }
}

class TipiExportSortingPanel_sortUpButton_actionAdapter
    implements java.awt.event.ActionListener {
  TipiSwingExportSortingPanel adaptee;
  TipiExportSortingPanel_sortUpButton_actionAdapter(TipiSwingExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.sortUpButton_actionPerformed(e);
  }
}

class TipiExportSortingPanel_sortDownButton_actionAdapter
    implements java.awt.event.ActionListener {
  TipiSwingExportSortingPanel adaptee;
  TipiExportSortingPanel_sortDownButton_actionAdapter(TipiSwingExportSortingPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.sortDownButton_actionPerformed(e);
  }
}
