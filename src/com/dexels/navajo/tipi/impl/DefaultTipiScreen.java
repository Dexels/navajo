package com.dexels.navajo.tipi.impl;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScreen extends DefaultTipiRootPane {
  public DefaultTipiScreen() {
  }
  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    DefaultTipiMainFrame td = new DefaultTipiMainFrame();
//
//    context.setToplevel(td);
//      td.setBounds(100,100,800,600);
//      td.show();
//    setContainer(td.getContentPane());
    super.load(definition,instance,context);
  }
}