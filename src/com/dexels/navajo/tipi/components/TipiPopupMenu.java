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
import java.awt.*;
import com.dexels.navajo.tipi.components.swing.*;

public class TipiPopupMenu
    extends SwingTipiComponent {

  private JPopupMenu myMenu;

  public void removeFromContainer(Component c) {
    myMenu.remove(c);
  }


  public Container createContainer() {
    myMenu = new JPopupMenu();
    TipiHelper th = new SwingTipiHelper();
    th.initHelper(this);
    addHelper(th);
    return myMenu;
  }

  public Container getContainer(){
    return myMenu;
  }



  public void addToContainer(Component menu, Object item) {
    myMenu.add(menu);
  }
//  public void load(XMLElement def, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    super.load(def,instance,context);
//    Vector v = def.getChildren();
//    for (int i = 0; i < v.size(); i++) {
//      XMLElement current = (XMLElement)v.get(i);
//      TipiComponent tc = context.instantiateComponent(current);
//      addComponent(tc,context,null);
//    }
//  }
}