package com.dexels.navajo.tipi.components.echoimpl;

import echopoint.*;
import com.dexels.navajo.tipi.components.echoimpl.impl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiScroller
    extends TipiEchoDataComponentImpl {
  public TipiScroller() {
  }

  public Object createContainer() {
    TipiEchoScroller scroll = new TipiEchoScroller();
//    scroll.setHeight(200);
    return scroll;
  }

}
