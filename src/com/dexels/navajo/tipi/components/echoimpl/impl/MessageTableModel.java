package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.util.*;

//import javax.swing.*;
//import javax.swing.table.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.lazy.*;
import echopoint.*;
import nextapp.echo.table.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class MessageTableModel
    extends AbstractTableModel
    implements MessageListener {

  private ArrayList myColumnIds = new ArrayList();
  private ArrayList myColumnTitles = new ArrayList();
  private ArrayList editableList = new ArrayList();
  private Message myMessage;
  private ArrayList filterList = new ArrayList();
  private boolean isFiltered = false;
  private int[] filterMap = null;
  private int filteredRecordCount = -1;
  private SortableTable myTable = null;

//  private boolean autoResize = false;
//  private boolean initialResize = true;
  private int lastSortedColumn = -1;
  private boolean lastSortedDirection = true;

  public MessageTableModel() {
  }

  public MessageTableModel(Message m) {
    setMessage(m);
  }

  public void setTable(SortableTable t) {
    this.myTable = t;
  }

  public void messageLoaded(int aap, int noot, int mies) {
    // Dont really remember what it should do. Added it to make it compile
  }

  public void clearMessage() {
    if (myMessage != null) {
      if (myMessage.getArraySize() > 0) {
        super.fireTableRowsDeleted(0, myMessage.getArraySize() - 1);
      }

      myMessage = null;
    }

  }

  public void setMessage(Message m) {
//    initialResize = true;

    lastSortedColumn = -1;
    lastSortedDirection = true;
    myMessage = m;
    filterMap = new int[myMessage.getArraySize()];
//    re
//    messageChanged();
  }

  public void addColumn(String id, String title, boolean editable) {
    myColumnIds.add(id);
    myColumnTitles.add(title);
    editableList.add(new Boolean(editable));
//    messageChanged();
  }

  public void removeColumn(String id) {
    int index = myColumnIds.indexOf(id);
    if (index > -1) {
      myColumnIds.remove(index);
      myColumnTitles.remove(index);
      editableList.remove(index);
    }
  }

  public void removeAllColumns() {
    myColumnIds.clear();
    myColumnTitles.clear();
    editableList.clear();
  }

  public void messageChanged() {
//    super.fireTableStructureChanged();

//    if (isFiltered) {
//      performFilters();
//    }

  }

  public int getColumnCount() {
    return myColumnIds.size();
  }

  public Object getValueAt(int column, int row) {
//    System.err.println("Get value at row: "+row+" column: " +column);
    if (myMessage == null) {
      return null;
    }
    if (column >= myColumnIds.size()) {
      return null;
    }
    Message m = getMessageRow(row);
    if (m != null) {
      String columnName = (String) myColumnIds.get(column);
      if (columnName == null) {
        return null;
      }
      Property p = m.getProperty(columnName);
      return p;
    }
    else {
      return null;
    }

  }

  public int getRowCount() {
    if (myMessage == null) {
      return 0;
    }
    if (isFiltered) {
      return filteredRecordCount;
    }
    return myMessage.getArraySize();
  }

  public String getColumnName(int column) {
    String s = (String) myColumnTitles.get(column);
    if (s == null) {
      return super.getColumnName(column);
    }
    else {
      return s;
    }
  }

  public String getColumnId(int column) {
    String s = (String) myColumnIds.get(column);
    return s;
  }

  public void setValueAt(Object aValue, int row, int column) {
  }

  public Message getMessageRow(int row) {
    if (row < 0) {
      return null;
    }
    if (myMessage == null) {
      return null;
    }
    if (isFiltered) {
      return myMessage.getMessage(filterMap[row]);
    }
    else {
      return myMessage.getMessage(row);
    }

  }

  public Class getColumnClass(int columnIndex) {
    return Property.class;
  }

//
//  public boolean isCellEditable(int rowIndex, int columnIndex) {
//    Boolean b = (Boolean) editableList.get(columnIndex);
//    if (b == null) {
//      return true;
//    }
//    return b.booleanValue();
//  }

  public void messageLoaded(int startIndex, int endIndex) {
    fireTableRowsUpdated(startIndex, endIndex);
  }

  public void fireDataChanged() {
    fireTableDataChanged();
  }

  public void clearPropertyFilters() {
    filterList.clear();
    isFiltered = false;
  }

//  public void performFilters() {
//    if (myMessage == null) {
//      return;
//    }
//    int count = 0;
//    isFiltered = true;
//    for (int i = 0; i < myMessage.getArraySize(); i++) {
//      Message current = (Message) myMessage.getMessage(i);
//      boolean complying = true;
//      for (int j = 0; j < filterList.size(); j++) {
//        PropertyFilter currentFilter = (PropertyFilter) filterList.get(j);
//        if (currentFilter.compliesWith(current)) {
//        }
//        else {
//          complying = false;
//          break;
//        }
//      }
//      if (complying) {
//        filterMap[count] = i;
//        count++;
//      }
//    }
//    filteredRecordCount = count;
//  }

  public void removeFilters() {
    isFiltered = false;
    filteredRecordCount = -1;
  }

  public void setSortingState(int columnIndex, boolean ascending) {
    lastSortedColumn = columnIndex;
    lastSortedDirection = ascending;
  }

//  public void saveColumns() {
//    if (lastSortedColumn == -1 || lastSortedColumn >= myColumnIds.size()) {
//      System.err.println("Unsorted");
//    }
//    else {
//      System.err.println("Sorting column: " + myColumnIds.get(lastSortedColumn) + "ascending? : " + lastSortedDirection);
//    }
//    MessageTableColumnModel tc = (MessageTableColumnModel) myTable.getColumnModel();
//    for (int i = 0; i < myColumnIds.size(); i++) {
//      TableColumn tcm = tc.getColumn(i);
//      System.err.println("    Column: " + myColumnIds.get(i) + " size: " + tcm.getWidth());
//    }
//  }
//
  public int getSortedColumn() {
    return lastSortedColumn;
  }

  public boolean getSortingDirection() {
    return lastSortedDirection;
  }

}
