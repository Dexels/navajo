package com.dexels.navajo.tipi;

import java.io.*;
import java.net.*;
import java.util.*;
//import java.awt.*;
//import javax.swing.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.components.core.*;
//import com.dexels.navajo.tipi.components.swingimpl.*;
//import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.*;

//import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiContext
    implements ResponseListener, StudioListener, ActivityController {
//  private static TipiContext instance;
  protected Map tipiMap = new HashMap();
  protected Map tipiServiceMap = new HashMap();
  protected Map tipiInstanceMap = new HashMap();
  protected Map tipiComponentMap = new HashMap();
  protected Map tipiClassMap = new HashMap();
  protected Map tipiClassDefMap = new HashMap();
  protected Map tipiActionDefMap = new HashMap();
  protected Map commonTypesMap = new HashMap();
  protected Map reservedTypesMap = new HashMap();
  private ArrayList includeList = new ArrayList();
  private TipiErrorHandler eHandler;
  private String errorHandler;
  protected ArrayList rootPaneList = new ArrayList();
  private ArrayList screenList = new ArrayList();
//  private TipiComponent currentComponent;
  private TipiActionManager myActionManager = new TipiActionManager();
  private ArrayList myTipiStructureListeners = new ArrayList();
  private ArrayList myNavajoTemplateListeners = new ArrayList();
  private XMLElement clientConfig = null;
  private boolean studioMode = false;
  private ArrayList myTipiDefinitionListeners = new ArrayList();
  private final Map navajoTemplateMap = new HashMap();
  protected final TipiThreadPool myThreadPool;
  protected TipiComponent topScreen = null;
  protected List myActivityListeners = Collections.synchronizedList(new ArrayList());
  protected List myThreadsToServer = Collections.synchronizedList(new ArrayList());
  private int maxToServer = 1;
  public TipiContext() {
    myThreadPool = new TipiThreadPool(this);
  }

  public void handleException(Exception e) {
    if (eHandler != null) {
      eHandler.showError(e);
    }
  }

  public void setStudioMode(boolean b) {
    studioMode = b;
  }

  public abstract void setSplash(Object s);

//  public void setToplevel(RootPaneContainer tl) {
//    myTopLevel = tl;
//  }
  public void parseFile(String location) throws IOException, XMLParseException, TipiException {
    parseStream(new FileInputStream(location), location);
  }

  public void parseURL(URL location) throws IOException, XMLParseException,
      TipiException {
    parseStream(location.openStream(), location.toString());
  }

  public Map getTipiClassDefMap() {
    return tipiClassDefMap;
  }

  public Map getTipiDefinitionMap() {
    return tipiMap;
  }

  private void clearResources() {
    tipiMap = new HashMap();
    tipiServiceMap = new HashMap();
    tipiInstanceMap = new HashMap();
    tipiComponentMap = new HashMap();
    tipiClassMap = new HashMap();
    tipiClassDefMap = new HashMap();
    commonTypesMap.clear();
    reservedTypesMap.clear();
    clearTopScreen();
    includeList.clear();
    eHandler = null;
    errorHandler = null;
    rootPaneList = new ArrayList();
    screenList = new ArrayList();
    Runtime runtimeObject = Runtime.getRuntime();
    runtimeObject.traceInstructions(false);
    runtimeObject.traceMethodCalls(false);
    runtimeObject.runFinalization();
    runtimeObject.gc();
  }

  public abstract void clearTopScreen();

  private void createClient(XMLElement config) throws TipiException {
    clientConfig = config;
    String impl = config.getStringAttribute("impl", "indirect");
    String cfg = config.getStringAttribute("config", "server.xml");
    String secure = config.getStringAttribute("secure", "false");
    String keystore = config.getStringAttribute("keystore");
    String storepass = config.getStringAttribute("storepass");
    String navajoServer = config.getStringAttribute("server");
    String navajoUsername = config.getStringAttribute("username");
    String navajoPassword = config.getStringAttribute("password");
    if (!impl.equals("direct")) {
      System.err.println("Using INDIRECT. Username = " + navajoUsername);
      NavajoClientFactory.createDefaultClient();
      NavajoClientFactory.getClient().setServerUrl(navajoServer);
      NavajoClientFactory.getClient().setUsername(navajoUsername);
      NavajoClientFactory.getClient().setPassword(navajoPassword);
    }
    else {
      NavajoClientFactory.createClient("com.dexels.navajo.client.impl.DirectClientImpl", getClass().getClassLoader().getResource(cfg));
    }
    if (secure.equals("true")) {
      if (storepass != null && keystore != null) {
        try {
          NavajoClientFactory.getClient().setSecure(keystore, storepass, true);
        }
        catch (ClientException ex) {
          throw new TipiException("Could not locate keystore: " + keystore);
        }
      }
    }
  }

  public void parseStream(InputStream in, String sourceName) throws IOException, XMLParseException, TipiException {
    clearResources();
    XMLElement doc = new CaseSensitiveXMLElement();
    doc.parseFromReader(new InputStreamReader(in, "UTF-8"));
    parseXMLElement(doc);
    /** @todo Think about this one */
    Class initClass = (Class) tipiClassMap.get("init");
    try {
      if (initClass != null) {
        System.err.println("---- Found init class");
        TipiInitInterface tii = (TipiInitInterface) initClass.newInstance();
        tii.init(this);
      }
    }
    catch (IllegalAccessException ex) {
      ex.printStackTrace();
    }
    catch (InstantiationException ex) {
      ex.printStackTrace();
    }
    catch (ClassCastException ex) {
      ex.printStackTrace();
    }
    switchToDefinition("init");
    if (studioMode) {
      instantiateStudio();
    }
    if (errorHandler != null) {
      try {
        Class c = getTipiClass(errorHandler);
        if (c != null) {
          eHandler = (TipiErrorHandler) c.newInstance();
          eHandler.setContext(this);
        }
      }
      catch (Exception e) {
        System.err.println("Error instantiating TipiErrorHandler!");
        e.printStackTrace();
      }
    }
  }

  private void parseXMLElement(XMLElement elm) throws TipiException {
    String elmName = elm.getName();
    setSplashInfo("Loading user interface");
    if (!elmName.equals("tid")) {
      throw new TipiException("TID Rootnode not found!, found " + elmName +
                              " instead.");
    }
    errorHandler = (String) elm.getAttribute("errorhandler", null);
    Vector children = elm.getChildren();
    XMLElement startScreenDef = null;
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      String childName = child.getName();
      if (childName.equals("client-config")) {
        createClient(child);
      }
      if (childName.equals("tipi")) {
        testDefinition(child);
        addTipiDefinition(child);
      }
      if (childName.equals("component")) {
        testDefinition(child);
        addComponentDefinition(child);
      }
      if (childName.equals("tipiclass")) {
        addTipiClassDefinition(child);
      }
      if (childName.equals("tipi-types")) {
        parseTypes(child);
      }
      if (childName.equals("tipiaction")) {
        addActionDefinition(child);
      }
//      if (childName.equals("frame-instance")) {
//        screenDefList.add(child);
//      }
      if (childName.equals("tipi-include")) {
        parseLibrary(child);
      }
      if (childName.equals("tipi-parser")) {
        parseParser(child);
      }
    }
  }

  private void parseTypes(XMLElement elm) {
    try {
      Vector children = elm.getChildren();
      for (int i = 0; i < children.size(); i++) {
        XMLElement child = (XMLElement) children.get(i);
        if ("common-types".equals(child.getName())) {
          Vector commons = child.getChildren();
          for (int j = 0; j < commons.size(); j++) {
            XMLElement type = (XMLElement) commons.get(j);
            if ("type".equals(type.getName())) {
              String name = type.getStringAttribute("name");
              String clazz = type.getStringAttribute("class");
//              System.err.println("Putting reserved type: " + name + " of class: " + clazz);
              Class c = Class.forName(clazz);
              commonTypesMap.put(name, c);
            }
          }
        }
        else if ("reserved-types".equals(child.getName())) {
          Vector reserved = child.getChildren();
          for (int j = 0; j < reserved.size(); j++) {
            XMLElement type = (XMLElement) reserved.get(j);
            if ("type".equals(type.getName())) {
              String name = type.getStringAttribute("name");
              String clazz = type.getStringAttribute("class");
              Class c = Class.forName(clazz);
              //             System.err.println("Adding type: "+name+" class: +"+clazz);
              reservedTypesMap.put(name, c);
            }
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Unknown class specified");
    }
  }

  private void testDefinition(XMLElement xe) {
    if (xe.getAttribute("name") == null) {
      throw new RuntimeException("Tipi/component definition found without name at: " + xe.getLineNr());
    }
//    if (xe.getAttribute("id") != null) {
//      throw new RuntimeException("Tipi/component definition found with id at: " + xe.getLineNr());
//    }
    if (xe.getAttribute("class") == null) {
      throw new RuntimeException("Tipi/component definition found without class at: " + xe.getLineNr());
    }
  }

  public Class getCommonTypeClass(String type) {
    return (Class) commonTypesMap.get(type);
  }

  public Class getReservedTypeClass(String type) {
    return (Class) reservedTypesMap.get(type);
  }

  public URL getResourceURL(String location) {
    return getClass().getClassLoader().getResource(location);
  }

  private void parseLibrary(XMLElement lib) {
    try {
      String location = (String) lib.getAttribute("location");
      System.err.println("PARSING INCLUDE: " + location);
      includeList.add(location);
//      System.err.println("Loading library: " + location);
      if (location != null) {
        URL loc = getResourceURL(location);
        if (loc != null) {
          InputStream in = loc.openStream();
          XMLElement doc = new CaseSensitiveXMLElement();
          try {
            doc.parseFromReader(new InputStreamReader(in, "UTF-8"));
          }
          catch (XMLParseException ex) {
            System.err.println("XML parse exception while parsing file: " + location + " at line: " + ex.getLineNr());
            ex.printStackTrace();
            return;
          }
          catch (IOException ex) {
            System.err.println("IO exception while parsing file: " + location);
            ex.printStackTrace();
            return;
          }
          parseXMLElement(doc);
        }
        else {
          System.err.println("Library file not found: " + location);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public TipiActionBlock instantiateTipiActionBlock(XMLElement definition, TipiComponent parent, TipiEvent event) throws TipiException {
    TipiActionBlock c = createTipiActionBlockCondition();
    c.load(definition, parent, event);
    return c;
  }

  public TipiActionBlock instantiateDefaultTipiActionBlock(TipiComponent parent, TipiEvent event) throws TipiException {
    TipiActionBlock c = createTipiActionBlockCondition();
    return c;
  }

  public TipiAction instantiateTipiAction(XMLElement definition, TipiComponent parent, TipiEvent event) throws TipiException {
    String type = (String) definition.getAttribute("type");
    if (type == null) {
      throw new TipiException("Undefined action type in: " + definition.toString());
    }
    return myActionManager.instantiateAction(definition, event, parent);
//    DefaultTipiAction a = new DefaultTipiAction();
//    a.load(definition, parent, event);
//    return a;
  }

  public TipiActionManager getActionManager() {
    return myActionManager;
  }

  public TipiLayout instantiateLayout(XMLElement instance) throws TipiException {
    String type = (String) instance.getAttribute("type");
    TipiLayout tl = (TipiLayout) instantiateClass(type, null, instance);
    XMLElement xx = (XMLElement) getTipiClassDefMap().get(type);
    tl.setName(type);
    tl.setClassDef(xx);
    tl.initializeLayout(instance);
    tl.loadClassDef();
    return tl;
  }

  private TipiComponent instantiateComponentByDefinition(XMLElement definition, XMLElement instance) throws TipiException {
    String clas = definition.getStringAttribute("class", "");
    String name = instance.getStringAttribute("name");
    if (!clas.equals("")) {
      Class cc = getTipiClass(clas);
      TipiComponent tc = (TipiComponent) instantiateClass(clas, name, instance);
      XMLElement classDef = (XMLElement) tipiClassDefMap.get(clas);
      tc.loadEventsDefinition(this, definition, classDef);
      tc.loadStartValues(definition);
//      boolean se = Boolean.getBoolean(definition.getStringAttribute("studioelement", "false"));
      boolean se = definition.getAttribute("studioelement") != null;
//      System.err.println("Is studio element? " + se + " (class is:" + tc.getClass() + ")");
//      System.err.println("Definition is: " + definition);
      tc.setStudioElement(se);
      return tc;
    }
    else {
      throw new TipiException("Problems instantiating TipiComponent class: " + definition.toString());
    }
  }

  public TipiComponent instantiateComponent(XMLElement instance) throws TipiException {
    String name = (String) instance.getAttribute("name");
    String clas = instance.getStringAttribute("class", "");
    TipiComponent tc = null;
    if (clas.equals("")) {
      XMLElement xx = getComponentDefinition(name);
      tc = instantiateComponentByDefinition(xx, instance);
    }
    else {
      tc = (TipiComponent) instantiateClass(clas, name, instance);
    }
    if (tc.getContainer() != null) {
//      if (RootPaneContainer.class.isInstance(tc.getContainer())) {
      if (tc.isTopLevel()) {
        rootPaneList.add(tc);
      }
    }
    tc.loadStartValues(instance);
    fireTipiStructureChanged(tc);
    tc.componentInstantiated();
    if (tc.getId() == null) {
      System.err.println("NULL ID: component: " + tc.store().toString());
    }
    return tc;
  }

  private void killComponent(TipiComponent comp) {
    comp.disposeComponent();
  }

  public void disposeTipiComponent(TipiComponent comp) {
//    System.err.println("Disposing tipicomponent: "+comp.getPath());
    if (comp == null) {
      System.err.println("Can not dispose null tipi!");
      return;
    }
    if (comp.getTipiParent() == null) {
      System.err.println("Can not dispose tipi: It has no parent!");
      return;
    }
    TipiComponent parent = comp.getTipiParent();
    parent.removeChild(comp);
    if (comp instanceof TipiDataComponent) {
      removeTipiInstance(comp);
    }
    killComponent(comp);
    fireTipiStructureChanged(parent);
  }

  private Object instantiateClass(String className, String defname, XMLElement instance) throws TipiException {
//    System.err.println("Instantiating class: " + className);
    XMLElement tipiDefinition = null;
    Class c = getTipiClass(className);
    tipiDefinition = getTipiDefinition(defname);
    XMLElement classDef = (XMLElement) tipiClassDefMap.get(className);
    if (c == null) {
      throw new TipiException("Error retrieving class definition. Looking for class: " + defname + ", classname: " + className);
    }
    Object o;
    try {
      o = c.newInstance();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new TipiException("Error instantiating class. Class may not have a public default contructor, or be abstract, or an interface");
    }
    if (TipiComponent.class.isInstance(o)) {
      TipiComponent tc = (TipiComponent) o;
      tc.setContext(this);
//      tc.setContainer(tc.createContainer());
      tc.setPropertyComponent(classDef.getBooleanAttribute("propertycomponent", "true", "false", false));
      tc.initContainer();
      tc.instantiateComponent(instance, classDef);
      if (tipiDefinition != null) {
        tc.load(tipiDefinition, instance, this);
      }
      else {
        tc.load(instance, instance, this);
      }
      // Moved from the previous else clause
      tc.loadEventsDefinition(this, instance, classDef);
//      System.err.println("Instantiating class: "+className+" def: "+defname);
//      tc.setContainerVisible(true);
      return tc;
    }
    else {
//      System.err.println("Not a TIPICOMPONENT!!");
    }
    if (TipiLayout.class.isInstance(o)) {
      TipiLayout tl = (TipiLayout) o;
      tl.setContext(this);
      return tl;
    }
    throw new TipiException("INSTANTIATING UNKOWN SORT OF CLASS THING.");
  }

  public Class getTipiClass(String name) throws TipiException {
    return (Class) tipiClassMap.get(name);
  }

  private void addTipiClassDefinition(XMLElement xe) throws TipiException {
    String pack = (String) xe.getAttribute("package");
    String name = (String) xe.getAttribute("name");
    String clas = (String) xe.getAttribute("class");
    String fullDef = pack + "." + clas;
    setSplashInfo("Adding: " + fullDef);
//    System.err.println("Adding class " + pack + "." + clas + " as " + name);
    try {
      Class c = Class.forName(fullDef);
      tipiClassMap.put(name, c);
      tipiClassDefMap.put(name, xe);
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace();
      throw new TipiException("Trouble loading class. Name: " + clas + " in package: " + pack);
    }
  }

  public Iterator getTipiClassDefIterator() {
    return tipiClassDefMap.keySet().iterator();
  }

  public void addActionDefinition(XMLElement xe) throws TipiException {
    myActionManager.addAction(xe, this);
  }

  public void addTipiInstance(String service, TipiDataComponent instance) {
//    System.err.println("Adding instance. Service: "+service+" instance: "+instance.getId());
    if (tipiInstanceMap.containsKey(service)) {
//      System.err.println("Already present. Adding to list.");
      ArrayList al = (ArrayList) tipiInstanceMap.get(service);
      al.add(instance);
    }
    else {
//      System.err.println("New service, creating new list");
      ArrayList al = new ArrayList();
      al.add(instance);
      tipiInstanceMap.put(service, al);
    }
  }

  public void removeTipiInstance(TipiComponent instance) {
    Iterator c = tipiInstanceMap.values().iterator();
    while (c.hasNext()) {
      ArrayList current = (ArrayList) c.next();
      if (current.contains(instance)) {
        current.remove(instance);
      }
    }
  }

  public void printTipiInstanceMap() {
//    System.err.println("Print of tipi instance map:");
    Iterator c = tipiInstanceMap.keySet().iterator();
    while (c.hasNext()) {
      String currentKey = (String) c.next();
      ArrayList current = (ArrayList) tipiInstanceMap.get(currentKey);
      System.err.println("Current service: " + currentKey);
      for (int i = 0; i < current.size(); i++) {
        TipiComponent tc = (TipiComponent) current.get(i);
        System.err.println("Tipi with path: " + tc.getPath());
      }
      System.err.println("End of Current service: " + currentKey);
    }
    System.err.println("End of print of tipi instance map:");
  }

  private XMLElement getTipiDefinition(String name) throws TipiException {
    return (XMLElement) tipiMap.get(name);
  }

  private XMLElement getTipiDefinitionByService(String service) throws TipiException {
    XMLElement xe = (XMLElement) tipiServiceMap.get(service);
    if (xe == null) {
      throw new TipiException("Tipi definition mapping to service: " + service + " not found!");
    }
    return xe;
  }

  public ArrayList getTipiInstancesByService(String service) throws TipiException {
    return (ArrayList) tipiInstanceMap.get(service);
  }

  private XMLElement getComponentDefinition(String componentName) throws TipiException {
    XMLElement xe = (XMLElement) tipiComponentMap.get(componentName);
    if (xe == null) {
      throw new TipiException("Component definition for: " + componentName + " not found!");
    }
    return xe;
  }

  private void addComponentDefinition(XMLElement elm) {
    String buttonName = (String) elm.getAttribute("name");
    setSplashInfo("Loading: " + buttonName);
    tipiComponentMap.put(buttonName, elm);
    tipiMap.put(buttonName, elm);
  }

  private void addTipiDefinition(XMLElement elm) {
    String tipiName = (String) elm.getAttribute("name");
    String tipiService = (String) elm.getAttribute("service");
    setSplashInfo("Loading: " + tipiName);
    tipiMap.put(tipiName, elm);
    tipiServiceMap.put(tipiService, elm);
    addComponentDefinition(elm);
  }

  private TipiActionBlock createTipiActionBlockCondition() {
    return new TipiActionBlock(this);
  }

  public ArrayList getScreens() {
    return screenList;
  }

  public Object getTopLevel() {
    return ( (TipiComponent) getDefaultTopLevel()).getContainer();
  }

//  public Tipi getTopScreen(String name) {
//    for (int i = 0; i < screenList.size(); i++) {
//      Tipi t = (Tipi) screenList.get(i);
//      if (name.equals(t.getName())) {
//        return t;
//      }
//    }
//    return null;
//  }
  public TipiComponent getDefaultTopLevel() {
    return topScreen;
  }

  public void setDefaultTopLevel(TipiComponent tc) {
    topScreen = tc;
  }

  public void closeAll() {
    screenList.clear();
//    screenDefList.clear();
    tipiMap.clear();
    tipiServiceMap.clear();
    tipiInstanceMap.clear();
    tipiClassMap.clear();
    tipiClassDefMap.clear();
    includeList.clear();
  }

  private void instantiateStudio() throws TipiException {
//    System.err.println("Instantiating COMPONENT\n");
    TipiComponent tc = instantiateComponent(getComponentDefinition("studio"));
    tc.setStudioElement(true);
    ( (TipiComponent) getDefaultTopLevel()).addComponent(tc, this, null);
    ( (TipiComponent) getDefaultTopLevel()).addToContainer(tc.getContainer(), null);
  }

  public void switchToDefinition(String name) throws TipiException {
//    clearTipiAllInstances();
    clearTopScreen();
    setSplashInfo("Starting application");
    TipiComponent tc = instantiateComponent(getComponentDefinition(name));
    System.err.println("FINISHED Instantiating COMPONENT\n");
    ( (TipiComponent) getDefaultTopLevel()).addComponent(tc, this, null);
    ( (TipiComponent) getDefaultTopLevel()).addToContainer(tc.getContainer(), null);
    if (TipiDataComponent.class.isInstance(tc)) {
      ( (TipiDataComponent) tc).autoLoadServices(this);
    }
//    if (splash != null) {
//      splash.setVisible(false);
//      splash = null;
//    }
    setSplashVisible(false);
    ( (TipiDataComponent) getDefaultTopLevel()).autoLoadServices(this);
    fireTipiStructureChanged(tc);
  }

//  public Message getMessageByPath(String path) {
//    TipiPathParser pp = new TipiPathParser(null, this, path);
//    return pp.getMessage();
//  }
//
//  public Property getPropertyByPath(String path) {
//    TipiPathParser pp = new TipiPathParser(null, this, path);
//    return pp.getProperty();
//  }
  public abstract void setSplashVisible(boolean b);

  public abstract void setSplashInfo(String s);

  public TipiComponent getTipiComponentByPath(String path) {
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    return ( (TipiComponent) getDefaultTopLevel()).getTipiComponentByPath(path);
  }

  public TipiDataComponent getTipiByPath(String path) {
    TipiComponent tc = getTipiComponentByPath(path);
    if (!TipiDataComponent.class.isInstance(tc)) {
      System.err.println("Object referred to by path: " + path + " is a TipiComponent, not a Tipi");
      return null;
    }
    return (TipiDataComponent) tc;
  }

  public void enqueueAsyncSend(Navajo n, String tipiDestinationPath, String service, ConditionErrorHandler ch) {
//    setWaiting(true);
//    synchronized(this) {
//    if (myThreadsToServer.size() >= maxToServer) {
//      try {
//        myThreadPool.write("Thread waiting for serverconnection");
//        wait();
//      }
//      catch (InterruptedException ex1) {
//        System.err.println("Ok, continuing");
//        myThreadPool.write("Thread resuming after waiting for serverconnection");
//      }
//    }
//    myThreadsToServer.add(Thread.currentThread());


    try {
      Navajo reply = NavajoClientFactory.getClient().doSimpleSend(n, service, ch);
      receive(reply, service, tipiDestinationPath);
    }
    catch (ClientException ex) {
      if (eHandler != null) {
        eHandler.showError(ex);
      }
      ex.printStackTrace();
    }
    finally {
//      myThreadsToServer.remove(Thread.currentThread());
//      notify();
    }
//  }
  }

  // At the moment, only used by the advanced table. Need to remove it.
  public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch) {
    // Doe iets met die CONDITIONERRORHANDLER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    if (myThreadsToServer.size() >= maxToServer) {
      try {
        myThreadPool.write("Thread waiting for serverconnection");
        wait();
      }
      catch (InterruptedException ex1) {
        System.err.println("Ok, continuing");
        myThreadPool.write("Thread resuming after waiting for serverconnection");
      }
    }
    myThreadsToServer.add(Thread.currentThread());
    Navajo reply = null;
    try {
      try {
        reply = NavajoClientFactory.getClient().doSimpleSend(n, service, ch);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      if (eHandler != null) {
        if (eHandler.hasErrors(reply)) {
          eHandler.showError();
          return null;
        }
        else {
          return reply;
        }
      }
      else {
        return reply;
      }
    }
    finally {
      myThreadsToServer.remove(Thread.currentThread());
      notify();
    }
  }

  public void performTipiMethod(TipiDataComponent t, Navajo n, String tipiDestinationPath, String method) throws TipiException {
    enqueueAsyncSend(n, tipiDestinationPath, method, (TipiComponent) t);
  }

  private void loadTipiMethod(Navajo reply, String tipiDestinationPath, String method) throws TipiException {
    TipiDataComponent tt;
    ArrayList tipiList;
    if ("-".equals(tipiDestinationPath)) {
      return;
    }
    tipiList = getTipiInstancesByService(method);
    if (tipiList == null) {
      return;
    }
    for (int i = 0; i < tipiList.size(); i++) {
      TipiDataComponent t = (TipiDataComponent) tipiList.get(i);
      t.loadData(reply, this);
      if (t.getContainer() != null) {
        t.tipiLoaded();
      }
    }
  }

  public void receive(Navajo n, String method, String id) {
    File f = new File("c:/navajo.xml");
    try {
      FileWriter fw = new FileWriter(f, true);
      fw.write("\n\nSERVICE = " + method + "\n");
      n.write(fw);
      fw.close();
    }
    catch (Exception ex2) {
      ex2.printStackTrace();
    }
    if (eHandler != null) {
      if (eHandler.hasErrors(n)) {
        boolean hasUserDefinedErrorHandler = false;
        try {
          if (studioMode) {
            storeTemplateNavajo(method, n);
            fireNavajoLoaded(method, n);
          }
          ArrayList tipis = getTipiInstancesByService(method);
          if (tipis != null) {
//            System.err.println("# of tipis found: "+tipis.size()+" using method: "+method);
            for (int i = 0; i < tipis.size(); i++) {
              TipiDataComponent current = (TipiDataComponent) tipis.get(i);
              if (current.hasPath(id)) {
                boolean hasHandler = false;
                hasHandler = current.loadErrors(n);
                if (hasHandler) {
                  hasUserDefinedErrorHandler = true;
                }
              }
            }
          }
        }
        catch (TipiException ex1) {
          ex1.printStackTrace();
        }
        if (!hasUserDefinedErrorHandler) {
          eHandler.showError();
        }
//        if (NavajoClientFactory.getClient().getPending() == 0) {
//          setWaiting(false);
//        }
        return;
      }
    }
    try {
      if (studioMode) {
        storeTemplateNavajo(method, n);
        fireNavajoLoaded(method, n);
      }
      loadTipiMethod(n, id, method);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
//    if (NavajoClientFactory.getClient().getPending() == 0) {
//      setWaiting(false);
//    }
  }

//  public void setCurrentComponent(TipiComponent c) {
//    currentComponent = c;
//  }
  public void resetConditionRuleById(String id) {
    //System.err.println("Resetting conditionErrors for rule: " + id);
    for (int i = 0; i < screenList.size(); i++) {
      // Instances
      TipiComponent current = (TipiComponent) screenList.get(i);
      current.resetComponentValidationStateByRule(id);
    }
  }

  /** Made it synchronized. Not sure if it is necessary, but I think it can cause problems otherwise */
  public Operand evaluate(String expr, TipiComponent tc) {
    Operand o = null;
    try {
//      setCurrentComponent(tc);
      o = Expression.evaluate(expr, tc.getNearestNavajo(), null, null, null, tc);
    }
    catch (Exception ex) {
      System.err.println("Not happy while evaluating expression: " + expr + " message: " + ex.getMessage());
      Operand op = new Operand(expr, Property.STRING_PROPERTY, "");
      return o;
    }
    catch (Error ex) {
      System.err.println("Not happy while evaluating expression: " + expr + " message: " + ex.getMessage());
      Operand op = new Operand(expr, Property.STRING_PROPERTY, "");
      return o;
    }
//    System.err.println("About to examine operand: "+o.type);
//    System.err.println("Reported value: "+o.value);
    if (o.type.equals(Property.STRING_PROPERTY)) {
      if (o.value != null) {
        String s = (String) o.value;
        if (s.length() > 1) {
          if (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'') {
            o.value = s.substring(1, s.length() - 2);
//            System.err.println(">>>>> " + o.value);
          }
        }
      }
    }
    return o;
  }

  public Object evaluateExpression(String expression, TipiComponent tc) throws Exception {
    Object obj = null;
    if (expression.startsWith("{") && expression.endsWith("}")) {
      String path = expression.substring(1, expression.length() - 1);
//      System.err.println("Evaluating: "+path);
      if (path.startsWith("?")) {
        obj = new Boolean(exists(tc, path.substring(1)));
      }
      else if (path.startsWith("!?")) {
        obj = new Boolean(!exists(tc, path.substring(2)));
      }
      else {
        StringTokenizer str = new StringTokenizer(path, ":/");
        String protocol = null;
        String rest = null;
        if (str.hasMoreTokens()) {
          protocol = str.nextToken();
        }
        rest = path.substring(protocol.length() + 2);
        obj = parse(tc, protocol, rest);
        if (true) {
          return obj;
        }
      }
    }
    else {
      System.err.println("Trying to evaluate a path that is not a tipipath: " + expression);
      Thread.dumpStack();
      return expression;
    }
    return obj;
  }

//  private final Map parserMap = new HashMap();
  private final Map parserInstanceMap = new HashMap();
  private void parseParser(XMLElement xe) {
//    System.err.println("LOADING PARSER::: " + xe.toString());
    String name = xe.getStringAttribute("name");
    String parserClass = xe.getStringAttribute("parser");
    String classType = xe.getStringAttribute("type");
    Class pClass = null;
    try {
      pClass = Class.forName(parserClass);
    }
    catch (ClassNotFoundException ex) {
      System.err.println("Error loading class for parser: " + parserClass);
      return;
    }
    TipiTypeParser ttp = null;
    try {
      ttp = (TipiTypeParser) pClass.newInstance();
    }
    catch (IllegalAccessException ex1) {
      System.err.println("Error instantiating class for parser: " + parserClass);
      ex1.printStackTrace();
      return;
    }
    catch (InstantiationException ex1) {
      System.err.println("Error instantiating class for parser: " + parserClass);
      ex1.printStackTrace();
      return;
    }
    ttp.setContext(this);
    try {
      Class cc = Class.forName(classType);
      ttp.setReturnType(cc);
    }
    catch (ClassNotFoundException ex) {
      System.err.println("Error verifying return type class for parser: " + classType);
      return;
    }
    parserInstanceMap.put(name, ttp);
  }

  public Object parse(TipiComponent source, String name, String expression) {
    TipiTypeParser ttp = (TipiTypeParser) parserInstanceMap.get(name);
    if (ttp == null) {
      System.err.println("Unknown type: " + name);
      return null;
    }
    Object o = ttp.parse(source, expression);
    Class c = ttp.getReturnType();
    if (o != null && !c.isInstance(o)) {
      throw new IllegalArgumentException("Wrong type returned. Expected: " + c + "\nfound: " + o.getClass() + "\nWas parsing expression: " + expression + "\nUsing parser: " + name);
    }
    return o;
  }

  private boolean exists(TipiComponent source, String path) {
    /** @todo BEWARE: REFACTORED WITHOUT TESTING */
    if (source != null) {
      try {
        Object p = source.evaluateExpression(path);
        return p != null;
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return false;
      }
    }
    else {
      Object p = evaluate(path, null);
      return p != null;
    }
//    try {
//      TipiPathParser pp = new TipiPathParser(source, this, path);
//      if (pp.getPathType() == pp.PATH_TO_ATTRIBUTE) {
//        if (pp.getAttribute() != null) {
//          return true;
//        }
//      }
//      if (pp.getPathType() == pp.PATH_TO_COMPONENT) {
//        if (pp.getComponent() != null) {
//          return true;
//        }
//      }
//      if (pp.getPathType() == pp.PATH_TO_MESSAGE) {
//        if (pp.getMessage() != null) {
//          return true;
//        }
//      }
//      if (pp.getPathType() == pp.PATH_TO_PROPERTY) {
//        if (pp.getProperty() != null) {
//          return true;
//        }
//      }
//      if (pp.getPathType() == pp.PATH_TO_TIPI) {
//        if (pp.getTipi() != null) {
//          return true;
//        }
//      }
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//      return false;
//    }
//    return false;
  }

  public void setWaiting(boolean b) {
  }

  public void setActiveThreads(int i) {
    for (int j = 0; j < myActivityListeners.size(); j++) {
      TipiActivityListener tal = (TipiActivityListener) myActivityListeners.get(j);
      tal.setActiveThreads(i);
    }
  }

  public boolean isDefined(TipiComponent comp) {
    if (comp != null) {
      if (tipiComponentMap.get(comp.getName()) != null) {
        return true;
      }
      else {
        return false;
      }
    }
    else {
      return true;
    }
  }

  private XMLElement getDefinitionTreeOfInstance(String definition) {
    return ( (TipiComponent) getDefaultTopLevel()).getTipiComponent(definition).store();
  }

  public XMLElement getComponentTree() {
    try {
      XMLElement root = new CaseSensitiveXMLElement();
      root.setName("tid");
      root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      root.setAttribute("xsi:noNamespaceSchemaLocation", "tipiscript.xsd");
      root.setAttribute("errorhandler", "error");
      Set s = new TreeSet(includeList);
      Iterator iter = s.iterator();
      while (iter.hasNext()) {
//      for (int j = 0; j < s.size(); j++) {
        String location = (String) iter.next();
        XMLElement inc = new CaseSensitiveXMLElement();
        inc.setName("tipi-include");
        inc.setAttribute("location", location);
        root.addChild(inc);
      }
      if (clientConfig != null) {
        root.addChild(clientConfig);
      }
      Iterator it = tipiComponentMap.keySet().iterator();
      while (it.hasNext()) {
        String name = (String) it.next();
        XMLElement current = (XMLElement) tipiComponentMap.get(name);
        boolean se = current.getAttribute("studioelement") != null;
        if (!se) {
          root.addChild(current);
        }
      }
      return root;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addDefinition(String definition, String classId) {
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("tipi");
    xe.setAttribute("name", definition);
    xe.setAttribute("class", classId);
    addTipiDefinition(xe);
    fireTipiDefinitionChanged();
  }

  public void addDefinition(XMLElement xe) {
    addTipiDefinition(xe);
    fireTipiDefinitionChanged();
  }

  public void deleteDefinition(String definition) {
    tipiMap.remove(definition);
    fireTipiDefinitionChanged();
  }

  public void commitDefinition(String definition) {
    XMLElement old = (XMLElement) tipiComponentMap.get(definition);
    if (old == null) {
      System.err.println("No such definition. Well. I don't care.");
      return;
    }
    boolean isStudio = old.getAttribute("studioelement") != null;
    if (isStudio) {
      System.err.println("Ignoring studio element!");
      return;
    }
    XMLElement xe = getDefinitionTreeOfInstance(definition);
    XMLElement elt = new CaseSensitiveXMLElement();
    elt.parseString(xe.toString());
    elt.setName("tipi");
    tipiComponentMap.put(definition, elt);
    System.err.println("\n\n\n\n" + elt);
  }

  public void storeComponentTree(String name) {
    try {
      System.err.println("NAME: " + name);
      FileWriter fw = new FileWriter(name);
      getComponentTree().write(fw);
      fw.flush();
      fw.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ArrayList componentSelectionListeners = new ArrayList();
  protected void fireComponentSelection(TipiComponent tc) {
    for (int i = 0; i < componentSelectionListeners.size(); i++) {
      ComponentSelectionListener current = (ComponentSelectionListener) componentSelectionListeners.get(i);
      current.setSelectedComponent(tc);
    }
  }

  public void setSelectedComponent(TipiComponent tc) {
    fireComponentSelection(tc);
  }

  public void addComponentSelectionListener(ComponentSelectionListener cs) {
    componentSelectionListeners.add(cs);
  }

  public void removeComponentSelectionListener(ComponentSelectionListener cs) {
    componentSelectionListeners.remove(cs);
  }

  public void addTipiStructureListener(TipiStructureListener cs) {
    myTipiStructureListeners.add(cs);
  }

  public void removeTipiStructureListener(TipiStructureListener cs) {
    myTipiStructureListeners.remove(cs);
  }

  public void fireTipiStructureChanged(TipiComponent tc) {
    for (int i = 0; i < myTipiStructureListeners.size(); i++) {
      TipiStructureListener current = (TipiStructureListener) myTipiStructureListeners.get(i);
      current.tipiStructureChanged(tc);
    }
  }

  public void fireNavajoLoaded(String service, Navajo n) {
    System.err.println("Firing:: " + service + " -- " + myNavajoTemplateListeners.size());
    for (int i = 0; i < myNavajoTemplateListeners.size(); i++) {
      NavajoTemplateListener current = (NavajoTemplateListener) myNavajoTemplateListeners.get(i);
      current.navajoLoaded(service, n);
    }
  }

  public void addNavajoTemplateListener(NavajoTemplateListener nt) {
    myNavajoTemplateListeners.add(nt);
  }

  public void removeNavajoTemplateListener(NavajoTemplateListener nt) {
    myNavajoTemplateListeners.remove(nt);
  }

  public void addTipiDefinitionListener(TipiDefinitionListener cs) {
    myTipiDefinitionListeners.add(cs);
  }

  public void removeTipiDefinitionListener(TipiDefinitionListener cs) {
    myTipiDefinitionListeners.remove(cs);
  }

  public void addTipiActivityListener(TipiActivityListener listener) {
    myActivityListeners.add(listener);
  }

  public void removeTipiActivityListener(TipiActivityListener listener) {
    myActivityListeners.remove(listener);
  }

  protected void fireTipiDefinitionChanged() {
    for (int i = 0; i < myTipiDefinitionListeners.size(); i++) {
      TipiDefinitionListener current = (TipiDefinitionListener) myTipiDefinitionListeners.get(i);
      current.tipiDefinitionChanged();
    }
  }

  //EOF
  private final ArrayList activityListenerList = new ArrayList();
  public void addActivityListener(ActivityController al) {
    activityListenerList.add(al);
  }

  public void removeActivityListener(ActivityController al) {
    activityListenerList.remove(al);
  }

  public void performedEvent(TipiComponent tc, TipiEvent e) throws BlockActivityException {
    boolean blocked = false;
    for (int i = 0; i < activityListenerList.size(); i++) {
      try {
        ActivityController current = (ActivityController) activityListenerList.get(i);
        current.performedEvent(tc, e);
      }
      catch (BlockActivityException ex) {
        blocked = true;
      }
    }
    if (blocked) {
      throw new BlockActivityException();
    }
  }

  public void performedBlock(TipiComponent tc, TipiActionBlock tab, String expression, String exprPath, boolean passed) throws BlockActivityException {
    boolean blocked = false;
    for (int i = 0; i < activityListenerList.size(); i++) {
      try {
        ActivityController current = (ActivityController) activityListenerList.get(i);
        current.performedBlock(tc, tab, expression, exprPath, passed);
      }
      catch (BlockActivityException ex) {
        blocked = true;
      }
      if (blocked) {
        throw new BlockActivityException();
      }
    }
  }

  public void performedAction(TipiComponent tc, TipiAction ta) throws BlockActivityException {
    boolean blocked = false;
    for (int i = 0; i < activityListenerList.size(); i++) {
      try {
        ActivityController current = (ActivityController) activityListenerList.get(i);
        current.performedAction(tc, ta);
      }
      catch (BlockActivityException ex) {
        blocked = true;
      }
    }
    if (blocked) {
      throw new BlockActivityException();
    }
  }

  public void storeTemplateNavajo(String service, Navajo data) {
    navajoTemplateMap.put(service, data);
    fireNavajoLoaded(service, data);
  }

  public Navajo getTemplateNavajo(String service) {
    return (Navajo) navajoTemplateMap.get(service);
  }

  public Set getTemplateNavajoSet() {
    return navajoTemplateMap.keySet();
  }

  public void performAction(final TipiEvent te, TipiEventListener listener) {
    myThreadPool.performAction(te, listener);
  }

  public void threadStarted(Thread workThread) {
  }

  public void threadEnded(Thread workThread) {
  }
}
