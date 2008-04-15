package com.dexels.navajo.echoclient.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javax.sound.midi.*;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.RadioButton;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.StyleSheet;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.list.DefaultListSelectionModel;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.*;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.echoclient.components.PageNavigator.PageIndexChangeEvent;
import com.dexels.navajo.echoclient.components.PageNavigator.PageIndexChangeListener;

import echopointng.PushButton;
import echopointng.table.DefaultPageableSortableTableModel;
import echopointng.table.DefaultSortableTableModel;
import echopointng.table.PageableSortableTable;
import echopointng.table.SortableTable;
import echopointng.table.SortableTableColumn;
import echopointng.table.SortableTableHeaderRenderer;

public class MessageTable extends PageableSortableTable implements PageIndexChangeListener {
	private static final int DEFAULT_ROWS_PER_PAGE = 20;

	private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
	
//	private static final Extent RADIOSIZE = new Extent(10, Extent.PX);

	private MessageTableModel myModel = null;

	private final TableCellRenderer myRenderer = new EchoPropertyComponent();

	private final List<String> ids = new ArrayList<String>();

	private final List<String> names = new ArrayList<String>();

	private final List<Boolean> editables = new ArrayList<Boolean>();

	private final List<Integer> sizes = new ArrayList<Integer>();

	private final List<TableEditorListener> editorListeners = new ArrayList<TableEditorListener>();

	private int lastSelectedRow = -1;

	private int currentSelectedRow = -1;

	private Color headerForeground = null;

	private Color headerBackground = null;

	private Color headerRolloverForeground = null;

	private Color headerRolloverBackground = null;

	private Color headerPressedBackground = null;

	private Color headerPressedForeground = null;

	private int headerHeight = 15;

	private SortableTableHeaderRenderer sortableTableHeaderRenderer = new SortableTableHeaderRenderer();

	private DefaultPageableSortableTableModel sortablePageableModel;

	private PageNavigator myPageNavigator;

	private Message myMessage;
	
	private final List<ActionListener> actionEventListeners = new ArrayList<ActionListener>();

	private final Map<Integer,RadioButton> rowSelectMap = new HashMap<Integer,RadioButton>();

