package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.echoimpl.layout.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoGridBagLayout
    extends TipiLayoutImpl {
  public TipiEchoGridBagLayout() {
  }

  protected void setValue(String name, TipiValue tv) {
    /**@todo Implement this com.dexels.navajo.tipi.internal.TipiLayout abstract method*/
  }

  public void createLayout() throws com.dexels.navajo.tipi.TipiException {
    myLayout = new EchoGridBagLayout(20, 50);
    EchoGridBagLayout p = (EchoGridBagLayout) myLayout;
    System.err.println("EchoGridBagLayout created!!");
    setLayout(p);
  }

  protected Object parseConstraint(String text) {
    return new EchoGridBagConstraints(text);
  }

}
