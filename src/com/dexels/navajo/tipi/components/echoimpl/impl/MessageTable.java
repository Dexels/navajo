package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.table.DefaultTableColumnModel;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.app.table.TableColumn;
import nextapp.echo2.app.table.TableColumnModel;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.components.echoimpl.EchoPropertyComponent;

import echopointng.PushButton;
import echopointng.table.PageableSortableTable;
import echopointng.table.SortableTable;
import echopointng.table.SortableTableColumn;
import echopointng.table.SortableTableHeaderRenderer;

public class MessageTable extends SortableTable {
	private MessageTableModel myModel = null;

	private final TableCellRenderer myRenderer = new EchoPropertyComponent();

	private final ArrayList ids = new ArrayList();

	private final ArrayList names = new ArrayList();

	private final ArrayList editables = new ArrayList();

	private final ArrayList sizes = new ArrayList();

	private int lastSelectedRow = -1;
	
	private int currentSelectedRow = -1;

	public MessageTable() {
		super.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				lastSelectedRow = currentSelectedRow;
				currentSelectedRow = getSelectedIndex();
				for (int i = 0; i < getColumnModel().getColumnCount(); i++) {
					// TODO Ewwwwww
//					System.err.println("Last row: "+lastSelectedRow);
//					System.err.println("Current row: "+currentSelectedRow);
					Component lastc = getCellComponent(i,lastSelectedRow);
					Component currentc = getCellComponent(i,currentSelectedRow);
//					System.err.println("Last: "+lastc+" current: "+currentc);
					if (lastSelectedRow>-1 && lastc instanceof EchoPropertyComponent) {
						((EchoPropertyComponent)lastc).setZebra(i,lastSelectedRow,false);
//						lastc.setBackground(new Color(0,0,255));
					}
					if (currentSelectedRow>-1 && currentc instanceof EchoPropertyComponent) {
						((EchoPropertyComponent)currentc).setZebra(i,currentSelectedRow,true);
//						currentc.setBackground(new Color(255,0,0));
					}
//					((EchoPropertyComponent)getCellComponent(i,lastSelectedRow)).setBackground(((EchoPropertyComponent)getCellComponent(i,lastSelectedRow)).getBackground());
				}
			}});
	}
	
	public void setMessage(Message m) {
		// setSelectionMode(Table.);
		setAutoCreateColumnsFromModel(false);
		setHeaderVisible(true);
		setDefaultRenderer(Property.class, myRenderer);
		setSelectionBackground(new Color(200, 200, 255));
		setColumnModel(createColumnModel(m,myRenderer));
		myModel = new MessageTableModel(this, getColumnModel(),m);
		setBackground(new Color(255,255,255));

		TableColumnModel tcm = getColumnModel();
		
		setModel(myModel);
		debugColumns(tcm);
		if (getSelectionModel() != null) {
			getSelectionModel().addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent arg0) {
					System.err.println("Selection changed!!!");
				}
			});
		} else {
			System.err.println("No selection model!");
		}
		addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				System.err.println("Selection changed!!!");
				
			}});
		setSelectionEnabled(true);
		addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				// System.err.println("AAAP!");
			}
		});
		getSelectionModel().clearSelection();
		setSelectionBackground(new Color(200, 200, 255));
		invalidate();
	}

	private void debugColumns(TableColumnModel tcm) {
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			TableColumn tc = tcm.getColumn(i);
			EchoPropertyComponent epc =new EchoPropertyComponent();
			tc.setCellRenderer(epc);
			epc.setWidth(tc.getWidth());
//			epc.setBackground(null);
		}
	}

	public void removeAllColumns() {
		for (int i = 0; i < ids.size(); i++) {
			String id = (String) ids.get(i);
//			removeColumn(id);
		}
		ids.clear();
		names.clear();
		editables.clear();
	}

	public TableCellRenderer getDefaultHeaderRenderer() {
//		System.err.println("DefaultHEaderREnderer CREATED");
		return new SortableTableHeaderRenderer();
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
				} else {
					System.err.println("Null value");
				}
			}
		}
	}

	public Message getSelectedMessage() {
//		System.err.println("GETTING SELECTED MESSAGE: "
//				+ getSelectionModel().getMinSelectedIndex());
		return myModel.getMessageRow(getSelectionModel().getMinSelectedIndex());
	}

	public int getSelectedIndex() {
//		System.err.println("GETTING SELECTED MESSAGE: "
//				+ getSelectionModel().getMinSelectedIndex());
		return getSelectionModel().getMinSelectedIndex();
	}

	
	public void addActionListener(ActionListener al) {
		System.err.println("Ignoring addActionListener. Use addselectionlistener");
	}
//
	public void addSelectionListener(ActionListener al) {
		super.addActionListener(al);
	}

	public void removeSelectionListener(ActionListener al) {
		super.removeActionListener(al);
	}

	
	public void addColumn(String id, String title, boolean editable, int size) {
		ids.add(id);
		names.add(title);
		sizes .add(new Integer(size));
		editables.add(new Boolean(editable));
	}
	
	public String getColumnTitle(int i) {
		return (String)names.get(i);
	}

//	public void removeColumn(String id) {
//		myModel.removeColumn(id);
//		/** @todo Implement. Or restructure class. */
//	}
	
	public Extent getColumnSize(int columnIndex) {
		Integer i = (Integer)sizes.get(columnIndex);
		if (i!=null) {
//			System.err.println("MESSAGETABLE: RETURNING SIZE: "+i.intValue());
			return new Extent(i.intValue(),Extent.PX);
		}
		return null;
	}
	
	public String getColumnId(int columnIndex) {
		return (String)ids.get(columnIndex);
	}
		
	
	public TableColumnModel createColumnModel(Message m, TableCellRenderer myCellRenderer) {
		TableColumnModel tcm = new DefaultTableColumnModel();
		int columnCount = ids.size();
//		super.setColumnCount(columnCount);

		for (int index = 0; index < columnCount; index++) {
			SortableTableColumn tc = new SortableTableColumn(index, getColumnSize(index),myRenderer,new MessageTableHeaderRenderer());
//			tc.setHeaderRenderer(new MessageTableHeaderRenderer());
			tc.setComparator(new Comparator(){
				public int compare(Object o1, Object o2) {
					if (o1==null || o2==null) {
						return 0;
					}
					return ((Property)o1).compareTo(o2);
				}
			});
			tcm.addColumn(tc);
//			tcm. setColumnName(index,names.get(index));
		}
		return tcm;
	}
	
}
