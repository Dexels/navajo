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
import com.dexels.navajo.parser.*;
import com.dexels.navajo.client.ConditionErrorHandler;
import javax.swing.tree.TreeNode;
import tipi.*;
import com.dexels.navajo.tipi.components.swing.*;
import java.awt.Point;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiComponent
    implements ConditionErrorHandler, TreeNode, TipiEventListener {
  public abstract Container createContainer();

  private Container myContainer = null;
  private Object myConstraints;
  private Container myOuterContainer = null;
  private String myService;
  protected ArrayList propertyNames = new ArrayList();
  protected ArrayList properties = new ArrayList();
  protected TipiContext myContext = null;
  protected ArrayList myEventList = new ArrayList();
  protected Navajo myNavajo = null;
  private Map tipiComponentMap = new HashMap();
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
  private TipiEventMapper myEventMapper = new DefaultEventMapper();
  private ImageIcon myIcon;
  private XMLElement myClassDef = null;
  private ImageIcon mySelectedIcon;
  private boolean isVisibleElement = false;
  private TipiLayout currentLayout = null;
  private int gridsize = 10;

  public abstract void addToContainer(Component c, Object constraints);
  public abstract void removeFromContainer(Component c);


  public void setHighlighted(boolean value){
    if(Designable.class.isInstance(getContainer())){
      Designable d = (Designable)getContainer();
      d.setHighlighted(value);
      getContainer().repaint();
    }

  }

  public boolean isHighlighted(){
    if(Designable.class.isInstance(getContainer())){
      Designable d = (Designable)getContainer();
      return d.isHighlighted();
    }else{
      return false;
    }

  }

  public void showGrid(boolean value){
    if(Designable.class.isInstance(getContainer())){
      Designable d = (Designable)getContainer();
      d.showGrid(value);
      getContainer().repaint();
    }
  }

  public boolean isGridShowing(){
    if(Designable.class.isInstance(getContainer())){
      Designable d = (Designable)getContainer();
      return d.isGridShowing();
    }else{
      return false;
    }
  }

  public void highLight(Component c, Graphics g){
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3.0f));
    Rectangle r = c.getBounds();
    g2.drawRect(r.x+1, r.y+1, r.width-2, r.height-2);
    g2.setStroke(new BasicStroke(1.0f));

