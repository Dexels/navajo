package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiScroller
    extends TipiPanel {
  private Container panelContainer;
  private JScrollPane jp;

  public Container createContainer() {
    Container c = super.createContainer();
    jp = new JScrollPane();
    jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jp.getViewport().add(c);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return c;
  }

  public void addToContainer(Object c, Object constraints) {
    getContainer().add((Component)c, constraints);
  }

  public void removeFromContainer(Object c) {
    getContainer().remove((Component)c);
  }
}