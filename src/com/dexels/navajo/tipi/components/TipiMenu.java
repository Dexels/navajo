package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import com.dexels.navajo.tipi.impl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMenu extends SwingTipiComponent {
  private JMenu myMenu = null;

  public Container createContainer() {
    myMenu = new JMenu();
    return myMenu;
  }
  public void addToContainer(Component menu, Object item) {
//    System.err.println("Adding somethinh to a menu");
    myMenu.add((JMenuItem)menu);
  }
  public void load(XMLElement def, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    System.err.println("MenuInstantiating menu: "+instance);
//    System.err.println("menuDef: "+def);
    super.load(def,instance,context);
    Vector v = def.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement)v.get(i);
      TipiComponent tc = context.instantiateComponent(current);
      addComponent(tc,context,null);
    }
  }
  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if ("text".equals(name)) {
      myMenu.setText((String)object);
    }
    if ("mnemonic".equals(name)) {
      String ch = (String) object;
      char mn = ch.charAt(0);
      myMenu.setMnemonic(mn);
    }
  }

}