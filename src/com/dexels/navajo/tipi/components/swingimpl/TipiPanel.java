package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiPanel
    extends TipiDataComponentImpl {
  public Container createContainer() {
    TipiSwingPanel tsp = new TipiSwingPanel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return tsp;
  }

  public void addToContainer(Component c, Object constraints) {
    getContainer().add(c, constraints);
  }

  public void removeFromContainer(Component c) {
    getContainer().remove(c);
  }

//  public DefaultTipiPanel() {
//    initContainer();
//  }
//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//  }
  public void setComponentValue(String name, Object value) {
    if ("enabled".equals(name)) {
      //System.err.println("=======================>> Woei! setting panel enabled");
      getContainer().setEnabled(value.equals("true"));
    }
    super.setComponentValue(name, value);
  }
}