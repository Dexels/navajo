package com.dexels.navajo.tipi.studio;
import javax.swing.event.*;
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

public interface StudioListener {
  public void setSelectedComponent(TipiComponent tc);
  public void addComponentSelectionListener(ComponentSelectionListener cs);
  public void removeComponentSelectionListener(ComponentSelectionListener cs);
}