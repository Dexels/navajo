package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipi extends TipiPanel implements Tipi{

  private String myService = "";
  private String myName = "";
  private Navajo myNavajo = null;
  private ArrayList tipiList = new ArrayList();
  private ArrayList containerList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  public DefaultTipi() {
//    setBackground(Color.white);
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    myName = (String)elm.getAttribute("name");
    myService = (String)elm.getAttribute("service");
  }

  public Navajo getNavajo() {
    return myNavajo;
  }

  public void addMethod(MethodComponent m) {
    System.err.println("ADDING METHOD!!!!!!");
    methodList.add(m);
  }
  public void performService(TipiContext context) {
    if (myNavajo==null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this,myService);
  }

  public void loadData(Navajo n, TipiContext tc) {
    myNavajo = n;
    for (int i = 0; i < containerList.size(); i++) {
      TipiContainer current = (TipiContainer)containerList.get(i);
      current.loadData(n,tc);
    }
    for (int i = 0; i < methodList.size(); i++) {
      MethodComponent current = (MethodComponent)methodList.get(i);
      current.loadData(n,tc);
    }
  }

  public void addComponent(TipiComponent c, TipiContext context, Map td){
      this.add((JComponent)c, td);
  }
  public void addTipi(Tipi t, TipiContext context, Map td) {
    tipiList.add(t);
    addComponent(t, context, td);
  }
  public void addTipiContainer(TipiContainer t, TipiContext context, Map td) {
    containerList.add(t);
    addComponent(t, context, td);
  }
  public void addProperty(String parm1, TipiComponent parm2, TipiContext parm3, Map td) {
    throw new RuntimeException("Can not add property to tipi!");
  }
}