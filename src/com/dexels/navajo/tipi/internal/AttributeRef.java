package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class AttributeRef implements TipiReference {
  private String myName = null;
  private TipiComponent myComponent = null;
  public AttributeRef(TipiComponent tc, String name) {
    setTipiComponent(tc);
    setName(name);
  }

  public Object getValue() {
    return myComponent.getValue(myName);
  }

  public void setValue(String expression, Operand value, TipiComponent source) {
    /** @todo Ignores the value operand. Not really beautiful */
    myComponent.setValue(myName, expression, source,null);
  }

  public void setTipiComponent(TipiComponent tc) {
    myComponent = tc;
  }

  public TipiComponent getTipiComponent() {
    return myComponent;
  }

  public String getName() {
    return myName;
  }

  public void setName(String n) {
    myName = n;
  }

  public String toString() {
    return "attributeref.[component:" + myComponent.getPath() + ",attribute:" + myName + "]";
  }
}
