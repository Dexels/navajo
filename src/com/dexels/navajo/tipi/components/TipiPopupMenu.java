package com.dexels.navajo.tipi.components;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import javax.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import java.util.*;
import java.awt.Container;

public class TipiPopupMenu
    extends SwingTipiComponent {

  private JPopupMenu myMenu;

  public TipiPopupMenu(){
    initContainer();
  }

  public void load(XMLElement e, TipiContext context) throws TipiException {
    Vector v = e.getChildren();
    parseMenu(e, context);
  }



  private void parseMenu(XMLElement xe, TipiContext context) throws TipiException {
    Vector v = xe.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement) v.get(i);
      TipiMenuItem jm = new TipiMenuItem();
      jm.load(current, context);
      getContainer().add((JMenuItem)jm.getContainer());
    }
  }

  public Container createContainer() {
    myMenu = new JPopupMenu();
    return myMenu;
  }

  public Container getContainer(){
    return myMenu;
  }
}