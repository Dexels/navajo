package com.dexels.navajo.tipi.components.calendar;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class CalendarTableCellEditor
    implements TableCellEditor {
  public CalendarTableCellEditor() {
  }

  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    /**@todo Implement this javax.swing.table.TableCellEditor method*/
    throw new java.lang.UnsupportedOperationException("Method getTableCellEditorComponent() not yet implemented.");
  }

  public Object getCellEditorValue() {
    /**@todo Implement this javax.swing.CellEditor method*/
    throw new java.lang.UnsupportedOperationException("Method getCellEditorValue() not yet implemented.");
  }

  public boolean isCellEditable(EventObject anEvent) {
    return false;
  }

  public boolean shouldSelectCell(EventObject anEvent) {
    return true;
  }

  public boolean stopCellEditing() {
    /**@todo Implement this javax.swing.CellEditor method*/
    throw new java.lang.UnsupportedOperationException("Method stopCellEditing() not yet implemented.");
  }

  public void cancelCellEditing() {
    /**@todo Implement this javax.swing.CellEditor method*/
    throw new java.lang.UnsupportedOperationException("Method cancelCellEditing() not yet implemented.");
  }

  public void addCellEditorListener(CellEditorListener l) {
    /**@todo Implement this javax.swing.CellEditor method*/
    throw new java.lang.UnsupportedOperationException("Method addCellEditorListener() not yet implemented.");
  }

  public void removeCellEditorListener(CellEditorListener l) {
    /**@todo Implement this javax.swing.CellEditor method*/
    throw new java.lang.UnsupportedOperationException("Method removeCellEditorListener() not yet implemented.");
  }

}
