package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.document.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiContainer extends TipiPanel implements TipiContainer{

  private ArrayList propertyNames = new ArrayList();
  private ArrayList properties = new ArrayList();
  private ArrayList containerList = new ArrayList();
  private String prefix;

  public DefaultTipiContainer() {
    setBackground(Color.blue);
    setPreferredSize(new Dimension(100,50));
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    prefix = (String)elm.getAttribute("prefix");
    System.err.println("colspan: " + context.getColumnAttribute("colspan"));
  }


  public void addComponent(TipiComponent c){
    this.add((JComponent)c);
  }

  public void addProperty(String name, TipiComponent comp){
    propertyNames.add(name);
    properties.add(comp);
    addComponent(comp);
  }

  public void addTipi(Tipi t) {
    throw new RuntimeException("This should not happen!");
  }
  public void addTipiContainer(TipiContainer t) {
      containerList.add(t);
      addComponent(t);
  }
  public void loadData(Navajo n) {
    System.err.println("\n\n LOADING CONTAINER!!\n");
    for (int i = 0; i < containerList.size(); i++) {
      TipiContainer current = (TipiContainer)containerList.get(i);
      current.loadData(n);
    }
    for (int i = 0; i < properties.size(); i++) {
      System.err.println("LOADING PROPERTY: "+propertyNames.get(i));
      BasePropertyComponent current = (BasePropertyComponent)properties.get(i);
      Property p;
      if(prefix != null){
        p = n.getRootMessage().getPropertyByPath(prefix + "/" + (String)propertyNames.get(i));
      }else{
        p = n.getRootMessage().getPropertyByPath((String)propertyNames.get(i));
      }
      current.setProperty(p);
    }

    System.err.println(">>>>>>>>>>>>>... "+n.toXml().toString());
  }

}