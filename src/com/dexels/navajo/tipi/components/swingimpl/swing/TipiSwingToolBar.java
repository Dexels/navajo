package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingToolBar
    extends JToolBar
    implements TipiDesignable {
  TipiToolBar me;
  private boolean gridFlag = false;
  private boolean selected = false;

  public TipiSwingToolBar(TipiToolBar me) {
    this.me = me;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    TipiGradientPaint myPaint = me.getPaint();
    if(myPaint != null){
      myPaint.setBounds(this.getBounds());
      Paint p =  myPaint.getPaint();
      Graphics2D g2 = (Graphics2D)g;
      Paint oldPaint = g2.getPaint();
      g2.setPaint(p);
      g2.fillRect(0,0,getWidth(), getHeight());
      g2.setPaint(oldPaint);
    }

    Color old = g.getColor();
    if (selected) {
      me.highLight(this, g);
    }
    g.setColor(old);
  }

  public Dimension getMinimumSize() {
      return getPreferredSize();
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
