package com.dexels.navajo.tipi;

import nanoxml.*;
import java.util.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.*;
import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiComponent implements TipiBase {

  private Container myContainer = null;
  protected ArrayList propertyNames = new ArrayList();
  protected ArrayList properties = new ArrayList();

  public abstract void load(XMLElement elm, TipiContext context) throws TipiException;

  public void addProperty(String name, BasePropertyComponent bpc,TipiContext context, Map contraints) {
    System.err.println("Oh dear");
    propertyNames.add(name);
    properties.add(bpc);
    addComponent(bpc,context,contraints);
  }

  public void addComponent(TipiBase c, TipiContext context, Map td) {
    System.err.println("td: "+td);
    if (JInternalFrame.class.isInstance(getContainer())) {
      ((JInternalFrame)getContainer()).getContentPane().add(c.getContainer());
    } else {
      getContainer().add(c.getContainer(), td);
    }
  }
//  public abstract void addComponent(TipiComponent c, TipiContext context,Map td);
  public Container getContainer() {
    return myContainer;
  }
  public void setContainer(Container c) {
    myContainer = c;
  }

}