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
    setContainer(createContainer());
  }

  public void addToContainer(Component c, Object constraints) {
    ((JTabbedPane)getContainer()).addTab("HOEI!",c);
  }

//  public void addTipi(Tipi t, TipiContext context, Map td, XMLElement definition) {
//    if (t==null) {
//      throw new NullPointerException("HOly cow!");
//    }
//    String id = t.getId();
//    System.err.println("Tipi added. My type: "+getClass()+" and my name: "+getName()+"my id: "+getId());
//    System.err.println("Tipi added. type: "+t.getClass()+" and name: "+t.getName()+" id: "+id );
//    tipiList.add(t);
//    tipiMap.put(id,t);
//    String vis = (String)definition.getAttribute("visible", "true");
//    String title = (String)definition.getAttribute("title", "unknown");
//    boolean visible;
//    if(vis.equals("false")){
//      visible = false;
//    }else{
//      visible = true;
//    }
//    t.getContainer().setVisible(visible);
//  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    JTabbedPane myPanel = new JTabbedPane();
//    setContainer(myPanel);
    getContainer().setBackground(Color.cyan);
    Vector children = elm.getChildren();
    System.err.println("---------------------------->TipiTabs has " + children.size() + " children");
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("tipi-instance")) {
        String windowName = (String)child.getAttribute("name");
        String title = (String)child.getAttribute("title");
        Tipi t = (Tipi)context.instantiateClass(this,child);
        addTipi(t,context,null, child);
        JTabbedPane p = (JTabbedPane)getContainer();
        p.addTab(title, t.getContainer());
      }
    }

    super.load(elm,instance, context);
  }

}