package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import java.awt.event.*;
import javax.swing.event.*;

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

  public void setUndecorated(boolean b) {
    // Ignored for now. Maybe implement once?
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


  public void setLocationRelativeTo(Component c) {
    // Ignored in internal frame. Is ok.
  }

  public void setModal(boolean b) {
    System.err.println("Modality not supported in studio (internal) frame. It is a pity");
  }

  public void addWindowListener(final WindowListener wl) {
    addInternalFrameListener(new InternalFrameListener() {
      public void internalFrameActivated(InternalFrameEvent ife) {
        wl.windowActivated(new WindowEvent(null,-1));
      }
      public void internalFrameClosed(InternalFrameEvent ife) {
        wl.windowClosed(new WindowEvent(null,-1));
      }
      public void internalFrameClosing(InternalFrameEvent ife) {
        wl.windowClosing(new WindowEvent(null,-1));
      }
      public void internalFrameDeactivated(InternalFrameEvent ife) {
        wl.windowDeactivated(new WindowEvent(null,-1));
      }
      public void internalFrameDeiconified(InternalFrameEvent ife) {
        wl.windowDeiconified(new WindowEvent(null,-1));
      }
      public void internalFrameIconified(InternalFrameEvent ife) {
        wl.windowIconified(new WindowEvent(null,-1));
      }
      public void internalFrameOpened(InternalFrameEvent ife) {
         wl.windowOpened(new WindowEvent(null,-1));
       }

    });
  }
}
