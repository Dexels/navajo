package com.dexels.navajo.tipi.components;

import javax.swing.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;

import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMenubar extends JMenuBar {
  public TipiMenubar() {
  }

  public void load(XMLElement e, TipiContext context)  throws TipiException {
    Vector v = e.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement)v.get(i);
      String name = (String)current.getAttribute("name");
      JMenu jm = new JMenu(name);
      parseMenu(jm,current,context);
      add(jm);
    }
  }

  private void parseMenu(JMenu menu, XMLElement xe, TipiContext context) throws TipiException {
    Vector v = xe.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement)v.get(i);
      TipiMenuItem jm = new TipiMenuItem();
      jm.load(current,context);
      menu.add(jm);
    }
  }

}