package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
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
public abstract class DefaultTipiLayout
    extends TipiLayout {
  protected XMLElement myInstanceElement;
  protected TipiContext myContext;

  protected abstract Object parseConstraint(String text);

//  public abstract void addComponent(TipiComponent tc, String constraint);

//  public boolean customParser() {
//    return false;
//  }


  public void loadLayout(XMLElement def, Tipi t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
    myContext = TipiContext.getInstance();
    myInstanceElement = def;
//    instantiateLayout(myContext,t,def);

    Vector v = myInstanceElement.getChildren();
    for (int i = 0; i < v.size(); i++) {
//      System.err.println("Adding child to defaultlayout. Tipi: "+t.getId());
      XMLElement child = (XMLElement) v.get(i);
//      System.err.println("======================================\n");
//      System.err.println(child.toString());
//      System.err.println("=========== END OF DEFAULT ===========\n");
      String constraintString = child.getStringAttribute("constraint");
      Object constraint = parseConstraint(constraintString);
      t.addAnyInstance(myContext, child, constraint);
    }
  }

  public Object getDefaultConstraint(TipiComponent tc, int index) {
    return null;
  }

//  public boolean needReCreate() {
//    return false;
//  }

//  public void reCreateLayout(TipiContext context, Tipi t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
//    createLayout(context, t, myInstanceElement, n);
//  }
}