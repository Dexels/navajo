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
public class TipiSwingButton
    extends JButton
    implements TipiDesignable {
  private TipiSwingComponentImpl me;
  private boolean gridFlag = false;
  private boolean selected = false;
  public TipiSwingButton(TipiSwingComponentImpl me) {
    this.me = me;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Color old = g.getColor();
    if (gridFlag) {
      me.paintGrid(this, g);
    }
    if (selected) {
      //me.highLight(this, g); // Doesn't work properly for a button.
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.red);
      g2.setStroke(new BasicStroke(3.0f));
      Rectangle r = me.getSwingContainer().getBounds();
      Insets insets = me.getSwingContainer().getInsets();
      g2.drawRect(insets.left, insets.top, getWidth() - insets.left - insets.right, getHeight() - insets.top - insets.bottom);
      g2.setStroke(new BasicStroke(1.0f));
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