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
    implements StudioListener, ActivityController {
//  private static TipiContext instance;
//  protected Map tipiMap = new HashMap();
//  protected Map tipiServiceMap = new HashMap();
  protected final Map tipiInstanceMap = new HashMap();
  protected final Map tipiComponentMap = new HashMap();
  protected final Map tipiClassMap = new HashMap();
  protected final Map tipiClassDefMap = new HashMap();
  protected final Map tipiActionDefMap = new HashMap();
    //  protected Map commonTypesMap = new HashMap();
    //  protected Map reservedTypesMap = new HashMap();
  private final ArrayList includeList = new ArrayList();
  private TipiErrorHandler eHandler;
  private String errorHandler;
  protected final ArrayList rootPaneList = new ArrayList();
  private final ArrayList screenList = new ArrayList();
//  private TipiComponent currentComponent;
  private final TipiActionManager myActionManager = new TipiActionManager();

  private final ArrayList myTipiStructureListeners = new ArrayList();
  private final ArrayList myNavajoTemplateListeners = new ArrayList();
  private final ArrayList myScriptListeners = new ArrayList();
  private final ArrayList myResourceListeners = new ArrayList();
  protected final List myActivityListeners = new ArrayList();
  private final ArrayList myTipiDefinitionListeners = new ArrayList();

  private final Map navajoTemplateMap = new HashMap();
  private final Map navajoScriptMap = new HashMap();
  private final Map navajoReverseMap = new HashMap();

  private final ArrayList myNodeSelectionListeners = new ArrayList();



  private XMLElement clientConfig = null;
  private boolean studioMode = false;
  protected TipiThreadPool myThreadPool;
  protected TipiComponent topScreen = null;
  protected List myThreadsToServer = new ArrayList();
  private int maxToServer = 1;
  private int poolSize = 2;
  private boolean singleThread = true;
  private String myStudioScreenPath = null;

  private boolean currentDefinitionChanged = false;
  private String currentDefinition = null;

//  private final Map parserMap = new HashMap();
  private final Map parserInstanceMap = new HashMap();
  private final Map resourceReferenceMap = new HashMap();
  private final List resourceReferenceList = new ArrayList();


  public TipiContext() {
//    myThreadPool = new TipiThreadPool(this);
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

  public boolean isStudioMode() {
    return studioMode;
//    return false;
  }

  public TipiComponent getStudioScreen() {
    if (!studioMode) {
      System.err.println("Not studio mode");
      return null;
    }
    if (myStudioScreenPath==null) {
      System.err.println("Can not retrieve studio path!!!");
      return null;
    }
    return getTipiComponentByPath(myStudioScreenPath);
  }

  public void setStudioScreenPath(String s) {
    myStudioScreenPath = s;
  }

  public void parseURL(URL location) throws IOException, XMLParseException,
      TipiException {
    parseStream(location.openStream(), location.toString());
  }

  public Map getTipiClassDefMap() {
    return tipiClassDefMap;
  }

  public Map getTipiDefinitionMap() {
    return tipiComponentMap;
  }

  private void clearResources() {
//    tipiInstanceMap = new HashMap();
//    tipiComponentMap = new HashMap();
//    tipiClassMap = new HashMap();
//    tipiClassDefMap = new HashMap();

    tipiInstanceMap.clear();
    tipiComponentMap.clear();
    tipiClassMap.clear();
    tipiClassDefMap.clear();

    clearTopScreen();
    includeList.clear();
    eHandler = null;
    errorHandler = null;
    rootPaneList.clear();
    screenList.clear();
    Runtime runtimeObject = Runtime.getRuntime();
    runtimeObject.traceInstructions(false);
    runtimeObject.traceMethodCalls(false);
    runtimeObject.runFinalization();
    runtimeObject.gc();
  }

  public abstract void clearTopScreen();

  private void configureTipi(XMLElement config) throws TipiException {
    /** @todo Implement configuration of tipi setup
     * For example, the threading model, the amount of event threads, the # of allowed
     * connections.
     *  */
//    System.err.println("CONFIGURING TIPI WITH: "+config);
    maxToServer = config.getIntAttribute("maxtoserver",1);
    System.setProperty("tipi.client.maxthreadstoserver",""+maxToServer);
    poolSize = config.getIntAttribute("poolsize",1);
    System.setProperty("tipi.client.threadpoolsize",""+poolSize);

//    String storepass = config.getStringAttribute("storepass");
//    String navajoServer = config.getStringAttribute("server");
//    String navajoUsername = config.getStringAttribute("username");
//    String navajoPassword = config.getStringAttribute("password");
  }

  private void setSystemProperty(String name, String value, boolean overwrite) {
    if (System.getProperty(name)!=null) {
      if (overwrite) {
        System.setProperty(name,value);
      }
    } else {
      System.setProperty(name,value);

    }
  }

  private void createClient(XMLElement config) throws TipiException {
    clientConfig = config;
    String impl = config.getStringAttribute("impl", "indirect");
    setSystemProperty("tipi.client.impl",impl,false);
    String cfg = config.getStringAttribute("config", "server.xml");
    setSystemProperty("tipi.client.config",cfg,false);
    String secure = config.getStringAttribute("secure", "false");
    setSystemProperty("tipi.client.impl",secure,false);
    String keystore = config.getStringAttribute("keystore","");
    setSystemProperty("tipi.client.keystore",keystore,false);
    String storepass = config.getStringAttribute("storepass","");
    setSystemProperty("tipi.client.storepass",storepass,false);

    String navajoServer = config.getStringAttribute("server","");
    setSystemProperty("tipi.client.server",navajoServer,false);
    String navajoUsername = config.getStringAttribute("username","");
    setSystemProperty("tipi.client.username",navajoUsername,false);
    String navajoPassword = config.getStringAttribute("password","");
    setSystemProperty("tipi.client.password",navajoPassword,false);

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
    InputStreamReader isr = new InputStreamReader(in, "UTF-8");
    doc.parseFromReader(isr);
    isr.close();
    long time = System.currentTimeMillis();
    parseXMLElement(doc);
    long parseTime = System.currentTimeMillis() - time;
//    System.err.println("P>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>arsetime: "+parseTime);
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
    if (studioMode) {
      instantiateStudio();
//      switchToDefinition("studio");
      switchToDefinition("init",null);
    } else {
    switchToDefinition("init",null);
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
      if (childName.equals("tipi-config")) {
        configureTipi(child);
      }
      parseDefinition(child);
      if (childName.equals("tipiclass")) {
        addTipiClassDefinition(child);
      }
//      if (childName.equals("tipi-types")) {
//        parseTypes(child);
//      }
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
      if (childName.equals("tipi-resource")) {
        parseResource(child);
      }
    }
  }

  public void parseDefinition(XMLElement child) {
    String childName = child.getName();
    if (childName.equals("tipi")|| childName.equals("component")) {
      testDefinition(child);
      addComponentDefinition(child);
    }
  }

//  private void parseTypes(XMLElement elm) {
//    try {
//      Vector children = elm.getChildren();
//      for (int i = 0; i < children.size(); i++) {
//        XMLElement child = (XMLElement) children.get(i);
//        if ("common-types".equals(child.getName())) {
//          Vector commons = child.getChildren();
//          for (int j = 0; j < commons.size(); j++) {
//            XMLElement type = (XMLElement) commons.get(j);
//            if ("type".equals(type.getName())) {
//              String name = type.getStringAttribute("name");
//              String clazz = type.getStringAttribute("class");
//              System.err.println("Putting reserved type: " + name + " of class: " + clazz);
//              Class c = Class.forName(clazz);
//              commonTypesMap.put(name, c);
//            }
//          }
//        }
//        else if ("reserved-types".equals(child.getName())) {
//          Vector reserved = child.getChildren();
//          for (int j = 0; j < reserved.size(); j++) {
//            XMLElement type = (XMLElement) reserved.get(j);
//            if ("type".equals(type.getName())) {
//              String name = type.getStringAttribute("name");
//              String clazz = type.getStringAttribute("class");
//              Class c = Class.forName(clazz);
//              //             System.err.println("Adding type: "+name+" class: +"+clazz);
//              reservedTypesMap.put(name, c);
//            }
//          }
//        }
//      }
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//      throw new RuntimeException("Unknown class specified");
//    }
//  }

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

//  public Class getCommonTypeClass(String type) {
//    return (Class) commonTypesMap.get(type);
//  }
//
//  public Class getReservedTypeClass(String type) {
//    return (Class) reservedTypesMap.get(type);
//  }

  public URL getResourceURL(String location) {
    return getClass().getClassLoader().getResource(location);
  }

  private void parseLibrary(XMLElement lib) {
    try {
      String location = (String) lib.getAttribute("location");
//      System.err.println("PARSING INCLUDE: " + location);
      includeList.add(location);
//      System.err.println("Loading library: " + location);
      if (location != null) {
        URL loc = getResourceURL(location);
        if (loc != null) {
          InputStream in = loc.openStream();
          XMLElement doc = new CaseSensitiveXMLElement();
          try {
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            doc.parseFromReader(isr);
            isr.close();
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

  public TipiActionBlock instantiateTipiActionBlock(XMLElement definition, TipiComponent parent) throws TipiException {
    TipiActionBlock c = createTipiActionBlockCondition();
    c.load(definition, parent);
    return c;
  }

  public TipiActionBlock instantiateDefaultTipiActionBlock(TipiComponent parent) throws TipiException {
    TipiActionBlock c = createTipiActionBlockCondition();
    return c;
  }

  public TipiAction instantiateTipiAction(XMLElement definition, TipiComponent parent) throws TipiException {
    String type = (String) definition.getAttribute("type");
    if (type == null) {
      throw new TipiException("Undefined action type in: " + definition.toString());
    }
    return myActionManager.instantiateAction(definition, parent);
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
    if (tl==null) {
      System.err.println("Null layout!!!!!!!!!!!!");
    }
    XMLElement xx = (XMLElement) getTipiClassDefMap().get(type);
    tl.setName(type);
    tl.setClassDef(xx);
    tl.initializeLayout(instance);
    tl.loadClassDef();
    return tl;
  }

  protected TipiComponent instantiateComponentByDefinition(XMLElement definition, XMLElement instance) throws TipiException {
    String clas = definition.getStringAttribute("class", "");
    String name = instance.getStringAttribute("name");
    if (!clas.equals("")) {
      Class cc = getTipiClass(clas);
      TipiComponent tc = (TipiComponent) instantiateClass(clas, name, instance);
      XMLElement classDef = (XMLElement) tipiClassDefMap.get(clas);
      tc.loadEventsDefinition(this, definition, classDef);
      tc.loadMethodDefinitions(this, definition, classDef);
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
//    fireTipiStructureChanged(tc);
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
      tc.setStudioElement(instance.getBooleanAttribute("studioelement", "true", "false", false));
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
      tc.loadMethodDefinitions(this, instance, classDef);
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
    if (tipiInstanceMap.containsKey(service)) {
      ArrayList al = (ArrayList) tipiInstanceMap.get(service);
      al.add(instance);
    }
    else {
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

  protected XMLElement getTipiDefinition(String name) throws TipiException {
    return (XMLElement) tipiComponentMap.get(name);
  }

  public ArrayList getTipiInstancesByService(String service) throws TipiException {
    return (ArrayList) tipiInstanceMap.get(service);
  }

  protected XMLElement getComponentDefinition(String componentName) throws TipiException {
    XMLElement xe = (XMLElement) tipiComponentMap.get(componentName);
    if (xe == null) {
      throw new TipiException("Component definition for: " + componentName + " not found!");
    }
    return xe;
  }

  private void addComponentDefinition(XMLElement elm) {
    String defname = (String) elm.getAttribute("name");
    setSplashInfo("Loading: " + defname);
    tipiComponentMap.put(defname, elm);
//    tipiMap.put(defname, elm);
  }

  public void addTipiDefinition(XMLElement elm) {
    String tipiName = (String) elm.getAttribute("name");
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
//    tipiMap.clear();
    tipiComponentMap.clear();
//    tipiServiceMap.clear();
    tipiInstanceMap.clear();
    tipiClassMap.clear();
    tipiClassDefMap.clear();
    includeList.clear();
  }

  protected void instantiateStudio() throws TipiException {
  }

  public void switchToDefinition(String name, TipiEvent event) throws TipiException {
    clearTopScreen();
    setSplashInfo("Starting application");
    TipiComponent tc = instantiateComponent(getComponentDefinition(name));
    ( (TipiComponent) getDefaultTopLevel()).addComponent(tc, this, null);
    ( (TipiComponent) getDefaultTopLevel()).addToContainer(tc.getContainer(), null);
    if (TipiDataComponent.class.isInstance(tc)) {
      ( (TipiDataComponent) tc).autoLoadServices(this,event);
    }
    setSplashVisible(false);
    ( (TipiDataComponent) getDefaultTopLevel()).autoLoadServices(this,event);
    fireTipiDefinitionSelected(name);
    fireTipiStructureChanged(tc);
    currentDefinitionChanged = false;
    currentDefinition = name;
  }

  public String getCurrentDefinition() {
    return currentDefinition;
  }

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

//  private void enqueueAsyncSend(Navajo n, String tipiDestinationPath, String method, ConditionErrorHandler ch, boolean breakOnError, TipiEvent event) throws TipiBreakException {
//    long xx = System.currentTimeMillis();
//    Navajo reply = doSimpleSend(n,method,ch);
//    if (reply!=null) {
//      if (eHandler != null) {
//        if (eHandler.hasErrors(reply)) {
//          boolean hasUserDefinedErrorHandler = false;
//          try {
//            ArrayList tipis = getTipiInstancesByService(method);
//            if (tipis != null) {
//              for (int i = 0; i < tipis.size(); i++) {
//                TipiDataComponent current = (TipiDataComponent) tipis.get(i);
//                if (current.hasPath(tipiDestinationPath,event)) {
//                  boolean hasHandler = false;
//                  hasHandler = current.loadErrors(reply);
//                  if (hasHandler) {
//                    hasUserDefinedErrorHandler = true;
//                  }
//                   }
//              }
//            }
//          }
//          catch (TipiException ex1) {
//            ex1.printStackTrace();
//          }
//          if (!hasUserDefinedErrorHandler) {
//            eHandler.showError();
//          }
//          if (breakOnError) {
//               throw new TipiBreakException(-1);
//             }
//          return;
//        }
//      }
//      try {
//        if (studioMode) {
//          storeTemplateNavajo(method, reply);
//          fireNavajoLoaded(method, reply);
//        }
//        loadTipiMethod(reply, tipiDestinationPath, method);
//      }
//      catch (TipiException ex) {
//        ex.printStackTrace();
//      }
//    }
//    long x2 = System.currentTimeMillis() - xx;
//    logServicePerformance(method,x2);
//  }

  private void logServicePerformance(String service, long time) {
  }


  public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch) {
     return doSimpleSend(n,service,ch,-1);

  }

  public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch, long expirtationInterval) {

    boolean useThreadLimiter = true;
    Navajo reply = null;
    if (!TipiThread.class.isInstance(Thread.currentThread())) {
      // if this function is called from an 'ordinary' thread, like main or the event thread,
      // let it through
      useThreadLimiter = false;
    }

    if (myThreadPool==null) {
      myThreadPool = new TipiThreadPool(this,poolSize);
    }
    if (useThreadLimiter) {
      synchronized (this) {
        while (myThreadsToServer.size() >= maxToServer) {
          try {
            // wait with timeout to be on the save side.
            wait(10000);
          }
          catch (InterruptedException ex1) {
            System.err.println("Thread interrupted: "+Thread.currentThread().toString());
          }
          System.err.println("Thread resuming after waiting for serverconnection");
        }
        myThreadsToServer.add(Thread.currentThread());

      }

    }

    try {

      reply = NavajoClientFactory.getClient().doSimpleSend(n, service, ch,expirtationInterval);
     }
    catch (Throwable ex) {
      if (eHandler != null && Exception.class.isInstance(ex)) {
        eHandler.showError((Exception)ex);
      }
      ex.printStackTrace();
    }
    finally {
      if (useThreadLimiter) {
        synchronized (this) {
          myThreadsToServer.remove(Thread.currentThread());
//          System.err.println("Removed. Now: #  in queue: "+myThreadsToServer.size());
          notify();
          for (int i = 0; i < myThreadsToServer.size(); i++) {
            Thread t = (Thread)myThreadsToServer.get(i);
            t.interrupt();
          }
        }

      }
    }
    if (reply == null) {
      reply = NavajoFactory.getInstance().createNavajo();
    }
    return reply;
  }


//  public void performTipiMethod(TipiDataComponent t, Navajo n, String tipiDestinationPath, String method,boolean breakOnError, TipiEvent event) throws TipiException, TipiBreakException {
//    performTipiMethod(t,n,tipiDestinationPath,method,breakOnError,event,-1);
//  }

  public void performTipiMethod(TipiDataComponent t, Navajo n, String tipiDestinationPath, String method,boolean breakOnError, TipiEvent event, long expirationInterval) throws TipiException, TipiBreakException {
    ConditionErrorHandler ch = t;
//    enqueueAsyncSend(n, tipiDestinationPath, method, (TipiComponent) t,breakOnError,event);
    long xx = System.currentTimeMillis();
    Navajo reply = doSimpleSend(n,method,ch,expirationInterval);
    if (reply!=null) {
//      receive(reply, service, tipiDestinationPath,breakOnError,event);
      if (eHandler != null) {
        if (eHandler.hasErrors(reply)) {
          boolean hasUserDefinedErrorHandler = false;
          try {
            ArrayList tipis = getTipiInstancesByService(method);
            if (tipis != null) {
              for (int i = 0; i < tipis.size(); i++) {
                TipiDataComponent current = (TipiDataComponent) tipis.get(i);
                if (current.hasPath(tipiDestinationPath,event)) {
                  boolean hasHandler = false;
                  hasHandler = current.loadErrors(reply);
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
          if (breakOnError) {
               throw new TipiBreakException(-1);
             }
          return;
        }
      }
      try {
        if (studioMode) {
          storeTemplateNavajo(method, reply);
          fireNavajoLoaded(method, reply);
        }
        loadTipiMethod(reply, tipiDestinationPath, method);
      }
      catch (TipiException ex) {
        ex.printStackTrace();
      }
    }
    long x2 = System.currentTimeMillis() - xx;
    logServicePerformance(method,x2);
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

//  public void receive(Navajo reply, String service, String tipiDestinationPath,boolean breakOnError, TipiEvent event) throws TipiBreakException {
//    if (eHandler != null) {
//      if (eHandler.hasErrors(reply)) {
//        boolean hasUserDefinedErrorHandler = false;
//        try {
//          ArrayList tipis = getTipiInstancesByService(service);
//          if (tipis != null) {
//            for (int i = 0; i < tipis.size(); i++) {
//              TipiDataComponent current = (TipiDataComponent) tipis.get(i);
//              if (current.hasPath(tipiDestinationPath,event)) {
//                boolean hasHandler = false;
//                hasHandler = current.loadErrors(reply);
//                if (hasHandler) {
//                  hasUserDefinedErrorHandler = true;
//                }
//                 }
//            }
//          }
//        }
//        catch (TipiException ex1) {
//          ex1.printStackTrace();
//        }
//        if (!hasUserDefinedErrorHandler) {
//          eHandler.showError();
//        }
//        if (breakOnError) {
//             throw new TipiBreakException(-1);
//           }
//        return;
//      }
//    }
//    try {
//      if (studioMode) {
//        storeTemplateNavajo(service, reply);
//        fireNavajoLoaded(service, reply);
//      }
//      loadTipiMethod(reply, tipiDestinationPath, service);
//    }
//    catch (TipiException ex) {
//      ex.printStackTrace();
//    }
//  }

  public void resetConditionRuleById(String id) {
     for (int i = 0; i < screenList.size(); i++) {
      // Instances
      TipiComponent current = (TipiComponent) screenList.get(i);
      current.resetComponentValidationStateByRule(id);
    }
  }

  public Operand evaluate(String expr, TipiComponent tc, TipiEvent event) {
    Operand o = null;
    try {
      synchronized (tc) {
        tc.setCurrentEvent(event);
        o = Expression.evaluate(expr, tc.getNearestNavajo(), null, null, null, tc);
      }
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
    if (o.type.equals(Property.STRING_PROPERTY)) {
      if (o.value != null) {
        String s = (String) o.value;
        if (s.length() > 1) {
          if (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'') {
            o.value = s.substring(1, s.length() - 2);
          }
        }
      }
    }
    return o;
  }

  public Object evaluateExpression(String expression, TipiComponent tc, TipiEvent event) throws Exception {
    Object obj = null;
    if (expression.startsWith("{") && expression.endsWith("}")) {
      String path = expression.substring(1, expression.length() - 1);

      // Bad bad and evil. The exist operator should be outside the curlies.
      // That would require some extensions to the expression mechanism.
      // The current construction is a bit stupid, but it kind of works for now.
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
        obj = parse(tc, protocol, rest,event);
//        if (true) {
          return obj;
//        }
      }
    }
    else {
      System.err.println("Trying to evaluate a path that is not a tipipath: " + expression);
//      Thread.dumpStack();
      return expression;
    }
    return obj;
  }


  private void parseResource(XMLElement xe) {
    TipiResourceReference trr = null;
    try {
      trr = new TipiResourceReference(this, xe);
    }
    catch (IOException ex) {
      System.err.println("ERROR LOADING EAGER Resource: "+xe);
    }
    resourceReferenceMap.put(trr.getId(),trr);
    resourceReferenceList.add(trr.getId());
  }

  public InputStream getResource(TipiComponent source, String id, TipiEvent event) throws IOException {
    TipiResourceReference trr = getResourceReference(id);
    if (trr!=null) {
      return trr.getStream(source,event);
    }
    throw new IOException("Resource: "+id+" unknown with tipi context");
  }

  public TipiResourceReference getResourceReference(String id) {
    return (TipiResourceReference)resourceReferenceMap.get(id);
  }

  public List getResourceList() {
    return resourceReferenceList;
  }


  public void addResourceReference(String id, String description, String path, String type, boolean local, boolean eager) {
    resourceReferenceList.add(id);
    resourceReferenceMap.put(id,new TipiResourceReference(this,id,description,path,type,local,eager));
  }

  public void clearResourceReference() {
    resourceReferenceMap.clear();
    resourceReferenceList.clear();
  }

  public void removeResourceReference(String id) {
    resourceReferenceList.remove(id);
    resourceReferenceMap.remove(id);
  }
  private void parseParser(XMLElement xe) {
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

  public Object parse(TipiComponent source, String name, String expression, TipiEvent te) {
    TipiTypeParser ttp = (TipiTypeParser) parserInstanceMap.get(name);
    if (ttp == null) {
      System.err.println("Unknown type: " + name);
      return null;
    }
    Object o = ttp.parse(source, expression, te);
    Class c = ttp.getReturnType();
    if (o != null && !c.isInstance(o)) {
      throw new IllegalArgumentException("Wrong type returned. Expected: " + c + "\nfound: " + o.getClass() + "\nWas parsing expression: " + expression + "\nUsing parser: " + name);
    }
    return o;
  }

  public boolean isValidType(String name) {
    return parserInstanceMap.containsKey(name);
  }

  public String toString(TipiComponent source, String name, Object o) {
    TipiTypeParser ttp = (TipiTypeParser) parserInstanceMap.get(name);
    if (ttp == null) {
      System.err.println("Unknown type: " + name);
      return null;
    }
    Class c = ttp.getReturnType();
    if (o==null) {
      return null;
    }
    if (!c.isInstance(o)) {
      System.err.println("PRocessing source: "+source.getPath());
       throw new IllegalArgumentException("Wrong type: Need type: "+name+" (being of class: "+c.toString()+") but found: "+o.getClass());
    }
    return ttp.toString(o,source);
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
      Object p = evaluate(path, null, null);
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

  private void writeTipiFile(File tipiDir, String name, XMLElement definition) throws IOException {
    File tipiFile = new File(tipiDir,name+".xml");
    System.err.println("Writing to file: "+tipiFile.toString());
    FileWriter fw = new FileWriter(tipiFile);
    definition.write(fw);
    fw.close();

  }

  public void writeComponentMap(File source, ArrayList jars, File dir, List classDefList) throws IOException{
    if (!dir.exists()) {
      boolean ok = dir.mkdirs();
      if (!ok) {
        System.err.println("Could not create directory: "+dir.toString());
        return;
      }
    }
    if (!dir.isDirectory()) {
      System.err.println("Can not write: Not a directory!");
      return;
    }
    if (dir.listFiles().length>0) {
      System.err.println("Can not write: Directory not empty!");
      return;
    }
    File libDir = new File(dir,"lib");
    libDir.mkdir();
    File tipiDir = new File(dir,"tipi");
    tipiDir.mkdir();

    createStartupTipi(tipiDir,classDefList);
    copyLibs(source, libDir, jars);

    Iterator it = tipiComponentMap.keySet().iterator();
    while (it.hasNext()) {
      String name = (String) it.next();
      XMLElement root = new CaseSensitiveXMLElement();
      root.setName("tid");
      root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      root.setAttribute("xsi:noNamespaceSchemaLocation", "tipiscript.xsd");
      root.setAttribute("errorhandler", "error");

      XMLElement current = (XMLElement) tipiComponentMap.get(name);
      boolean se = current.getBooleanAttribute("studioelement","true","false",false);
      if (!se) {
        System.err.println("Writing file: "+name);
        root.addChild(current);
        writeTipiFile(tipiDir,name,root);
      }
    }
    createStartupFile(dir, jars) ;

  }
  private void copyResource(OutputStream out, InputStream in) throws IOException{
      BufferedInputStream bin = new BufferedInputStream(in);
      BufferedOutputStream bout = new BufferedOutputStream(out);
      byte[] buffer = new byte[1024];
      int read;
      while ((read = bin.read(buffer)) > -1) {
        bout.write(buffer,0,read);
      }
      bin.close();
      bout.flush();
      bout.close();
  }

  private void copyLibs(File sourcedir, File destdir, ArrayList list)  throws IOException{
    for (int i = 0; i < list.size(); i++) {
      String currentLib = (String)list.get(i);
      File sourceFile = new File(sourcedir,currentLib);
      File destFile = new File(destdir,currentLib);
      FileInputStream sourceStream = new FileInputStream(sourceFile);
      FileOutputStream destStream = new FileOutputStream(destFile);
      copyResource(destStream, sourceStream);
    }
  }

  private void createStartupTipi(File dir, List classDefList) throws IOException {
    File ff = new File(dir,"start.xml");
    XMLElement root = new CaseSensitiveXMLElement();
    root.setName("tid");
    root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    root.setAttribute("xsi:noNamespaceSchemaLocation", "tipiscript.xsd");
    root.setAttribute("errorhandler", "error");

    for (int i = 0; i < classDefList.size(); i++) {
      String crnt = (String)classDefList.get(i);
      XMLElement xx = new CaseSensitiveXMLElement();
      xx.setName("tipi-include");
      xx.setAttribute("location",crnt);
      root.addChild(xx);
    }


    Set s = new TreeSet(includeList);
    Iterator iter = tipiComponentMap.keySet().iterator();
    while (iter.hasNext()) {
//      for (int j = 0; j < s.size(); j++) {
      String location = (String) "tipi/"+iter.next()+".xml";
      XMLElement inc = new CaseSensitiveXMLElement();
      inc.setName("tipi-include");
      inc.setAttribute("location", location);
      root.addChild(inc);
    }


    if (clientConfig != null) {
      root.addChild(clientConfig);
    }
    FileWriter fw = new FileWriter(ff);
    root.write(fw);
    fw.close();
  }

  public void addDefinition(String definition, String classId) {
    XMLElement xe = new CaseSensitiveXMLElement();
    /** @todo Maybe check for component definitions... */

    xe.setName("tipi");
    xe.setAttribute("name", definition);
    xe.setAttribute("class", classId);
    addComponentDefinition(xe);
    fireTipiDefinitionAdded(definition);
  }

//  public void addDefinition(XMLElement xe) {
//    addTipiDefinition(xe);
//    fireTipiDefinitionAdded(xe.getStringAttribute("name"));
//  }

  public void deleteDefinition(String definition) {
    tipiComponentMap.remove(definition);
    fireTipiDefinitionDeleted(definition);
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
    fireTipiDefinitionCommitted(definition);
  }

  public void replaceDefinition(XMLElement xe) {
    String name = xe.getStringAttribute("name");
    tipiComponentMap.put(name,xe);
  }

  public void storeComponentTree(String name) {
    try {
//      System.err.println("NAME: " + name);
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
    currentDefinitionChanged = true;
  }

  public boolean hasDefinitionChanged() {
    return currentDefinitionChanged;
  }

  private String selectedItemName = null;

  public void fireNavajoLoaded(String service, Navajo n) {
    if (service==null) {
      return;
    }
    for (int i = 0; i < myNavajoTemplateListeners.size(); i++) {
        NavajoTemplateListener current = (NavajoTemplateListener) myNavajoTemplateListeners.get(i);
        current.navajoLoaded(service, n);
    }
  }

  public void fireNavajoSelected(String service, Navajo n) {
    if (service==null) {
     return;
   }
   System.err.println("@@@@@@@@@@     CURRENT NAVAJO: "+service+" present? "+n!=null);
//    System.err.println("HASH: "+n.hashCode()+"old: "+navajoTemplateMap.get(service).hashCode();
//    if (selectedItemName == service && n == navajoTemplateMap.get(service)) {
//      System.err.println("SAME... IGNORING");
//      return;
//    }
    selectedItemName = service;
    for (int i = 0; i < myNavajoTemplateListeners.size(); i++) {
      NavajoTemplateListener current = (NavajoTemplateListener) myNavajoTemplateListeners.get(i);
      current.navajoSelected(service, n);
    }
  }

  public void fireNavajoRemoved(String service) {
    for (int i = 0; i < myNavajoTemplateListeners.size(); i++) {
      NavajoTemplateListener current = (NavajoTemplateListener) myNavajoTemplateListeners.get(i);
      current.navajoRemoved(service);
    }
  }

  public void fireResourceAdded(String service, TipiEvent event) {
    for (int i = 0; i < myResourceListeners.size(); i++) {
      ResourceListener current = (ResourceListener) myResourceListeners.get(i);
      current.resourceAdded(service,event);
    }
  }

  public void fireResourceSelected(String service, TipiEvent event) {
    for (int i = 0; i < myResourceListeners.size(); i++) {
      ResourceListener current = (ResourceListener) myResourceListeners.get(i);
      current.resourceSelected(service,event);
    }
  }

  public void fireResourceRemoved(String service, TipiEvent event) {
    for (int i = 0; i < myResourceListeners.size(); i++) {
      ResourceListener current = (ResourceListener) myResourceListeners.get(i);
      current.resourceRemoved(service,event);
    }
  }
  public void fireResourceChanged(String service, TipiEvent event) {
    for (int i = 0; i < myResourceListeners.size(); i++) {
      ResourceListener current = (ResourceListener) myResourceListeners.get(i);
      current.resourceChanged(service,event);
    }
  }



  public void fireScriptCreated(String service) {
    for (int i = 0; i < myScriptListeners.size(); i++) {
      NavajoScriptListener current = (NavajoScriptListener) myScriptListeners.get(i);
      current.scriptCreated(service);
    }
  }

  public void fireScriptLoaded(String service) {
    for (int i = 0; i < myScriptListeners.size(); i++) {
      NavajoScriptListener current = (NavajoScriptListener) myScriptListeners.get(i);
      current.scriptLoaded(service);
    }
  }

  public void fireScriptCommitted(String service) {
    for (int i = 0; i < myScriptListeners.size(); i++) {
      NavajoScriptListener current = (NavajoScriptListener) myScriptListeners.get(i);
      current.scriptCommitted(service);
    }
  }
  public void fireScriptDeleted(String service) {
    for (int i = 0; i < myScriptListeners.size(); i++) {
      NavajoScriptListener current = (NavajoScriptListener) myScriptListeners.get(i);
      current.scriptDeleted(service);
    }
  }
  public void fireScriptSelected(String service) {
    if (service==null) {
      return;
    }
    selectedItemName = service;
   for (int i = 0; i < myScriptListeners.size(); i++) {
      NavajoScriptListener current = (NavajoScriptListener) myScriptListeners.get(i);
      current.scriptSelected(service);
    }
  }










  public void addNavajoTemplateListener(NavajoTemplateListener nt) {
    myNavajoTemplateListeners.add(nt);
  }

  public void removeNavajoTemplateListener(NavajoTemplateListener nt) {
    myNavajoTemplateListeners.remove(nt);
  }

  public void addResourceListener(ResourceListener nt) {
    myResourceListeners.add(nt);
  }

  public void removeResourceListener(ResourceListener nt) {
    myResourceListeners.remove(nt);
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

  public void addNavajoScriptListener(NavajoScriptListener listener) {
    myScriptListeners.add(listener);
  }

  public void removeNavajoScriptListener(NavajoScriptListener listener) {
    myScriptListeners.remove(listener);
  }


  public void addNodeSelectionListener(NodeSelectionListener listener) {
    myNodeSelectionListeners.add(listener);
  }

  public void removeNodeSelectionListener(NodeSelectionListener listener) {
    myNodeSelectionListeners.remove(listener);
  }

  public void fireNodeSelected(Object node,String xpath,String type, Object xmlcontainer) {
    for (int i = 0; i < myNodeSelectionListeners.size(); i++) {
      NodeSelectionListener current = (NodeSelectionListener) myNodeSelectionListeners.get(i);
      current.nodeSelected(node,xpath,type,xmlcontainer);
    }
  }

  public void fireTipiDefinitionAdded(String name) {
    for (int i = 0; i < myTipiDefinitionListeners.size(); i++) {
      TipiDefinitionListener current = (TipiDefinitionListener) myTipiDefinitionListeners.get(i);
      current.definitionAdded(name);
    }
  }
  public void fireTipiDefinitionCommitted(String name) {
    for (int i = 0; i < myTipiDefinitionListeners.size(); i++) {
      TipiDefinitionListener current = (TipiDefinitionListener) myTipiDefinitionListeners.get(i);
      current.definitionCommitted(name);
    }
  }
  public void fireTipiDefinitionDeleted(String name) {
    for (int i = 0; i < myTipiDefinitionListeners.size(); i++) {
      TipiDefinitionListener current = (TipiDefinitionListener) myTipiDefinitionListeners.get(i);
      current.definitionDeleted(name);
    }
  }
  public void fireTipiDefinitionSelected(String name) {
    for (int i = 0; i < myTipiDefinitionListeners.size(); i++) {
      TipiDefinitionListener current = (TipiDefinitionListener) myTipiDefinitionListeners.get(i);
      current.definitionSelected(name);
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
  public Navajo removeTemplateNavajo(String service) {
    return (Navajo) navajoTemplateMap.remove(service);
  }

  public void performAction(final TipiEvent te, TipiEventListener listener) {
    if (myThreadPool==null) {
      myThreadPool = new TipiThreadPool(this,poolSize);
    }
    myThreadPool.performAction(te, listener);
  }

  public void threadStarted(Thread workThread) {
  }

  public void threadEnded(Thread workThread) {
  }

  public void createStartupFile(File startupDir, ArrayList jarList) throws IOException {
    System.err.println("This implementation can not create a startup file!");
  }

  public String getScriptSource(String scriptName) {
    return (String)navajoScriptMap.get(scriptName);
  }

  public String loadScript(String name) {
    if (name==null) {
      return "";
    }
    if ("".equals(name)) {
      return "";
    }
    Navajo n = doSimpleSend(NavajoFactory.getInstance().createNavajo(),"InitScripts",null);
    Property p = n.getProperty("/RequestScript/ScriptName");
    p.setValue(name);
    Navajo m = doSimpleSend(n,"ProcessGetScript",null);
    Property q = m.getProperty("/NavajoScript/Script");
//    System.err.println("Type: "+q.getType());
//    byte[] o = (byte[])q.getTypedValue();
    String s = q.getValue();
    sun.misc.BASE64Decoder enc = new sun.misc.BASE64Decoder();
    byte[] bb = null;
    try {
     bb = enc.decodeBuffer(s);
    }
    catch (IOException ex) {
    }
    String sss = new String(bb);
    navajoScriptMap.put(name,sss);
    fireScriptLoaded(name);
    return sss;
  }

  public void removeScript(String name) {
    if (navajoScriptMap.containsKey(name)) {
      navajoScriptMap.remove(name);
      fireScriptDeleted(name);
    }
  }

  public void createScript(String name, String scriptData) {
    String result = (String)navajoScriptMap.get(name);
   if (result!=null) {
     System.err.println("Script exists. Delete old one first");
     return;
   }

   sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
   String encoded = enc.encode(scriptData.getBytes());

   Navajo n = doSimpleSend(NavajoFactory.getInstance().createNavajo(),"InitStoreScript",null);
   Property p = n.getProperty("/StoreScript/ScriptName");
   p.setValue(name);
   Property r = n.getProperty("/StoreScript/ScriptData");
   r.setValue(encoded);
//   try {
//     n.write(System.err);
//   }
//   catch (NavajoException ex) {
//     ex.printStackTrace();
//   }
   Navajo m = doSimpleSend(n,"ProcessUpdateScript",null);
//   try {
//     m.write(System.err);
//   }
//   catch (NavajoException ex) {
//     ex.printStackTrace();
//   }
   navajoScriptMap.put(name,scriptData);

  }

  public void commitScript(String name, String script) {
    navajoScriptMap.put(name,script);
  }

  public void storeScript(String name) {
    String result = (String)navajoScriptMap.get(name);
    if (result==null) {
      return;
    }

    sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
    String encoded = enc.encode(result.getBytes());

    Navajo n = doSimpleSend(NavajoFactory.getInstance().createNavajo(),"InitStoreScript",null);
    Property p = n.getProperty("/StoreScript/ScriptName");
    p.setValue(name);
    Property r = n.getProperty("/StoreScript/ScriptData");
    r.setValue(encoded);
//    try {
//      n.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
    Navajo m = doSimpleSend(n,"ProcessUpdateScript",null);
    try {
      m.write(System.err);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }

  }
  public void deleteScript(String scriptName) {
    Navajo n = doSimpleSend(NavajoFactory.getInstance().createNavajo(),"InitDeleteScript",null);
    Property name = n.getProperty("/DeleteScript/ScriptName");
    name.setValue(scriptName);
    Navajo p = doSimpleSend(n,"ProcessDeleteScript",null);

   }

   public void loadServerSettingsFromProperties() {
     String impl = System.getProperty("tipi.client.impl");

     if ("direct".equals(impl)) {
       System.err.println("********* FOR NOW: Only supports indirect client *******");
     }
     String cfg = System.getProperty("tipi.client.config");
//     String secure = System.getProperty("tipi.client.impl");
     String keystore = System.getProperty("tipi.client.keystore");
     String storepass = System.getProperty("tipi.client.storepass");

     String navajoServer = System.getProperty("tipi.client.server");
     String navajoUsername = System.getProperty("tipi.client.username");
     String navajoPassword = System.getProperty("tipi.client.password");

     NavajoClientFactory.getClient().setUsername(navajoUsername);
     NavajoClientFactory.getClient().setPassword(navajoPassword);
     NavajoClientFactory.getClient().setServerUrl(navajoServer);
   }

public boolean isInitScript(String name) {
  if (name==null) {
    return false;
  }
  int ix = name.lastIndexOf("/");
  if (ix==-1) {
    return name.startsWith("Init");
  }
  return name.substring(ix+1).startsWith("Init");
}

public String getParentScriptName(String dest) {
  return (String)navajoReverseMap.get(dest);
}

/**
 * associate destination with source, so, ProcessQueryMember should return: InitUpdateMember
 * (or something like that). Used to check if a script can be run or not.
 */
public void associateScripts(String dest, String source) {
  navajoReverseMap.put(dest,source);
}

}
