package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.*;
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

public class YearOverviewPopupMenu
    extends JPopupMenu {
  final JSlider mySlider = new JSlider(200, 1000, 200);
  final YearOverviewTable myTable;

  public YearOverviewPopupMenu(YearOverviewTable table, JScrollPane scroll) {
    myTable = table;
    final JScrollPane scroller = scroll;
    mySlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        // FF disabled
        //CalendarConstants.DEFAULT_COLUMN_WIDTH = mySlider.getValue()/10;
        //CalendarConstants.DEFAULT_ROW_HEIGHT = mySlider.getValue()/10;
        myTable.zoomOnCurrentCell(mySlider.getValue());
        Rectangle cellRect = myTable.getCellRect(myTable.getSelectedColumn(), myTable.getSelectedRow(), true);
        scroller.getViewport().setViewPosition(new Point(cellRect.x, cellRect.y));
        //table.rebuildUI();
      }
    });

    mySlider.setOrientation(SwingConstants.VERTICAL);
    this.add(mySlider);
  }
}