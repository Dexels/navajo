package com.dexels.navajo.tipi.impl;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScroller extends DefaultTipiPanel {
  private Container panelContainer;
  public DefaultTipiScroller() {
    panelContainer = super.createContainer();
    setContainer(createContainer());
    ((JScrollPane)getContainer()).getViewport().add(panelContainer);
  }
  public Container createContainer() {
    JScrollPane jp = new JScrollPane();
    jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    return jp;
  }

  public void addToContainer(Component c, Object constraints) {
    panelContainer.add(c,constraints);
  }


  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition,instance,context);
  }

  public void setContainerLayout(LayoutManager layout) {
    panelContainer.setLayout(layout);
  }

}