//      Graphics2D g2 = (Graphics2D)g;
//      g2.setColor(Color.red);
//      g2.setStroke(new BasicStroke(3.0f));
//      Rectangle r = getContainer().getBounds();
//      Insets insets = getContainer().getInsets();
//      g2.drawRect(insets.left, insets.top, c.getWidth()-insets.left - insets.right, c.getHeight()-insets.top - insets.bottom);
//      g2.setStroke(new BasicStroke(1.0f));

  }

  public void paintGrid(Component c, Graphics g){
    Color old = g.getColor();
    Rectangle r = c.getBounds();
    g.setColor(Color.gray);
    for(int xpos = r.x;xpos<=r.width;xpos+=gridsize){
      g.drawLine(xpos,r.y,xpos,r.height);
    }
    for(int ypos = r.y;ypos<=r.height;ypos+=gridsize){
      g.drawLine(r.x,ypos,r.width,ypos);
    }
    g.setColor(old);
  }

  public TipiContext getContext() {
    return myContext;
  }

  protected void initContainer() {
    if (getContainer() == null) {
      setContainer(createContainer());
    }
  }

  public Set getPossibleValues() {
    return valueList;
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
    setValue(name, value, this);
  }

  public void setValue(String name, Object value, TipiComponent source) {

    TipiValue tv = (TipiValue) componentValues.get(name);
    if (name.equals("constraints")) {
      setConstraints(value);
      if (getTipiParent()!=null) {
        ((DefaultTipi)getTipiParent()).refreshLayout();
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
        Operand o = evaluate((String)value, source);
//        if (!type.equals("string") && !type.equals("integer") && !type.equals("boolean")) {
//          System.err.println("Evaluating name: "+name+", because it has type: "+type);
//          System.err.println("Return type: "+o.type);
//          System.err.println("Return value: "+o.value);
//          System.err.println("Class type: "+o.value.getClass());
//        }
//
//
        // Dunno if we should do this here.. probably not..
        if(c.getName().equals("java.awt.Color")){
          System.err.println("Decoding: " + o.value.toString());
          Color col = Color.decode(o.value.toString());
          setComponentValue(name, col);
          return;
        }
        setComponentValue(name, o.value);
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
      throw new RuntimeException("Attribute type not specified in CLASSDEF: " + type);
    }
  }

  public Object getValue(String name) {
    if (name.equals("constraints")) {
      return getConstraints();
    }
    return getComponentValue(name);
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
          throw new RuntimeException("Invalid event type for component with name " + myName + ": " + type + ". This component allows: " + componentEvents);
        }
        TipiEvent event = new TipiEvent();
        event.load(this, xx, context);
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
//      throw new RuntimeException("Component has no id at: "+instance.getLineNr()+" current class:"+getClass()+"\n>"+instance.toString());
    }
    else {
      myId = id;
    }
  }

  public void setId(String id) {
    myId = id;
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
    isVisibleElement = classdef.getStringAttribute("addtocontainer","false").equals("true");
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
//    XMLElement invocation = (XMLElement)componentMethods.get(methodName);
//    if (invocation == null) {
//      throw new RuntimeException("No such method in tipi!");
//    }
//    if (!invocation.getName().equals("action")) {
//      throw new IllegalArgumentException("I always thought that a TipiComponent method would be called with an invocation called action, and not: " + invocation.getName());
//    }
//    if (!"performTipiMethod".equals(invocation.getStringAttribute("type"))) {
//      throw new IllegalArgumentException("I always thought that a TipiComponent method would be called with an action invocation with type: performTipiMethod, and not: " + invocation.getStringAttribute("type"));
//    }
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

  protected void performComponentMethod(String name,TipiComponentMethod compMeth) {
  }

  public TipiComponent getTipiComponentByPath(String path) {
//    System.err.println("Getting tipi component: "+path);
    if (path.equals(".")) {
      return this;
    }
    if (path.equals("..")) {
      return myParent;
    }
    if (path.startsWith("..")) {
//      System.err.println("Getting path from parent: "+path.substring(3));
      return myParent.getTipiComponentByPath(path.substring(3));
    }
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    int s = path.indexOf("/");
    if (s == -1) {
//      System.err.println("Ok, I am the parent. Getting: "+path);
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

  public void disposeComponent() {
    // do nothing. Override to perform extra cleanup
//    Iterator it = tipiComponentMap.keySet().iterator();
    ArrayList backup = (ArrayList) tipiComponentList.clone();
    for (int i = 0; i < backup.size(); i++) {
      TipiComponent current = (TipiComponent) backup.get(i);
      TipiContext.getInstance().disposeTipiComponent(current);
    }
    tipiComponentList.clear();
    tipiComponentMap.clear();
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
    Container c = child.getContainer();
    if (c != null) {
      removeFromContainer(c);
    }
//    getContainer().repaint();
    if (PropertyComponent.class.isInstance(child)) {
      PropertyComponent pc = (PropertyComponent)child;
      properties.remove(child);
      propertyNames.remove(pc.getPropertyName());
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
    tipiComponentMap.put(c.getId(), c);
    tipiComponentList.add(c);
    c.setParent(this);
//    if (c.getContainer() != null && !java.awt.Window.class.isInstance(c.getContainer())) {
//    if (getContainer()!=null && c.getContainer()!=null ) {
    if (getContainer()==null && c.isVisibleElement()) {
      System.err.println("THIS IS WEIRD: COMPONENT: "+c.getPath()+" has no container, but it is visible.");
      // Invisible container component baby!
    }
    if (getContainer()!=null && c.isVisibleElement()) {
      addToContainer(c.getContainer(), td);
    }
//    else
//    {
//      System.err.println("Ignoring invisible element: "+c.getId());
//    }
    if (PropertyComponent.class.isInstance(c)) {
      properties.add(c);
      propertyNames.add(((PropertyComponent)c).getPropertyName());
    }
    try {
      c.performTipiEvent("onInstantiate", c);
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
  }

  public boolean performTipiEvent(String type, Object event) throws TipiException {
    boolean hasEventType = false;
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent) myEventList.get(i);
      if (te.isTrigger(type, myService)) {
        hasEventType = true;
        te.performAction(this, getContext(), event);
      }
      //System.err.println("Performing event # " +i+" of "+myEventList.size()+" -> "+ te.getSource());
    }
    return hasEventType;
  }

  protected Operand evaluate(String expr, TipiComponent source) {
    return myContext.evaluate(expr,source);
  }



  public String getName() {
    return myName;
  }

  public Container getContainer() {
    return myContainer;
  }

  public void replaceContainer(Container c) {
    myContainer = c;
//    c.getLayout().s
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

  protected void setComponentValue(String name, Object object) {
//    throw new UnsupportedOperationException("Whoops! This class: " + getClass() + " did not override setComponentValue!");
  }

  protected Object getComponentValue(String name) {
    return null;
  }

  public void setCursor(int cursorid) {
    if (getContainer() != null) {
      getContainer().setCursor(Cursor.getPredefinedCursor(cursorid));
    }
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
      if (PropertyPanel.class.isInstance(myContainer)) {
        PropertyPanel p = (PropertyPanel) myContainer;
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
      if (PropertyPanel.class.isInstance(myContainer)) {
        PropertyPanel p = (PropertyPanel) myContainer;
        p.resetComponentValidationStateByRule(id);
      }
    }
  }

  public boolean hasConditionErrors() {
    return hadConditionErrors;
  }

  public TreeNode getChildAt(int childIndex) {
//    System.err.println("Getting child: at nr: " + childIndex);
    return (TreeNode) tipiComponentList.get(childIndex);
  }

  public int getChildCount() {
//    System.err.println("Getting childcount: "+tipiComponentList.size());
    return tipiComponentList.size();
  }

  public TreeNode getParent() {
    return getTipiParent();
  }

  public int getIndex(TreeNode node) {
    if (getTipiParent() != null) {
      System.err.println("Returning index: " + getTipiParent().getIndex(this));
      return getTipiParent().getIndex(this);
    }
    else {
      return -1;
    }
  }

  public boolean getAllowsChildren() {
    return true;
  }

  public boolean isLeaf() {
    return getChildCount() == 0;
  }

  public Enumeration children() {
    return new Vector(tipiComponentList).elements();
  }

  public ImageIcon getIcon() {
    return myIcon;
  }

  public ImageIcon getSelectedIcon() {
    return mySelectedIcon;
  }


  public String toString() {
    // This is a bit annoying sometimes...
    if (this instanceof Tipi) {
      myIcon = new ImageIcon(MainApplication.class.getResource("container.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("container_selected.gif"));
    }
    else {
      myIcon = new ImageIcon(MainApplication.class.getResource("component.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("component_selected.gif"));
    }
    if (this instanceof DefaultTipiScreen || this instanceof DefaultTipiFrame) {
      myIcon = new ImageIcon(MainApplication.class.getResource("root.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("root_selected.gif"));
    }
    if (getId() == null) {
      return getName();
    }
    if (getId().equals("")) {
      return getName();
    }
    return getId();
  }

//  public void setContainerVisible(boolean b) {
//  }

  public boolean hasPath(String path) {
    System.err.println("Checking path: "+path+" against my own assumed path: "+getPath());
    if (path.equals("*")) {
      return true;
    }
    TipiPathParser tp = new TipiPathParser(this,myContext,path);
    return tp.appliesTo(this);
  }


  public String getPath() {
    if (this instanceof Tipi) {
      return getPath("tipi:/");
    }
    else {
      return getPath("component:/");
    }
  }

  String getPath(String typedef) {
    if (getTipiParent() == null) {
      return typedef + "/" + getId();
    }
    else {
      return getTipiParent().getPath(typedef) + "/" + getId();
    }
  }

  public AttributeRef getAttributeRef(String name) {
    return new AttributeRef(this,name);
  }

  public void tipiLoaded() {

  }

  public void childDisposed() {

  }

  public void componentInstantiated() {

  }
}
