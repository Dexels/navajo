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
    setContainer(createContainer());
  }

  public Container createContainer() {
    JInternalFrame f = new JInternalFrame();
    f.setMaximizable(true);
    f.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    return f;
  }

  public void addToContainer(Component c, Object constraints) {
    ((JInternalFrame)getContainer()).getContentPane().add(c,constraints);
  }
  public void setContainerLayout(LayoutManager layout){
    ((JInternalFrame)getContainer()).getContentPane().setLayout(layout);
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {

//    System.err.println("\n\nLOADING WINDOW!!!!\n\n");
    JInternalFrame jj = (JInternalFrame)getContainer();
    String elmName = elm.getName();
    String title = (String)elm.getAttribute("title");
//    if (!elmName.equals("window")) {
//      throw new TipiException("Window node not found!, found " + elmName + " instead.");
//    }
    super.load(elm,instance,context);
    int x = Integer.parseInt( (String) elm.getAttribute("x", "0"));
    int y = Integer.parseInt( (String) elm.getAttribute("y", "0"));
    int w = Integer.parseInt( (String) elm.getAttribute("w", "100"));
    int h = Integer.parseInt( (String) elm.getAttribute("h", "100"));
    jj.setTitle(title);
    jj.setClosable(true);
    jj.setIconifiable(true);
    jj.setResizable(true);
    jj.setBounds(new Rectangle(x, y, w, h));
    jj.setVisible(true);
//    System.err.println("LOADED WINDOW\n\n\n\n");

  }
}