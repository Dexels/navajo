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

  public DefaultTipi() {
  }

  public void load(XMLElement elm, TipiContext context) throws TipiException {
    boolean isDefault = false;
    XMLElement defaultElm = null;
    TipiPanel myPanel = new TipiPanel();
    setContainer(myPanel);
    super.load(elm,context);
    myService = (String)elm.getAttribute("service");

    String tipiMethod = (String) elm.getAttribute("service");
    context.addTipiInstance(tipiMethod,this);


    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("table")) {
        parseTable(context,this,child);
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
    performService(context);
    if(isDefault){
      makeDefaultTipi(context,defaultElm, this);
    }
  }
  public Navajo getNavajo() {
    return myNavajo;
  }

  public String getName() {
    return myName;
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
    System.err.println("Tipi added. My type: "+getClass()+" and my name: "+getName() );
    System.err.println("Tipi added. type: "+t.getClass()+" and name: "+t.getName() );
    tipiList.add(t);
    tipiMap.put(t.getName(),t);
//    addComponent(t, context, td);
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
      return null;
    }
    /** @todo Add support for nested tipis */
    return t.getTipiByPath(rest);

  }

    private void makeDefaultTipi(TipiContext context,XMLElement elm, Tipi t){
      int columns = 1;
      columns = elm.getIntAttribute("columns", columns);
      Navajo n = t.getNavajo();
      TipiContainer c = new DefaultTipiContainer();
      try {
        c.load(elm,context);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

      TipiTableLayout layout = new TipiTableLayout();
      Container con = c.getContainer();
      con.setLayout(layout);
      Container conTipi = t.getContainer();
      conTipi.setLayout(new TipiTableLayout());
      TipiTableLayout l = (TipiTableLayout)con.getLayout();
      int current_column = 0;

      ArrayList msgs = n.getAllMessages();
      for(int i=0;i<msgs.size();i++){
        Message current = (Message)msgs.get(i);
        ArrayList props = current.getAllProperties();
        l.startRow();
        for(int j=0;j<props.size();j++){
          l.startColumn();
          current_column++;
          Property p = (Property) props.get(j);
          BasePropertyComponent bpc = new BasePropertyComponent(p);
          c.addProperty(p.getName(), bpc, context, null);
          l.endColumn();
          if(current_column > columns-1){
            current_column=0;
            l.endRow();
            l.startRow();
          }
        }
      }
      t.addTipiContainer(c, context, null);
    }


}