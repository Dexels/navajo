package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiLabel extends TipiEchoComponentImpl {
  public TipiLabel() {
  }
  public Object createContainer() {
    Label b = new Label();
    return b;
  }
  protected void setComponentValue(String name, Object object) {
    Label b = (Label)getContainer();
    if ("text".equals(name)) {
      b.setText(""+object);
    }
    if ("icon".equals(name)) {
      System.err.println("URL: "+object.toString());
      b.setIcon(new ResourceImageReference(object.toString()));
    }
  }

}
