package com.dexels.navajo.tipi.impl;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.swing.*;
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
  private JScrollPane jp;

//  public DefaultTipiScroller() {
//    initContainer();
//  }
//
  public Container createContainer() {
    Container c = super.createContainer();
    jp = new JScrollPane();
    jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    setOuterContainer(jp);
    jp.getViewport().add(c);
    TipiHelper th = new SwingTipiHelper();
th.initHelper(this);
addHelper(th);

    return c;
  }

//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//  }

  public void setContainerLayout(LayoutManager layout) {
//    getContainer().setLayout(layout);
    throw new RuntimeException("setting containerlayout of a scrollpanel?! Hmmmm...");
  }
//  public void setContainer(Container c) {
//    super.setContainer(c);
//    jp.getViewport().add(c);
//  }
  public void addToContainer(Component c, Object constraints) {
    getContainer().add(c,constraints);
  }
  public void removeFromContainer(Component c) {
    getContainer().remove(c);
  }

}