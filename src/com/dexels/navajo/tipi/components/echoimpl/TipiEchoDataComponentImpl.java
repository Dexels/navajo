package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.echoimpl.helpers.*;
import echopoint.layout.*;
import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public abstract class TipiEchoDataComponentImpl
    extends TipiDataComponentImpl {
  public TipiEchoDataComponentImpl() {
    TipiHelper th = new EchoTipiHelper();
    th.initHelper(this);
    addHelper(th);
  }

  public void removeFromContainer(Object c) {
    Component cc = (Component) getContainer();
    Component child = (Component) c;
    cc.remove(child);
  }

  public void addToContainer(Object c, Object constraints) {

    Component cc = (Component) getContainer();
    Component child = (Component) c;

    if (LayoutManageable.class.isInstance(getContainer())) {
      try {
         ( (LayoutManageable) getContainer()).add(child, constraints);
      }
      catch (Throwable ex) {
        System.err.println("#@@#$#@$ stupid layout");
        ex.printStackTrace();
      }
    }
    else {
      cc.add(child);
    }
  }

  public void setContainerLayout(Object layout) {
    if (LayoutManageable.class.isInstance(getContainer())) {
      System.err.println("Setting layout: " + layout.getClass());
      ( (LayoutManageable) getContainer()).setLayoutManager( (LayoutManager) layout);
    }
  }

  /**
   * loadData
   *
   * @param n Navajo
   * @param context TipiContext
   * @throws TipiException
   * @todo Implement this com.dexels.navajo.tipi.TipiDataComponent method
   */
  public void loadData(Navajo n, TipiContext context,String method) throws TipiException {
    super.loadData(n, context,method);
//    System.err.println("Loading data: ");
//    try {
//      n.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
  }

}
