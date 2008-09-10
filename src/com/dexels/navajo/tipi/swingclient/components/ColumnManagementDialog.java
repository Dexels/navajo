package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.awt.event.*;
/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

class NameIdMap {
  HashMap propertyNameIdMap = new HashMap();
  HashMap propertyIdNameMap = new HashMap();

  public void put(String id, String name) {
    propertyNameIdMap.put(name, id);
    propertyIdNameMap.put(id, name);
  }

  public String getById(String id) {
    return (String) propertyIdNameMap.get(id);
  }

  public String getByName(String name) {
    return (String) propertyNameIdMap.get(name);
  }

  public void clear() {
    propertyNameIdMap.clear();
    propertyIdNameMap.clear();
  }
}

public class ColumnManagementDialog
    extends JDialog {
  JList availableColumnList = new JList();
  JList visibleColumnList = new JList();
  JButton hideButton = new JButton();
  JButton showButton = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  MessageTable myTable;
  NameIdMap nameIdMap = new NameIdMap();
  JScrollPane scroll1 = new JScrollPane();
  JScrollPane scroll2 = new JScrollPane();
  ArrayList availableItems = new ArrayList();
  ArrayList visibleItems = new ArrayList();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JButton jButton1 = new JButton();
  JButton upButton = new JButton();
  JButton downButton = new JButton();
  String[] ignoreList;
  private Container myToplevel;
  HashMap editableMap = new HashMap();

  public ColumnManagementDialog(Dialog toplevel) {
    super(toplevel, true);
    myToplevel = toplevel;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ColumnManagementDialog(Frame toplevel) {
    super(toplevel, true);
    myToplevel = toplevel;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setTopLevel(Frame top) {
  }

  public final void setIgnoreList(String[] list) {
    ignoreList = list;
  }

  private final boolean isInIgnoreList(String s) {
    if (ignoreList == null) {
      return false;
    }
    for (int i = 0; i < ignoreList.length; i++) {
      if (ignoreList[i].equals(s)) {
        return true;
      }
    }
    return false;
  }

  public final void setMessageTable(MessageTable mt) {
    myTable = mt;
    if (myTable == null) {
      System.err.println("Null message table! ignoring");
      return;
    }

    Message m = mt.getMessage();
    Message first = m.getMessage(0);
    ArrayList props = first.getAllProperties();

    for (int i = 0; i < props.size(); i++) {
      Property current = (Property) props.get(i);
      String name = current.getDescription();
      String id = current.getName();

      // Check if the column is allready showing and what his editability is, then use that.
      boolean tc = myTable.getMessageModel().isShowingColumn(id);
      int index = myTable.getMessageModel().getColumnIndex(id);
      if (tc && index >= 0) {
        boolean editableNow = myTable.getMessageModel().isColumnEditable(index);
        editableMap.put(id,  Boolean.valueOf(editableNow));
      }else {
        if (current.isDirIn()) {
          editableMap.put(id, Boolean.TRUE);
        }
        else {
          editableMap.put(id, Boolean.FALSE);
        }
      }
      if (name == null) {
        name = id;
      }
      if (!isInIgnoreList(current.getName())) {
        nameIdMap.put(id, name);
        availableItems.add(id);
      }
    }
    fillEmUp();
  }

  private final void fillEmUp() {
    int currentColumnCount = myTable.getColumnCount();
    DefaultListModel model = (DefaultListModel) visibleColumnList.getModel();

    for (int i = 0; i < currentColumnCount; i++) {
      String item = myTable.getMessageModel().getColumnName(i);
      String id = myTable.getMessageModel().getColumnId(i);
      model.addElement(item);
      visibleItems.add(id);
      availableItems.remove(id);
      nameIdMap.put(id, item);
    }

    DefaultListModel model2 = (DefaultListModel) availableColumnList.getModel();
    for (int j = 0; j < availableItems.size(); j++) {
      String id = (String) availableItems.get(j);
      String name = nameIdMap.getById(id);
      model2.addElement(name);
    }
  }

  private final void jbInit() throws Exception {
    availableColumnList.setModel(new DefaultListModel());
    visibleColumnList.setModel(new DefaultListModel());
    this.setTitle("Toevoegen en verwijderen van kolommen");
    this.getContentPane().setSize(500, 400);
    setLocationRelativeTo(myToplevel);
    this.getContentPane().setLayout(gridBagLayout1);
    hideButton.setIcon(new ImageIcon(ColumnManagementDialog.class.getResource("arrow_left.gif")));
    hideButton.setText("");
    hideButton.addActionListener(new ColumnManagementDialog_hideButton_actionAdapter(this));
    showButton.setIcon(new ImageIcon(ColumnManagementDialog.class.getResource("arrow_right.gif")));
    showButton.addActionListener(new ColumnManagementDialog_showButton_actionAdapter(this));
    jLabel1.setText("Beschikbare kolommen");
    jLabel2.setText("Zichtbare kolommen");
    okButton.setText("Ok");
    okButton.addActionListener(new ColumnManagementDialog_okButton_actionAdapter(this));
    cancelButton.setToolTipText("");
    cancelButton.setText("Annuleren");
    cancelButton.addActionListener(new ColumnManagementDialog_cancelButton_actionAdapter(this));
    jButton1.setText("jButton1");
    upButton.addActionListener(new ColumnManagementDialog_upButton_actionAdapter(this));
    downButton.setIcon(new ImageIcon(ColumnManagementDialog.class.getResource("arrow_down.gif")));
    downButton.setText("");
    downButton.addActionListener(new ColumnManagementDialog_downButton_actionAdapter(this));
    upButton.setIcon(new ImageIcon(ColumnManagementDialog.class.getResource("arrow_up.gif")));
    upButton.setText("");
    scroll1.getViewport().add(availableColumnList);
    this.getContentPane().add(scroll1, new GridBagConstraints(0, 1, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 130, 204));
    this.getContentPane().add(hideButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 26, 0));
    this.getContentPane().add(showButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 26, 0));
    this.getContentPane().add(scroll2, new GridBagConstraints(2, 1, 2, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 133, 206));
    scroll2.getViewport().add(visibleColumnList);
    this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 10, 0));
    this.getContentPane().add(jLabel2, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 32, 0));
    this.getContentPane().add(okButton, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(cancelButton, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(upButton, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(downButton, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
  }

  final void hideButton_actionPerformed(ActionEvent e) {
    Object[] removedNames = visibleColumnList.getSelectedValues();
    DefaultListModel vm = (DefaultListModel) visibleColumnList.getModel();
    DefaultListModel am = (DefaultListModel) availableColumnList.getModel();
    for (int i = 0; i < removedNames.length; i++) {
      String name = removedNames[i].toString();
      vm.removeElement(name);
      am.addElement(name);
      availableItems.add(name);
      visibleItems.remove(name);
    }
    visibleColumnList.setSelectedIndex(1);
  }

  final void showButton_actionPerformed(ActionEvent e) {
    Object[] addedNames = availableColumnList.getSelectedValues();
    DefaultListModel vm = (DefaultListModel) visibleColumnList.getModel();
    DefaultListModel am = (DefaultListModel) availableColumnList.getModel();
    for (int i = 0; i < addedNames.length; i++) {
      String name = addedNames[i].toString();
      am.removeElement(name);
      vm.addElement(name);
      availableItems.remove(name);
      visibleItems.add(name);
    }
    visibleColumnList.setSelectedIndex(1);
  }

  final void okButton_actionPerformed(ActionEvent e) {
    int currentColumnCount = myTable.getColumnCount();
    int min = (myTable.isShowingRowHeaders() ? 1 : 0);
    for (int i = currentColumnCount - 1; i >= min; i--) {
      String item = myTable.getMessageModel().getColumnId(i);
      myTable.removeColumn(item);
    }
    DefaultListModel vm = (DefaultListModel) visibleColumnList.getModel();
    Enumeration m = vm.elements();
    myTable.removeAllColumns();
    int i = 0;
    while (m.hasMoreElements()) {
      String name = (String) m.nextElement();
      System.err.println("Name: " + name);
      String id = nameIdMap.getByName(name);
      if (id != null && !id.equals("") && !id.equals("ERROR!")) {
        boolean editable = false;
        if(editableMap.get(id) != null){
          editable = ((Boolean)editableMap.get(id)).booleanValue();
        }
        myTable.addColumn(id, name, editable);
      }
    }
    this.setVisible(false);
//    SwingUtilities.invokeLater(new Runnable() {
//      public void run() {
//        myTable.setDefaultColumnSizes(myTable.getMessage());
        myTable.createDefaultFromModel(null);
        myTable.setEqualColumnSizes();
//      }
//    });
  }

  final void cancelButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  final void upButton_actionPerformed(ActionEvent e) {
    int index = visibleColumnList.getSelectedIndex();
    DefaultListModel vm = (DefaultListModel) visibleColumnList.getModel();
    if (index > 0) {
      String value = (String) visibleColumnList.getSelectedValue();
      vm.removeElement(value);
      vm.insertElementAt(value, index - 1);
      visibleColumnList.setSelectedIndex(index - 1);
    }
  }

  final void downButton_actionPerformed(ActionEvent e) {
    int index = visibleColumnList.getSelectedIndex();
    DefaultListModel vm = (DefaultListModel) visibleColumnList.getModel();
    if (index < vm.indexOf(vm.lastElement())) {
      String value = (String) visibleColumnList.getSelectedValue();
      vm.removeElement(value);
      vm.insertElementAt(value, index + 1);
      visibleColumnList.setSelectedIndex(index + 1);
    }
  }

}

class ColumnManagementDialog_hideButton_actionAdapter
    implements java.awt.event.ActionListener {
  ColumnManagementDialog adaptee;

  ColumnManagementDialog_hideButton_actionAdapter(ColumnManagementDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.hideButton_actionPerformed(e);
  }
}

class ColumnManagementDialog_showButton_actionAdapter
    implements java.awt.event.ActionListener {
  ColumnManagementDialog adaptee;

  ColumnManagementDialog_showButton_actionAdapter(ColumnManagementDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.showButton_actionPerformed(e);
  }
}

class ColumnManagementDialog_okButton_actionAdapter
    implements java.awt.event.ActionListener {
  ColumnManagementDialog adaptee;

  ColumnManagementDialog_okButton_actionAdapter(ColumnManagementDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class ColumnManagementDialog_cancelButton_actionAdapter
    implements java.awt.event.ActionListener {
  ColumnManagementDialog adaptee;

  ColumnManagementDialog_cancelButton_actionAdapter(ColumnManagementDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class ColumnManagementDialog_upButton_actionAdapter
    implements java.awt.event.ActionListener {
  ColumnManagementDialog adaptee;

  ColumnManagementDialog_upButton_actionAdapter(ColumnManagementDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.upButton_actionPerformed(e);
  }
}

class ColumnManagementDialog_downButton_actionAdapter
    implements java.awt.event.ActionListener {
  ColumnManagementDialog adaptee;

  ColumnManagementDialog_downButton_actionAdapter(ColumnManagementDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.downButton_actionPerformed(e);
  }
}
