package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.app.table.TableColumnModel;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.components.echoimpl.EchoPropertyComponent;

import echopointng.table.PageableSortableTable;
import echopointng.table.SortableTableHeaderRenderer;

public class MessageTable extends PageableSortableTable {
	private MessageTableModel myModel = null;

	private final TableCellRenderer myRenderer = new EchoPropertyComponent();

	private final ArrayList ids = new ArrayList();

	private final ArrayList names = new ArrayList();

	private final ArrayList editables = new ArrayList();

	private final ArrayList sizes = new ArrayList();

	public void setMessage(Message m) {
		// setSelectionMode(Table.);
		setAutoCreateColumnsFromModel(false);
		setHeaderVisible(true);
		((EchoPropertyComponent)myRenderer).setUseLabelForReadOnlyProperties(true);
//		setDefaultRenderer(Property.class, myRenderer);
		setSelectionBackground(new Color(200, 200, 255));
		myModel = new MessageTableModel(getColumnModel());
		for (int i = 0; i < ids.size(); i++) {
			myModel.addColumn((String) ids.get(i), (String) names.get(i),
					((Boolean) editables.get(i)).booleanValue());
		}
		// SortablePagedTableModel sptm = new SortablePagedTableModel(myModel);
		// sptm.setRowsPerPage(25);
		// setAutoCreateColumnsFromModel(true);
		TableColumnModel tcm = getColumnModel();
		
		setModel(myModel);
		myModel.createColumnsFromModel(this, tcm, myRenderer);
		myModel.setMessage(m);
		// debugTableModel();
		System.err.println("Sel.mod: " + getSelectionModel());
		if (getSelectionModel() != null) {
			getSelectionModel().addChangeListener(new ChangeListener() {

				public void stateChanged(ChangeEvent arg0) {
					System.err.println("Selection changed!!!");
				}
			});
		}
		setSelectionEnabled(true);
		addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				// System.err.println("AAAP!");
			}
		});

		setSelectionBackground(new Color(200, 200, 255));
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

	public TableCellRenderer getDefaultHeaderRenderer() {
		System.err.println("DefaultHEaderREnderer CREATED");
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
		System.err.println("GETTING SELECTED MESSAGE: "
				+ getSelectionModel().getMinSelectedIndex());
		return myModel.getMessageRow(getSelectionModel().getMinSelectedIndex());
	}

	public void addActionListener(ActionListener al) {
		System.err.println("Not yet implemented");
	}

	public void addColumn(String id, String title, boolean editable, int size) {
		ids.add(id);
		names.add(title);
		sizes .add(new Integer(size));
		editables.add(new Boolean(editable));
	}

	public void removeColumn(String id) {
		myModel.removeColumn(id);
		/** @todo Implement. Or restructure class. */
	}
	
	public Extent getColumnSize(int columnIndex) {
		Integer i = (Integer)sizes.get(columnIndex);
		if (i!=null) {
			return new Extent(i.intValue(),Extent.PX);
		}
		return null;
	}
}
