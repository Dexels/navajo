package com.dexels.navajo.tipi.swingclient.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.filter.PropertyFilter;

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

public class MessageTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7930139592084553531L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MessageTableModel.class);
	private final List<String> myColumnIds = new ArrayList<String>();
	private final List<String> myColumnTitles = new ArrayList<String>();
	// private final ArrayList editableList = new ArrayList();
	private final HashMap<String, Boolean> editableMap = new HashMap<String, Boolean>();
	private Message myMessage;
	private final List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
	private boolean isFiltered = false;
	private int[] filterMap = null;
	private int filteredRecordCount = -1;

	private int lastSortedColumn = -1;
	private boolean lastSortedDirection = true;
	private boolean readOnly = false;
	private int subsractColumnCount = 1;
	private boolean rowHeadersVisible = true;

	private final Map<String, String> myTypeMap = new HashMap<String, String>();

	public MessageTableModel() {
	}

	public MessageTableModel(Message m) {
		setMessage(m);
	}

	public void setShowRowHeaders(boolean b) {
		rowHeadersVisible = b;
		if (rowHeadersVisible) {
			subsractColumnCount = 1;
		} else {
			subsractColumnCount = 0;
		}
	}

	public boolean isShowingRowHeaders() {
		return rowHeadersVisible;
	}

	public boolean isShowingColumn(String id) {
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
		// initialResize = true;

		lastSortedColumn = -1;
		lastSortedDirection = true;

		// if(myMessage!=null) {
		// myMessage.removePropertyChangeListener(this);
		// }

		myMessage = m;
		filterMap = new int[myMessage.getArraySize()];

		if (myMessage != null) {
			myMessage.addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(final PropertyChangeEvent e) {
					final Property p = (Property) e.getSource();
					Message m = p.getParentMessage();
					if (m != null) {
						final int row = getRowOfMessage(m);
						final int column = getColumnOfProperty(row, p);
						try {
							if (!SwingUtilities.isEventDispatchThread()) {
								SwingUtilities.invokeAndWait(new Runnable() {

									@Override
									public void run() {
										if (row >= 0 && column >= 0) {
											fireTableCellUpdated(row, column);
										}
										// logger.info("Updating: "+row+" column: "+column+" property: "+p.getName()+" orig. row: "+p.getParentMessage().getName()+" ---- "+p.getParentMessage().getIndex());
										// logger.info("Old: "+e.getOldValue()+" new: "+e.getNewValue());
									}
								});
							} else {
								if (row >= 0 && column >= 0) {
									fireTableCellUpdated(row, column);
								}
								// logger.info("Updating: "+row+" column: "+column+" property: "+p.getName()+" orig. row: "+p.getParentMessage().getName()+" ---- "+p.getParentMessage().getIndex());
								// logger.info("Old: "+e.getOldValue()+" new: "+e.getNewValue());

							}
						} catch (InterruptedException e1) {
							logger.error("Error: ", e1);
						} catch (InvocationTargetException e1) {
							logger.error("Error: ", e1);
						}
					}

				}
			});
		}
		// re
		// messageChanged();
	}

	public void setReadOnly(boolean b) {
		readOnly = b;
	}

	public String getTypeHint(String id) {
		return myTypeMap.get(id);
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
	    myColumnIds.add(id);
		myColumnTitles.add(title);
		if (!editableMap.containsKey(id)) { // EDITABLE IS DETERMINED BY THE FIRST
			editableMap.put(id, Boolean.valueOf(editable));
		}
		int index = myColumnIds.indexOf(id);
		return index + subsractColumnCount;

		// messageChanged();
	}

	public void removeColumn(String id) {
		int index = myColumnIds.indexOf(id);
		if (index > -1) {
			removeColumn(index);
		}
	}

	public void removeColumn(int index) {
		String id = myColumnIds.get(index);
		myColumnIds.remove(index);
		myColumnTitles.remove(index);
		myTypeMap.remove(id);
	}

	public void removeAllColumns() {
		myColumnIds.clear();
		myColumnTitles.clear();
		// editableList.clear(); // REMOVED clear of editable, so we REMEMBER
		myTypeMap.clear();
	}

	public void messageChanged() {
		// super.fireTableStructureChanged();

		if (isFiltered) {
			performFilters();
		}

	}

	@Override
	public int getColumnCount() {
		return myColumnIds.size() + subsractColumnCount;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0 && rowHeadersVisible) {
			return new Integer(row);
		} else {
			if (myMessage == null) {
				return null;
			}
			if ((column - subsractColumnCount) >= myColumnIds.size()) {
				return null;
			}

			String columnName = myColumnIds.get(column - subsractColumnCount);
			if (columnName == null) {
				return null;
			}

			Message m = getMessageRow(row);
			if (m != null) {
				Property p = m.getProperty(columnName);
				return p;
			} else {
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
			firePropertyChanged(current, "value");
		}
	}

	@Override
	public int getRowCount() {
		if (myMessage == null) {
			return 0;
		}
		if (isFiltered) {
			return filteredRecordCount;
		}
		return myMessage.getArraySize();
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0 && rowHeadersVisible) {
			return "";
		} else {
			if (myColumnTitles.size() > 0) {
				String s = ((column - subsractColumnCount) < myColumnTitles
						.size()) ? myColumnTitles.get(column- subsractColumnCount) : null;
				if (s == null) {
					return super.getColumnName(column - subsractColumnCount);
				} else {
					return s;
				}
			} else {
				return super.getColumnName(column - 1);
			}
		}
	}

	public String getColumnId(int column) {
		if (column == 0 && rowHeadersVisible) {
			return "";
		} else {
			if (column - subsractColumnCount >= myColumnIds.size()) {
				return "ERROR!";
			}
			String s = myColumnIds.get(column - subsractColumnCount);
			return s;
		}
	}

	public int getColumnIndex(String id) {
		for (int i = 0; i < myColumnIds.size(); i++) {
			String mId = myColumnIds.get(i);
			if (id.equals(mId)) {
				return i + subsractColumnCount;
			}
		}
		return -1;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
	}

	public Message getMessageRow(int row) {
		// if(myMessage == null || row < 0 || row >= myMessage.getArraySize()){
		if (myMessage == null || row < 0) {
			return null;
		}

		if (isFiltered && filterMap != null && filterMap.length > row) {
			return myMessage.getMessage(filterMap[row]);
		}
		isFiltered = false;
		return myMessage.getMessage(row);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Property.class;
	}

	public boolean isColumnEditable(int columnIndex) {
		String id = myColumnIds.get(columnIndex - subsractColumnCount);
		Boolean b = editableMap.get(id);
		return b.booleanValue();
	}

	public void setColumnEditable(int columnIndex, boolean value) {
		String id = myColumnIds.get(columnIndex - subsractColumnCount);
		editableMap.put(id, value);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		int column = columnIndex - subsractColumnCount;
		if (column < 0) {
			return true;
		}
		String id = myColumnIds.get(column);
		if (readOnly) {
			return false;
		}
		if (!editableMap.containsKey(id)) {
			logger.info("Not in editable list. index too big");
			return false;
		}
		Boolean b = editableMap.get(id);
		if (b == null) {
			logger.info("Nothing in editablelist");
			return false;
		}
		if (!b.booleanValue()) {
			return false;
		}
		Message m = getMessageRow(rowIndex);
		if (m == null) {
			// logger.info("False: no such message");
			return false;
		}
		Property p = m.getProperty(getColumnId(columnIndex));
		if (p == null) {
			// logger.info("False: no such property: " +
			// getColumnId(columnIndex));
			return false;
		} else {
			// logger.info("Property found. (" + p.getName() +
			// ") Returning: "+p.isDirIn()+" row: "+rowIndex+" column: "+columnIndex);
			// Thread.dumpStack();
			return p.isDirIn();
		}
		// return b.booleanValue();
		// return true;
	}

	public void messageLoaded(int startIndex, int endIndex, int newTotal) {

		fireTableRowsUpdated(startIndex, endIndex);
		if (newTotal != getRowCount()) {
			fireTableRowsDeleted(newTotal, getRowCount());
		}
	}

	public final void fireDataChanged() {
		fireTableDataChanged();
		// filteredRecordCount = 0;
		// performFilters();
	}

	/** @todo Rewrite this inefficient piece of sh@# */
	public final int getColumnOfProperty(int row, Property p) {
		int start = 0;
		if (p == null) {
			return -1;
		}
		if (rowHeadersVisible) {
			start = 1;
		}
		for (int i = start; i < getColumnCount(); i++) {
			Property current = (Property) getValueAt(row, i);
			if (current == null) {
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

	public final void firePropertyChanged(Property p, String beanPropertyName) {

		Message parent = p.getParentMessage();
		if (parent.getArrayParentMessage() != myMessage) {
			return;
		}

		final int row = getRowOfMessage(parent);
		if (row == -1) {
			logger.info("Trouble locating message");
			return;
		}
		final int column = getColumnOfProperty(row, p);

		if (column >= 0) {
			if (SwingUtilities.isEventDispatchThread()) {
				fireTableCellUpdated(row, column);
			} else {
				try {
					SwingUtilities.invokeAndWait(new Runnable() {

						@Override
						public void run() {
							fireTableCellUpdated(row, column);

						}
					});
				} catch (InterruptedException e) {
					logger.error("Error: ",e);
				} catch (InvocationTargetException e) {
					logger.error("Error: ",e);
				}
			}
		}
	}

	public boolean hasPropertyFilters() {
		return filterList.size() > 0;
	}

	public void addPropertyFilter(String propName, Property value,
			String operator) {
		PropertyFilter pf = new PropertyFilter(propName, value, operator);
		filterList.add(pf);
	}

	public void clearPropertyFilters() {
		filterList.clear();
		isFiltered = false;
	}

	public void performFilters() {
		try {
			if (myMessage == null) {
				return;
			}
			int count = 0;
			isFiltered = true;
			for (int i = 0; i < myMessage.getArraySize(); i++) {
				Message current = myMessage.getMessage(i);
				boolean complying = true;
				for (int j = 0; j < filterList.size(); j++) {
					PropertyFilter currentFilter = filterList.get(j);
					if (currentFilter.compliesWith(current)) {
					} else {
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
		} catch (Exception e) {
			logger.error("Error: ",e);
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

	}
	
}
