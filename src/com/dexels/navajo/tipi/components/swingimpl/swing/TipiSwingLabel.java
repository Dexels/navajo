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
public class TipiSwingLabel
    extends JLabel
    implements TipiDesignable {
  TipiSwingComponentImpl me;
  private boolean gridFlag = false;
  private boolean selected = false;
  public TipiSwingLabel(TipiSwingComponentImpl me) {
    this.me = me;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Color old = g.getColor();
//    if (gridFlag) {
//      me.paintGrid(this, g);
//    }
    if (selected) {
      me.highLight(this, g);
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
