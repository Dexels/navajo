package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.client.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import com.dexels.navajo.tipi.studio.ComponentSelectionListener;
import com.dexels.navajo.tipi.studio.StudioListener;
import com.dexels.navajo.parser.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiContext
    implements ResponseListener, TipiLink, StudioListener {
  public static final int UI_MODE_APPLET = 1;
  public static final int UI_MODE_FRAME = 2;
  public static final int UI_MODE_STUDIO = 3;
  private static final String BASEURL = "tipi/";
  private static TipiContext instance;
  private Map tipiMap = new HashMap();
  private Map tipiServiceMap = new HashMap();
  private Map tipiInstanceMap = new HashMap();
  private Map tipiComponentMap = new HashMap();
  private Map tipiClassMap = new HashMap();
  private Map tipiClassDefMap = new HashMap();
  private Map tipiActionDefMap = new HashMap();
//  private Map tipiActionInstanceMap = new HashMap();
  private Map commonTypesMap = new HashMap();
  private Map reservedTypesMap = new HashMap();
  private DefaultTipiScreen topScreen = new DefaultTipiScreen();
//  private RootPaneContainer myTopLevel = null;
  private ArrayList includeList = new ArrayList();
//  private Tipi topScreen;
  private RootPaneContainer myTopLevel = null;
  private TipiErrorHandler eHandler;
  private int internalMode = UI_MODE_FRAME;
  private String errorHandler;
  private JDialog waitDialog = null;
  private ArrayList rootPaneList = new ArrayList();
//  private ArrayList screenDefList = new ArrayList();
  private ArrayList screenList = new ArrayList();
  private com.dexels.navajo.tipi.impl.DefaultTipiSplash splash;
//  private URL imageBaseURL = null;
  private TipiComponent currentComponent;
  private TipiActionManager myActionManager = new TipiActionManager();
  private ArrayList myTipiStructureListeners = new ArrayList();
  private XMLElement clientConfig = null;
  private boolean studioMode = false;
  public TipiContext() {
  }

  public static TipiContext getInstance() {
    if (instance != null) {
      return instance;
    }
    else {
      instance = new TipiContext();
      return instance;
    }
  }

  public void handleException(Exception e) {
    if (eHandler != null) {
      eHandler.showError(e);
    }
  }

  public void setStudioMode(boolean b) {
    studioMode = b;
  }

  public void setSplash(DefaultTipiSplash s) {
    splash = s;
  }

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
//<<<<<<< TipiContext.java
    if (topScreen != null) {
      topScreen.clearTopScreen();
    }
//    myTopLevel = null;
//=======
    includeList.clear();
//    topScreen = null;
//    myTopLevel = null;
//>>>>>>> 1.153
    eHandler = null;
//    internalMode = UI_MODE_FRAME;
    errorHandler = null;
    waitDialog = null;
    rootPaneList = new ArrayList();
//    screenDefList = new ArrayList();
    screenList = new ArrayList();
//    imageBaseURL = null;
    Runtime runtimeObject = Runtime.getRuntime();
    runtimeObject.traceInstructions(false);
    runtimeObject.traceMethodCalls(false);
    runtimeObject.runFinalization();
    runtimeObject.gc();
  }

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
//      System.err.println("Using DIRECT client");
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
    System.err.println("Parsing file: " + sourceName);
    doc.parseFromReader(new InputStreamReader(in, "UTF-8"));
    parseXMLElement(doc);
    /** @todo Think about this one */
    Class initClass = (Class) tipiClassMap.get("init");
    try {
      if (initClass != null) {
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
        eHandler = (TipiErrorHandler) c.newInstance();
        eHandler.setContext(this);
      }
      catch (Exception e) {
        System.err.println("Error instantiating TipiErrorHandler!");
        e.printStackTrace();
      }
    }
  }

