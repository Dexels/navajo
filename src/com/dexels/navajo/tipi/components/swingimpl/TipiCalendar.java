package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.calendar.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiCalendar
    extends TipiPanel {
  public TipiCalendar() {
  }

  public Object createContainer() {
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return new TipiCalendarTable();
  }
}