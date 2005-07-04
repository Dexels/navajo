package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiDispose
    extends TipiAction {
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    try {
      TipiComponent tp = (TipiComponent) evaluate(getParameter("path").getValue(),event).value;
      if (tp != null) {
//        System.err.println("ATTEMPTING TO DISPOSE: " + tp.getPath());
      } else {
          System.err.println("ATTEMPTING TO DISPOSE NULL component. ");
      }
//      TipiPathParser tp = new TipiPathParser( myComponent, myContext, path);
      myContext.disposeTipiComponent( (TipiComponent) (tp));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
