package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.impl.*;
import javax.swing.*;
import javax.swing.event.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiComponent
    implements TipiBase {

  public abstract Container createContainer();

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
  protected TipiComponent myParent = null;

  private ArrayList componentEvents = new ArrayList();
  private Map componentValues = new HashMap();
  private Map componentMethods = new HashMap();

  private TipiEventMapper myEventMapper = new DefaultEventMapper();

  public TipiContext getContext() {
    return myContext;
  }

  protected void initContainer() {
    if (getContainer()==null) {
      setContainer(createContainer());
    }

  }

  public void setEventMapper(TipiEventMapper tm) {
    myEventMapper = tm;
  }

  public TipiEventMapper getEventMapper() {
    return myEventMapper;
  }

  public void setName(String name) {
    myName = name;
  }

  public void setContext(TipiContext tc) {
    myContext = tc;
  }

  public void setValue(String name, Object value) {
    TipiValue tv = (TipiValue) componentValues.get(name);
    System.err.println("MAP: " + componentValues);
    if (tv == null) {
      throw new UnsupportedOperationException("Setting value: " + name + " in: " + getClass() + " is not supported!");
    }
    if ("out".equals(tv.getDirection())) {
      throw new UnsupportedOperationException("Setting value: " + name + " in: " + getClass() + " is has out direction!");
    }
    setComponentValue(name, value);
  }

  public Object getValue(String name) {
    return null;
  }

  /**
   * Loads an event definition from the component definition
   */

  protected void loadEventsDefinition(TipiContext context, XMLElement definition, XMLElement classDef) throws TipiException {
    Vector defChildren = definition.getChildren();
    for (int i = 0; i < defChildren.size(); i++) {
      XMLElement xx = (XMLElement) defChildren.get(i);
      if (xx.getName().equals("event")) {
        String type = xx.getStringAttribute("type");
        if (!componentEvents.contains(type)) {
          throw new RuntimeException("Invalid event type for component with name "+myName+": " + type+". This component allows: "+componentEvents);
        }
        TipiEvent event = new TipiEvent();
        event.load(this,xx, context);
        addTipiEvent(event);
      }
    }

    registerEvents();
  }

  public abstract void registerEvents();
  public void load(XMLElement def, XMLElement instance, TipiContext context) throws TipiException {
    setContext(context);
    String id = instance.getStringAttribute("id");
    String name = instance.getStringAttribute("name");
    setName(name);

    if (id == null || id.equals("")) {
      myId = name;
    }
    else {
      myId = id;
    }
  }

  public void setId(String id) {
    myId = id;
  }

  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException {
    String id = (String) instance.getAttribute("id");
    String defname = (String) instance.getAttribute("name");
    if (id == null || "".equals(id)) {
      id = defname;
    }
    myId = id;
    Vector children = classdef.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      if ("events".equals(xx.getName())) {
        loadEvents(xx);
      }
      if ("values".equals(xx.getName())) {
        loadValues(xx);
      }
      if ("methods".equals(xx.getName())) {
        loadMethods(xx);
      }
    }
  }

  public void loadStartValues(XMLElement element) {
    Iterator it = componentValues.keySet().iterator();
    while (it.hasNext()) {
      String key = (String) it.next();
      TipiValue tv = (TipiValue) componentValues.get(key);
//      System.err.println("Getting key: " + key);
//      if (!tv.getType().equals("string")) {
//        System.err.println("Skipping non-string type value on instantiation. Type encountered: "+tv.getType());
//        continue;
//      }
      String value = element.getStringAttribute(key);
//      System.err.println("With value: " + value + " from instance: " + element);
      if (value != null) {
        if (tv.getType().equals("out")) {
          throw new RuntimeException("You cannot pass the value of an 'out' direction value in to an instance or definition in the script");
        }

        setComponentValue(key, value);
      }
}

  }

  /**
   * Loads all the allowed event from the classdefinition
   */

  private void loadEvents(XMLElement events) {
    Vector children = events.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String eventName = xx.getStringAttribute("name");
      componentEvents.add(eventName);
    }
  }

  /**
   * Loads all the allowed methods from the classdefinition
   */

  private void loadMethods(XMLElement events) {
    Vector children = events.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String methodName = xx.getStringAttribute("name");
      TipiComponentMethod tcm = new TipiComponentMethod();
      tcm.load(xx);
      componentMethods.put(methodName,tcm);
    }
  }


  private void loadValues(XMLElement values) {
    Vector children = values.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String valueName = xx.getStringAttribute("name");
//      String valueDirection = xx.getStringAttribute("direction");
//      String valueType = xx.getStringAttribute("type");
//      System.err.println("Adding value: "+xx);
      TipiValue tv = new TipiValue();
      tv.load(xx);
//      valueName, valueType, valueDirection);
      componentValues.put(valueName, tv);
    }
  }

  public String getId() {
    return myId;
  }

  public void performMethod(String methodName, XMLElement invocation) {
//    XMLElement invocation = (XMLElement)componentMethods.get(methodName);
    if (invocation==null) {
      throw new RuntimeException("No such method in tipi!");
    }

    if (!invocation.getName().equals("action")) {
      throw new IllegalArgumentException("I always thought that a TipiComponent method would be called with an invocation called action, and not: "+invocation.getName());
    }
    if (!"performTipiMethod".equals(invocation.getStringAttribute("type"))) {
      throw new IllegalArgumentException("I always thought that a TipiComponent method would be called with an action invocation with type: performTipiMethod, and not: "+invocation.getStringAttribute("type"));
    }
    TipiComponentMethod tcm = (TipiComponentMethod)componentMethods.get(methodName);
    if (tcm.checkFormat(methodName, invocation)) {
      tcm.loadInstance(invocation);
      performComponentMethod(methodName,invocation,tcm);
    }
  }

  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
    System.err.println("Component: "+getClass()+" has no support for components, so it cannot perform: "+name);
  }
