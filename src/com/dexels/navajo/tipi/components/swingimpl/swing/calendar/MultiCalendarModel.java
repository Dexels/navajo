package com.dexels.navajo.tipi.components.swingimpl.swing.calendar;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */
public class MultiCalendarModel
    extends CalendarTableModel {
  private ArrayList myModels = new ArrayList();
  private JTable myTable;
  private boolean columnsSized = false;
  private CalendarTableModel myDefaultModel;
  public MultiCalendarModel() {
  }

  public MultiCalendarModel(JTable t) {
    setTable(t);
  }

  public void setTable(JTable t) {
    myTable = t;
  }

  public void addCalendar(CalendarTableModel model) {
    if (myDefaultModel == null) {
      myDefaultModel = model;
    }
    myModels.add(model);
  }

  public void removeCalendar(CalendarTableModel model) {
    myModels.remove(model);
  }

  public int getRowCount() {
    if (myModels.size() > 0) {
      CalendarTableModel base = (CalendarTableModel) myModels.get(0);
      return base.getRowCount();
    }
    else {
      System.err.println("ERROR: MultiCalendarModel contains no CalendarTableModel(s)");
      return -1;
    }
  }

  public int getColumnCount() {
    if (myModels.size() > 0) {
      CalendarTableModel base = (CalendarTableModel) myModels.get(0);
      return base.getColumnCount();
    }
    else {
      System.err.println("ERROR: MultiCalendarModel contains no CalendarTableModel(s)");
      return -1;
    }
  }

  public String getColumnName(int parm1) {
    if (myModels.size() > 0) {
      CalendarTableModel base = (CalendarTableModel) myModels.get(0);
      return base.getColumnName(parm1);
    }
    else {
      System.err.println("ERROR: MultiCalendarModel contains no CalendarTableModel(s)");
      return "error";
    }
  }

  public void fireStructureChanged() {
    for (int i = 0; i < myModels.size(); i++) {
      CalendarTableModel current = (CalendarTableModel) myModels.get(i);
      current.fireStructureChanged();
    }
  }

  public int getMonth() {
    return myDefaultModel.getMonth();
  }

  public int getYear() {
    return myDefaultModel.getYear();
  }

  public Class getColumnClass(int parm1) {
    return Object.class;
  }

  public boolean isCellEditable(int row, int column) {
    return true;
  }

  public Object getValueAt(int week, int day) {
    int size = myModels.size();
    if (size > 0) {
      if (week > 0) {
        if (size > 1) {
          MultipleDayContainer mDay = new MultipleDayContainer();
          Day[] days = new Day[size];
          for (int i = 0; i < size; i++) {
            CalendarTableModel current = (CalendarTableModel) myModels.get(i);
            days[i] = (Day) current.getValueAt(week, day);
          }
          mDay.setDays(days);
          return mDay;
        }
        else {
          return myDefaultModel.getValueAt(week, day);
        }
      }
      else {
        // Column widths
        myTable.getColumnModel().getColumn(day).setPreferredWidth(CalendarConstants.DEFAULT_COLUMN_WIDTH);
        return myDefaultModel.getColumnName(day);
      }
    }
    else {
      return "error";
    }
  }

  public void setValueAt(Object value, int row, int column) {
    /**@todo Implement this javax.swing.table.TableModel method*/
    // We will probably not implement this
  }

  public void addTableModelListener(TableModelListener parm1) {
    for (int i = 0; i < myModels.size(); i++) {
      CalendarTableModel current = (CalendarTableModel) myModels.get(i);
      current.addTableModelListener(parm1);
    }
  }

  public void removeTableModelListener(TableModelListener parm1) {
    for (int i = 0; i < myModels.size(); i++) {
      CalendarTableModel current = (CalendarTableModel) myModels.get(i);
      current.removeTableModelListener(parm1);
    }
  }
}
