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


  private ArrayList componentEvents = new ArrayList();
  private Map componentValues = new HashMap();

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
  }

  public void setValue(String name, Object value) {
    TipiValue tv = (TipiValue)componentValues.get(name);
    if (tv==null) {
      throw new UnsupportedOperationException("Setting value: "+name+" in: "+getClass()+" is not supported!");
    }
    if ("out".equals(tv.getDirection())) {
      throw new UnsupportedOperationException("Setting value: "+name+" in: "+getClass()+" is has out direction!");
    }
    setComponentValue(name,value);
  }

  public Object getValue(String name) {
    return null;
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
//    System.err.println(">>>>>>>>>>>>>>: "+classdef);
    String id = (String) instance.getAttribute("id");
    String defname = (String) instance.getAttribute("name");
    if (id==null || "".equals(id)) {
      id = defname;
    }
    myId = id;
    Vector children = classdef.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement)children.get(i);
      System.err.println("Instantiating: "+classdef);
      System.err.println("Instantiating: "+xx);
      if ("events".equals(xx.getName())) {
        loadEvents(xx);
      }
      if ("valuess".equals(xx.getName())) {
        loadValues(xx);
      }

    }

  }

  private void loadEvents(XMLElement events) {
    Vector children = events.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement)children.get(i);
      String eventName = xx.getStringAttribute("name");
      System.err.println("Adding event: "+xx);
      componentEvents.add(eventName);
    }
  }

  private void loadValues(XMLElement values) {
    Vector children = values.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement)children.get(i);
      String valueName = xx.getStringAttribute("name");
      String valueDirection = xx.getStringAttribute("direction");
      String valueType = xx.getStringAttribute("type");
      System.err.println("Adding value: "+xx);
      TipiValue tv = new TipiValue(valueName,valueType,valueDirection);
      componentValues.put(valueName,tv);
    }
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

  public void setComponentValue(String name, Object object) {
    throw new UnsupportedOperationException("Whoops! This class did not override setComponentValue!");
  }
  public Object getComponentValue(String name) {
    throw new UnsupportedOperationException("Whoops! This class did not override getComponentValue!");
  }

}