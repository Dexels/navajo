package com.dexels.navajo.tipi.components.core;

import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMessageStoreImpl
    extends TipiDataComponentImpl
    implements TipiDataComponent {
  private Object myObject;
  private Navajo myNavajo;
  public Container createContainer() {
    return null;
  }

  public void setComponentValue(String name, Object object) {
    // selectedMessage is the only name that occurs, I guess.
    myObject = object;
  }

  public Object getComponentValue(String name) {
    return super.getComponentValue(name);
  }
}