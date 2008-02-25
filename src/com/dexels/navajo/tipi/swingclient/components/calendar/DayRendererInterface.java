package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public interface DayRendererInterface {
  public void setDay(Day d);

  public void setAnchor(boolean value);

  public void setSelected(boolean value);

  public void setBackground(Color c);

  public Component getComponent();

}