package com.dexels.navajo.tipi.components.calendar;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public interface CalendarManager {
  public void fireCalendarEvent(CalendarEvent e);
  public void setSource(TipiCalendarTable t);
}
