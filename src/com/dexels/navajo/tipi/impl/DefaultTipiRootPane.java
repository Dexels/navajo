package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DefaultTipiRootPane extends DefaultTipi {

  public DefaultTipiRootPane() {
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition,instance,context);


    String elmName = definition.getName();

    String menubar = (String)definition.getAttribute("menubar");
    if (menubar!=null) {
      XMLElement xe = context.getTipiMenubarDefinition(menubar);
      TipiMenubar tm = context.createTipiMenubar();
      tm.load(xe,context);
      context.getTopLevel().setTipiMenubar(tm);
    }
  }
}