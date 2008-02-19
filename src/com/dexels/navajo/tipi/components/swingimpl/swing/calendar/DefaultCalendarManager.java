package com.dexels.navajo.tipi.components.swingimpl.swing.calendar;

import java.awt.event.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */
public class DefaultCalendarManager
    implements CalendarManager {
  private TipiCalendarTable mySource;
  public DefaultCalendarManager() {
  }

  public void setSource(TipiCalendarTable t) {
    mySource = t;
  }

  public void fireCalendarEvent(CalendarEvent e) {
    if (mySource == null) {
      mySource = e.getSource();
    }
    if (KeyEvent.class.isInstance(e.getEvent())) {
      KeyEvent ke = (KeyEvent) e.getEvent();
      if (ke.getKeyCode() == KeyEvent.VK_B) {
        List<Day> selectedDays = mySource.getSelectedDays();
        for (int i = 0; i < selectedDays.size(); i++) {
          Day current = selectedDays.get(i);
          Map<String,String> attr = new HashMap<String,String>();
          attr.put("from", "09:00");
          attr.put("to", "23:00");
          attr.put("hall", "1");
          current.setAttributes(attr);
          current.setDate(current.getDate());
          mySource.rebuildUI();
        }
      }
      if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
    	  List<Day> selectedDays = mySource.getSelectedDays();
        for (int i = 0; i < selectedDays.size(); i++) {
          Day current = selectedDays.get(i);
          Map<String,String> attr = new HashMap<String,String>();
          attr.put("from", " ");
          attr.put("to", " ");
          attr.put("hall", " ");
          current.setAttributes(attr);
          current.setDate(current.getDate());
          mySource.rebuildUI();
        }
      }
    }
    if (mySource.getSelectedColumn() > -1 && mySource.getSelectedRow() > -1) {
      if (Day.class.isInstance( (mySource.getModel().getValueAt(mySource.getSelectedRow(), mySource.getSelectedColumn())))) {
//        Day d = (Day) (mySource.getModel().getValueAt(mySource.getSelectedRow(), mySource.getSelectedColumn()));
        System.err.println("Selected: " + mySource.getSelectedRow() + ", " + mySource.getSelectedColumn());
      }
      else {
        System.err.println("You clicked on 'something'!");
      }
    }
  }
}