	public MessageTable() {
		StyleSheet sh = Styles.DEFAULT_STYLE_SHEET;
		if (sh!=null) {
			Style ss = sh.getStyle(this.getClass(), "Default");
		}
		
		Style ss=null;
		if(ss!=null) {
			ss = Styles.DEFAULT_STYLE_SHEET.getStyle(this.getClass(), "Default");
			setStyle(ss);
		}

		 super.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		 });
		 
		 
			 //
		// public void actionPerformed(ActionEvent e) {
		// lastSelectedRow = currentSelectedRow;
		// currentSelectedRow = getSelectedIndex();
		// if (lastSelectedRow>=getMessageTableModel().getRowCount()) {
		// lastSelectedRow = -1;
		// }
		// for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
		// // TODO Ewwwwww
		// // System.err.println("Last row: "+lastSelectedRow);
		// // System.err.println("Current row: "+currentSelectedRow);
		// Component lastc = getCellComponent(i, lastSelectedRow);
		// // System.err.println("Last: "+lastc+" current: "+currentc);
		// if (lastSelectedRow > -1 && lastc instanceof EchoPropertyComponent) {
		// ((EchoPropertyComponent) lastc).setZebra(i, lastSelectedRow, false);
		//                        
		// }
		// Component currentc = getCellComponent(i, currentSelectedRow);
		// if (currentSelectedRow > -1 && currentc instanceof
		// EchoPropertyComponent) {
		// ((EchoPropertyComponent) currentc).setZebra(i, currentSelectedRow,
		// true);
		// // currentc.setBackground(new Color(255,0,0));
		// }
		// //
		// ((EchoPropertyComponent)getCellComponent(i,lastSelectedRow)).setBackground(((EchoPropertyComponent)getCellComponent(i,lastSelectedRow)).getBackground());
		// }
		// }
		// });
	}

	
	public void setRowsPerPage(int rpp) {
		rowsPerPage = rpp;
		if(sortablePageableModel!=null) {
			sortablePageableModel.setRowsPerPage(rpp);
		}
	}
	
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setMessage(Message m) {
		// setSelectionMode(Table.);
		myMessage = m;
		rowSelectMap.clear();
		setAutoCreateColumnsFromModel(false);
		setHeaderVisible(true);
		setDefaultRenderer(Property.class, myRenderer);
		//setSelectionBackground(new Color(200, 200, 255));
		setColumnModel(createColumnModel(m, myRenderer));
		final DefaultListSelectionModel defaultListSelectionModel = new DefaultListSelectionModel();
		myModel = new MessageTableModel(this, getColumnModel(), m,defaultListSelectionModel);
sortablePageableModel = new DefaultPageableSortableTableModel(myModel);
		sortablePageableModel.setRowsPerPage(rowsPerPage);
		setBackground(new Color(255, 255, 255));
//		System.err.println("Total pages: " + sortablePageableModel.getTotalPages());
//		System.err.println("Current page: " + sortablePageableModel.getCurrentPage());

		TableColumnModel tcm = getColumnModel();

		setModel(sortablePageableModel);
		debugColumns(tcm);
		// if (getSelectionModel() != null) {
		// getSelectionModel().addChangeListener(new ChangeListener() {
		//
		// public void stateChanged(ChangeEvent arg0) {
		// }
		// });
		// } else {
		// System.err.println("No selection model!");
		// }
		// addActionListener(new ActionListener() {
		//
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// });
		// addPropertyChangeListener(new PropertyChangeListener() {
		//
		// public void propertyChange(PropertyChangeEvent evt) {
		// // System.err.println("AAAP!");
		// }
		// });
		getSelectionModel().clearSelection();
		setSelectionModel(defaultListSelectionModel);
		setSelectionEnabled(false);
		
		defaultListSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		defaultListSelectionModel.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				int row = defaultListSelectionModel.getMinSelectedIndex();
				if(row<0) {
					return;
				}
				System.err.println("Row: "+row);
				setSelectedRow(row);
//				RadioButton rb = (RadioButton) rowSelectMap.get(new Integer(row));
//				if(rb!=null) {
//					rb.setSelected(true);
//				}
			}});
	//	defaultListSelectionModel.setSelectedIndex(1, true);
		setSelectionBackground(new Color(200, 200, 255));
		invalidate();
	}

	private void debugColumns(TableColumnModel tcm) {
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			EchoPropertyComponent epc = new EchoPropertyComponent();
			tc.setCellRenderer(epc);
			epc.setWidth(tc.getWidth());
			// epc.setBackground(null);
		}
	}

	public void removeAllColumns() {
		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i);
			// removeColumn(id);
		}
		ids.clear();
		names.clear();
		editables.clear();
	}

	public void addTableEditorListener(TableEditorListener te) {
		editorListeners.add(te);
	}

	public void removeTableEditorListener(TableEditorListener te) {
		editorListeners.remove(te);
	}

	public void fireTableEdited(Property p, int column, int row) {
		// System.err.println("Edited: "+column+" / "+row+" value:
		// "+p.getValue()+" listeners: "+editorListeners.size());

		for (int i = 0; i < editorListeners.size(); i++) {
			TableEditorListener element = editorListeners.get(i);
			element.propertyChanged(p, "onValueChanged", column, row);
		}
	}

	public TableCellRenderer getDefaultHeaderRenderer() {
		// System.err.println("DefaultHEaderREnderer CREATED");
		return sortableTableHeaderRenderer;
	}

	public MessageTableModel getMessageTableModel() {
		return (MessageTableModel) ((DefaultSortableTableModel) getModel()).getUnderlyingTableModel();
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
				} else {
					System.err.println("Null value");
				}
			}
		}
	}

	public Message getSelectedMessage() {
		int index = getSelectionModel().getMinSelectedIndex();
		System.err.println("GETTING SELECTED INDEX: "+index);
		if (index < 0) {
			return null;
		}
		System.err.println("INDEX: " + index);
		int sortedPagedIndex= -1;
		int pagedSortedIndex= -1;
		try {
			int sortedIndex = sortablePageableModel.toUnsortedModelRowIndex(index);
//			System.err.println("sorted: " + sortedIndex);
			int pagedIndex = sortablePageableModel.toUnpagedModelRowIndex(index);
//			System.err.println("pagedIndex: " + pagedIndex);
			sortedPagedIndex = sortablePageableModel.toUnpagedModelRowIndex(sortedIndex);
//			System.err.println("sortedPagedIndex: " + sortedPagedIndex);
			pagedSortedIndex = sortablePageableModel.toUnsortedModelRowIndex(pagedIndex);
//			System.err.println("pagedSortedIndex: " + pagedSortedIndex);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Actually removed all the above stuff...
		Message selectedMessage = myModel.getMessageRow(index);
		if(selectedMessage==null) {
			System.err.println("WARNING, no MESSAGe sekektet");
		}
		return selectedMessage;
	}

	public int getSelectedIndex() {

		int selIndex = getSelectionModel().getMinSelectedIndex();
		System.err.println("GETING IDEX: "+selIndex);
		return selIndex;
	}

	public void setSelectedIndex(int s) {
		// System.err.println("GETTING SELECTED MESSAGE: "
		// + getSelectionModel().getMinSelectedIndex());
		int old = getSelectedIndex();
//		getSelectionModel().clearSelection();
		getSelectionModel().setSelectedIndex(s, true);
//		System.err.println("Setting selected index to: "+s);
//		System.err.println("Set selected index to: "+getSelectionModel().getMinSelectedIndex());
		firePropertyChange(MessageTable.SELECTION_CHANGED_PROPERTY, old, s);
		if(old>=0) {
			((AbstractTableModel)getModel()).fireTableRowsUpdated(old, old);
		}
		
		((AbstractTableModel)getModel()).fireTableRowsUpdated(s, s);
		fireActionEvents(new ActionEvent(this,""+s));
	}

	private void fireActionEvents(ActionEvent a) {
		for (int i = 0; i < actionEventListeners.size(); i++) {
			ActionListener al = actionEventListeners.get(i);
			al.actionPerformed(a);
		}
	}


	public void addActionListener(ActionListener al) {
		System.err.println("Ignoring addActionListener. Use addselectionlistener");
	}

	//
	public void addSelectionListener(ActionListener al) {
	//	super.addActionListener(al);
		actionEventListeners.add(al);
		
	}

	public void removeSelectionListener(ActionListener al) {
//		super.removeActionListener(al);
		actionEventListeners.remove(al);
	}

	public void addColumn(String id, String title, boolean editable, int size) {
		ids.add(id);
		names.add(title);
		sizes.add(new Integer(size));
		editables.add(new Boolean(editable));
	}

	public boolean isColumnEditable(int index) {
		//Boolean b = (Boolean) editables.get(index);
		 if(index==0) {
		 return true;
		 }
		 Boolean b = editables.get(index-1);
		if (b != null) {
			return b.booleanValue();
		}
		return false;
	}

	public String getColumnTitle(int i) {
		if(i>=names.size()) {
			return "";
		}
		return names.get(i);
		// if(i==0){
		// return " ";
		// }
		// return (String) names.get(i-1);
	}

	// public void removeColumn(String id) {
	// myModel.removeColumn(id);
	// /** @todo Implement. Or restructure class. */
	// }

	public Extent getColumnSize(int columnIndex) {
		if(columnIndex==0) {
			return new Extent(8, Extent.PX);
		}
		Integer i = sizes.get(columnIndex-1);
		if (i != null) {
			// System.err.println("MESSAGETABLE: RETURNING SIZE:
			// "+i.intValue());
			return new Extent(i.intValue(), Extent.PX);
		}
		return null;
		// if(columnIndex==0){
		// return RADIOSIZE;
		// }
		// Integer i = (Integer) sizes.get(columnIndex-1);
		// if (i != null) {
		// return new Extent(i.intValue(), Extent.PX);
		// }
	}

	public String getColumnId(int columnIndex) {
//		return (String) ids.get(columnIndex);
		 if(columnIndex==0){
		 return " ";
		 }
		 return ids.get(columnIndex-1);
	}

	public TableColumnModel createColumnModel(Message m, TableCellRenderer myCellRenderer) {
		TableColumnModel tcm = new DefaultTableColumnModel();
		int columnCount = ids.size();
		// super.setColumnCount(columnCount);
		SortableTableColumn tc = new SortableTableColumn(0, getColumnSize(0), myRenderer, new MessageTableHeaderRenderer());
		tcm.addColumn(tc);
		
		
		for (int index = 0; index < columnCount; index++) {
			tc = new SortableTableColumn(index+1, getColumnSize(index+1), myRenderer, new MessageTableHeaderRenderer());
			// tc.setHeaderRenderer(new MessageTableHeaderRenderer());
			tc.setComparator(new Comparator<Property>() {
				public int compare(Property o1, Property o2) {
					if (o1 == null || o2 == null) {
						return 0;
					}
					return o1.compareTo(o2);
				}
			});
			tcm.addColumn(tc);
			// tcm. setColumnName(index,names.get(index));
		}
		return tcm;
	}

	// public Color getHeaderForeground() {
	// return headerForeground;
	// }
	//
	// public Color getHeaderBackground() {
	// return headerBackground;
	// }
	//    
	// public Color getHeaderRolloverForeground() {
	// return headerRolloverForeground;
	// }
	//
	// public Color getHeaderRolloverBackground() {
	// return headerRolloverBackground;
	// }
	//
	// public Color getHeaderPressedBackground() {
	// return headerPressedBackground;
	// }
	// public Color getHeaderPressedForeground() {
	// return headerPressedForeground;
	// }
	//
	// public final void setHeaderForeground(Color headerForeground) {
	// this.headerForeground = headerForeground;
	// }
	//
	// public final void setHeaderPressedBackground(Color
	// headerPressedBackground) {
	// this.headerPressedBackground = headerPressedBackground;
	// }
	//
	// public final void setHeaderPressedForeground(Color
	// headerPressedForeground) {
	// this.headerPressedForeground = headerPressedForeground;
	// }
	//
	// public final void setHeaderRolloverBackground(Color
	// headerRolloverBackground) {
	// this.headerRolloverBackground = headerRolloverBackground;
	// }
	//
	// public final void setHeaderRolloverForeground(Color
	// headerRolloverForeground) {
	// this.headerRolloverForeground = headerRolloverForeground;
	// }
	public final void setHeaderHeight(int headerHeight) {
		this.headerHeight = headerHeight;
	}

	public int getHeaderHeight() {
		return this.headerHeight;
	}

	public void pageIndexChanged(PageIndexChangeEvent e) {
		if(sortablePageableModel!=null) {
			sortablePageableModel.setCurrentPage(e.getNewPageIndex());
		}
	}

	public int getTotalPages() {
		if (sortablePageableModel == null) {
			return 0;
		}
		return sortablePageableModel.getTotalPages();
	}


	public void setPageNavigator(PageNavigator pageNavigator) {
		myPageNavigator = pageNavigator;
	}


	public Message getMessage() {
		return myMessage;
	}


	public void setSelectedMessage(Message selectedMessage) {
		// not implemented for now.
	}


	public void mapRowSelection(RadioButton rb, int row) {
		rowSelectMap.put(new Integer(row),rb);
	}
	
	public void setSelectedRow(int row) {
		for (Iterator<Integer> iter = rowSelectMap.keySet().iterator(); iter.hasNext();) {
			Integer element = iter.next();
			RadioButton r = rowSelectMap.get(element);
			r.setSelected(element.intValue()==row);
		}
	}
}
