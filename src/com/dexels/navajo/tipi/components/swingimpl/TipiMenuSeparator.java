package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMenuSeparator
    extends TipiSwingComponentImpl {
  private JSeparator mySeparator = null;
  public Object createContainer() {
	  mySeparator = new JSeparator();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return mySeparator;
  }



 
}
