/**
 * A sorter for TableModels. The sorter has a model (conforming to TableModel)
 * and itself implements TableModel. TableSorter does not store or copy
 * the data in the TableModel, instead it maintains an array of
 * integers which it keeps the same size as the number of rows in its
 * model. When the model changes it notifies the sorter that something
 * has changed eg. "rowsAdded" so that its internal array of integers
 * can be reallocated. As requests are made of the sorter (like
 * getValueAt(row, col) it redirects them to its model via the mapping
 * array. That way the TableSorter appears to hold another copy of the table
 * with the rows in a different order. The sorting algorthm used is stable
 * which means that it does not move around rows when its comparison
 * function returns 0 to denote that they are equivalent.
 *
 * @version 1.5 12/17/97
 * @author Philip Milne
 */

package com.dexels.navajo.tipi.swingclient.components.sort;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.dexels.navajo.tipi.swingclient.components.MessageTable;

//import com.dexels.navajo.document.*;

public class TableSorter extends TableMap {

	private static final long serialVersionUID = -3022350836931378122L;

	int indexes[];

	Vector<Integer> sortingColumns = new Vector<Integer>();

	boolean ascending = true;
	int compares;
	int sortedColumn = -1;
	boolean sortedAscending = true;

	// Used for async sorting
	// private int currentSortingColumn = -1;
	// private boolean currentAscending = false;

	public TableSorter() {
		indexes = new int[0]; // for consistency

	}

	public TableSorter(TableModel model) {
		setModel(model);

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		checkModel();
		if (model == null || row < 0) {
			return false;
		}

		if (indexes != null && (row < indexes.length)) {
			return model.isCellEditable(indexes[row], column);
		}

		return false;
	}

	@Override
	public void setModel(TableModel model) {
		super.setModel(model);
		reallocateIndexes();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compareRowsByColumn(int row1, int row2, int column) {
		// Class<?> type = model.getColumnClass(column);
		TableModel data = model;
		// Check for nulls.
		Object o1 = data.getValueAt(row1, column);
		Object o2 = data.getValueAt(row2, column);
		// If both values are null, return 0.

		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null) { // Define null less than everything.
			return -1;
		} else if (o2 == null) {
			return 1;
		}

		// ************** HackedByFrank
		if (Comparable.class.isInstance(o1)) {
			// System.err.println("comp: '" + o1 + "' and '" + o2 +
			// "' compare as: "+( (Comparable) o1).compareTo(o2));
			return ((Comparable) o1).compareTo(o2);
		} else {
			System.err.println("Oh, dear, comparing NON-COMPARABLE STUFF!");
			return 0;
		}

	}

	public int compare(int row1, int row2) {
		compares++;
		for (int level = 0; level < sortingColumns.size(); level++) {
			Integer column = sortingColumns.elementAt(level);
			int result = compareRowsByColumn(row1, row2, column.intValue());
			if (result != 0) {
				return ascending ? result : -result;
			}
		}
		return 0;

	}

	public int getRowIndex(int i) {
		if (i == -1) {
			return -1;
		}
		if (i < indexes.length) {
			return indexes[i];
		}
		return -1;
	}

	public void reallocateIndexes() {
		int rowCount = model.getRowCount();
		// Set up a new array of indexes with the right number of elements
		// for the new data model.
		indexes = new int[rowCount];
		// Initialise with the identity mapping.
		for (int row = 0; row < rowCount; row++) {
			indexes[row] = row;
		}

	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// System.out.println("Sorter: tableChanged");
		reallocateIndexes();
		sort(this);
		super.tableChanged(e);

	}

	public void checkModel() {
		if (indexes.length != model.getRowCount()) {
			// System.err.println("Sorter not informed of a change in model.");
		}

	}

	public void sort(Object sender) {
		checkModel();
		compares = 0;
		// n2sort();
		// qsort(0, indexes.length-1);
		shuttlesort(indexes.clone(), indexes, 0, indexes.length);
		// System.out.println("Compares: "+compares);

	}

