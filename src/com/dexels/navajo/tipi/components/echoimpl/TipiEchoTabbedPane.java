package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
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

public class TipiEchoTabbedPane extends TipiEchoDataComponentImpl {
  public TipiEchoTabbedPane() {
  }
  public Object createContainer() {
    TabbedPane p = new TabbedPane();
    return p;
  }
  public void addToContainer(Object c, Object constraints) {
    TabbedPane p = (TabbedPane)getContainer();
    Component comp = (Component)c;
    p.addTab(constraints.toString(),comp);
  }


}
