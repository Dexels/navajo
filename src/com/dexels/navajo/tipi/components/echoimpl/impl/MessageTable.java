package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.echoimpl.*;
import echopoint.*;
import echopoint.table.*;
import nextapp.echo.event.*;
import nextapp.echo.table.*;

public class MessageTable
    extends SelectableTable {
  private MessageTableModel myModel = null;
  private TableCellRenderer myRenderer = new EchoPropertyComponent();

  private ArrayList ids = new ArrayList();
  private ArrayList names = new ArrayList();
  private ArrayList editables = new ArrayList();

  public void setMessage(Message m) {
    setSelectionMode(SelectableTable.SINGLE_SELECTION);
    setAutoCreateColumnsFromModel(false);
    myModel = new MessageTableModel(m);

    for (int i = 0; i < ids.size(); i++) {
      myModel.addColumn( (String) ids.get(i), (String) names.get(i), ( (Boolean) editables.get(i)).booleanValue());
    }
    SortablePagedTableModel sptm = new SortablePagedTableModel(myModel);
    sptm.setRowsPerPage(25);
    setModel(sptm);
    createDefaultColumnsFromModel();
    setDefaultRenderer(Property.class, myRenderer);
//    debugTableModel();
  }

  public void removeAllColumns() {
    for (int i = 0; i < ids.size(); i++) {
      String id = (String) ids.get(i);
      removeColumn(id);
    }
    ids.clear();
    names.clear();
    editables.clear();
  }

  public void nextPage() {
    SortablePagedTableModel sptm = (SortablePagedTableModel) getModel();
    sptm.next();
  }

  public void previousPage() {
    SortablePagedTableModel sptm = (SortablePagedTableModel) getModel();
    sptm.previous();
  }

  public int getPageCount() {
    SortablePagedTableModel sptm = (SortablePagedTableModel) getModel();
    return sptm.getMaxPageIndex();
  }

  public int getCurrentPage() {
    SortablePagedTableModel sptm = (SortablePagedTableModel) getModel();
    return sptm.getPageIndex();
  }

  public MessageTableModel getMessageTableModel() {
    return (MessageTableModel) getModel();
  }

  public void debugTableModel() {
    System.err.println("RowCount: " + myModel.getRowCount());
    System.err.println("ColumnCount: " + myModel.getColumnCount());
    for (int i = 0; i < myModel.getRowCount(); i++) {
      for (int j = 0; j < myModel.getColumnCount(); j++) {
        System.err.println("ROW: " + i + " COLUMN: " + j);
        Object value = myModel.getValueAt(i, j);
        if (value != null) {
          System.err.print("Value class: " + value.getClass());
          System.err.println("Value: " + value.toString());
        }
      }
    }

  }

  public Message getSelectedMessage() {
    int index = getSelectedIndex();
    return myModel.getMessageRow(index);
  }

  public void addActionListener(ActionListener al) {
    System.err.println("Not yet implemented");
  }

  public void addColumn(String id, String title, boolean editable) {
//    System.err.println("addColumn: start");
//    System.err.println("ID: "+id);
//    System.err.println("Title: "+title+" editable: "+editable);
//
    ids.add(id);
    names.add(title);
    editables.add(new Boolean(editable));
//    System.err.println("addCOlumn: finished");
  }

  public void removeColumn(String id) {
    myModel.removeColumn(id);
    /** @todo Implement. Or restructure class. */
  }

}
