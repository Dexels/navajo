package com.dexels.navajo.tipi.components.swingimpl.swing.calendar;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */
public class CalendarTableCellRenderer
    implements TableCellRenderer {
  JLabel week = new JLabel();
  DefaultDayRenderer dd = new DefaultDayRenderer();
  MultipleDayRenderer md = new MultipleDayRenderer();
  public CalendarTableCellRenderer() {
    week.setHorizontalAlignment(JLabel.CENTER);
    week.setHorizontalTextPosition(JLabel.CENTER);
    week.setFont(new java.awt.Font("Dialog", 1, 11));
    week.setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    // Determine if day is anchor of selection
    boolean isAnchor = false;
    if (table.getSelectedColumn() == column && table.getSelectedRow() == row) {
      isAnchor = true;
    }
    if (row == 0 && column == 0) {
      week.setIcon(new ImageIcon(MainApplication.class.getResource("select-all.gif")));
      week.setText("");
      week.setForeground(CalendarConstants.getColor(CalendarConstants.BGFONT_COLOR));
      week.setBackground(CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      return week;
    }
    else {
      week.setIcon(null);
    }
    /*
        Single Calendar Rendering Mode
     */
    if (Day.class.isInstance(value)) {
      Day d = (Day) value;
      if (column > 0) {
        dd.setBackground(isSelected ? Color.blue : Color.white);
        dd.setDay(d);
        dd.setSelected(isSelected);
        dd.setAnchor(isAnchor);
        return dd;
      }
      else {
        week.setText("" + d.getWeekOfYear());
        week.setForeground(CalendarConstants.getColor(CalendarConstants.BGFONT_COLOR));
        week.setBackground(CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
        return week;
      }
    }
    /*
        Multiple Calendar Rendering Mode
     */
    else if (MultipleDayContainer.class.isInstance(value)) {
      // Implement this!!!
      MultipleDayContainer d = (MultipleDayContainer) value;
      if (column > 0) {
        md.setDays(d.getDays());
        md.setBackground(isSelected ? Color.blue : Color.white);
        md.setSelected(isSelected);
        md.setAnchor(isAnchor);
        return md;
      }
      else {
        week.setText("" + d.getDays()[0].getWeekOfYear());
        week.setForeground(CalendarConstants.getColor(CalendarConstants.BGFONT_COLOR));
        week.setBackground(CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
        return week;
      }
    }
    else {
      week.setText( (String) value);
      week.setForeground(CalendarConstants.getColor(CalendarConstants.BGFONT_COLOR));
      week.setBackground(CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
      return week;
    }
  }
}
