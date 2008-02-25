package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.util.*;

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
  public void load(Date d);
  public void setSource(CalendarTable t);
}
