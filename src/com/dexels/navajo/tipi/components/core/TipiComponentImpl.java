package com.dexels.navajo.tipi.components.core;

import java.util.*;
import java.awt.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

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
  protected ArrayList properties = new ArrayList();
  protected TipiContext myContext = null;
  protected ArrayList myEventList = new ArrayList();
  protected Navajo myNavajo = null;
  protected Map tipiComponentMap = new HashMap();
  private ArrayList tipiComponentList = new ArrayList();
  protected String myName;
  protected String myId;
  protected TipiComponent myParent = null;
  private Map detectedExpressions = new HashMap();
  private ArrayList componentEvents = new ArrayList();
  private Map componentValues = new HashMap();
  private Map componentMethods = new HashMap();
  private Set valueList = new HashSet();
  private String className;
  private boolean hadConditionErrors = false;
//  private DefaultEventMapper myEventMapper = null;
//  private ImageIcon myIcon;
  private XMLElement myClassDef = null;
//  private ImageIcon mySelectedIcon;
  private boolean isVisibleElement = false;
  private TipiLayout currentLayout = null;
  private boolean isPropertyComponent = false;
  // This set keeps track of the component values that have actually been set.
  // Only values in this set will be stored.
  private Set valuesSet = new HashSet();
  private ArrayList myHelpers = new ArrayList();
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
    if (myContext==null) {
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

  public boolean isPropertyComponent() {
    return isPropertyComponent;
  }

  public void setPropertyComponent(boolean b) {
    isPropertyComponent = b;
  }

  public void setValue(String name, Object value) {
    setValue(name, value, this);
  }

  public void setValue(String name, Object value, TipiComponent source) {
    TipiValue tv = (TipiValue) componentValues.get(name);
    if (name.equals("constraints")) {
      setConstraints(value);
      if (getTipiParent() != null) {
        ( (TipiDataComponentImpl) getTipiParent()).refreshLayout();
      }
      return;
    }
    if (tv == null) {
      throw new UnsupportedOperationException("Setting value: " + name + " in: " + getClass() + " is not supported!");
    }
    if ("out".equals(tv.getDirection())) {
      throw new UnsupportedOperationException("Setting value: " + name + " in: " + getClass() + " is has out direction!");
    }
    String type = tv.getType();
    Class c;
    if ( (c = myContext.getCommonTypeClass(type)) != null) {
      try {
        detectedExpressions.put(name, (String) value);
        Operand o = evaluate( (String) value, source);
        // Dunno if we should do this here.. probably not..
        if (c.getName().equals("java.awt.Color")) {
          Color col = Color.decode(o.value.toString());
          setComponentValue(name, col);
          return;
        }
        if (o != null && name != null && o.value != null) {
          setComponentValue(name, o.value);
        }
        else {
          System.err.println("Null evaluation. Ignoring setComponentValue");
        }
        return;
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else if ( (c = myContext.getReservedTypeClass(type)) != null) {
      if ("selection".equals(type)) {
        if (!tv.isValidSelectionValue( (String) value)) {
          throw new RuntimeException(this.getName() + ": Invalid selection value [" + value + "] for attribute " + name + ", valid values are: " + tv.getValidSelectionValues());
        }
      }
      if (!c.isInstance(value)) {
        //System.err.println("Value class(" + value.getClass() + ") differs fromt type class(" + c +")");
      }
      setComponentValue(name, value);
    }
    else {
      setComponentValue(name, value);
      System.err.println("Attribute type not specified in CLASSDEF: " + type);
      System.err.println("Component name: "+getClass());
      System.err.println("Attribute name: "+name);
      System.err.println("Value: "+value);
      throw new RuntimeException("Attribute type not specified in CLASSDEF: " + type);
    }
  }

  public Object getValue(String name) {
    if (name.equals("constraints")) {
      return getConstraints();
    }
    return getComponentValue(name);
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
        event.load(this, xx, context);
        addTipiEvent(event);
//        System.err.println("EVENT LOADED: " + event.getEventName());
      }
    }
//    registerEvents();
  }

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws TipiException {
    setContext(context);
    String id = instance.getStringAttribute("id");
    String name = instance.getStringAttribute("name");
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
    myId = id;
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

  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException {
    String id = (String) instance.getAttribute("id");
    String defname = (String) instance.getAttribute("name");
    className = (String) classdef.getAttribute("name");
    isVisibleElement = classdef.getStringAttribute("addtocontainer", "false").equals("true");
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
      String value = element.getStringAttribute(key);
      if (value != null) {
        if (tv.getType().equals("out")) {
          throw new RuntimeException("You cannot pass the value of an 'out' direction value in to an instance or definition in the script");
        }
        setValue(key, value);
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
      componentMethods.put(methodName, tcm);
    }
  }

  public boolean isReusable() {
    return false;
  }

  public void reUse() {
    // no action
  }

  private void loadValues(XMLElement values) {
    Vector children = values.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String valueName = xx.getStringAttribute("name");
      TipiValue tv = new TipiValue();
      tv.load(xx);
      componentValues.put(valueName, tv);
      if (tv.getValue() != null && !"".equals(tv.getValue())) {
        setValue(tv.getName(), tv.getValue());
      }
      valueList.add(valueName);
    }
  }

  public String getId() {
    return myId;
  }

  public void performMethod(String methodName, TipiAction invocation) {
    TipiComponentMethod tcm = (TipiComponentMethod) componentMethods.get(methodName);
    if (tcm == null) {
      System.err.println("Could not find component method: " + methodName);
    }
    tcm.loadInstance(invocation);
    performComponentMethod(methodName, tcm);
  }

  public TipiComponentMethod getTipiComponentMethod(String methodName) {
    TipiComponentMethod tcm = (TipiComponentMethod) componentMethods.get(methodName);
    return tcm;
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth) {
  }

  public TipiComponent getTipiComponentByPath(String path) {
    if (path.equals(".")) {
      return this;
    }
    if (path.equals("..")) {
      return myParent;
    }
    if (path.startsWith("..")) {
      return myParent.getTipiComponentByPath(path.substring(3));
    }
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    int s = path.indexOf("/");
    if (s == -1) {
      if (path.equals("")) {
        return myContext.getDefaultTopLevel();
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
    return (TipiComponent) tipiComponentMap.get(s);
  }

  public TipiComponent getTipiComponent(int i) {
    return (TipiComponent) tipiComponentList.get(i);
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
    // do nothing. Override to perform extra cleanup
    ArrayList backup = (ArrayList) tipiComponentList.clone();
    for (int i = 0; i < backup.size(); i++) {
      TipiComponent current = (TipiComponent) backup.get(i);
      myContext.disposeTipiComponent(current);
    }
    tipiComponentList.clear();
    tipiComponentMap.clear();
    helperDispose();
  }

  public void removeChild(TipiComponent child) {
    if (child == null) {
      System.err.println("Null child... Can not proceed with deleting.");
      return;
    }
    if (!tipiComponentMap.containsValue(child)) {
      System.err.println("Can not dispose! No such component. I am " + getName() + " my class: " + getClass());
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
    if (td == null && getLayout() != null) {
      td = getLayout().createDefaultConstraint(tipiComponentList.size());
      if (td != null) {
        c.setConstraints(td);
      }
    }
    tipiComponentMap.put(c.getId(), c);
    tipiComponentList.add(c);
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

    /** @todo Beware: I think this means that the onInstantiate event is never called on a toplevel component */
    try {
      c.performTipiEvent("onInstantiate", c,true);
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

  public boolean performTipiEvent(String type, Object event, boolean sync) throws TipiException {
    boolean hasEventType = false;
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent) myEventList.get(i);
      if (te.isTrigger(type, myService)) {
        hasEventType = true;
//        te.performAction(event);
        if (sync) {
          te.performAction(event);
        } else {
          te.asyncPerformAction(event);
        }

      }
    }
    return hasEventType;
  }

  protected Operand evaluate(String expr, TipiComponent source) {
    return myContext.evaluate(expr, source);
  }

  public String getName() {
    return myName;
  }

  public Object getContainer() {
    return myContainer;
  }

  protected void replaceContainer(Object c) {
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
    Iterator pipo = componentValues.keySet().iterator();
    while (pipo.hasNext()) {
      String name = (String) pipo.next();
      if (!valuesSet.contains(name)) {
        System.err.println("Skipping value: " + name);
        continue;
      }
      String expr = (String) detectedExpressions.get(name);
      Object o = getComponentValue(name);
      if (expr != null) {
        IamThereforeIcanbeStored.setAttribute(name, expr);
      }
      else {
        if (o != null) {
          IamThereforeIcanbeStored.setAttribute(name, o.toString());
        }
      }
    }
    Object myc = this.getConstraints();
    if (myc != null) {
      System.err.println("Storing constraint of type: " + myc.getClass());
      IamThereforeIcanbeStored.setAttribute("constraint", myc.toString());
    }
    TipiLayout myLayout = getLayout();
    if (myLayout != null) {
      XMLElement layout = myLayout.store();
      Iterator it = tipiComponentMap.keySet().iterator();
      while (it.hasNext()) {
        TipiComponent current = (TipiComponent) tipiComponentMap.get(it.next());
        if (!myContext.isDefined(current)) {
          layout.addChild(current.store());
        }
      }
      for (int i = 0; i < myEventList.size(); i++) {
        TipiEvent current = (TipiEvent) myEventList.get(i);
        IamThereforeIcanbeStored.addChild(current.store());
      }
      IamThereforeIcanbeStored.addChild(layout);
    }
    else {
      Iterator it = tipiComponentMap.keySet().iterator();
      while (it.hasNext()) {
        TipiComponent current = (TipiComponent) tipiComponentMap.get(it.next());
        if (!myContext.isDefined(current)) {
          IamThereforeIcanbeStored.addChild(current.store());
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
    return IamThereforeIcanbeStored;
  }

  public void checkValidation(Message msg) {
    if (myContainer != null) {
      Iterator it = tipiComponentMap.keySet().iterator();
      while (it.hasNext()) {
        TipiComponent next = (TipiComponent) tipiComponentMap.get(it.next());
        next.checkValidation(msg);
      }
      hadConditionErrors = true;
      if (TipiSwingPropertyPanel.class.isInstance(myContainer)) {
        TipiSwingPropertyPanel p = (TipiSwingPropertyPanel) myContainer;
        p.checkForConditionErrors(msg);
      }
    }
  }

  public void resetComponentValidationStateByRule(String id) {
    if (myContainer != null) {
      Iterator it = tipiComponentMap.keySet().iterator();
      while (it.hasNext()) {
        TipiComponent next = (TipiComponent) tipiComponentMap.get(it.next());
        next.resetComponentValidationStateByRule(id);
      }
      //hadConditionErrors = true;
      if (TipiSwingPropertyPanel.class.isInstance(myContainer)) {
        TipiSwingPropertyPanel p = (TipiSwingPropertyPanel) myContainer;
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

  public boolean hasPath(String path) {
//    System.err.println("Checking path: "+path+" against my own assumed path: "+getPath());
    if (path.equals("*")) {
      return true;
    }
    TipiPathParser tp = new TipiPathParser(this, myContext, path);
    return tp.appliesTo(this);
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

  private void helperDispose() {
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
    addComponent(ti, context, constraints);
    if (ti instanceof TipiDataComponentImpl) {
      ( (TipiDataComponentImpl) ti).autoLoadServices(context);
    }
    return ti;
  }
  public Object evaluateExpression(String expression) throws Exception {
    return myContext.evaluateExpression(expression,this);
  }
}
