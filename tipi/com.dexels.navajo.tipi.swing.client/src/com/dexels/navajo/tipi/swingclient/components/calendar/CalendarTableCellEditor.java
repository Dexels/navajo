package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

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
 * @author not attributable
 * @version 1.0
 */

public class CalendarTableCellEditor implements TableCellEditor {
	public CalendarTableCellEditor() {
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		/** @todo Implement this javax.swing.table.TableCellEditor method */
		throw new java.lang.UnsupportedOperationException(
				"Method getTableCellEditorComponent() not yet implemented.");
	}

	@Override
	public Object getCellEditorValue() {
		/** @todo Implement this javax.swing.CellEditor method */
		throw new java.lang.UnsupportedOperationException(
				"Method getCellEditorValue() not yet implemented.");
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return false;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		/** @todo Implement this javax.swing.CellEditor method */
		throw new java.lang.UnsupportedOperationException(
				"Method stopCellEditing() not yet implemented.");
	}

	@Override
	public void cancelCellEditing() {
		/** @todo Implement this javax.swing.CellEditor method */
		throw new java.lang.UnsupportedOperationException(
				"Method cancelCellEditing() not yet implemented.");
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		/** @todo Implement this javax.swing.CellEditor method */
		throw new java.lang.UnsupportedOperationException(
				"Method addCellEditorListener() not yet implemented.");
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		/** @todo Implement this javax.swing.CellEditor method */
		throw new java.lang.UnsupportedOperationException(
				"Method removeCellEditorListener() not yet implemented.");
	}

}