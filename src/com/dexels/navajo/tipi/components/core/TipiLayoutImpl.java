package com.dexels.navajo.tipi.components.core;

import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiLayoutImpl
    extends TipiLayout {
  protected XMLElement myInstanceElement;
  protected abstract Object parseConstraint(String text);

  public void loadLayout(XMLElement def, TipiComponent t, Navajo n) throws com.dexels.navajo.tipi.TipiException {
    myInstanceElement = def;
    Vector v = myInstanceElement.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement child = (XMLElement) v.get(i);
      String constraintString = child.getStringAttribute("constraint");
      if (!child.getName().equals("event")) {
        Object constraint = parseConstraint(constraintString);
        t.addComponentInstance(myContext, child, constraint);
      } else {
        System.err.println("Event found within layout. Line: "+def.getLineNr()+"\nNot right, but should work");
      }
    }
  }

  public Object getDefaultConstraint(TipiComponent tc, int index) {
    return null;
  }
}