//  public void performTipiMethod(String name, XMLElement invocation) {
//    TipiComponentMethod tcm = (TipiComponentMethod)componentMethods.get(name);
//    if (tcm.checkFormat(name, invocation)) {
//      performComponentMethod(name,invocation,tcm);
//    }
//  }

  public TipiComponent getTipiComponentByPath(String path) {

    if (path.equals(".")) {
      return this;
    }
    if (path.startsWith("..")) {
      return myParent.getTipiComponentByPath(path.substring(3));
    }

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

  public TipiComponent addComponentInstance(TipiContext context, XMLElement inst, Object constraints) throws TipiException {
    TipiComponent ti = (TipiComponent) (context.instantiateComponent(inst));
    addComponent(ti, context, constraints);
    return ti;
  }

//  public void addToContainer(Component c) {
//
//  }

  public void setParent(TipiComponent tc) {
    myParent = tc;
  }

  public TipiComponent getParent() {
    return myParent;
  }

  public void addComponent(TipiComponent c, TipiContext context, Object td) {
    //System.err.println("Adding component: "+c.getName()+" to: "+getName());
    tipiComponentMap.put(c.getId(), c);
    c.setParent(this);
    if(c.getContainer() != null){
      addToContainer(c.getContainer(), td);
    }
    if (PropertyComponent.class.isInstance(c)) {
      properties.add(c);
      propertyNames.add(c.getName());
    }

  }

//  public void addComponentInstance(TipiContext context, XMLElement instance, Map constraints) throws TipiException {
//    TipiComponent tc = context.instantiateComponent(instance);
//    addComponent(tc,context,constraints);
//  }

  public Navajo getNavajo() {
//    if (myNavajo != null) {
//      System.err.println("Getting Navajo: " + myNavajo.toXml().toString());
//    }
    return myNavajo;
  }

  public Navajo getNearestNavajo() {
    if (myNavajo!=null) {
      return myNavajo;
    }
    if (myParent==null) {
      return null;
    }
    return myParent.getNearestNavajo();


  }

  public void addTipiEvent(TipiEvent te) {
    myEventList.add(te);
  }

  public void performTipiEvent(int type, String source) throws TipiException {

    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent) myEventList.get(i);
      if (te.getType() == type && te.getSource().equals(source)) {
        te.performAction(getNavajo(), source, getContext(),null);
      }
    }
  }

  private void performEvent(TipiEvent te,Object event) throws TipiException {
    te.performAction(getNavajo(), te.getSource(), getContext(),event);
  }

  public void performAllEvents(int type,Object event) throws TipiException {
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent) myEventList.get(i);
      if (te.getType() == type) {
        System.err.println("Performing event # " +i+" of "+myEventList.size()+" -> "+te.getType() );
        performEvent(te,event);
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
    if (myOuterContainer == null) {
      return getContainer();
    }
    return myOuterContainer;
  }

  public void setContainer(Container c) {
    if (getContainer() == null) {
      replaceContainer(c);
    }
  }

  public void setOuterContainer(Container c) {
    myOuterContainer = c;
  }

  public void setComponentValue(String name, Object object) {
    throw new UnsupportedOperationException("Whoops! This class: " + getClass() + " did not override setComponentValue!");
  }

  public Object getComponentValue(String name) {
    return null;
  }

}