package com.dexels.navajo.tipi.impl;


import java.awt.Container;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.calendar.*;
import com.dexels.navajo.tipi.components.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiCalendar extends DefaultTipiPanel {
  public DefaultTipiCalendar() {
  }

  public Container createContainer() {
    TipiHelper th = new SwingTipiHelper();
    th.initHelper(this);
    addHelper(th);
    return new TipiCalendarTable();
  }

}