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
public class TipiSwingDesktop
    extends JDesktopPane
    implements TipiDesignable {
  private Image myLogo;
  private TipiSwingDataComponentImpl me;
  private boolean gridFlag = false;
  private boolean selected = false;
  public TipiSwingDesktop(TipiSwingDataComponentImpl me) {
    this.me = me;
  }

  public TipiSwingDesktop(Image i, TipiSwingDataComponentImpl me) {
    this.me = me;
    myLogo = i;
  }

  public void setImage(Image i) {
    myLogo = i;
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