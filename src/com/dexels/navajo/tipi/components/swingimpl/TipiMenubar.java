package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMenubar
    extends TipiSwingComponentImpl {
  private TipiSwingMenuBar myMenuBar;

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition, instance, context);
    Vector v = definition.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement) v.get(i);
      TipiComponent tc = context.instantiateComponent(current);
      addComponent(tc, context, null);
    }
 }

  public Object createContainer() {
    myMenuBar = new TipiSwingMenuBar(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myMenuBar;
  }
}
