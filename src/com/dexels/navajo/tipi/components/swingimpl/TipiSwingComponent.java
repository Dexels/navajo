package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import java.awt.event.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiSwingComponent extends TipiComponent{
  public void highLight(Component c, Graphics g);
//
  public void setCursor(int cursorid);
  public void setCursor(Cursor c);
  public Container getSwingContainer();
  public void showPopup(MouseEvent e);
  public void setWaitCursor(boolean b);

  public void runSyncInEventThread(Runnable r);
  public void runASyncInEventThread(Runnable r) ;
}
