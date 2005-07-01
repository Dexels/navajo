package com.dexels.navajo.tipi.components.core;

import java.util.*;
import java.awt.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.event.*;
import javax.swing.*;
import java.lang.reflect.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiComponentImpl
    implements ConditionErrorHandler, TipiEventListener, TipiComponent, TipiLink {
  public abstract Object createContainer();

  private Object myContainer = null;
  private Object myConstraints;
  private String myService;
  private boolean isStudioElement = false;
//  protected ArrayList propertyNames = new ArrayList();
  protected TipiContext myContext = null;
  protected Navajo myNavajo = null;
  protected String myName;
  protected String myId;
  protected TipiComponent myParent = null;
  private String className;

  protected final Map tipiComponentMap = new HashMap();
  protected final ArrayList properties = new ArrayList();
  protected final ArrayList myEventList = new ArrayList();


  private final ArrayList tipiComponentList = new ArrayList();
  private final Map detectedExpressions = new HashMap();
  private final ArrayList componentEvents = new ArrayList();
  private final Map componentValues = new HashMap();
  private final Map componentMethods = new HashMap();
  private final Set valueList = new HashSet();
  private final Set valuesSet = new HashSet();
  private final ArrayList myHelpers = new ArrayList();

  private boolean hadConditionErrors = false;
  private XMLElement myClassDef = null;
//  private ImageIcon mySelectedIcon;
  private boolean isVisibleElement = false;
  private boolean isToplevel = false;
  private TipiLayout currentLayout = null;
  private boolean isPropertyComponent = false;
  private boolean isDisposed = false;
  public boolean isTransient=false;

  public void removeFromContainer(Object c) {
    throw new UnsupportedOperationException("Can not remove from container of class: " + getClass());
  }

  public void addToContainer(Object c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: " + getClass());
  }

  public Object getContainerLayout() {
    // override to be useful
    return null;
  }

  public void clearConditionErrors() {
//    throw new UnsupportedOperationException("clearConditionErrors: Dont know what to do");
  }

  public void setContainerLayout(Object layout) {
    // override to be useful
  }

  public boolean isGridShowing() {
    if (TipiDesignable.class.isInstance(getContainer())) {
      TipiDesignable d = (TipiDesignable) getContainer();
      return d.isGridShowing();
    }
    else {
      return false;
    }
  }

  public void setHighlighted(boolean value) {
    if (TipiDesignable.class.isInstance(getContainer())) {
      TipiDesignable d = (TipiDesignable) getContainer();
      d.setHighlighted(value);
      d.repaint();
    }
  }

  public boolean isHighlighted() {
    if (TipiDesignable.class.isInstance(getContainer())) {
      TipiDesignable d = (TipiDesignable) getContainer();
      return d.isHighlighted();
    }
    else {
      return false;
    }
  }

  public void showGrid(boolean value) {
    if (TipiDesignable.class.isInstance(getContainer())) {
      TipiDesignable d = (TipiDesignable) getContainer();
      d.showGrid(value);
      d.repaint();
    }
  }

  public TipiContext getContext() {
    if (myContext == null) {
      throw new RuntimeException("TipiComponent without context. This is not allowed");
    }
    return myContext;
  }

  public void initContainer() {
    if (getContainer() == null) {
      setContainer(createContainer());
    }
  }

  public Set getPossibleValues() {
    return valueList;
  }

  public void deregisterEvent(TipiEvent e) {
    this.removeTipiEvent(e);
    helperDeregisterEvent(e);
  }

  public void setName(String name) {
    myName = name;
  }

  public void setContext(TipiContext tc) {
    myContext = tc;
  }
  public void setTransient(boolean b) {
    isTransient = b;
  }

  public boolean isTransient() {
    return isTransient;
  }

  public boolean isPropertyComponent() {
    return isPropertyComponent;
  }

  public boolean isValueSet(String name) {
    return detectedExpressions.containsKey(name);
  }

  public void unSetValue(String name) {
    setValue(name,getTipiValue(name).getDefaultValue(),this,true,null);
    detectedExpressions.remove(name);
  }

  public void setPropertyComponent(boolean b) {
    isPropertyComponent = b;
  }

  public void setValue(String name, Object value) {
    setValue(name, value, this,null);
  }

  public void setValue(String name, Object value, TipiComponent source, TipiEvent event) {
    setValue(name,value,source,false,event);
  }

  private final void setValue(String name, Object value, TipiComponent source, boolean defaultValue,TipiEvent event) {
      setValue(name,""+value,value,source,defaultValue,event);
  }  
  
  private final void setValue(String name, String expression, Object value, TipiComponent source, boolean defaultValue,TipiEvent event) {
//    System.err.println("Setting value with name: "+name+" in component: "+this.getPath());
//    System.err.println("Suspected value: "+value + " is of class: " + value.getClass());
    TipiValue tv = (TipiValue) componentValues.get(name);
//    if (name.equals("constraints")) {
//      setConstraints(value);
//      if (getTipiParent() != null) {
//        ( (TipiDataComponentImpl) getTipiParent()).refreshLayout();
//      }
//      return;
//    }
    if (tv == null) {
      throw new UnsupportedOperationException("Setting value: " + name + " in: " + getClass() + " is not supported!");
    }
    if ("out".equals(tv.getDirection())) {
      throw new UnsupportedOperationException("Setting value: " + name + " in: " + getClass() + " is has out direction!");
    }
    String type = tv.getType();
//    System.err.println("Type in TipiValue is: " + type);
//    Class c;
    if ( (myContext.isValidType(type))) {
      try {
        if (!defaultValue) {
          detectedExpressions.put(name, expression);
        }
        if ("selection".equals(type)) {
          if (!tv.isValidSelectionValue( (String) value)) {
            throw new RuntimeException(this.getName() + ": Invalid selection value [" + value + "] for attribute " + name + ", valid values are: " + tv.getValidSelectionValues());
          } else {
//            System.err.println("NOT PARSING VALUE FOR SELECTION ATTRIBUTE: "+name+" / "+value);
            setComponentValue(name, value);
            return;
          }
        }
//        if ("object".equals(type)) {
        
        //  Bit of a bold change;
        //System.err.println("Setting type: "+name +" to class: "+value.getClass()+" val: "+value);
          setComponentValue(name, value);
//          return;
//        }
//        else {
//
//          Operand o = evaluate(  value.toString(), source,event);
//          if (o != null && name != null && o.value != null) {
//            setComponentValue(name, o.value);
//          }
// 
//        }
        return;
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
//    else if ( (c = myContext.getReservedTypeClass(type)) != null) {
//      if ("selection".equals(type)) {
//        if (!tv.isValidSelectionValue( (String) value)) {
//          throw new RuntimeException(this.getName() + ": Invalid selection value [" + value + "] for attribute " + name + ", valid values are: " + tv.getValidSelectionValues());
//        }
//      }
//      setComponentValue(name, value);
//    }
    else {
      setComponentValue(name, value);
      System.err.println("Attribute type not specified in CLASSDEF: " + type);
      System.err.println("Component name: " + getClass());
      System.err.println("Attribute name: " + name);
      System.err.println("Value: " + value);
      throw new RuntimeException("Attribute type not specified in CLASSDEF: " + type);
    }
  }

  public Object getValue(String name) {
    TipiValue tv = (TipiValue) componentValues.get(name);
    if (tv == null) {
      throw new UnsupportedOperationException("Getting value: " + name + " in: " + getClass() + " is not supported!");
    }
    return getComponentValue(name);
  }

  public String getStringValue(String name) {
//    System.err.println("Getting string value: "+name+" my PAth: "+getPath()+" my class: "+getClass());
    TipiValue tv = (TipiValue) componentValues.get(name);
    if (tv == null) {
      throw new UnsupportedOperationException("Getting value: " + name + " in: " + getClass() + " is not supported!");
    }
//    System.err.println("Getting type: "+tv.getType());
//    String type = tv.getType();
//    Class c;
    Object obj = getComponentValue(name);
    if (obj!=null) {
//      System.err.println("Object type: "+obj.getClass());
      String result = myContext.toString(this,tv.getType(),obj);
      if (result!=null) {
        return result;
      }

      return obj.toString();
    }
    return null;
  }

  public String getExpression(String name) {
    return (String)detectedExpressions.get(name);
  }


  public TipiValue getTipiValue(String name) {
    return (TipiValue) componentValues.get(name);
  }

  /**
   * Loads an event definition from the component definition
   */
  public void loadEventsDefinition(TipiContext context, XMLElement definition, XMLElement classDef) throws TipiException {
    Vector defChildren = definition.getChildren();
    for (int i = 0; i < defChildren.size(); i++) {
      XMLElement xx = (XMLElement) defChildren.get(i);
      if (xx.getName().equals("event")) {
        String type = xx.getStringAttribute("type");
        if (!componentEvents.contains(type)) {
          throw new RuntimeException("Invalid event type for component with name " + myName + ": " + type + ". This component allows: " + componentEvents);
        }
        TipiEvent event = new TipiEvent();
//        System.err.println(">>>>>>>>>>>>>> "+classDef);
        XMLElement eventDef = getEventDefFromClassDef(classDef,xx.getStringAttribute("type"));
        event.init(eventDef);
        event.load(this, xx, context);
        addTipiEvent(event);
//        System.err.println("EVENT LOADED: " + event.getEventName());
      }
    }
//    registerEvents();
  }

  private XMLElement getEventDefFromClassDef(XMLElement def, String eventName) {
  Vector v = def.getChildren();
   for (int i = 0; i < v.size(); i++) {
     XMLElement child = (XMLElement)v.get(i);
//     System.err.println("Aap: "+child.getName()+"  === " +eventName);
     if ("events".equals(child.getName())) {
       Vector eventChildren = child.getChildren();
       for (int j = 0; j < eventChildren.size(); j++) {
         XMLElement eventChild = (XMLElement)eventChildren.get(j);
//          System.err.println("Noot: "+eventChild.getName());
//          System.err.println("Mies "+eventChild.getStringAttribute("name"));
          if (eventName.equals(eventChild.getStringAttribute("name"))) {
            return eventChild;
          }

       }
      }
   }
   return null;
  }


  public void loadMethodDefinitions(TipiContext context, XMLElement definition, XMLElement classDef) throws TipiException {
    Vector defChildren = definition.getChildren();
    for (int i = 0; i < defChildren.size(); i++) {
      XMLElement xx = (XMLElement) defChildren.get(i);
      if (xx.getName().equals("method")) {
        String type = xx.getStringAttribute("type");
        TipiComponentMethod tcm = new TipiComponentMethod();
        tcm.load(xx);
//        addTipiEvent(event);
//        System.err.println("EVENT LOADED: " + event.getEventName());
      }
    }
  }

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws TipiException {
    setContext(context);
    String id = instance.getStringAttribute("id");
    String name = instance.getStringAttribute("name");
    boolean studioelt = instance.getBooleanAttribute("studioelement","true","false",false);
    boolean studioelt2 = def.getBooleanAttribute("studioelement","true","false",false);
    setStudioElement(studioelt || studioelt2);
    setName(name);
    if (id == null || id.equals("")) {
      myId = name;
//      throw new RuntimeException("Component has no id at: "+instance.getLineNr()+" current class:"+getClass()+"\n>"+instance.toString());
    }
    else {
      myId = id;
    }
  }

  public void setId(String id) {
//    System.err.println("CHANGING ID. FROM: "+myId+" to: "+id+"......... VERIFYING PARENT. ");
    if (getTipiParent()!=null) {
      getTipiParent().updateId(this,myId, id);
    }
    myId = id;
  }

  public void updateId(TipiComponent tc, String oldId, String id) {
    if(!tipiComponentMap.containsValue(tc)) {
      System.err.println("!!!!!!   Can not update id: Component not found.");
    }
    if(!tipiComponentList.contains(tc)) {
      System.err.println("!!!!!!   Can not update id: Component not found in list.");
    }
    if(!tipiComponentMap.containsKey(oldId)) {
      System.err.println("!!!!!!   Can not update id: Component not found in map.");
    }
    tipiComponentMap.remove(oldId);
    tipiComponentMap.put(id,tc);

  }

  public ArrayList getDefinedEvents() {
    ArrayList eventDefs = new ArrayList();
    if (myClassDef != null) {
      Vector kids = myClassDef.getChildren();
      for (int i = 0; i < kids.size(); i++) {
        XMLElement kid = (XMLElement) kids.get(i);
        if (kid.getName().equals("events")) {
          Vector events = kid.getChildren();
          for (int j = 0; j < events.size(); j++) {
            XMLElement ev = (XMLElement) events.get(j);
            String name = ev.getStringAttribute("name");
            eventDefs.add(name);
          }
        }
      }
      return eventDefs;
    }
    else {
      return null;
    }
  }

  public Map getClassDefValues() {
    return componentValues;
  }

  public boolean isVisibleElement() {
    return isVisibleElement;
  }

  public boolean isTopLevel() {
    return isToplevel;
  }

  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException {
    String id = (String) instance.getAttribute("id");
    String defname = (String) instance.getAttribute("name");
    className = (String) classdef.getAttribute("name");
    isVisibleElement = classdef.getStringAttribute("addtocontainer", "false").equals("true");
    isToplevel = classdef.getStringAttribute("toplevel", "false").equals("true");
    myService = instance.getStringAttribute("service", null);
    myClassDef = classdef;
    if (id == null || "".equals(id)) {
      id = defname;
    }
    myId = id;
    Vector children = classdef.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      if ("events".equals(xx.getName())) {
        loadEvents(xx, classdef);
      }
      if ("values".equals(xx.getName())) {
        loadValues(xx,null);
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
      String value = element.getStringAttribute(key);
      if (value != null) {
        if (tv.getType().equals("out")) {
          throw new RuntimeException("You cannot pass the value of an 'out' direction value in to an instance or definition in the script");
        }
        Operand o = evaluate(  value.toString(), this,null);
      if (o != null &&  o.value != null) {
          setValue(key, value,o.value,this,false,null);
//          setComponentValue(key, o.value);
      } else {
          setValue(key, value);
      }
      }
    }
  }

  /**
   * Loads all the allowed event from the classdefinition
   */
  private final  void loadEvents(XMLElement events, XMLElement classdef) {
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
  private final void loadMethods(XMLElement events) {
    Vector children = events.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String methodName = xx.getStringAttribute("name");
      TipiComponentMethod tcm = new TipiComponentMethod();
      tcm.load(xx);
      componentMethods.put(methodName, tcm);
    }
  }

  public boolean isReusable() {
    return false;
  }

  public void reUse() {
    // no action
  }

  private final void loadValues(XMLElement values,TipiEvent event) {
    Vector children = values.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String valueName = xx.getStringAttribute("name");
      TipiValue tv = new TipiValue();
      tv.load(xx);
      componentValues.put(valueName, tv);
      if (tv.getValue() != null && !"".equals(tv.getValue())) {
        try {
          setValue(tv.getName(), evaluate(tv.getValue(), this, event).value, this, true, event);
        }
        catch (Throwable ex) {
//          System.err.println("Expression failed. setting directly");
      setValue(tv.getName(), tv.getValue(), this, true, event);
         }
      }
      valueList.add(valueName);
    }
  }

  public String getId() {
    return myId;
  }

  public void performMethod(String methodName, TipiAction invocation, TipiEvent event) throws TipiBreakException {
    TipiComponentMethod tcm = (TipiComponentMethod) componentMethods.get(methodName);
    if (tcm == null) {
      System.err.println("Could not find component method: " + methodName);
    } else {
    tcm.loadInstance(invocation);
    performComponentMethod(methodName, tcm, event);
    }
  }

  public TipiComponentMethod getTipiComponentMethod(String methodName) {
    TipiComponentMethod tcm = (TipiComponentMethod) componentMethods.get(methodName);
    return tcm;
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
  }

  public TipiComponent getTipiComponentByPath(String path) {
//    System.err.println("TIpiCOmponentImpl: Looking for: "+path);
    if (path.equals(".")) {
      return this;
    }
    if (path.equals("..")) {
      return myParent;
    }
    if (path.startsWith("./")) {
      return getTipiComponentByPath(path.substring(2));
    }
    if (path.startsWith("../")) {
      return myParent.getTipiComponentByPath(path.substring(3));
    }
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    int s = path.indexOf("/");
    if (s == -1) {
      if (path.equals("")) {
        return (TipiComponent) myContext.getDefaultTopLevel();
      }
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

  public void setLayout(TipiLayout tl) {
    currentLayout = tl;
  }

  public TipiLayout getLayout() {
    return currentLayout;
  }

  public TipiComponent getTipiComponent(String s) {
    if (tipiComponentMap.size()!=tipiComponentList.size()) {
//      System.err.println("PROBLEMS: Mapsize: "+tipiComponentMap.size()+" LIST: "+tipiComponentList.size());
    }
//    System.err.println("getting component. # of components: "+tipiComponentMap.size());
    return (TipiComponent) tipiComponentMap.get(s);
  }

  public TipiComponent getTipiComponent(int i) {
    if (tipiComponentMap.size()!=tipiComponentList.size()) {
//     System.err.println("PROBLEMS: Mapsize: "+tipiComponentMap.size()+" LIST: "+tipiComponentList.size());
    }
    return (TipiComponent) tipiComponentList.get(i);
  }

  public TipiComponent getChildByContainer(Object container) {
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent child = getTipiComponent(i);
      if (child!=null) {
        if (container == child.getContainer()) {
          return child;
        }
      }
    }
    return null;
  }

  public ArrayList getChildComponentIdList() {
    ArrayList l = new ArrayList();
    for (int i = 0; i < tipiComponentList.size(); i++) {
      TipiComponent current = getTipiComponent(i);
      l.add(current.getId());
    }
    return l;
  }

  public void disposeComponent() {
    removeAllChildren();
    tipiComponentList.clear();
    tipiComponentMap.clear();
    helperDispose();
    isDisposed = true;
  }

  public boolean isDisposed() {
    return isDisposed;
  }


  public void removeAllChildren() {
    ArrayList backup = (ArrayList) tipiComponentList.clone();
    for (int i = 0; i < backup.size(); i++) {
      TipiComponent current = (TipiComponent) backup.get(i);
//      myContext.disposeTipiComponent(current);
      current.disposeComponent();
    }
    tipiComponentList.clear();
    tipiComponentMap.clear();
  }

/**
 * Similar to remove all children, but it will only remove the components instantiated using
 * the InstantiateTipi action, not components added 'by class'
 */
  public void removeInstantiatedChildren() {
    ArrayList backup = (ArrayList) tipiComponentList.clone();
    for (int i = 0; i < backup.size(); i++) {
      TipiComponent current = (TipiComponent) backup.get(i);
      if (!myContext.isDefined(current)) {
        if (!current.isTransient()) {
          current.disposeComponent();
          current.removeChild(current);
        }
      }
//      myContext.disposeTipiComponent(current);
    }
//    tipiComponentList.clear();
//    tipiComponentMap.clear();
  }


  public void removeChild(TipiComponent child) {
    if (child == null) {
      System.err.println("Null child... Can not proceed with deleting.");
      return;
    }
    if (!tipiComponentMap.containsValue(child)) {
      System.err.println("Can not dispose! No such component. I am " + getId() + " my class: " + getClass()+" child id: "+child.getId());
//      System.err.println("Ids: "+tipiComponentMap.keySet().toString()+" listsize: "+tipiComponentList.size());
//      Thread.dumpStack();
      return;
    }
    Object c = child.getContainer();
    if (c != null) {
      removeFromContainer(c);
    }
//    getContainer().repaint();
    if (PropertyComponent.class.isInstance(child)) {
      PropertyComponent pc = (PropertyComponent) child;
      properties.remove(child);
//      propertyNames.remove(pc.getPropertyName());
    }
    tipiComponentMap.remove(child.getId());
    tipiComponentList.remove(child);
    childDisposed();
  }

  public void setParent(TipiComponent tc) {
    myParent = tc;
  }

  public TipiComponent getTipiParent() {
    return myParent;
  }
  public void addComponent(TipiComponent c, TipiContext context, Object td) {
    addComponent(c,-1,context,td);
  }
  public void addComponent(final TipiComponent c, int index, TipiContext context, Object td) {
    if (td == null && getLayout() != null) {
      td = getLayout().createDefaultConstraint(tipiComponentList.size());
      if (td != null) {
        c.setConstraints(td);
      }
    }

    /** @todo Fix following scenario:
     * What should happen when a component is added with the same id? */
if (tipiComponentMap.containsKey(c.getId())) {
  System.err.println("===================================\n   WARNING: Adding component which is already present.\n   =========================================");
  System.err.println("id: "+c.getId());
}
    tipiComponentMap.put(c.getId(), c);
    if (index<0) {
      tipiComponentList.add(c);
    } else {
      tipiComponentList.add(index,c);
    }
    c.setParent(this);
//    if (c.getContainer() != null && !java.awt.Window.class.isInstance(c.getContainer())) {
//    if (getContainer()!=null && c.getContainer()!=null ) {
    if (getContainer() == null && c.isVisibleElement()) {
      System.err.println("THIS IS WEIRD: COMPONENT: " + c.getPath() + " has no container, but it is visible.");
    }
    if (getContainer() != null && c.isVisibleElement()) {
      addToContainer(c.getContainer(), td);
      addedToParent();
    }
    else {
    }
    if (c.isPropertyComponent()) {
      properties.add(c);
    }
    if (isStudioElement) {
      c.setStudioElement(true);
    }
    /** @todo Beware: I think this means that the onInstantiate event is never called on a toplevel component */

    // I placed this in a invokeAndWait clause, which is concepually not correct, especially when
    // if we are using a non-swing implementation. Need to fix.
    // placing it in this thread gives _occasional_ freezes on startup.
    // As far as I know, it only happens when the performTipiEvent is called from the main thread

                  try {
                    c.performTipiEvent("onInstantiate", null, false);
                  }
                  catch (TipiException ex) {
                    ex.printStackTrace();
                  }
            }

  public Navajo getNavajo() {
    return myNavajo;
  }

  public Navajo getNearestNavajo() {
    if (myNavajo != null) {
      return myNavajo;
    }
    if (myParent == null) {
      return null;
    }
    return myParent.getNearestNavajo();
  }

  public void setConstraints(Object constraints) {
    myConstraints = constraints;
  }

  public Object getConstraints() {
    return myConstraints;
  }

  public void addTipiEvent(TipiEvent te) {
    myEventList.add(te);
    helperRegisterEvent(te);
  }

  public void removeTipiEvent(TipiEvent e) {
    myEventList.remove(e);
    helperDeregisterEvent(e);
  }

  public void refreshParent() {
    if (getTipiParent() == null) {
      System.err.println("Can not refresh parent: No parent present!");
      return;
    }
    if (TipiDataComponent.class.isInstance(getTipiParent())) {
      ( (TipiDataComponent) getTipiParent()).refreshLayout();
    }
    else {
      System.err.println("Can not refresh parent: Parent is not a tipi. THIS is a bit weird, actually.");
    }
  }

  public boolean performTipiEvent(String type, Map event, boolean sync) throws TipiException {
    boolean hasEventType = false;
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent) myEventList.get(i);
      if (te.isTrigger(type, myService)) {
        hasEventType = true;
//        te.performAction(event);
//        System.err.println("MAAAP: "+event);
        if (sync) {
          te.performAction(this, event);
        }
        else {
          te.asyncPerformAction(this, event);
        }
      }
    }
//    System.err.println("Performing event type: "+type+" has event? "+hasEventType);
    return hasEventType;
  }

  public void eventStarted(TipiExecutable te, Object event) {
  }

  public void eventFinished(TipiExecutable te, Object event) {
  }

  protected Operand evaluate(String expr, TipiComponent source, TipiEvent event) {
//    System.err.println("%%%%%%%%%%%%%%5 EVALUATING: "+expr);
    return myContext.evaluate(expr, source, event);
  }

  public String getName() {
    return myName;
  }

  public Object getContainer() {
    return myContainer;
  }

  public void replaceContainer(Object c) {
    myContainer = c;
  }

  public void setContainer(Object c) {
    if (getContainer() == null) {
      replaceContainer(c);
    }
  }

  protected void setComponentValue(String name, Object object) {
    valuesSet.add(name);
    helperSetComponentValue(name, object);
  }

  protected Object getComponentValue(String name) {
    return helperGetComponentValue(name);
  }

  public void setCursor(int cursorid) {
  }

  public XMLElement store() {

    XMLElement IamThereforeIcanbeStored = new CaseSensitiveXMLElement();
    IamThereforeIcanbeStored.setName("component-instance");
    if (myName != null) {
      IamThereforeIcanbeStored.setAttribute("name", myName);
    }
    if (myId != null) {
      IamThereforeIcanbeStored.setAttribute("id", myId);
    }
    if (className != null) {
      IamThereforeIcanbeStored.setAttribute("class", className);
    }
//    if (className != null && myName!=null) {
//      System.err.println("THERE IS BOTH A CLASS AND A NAME SET. THIS IS EVIL, BUT I AM GETTING USED TO IT");
//    }
 //    Iterator pipo = componentValues.keySet().iterator();
//    while (pipo.hasNext()) {
//      String name = (String) pipo.next();
//      if (!valuesSet.contains(name)) {
//        System.err.println("Skipping value: " + name);
//        continue;
//      }
//      String expr = (String) detectedExpressions.get(name);
//      Object o = getComponentValue(name);
//      if (expr != null) {
//        IamThereforeIcanbeStored.setAttribute(name, expr);
//      }
//      else {
//        if (o != null) {
//          IamThereforeIcanbeStored.setAttribute(name, o.toString());
//        }
//      }
//    }

    Iterator it2 = detectedExpressions.keySet().iterator();
    while (it2.hasNext()) {
      String name = (String)it2.next();
      String expression = (String)detectedExpressions.get(name);
      if (expression!=null && !"".equals(expression)) {
        IamThereforeIcanbeStored.setAttribute(name,expression);
      }
    }
    Object myc = this.getConstraints();
    if (myc != null) {
//      System.err.println("Storing constraint of type: " + myc.getClass());
      IamThereforeIcanbeStored.setAttribute("constraint", myc.toString());
    }
    TipiLayout myLayout = getLayout();
    if (myLayout != null) {
      XMLElement layout = myLayout.store();
//      Iterator it = tipiComponentMap.keySet().iterator();
      Iterator it = tipiComponentList.iterator();
      while (it.hasNext()) {
        TipiComponent current = (TipiComponent) it.next();
        if (!myContext.isDefined(current)) {
          if (!current.isTransient()) {
            layout.addChild(current.store());

          }
        }
      }
      for (int i = 0; i < myEventList.size(); i++) {
        TipiEvent current = (TipiEvent) myEventList.get(i);
        IamThereforeIcanbeStored.addChild(current.store());
      }
      IamThereforeIcanbeStored.addChild(layout);
    }
    else {
      Iterator it = tipiComponentList.iterator();
      while (it.hasNext()) {
        TipiComponent current = (TipiComponent)it.next();
        if (!myContext.isDefined(current)) {
          if (!current.isTransient()) {
          IamThereforeIcanbeStored.addChild(current.store());
          }
        }
      }
      for (int i = 0; i < myEventList.size(); i++) {
        TipiEvent current = (TipiEvent) myEventList.get(i);
        IamThereforeIcanbeStored.addChild(current.store());
      }
      if (myConstraints != null) {
//      System.err.println("My contraints: " + myConstraints.toString() + " cLASS:" + myConstraints.getClass());
      }
    }
//    System.err.println("PResent: "+IamThereforeIcanbeStored.toString());

    return IamThereforeIcanbeStored;
  }

  public void checkValidation(Message msg) {
    if (myContainer != null) {
      Iterator it = tipiComponentList.iterator();
      while (it.hasNext()) {
        TipiComponent next = (TipiComponent)it.next();
        next.checkValidation(msg);
      }
      hadConditionErrors = true;
//      System.err.println("CHECKING VALIDATION: ");
//      msg.write(System.err);
      /** @todo Rewrite check for propertycomponent flag in classdef */
      if (PropertyValidatable.class.isInstance(myContainer)) {
        PropertyValidatable p = (PropertyValidatable) myContainer;
        p.checkForConditionErrors(msg);
      }
    }
  }

  public void resetComponentValidationStateByRule(String id) {
    if (myContainer != null) {
      Iterator it = tipiComponentList.iterator();
      while (it.hasNext()) {
        TipiComponent next = (TipiComponent)it.next();
        next.resetComponentValidationStateByRule(id);
      }
      //hadConditionErrors = true;
      if (PropertyValidatable.class.isInstance(myContainer)) {
        PropertyValidatable p = (PropertyValidatable) myContainer;
        p.resetComponentValidationStateByRule(id);
      }
    }
  }

  public boolean hasConditionErrors() {
    return hadConditionErrors;
  }

  public int getChildCount() {
    return tipiComponentList.size();
  }

  public boolean hasPath(String path, TipiEvent event) {
//    System.err.println("Checking path: "+path+" against my own assumed path: "+getPath());
    if (path.equals("*")) {
      return true;
    }
    TipiComponent tc = (TipiComponent) myContext.parse(this, "component", path,event);
//    TipiPathParser tp = new TipiPathParser(this, myContext, path);
    return tc == this;
  }

  public String getPath() {
    if (this instanceof TipiDataComponent) {
      return getPath("tipi:/");
    }
    else {
      return getPath("component:/");
    }
  }

  public String getPath(String typedef) {
//    System.err.println("getPath, in TipiComponent.");
    if (getTipiParent() == null) {
      return typedef + "/" + getId();
    }
    else {
      return getTipiParent().getPath(typedef) + "/" + getId();
    }
  }

  public AttributeRef getAttributeRef(String name) {
    return new AttributeRef(this, name);
  }

  public void setChildIndex(TipiComponent child, int index) {
    if (!tipiComponentList.contains(child)) {
      return;
    }
    tipiComponentList.remove(child);
    tipiComponentList.add(index,child);
  }

  public int getIndexOfComponent(TipiComponent source) {
    return tipiComponentList.indexOf(source);
  }


  public ArrayList getEventList() {
    return myEventList;
  }

  public void tipiLoaded() {
  }

  public void childDisposed() {
  }

  public void componentInstantiated() {
  }

  public boolean isStudioElement() {
    return isStudioElement;
  }

  public void setStudioElement(boolean b) {
    isStudioElement = b;
  }

  public void addHelper(TipiHelper th) {
    myHelpers.add(th);
  }

  public void removeHelper(TipiHelper th) {
    myHelpers.add(th);
  }

  protected void helperSetComponentValue(String name, Object object) {
    for (int i = 0; i < myHelpers.size(); i++) {
      TipiHelper current = (TipiHelper) myHelpers.get(i);
      current.setComponentValue(name, object);
    }
  }

  protected Object helperGetComponentValue(String name) {
    for (int i = 0; i < myHelpers.size(); i++) {
      TipiHelper current = (TipiHelper) myHelpers.get(i);
      Object o = current.getComponentValue(name);
      if (o != null) {
        return o;
      }
    }
    return null;
  }

  protected void helperRegisterEvent(TipiEvent te) {
    for (int i = 0; i < myHelpers.size(); i++) {
      TipiHelper current = (TipiHelper) myHelpers.get(i);
      current.registerEvent(te);
    }
  }

  protected void helperDeregisterEvent(TipiEvent te) {
    for (int i = 0; i < myHelpers.size(); i++) {
      TipiHelper current = (TipiHelper) myHelpers.get(i);
      current.deregisterEvent(te);
    }
  }

  private final void helperDispose() {
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent current = (TipiEvent) myEventList.get(i);
      helperDeregisterEvent(current);
    }
  }

  protected void addedToParent() {
//    System.err.println("Added to Parent");
  }

  public int getIndex(TipiComponent node) {
    return tipiComponentList.indexOf(node);
  }

  public TipiComponent addComponentInstance(TipiContext context, XMLElement inst, Object constraints) throws TipiException {
    TipiComponent ti = (TipiComponent) (context.instantiateComponent(inst));
    ti.setConstraints(constraints);
    if (isStudioElement) {
      ti.setStudioElement(true);
    }
    addComponent(ti, context, constraints);
    if (ti instanceof TipiDataComponentImpl) {
      ( (TipiDataComponentImpl) ti).autoLoadServices(context,null);
    }
    return ti;
  }

  // This method actually implements the TipiLink interface

  private TipiEvent lastEvent = null;
  public void setCurrentEvent(TipiEvent event) {
//    if (event!=null) {
//      System.err.println("Setting last event to: "+event.getEventName());
//    } else {
//      System.err.println("Setting last event to: nullevent");
//    }
//    System.err.println("SETTINg EVENT");
//    Thread.dumpStack();
    lastEvent = event;
  }

  public Object evaluateExpression(String expression) throws Exception {
    return myContext.evaluateExpression(expression, this,lastEvent);
  }
}
