package com.dexels.navajo.tipi.components.swingimpl.swing.calendar;

import java.text.*;
import java.util.*;

import javax.swing.event.*;
import javax.swing.table.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */
public class CalendarTableModel
    implements TableModel {
  private Calendar myCalendar;
  private List<TableModelListener> myListeners = new ArrayList<TableModelListener>();
  private int myMonth;
  private int myYear;
  private int firstDayOfWeek = Calendar.MONDAY;
  private Map<String,Day> dayMap = new HashMap<String,Day>();
 
  public CalendarTableModel() {
    myCalendar = Calendar.getInstance();
    myCalendar.set(Calendar.DATE, myCalendar.getActualMinimum(Calendar.DATE)); // Set to the first of the current month
    myCalendar.setFirstDayOfWeek(firstDayOfWeek);
    myMonth = myCalendar.get(Calendar.MONTH);
    myYear = myCalendar.get(Calendar.YEAR);
    fillMap(myMonth, myYear);
  }

  public int getRowCount() {
    myCalendar = Calendar.getInstance();
    myCalendar.set(Calendar.MONTH, myMonth);
    myCalendar.set(Calendar.YEAR, myYear);
    myCalendar.setFirstDayOfWeek(firstDayOfWeek);
    myCalendar.setMinimalDaysInFirstWeek(1);
    int weeks = myCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    return weeks + 1; // The one extra is for the day-headers table-row
  }


  public void setMessage(Message msg) {
  
  }

  public void fireStructureChanged() {
    TableModelEvent e = new TableModelEvent(this, TableModelEvent.UPDATE);
    fireTableEvent(e);
  }

  public void fireCellChanged(int row, int column) {
    TableModelEvent e = new TableModelEvent(this, row, row, column);
    fireTableEvent(e);
  }

  public void fireTableEvent(TableModelEvent e) {
    for (int i = 0; i < myListeners.size(); i++) {
      TableModelListener current = myListeners.get(i);
      current.tableChanged(e);
    }
  }

  public int getColumnCount() {
    return 8;
  }

  public Day getDay(String dayOfYear) {
    //System.err.println("Getting: " + dayOfYear);
    return dayMap.get(dayOfYear);
  }

  public Object getValueAt(int week, int day) {
    Day d = new Day();
    // Weekdays range from 1-7 instead of 0-6
    if (week > 0) {
      myCalendar = Calendar.getInstance();
      myCalendar.setFirstDayOfWeek(firstDayOfWeek);
      myCalendar.setMinimalDaysInFirstWeek(1);
      myCalendar.set(Calendar.MONTH, myMonth);
      myCalendar.set(Calendar.YEAR, myYear);
      myCalendar.set(Calendar.DATE, myCalendar.getActualMinimum(Calendar.DATE));
      int yd_of_first = myCalendar.get(Calendar.DAY_OF_YEAR);
      int lastDay = myCalendar.getActualMaximum(Calendar.DATE);
      int yd_of_last = yd_of_first + lastDay;
      myCalendar.set(Calendar.WEEK_OF_MONTH, week);
      if (day > 0) {
        day = day - 1;
        myCalendar.set(Calendar.DAY_OF_WEEK, ( (firstDayOfWeek + day) % 7));
//        int date = myCalendar.get(Calendar.DATE);
        // Now we know the date of the current location.
        // We return either a saved day or a new one.
        int doy = myCalendar.get(Calendar.DAY_OF_YEAR);
        String day_in_year = String.valueOf(doy);
        String year = String.valueOf(myCalendar.get(Calendar.YEAR));
        Day dayM = dayMap.get(day_in_year + year);
        if (dayM != null && doy >= yd_of_first && doy < yd_of_last) {
          return dayM;
        }
        else {
          d.setDate( -1);
        }
      }
      else {
        d.setWeekOfYear(myCalendar.get(Calendar.WEEK_OF_YEAR));
      }
    }
    else {
      return getColumnName(day);
    }
    return d;
  }

  public void fillMap(int month, int year) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.MONTH, month);
    c.set(Calendar.YEAR, year);
    c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
    int lastDay = myCalendar.getActualMaximum(Calendar.DATE);
    for (int i = c.getActualMinimum(Calendar.DATE); i <= lastDay; i++) {
      Calendar cc = Calendar.getInstance();
      cc.set(Calendar.MONTH, month);
      cc.set(Calendar.YEAR, year);
      cc.set(Calendar.DATE, i);
      Day d = new Day();
      d.setDate(cc.getTime());
      d.setDate(i);
      d.setWeekOfMonth(cc.get(Calendar.WEEK_OF_MONTH));
      d.setWeekOfYear(cc.get(Calendar.WEEK_OF_YEAR));
      String key = "" + cc.get(Calendar.DAY_OF_YEAR) + year;
      dayMap.put(key, d);
    }
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    /**@todo Implement this javax.swing.table.TableModel abstract method*/
  }

  public void removeTableModelListener(TableModelListener l) {
    myListeners.remove(l);
  }

  public String getColumnName(int columnIndex) {
    if (CalendarConstants.DEFAULT_COLUMN_WIDTH < 70 && columnIndex > 0) {
      SimpleDateFormat d = new SimpleDateFormat("EE");
      myCalendar.set(Calendar.DAY_OF_WEEK, ( (firstDayOfWeek + columnIndex - 1) % 7));
      return d.format(myCalendar.getTime());
    }
    else if (columnIndex > 0) {
      SimpleDateFormat d = new SimpleDateFormat("EEEE");
      myCalendar.set(Calendar.DAY_OF_WEEK, ( (firstDayOfWeek + columnIndex - 1) % 7));
      return d.format(myCalendar.getTime());
    }
    else {
      return " ";
    }
  }

  public void addTableModelListener(TableModelListener l) {
    myListeners.add(l);
  }

  public Class<?> getColumnClass(int columnIndex) {
    return Object.class;
  }

  public int getMonth() {
    return myMonth;
  }

  public int getYear() {
    return myYear;
  }

  public String getMonthString() {
    SimpleDateFormat d = new SimpleDateFormat("MMMM");
    myCalendar = Calendar.getInstance();
    myCalendar.setFirstDayOfWeek(firstDayOfWeek);
    myCalendar.setMinimalDaysInFirstWeek(1);
    myCalendar.set(Calendar.MONTH, myMonth);
    myCalendar.set(Calendar.YEAR, myYear);
    return d.format(myCalendar.getTime());
  }

  public void setMonth(int month) {
    myMonth = month;
    fillMap(myMonth, myYear);
    fireStructureChanged();
  }

  public void setYear(int year) {
    myYear = year;
    fillMap(myMonth, myYear);
    fireStructureChanged();
  }
}
