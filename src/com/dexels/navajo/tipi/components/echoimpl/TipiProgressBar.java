package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import echopoint.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiProgressBar extends TipiEchoComponentImpl {
  public TipiProgressBar() {
  }
  public Object createContainer() {
    ProgressBar p = new ProgressBar();
    p.setMaximum(100);
    p.setMinimum(0);
    return p;
  }
  protected void setComponentValue(String name, Object object) {
    ProgressBar p = (ProgressBar)getContainer();
     if ("value".equals(name)) {
       System.err.println("Setting value to: "+object.toString());
       int value = ((Integer)object).intValue();
       p.setValue(value);
     }
     if ("text".equals(name)) {
       p.setProgressString(""+object);
     }
     super.setComponentValue(name,object);

}

}
