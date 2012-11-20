package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;



public class GenericPropertyCellEditor implements TableCellEditor,
		ListSelectionListener {
	private final GenericPropertyComponent myComponent = new GenericPropertyComponent();
	private final MessageTable myTable;
	private final List<CellEditorListener> myListeners = new ArrayList<CellEditorListener>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(GenericPropertyCellEditor.class);
	
	private Property copy;

	private int lastRow = -1, lastColumn = -1;

	public GenericPropertyCellEditor(MessageTable t) {
		myTable = t;
		myComponent.setLabelVisible(false);
		myComponent.revalidate();
		myComponent.addCustomFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				stopCellEditing();
			}
		});

	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (Property.class.isInstance(value)) {
			Property p = (Property) value;
			if (p.getType().equals(Property.SELECTION_PROPERTY)) {
				myComponent.setSelectionType("dropdown");
			}
			myComponent.setProperty((Property) value);
			copy = (Property) ((Property) value).clone();
			lastRow = row;
			lastColumn = column;
			// myTable.setEditingColumn(column);
			// myTable.setEditingRow(row);
			// myComponent.gainFocus();
			// myComponent.requestFocus();
			myComponent.doLayout();
			return myComponent;
		}
		throw new IllegalArgumentException("Er moet een property in baviaan!");
	}

	public Object getCellEditorValue() {
		return myComponent.getProperty();

	}

	public boolean isCellEditable(EventObject anEvent) {
		if (myTable != null) {
			boolean b = myTable.getMessageModel().isCellEditable(
					myTable.getSelectedRow(), myTable.getSelectedColumn());
			logger.info("Returning: " + b);
			return b;
		}
		logger.info("Returning false");
		return false;

	}

	public Property getInitialProperty() {
		return copy;
	}

	public Property getProperty() {
		return myComponent.getProperty();
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	public boolean stopCellEditing() {
		logger.info("Stopped editing initial: "
				+ getInitialProperty().getValue());
		myComponent.updateProperty();
		logger.info("Stopped editing now: " + getProperty().getValue());

		if (lastRow > -1) {
			for (int i = 0; i < myListeners.size(); i++) {
				CellEditorListener ce = myListeners.get(i);
				ce.editingStopped(new MessageTableChangeEvent(myTable, lastRow,
						lastColumn));
			}
		}
		myTable.editingStopped(null);
		myTable.removeEditor();

		return true;
	}

	public void cancelCellEditing() {
		// myTable.editingCanceled(null);
	}

	public void addCellEditorListener(CellEditorListener ce) {
		myListeners.add(ce);
	}

	public void removeCellEditorListener(CellEditorListener ce) {
		myListeners.remove(ce);
	}

	public void valueChanged(ListSelectionEvent e) {
	}

}
