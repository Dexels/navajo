package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingFrameStudioImpl extends TipiSwingWindow
    implements TipiSwingFrame {

  private int myExtendedState = -1;

  public TipiSwingFrameStudioImpl(TipiSwingDataComponentImpl td) {
    super(td);
  }


  public void setExtendedState(int state) {
    myExtendedState = state;

  }

  /**
   * setIconImage
   *
   * @param i Image
   * @todo Implement this
   *   com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingFrame method
   */
  public void setIconImage(ImageIcon i) {
    setFrameIcon(i);
  }

  public int getExtendedState() {
    return myExtendedState;
  }


}
