package com.dexels.navajo.tipi;

import nanoxml.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
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

//  public void setValue(String s) {
//  }

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
        System.err.println("LOADING EVENT FROM DEF. I AM: " + getName());
        String type = xx.getStringAttribute("type");
        if (!componentEvents.contains(type)) {
          throw new RuntimeException("Invalid event type for this component: " + type+". This component allows: "+componentEvents);
        }
        TipiEvent event = new TipiEvent();
        event.load(xx, context);
        addTipiEvent(event);
      }
    }

    registerEvents(getContainer());
  }


  public void registerEvents(Component c) {
    defaultRegisterEvents(c);
  }

  protected void defaultRegisterEvents(Component c) {
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent current = (TipiEvent)myEventList.get(i);
      defaultRegisterEvent(c,current);
    }

  }

  private void defaultRegisterEvent(Component c, TipiEvent te) {
    switch (te.getType()) {
      case TipiEvent.TYPE_ONACTIONPERFORMED:
        break;
      case TipiEvent.TYPE_ONMOUSE_ENTERED:
        break;
    }

    if (te.getType()==TipiEvent.TYPE_ONACTIONPERFORMED) {
      if (!AbstractButton.class.isInstance(c)) {
        throw new RuntimeException("Can not fire actionperformed event from class: "+c.getClass());
      }
      AbstractButton myButton = (AbstractButton)c;
      myButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
          try {
            performAllEvents(TipiEvent.TYPE_ONACTIONPERFORMED);
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }
    if (te.getType()==TipiEvent.TYPE_ONMOUSE_ENTERED) {
      c.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent e) {
          try {
            performAllEvents(TipiEvent.TYPE_ONMOUSE_ENTERED);
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }
    if (te.getType()==TipiEvent.TYPE_ONMOUSE_EXITED) {
      c.addMouseListener(new MouseAdapter() {
         public void mouseExited(MouseEvent e) {
          try {
            performAllEvents(TipiEvent.TYPE_ONMOUSE_EXITED);
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }

  }

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

  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException {
//    System.err.println(">>>>>>>>>>>>>>: "+classdef);
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
    }
  }

  public void loadStartValues(XMLElement element) {
    Iterator it = componentValues.keySet().iterator();
    while (it.hasNext()) {
      String key = (String) it.next();
      TipiValue tv = (TipiValue) componentValues.get(key);
      System.err.println("Getting key: " + key);
      if (!tv.getType().equals("string")) {
        System.err.println("Skipping non-string type value on instantiation");
        continue;
      }
      String value = element.getStringAttribute(key);
      System.err.println("With value: " + value + " from instance: " + element);
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
//      System.err.println("Adding event: "+xx);
      componentEvents.add(eventName);
    }
  }

  private void loadValues(XMLElement values) {
    Vector children = values.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String valueName = xx.getStringAttribute("name");
      String valueDirection = xx.getStringAttribute("direction");
      String valueType = xx.getStringAttribute("type");
//      System.err.println("Adding value: "+xx);
      TipiValue tv = new TipiValue(valueName, valueType, valueDirection);
      componentValues.put(valueName, tv);
    }
  }

  public String getId() {
    return myId;
  }

//  public void addProperty(String name, BasePropertyComponent bpc,TipiContext context, Map contraints) {
//    propertyNames.add(name);
//    properties.add(bpc);
//    addComponent(bpc,context,contraints);
//  }
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

  public TipiComponent addComponentInstance(TipiContext context, XMLElement inst, Object constraints) throws TipiException {
    TipiComponent ti = (TipiComponent) (context.instantiateComponent(inst));
    addComponent(ti, context, constraints);
    return ti;
  }

//  public void addToContainer(Component c) {
//
//  }

  public void addComponent(TipiBase c, TipiContext context, Object td) {
    tipiComponentMap.put(c.getId(), c);
    addToContainer(c.getOuterContainer(), td);
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
    if (myNavajo != null) {
      System.err.println("Getting Navajo: " + myNavajo.toXml().toString());
    }
    else {
      System.err.println("Getting Navajo NULL");
    }
    return myNavajo;
  }

  public void addTipiEvent(TipiEvent te) {
    myEventList.add(te);
  }

  public void performTipiEvent(int type, String source) throws TipiException {

    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent) myEventList.get(i);
      if (te.getType() == type && te.getSource().equals(source)) {
        te.performAction(getNavajo(), source, getContext());
      }
    }
  }

  private void performEvent(TipiEvent te) throws TipiException {
    te.performAction(getNavajo(), te.getSource(), getContext());
  }

  public void performAllEvents(int type) throws TipiException {
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent) myEventList.get(i);
      if (te.getType() == type) {
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
    throw new UnsupportedOperationException("Whoops! This class did not override getComponentValue!");
  }

}