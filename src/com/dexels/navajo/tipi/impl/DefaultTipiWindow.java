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
    extends DefaultTipi implements TipiWindow {

  private int x, y, w, h;

  public DefaultTipiWindow() {
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    String elmName = elm.getName();
    String title = (String)elm.getAttribute("title");
    if (!elmName.equals("window")) {
      throw new TipiException("Window node not found!, found " + elmName + " instead.");
    }
    x = Integer.parseInt( (String) elm.getAttribute("x", "0"));
    y = Integer.parseInt( (String) elm.getAttribute("y", "0"));
    w = Integer.parseInt( (String) elm.getAttribute("w", "100"));
    h = Integer.parseInt( (String) elm.getAttribute("h", "100"));
    JInternalFrame jj = new JInternalFrame();
    setContainer(jj);
    jj.setTitle(title);
    jj.setClosable(true);
    jj.setIconifiable(true);
    jj.setResizable(true);
  }

  public void setBounds() {
    System.err.println("x: "+x);
    System.err.println("y: "+y);
    System.err.println("w: "+w);
    System.err.println("h: "+h);
    ((JInternalFrame)getContainer()).setBounds(new Rectangle(x, y, w, h));
    getContainer().setVisible(true);
//    getContainer().repaint();
  }

}