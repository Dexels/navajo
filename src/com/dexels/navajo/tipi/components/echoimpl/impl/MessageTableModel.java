package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo2.app.Table;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.app.table.TableColumn;
import nextapp.echo2.app.table.TableColumnModel;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.lazy.MessageListener;

import echopointng.table.DefaultPageableSortableTableModel;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class MessageTableModel extends DefaultPageableSortableTableModel
		implements MessageListener {

	private ArrayList myColumnIds = new ArrayList();

	private ArrayList myColumnTitles = new ArrayList();

	private ArrayList editableList = new ArrayList();

	private Message myMessage;

	private ArrayList filterList = new ArrayList();

	// private boolean isFiltered = false;
	// private int[] filterMap = null;
	// private int filteredRecordCount = -1;
	private Table myTable = null;

	// private int lastSortedColumn = -1;
	// private boolean lastSortedDirection = true;

	private List myData;

	public MessageTableModel(TableColumnModel columnModel) {
		super(columnModel);
	}

	public MessageTableModel(TableColumnModel columnModel, Message m) {
		super(columnModel);
		setMessage(m);
	}

	public void setTable(Table t) {
		this.myTable = t;
	}

	public void messageLoaded(int aap, int noot, int mies) {
	}

	public void clearMessage() {
		if (myMessage != null) {
			if (myMessage.getArraySize() > 0) {
				super.fireTableRowsDeleted(0, myMessage.getArraySize() - 1);
			}

			myMessage = null;
		}

	}

	//  
	// protected List getRows() {
	// return myData;
	// }

	private void createListFromMessage(Message m, List l) {
		for (int i = 0; i < m.getArraySize(); i++) {
			Message current = m.getMessage(i);
			List currentMessage = new ArrayList();
			for (int j = 0; j < current.getAllProperties().size(); j++) {
				// String currentColumn = (String)myColumnIds.get(j);
				Property p = (Property) current.getAllProperties().get(j);
				currentMessage.add(p);
			}
			l.add(currentMessage);
		}
		// return l;
	}

	public void setMessage(Message m) {
		// lastSortedColumn = -1;
		// lastSortedDirection = true;
		myMessage = m;
		createListFromMessage(myMessage, getRows());
		fireTableStructureChanged();

		// fireDataChanged();
		// setRows(myData);
		// filterMap = new int[myMessage.getArraySize()];
	}

	public void addColumn(String id, String title, boolean editable) {
		myColumnIds.add(id);
		myColumnTitles.add(title);
		editableList.add(new Boolean(editable));
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
	}

	public int getColumnCount() {
		return myColumnIds.size();
	}

	// public Object getValueAt(int column, int row) {
	// if (myMessage == null) {
	// return null;
	// }
	// if (column >= myColumnIds.size()) {
	// return null;
	// }
	// Message m = getMessageRow(row);
	// if (m != null) {
	// String columnName = (String) myColumnIds.get(column);
	// if (columnName == null) {
	// return null;
	// }
	// Property p = m.getProperty(columnName);
	// return p;
	// }
	// else {
	// return null;
	// }
	//
	// }

	// public int getRowCount() {
	// if (myMessage == null) {
	// return 0;
	// }
	// return myMessage.getArraySize();
	// }

	public String getColumnName(int column) {
		System.err.println("Getting column name: " + column);
		String s = (String) myColumnTitles.get(column);
		if (s == null) {
			s = getColumnId(column);
			if (s == null) {
				return super.getColumnName(column);
			} else {
				return s;
			}
		} else {
			return s;
		}
	}

	public String getColumnId(int column) {
		String s = (String) myColumnIds.get(column);
		return s;
	}

	// public void setValueAt(Object aValue, int row, int column) {
	// }

	public Message getMessageRow(int row) {
		if (row < 0) {
			return null;
		}
		if (myMessage == null) {
			return null;
		}
		return myMessage.getMessage(row);

	}

	public Class getColumnClass(int columnIndex) {
		return Property.class;
	}

	public void messageLoaded(int startIndex, int endIndex) {
		fireTableRowsUpdated(startIndex, endIndex);
	}

	public void fireDataChanged() {
		fireTableDataChanged();
	}

	public void createColumnsFromModel(MessageTable table,
			TableColumnModel tcm, TableCellRenderer myCellRenderer) {
		while (tcm.getColumnCount() > 0) {
			tcm.removeColumn(tcm.getColumn(0));
		}

		int columnCount = getColumnCount();
		for (int index = 0; index < columnCount; ++index) {
			// tcm.addColumn(new TableColumn(index,null,myCellRenderer,new
			// SortableTableHeaderRenderer()));
			tcm.addColumn(new TableColumn(index, null));
		}

	}

	// public void clearPropertyFilters() {
	// filterList.clear();
	// isFiltered = false;
	// }
	//
	//
	// public void removeFilters() {
	// isFiltered = false;
	// filteredRecordCount = -1;
	// }

	// public void setSortingState(int columnIndex, boolean ascending) {
	// lastSortedColumn = columnIndex;
	// lastSortedDirection = ascending;
	// }
	//
	// public int getSortedColumn() {
	// return lastSortedColumn;
	// }
	//
	// public boolean getSortingDirection() {
	// return lastSortedDirection;
	// }

}
