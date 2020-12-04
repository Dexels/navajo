/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.echoclient.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.DefaultTableModel;
import nextapp.echo2.app.table.TableColumnModel;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;



public class MessageTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -2456719890318915748L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MessageTableModel.class);
	private Message myMessage;
	private final MessageTable myTable;
	private final TableColumnModel myColumnModel;
	private ListSelectionModel mySelectionModel;
	/**
	 * Creates a List of Lists based on the data backing this model in the
	 * superclass.
	 */
	protected List<List<Object>> getRows() {
		List<List<Object>> rows = new LinkedList<List<Object>>();
		for (int i = 0; i < getRowCount(); i++) {
			List<Object> row = new LinkedList<Object>();
			for (int j = 0; j < getColumnCount(); j++) {
				row.add(j, getValueAt(j, i));
			}
			// Fix, to make sure the sorting/selecting goes well
			// I need to be able to find out what message it was originally.
			Message current = null;
			if (getColumnCount() > 1) {
				// take the first value, value 0 is the boolean
				Property p = (Property) getValueAt(1, i);
				if (p != null) {
					current = p.getParentMessage();
				}
			}
			row.add(current);
			rows.add(row);
		}
		return rows;
	}

	// public MessageTableModel(MessageTable t, TableColumnModel columnModel) {
	// // super(columnModel);
	// this.myTable = t;
	// myColumnModel = columnModel;
	// }

	@Override
	public int getRowCount() {
		if (myMessage != null) {
			return myMessage.getArraySize();
		}
		return super.getRowCount();
	}

	public MessageTableModel(MessageTable t, TableColumnModel columnModel,
			Message m, ListSelectionModel selection) {
		// super(columnModel);
		this.myTable = t;
		this.mySelectionModel = selection;
		myColumnModel = columnModel;
		setMessage(m);
	}

	public void messageLoaded(int aap, int noot, int mies) {
	}

	@Override
	public Object getValueAt(int column, int row) {
		if (column == 0) {
			return new Boolean(mySelectionModel.isSelectedIndex(row));
		}
		Object o = super.getValueAt(column - 1, row);
		return o;
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
	//
	// private void createListFromMessage(Message m, List l) {
	// l.clear();
	// for (int i = 0; i < m.getArraySize(); i++) {
	// Message current = m.getMessage(i);
	// List currentMessage = new ArrayList();
	// for (int j = 0; j < current.getAllProperties().size(); j++) {
	// // String currentColumn = (String)myColumnIds.get(j);
	// Property p = (Property) current.getAllProperties().get(j);
	// currentMessage.add(p);
	// }
	// l.add(currentMessage);
	// }
	// // return l;
	// }

	public void setMessage(Message m) {
		// lastSortedColumn = -1;
		// lastSortedDirection = true;
		myMessage = m;
		// myMessageRows.clear();

		setColumnCount(myColumnModel.getColumnCount());
		for (int i = 0; i < myColumnModel.getColumnCount(); i++) {
			setColumnName(i, myTable.getColumnTitle(i));
		}

		for (int i = 0; i < m.getArraySize(); i++) {
			List<Property> c = createListFromRow(myMessage.getMessage(i));
			// logger.info();
			addRow(c.toArray());
		}
	}

	private List<Property> createListFromRow(Message m) {
		List<Property> al = new ArrayList<Property>();
		// al.add(new Boolean(false));
		for (int i = 0; i < myColumnModel.getColumnCount(); i++) {
			String id = myTable.getColumnId(i);
			Property property = m.getProperty(id);
			if (property != null) {
				al.add(property);
			}
		}
		return al;
	}

	public void messageChanged() {
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "";
		}
		return myTable.getColumnTitle(column - 1);
	}

	public Message getMessageRow(int row) {
		List<List<Object>> l = getRows();
		List<Object> rowList = l.get(row);
		Object last = rowList.get(rowList.size() - 1);
		if (last instanceof Message) {
			return (Message) last;
		}
		logger.info("Can not retrieve message. Sorry");
		if (row < 0) {
			return null;
		}
		if (myMessage == null) {
			return null;
		}
		return myMessage.getMessage(row);

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return Boolean.class;
		}
		return Property.class;
	}

	public void messageLoaded(int startIndex, int endIndex) {
		fireTableRowsUpdated(startIndex, endIndex);
	}

	public void fireDataChanged() {
		fireTableDataChanged();
	}

	public ListSelectionModel getMySelectionModel() {
		return mySelectionModel;
	}

	public void setMySelectionModel(ListSelectionModel mySelectionModel) {
		this.mySelectionModel = mySelectionModel;
	}

	// public void createColumnsFromModel(Message m, MessageTable table,
	// TableColumnModel tcm, TableCellRenderer myCellRenderer) {
	//
	// while (tcm.getColumnCount() > 0) {
	// tcm.removeColumn(tcm.getColumn(0));
	// }
	//
	// int columnCount = getColumnCount();
	// super.setColumnCount(columnCount);
	//
	// for (int index = 0; index < columnCount; index++) {
	// tcm.addColumn(new SortableTableColumn(index,
	// table.getColumnSize(index)));
	// super.setColumnName(index,getColumnName(index));
	// }
	// setMessage(m);
	// }

	// public int getRowCount() {
	// return myMessage.getArraySize();
	// }
	//
	// public Object getValueAt(int column, int row) {
	// Message m = myMessage.getMessage(row);
	// if (m!=null) {
	// String propName = (String)myColumnIds.get(column);
	// return m.getProperty(propName);
	//
	// }
	// return null;
	// }

}
