package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import java.util.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiTabs extends DefaultTipi {

  private ArrayList tipiList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();


  public Container createContainer() {
    return new JTabbedPane();
  }


  public DefaultTipiTabs() {
    initContainer();
  }

  public void addToContainer(Component c, Object constraints) {
    System.err.println("\n\nCONSTRAINTS: "+constraints);
    ((JTabbedPane)getContainer()).addTab((String)constraints,c);
    /** @todo STRANGE........ */

  }
  public void setComponentValue(String name, Object object) {
    System.err.println(">>>>>>>>>>"+name);
    super.setComponentValue(name, object);
    if (name.equals("selected")) {
      String sel = (String)object;
      TipiComponent tc = getTipiComponent(sel);
      ((JTabbedPane)getContainer()).setSelectedComponent(tc.getContainer());
    }
    if (name.equals("placement")) {
      String sel = (String)object;
      setTabPlacement(sel);
//      ((JTabbedPane)getContainer()).setSelectedComponent(tc.getContainer());
    }


        /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
  }

  public void setTabPlacement(String sel) {
    int placement = -1;
    if (sel.equals("top")) {
      placement = JTabbedPane.TOP;
    }
    if (sel.equals("bottom")) {
      placement = JTabbedPane.BOTTOM;
    }
    if (sel.equals("left")) {
      placement = JTabbedPane.LEFT;
    }
    if (sel.equals("right")) {
      placement = JTabbedPane.RIGHT;
    }
    ((JTabbedPane)getContainer()).setTabPlacement(placement);
  }

  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
    return super.getComponentValue(name);
  }

//  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    Vector children = elm.getChildren();
//    for (int i = 0; i < children.size(); i++) {
//      XMLElement child = (XMLElement) children.elementAt(i);
//      if (child.getName().equals("tipi-instance")) {
//        String windowName = (String)child.getAttribute("name");
//        String title = (String)child.getAttribute("title");
//        Tipi t = addTipiInstance(context,null,child);
//        JTabbedPane p = (JTabbedPane)getContainer();
//        p.addTab(title, t.getContainer());
//      }
//    }
//
//    super.load(elm,instance, context);
//  }

}