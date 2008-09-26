package com.dexels.navajo.tipi.swingclient.components.calendar;

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
  private ArrayList<CalendarTableModel> myModels = new ArrayList<CalendarTableModel>();
  private JTable myTable;
//  private boolean columnsSized = false;
  private CalendarTableModel myDefaultModel;
  private CalendarConstants myConstants = new CalendarConstants();

  public MultiCalendarModel() {
		 System.err.println("Multi created");
  }

  @Override
public void setCalendarConstants(CalendarConstants cc) {
    myConstants = cc;
    for (int i = 0; i < myModels.size(); i++) {
      CalendarTableModel current = myModels.get(i);
      current.setCalendarConstants(myConstants);
    }
  }

  public MultiCalendarModel(JTable t) {
	  
	 System.err.println("Multi created");
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

  @Override
public int getRowCount() {
    if (myModels.size() > 0) {
      CalendarTableModel base = myModels.get(0);
      return base.getRowCount();
    }
    else {
//      System.err.println("ERROR: MultiCalendarModel contains no CalendarTableModel(s)");
      return -1;
    }
  }

  @Override
public int getColumnCount() {
    if (myModels.size() > 0) {
      CalendarTableModel base = myModels.get(0);
      return base.getColumnCount();
    }
    else {
//      System.err.println("ERROR: MultiCalendarModel contains no CalendarTableModel(s)");
      return -1;
    }

  }

  @Override
public String getColumnName(int parm1) {
    if (myModels.size() > 0) {
      CalendarTableModel base = myModels.get(0);
      return base.getColumnName(parm1);
    }
    else {
//      System.err.println("ERROR: MultiCalendarModel contains no CalendarTableModel(s)");
      return "error";
    }

  }

  @Override
public void fireStructureChanged() {
    for (int i = 0; i < myModels.size(); i++) {
      CalendarTableModel current = myModels.get(i);
      current.fireStructureChanged();
    }
  }

  @Override
public int getMonth() {
    return myDefaultModel.getMonth();
  }

  @Override
public int getYear() {
    return myDefaultModel.getYear();
  }

  @Override
public Class<?> getColumnClass(int parm1) {
    return Object.class;
  }

  @Override
public boolean isCellEditable(int row, int column) {
    return true;
  }

  @Override
public Object getValueAt(int week, int day) {
    int size = myModels.size();
    if (size > 0) {
      if (week > 0) {
        if (size > 1) {
          MultipleDayContainer mDay = new MultipleDayContainer();
          Day[] days = new Day[size];
          for (int i = 0; i < size; i++) {
            CalendarTableModel current = myModels.get(i);
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
        myTable.getColumnModel().getColumn(day).setPreferredWidth(myConstants.getColumnWidth());
        return myDefaultModel.getColumnName(day);
      }
    }
    else {
      return "error";
    }
  }

  @Override
public void setValueAt(Object value, int row, int column) {
    /**@todo Implement this javax.swing.table.TableModel method*/
    // We will probably not implement this
  }

  @Override
public void addTableModelListener(TableModelListener parm1) {
    for (int i = 0; i < myModels.size(); i++) {
      CalendarTableModel current = myModels.get(i);
      current.addTableModelListener(parm1);
    }
  }

  @Override
public void removeTableModelListener(TableModelListener parm1) {
    for (int i = 0; i < myModels.size(); i++) {
      CalendarTableModel current = myModels.get(i);
      current.removeTableModelListener(parm1);
    }

  }

}