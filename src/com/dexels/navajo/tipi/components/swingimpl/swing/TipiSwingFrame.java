package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiSwingFrame {
  public Container getContentPane();
  public void setJMenuBar(JMenuBar j);
  public boolean isVisible();
  public boolean isResizable();
  public Rectangle getBounds();
  public void setBounds(Rectangle r);
  public void setTitle(String title);
  public void setExtendedState(int state);
  public int getExtendedState();
  public void setIconImage(ImageIcon i);
  public String getTitle();
}
