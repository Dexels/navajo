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

public class DefaultTipi extends DefaultTipiContainer implements Tipi{

  private String myService = "";
  private String myName = "";
  private Navajo myNavajo = null;
  private ArrayList tipiList = new ArrayList();
//  private ArrayList containerList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();
  private Map containerMap = new HashMap();
  public DefaultTipi() {
    TipiPanel myPanel = new TipiPanel();
//    setBackground(Color.white);
    setContainer(myPanel);
  }

  public void load(XMLElement elm, TipiContext context) throws TipiException {
    myName = (String)elm.getAttribute("name");
    myService = (String)elm.getAttribute("service");
  }
  public Navajo getNavajo() {
    return myNavajo;
  }

  public String getName() {
    return myName;
  }

  public void addMethod(MethodComponent m) {
    methodList.add(m);
  }
  public void performService(TipiContext context) {
    performService(context,myService);
  }

  public void performService(TipiContext context, String service) {
    if (myNavajo==null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this,service);
  }

  public void loadData(Navajo n, TipiContext tc) {
    myNavajo = n;
    for (int i = 0; i < getTipiContainerCount(); i++) {
      TipiContainer current = getTipiContainer(i);
      current.loadData(n,tc);
    }
    for (int i = 0; i < methodList.size(); i++) {
      MethodComponent current = (MethodComponent)methodList.get(i);
      current.loadData(n,tc);
    }
  }

//  public void addComponent(TipiComponent c, TipiContext context, Map td){
//      getContainer().add(c.getContainer(), td);
//  }
  public void addTipi(Tipi t, TipiContext context, Map td) {
    tipiList.add(t);
    tipiMap.put(t.getName(),t);
    addComponent(t, context, td);
  }

  public Tipi getTipi(String name) {
    return (Tipi)tipiMap.get(name);
  }
  public TipiContainer getTipiContainer(String name) {
    return (TipiContainer)containerMap.get(name);
  }
  public void addProperty(String parm1, TipiComponent parm2, TipiContext parm3, Map td) {
    throw new RuntimeException("Can not add property to tipi!");
  }


  public Tipi getTipiByPath(String path) {
    System.err.println("getTipiByPath (Screen: ): "+path);
    int s = path.indexOf("/");
    if (s==-1) {
      return getTipi(path);
    }
    if (s==0) {
      return getTipiByPath(path.substring(1));
    }

    String name = path.substring(0,s);
    String rest = path.substring(s);
    System.err.println("Name: "+name);
    System.err.println("Rest: "+rest);
    Tipi t = getTipi(name);
    if (t==null) {
      return null;
    }
    /** @todo Add support for nested tipis */
    return t.getTipiByPath(rest);

  }


}