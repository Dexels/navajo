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
//    ((JTabbedPane)getContainer()).addTab("HOEI!",c);
    /** @todo STRANGE........ */
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("tipi-instance")) {
        String windowName = (String)child.getAttribute("name");
        String title = (String)child.getAttribute("title");
        Tipi t = addTipiInstance(context,null,child);
//        Tipi t = (Tipi)context.instantiateClass(child);
//        addTipi(t,context,null, child);
        JTabbedPane p = (JTabbedPane)getContainer();
        p.addTab(title, t.getContainer());
      }
    }

    super.load(elm,instance, context);
  }

}