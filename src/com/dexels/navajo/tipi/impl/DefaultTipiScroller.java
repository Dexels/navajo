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
  private JScrollPane jp;

  public DefaultTipiScroller() {
    jp = new JScrollPane();
    jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
     jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    setOuterContainer(jp);
    setContainer(createContainer());
    getContainer().setBackground(Color.black);
    jp.getViewport().add(getContainer());
  }

//  public Container createContainer() {
//     return super.createContainer();
//  }
//
//  public void addToContainer(Component c, Object constraints) {
//    getContainer().add(c,constraints);
//  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition,instance,context);
  }

  public void setContainerLayout(LayoutManager layout) {
    getContainer().setLayout(layout);
  }

}