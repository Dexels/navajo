package com.dexels.navajo.tipi.swingclient.components.calendar;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class YearOverviewTable
    extends JTable {

  public YearOverviewTable() {

    this.setModel(new YearOverviewModel());
    this.setDefaultRenderer(Object.class, new YearOverviewRenderer());
    this.setDefaultEditor(Object.class, null);
    this.setShowGrid(false);
    this.setRowHeight(200);
    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    this.setBackground(CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
    this.setCellSelectionEnabled(true);
    //this.add(myPopup);
    for (int i = 0; i < 4; i++) {
      getColumnModel().getColumn(i).setPreferredWidth(200);
    }
  }

  public void zoomOnCurrentCell(int zoomFactor) {
    this.setRowHeight(zoomFactor);
    for (int i = 0; i < 4; i++) {
      getColumnModel().getColumn(i).setPreferredWidth(zoomFactor);
    }

//    CalendarTable selectedTable = (CalendarTable)getModel().getValueAt(getSelectedRow(), getSelectedColumn());
//    selectedTable.rebuildUI();
  }

  public int getSelectedMonth() {
    return 4 * getSelectedRow() + getSelectedColumn();
  }

}