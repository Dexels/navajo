package com.dexels.navajo.tipi.components;

import javax.swing.event.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.swingclient.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 *
 *
 * se JList.setFixedCellHeight() and setFixedCellWidth() in combination with
setPreferredSize() and setMaximumSize() to set size of container.
 */

public class MultipleSelectionPropertyList extends JPanel {
  /** @todo Add action / changelisteners to this class */

  private Property myProperty;
  private DefaultListModel myModel= new DefaultListModel();
  private JList myList = new JList(myModel);
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();

  private int myVisibleRowCount = 8;

   public MultipleSelectionPropertyList() {
    try {
      jbInit();
      myList.setCellRenderer(new PropertyCellRenderer());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    myList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        this_valueChanged(e);
      }
    });
//    setMinimumSize(new Dimension(1,1));
//    setMaximumSize(new Dimension(5000,5000));
//    myList.setMaximumSize(new Dimension(100,100));
//    myModel.addListDataListener(new ListDataListener() {
//      public void intervalAdded(ListDataEvent e) {
//      }
//      public void intervalRemoved(ListDataEvent e) {
//      }
//      public void contentsChanged(ListDataEvent e) {
//      }
//    });
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    myList.setFixedCellHeight(16);
  //  jScrollPane1.setPreferredSize(myList.getPreferredSize());
  jScrollPane1.getViewport().setView(myList);
    this.add(jScrollPane1,  BorderLayout.SOUTH);
  }

  public void setVisibleRowCount(int i) {
//    System.err.println("SETTING ROWCOUNT>> "+i);
//    Thread.dumpStack();
    myList.setVisibleRowCount(i);
    myVisibleRowCount = i;
  }

  public void setProperty(Property p) {
//    myList.setModel(myModel);
    try {
      myProperty = p;
      myModel.clear();
      ArrayList selections = myProperty.getAllSelections();
      if (selections.size() <= 0) {
        System.err.println("Watch it! No selection property, or selection property without selections!");
      }
      else {
        for (int i = 0; i < selections.size(); i++) {
          Selection current = (Selection) selections.get(i);
//          System.err.println("Adding: "+current);
          myModel.addElement(current);
          if (current.isSelected()) {
            myList.addSelectionInterval(i,1);
          }
//          SelectionCheckBox cb = new SelectionCheckBox();
        }
      }
//      updateUI();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    setVisibleRowCount(myVisibleRowCount);
  }
  public void update() {
    // dummy
  }

  void this_valueChanged(ListSelectionEvent e) {
    for (int i = 0; i < myModel.size(); i++) {
      Selection current = (Selection)myModel.get(i);
      current.setSelected(myList.isSelectedIndex(i));
    }

  }

}