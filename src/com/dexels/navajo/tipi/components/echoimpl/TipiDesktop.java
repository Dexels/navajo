package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import echopoint.*;
import nextapp.echo.Color;
import echopoint.layout.*;
import nextapp.echo.*;

//import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiDesktop
    extends TipiEchoDataComponentImpl {

  private int windowCount = 0;

  public TipiDesktop() {
  }

  public Object createContainer() {
    Panel p = new Panel();
    p.setBackground(new Color(30, 30, 240));
    p.setLayoutManager(new XyLayoutManager());
    return p;
  }

  public void addToContainer(Object c, Object constraints) {
    windowCount++;
    Panel p = (Panel)getContainer();
    Component cc = (Component)c;
    p.add(cc);
  }

  public void removeFromContainer(Object c) {
    windowCount--;

  }
}
