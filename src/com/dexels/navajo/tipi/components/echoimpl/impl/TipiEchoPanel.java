package com.dexels.navajo.tipi.components.echoimpl.impl;

import echopoint.Panel;
import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiEchoPanel
    extends Panel {
  public int w = -1;
  public int h = -1;

  public TipiEchoPanel() {

  }

  public void setBackground(Color c) {
    super.setBackground(c);
    System.err.println("setBackground called in TipiEchoPanel: " + c);
  }

  public void setWidth(int w) {
    this.w = w;
  }

  public void setHeight(int h) {
    this.h = h;
  }

}
