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

  public DefaultTipiContainer() {
    setBackground(Color.blue);
    setPreferredSize(new Dimension(100,50));
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    /**@todo Implement this com.dexels.navajo.tipi.TipiObject abstract method*/
  }

  public void addComponent(TipiComponent c){
    System.err.println("ADDING COMPONENT TO CONTAINER");
    System.err.println(">> "+c);
    this.add((JComponent)c);
    System.err.println("COunt: "+getComponentCount());
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
      Property p = n.getRootMessage().getPropertyByPath((String)propertyNames.get(i));
      current.setProperty(p);
    }

    System.err.println(">>>>>>>>>>>>>... "+n.toXml().toString());
  }

}