package com.dexels.navajo.tipi.components.echoimpl;

import java.net.*;

import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import echopoint.*;
import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiButton
    extends TipiEchoComponentImpl {
  public TipiButton() {
  }

  public Object createContainer() {
    PushButton b = new PushButton();
    b.setIconTextMargin(10);
    b.setHorizontalTextPosition(EchoConstants.CENTER);

    return b;
  }

  /**
   * getComponentValue
   *
   * @param name String
   * @return Object
   * @todo Implement this
   *   com.dexels.navajo.tipi.components.core.TipiComponentImpl method
   */
  protected Object getComponentValue(String name) {
    return "";
  }

  /**
   * setComponentValue
   *
   * @param name String
   * @param object Object
   * @todo Implement this
   *   com.dexels.navajo.tipi.components.core.TipiComponentImpl method
   */
  protected void setComponentValue(String name, Object object) {
    Button b = (Button) getContainer();
    if ("text".equals(name)) {
      b.setText("" + object);
    }
    if ("icon".equals(name)) {
      System.err.println("URL: " + object.toString());
      URL u = (URL) object;
      b.setIcon(new TipiImageReference(u));
    }
    super.setComponentValue(name, object);
  }

}
