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

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiContext implements ResponseListener, TipiLink {

  public static final int UI_MODE_APPLET = 1;
  public static final int UI_MODE_FRAME = 2;
  public static final int UI_MODE_STUDIO = 3;

  private static TipiContext instance;
  private Map tipiMap = new HashMap();
  private Map tipiServiceMap = new HashMap();
  private Map tipiInstanceMap = new HashMap();
  private Map tipiComponentMap = new HashMap();
  private Map tipiClassMap = new HashMap();
  private Map tipiClassDefMap = new HashMap();
  private Map tipiActionDefMap = new HashMap();
  private Map tipiActionInstanceMap = new HashMap();
  private Map commonTypesMap = new HashMap();
  private Map reservedTypesMap = new HashMap();
  private Tipi topScreen;
  private RootPaneContainer myTopLevel = null;
  private TipiErrorHandler eHandler;
  private int internalMode = UI_MODE_FRAME;
  private String errorHandler;
  private JDialog waitDialog = null;
  private ArrayList rootPaneList = new ArrayList();
  private ArrayList screenDefList = new ArrayList();
  private ArrayList screenList = new ArrayList();
  private com.dexels.navajo.tipi.impl.DefaultTipiSplash splash;
  private URL imageBaseURL = null;
  private Thread startUpThread = null;
  private TipiComponent currentComponent;

  public TipiContext() {
    startUpThread = Thread.currentThread();
    System.err.println("CLIENT URL: "+getClass().getClassLoader().getResource("server.xml"));
    NavajoClientFactory.createClient("com.dexels.navajo.client.impl.DirectClientImpl",getClass().getClassLoader().getResource("server.xml"));

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

  public void handleException(Exception e){
    if(eHandler != null){
      eHandler.showError(e);
    }
  }

  public void setSplash(DefaultTipiSplash s){
    splash = s;
  }

  public void setToplevel(RootPaneContainer tl) {
    myTopLevel = tl;
  }

  public void parseFile(String location) throws IOException, XMLParseException, TipiException {
    parseStream(new FileInputStream(location));
  }
  public void parseURL(URL location) throws IOException, XMLParseException,
    TipiException {
    System.err.println("Opening: " + location.toString());
    parseStream(location.openStream());
  }

  private void clearResources(){
    tipiMap = new HashMap();
    tipiServiceMap = new HashMap();
    tipiInstanceMap = new HashMap();
    tipiComponentMap = new HashMap();
    tipiClassMap = new HashMap();
    tipiClassDefMap = new HashMap();
    commonTypesMap.clear();
    reservedTypesMap.clear();
    topScreen = null;
    myTopLevel = null;
    eHandler = null;
    internalMode = UI_MODE_FRAME;
    errorHandler = null;
    waitDialog = null;
    rootPaneList = new ArrayList();
    screenDefList = new ArrayList();
    screenList = new ArrayList();
    imageBaseURL = null;
    Runtime runtimeObject = Runtime.getRuntime();
    runtimeObject.traceInstructions(false);
    runtimeObject.traceMethodCalls(false);
    runtimeObject.runFinalization();
    runtimeObject.gc();
  }

  public void parseStream(InputStream in) throws IOException, XMLParseException,
      TipiException {
    clearResources();
    XMLElement doc = new CaseSensitiveXMLElement();
    doc.parseFromReader(new InputStreamReader(in));
    parseXMLElement(doc);
    Class initClass = (Class)tipiClassMap.get("init");
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
    catch(ClassCastException ex){
      ex.printStackTrace();

    }

    for (int i = 0; i < screenDefList.size(); i++) {
      setSplashInfo("Instantiating topscreen");
      topScreen = (Tipi) instantiateComponent( (XMLElement) screenDefList.get(i));
      screenList.add(topScreen);
      if(splash != null){
        splash.setVisible(false);
        splash = null;
      }
      topScreen.getContainer().setVisible(true);
    }
    if (errorHandler != null) {
      try {
        Class c = getTipiClass(errorHandler);
        eHandler = (TipiErrorHandler) c.newInstance();
        eHandler.setContext(this);
      }
      catch (Exception e) {
        System.err.println("Error instantiating TipiErrorHandler!");
      }
    }
  }

  public URL getResourceURL() {
    return imageBaseURL;
  }

  public void setResourceURL(URL u) {
    imageBaseURL = u;
  }

  public void setSplashInfo(String info){
    if(splash != null){
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

      if (childName.equals("tipi")) {
        addTipiDefinition(child);
      }
      if (childName.equals("component")) {
        addComponentDefinition(child);
      }
      if (childName.equals("tipiclass")) {
        addTipiClassDefinition(child);
      }
      if(childName.equals("tipi-types")){
        parseTypes(child);
      }
      if (childName.equals("tipiaction")) {
        addActionDefinition(child);
      }
      if (childName.equals("screen-instance")) {
        screenDefList.add(child);
      }
      if (childName.equals("tipi-include")) {
        parseLibrary(child);
      }
    }
  }

  private void parseTypes(XMLElement elm){
    try{
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
    }catch(Exception e){
      e.printStackTrace();
      throw new RuntimeException("Unknown class specified");
    }
  }

  public Class getCommonTypeClass(String type){
    return (Class)commonTypesMap.get(type);
  }

  public Class getReservedTypeClass(String type){
    return (Class)reservedTypesMap.get(type);
  }

  private void parseLibrary(XMLElement lib) {
    try {
      String location = (String) lib.getAttribute("location");
      if (location != null) {
        URL loc = MainApplication.class.getResource(location);
        if (loc != null) {
          InputStream in = loc.openStream();
          XMLElement doc = new CaseSensitiveXMLElement();
          doc.parseFromReader(new InputStreamReader(in));
          parseXMLElement(doc);
        }else{
          System.err.println("Library file not found!!");
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getUIMode() {
    return internalMode;
  }

  public void setUIMode(int value) {
    internalMode = value;
  }

  public TipiCondition instantiateTipiCondition(XMLElement definition, TipiComponent parent, TipiEvent event) throws TipiException {
    TipiCondition c = createTipiCondition();
    c.load(definition, parent, event);
    return c;
  }

  public TipiAction instantiateTipiAction(XMLElement definition, TipiComponent parent, TipiEvent event) throws TipiException {
    String type = (String)definition.getAttribute("type");
    if (type==null) {
      throw new TipiException("Undefined action type in: "+definition.toString());
    }
    TipiAction a = createTipiAction();
    a.load(definition, parent, event);
    return a;
  }

  public TipiLayout instantiateLayout(XMLElement instance) throws TipiException {
    String type = (String) instance.getAttribute("type");
    return (TipiLayout) instantiateClass(type, null, instance);
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
    if (tc.getContainer()!=null) {
      if (RootPaneContainer.class.isInstance(tc.getContainer())) {
        rootPaneList.add(tc);
      }
    }
    tc.loadStartValues(instance);
    if (tc instanceof DefaultTipi) {
        ((DefaultTipi) tc).autoLoadServices(this);
    }
    return tc;
  }

  public void disposeTipi(TipiComponent comp) {
    if (comp==null) {
      System.err.println("Can not dispose null tipi!");
      return;
    }
    if (comp.getParent()==null) {
      System.err.println("Can not dispose tipi: It has no parent!");
      return;
    }
    comp.getParent().disposeChild(comp);
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
      tc.setContainer(tc.createContainer());
      tc.instantiateComponent(instance, classDef);
      if (tipiDefinition != null) {
        tc.load(tipiDefinition, instance, this);
      }
      else {
        tc.load(instance, instance, this);
        tc.loadEventsDefinition(this, instance, classDef);
      }
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

  public void addTipiClassDefinition(XMLElement xe) throws TipiException {
    String pack = (String) xe.getAttribute("package");
    String name = (String) xe.getAttribute("name");
    String clas = (String) xe.getAttribute("class");
    String fullDef = pack + "." + clas;
    setSplashInfo("Adding: " + fullDef);
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
    String pack = (String) xe.getAttribute("package");
    String name = (String) xe.getAttribute("name");
    String clas = (String) xe.getAttribute("class");
    String fullDef = pack + "." + clas;
    setSplashInfo("Adding action: " + fullDef);
    try {
      Class c = Class.forName(fullDef);
      tipiActionDefMap.put(name, xe);
    }
    catch (ClassNotFoundException ex) {
      System.err.println("Trouble loading action class: "+fullDef);
    }
  }

  public TipiAction getTipiAction(String name) throws TipiException  {
    TipiAction t = (TipiAction)tipiActionInstanceMap.get(name);
    if (t!=null) {
      return t;
    }
    XMLElement actionDef = (XMLElement)tipiActionDefMap.get(name);
    if (actionDef==null) {
      throw new TipiException("Unknown action: "+name);
    }
    String pack = (String) actionDef.getAttribute("package");
    String clas = (String) actionDef.getAttribute("class");
    String fullDef = pack + "." + clas;
    Class c;
    try {
      c = Class.forName(fullDef);
    }
    catch (ClassNotFoundException ex) {
      throw new TipiException("Error instantiating tipi action: Class: "+fullDef+" not found!");
    }
    try {
      t = (TipiAction) c.newInstance();
    }
    catch (InstantiationException ex1) {
      throw new TipiException("Error instantiating tipi action: Class: "+fullDef+" can not be instantiated: "+ex1.getMessage());
    }
    catch (IllegalAccessException ex1) {
      throw new TipiException("Error instantiating tipi action: Class: "+fullDef+" can not be instantiated: "+ex1.getMessage());
    }
    return t;
  }

  public void addTipiInstance(String service, Tipi instance) {
    if (tipiInstanceMap.containsKey(service)) {
      ArrayList al = (ArrayList) tipiInstanceMap.get(service);
      al.add(instance);
    } else {
      ArrayList al = new ArrayList();
      al.add(instance);
      tipiInstanceMap.put(service, al);
    }
  }

  public void removeTipiInstance(TipiComponent instance) {
    Iterator c = tipiInstanceMap.values().iterator();
    while(c.hasNext()) {
      ArrayList current = (ArrayList)c.next();
      if (current.contains(instance)) {
        current.remove(instance);
      }

    }
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

  public TipiMenubar createTipiMenubar() {
    return new TipiMenubar();
  }

  private TipiPopupMenu createTipiPopup() {
    return new TipiPopupMenu();
  }

  private TipiEvent createTipiEvent() {
    return new TipiEvent();
  }

  private TipiCondition createTipiCondition(){
    return new DefaultTipiCondition();
  }

  private TipiAction createTipiAction() {
    return new DefaultTipiAction();
  }

  public ArrayList getScreens() {
    return screenList;
  }

  public RootPaneContainer getTopLevel() {
    return myTopLevel;
  }

  public Tipi getTopScreen(String name) {
    for (int i = 0; i < screenList.size(); i++) {
      Tipi t= (Tipi)screenList.get(i);
      if (name.equals(t.getName())) {
        return t;
      }
    }
    return null;
  }

  public void closeAll(){
    for (int i = 0; i < screenList.size(); i++) {
      Tipi t= (Tipi)screenList.get(i);
      Window w = (Window)t.getContainer();
      w.hide();
    }
    screenList.clear();
    screenDefList.clear();
    tipiMap.clear();
    tipiServiceMap.clear();
    tipiInstanceMap.clear();
    tipiClassMap.clear();
    tipiClassDefMap.clear();
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
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    int s = path.indexOf("/");
    if (s == -1) {
      return (TipiComponent)getTopScreen(path);
    }
    else {
      String name = path.substring(0, s);
      String rest = path.substring(s);
      Tipi t = getTopScreen(name);
      if (t == null) {
        return null;
      }
      return t.getTipiComponentByPath(rest);
    }
  }

  public Tipi getTipiByPath(String path) {
    TipiComponent tc = getTipiComponentByPath(path);
    if (!Tipi.class.isInstance(tc)) {
      System.err.println("Object referred to by path: "+path+" is a TipiComponent, not a Tipi");
      return null;
    }
    return (Tipi)tc;
  }


  public void enqueueAsyncSend(Navajo n, String service) {
    setWaiting(true);
    System.err.println("Starting service "+service);
    try {
      NavajoClientFactory.getClient().doAsyncSend(n, service, this, "");
    }
    catch (ClientException ex) {
      if(eHandler != null){
        eHandler.showError(ex);
      }
      ex.printStackTrace();
    }
  }


  public Navajo doSimpleSend(Navajo n, String service) {
    Navajo reply = null;
    try {
      reply = NavajoClientFactory.getClient().doSimpleSend(n, service);
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

  public void performTipiMethod(Tipi t, String method) throws TipiException {
    enqueueAsyncSend(t.getNavajo(),method);
  }

  public void performMethod(String service) throws TipiException {
      enqueueAsyncSend(NavajoFactory.getInstance().createNavajo(),service);
  }

  public void loadTipiMethod(Navajo reply, String method) throws TipiException {
    System.err.println("LoadTPMethod: "+method);
    Tipi tt;
    ArrayList tipiList;
    try {
      tipiList = getTipiInstancesByService(method);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
      return;
    }
    if (tipiList == null) {
      System.err.println("Whoops! no tipi's");
      return;
    }
    for (int i = 0; i < tipiList.size(); i++) {
      Tipi t = (Tipi) tipiList.get(i);
      t.loadData(reply, this);
      if (t.getContainer()!=null) {
        t.getContainer().repaint();
      }
    }
  }

  public ImageIcon getIcon(String name) {
    ImageIcon i = new ImageIcon(MainApplication.class.getResource(name));
    return i;
  }

  public void receive(Navajo n, String method, String id) {
    if (eHandler != null) {
      if (eHandler.hasErrors(n)) {
        eHandler.showError();
        try {
          ArrayList tipis = getTipiInstancesByService(method);
          if (tipis != null) {
            for (int i = 0; i < tipis.size(); i++) {
              Tipi current = (Tipi) tipis.get(i);
              current.loadErrors(n);
            }
          }
        }
        catch (TipiException ex1) {
          ex1.printStackTrace();
        }
        if (NavajoClientFactory.getClient().getPending() == 0) {
          setWaiting(false);
        }
        return;
      }

    }
        try {
          loadTipiMethod(n, method);
        }
        catch (TipiException ex) {
          ex.printStackTrace();
        }
    if (NavajoClientFactory.getClient().getPending() == 0) {
      setWaiting(false);
    }
  }

  public void showErrorDialog(String error){
    final JFrame top = (JFrame)getTopLevel();
    final String errorString = error;
    SwingUtilities.invokeLater(new Runnable(){
     public void run(){
       JOptionPane.showMessageDialog(top, errorString, "Error", JOptionPane.ERROR_MESSAGE);
     }
    });
  }

  public void setCurrentComponent(TipiComponent c){
    currentComponent = c;
    //System.err.println("Current component set: " + c.getName());
  }

  public Object evaluateExpression(String expression) throws Exception{
    //System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-===>>>> Evaluating: " + expression);
    Object obj = null;
    if(expression.startsWith("@")){
      String path = expression.substring(1);
      if(path.startsWith("?")){
        obj  = new Boolean(exists(currentComponent, path.substring(1)));
      }else if(path.startsWith("!?")){
        obj = new Boolean(!exists(currentComponent, path.substring(2)));
      }else{
        System.err.println("Evaluating relative to: " + currentComponent.getName());
        TipiPathParser pp = new TipiPathParser(currentComponent , this, path);
        if(pp.getPathType() != pp.PATH_TO_ATTRIBUTE && pp.getPathType() != pp.PATH_TO_PROPERTY){
          throw new Exception("Only use PATH_TO_PROPERTTY or PATH_TO_ATTRIBUTE for expressions other than (!)?");
        }else{
          if(pp.getPathType() == pp.PATH_TO_ATTRIBUTE){
            //System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for attribute");
            obj = pp.getAttribute();
          }
          if(pp.getPathType() == pp.PATH_TO_PROPERTY){
            //System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>> Looking for property");
            obj = pp.getProperty().getTypedValue();
          }
        }
      }
    }else{
      throw new Exception("Trying to evaluate a path that is not a tipipath: " + expression);
    }
    System.err.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-===>>> Returning: " + obj);
    return obj;
  }

  private boolean exists(TipiComponent source, String path){
    try{
      TipiPathParser pp = new TipiPathParser(source, TipiContext.getInstance(), path);
      if(pp.getPathType() == pp.PATH_TO_ATTRIBUTE){
        if(pp.getAttribute() != null){
          return true;
        }
      }
      if(pp.getPathType() == pp.PATH_TO_COMPONENT){
        if(pp.getComponent() != null){
          return true;
        }
      }
      if(pp.getPathType() == pp.PATH_TO_MESSAGE){
        if(pp.getMessage() != null){
          return true;
        }
      }
      if(pp.getPathType() == pp.PATH_TO_PROPERTY){
        if(pp.getProperty() != null){
          return true;
        }
      }
      if(pp.getPathType() == pp.PATH_TO_TIPI){
        if(pp.getTipi() != null){
          return true;
        }
      }
    }catch(Exception e){
      e.printStackTrace();
      return false;
    }
    return false;
  }

  public Object getCurrentComponent(){
    return currentComponent;
  }

  public synchronized void setWaiting(boolean b) {
    for (int i = 0; i < rootPaneList.size(); i++) {
      TipiComponent tc = (TipiComponent)rootPaneList.get(i);
      if (DefaultTipiScreen.class.isInstance(tc)) {
      }
      tc.getContainer().setCursor(b?Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR):Cursor.getDefaultCursor());

    }
  }

  public void storeComponentTree(){
    try{
      FileWriter fw = new FileWriter("c:/tree.xml");
      System.err.println("scerrenlistrre: " + screenList.size());
      for (int i = 0; i < screenList.size(); i++) {
        TipiComponent current = (TipiComponent) screenList.get(i);
        XMLElement tree = current.store();
        System.err.println("Tree: " + tree.toString());
        tree.write(fw);
      }
      fw.flush();
      fw.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

 //EOF
}
