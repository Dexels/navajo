package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.echoimpl.impl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiTextField
    extends TipiEchoComponentImpl {
  public TipiTextField() {
  }

  public Object createContainer() {
    TipiEchoTextField b = new TipiEchoTextField();
    return b;
  }

  protected void setComponentValue(String name, Object object) {
    TipiEchoTextField b = (TipiEchoTextField) getContainer();
    if ("text".equals(name)) {
      b.setText("" + object);
    }
    if ("enabled".equals(name)) {
      b.setEnabled("true".equals(object));
    }
    if ("w".equals(name)) {
      int w = ( (Integer) object).intValue();
      b.setColumns(w);
    }
    super.setComponentValue(name, object);
  }

}
