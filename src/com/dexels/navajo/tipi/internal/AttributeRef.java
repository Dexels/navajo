package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class AttributeRef {
  private String myName = null;
  private TipiComponent myComponent = null;
  public AttributeRef(TipiComponent tc, String name) {
    setTipiComponent(tc);
    setName(name);
  }

  public Object getValue() {
    return myComponent.getValue(myName);
  }

  public void setValue(Object value, TipiComponent source) {
    myComponent.setValue(myName, value, source);
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
