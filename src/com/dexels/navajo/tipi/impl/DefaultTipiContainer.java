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
    prefix = (String) elm.getAttribute("prefix");
  }


  public void addComponent(TipiComponent c, TipiContext context){
    this.add((JComponent)c, context);
  }

  public void addProperty(String name, TipiComponent comp, TipiContext context){
    propertyNames.add(name);
    properties.add(comp);
    addComponent(comp, context);
  }

  public void addTipi(Tipi t, TipiContext context) {
    throw new RuntimeException("This should not happen!");
  }
  public void addTipiContainer(TipiContainer t, TipiContext context) {
      containerList.add(t);
      addComponent(t, context);
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