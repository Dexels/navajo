package com.dexels.navajo.tipi.components;

import javax.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;

import java.util.*;
import java.awt.Container;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMenubar extends SwingTipiComponent {
  private JMenuBar myMenuBar;

  public TipiMenubar() {
    initContainer();
  }

  public void load(XMLElement e, TipiContext context)  throws TipiException {
    Vector v = e.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement)v.get(i);
      String name = (String)current.getAttribute("name");
      JMenu jm = new JMenu(name);
      parseMenu(jm,current,context);
      getContainer().add(jm);
    }
  }

  private void parseMenu(JMenu menu, XMLElement xe, TipiContext context) throws TipiException {
    Vector v = xe.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement)v.get(i);
      TipiMenuItem jm = new TipiMenuItem();
      jm.load(current,context);
      menu.add((JMenuItem)jm.getContainer());
    }
  }

  public Container getContainer(){
    return myMenuBar;
  }

  public Container createContainer() {
    myMenuBar = new JMenuBar();
    return myMenuBar;
  }

}