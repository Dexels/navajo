package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.echoimpl.helpers.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public abstract class TipiEchoComponentImpl
    extends TipiComponentImpl {

  public TipiEchoComponentImpl() {
    TipiHelper th = new EchoTipiHelper();
    th.initHelper(this);
    addHelper(th);
  }

}
