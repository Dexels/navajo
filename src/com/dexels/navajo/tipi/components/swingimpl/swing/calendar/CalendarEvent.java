package com.dexels.navajo.tipi.components.swingimpl.swing.calendar;

import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */
public class CalendarEvent {
  TipiCalendarTable mySource;
  InputEvent myEvent;
  public CalendarEvent(TipiCalendarTable t, InputEvent e) {
    mySource = t;
    myEvent = e;
  }

  public CalendarEvent() {
  }

  public InputEvent getEvent() {
    return myEvent;
  }

  public TipiCalendarTable getSource() {
    return mySource;
  }
}
