package com.dexels.navajo.tipi.components;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.tipi.tipixml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMenubar extends SwingTipiComponent {
  private TipiSwingMenuBar myMenuBar;

  public TipiMenubar() {
    initContainer();
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context)  throws TipiException {
    super.load(definition,instance,context);
    Vector v = definition.getChildren();
//    System.err.println("Number of menus in menubar: "+v.size());
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement)v.get(i);
      TipiComponent tc = context.instantiateComponent(current);
      addComponent(tc,context,null);
    }
//    System.err.println("Menubar instantiated. countainer");
  }

  public Container getContainer(){
    return myMenuBar;
  }

  public Container createContainer() {
    myMenuBar = new TipiSwingMenuBar(this);
    return myMenuBar;
  }
  public void addToContainer(Component item, Object constraints) {
//    System.err.println("Adding somethinh to a menubar");
    myMenuBar.add((TipiSwingMenu)item);
  }

}