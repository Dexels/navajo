package com.dexels.navajo.tipi.components.echoimpl;

import echopoint.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiWindow
    extends TipiEchoDataComponentImpl {
  public TipiWindow() {
  }

  public Object createContainer() {
    Panel ew = new Panel();
    return ew;
  }

  protected void setComponentValue(String name, Object object) {
    Panel w = (Panel) getContainer();
//    if ("title".equals(name)) {
//      w.setTitle(""+object);
//    }
//    if ("w".equals(name)) {
//      w.setWidth(((Integer)object).intValue());
//    }
//    if ("h".equals(name)) {
//      w.setHeight(((Integer)object).intValue());
//    }
//    if ("x".equals(name)) {
//      w.setLeft(((Integer)object).intValue());
//    }
//    if ("y".equals(name)) {
//      w.setTop(((Integer)object).intValue());
//    }
    if ("visible".equals(name)) {
      w.setVisible( ( (Boolean) object).booleanValue());
    }
    super.setComponentValue(name, object);
  }

}
