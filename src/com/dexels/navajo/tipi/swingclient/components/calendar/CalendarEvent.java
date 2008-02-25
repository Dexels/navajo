package com.dexels.navajo.tipi.swingclient.components.calendar;

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
  CalendarTable mySource;
  InputEvent myEvent;

  public CalendarEvent(CalendarTable t, InputEvent e) {
    mySource = t;
    myEvent = e;
  }

  public CalendarEvent() {

  }

  public InputEvent getEvent() {
    return myEvent;
  }

  public CalendarTable getSource() {
    return mySource;
  }

}