	public void n2sort() {
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = i + 1; j < getRowCount(); j++) {
				if (compare(indexes[i], indexes[j]) == -1) {
					swap(i, j);
				}
			}
		}

	}

	// This is a home-grown implementation which we have not had time

	// to research - it may perform poorly in some circumstances. It

	// requires twice the space of an in-place algorithm and makes

	// NlogN assigments shuttling the values between the two

	// arrays. The number of compares appears to vary between N-1 and

	// NlogN depending on the initial order but the main reason for

	// using it here is that, unlike qsort, it is stable.

	public void shuttlesort(int from[], int to[], int low, int high) {
		if (high - low < 2) {
			return;
		}
		int middle = (low + high) / 2;
		shuttlesort(to, from, low, middle);
		shuttlesort(to, from, middle, high);
		int p = low;
		int q = middle;
		/*
		 * This is an optional short-cut; at each recursive call, check to see
		 * if the elements in this subset are already ordered. If so, no further
		 * comparisons are needed; the sub-array can just be copied. The array
		 * must be copied rather than assigned otherwise sister calls in the
		 * recursion might get out of sinc. When the number of elements is three
		 * they are partitioned so that the first set, [low, mid), has one
		 * element and and the second, [mid, high), has two. We skip the
		 * optimisation when the number of elements is three or less as the
		 * first compare in the normal merge will produce the same sequence of
		 * steps. This optimisation seems to be worthwhile for partially ordered
		 * lists but some analysis is needed to find out how the performance
		 * drops to Nlog(N) as the initial order diminishes - it may drop very
		 * quickly.
		 */
		if (high - low >= 4 && compare(from[middle - 1], from[middle]) <= 0) {
			for (int i = low; i < high; i++) {
				to[i] = from[i];
			}
			return;
		}
		// A normal merge.
		for (int i = low; i < high; i++) {
			if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
				to[i] = from[p++];
			} else {
				to[i] = from[q++];
			}
		}

	}

	public void swap(int i, int j) {
		int tmp = indexes[i];
		indexes[i] = indexes[j];
		indexes[j] = tmp;

	}

	// The mapping only affects the contents of the data rows.

	// Pass all requests to these rows through the mapping array: "indexes".

	@Override
	public Object getValueAt(int aRow, int aColumn) {
		checkModel();
		if (indexes != null && (aRow < indexes.length)) {
			return model.getValueAt(indexes[aRow], aColumn);
		} else {
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int aRow, int aColumn) {
		checkModel();
		if (indexes != null && (aRow < indexes.length)) {
			model.setValueAt(aValue, indexes[aRow], aColumn);
		}

	}

	public void doSort(JTable table, int viewColumn, boolean ascending) {
		TableSorter sorter = this;
		JTable tableView = table;
		TableColumnModel columnModel = tableView.getColumnModel();

		int column = tableView.convertColumnIndexToModel(viewColumn);
		if (column != -1) {
			sorter.sortByColumn(column, ascending);
			((MessageTable) tableView).setSortingState(column, ascending);
			sortedColumn = column;
		}
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			// CustomTableHeaderRenderer cth = (CustomTableHeaderRenderer)
			// columnModel.getColumn(i).getHeaderRenderer();
			CustomTableHeaderRenderer cth = (CustomTableHeaderRenderer) table
					.getTableHeader().getDefaultRenderer();
			if (cth != null) {
				cth.setSortingState(column, CustomTableHeaderRenderer.NONE);
				cth.repaint();
			}
		}
		// CustomTableHeaderRenderer cth = (CustomTableHeaderRenderer)
		// columnModel.
		// getColumn(viewColumn).getHeaderRenderer();
		CustomTableHeaderRenderer cth = (CustomTableHeaderRenderer) table
				.getTableHeader().getDefaultRenderer();

		cth.setSortingState(column,
				ascending ? CustomTableHeaderRenderer.ASCENDING
						: CustomTableHeaderRenderer.DESCENDING);

		// cth.setSelected(false);
		final MessageTable m = (MessageTable) tableView;
		m.repaintHeader();
	}

	// public void sortByColumn(int column) {
	// sortByColumn(column, true);
	// }
	//
	public void sortByColumn(int column, boolean ascending) {
		this.ascending = ascending;
		sortingColumns.removeAllElements();
		sortingColumns.addElement(new Integer(column));
		sort(this);
		super.tableChanged(new TableModelEvent(this));

	}

	//
	// public void sortAsyncByColumn(int column, boolean ascending) {
	// currentSortingColumn = column;
	// currentAscending = ascending;
	// Runnable r = new Runnable() {
	// public void run() {
	// sortByColumn(currentSortingColumn, currentAscending);
	// }
	// };
	// Thread t = new Thread(r);
	// t.start();
	// System.err.println("Sort returned");
	// }

	// There is no-where else to put this.

	// Add a mouse listener to the Table to trigger a table sort

	// when a column heading is clicked in the JTable.

	public MouseAdapter addMouseListenerToHeaderInTable(MessageTable table) {
		// final TableSorter sorter = this;
		final MessageTable tableView = table;
		// tableView.setColumnSelectionAllowed(false);
		MouseAdapter listMouseListener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				TableColumnModel columnModel = tableView.getColumnModel();
				int viewColumn = columnModel.getColumnIndexAtX(e.getX());
				Rectangle headerRect = tableView.getTableHeader()
						.getHeaderRect(viewColumn);
				if (e.getClickCount() > 1
						&& e.getButton() == MouseEvent.BUTTON1) {
					if (headerRect != null) {
						if (e.getX() > (headerRect.x + headerRect.width - 4)) {
							tableView.determineMinumumColumnWidth(viewColumn);
						}
					}
					e.consume();
					return;
				} else if (!e.isPopupTrigger()
						&& e.getButton() == MouseEvent.BUTTON1
						&& tableView.isSortingAllowed()
						&& e.getX() > (headerRect.x + 4)
						&& e.getX() < (headerRect.x + headerRect.width - 4)) {

					boolean ascending = sortedAscending;
					if (sortedColumn == viewColumn) {
						sortedAscending = !sortedAscending;
						ascending = sortedAscending;
					} else {
						ascending = true;
					}
					doSort(tableView, viewColumn, ascending);
				}
			}

		};
		JTableHeader th = tableView.getTableHeader();
		th.addMouseListener(listMouseListener);
		return listMouseListener;

	}

}
