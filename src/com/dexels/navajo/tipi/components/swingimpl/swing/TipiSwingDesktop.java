package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingDesktop
    extends JDesktopPane
    implements TipiDesignable {
  private Image myLogo;
  TipiComponent me;
  private boolean gridFlag = false;
  private boolean selected = false;
  public TipiSwingDesktop(TipiComponent me) {
    this.me = me;
  }

  public TipiSwingDesktop(Image i, TipiComponent me) {
    this.me = me;
    myLogo = i;
  }

  public void setImage(Image i) {
    myLogo = i;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (myLogo != null) {
      int img_width = myLogo.getWidth(this);
      int img_height = myLogo.getHeight(this);
      g.drawImage(myLogo, (getWidth() / 2) - (img_width / 2), (getHeight() / 2) - (img_height / 2), this);
    }
    Color old = g.getColor();
    if (gridFlag) {
      me.paintGrid(this, g);
    }
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