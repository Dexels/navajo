package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScreen extends DefaultTipi implements TipiScreen{

  public DefaultTipiScreen() {
  }

  public void load(XMLElement elm, TipiContext context) throws TipiException {
    String elmName = elm.getName();
    if(!elmName.equals("screen")){
      throw new TipiException("Screen node not found!, found " + elmName + " instead.");
    }
    String type = (String)elm.getAttribute("type");
    if (type.equals("desktop")) {
      setContainer(new JDesktopPane());
    } else {
      setContainer(new TipiPanel());
    }
    super.load(elm,context);
  }
}