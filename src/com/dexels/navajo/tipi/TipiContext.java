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

public class TipiContext implements ResponseListener {

  public static final int UI_MODE_APPLET = 1;
  public static final int UI_MODE_FRAME = 2;
  public static final int UI_MODE_STUDIO = 3;

  private static TipiContext instance;
//  private Map screenMap = new HashMap();
  private Map tipiMap = new HashMap();
  private Map tipiServiceMap = new HashMap();
  private Map tipiInstanceMap = new HashMap();
  private Map containerMap = new HashMap();
  private Map tipiButtonMap = new HashMap();
  private Map tipiComponentMap = new HashMap();
//  private Map windowMap = new HashMap();
  private Map popupDefinitionMap = new HashMap();
  private Map menuDefinitionMap = new HashMap();
  private Map tipiClassMap = new HashMap();
  private Map tipiClassDefMap = new HashMap();
  private Tipi topScreen;
  private RootPaneContainer myTopLevel = null;
  private TipiErrorHandler eHandler;
  private int internalMode = UI_MODE_FRAME;
  private String errorHandler;
  private JDialog waitDialog = null;
  private ArrayList rootPaneList = new ArrayList();
//  private boolean isQueueRunning = false;

  private ArrayList screenDefList = new ArrayList();
  private ArrayList screenList = new ArrayList();
  private com.dexels.navajo.tipi.impl.DefaultTipiSplash splash;
  private URL imageBaseURL = null;
//  private ArrayList myNavajoQueue = new ArrayList();

  public TipiContext() {
    System.err.println("CLIENT URL: "+getClass().getClassLoader().getResource("server.xml"));
    NavajoClientFactory.createClient("com.dexels.navajo.client.impl.DirectClientImpl",getClass().getClassLoader().getResource("server.xml"));
    NavajoClientFactory.getClient().setServerUrl("dexels.durgerlan.nl/sport-tester/servlet/Postman");
    NavajoClientFactory.getClient().setUsername("ROOT");
    NavajoClientFactory.getClient().setPassword("");
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
    parseStream(location.openStream());
  }

  public void parseStream(InputStream in) throws IOException, XMLParseException,
      TipiException {
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
//      JOptionPane jop = new JOptionPane("WAIT!!!!");
//      waitDialog = jop.createDialog(topScreen.getContainer(),"Laden...");
//      waitDialog.setModal(false);
      //        SwingUtilities.updateComponentTreeUI(topScreen.getContainer());

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
      System.err.println("Setting info: " + info);
      splash.setInfoText(info);
    }
  }

  private void parseXMLElement(XMLElement elm) throws TipiException {
    String elmName = elm.getName();
    setSplashInfo("Loading screens");
    System.err.println("parseXMLElement called");
    if (!elmName.equals("tid")) {
      throw new TipiException("TID Rootnode not found!, found " + elmName +
                              " instead.");
    }
//    String startScreen = (String) elm.getAttribute("startscreen");
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
      if (childName.equals("menubar")) {
        addMenuDefinition(child);
      }
      if (childName.equals("popup")) {
        addPopupDefinition(child);
      }

      if (childName.equals("tipiclass")) {
        addTipiClassDefinition(child);
      }
      if (childName.equals("screen-instance")) {
        screenDefList.add(child);
      }
      if (childName.equals("tipi-include")) {
        parseLibrary(child);
      }
    }

  }

  private void parseLibrary(XMLElement lib) {
    try {
      String location = (String) lib.getAttribute("location");
      System.err.println("library: " + location);
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

  public TipiPopupMenu instantiateTipiPopupMenu(String name) throws TipiException {
    TipiPopupMenu tt = createTipiPopup();
    XMLElement xe = getPopupDefinition(name);
    tt.load(xe, this);
    return tt;
  }

  public TipiAction instantiateTipiAction(XMLElement definition, TipiComponent parent, TipiEvent event) throws TipiException {
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
//      tc.load(definition,instance,this);
      tc.loadEventsDefinition(this, definition, classDef);
      tc.loadStartValues(definition);
      return tc;
    }
    else {
      throw new TipiException("Problems instantiating TipiComponent class: " + definition.toString());
    }
  }

//  public TipiComponent instantiateComponent(String name) throws TipiException {
//    XMLElement xe = new CaseSensitiveXMLElement();
//    xe.setName("component-instance");
//    xe.setAttribute("name",name);
//    return instantiateComponent(xe);
//  }

  public TipiComponent instantiateComponent(XMLElement instance) throws TipiException {
    String name = (String) instance.getAttribute("name");
//    String value = (String)instance.getAttribute("value");
    String clas = instance.getStringAttribute("class", "");
    /** @todo Allow all the allowed values to be specified at instantiating.*/
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
    return tc;
  }

  public void disposeTipi(TipiComponent comp) {
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
//        tc.loadEventsDefinition(this,tipiDefinition,classDef);
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

  public void attachPopupMenu(String name, Container c) {
  }

  public void showPopup(MouseEvent e) {

  }

  public void addTipiInstance(String service, Tipi instance) {
    //System.err.println("Adding: " + service);
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

  private XMLElement getPopupDefinition(String name) throws TipiException {
    setSplashInfo("Loading: " + name);
    XMLElement xe = (XMLElement) popupDefinitionMap.get(name);
    if (xe == null) {
      throw new TipiException("Popup definition for: " + name + " not found!");
    }
    return xe;
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
    //System.err.println("Service: " + service);
    return (ArrayList) tipiInstanceMap.get(service);
  }


  public XMLElement getTipiMenubarDefinition(String name) throws TipiException {
    XMLElement xe = (XMLElement) menuDefinitionMap.get(name);
    if (xe == null) {
      throw new TipiException("Screen definition for: " + name + " not found!");
    }
    return xe;
  }

  private XMLElement getContainerDefinition(String containerName) throws TipiException {
    XMLElement xe = (XMLElement) containerMap.get(containerName);
    if (xe == null) {
      throw new TipiException("Screen definition for: " + containerName + " not found!");
    }
    return xe;
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
    /** @todo Remove some maps */
    tipiComponentMap.put(buttonName, elm);
    tipiMap.put(buttonName, elm);
  }

  private void addTipiDefinition(XMLElement elm) {
    String tipiName = (String) elm.getAttribute("name");
    String tipiService = (String) elm.getAttribute("service");
    setSplashInfo("Loading: " + tipiName);
    tipiMap.put(tipiName, elm);
    tipiServiceMap.put(tipiService, elm);
    //System.err.println("Adding component (tipi) definition: "+tipiName);
    addComponentDefinition(elm);
  }

  private void addMenuDefinition(XMLElement elm) {
    String name = (String) elm.getAttribute("name");
    setSplashInfo("Loading: " + name);
    menuDefinitionMap.put(name, elm);
  }

  private void addPopupDefinition(XMLElement elm) {
    String name = (String) elm.getAttribute("name");
    setSplashInfo("Loading: " + name);
    popupDefinitionMap.put(name, elm);
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
    containerMap.clear();
    tipiButtonMap.clear();
    popupDefinitionMap.clear();
    menuDefinitionMap.clear();
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
//    System.err.println("Getting tipi-component by path: " + path);
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
        throw new NullPointerException("Did not find Tipi: " + name);
      }
      return t.getTipiComponentByPath(rest);
    }
  }

  public Tipi getTipiByPath(String path) {
//    System.err.println("Getting tipi by path: " + path);
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
      ex.printStackTrace();
    }
  }