//  public URL getResourceURL() {
//    return imageBaseURL;
//  }
//  public void setResourceURL(URL u) {
//    imageBaseURL = u;
//  }
  public void setSplashInfo(String info) {
    if (splash != null) {
      splash.setInfoText(info);
    }
  }

  private void parseXMLElement(XMLElement elm) throws TipiException {
    String elmName = elm.getName();
    setSplashInfo("Loading screens");
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
    return getClass().getClassLoader().getResource(BASEURL + location);
  }

  private void parseLibrary(XMLElement lib) {
    try {
      String location = (String) lib.getAttribute("location");
      includeList.add(location);
//      System.err.println("Loading library: " + location);
      if (location != null) {
        URL loc = getResourceURL(location);
        if (loc != null) {
          InputStream in = loc.openStream();
          XMLElement doc = new CaseSensitiveXMLElement();
          doc.parseFromReader(new InputStreamReader(in, "UTF-8"));
          parseXMLElement(doc);
        }
        else {
          System.err.println("Library file not found!!");
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

//
//  public int getUIMode() {
//    return internalMode;
//  }
//
//  public void setUIMode(int value) {
//    internalMode = value;
//  }
//
  public TipiCondition instantiateTipiCondition(XMLElement definition, TipiComponent parent, TipiEvent event) throws TipiException {
    TipiCondition c = createTipiCondition();
    c.load(definition, parent, event);
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
      if (RootPaneContainer.class.isInstance(tc.getContainer())) {
        rootPaneList.add(tc);
      }
    }
    tc.loadStartValues(instance);
    fireTipiStructureChanged();
    tc.componentInstantiated();
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
    comp.getTipiParent().removeChild(comp);
    if (comp instanceof Tipi) {
      removeTipiInstance(comp);
    }
    killComponent(comp);
    fireTipiStructureChanged();
  }

  private Object instantiateClass(String className, String defname, XMLElement instance) throws TipiException {
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
      tc.initContainer();
      tc.instantiateComponent(instance, classDef);
      if (tipiDefinition != null) {
        tc.load(tipiDefinition, instance, this);
      }
      else {
        tc.load(instance, instance, this);
        tc.loadEventsDefinition(this, instance, classDef);
      }
//      System.err.println("Instantiating class: "+className+" def: "+defname);
//      tc.setContainerVisible(true);
      return tc;
    }
    if (TipiLayout.class.isInstance(o)) {
      TipiLayout tl = (TipiLayout) o;
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

  public void addActionDefinition(XMLElement xe) throws TipiException {
    myActionManager.addAction(xe, this);
  }

  public void addTipiInstance(String service, Tipi instance) {
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

  private ArrayList getTipiInstancesByService(String service) throws TipiException {
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

  private TipiCondition createTipiCondition() {
    return new DefaultTipiCondition();
  }

  public ArrayList getScreens() {
    return screenList;
  }

  public RootPaneContainer getTopLevel() {
    return topScreen.getTopLevel();
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
  public DefaultTipiScreen getDefaultTopLevel() {
    return (DefaultTipiScreen) topScreen;
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

  protected void clearTipiAllInstances() {
    for (int i = 0; i < screenList.size(); i++) {
      Tipi t = (Tipi) screenList.get(i);
      Window w = (Window) t.getContainer();
      w.hide();
    }
    tipiInstanceMap.clear();
//    Iterator loop = tipiInstanceMap.keySet().iterator();
//    while (loop.hasNext()) {
//      String id = (String)loop.next();
//      System.err.println(">>>>> "+tipiInstanceMap.get(id).getClass());
//      TipiComponent tc = (TipiComponent)tipiInstanceMap.get(id);
//      if (!tc.isStudioElement()) {
//        tipiInstanceMap.remove(tc);
//      }
//
//    }
    topScreen.disposeComponent();
    for (int i = 0; i < topScreen.getTipiCount(); i++) {
      TipiComponent current = topScreen.getTipiComponent(i);
//      current.disposeComponent();
      if (!current.isStudioElement()) {
        disposeTipiComponent(current);
      }
    }
  }

  private void instantiateStudio() throws TipiException {
//    System.err.println("Instantiating COMPONENT\n");
    TipiComponent tc = instantiateComponent(getComponentDefinition("studio"));
    tc.setStudioElement(true);
    topScreen.addComponent(tc, this, null);
    topScreen.addToContainer(tc.getContainer(), null);
  }

  public void switchToDefinition(String name) throws TipiException {
    System.err.println("Attempting to switch to def: " + name);
//    clearTipiAllInstances();
    if (topScreen != null) {
      topScreen.clearTopScreen();
    }
    else {
      System.err.println("No topscreen, so can not reset screen after switch");
    }
    setSplashInfo("Instantiating topscreen");
//    System.err.println("Instantiating COMPONENT\n");
    TipiComponent tc = instantiateComponent(getComponentDefinition(name));
//    System.err.println("FINISHED Instantiating COMPONENT\n");
    topScreen.addComponent(tc, this, null);
    topScreen.addToContainer(tc.getContainer(), null);
    ((Tipi)tc).autoLoadServices(this);
//    screenList.add(topScreen);
    if (splash != null) {
      splash.setVisible(false);
      splash = null;
    }
    topScreen.autoLoadServices(this);
//    topScreen.getContainer().setVisible(true);
    fireTipiStructureChanged();
  }

  public Message getMessageByPath(String path) {
    TipiPathParser pp = new TipiPathParser(null, this, path);
    return pp.getMessage();
  }

  public Property getPropertyByPath(String path) {
    TipiPathParser pp = new TipiPathParser(null, this, path);
    return pp.getProperty();
  }

  public TipiComponent getTipiComponentByPath(String path) {
//    System.err.println("Locating: " + path);
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    return getDefaultTopLevel().getTipiComponentByPath(path);
//    int s = path.indexOf("/");
//    if (s == -1) {
//      return  getDefaultTopLevel().getTipiComponentByPath(path);
//    }
//    else {
//      String name = path.substring(0, s);
//      String rest = path.substring(s);
//      Tipi t = (Tipi)getTipiComponentByPath(name);
//      if (t == null) {
//        return null;
//      }
//      return t.getTipiComponentByPath(rest);
//    }
  }

  public Tipi getTipiByPath(String path) {
    TipiComponent tc = getTipiComponentByPath(path);
    if (!Tipi.class.isInstance(tc)) {
      System.err.println("Object referred to by path: " + path + " is a TipiComponent, not a Tipi");
      return null;
    }
    return (Tipi) tc;
  }

  public void enqueueAsyncSend(Navajo n, String tipiDestinationPath, String service, ConditionErrorHandler ch) {
    setWaiting(true);
    try {
      NavajoClientFactory.getClient().doAsyncSend(n, service, this, tipiDestinationPath, ch);
    }
    catch (ClientException ex) {
      if (eHandler != null) {
        eHandler.showError(ex);
      }
      ex.printStackTrace();
    }
  }

  public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch) {
    // Doe iets met die CONDITIONERRORHANDLER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    Navajo reply = null;
    try {
      reply = NavajoClientFactory.getClient().doSimpleSend(n, service, ch);
      //reply = NavajoClientFactory.getClient().doSimpleSend(n, service);
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

  public void performTipiMethod(Tipi t, Navajo n, String tipiDestinationPath, String method) throws TipiException {
    enqueueAsyncSend(n, tipiDestinationPath, method, (TipiComponent) t);
  }

  private void loadTipiMethod(Navajo reply, String tipiDestinationPath, String method) throws TipiException {
//    System.err.println("LoadTPMethod: " + tipiDestinationPath + ", method = " + method);
    Tipi tt;
    ArrayList tipiList;
//    try {
    tipiList = getTipiInstancesByService(method);
    if (tipiList == null) {
      System.err.println("Null tipi list");
      return;
    }
    if (tipiList != null) {
//      System.err.println("FOUND " + tipiList.size() + " TIPI's THAT ARE LISTENING");
//    }
//    catch (TipiException ex) {
//      ex.printStackTrace();
//      return;
//    }
//    if (tipiList == null) {
//      return;
//    }
    }
    for (int i = 0; i < tipiList.size(); i++) {
      Tipi t = (Tipi) tipiList.get(i);
//      System.err.println("Calling loadData on " + t.getId()+" class: "+t.getClass());
      t.loadData(reply, this);
      if (t.getContainer() != null) {
        t.tipiLoaded();
      }
    }
  }

  public ImageIcon getIcon(String name) {
    System.err.println("Retrieving icon: "+name);
    URL u = getResourceURL(name);
    if (u == null) {
      return null;
    }
    return getIcon(u);
  }

  public ImageIcon getIcon(URL u) {
    ImageIcon i = new ImageIcon(u);
    return i;
  }

  public void receive(Navajo n, String method, String id) {
//    System.err.println("RECEIVING NAVAJO. METHOD: "+method);
//    try {
//      n.write(System.err);
//    }
//    catch (NavajoException ex2) {ex2.printStackTrace();
//    }
    if (eHandler != null) {
      if (eHandler.hasErrors(n)) {
        boolean hasUserDefinedErrorHandler = false;
        try {
          ArrayList tipis = getTipiInstancesByService(method);
          if (tipis != null) {
//            System.err.println("# of tipis found: "+tipis.size()+" using method: "+method);
            for (int i = 0; i < tipis.size(); i++) {
              Tipi current = (Tipi) tipis.get(i);
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
        if (NavajoClientFactory.getClient().getPending() == 0) {
          setWaiting(false);
        }
        return;
      }
    }
    try {
      loadTipiMethod(n, id, method);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
    if (NavajoClientFactory.getClient().getPending() == 0) {
      setWaiting(false);
    }
  }

  public void showErrorDialog(String error) {
    final JFrame top = (JFrame) topScreen.getContainer();
    final String errorString = error;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JOptionPane.showMessageDialog(top, errorString, "Error", JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  public void setCurrentComponent(TipiComponent c) {
    currentComponent = c;
  }

  public void resetConditionRuleById(String id) {
    //System.err.println("Resetting conditionErrors for rule: " + id);
    for (int i = 0; i < screenList.size(); i++) {
      // Instances
      TipiComponent current = (TipiComponent) screenList.get(i);
      current.resetComponentValidationStateByRule(id);
    }
  }

  /** Made it synchronized. Not sure if it is necessary, but I think it can cause problems otherwise */
  public synchronized Operand evaluate(String expr, TipiComponent tc) {
    Operand o = null;
    try {
      setCurrentComponent(tc);
      o = Expression.evaluate(expr, tc.getNearestNavajo(), null, null, null, this);
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

  public Object evaluateExpression(String expression) throws Exception {
    return evaluateExpression(expression, currentComponent);
  }

  public Object evaluateExpression(String expression, TipiComponent tc) throws Exception {
//    System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-===>>>> Evaluating: " + expression);
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
        TipiPathParser pp = new TipiPathParser(tc, this, path);
        if (pp.getPathType() == pp.PATH_TO_ATTRIBUTEREF) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for attributeref");
          obj = pp.getAttributeRef();
        }
        if (pp.getPathType() == pp.PATH_TO_ATTRIBUTE) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for attribute");
          obj = pp.getAttribute();
        }
        if (pp.getPathType() == pp.PATH_TO_PROPERTY) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for property");
          obj = pp.getProperty().getTypedValue();
        }
        if (pp.getPathType() == pp.PATH_TO_MESSAGE) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for property");
          obj = pp.getMessage();
        }
        if (pp.getPathType() == pp.PATH_TO_PROPERTYREF) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for property reference");
          obj = pp.getProperty();
        }
        if (pp.getPathType() == pp.PATH_TO_COMPONENT) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for component");
          obj = pp.getComponent();
        }
        if (pp.getPathType() == pp.PATH_TO_TIPI) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for tipi");
          obj = pp.getTipi();
        }
        if (pp.getPathType() == pp.RESOURCE_DEF) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for resource");
          obj = pp.getObject();
        }
        if (pp.getPathType() == pp.URL_DEF) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for resource");
          obj = pp.getObject();
        }
        if (pp.getPathType() == pp.BORDER_DEF) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for resource");
          obj = pp.getObject();
        }
        if (pp.getPathType() == pp.COLOR_DEF) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for resource");
          obj = pp.getObject();
        }
        if (pp.getPathType() == pp.FONT_DEF) {
//            System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for resource");
          obj = pp.getObject();
        }
      }
    }
    else {
      System.err.println("Trying to evaluate a path that is not a tipipath: " + expression);
      return expression;
    }
//    if (obj==null) {
//      System.err.println("Returning null object.");
//    } else {
//      System.err.println(obj.getClass());
//    }
//
//    System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>>> Returning: " + obj);
    return obj;
  }

  private boolean exists(TipiComponent source, String path) {
    try {
      TipiPathParser pp = new TipiPathParser(source, TipiContext.getInstance(), path);
      if (pp.getPathType() == pp.PATH_TO_ATTRIBUTE) {
        if (pp.getAttribute() != null) {
          return true;
        }
      }
      if (pp.getPathType() == pp.PATH_TO_COMPONENT) {
        if (pp.getComponent() != null) {
          return true;
        }
      }
      if (pp.getPathType() == pp.PATH_TO_MESSAGE) {
        if (pp.getMessage() != null) {
          return true;
        }
      }
      if (pp.getPathType() == pp.PATH_TO_PROPERTY) {
        if (pp.getProperty() != null) {
          return true;
        }
      }
      if (pp.getPathType() == pp.PATH_TO_TIPI) {
        if (pp.getTipi() != null) {
          return true;
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }

  public Object getCurrentComponent() {
    return currentComponent;
  }

  public synchronized void setWaiting(boolean b) {
    for (int i = 0; i < rootPaneList.size(); i++) {
      TipiComponent tc = (TipiComponent) rootPaneList.get(i);
      tc.getContainer().setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
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
//      for (int i = 0; i < screenList.size(); i++) {
    // Instances
//        TipiComponent current = (TipiComponent) screenList.get(i);
    return getDefaultTopLevel().getTipiComponent(definition).store();
    // Definitions, maar die willen we ff niet
//      }
  }

  public XMLElement getComponentTree() {
    try {
      XMLElement root = new CaseSensitiveXMLElement();
      root.setName("tid");
      root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
      root.setAttribute("xsi:noNamespaceSchemaLocation", "tipiscript.xsd");
      root.setAttribute("errorhandler", "error");
      for (int j = 0; j < includeList.size(); j++) {
        String location = (String) includeList.get(j);
        XMLElement inc = new CaseSensitiveXMLElement();
        inc.setName("tipi-include");
        inc.setAttribute("location", location);
        root.addChild(inc);
      }
      if (clientConfig != null) {
        root.addChild(clientConfig);
      }
//      System.err.println("screenlist size: " + screenList.size());
      Iterator it = tipiComponentMap.keySet().iterator();
      while (it.hasNext()) {
        String name = (String) it.next();
        XMLElement current = (XMLElement) tipiComponentMap.get(name);
        boolean se = current.getAttribute("studioelement") != null;
        if (!se) {
          root.addChild(current);
        }
      }
//      for (int i = 0; i < screenList.size(); i++) {
//        // Instances
//        TipiComponent current = (TipiComponent) screenList.get(i);
//        root.addChild(current.store());
//        // Definitions, maar die willen we ff niet
//      }
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
    xe.setAttribute("name",definition);
    xe.setAttribute("class",classId);
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

  protected void fireTipiStructureChanged() {
    for (int i = 0; i < myTipiStructureListeners.size(); i++) {
      TipiStructureListener current = (TipiStructureListener) myTipiStructureListeners.get(i);
      current.tipiStructureChanged();
    }
  }

  public void addTipiDefinitionListener(TipiDefinitionListener cs) {
    myTipiDefinitionListeners.add(cs);
  }

  public void removeTipiDefinitionListener(TipiDefinitionListener cs) {
    myTipiDefinitionListeners.remove(cs);
  }

  protected void fireTipiDefinitionChanged() {
    for (int i = 0; i < myTipiDefinitionListeners.size(); i++) {
      TipiDefinitionListener current = (TipiDefinitionListener) myTipiDefinitionListeners.get(i);
      current.tipiDefinitionChanged();
    }
  }

  private ArrayList myTipiDefinitionListeners = new ArrayList();
  //EOF
}
