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

public class TipiMenuBar
    extends TipiEchoDataComponentImpl {
  public TipiMenuBar() {
  }

  public Object createContainer() {
    MenuBar b = new MenuBar();
    b.setTopOffset(0);
    System.err.println("Creating menubar");
    b.setLeft(0);
    b.setTop(0);
    b.setFixedPosition(true);
    return b;
  }

  protected void setComponentValue(String name, Object object) {
    MenuBar b = (MenuBar) getContainer();
    if ("text".equals(name)) {
      b.setText("" + object);
    }
//    if ("icon".equals(name)) {
//      System.err.println("URL: "+object.toString());
//      b.setIcon(new ResourceImageReference(object.toString()));
//   }
    super.setComponentValue(name, object);
  }

}