//  public void serveAsyncSend() {
//    if (myNavajoQueue.size()==0) {
//      return;
//    }
//    QueueEntry qe = (QueueEntry)myNavajoQueue.get(0);
//    myNavajoQueue.remove(0);
//    doAsyncSend(qe.getNavajo(),qe.getMethod());
//    if (myNavajoQueue.size()==0) {
//      setQueueRunning(false);
//    }
//  }

  public Navajo doSimpleSend(Navajo n, String service) {
    Navajo reply = null;
    //System.err.println("Reply: " + ((NavajoImpl)reply).toXml().toString());
    try {
      reply = NavajoClientFactory.getClient().doSimpleSend(n, service);
      //reply.write(System.out);
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
//    Navajo n = doSimpleSend(t.getNavajo(),method);
//    loadTipiMethod(n, method);
//    doAsyncSend(t.getNavajo(),method);

      //doSimpleSend(t.getNavajo(), method);
    enqueueAsyncSend(t.getNavajo(),method);
  }

  public void performMethod(String service) throws TipiException {
//    Navajo reply = doSimpleSend(NavajoFactory.getInstance().createNavajo(),service);
//    loadTipiMethod(reply, service);
//    doAsyncSend(NavajoFactory.getInstance().createNavajo(),service);
    //doSimpleSend(NavajoFactory.getInstance().createNavajo(),service);
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
//    System.err.println("Looking for tipi with method: " + method);
//    System.err.println("# of entries in tipilist: " + tipiList.size());
//    for (int i = 0; i < tipiList.size(); i++) {
//      Tipi t = (Tipi) tipiList.get(i);
//      System.err.println("Current tipi: "+t.getName());
//    }
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
    System.err.println("Ending service: "+method);
//    System.err.println("Queue entries: "+myNavajoQueue.size());
//    try {
//      n.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
    try {
      loadTipiMethod(n, method);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
    if (NavajoClientFactory.getClient().getPending()==0) {
      setWaiting(false);
    }

//    serveAsyncSend();
  }
  public synchronized void setWaiting(boolean b) {
//    System.err.println("\nSet waiting: "+b+"\n");
    for (int i = 0; i < rootPaneList.size(); i++) {
      TipiComponent tc = (TipiComponent)rootPaneList.get(i);
      if (DefaultTipiScreen.class.isInstance(tc)) {
//        if (waitDialog!=null) {
//          waitDialog.setVisible(b);
//        }

      }

//      System.err.println("Setting waiting for: "+tc.getClass()+" | "+tc.getName());
      tc.getContainer().setCursor(b?Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR):Cursor.getDefaultCursor());

    }
//    System.err.println("\n\n");
  }
}
