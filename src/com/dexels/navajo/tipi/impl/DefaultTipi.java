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
  private Navajo myNavajo = null;
  private ArrayList tipiList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();
  private String myId = null;
  private TipiLayout myLayout = null;

  public DefaultTipi() {
  }

  public void load(XMLElement elm, TipiContext context) throws TipiException {
    boolean isDefault = false;
    XMLElement defaultElm = null;
    TipiPanel myPanel = new TipiPanel();
    setContainer(myPanel);
    super.load(elm,context);
    myService = (String)elm.getAttribute("service");
//    String tipiMethod = (String) elm.getAttribute("service");
    if (myService!=null) {
      context.addTipiInstance(myService,this);
    }
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("layout")) {
        TipiLayout tl = context.instantiateLayout(child);
        tl.createLayout(context,this,child,null);
        myLayout = tl;
//        parseTable(context,this,child);
      }
      else if (child.getName().equals("default")) {
        //parseTable(child, s);
        System.err.println("Default tipi found!");
        isDefault = true;
        defaultElm = child;
      }else {
//        throw new TipiException("Unexpected element found [" + child.getName() +
//                                "]. Expected 'table'");
        break;
      }
    }
    /** @todo Think of auto loading on or off */
    System.err.println("Performing service: "+myService);
    performService(context);
//    if(isDefault){
//      makeDefaultTipi(context,defaultElm, this);
//    }
  }
  public Navajo getNavajo() {
    return myNavajo;
  }

  public String getName() {
    return myName;
  }

  public Tipi getTipi(int i) {
    return (Tipi)tipiList.get(i);
  }
  public String getService() {
    return myService;
  }

  public void addMethod(MethodComponent m) {
    methodList.add(m);
  }
  public void performService(TipiContext context) throws TipiException{
    performService(context,myService);
  }

  public void performService(TipiContext context, String service) throws TipiException {
    if (myNavajo==null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this,service);
  }


  public void loadData(Navajo n, TipiContext tc) throws TipiException {
//    System.err.println("LOADING NAVAJO:  "+n.toXml());
    if (getLayout().needReCreate()) {
      getLayout().reCreateLayout(tc,this,n);
    }

    super.loadData(n,tc);
    if (n==null) {
      System.err.println("NULL NAVAJO!");
      return;
    }

    myNavajo = n;
    for (int i = 0; i < getTipiCount(); i++) {
      Tipi current = getTipi(i);
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
    if (t==null) {
      throw new NullPointerException("HOly cow!");
    }
    String id = t.getId();
    System.err.println("Tipi added. My type: "+getClass()+" and my name: "+getName()+"my id: "+getId());
    System.err.println("Tipi added. type: "+t.getClass()+" and name: "+t.getName()+" id: "+id );
    tipiList.add(t);
    tipiMap.put(id,t);
//    addComponent(t, context, td);
  }

  public String getId() {
    return myId;
  }

  public void setId(String id) {
    myId = id;
  }

  public Tipi getTipi(String name) {
    return (Tipi)tipiMap.get(name);
  }
//  public TipiContainer getTipiContainer(String name) {
//    return (TipiContainer)containerMap.get(name);
//  }
  public void addProperty(String parm1, TipiComponent parm2, TipiContext parm3, Map td) {
    throw new RuntimeException("Can not add property to tipi!");
  }
  public int getTipiCount() {
    return tipiList.size();
  }

  public Tipi getTipiByPath(String path) {
    System.err.println("Looking in: "+getClass()+" my name is: "+getName());
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
      throw new NullPointerException("Did not find Tipi: "+name+" list: "+tipiList);
//      return null;
    }
    return t.getTipiByPath(rest);
  }

  public TipiLayout getLayout() {
    return myLayout;
  }

}