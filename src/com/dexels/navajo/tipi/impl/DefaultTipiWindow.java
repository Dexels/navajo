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

public class DefaultTipiWindow
    extends DefaultTipi {

//  private int x, y, w, h;

  public DefaultTipiWindow() {
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    JInternalFrame jj = new JInternalFrame();
    setContainer(jj);
    String elmName = elm.getName();
    String title = (String)elm.getAttribute("title");
//    if (!elmName.equals("window")) {
//      throw new TipiException("Window node not found!, found " + elmName + " instead.");
//    }
    super.load(elm,context);
    int x = Integer.parseInt( (String) elm.getAttribute("x", "0"));
    int y = Integer.parseInt( (String) elm.getAttribute("y", "0"));
    int w = Integer.parseInt( (String) elm.getAttribute("w", "100"));
    int h = Integer.parseInt( (String) elm.getAttribute("h", "100"));
    jj.setTitle(title);
    jj.setClosable(true);
    jj.setIconifiable(true);
    jj.setResizable(true);
    ((JInternalFrame)getContainer()).setBounds(new Rectangle(x, y, w, h));
    getContainer().setVisible(true);
  }
}