package com.dexels.navajo.tipi.components.swingimpl.questioneditor;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import javax.swing.event.*;
import java.util.*;
//import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SelectionPropertyPanel
    extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane tableScroller = new JScrollPane();
  JTable propertyTable = new JTable();
  JToolBar propertyToolbar = new JToolBar();
  JButton addButton = new JButton();
  JButton deleteButton = new JButton();

  private Property myProperty = null;

//  private XMLElement myNode = null;

  private final PropertyTableModel myModel = new PropertyTableModel();
  JComboBox cardinalityBox = new JComboBox();

  private PropertyElementHelper myPropertyElementHelper = null;

  public SelectionPropertyPanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    propertyTable.setModel(myModel);
    cardinalityBox.setModel(new DefaultComboBoxModel(Property.
        VALID_CARDINALITIES));
  }

  public void setPropertyHelper(PropertyElementHelper a) {
    myPropertyElementHelper = a;
  }

  public void clear() {
    myModel.clear();
  }

  private final void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    propertyToolbar.setFloatable(false);
    addButton.setText("Add");
    addButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addButton_actionPerformed(e);
      }
    });
    deleteButton.setText("Delete");
    deleteButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteButton_actionPerformed(e);
      }
    });
    cardinalityBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardinalityBox_actionPerformed(e);
      }
    });
    myModel.addTableModelListener(new javax.swing.event.TableModelListener() {
      public void tableChanged(TableModelEvent e) {
        myModel_tableChanged(e);
      }
    });
    this.add(tableScroller, BorderLayout.CENTER);
    this.add(propertyToolbar, BorderLayout.SOUTH);
    propertyToolbar.add(deleteButton, null);
    propertyToolbar.add(addButton, null);
    this.add(cardinalityBox, BorderLayout.NORTH);
    tableScroller.getViewport().add(propertyTable, null);
  }

  public void load(Property p) {
    myProperty = p;
//    myNode = sn;
//    if (myNode==sn) {
//      throw new RuntimeException("They are f#$%$in the same");
//    }
    myModel.load(p);
    String cardinalities = p.getCardinality();
    if (cardinalities != null) {
      cardinalityBox.setSelectedItem(cardinalities);
    }
  }

  void deleteButton_actionPerformed(ActionEvent e) {
    int[] indices = propertyTable.getSelectedRows();
    ArrayList selections = new ArrayList();
    for (int i = indices.length - 1; i >= 0; i--) {
      /** @todo Fix remove */
//      myNode.removeChildNodeAt(indices[i]);
//      myProperty.getS
      Selection s = myModel.getSelection(indices[i]);
      selections.add(s);
    }

    myPropertyElementHelper.updateExample(myProperty);
    for (int i = 0; i < selections.size(); i++) {
      try {
        myProperty.removeSelection( (Selection) selections.get(i));
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
    }
    load(myProperty);
  }

  void addButton_actionPerformed(ActionEvent e) {

    try {
      Selection s = NavajoFactory.getInstance().createSelection(myProperty.
          getRootDoc(), "new", "new", false);
      myProperty.addSelection(s);
      load(myProperty);
      myPropertyElementHelper.updateExample(myProperty);

    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
  }


  void cardinalityBox_actionPerformed(ActionEvent e) {
    myProperty.setCardinality(""+cardinalityBox.getSelectedItem());
    load(myProperty);
    myPropertyElementHelper.updateExample(myProperty);
  }

  void myModel_tableChanged(TableModelEvent e) {
    myPropertyElementHelper.updateExample(myProperty);
  }

}
