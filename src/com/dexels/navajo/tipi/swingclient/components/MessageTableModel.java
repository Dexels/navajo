package com.dexels.navajo.tipi.swingclient.components;

import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.filter.*;
import com.dexels.navajo.document.lazy.*;

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
     {

  private final ArrayList myColumnIds = new ArrayList();
  private final ArrayList myColumnTitles = new ArrayList();
  //private final ArrayList editableList = new ArrayList();
  private final HashMap editableMap = new HashMap();
  private Message myMessage;
  private final ArrayList filterList = new ArrayList();
  private boolean isFiltered = false;
  private int[] filterMap = null;
  private int filteredRecordCount = -1;
 
  private int lastSortedColumn = -1;
  private boolean lastSortedDirection = true;
  private boolean readOnly = false;
  private int subsractColumnCount = 1;
  private boolean rowHeadersVisible = true;

  private final Map myTypeMap = new HashMap();

  public MessageTableModel() {
  }

  public MessageTableModel(Message m) {
    setMessage(m);
  }

  public void setShowRowHeaders(boolean b){
    rowHeadersVisible = b;
    if(rowHeadersVisible){
      subsractColumnCount = 1;
    }else{
      subsractColumnCount = 0;
    }
  }

  public boolean isShowingRowHeaders(){
    return rowHeadersVisible;
  }

  public boolean isShowingColumn(String id){
    return myColumnIds.contains(id);
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
    
//    if(myMessage!=null) {
//    	myMessage.removePropertyChangeListener(this);
//    }
    
    myMessage = m;
    filterMap = new int[myMessage.getArraySize()];

    if(myMessage!=null) {
    	myMessage.addPropertyChangeListener(new PropertyChangeListener(){

				public void propertyChange(final PropertyChangeEvent e) {
					final Property p = (Property) e.getSource();
					Message m = p.getParentMessage();
					if(m!=null) {
						final int row = getRowOfMessage(m);
						final int column = getColumnOfProperty(row, p);
						try {
							if(!SwingUtilities.isEventDispatchThread()) {
							SwingUtilities.invokeAndWait(new Runnable(){

								public void run() {
									if(row>=0 && column>=0) {
										fireTableCellUpdated(row, column);
									}
//									System.err.println("Updating: "+row+" column: "+column+" property: "+p.getName()+" orig. row: "+p.getParentMessage().getName()+" ---- "+p.getParentMessage().getIndex());
//									System.err.println("Old: "+e.getOldValue()+" new: "+e.getNewValue());
								}});
							} else {
								if(row>=0 && column>=0) {
									fireTableCellUpdated(row, column);
								}
//								System.err.println("Updating: "+row+" column: "+column+" property: "+p.getName()+" orig. row: "+p.getParentMessage().getName()+" ---- "+p.getParentMessage().getIndex());
//								System.err.println("Old: "+e.getOldValue()+" new: "+e.getNewValue());

							}
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							e1.printStackTrace();
						}
								}
				
			}});
    }
    //    re
//    messageChanged();
  }

  public void setReadOnly(boolean b) {
    readOnly = b;
  }

  public String getTypeHint(String id) {
    return (String) myTypeMap.get(id);
  }

  public void setTypeHint(String id, String type) {
    myTypeMap.put(id, type);
  }

  public int addColumn(String id, String title, boolean editable, String type) {
    int nn = addColumn(id, title, editable);
    myTypeMap.put(id, type);
    return nn;
  }

  public void setColumnTitle(int index, String title) {
    myColumnTitles.set(index, title);
  }

  public int addColumn(String id, String title, boolean editable) {
//    System.err.println("Adding column: " + id + ", " + editable + ", NEW VERSION BABY");
    myColumnIds.add(id);
    myColumnTitles.add(title);
    if(!editableMap.containsKey(id)){							// EDITABLE IS DETERMINED BY THE FIRST
    	editableMap.put(id, Boolean.valueOf(editable));
    }
    int index = myColumnIds.indexOf(id);
    return index + subsractColumnCount;

//    messageChanged();
  }

  public void removeColumn(String id) {
    int index = myColumnIds.indexOf(id);
    if (index > -1) {
      removeColumn(index);
    }
  }

public void removeColumn(int index) {
      String id = (String) myColumnIds.get(index);
      myColumnIds.remove(index);
      myColumnTitles.remove(index);
      myTypeMap.remove(id);
}
  

  public void removeAllColumns() {
    myColumnIds.clear();
    myColumnTitles.clear();
//    editableList.clear(); // REMOVED clear of editable, so we REMEMBER
    myTypeMap.clear();
  }

  public void messageChanged() {
//    super.fireTableStructureChanged();

    if (isFiltered) {
      performFilters();
    }

  }

  public int getColumnCount() {
    return myColumnIds.size() + subsractColumnCount;
  }

  public Object getValueAt(int row, int column) {
    if (column == 0 && rowHeadersVisible) {
      return new Integer(row);
    }
    else {
      if (myMessage == null) {
        return null;
      }
      if ((column-subsractColumnCount) >= myColumnIds.size()) {
        return null;
      }

      String columnName = (String) myColumnIds.get(column-subsractColumnCount);
      if (columnName == null) {
        return null;
      }

      Message m = getMessageRow(row);
      if (m != null) {
        Property p = m.getProperty(columnName);
        return p;
      }
      else {
        return null;
      }

    }

  }

  public void updateProperties(List<Property> l) {
    if (l == null) {
      return;
    }
    for (int i = 0; i < l.size(); i++) {
      Property current = l.get(i);
      firePropertyChanged(current,"value");
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
    if (column == 0 && rowHeadersVisible) {
      return "";
    }
    else {
      if (myColumnTitles.size() > 0) {
        String s = ((column-subsractColumnCount) < myColumnTitles.size()) ? (String) myColumnTitles.get(column-subsractColumnCount) : null;
        if (s == null) {
          return super.getColumnName(column-subsractColumnCount);
        }
        else {
          return s;
        }
      }
      else {
        return super.getColumnName(column-1);
      }
    }
  }

  public String getColumnId(int column) {
    if(column == 0 && rowHeadersVisible){
      return "";
    }else{
      if(column-subsractColumnCount >= myColumnIds.size()){
        return "ERROR!";
      }
      String s = (String) myColumnIds.get(column-subsractColumnCount);
    return s;
    }
  }

  public int getColumnIndex(String id){
    for(int i=0;i<myColumnIds.size();i++){
      String mId = (String) myColumnIds.get(i);
      if(id.equals(mId)){
        return i + subsractColumnCount;
      }
    }
    return -1;
  }

  public void setValueAt(Object aValue, int row, int column) {
  }

  public Message getMessageRow(int row) {
    //if(myMessage == null || row < 0 || row >= myMessage.getArraySize()){
    if (myMessage == null || row < 0) {
      return null;
    }

    if (isFiltered && filterMap != null && filterMap.length > row) {
      return myMessage.getMessage(filterMap[row]);
    }
    isFiltered = false;
    return myMessage.getMessage(row);
  }

  public Class getColumnClass(int columnIndex) {
    return Property.class;
  }

  public boolean isColumnEditable(int columnIndex) {
  	String id = (String)myColumnIds.get(columnIndex - subsractColumnCount);
    Boolean b = (Boolean) editableMap.get(id);
    return b.booleanValue();
  }

  
  public void setColumnEditable(int columnIndex, boolean value) {
	  	String id = (String)myColumnIds.get(columnIndex - subsractColumnCount);
	  	editableMap.put(id, value);
  }
  public boolean isCellEditable(int rowIndex, int columnIndex) {
	  
    int column = columnIndex - subsractColumnCount;
    if (column < 0) {
      return true;
    }
    String id =  (String)myColumnIds.get(column);
//    System.err.println("In isCelleditable in MessageTableModel ("+rowIndex+","+column+")");
//    System.err.println("EditableListSize: " + editableList.size());
//    System.err.println(editableList.toString());
    if (readOnly) {
      System.err.println("Readonly table");
      return false;
    }
  if (!editableMap.containsKey(id)) {
      System.err.println("Not in editable list. index too big");
      return false;
    }
    Boolean b = (Boolean) editableMap.get(id);
    if (b == null) {
      System.err.println("Nothing in editablelist");
      return false;
    }
    if (b.booleanValue() == false) {
      System.err.println("Forced non-editable by table");
      return false;
    }
    Message m = getMessageRow(rowIndex);
    if (m == null) {
      //System.err.println("False: no such message");
      return false;
    }
    Property p = m.getProperty(getColumnId(columnIndex));
    if (p == null) {
      //System.err.println("False: no such property: " + getColumnId(columnIndex));
      return false;
    }
    else {
//      System.err.println("Property found. (" + p.getName() + ") Returning: "+p.isDirIn()+" row: "+rowIndex+" column: "+columnIndex);
//      Thread.dumpStack();
      return p.isDirIn();
    }
//    return b.booleanValue();
//    return true;
  }

  public void messageLoaded(int startIndex, int endIndex, int newTotal) {

    fireTableRowsUpdated(startIndex, endIndex);
    if (newTotal != getRowCount()) {
      fireTableRowsDeleted(newTotal, getRowCount());
    }
  }

  public final void fireDataChanged() {
    fireTableDataChanged();
//    filteredRecordCount = 0;
//    performFilters();
  }

  /** @todo Rewrite this inefficient piece of sh@# */
  public final int getColumnOfProperty(int row, Property p) {
    int start = 0;
    if(p==null) {
    	return -1;
    }
    if(rowHeadersVisible){
      start = 1;
    }
    for (int i = start; i < getColumnCount(); i++) {
      Property current = (Property) getValueAt(row, i);
      if(current==null) {
    	  continue;
      }
      if (current.getName().equals(p.getName())) {
        return i;
      }
    }
    return -1;
  }

  /** @todo Rewrite this inefficient piece of sh@# */
  public final int getRowOfMessage(Message m) {
    for (int i = 0; i < getRowCount(); i++) {
      Message current = getMessageRow(i);
      if (current == m) {
        return i;
      }
    }
    return -1;
  }

  public final void firePropertyChanged(Property p,String beanPropertyName) {
	  
    Message parent = p.getParentMessage();
    if (parent == null || myMessage == null) {
      System.err.println("Null parents involved. Bad news.");
    }
    if (parent.getArrayParentMessage() != myMessage) {
//      System.err.println("Wrong parent: "+parent.getFullMessageName()+" instead of "+myMessage.getFullMessageName());
      return;
    }

//    System.err.println("Yes. Right parent.");
    final int row = getRowOfMessage(parent);
    if (row == -1) {
      System.err.println("Trouble locating message");
      return;
    }
    final int column = getColumnOfProperty(row, p);
//    try {
//      System.err.println("Property: " + p.getFullPropertyName() + " row: " + row + " column: " + column);
//    }
//    catch (NavajoException ex) {
//    }
    if(column>=0) {
    	if(SwingUtilities.isEventDispatchThread()) {
            fireTableCellUpdated(row, column);
    	} else {
    		try {
				SwingUtilities.invokeAndWait(new Runnable(){

					public void run() {
				        fireTableCellUpdated(row, column);
				        		
					}});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
    	}
    }
  }
  
  public boolean hasPropertyFilters(){
  	return filterList.size() > 0;
  }

  public void addPropertyFilter(String propName, Property value, String operator) {
    PropertyFilter pf = new PropertyFilter(propName, value, operator);
    filterList.add(pf);
  }

  public void clearPropertyFilters() {
    filterList.clear();
    isFiltered = false;
  }

  public void performFilters() {
    try{
      if (myMessage == null) {
        return;
      }
      int count = 0;
      isFiltered = true;
      for (int i = 0; i < myMessage.getArraySize(); i++) {
        Message current = myMessage.getMessage(i);
        boolean complying = true;
//      System.err.println("Checking row: " + i);
        for (int j = 0; j < filterList.size(); j++) {
          PropertyFilter currentFilter = (PropertyFilter) filterList.get(j);
//        System.err.println("Got filter");
          if (currentFilter.compliesWith(current)) {
          }
          else {
            complying = false;
            break;
          }
        }
        if (complying) {
          filterMap[count] = i;
          count++;
        }
      }
      filteredRecordCount = count;
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public int getFilteredRecordCount() {
    return filteredRecordCount;
  }

  public void removeFilters() {
    isFiltered = false;
    filteredRecordCount = -1;
  }

  public void setSortingState(int columnIndex, boolean ascending) {
    lastSortedColumn = columnIndex;
    lastSortedDirection = ascending;
  }


  public int getSortedColumn() {
    return lastSortedColumn;
  }

  public boolean getSortingDirection() {
    return lastSortedDirection;
  }



public void propertyChange(PropertyChangeEvent arg0) {
	// TODO Auto-generated method stub
	
}

}
