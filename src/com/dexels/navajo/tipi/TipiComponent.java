package com.dexels.navajo.tipi;

import nanoxml.*;
import java.util.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.*;
import javax.swing.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiComponent implements TipiBase {

  private Container myContainer = null;
  private Container myOuterContainer = null;
  protected ArrayList propertyNames = new ArrayList();
  protected ArrayList properties = new ArrayList();
  protected TipiContext myContext = null;
  protected ArrayList myEventList = new ArrayList();
  protected Navajo myNavajo = null;
  private Map tipiComponentMap = new HashMap();
  protected String myName;
  protected String myId;
  public TipiContext getContext() {
    return myContext;
  }

  public void setName(String name) {
    myName = name;
  }
  public void setContext(TipiContext tc) {
    myContext = tc;
  }

  public void setValue(String s) {
    System.err.println("Setting value of some component....Should be overridden: "+s);
  }

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws TipiException{
    String id = instance.getStringAttribute("id");
    String name = instance.getStringAttribute("name");
    setName(name);

    if (id==null || id.equals("")) {
      myId = name;
    } else {
      myId = id;
    }
  }

  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException{
    String id = (String) instance.getAttribute("id");
    String defname = (String) instance.getAttribute("name");
    if (id==null || "".equals(id)) {
      id = defname;
    }
    myId = id;

  }

  public String getId() {
    return myId;
  }
  public void addProperty(String name, BasePropertyComponent bpc,TipiContext context, Map contraints) {
    propertyNames.add(name);
    properties.add(bpc);
    addComponent(bpc,context,contraints);
  }
  public TipiComponent getTipiComponentByPath(String path) {
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    int s = path.indexOf("/");
    if (s == -1) {
      return getTipiComponent(path);
    }
    else {
      String name = path.substring(0, s);
      String rest = path.substring(s);

      TipiComponent t = getTipiComponent(name);
      if (t == null) {
        throw new NullPointerException("Did not find Tipi: " + name);
      }
      return t.getTipiComponentByPath(rest);
    }
  }

  public TipiComponent getTipiComponent(String s) {
    return (TipiComponent) tipiComponentMap.get(s);
  }
  public TipiComponent addComponentInstance(TipiContext context, XMLElement inst, Map constraints) throws TipiException {
    TipiComponent ti = (TipiComponent) (context.instantiateComponent(inst));
    addComponent(ti,context,constraints);
    return ti;
  }

//  public void addToContainer(Component c) {
//
//  }

  public void addComponent(TipiBase c, TipiContext context, Map td) {
    tipiComponentMap.put(c.getId(),c);
    addToContainer(c.getOuterContainer(),td);
    Date d;
  }

//  public void addComponentInstance(TipiContext context, XMLElement instance, Map constraints) throws TipiException {
//    TipiComponent tc = context.instantiateComponent(instance);
//    addComponent(tc,context,constraints);
//  }

  public Navajo getNavajo() {
    if(myNavajo != null){
      System.err.println("Getting Navajo: " + myNavajo.toXml().toString());
    }else{
      System.err.println("Getting Navajo NULL");
    }
    return myNavajo;
  }

  public void addTipiEvent(TipiEvent te) {
   myEventList.add(te);
 }

 public void performTipiEvent(int type, String source){

     for(int i=0;i<myEventList.size();i++){
       TipiEvent te = (TipiEvent)myEventList.get(i);
       if(te.getType() == type && te.getSource().equals(source)){
         te.performAction(getNavajo(), source, getContext());
       }
     }
   }

   public void performEvent(TipiEvent te) {
      te.performAction(getNavajo(),te.getSource(),getContext());
    }

    public void performAllEvents(int type) {
      for (int i = 0; i < myEventList.size(); i++) {
        TipiEvent te = (TipiEvent)myEventList.get(i);
        if (te.getType()==type) {
          performEvent(te);
        }
      }
    }

  public String getName() {
    return myName;
  }

  public Container getContainer() {
    return myContainer;
  }
  public void replaceContainer(Container c) {
    myContainer = c;
  }

  public Container getOuterContainer() {
    if (myOuterContainer==null) {
      return getContainer();
    }
    return myOuterContainer;
  }
  public void setContainer(Container c) {
    if (getContainer()==null) {
      replaceContainer(c);
    }
  }

  public void setOuterContainer(Container c) {
    myOuterContainer = c;
  }

  public void setComponentValue(String name, Object component) {
    throw new UnsupportedOperationException("Whoops! This class did not override setComponentValue!");
  }
  public Object getComponentValue(String name) {
    throw new UnsupportedOperationException("Whoops! This class did not override getComponentValue!");
  }

}