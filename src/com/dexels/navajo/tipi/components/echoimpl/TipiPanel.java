package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.echoimpl.impl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiPanel
    extends TipiEchoDataComponentImpl {

  public TipiPanel() {
  }

  public Object createContainer() {
    TipiEchoPanel p = new TipiEchoPanel();
    return p;
  }

//  public void addToContainer(Object o, Object contraints){
//
//  }
//
//  public void setContainerLayout(Object l){
//
//  }

  public void setComponentValue(final String name, final Object object) {

    if ("w".equals(name)) {
      TipiEchoPanel cont = (TipiEchoPanel) getContainer();
      cont.setWidth( ( (Integer) object).intValue());
    }
    if ("h".equals(name)) {
      TipiEchoPanel cont = (TipiEchoPanel) getContainer();
      cont.setHeight( ( (Integer) object).intValue());
    }
    super.setComponentValue(name, object);
  }

}
