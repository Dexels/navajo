package com.dexels.navajo.tipi.components.echoimpl.ui;

import echopoint.ui.layout.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CustomUIComponents {
  public CustomUIComponents() {
  }

  public static void register() {
    LayoutManagerPeerFactory.addAssociations("com.dexels.navajo.tipi.components.echoimpl.ui.layout.EchoBorderLayout");
    LayoutManagerPeerFactory.addAssociations("com.dexels.navajo.tipi.components.echoimpl.ui.layout.EchoGridBagLayout");
    TipiEchoPanelUI.register();
    TipiEchoTextFieldUI.register();
    TipiEchoScrollerUI.register();
    //..etc
  }

}
