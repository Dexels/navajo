package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.components.swing.TipiSwingToolBar;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiToolBar extends DefaultTipi {
  private int orientation = TipiSwingToolBar.HORIZONTAL;
  public DefaultTipiToolBar() {
    initContainer();
  }
  public void addToContainer(Component c, Object parm2) {
    getContainer().add(c);
  }
  public void removeFromContainer(Component c) {
    getContainer().remove(c);
  }
  public Container createContainer() {
    return new TipiSwingToolBar(this);
  }
//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    super.load(definition, instance, context);
//    String o = definition.getStringAttribute("orientation","horizontal");
//    setOrientation(o);
//  }

  private void setOrientation(String o) {
    if ("horizontal".equals(o)) {
     ((TipiSwingToolBar)getContainer()).setOrientation(TipiSwingToolBar.HORIZONTAL);
   }
   if ("vertical".equals(o)) {
     ((TipiSwingToolBar)getContainer()).setOrientation(TipiSwingToolBar.VERTICAL);
   }

 }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if ("orientation".equals(name)) {
      setOrientation((String)object);
    }

  }

}