package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import java.awt.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DefaultTipiLayout extends TipiLayout {
  protected XMLElement myInstanceElement;
  protected TipiContext myContext;

  public abstract LayoutManager getLayout();
  public abstract Object parseConstraint(String text);
  public boolean customParser() {
    return false;
  }
  public void createLayout(TipiContext context, Tipi t, XMLElement def, Navajo n) throws com.dexels.navajo.tipi.TipiException {
    myContext = context;
    myInstanceElement = def;
  }

  public Object getDefaultConstraint(TipiComponent tc, int index) {
    return null;
  }

  public boolean needReCreate() {
    return false;
  }
  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
    createLayout(context,t,myInstanceElement,n);
  }

}