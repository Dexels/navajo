package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingWindow
    extends JInternalFrame
    implements TipiDesignable {
  private TipiSwingDataComponentImpl me;
  private boolean gridFlag = false;
  private boolean selected = false;
  public TipiSwingWindow(TipiSwingDataComponentImpl me) {
    this.me = me;
  }

  public void paint(Graphics g) {
    super.paint(g);
    Color old = g.getColor();
    if (gridFlag) {
      me.paintGrid(getRootPane(), g);
    }
    if (selected) {
      me.highLight(getRootPane(), g);
    }
    g.setColor(old);
  }

  public void setHighlighted(boolean value) {
    selected = value;
  }

  public boolean isHighlighted() {
    return selected;
  }

  public void showGrid(boolean value) {
    gridFlag = value;
  }

  public boolean isGridShowing() {
    return gridFlag;
  }
}