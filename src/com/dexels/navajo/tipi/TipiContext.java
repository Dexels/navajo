package com.dexels.navajo.tipi;

import nanoxml.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.nanoclient.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiContext {

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
  private TopLevel myTopLevel = null;
  private TipiErrorHandler eHandler;
  private boolean isInternalMode = false;

  private ArrayList screenDefList = new ArrayList();
  private ArrayList screenList = new ArrayList();

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

  public void setToplevel(TopLevel tl) {
    myTopLevel = tl;
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
  }

  private void parseXMLElement(XMLElement elm) throws TipiException {
    String elmName = elm.getName();
    if (!elmName.equals("tid")) {
      throw new TipiException("TID Rootnode not found!, found " + elmName +
                              " instead.");
    }
    String startScreen = (String) elm.getAttribute("startscreen");
    String errorHandler = (String) elm.getAttribute("errorhandler", null);
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
//        startScreenDef = child;
        screenDefList.add(child);
      }
      if (childName.equals("tipi-include")) {
        System.err.println("Library found");
        parseLibrary(child);
      }

    }
//    if(startScreenDef != null){
//      topScreen = (Tipi) instantiateComponent(startScreenDef);
//      SwingUtilities.updateComponentTreeUI(topScreen.getContainer());
//    }
      for (int i = 0; i < screenDefList.size(); i++) {
        topScreen = (Tipi) instantiateComponent((XMLElement)screenDefList.get(i));
        screenList.add(topScreen);
        topScreen.getContainer().setVisible(true);
 //        SwingUtilities.updateComponentTreeUI(topScreen.getContainer());

    }


    if(errorHandler != null){
      try{
        Class c = getTipiClass(errorHandler);
        eHandler = (TipiErrorHandler) c.newInstance();
        eHandler.setContext(this);
      }catch(Exception e){
        System.err.println("Error instantiating TipiErrorHandler!");
      }

    }
  }

  private void parseLibrary(XMLElement lib){
    try{
      String location = (String) lib.getAttribute("location");
      if (location != null) {
        URL loc = MainApplication.class.getResource(location);
        if (loc != null) {
          InputStream in = loc.openStream();
          XMLElement doc = new CaseSensitiveXMLElement();
          doc.parseFromReader(new InputStreamReader(in));
          parseXMLElement(doc);
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public boolean getInternalMode(){
    return isInternalMode;
  }

  public void setInternalMode(boolean value){
      isInternalMode = value;
  }

  public TipiPopupMenu instantiateTipiPopupMenu(String name) throws TipiException {
    TipiPopupMenu tt = createTipiPopup();
    XMLElement xe = getPopupDefinition(name);
    tt.load(xe, this);
    return tt;
  }

  public TipiAction instantiateTipiAction(XMLElement definition,TipiComponent parent, TipiEvent event) throws TipiException {
    TipiAction a = createTipiAction();
    a.load(definition,parent,event);
    return a;
  }

  public TipiLayout instantiateLayout(XMLElement instance) throws TipiException {
    String type = (String)instance.getAttribute("type");
    return (TipiLayout)instantiateClass(type,null, instance);
  }

  private TipiComponent instantiateComponentByDefinition(XMLElement definition,XMLElement instance) throws TipiException {
    String clas = definition.getStringAttribute("class","");
    String name = instance.getStringAttribute("name");
    if (!clas.equals("")) {
      Class cc = getTipiClass(clas);
      TipiComponent tc = (TipiComponent)instantiateClass(clas,name,instance);
      XMLElement classDef = (XMLElement)tipiClassDefMap.get(clas);
//      tc.load(definition,instance,this);
      tc.loadEventsDefinition(this,definition,classDef);
      tc.loadStartValues(definition);
      return tc;
    } else {
      throw new TipiException("Problems instantiating TipiComponent class: "+definition.toString());
    }
  }

//  public TipiComponent instantiateComponent(String name) throws TipiException {
//    XMLElement xe = new CaseSensitiveXMLElement();
//    xe.setName("component-instance");
//    xe.setAttribute("name",name);
//    return instantiateComponent(xe);
//  }

  public TipiComponent instantiateComponent(XMLElement instance) throws TipiException {
    String name = (String)instance.getAttribute("name");
//    String value = (String)instance.getAttribute("value");
    String clas = instance.getStringAttribute("class","");
/** @todo Allow all the allowed values to be specified at instantiating.*/
    TipiComponent tc = null;
    if (clas.equals("")) {
      XMLElement xx = getComponentDefinition(name);
      tc = instantiateComponentByDefinition(xx,instance);
    } else {
      tc = (TipiComponent)instantiateClass(clas,name,instance);
    }
    tc.loadStartValues(instance);
    return tc;
  }

  private Object instantiateClass(String className, String defname, XMLElement instance) throws TipiException {
    XMLElement tipiDefinition = null;
    Class c = getTipiClass(className);
        tipiDefinition = getTipiDefinition(defname);
    XMLElement classDef = (XMLElement)tipiClassDefMap.get(className);
    if (c == null) {
      throw new TipiException("Error retrieving class definition. Looking for class: " + defname);
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
      tc.setContainer(tc.createContainer());
      tc.setContext(this);
      tc.instantiateComponent(instance,classDef);
      if (tipiDefinition!=null) {
        tc.load(tipiDefinition,instance, this);
//        tc.loadEventsDefinition(this,tipiDefinition,classDef);
      } else {
        tc.load(instance,instance, this);
        tc.loadEventsDefinition(this,instance,classDef);
      }
      return tc;
    }
    if(TipiLayout.class.isInstance(o)) {
      TipiLayout tl = (TipiLayout)o;
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
    System.err.println("Adding: " + service);
    if (tipiInstanceMap.containsKey(service)) {
      ArrayList al = (ArrayList)tipiInstanceMap.get(service);
      al.add(instance);
    } else {
      ArrayList al = new ArrayList();
      al.add(instance);
      tipiInstanceMap.put(service, al);
    }
  }

//  public TipiButton instantiateTipiButton(String name, XMLElement instance, Tipi myTipi) throws TipiException {
//    TipiButton s = createTipiButton();
//    s.setTipi(myTipi);
//    XMLElement definition = getTipiButtonDefinition(name);
//    s.load(definition,instance, this);
//    return s;
//  }
  private XMLElement getPopupDefinition(String name) throws TipiException {
    XMLElement xe = (XMLElement) popupDefinitionMap.get(name);
    if (xe == null) {
      throw new TipiException("Popup definition for: " + name + " not found!");
    }
    return xe;
  }
  private XMLElement getTipiDefinition(String name) throws TipiException {
//    String tipiName = (String) reference.getAttribute("name");
//    System.err.println(">>><<<>>>"+tipiMap.keySet());
//    System.err.println("TipiMap: "+tipiMap.size());
    XMLElement xe = (XMLElement) tipiMap.get(name);
    if (xe == null) {
//     System.err.println("Tipi definition for: " + name + " not found!");
    }
    return xe;
  }

  private XMLElement getTipiDefinitionByService(String service) throws TipiException {
    XMLElement xe = (XMLElement) tipiServiceMap.get(service);
    if (xe == null) {
      throw new TipiException("Tipi definition mapping to service: " + service + " not found!");
    }
    return xe;
  }

  private ArrayList getTipiInstancesByService(String service) throws TipiException {
    System.err.println("Service: " + service);
    return (ArrayList)tipiInstanceMap.get(service);
  }

//  private Tipi getTipiInstanceByService(String service) throws TipiException {
//    Tipi t = (Tipi) tipiInstanceMap.get(service);
//    if (t == null) {
//      throw new TipiException("Tipi instance for service: " + service + " not found!");
//    }
//    return t;
//  }

//  private XMLElement getTipiButtonDefinition(String name) throws TipiException {
//    XMLElement xe = (XMLElement) tipiButtonMap.get(name);
//    if (xe == null) {
//      throw new TipiException("Screen definition for: " + name + " not found!");
//    }
//    return xe;
//  }

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
    /** @todo Remove some maps */
    tipiComponentMap.put(buttonName, elm);
    tipiMap.put(buttonName,elm);
  }
  private void addTipiDefinition(XMLElement elm) {
    String tipiName = (String) elm.getAttribute("name");
    String tipiService = (String) elm.getAttribute("service");
    tipiMap.put(tipiName, elm);
    tipiServiceMap.put(tipiService, elm);
    addComponentDefinition(elm);
  }
//
//  private void addContainerDefinition(XMLElement elm) {
//    String containerName = (String) elm.getAttribute("name");
//    containerMap.put(containerName, elm);
//  }

  private void addMenuDefinition(XMLElement elm) {
    String name = (String) elm.getAttribute("name");
    menuDefinitionMap.put(name, elm);
  }

  private void addPopupDefinition(XMLElement elm) {
    String name = (String) elm.getAttribute("name");
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

  public ArrayList getScreens(){
    return screenList;
  }

  public TopLevel getTopLevel() {
    return myTopLevel;
  }

  public Tipi getTopScreen() {
    return topScreen;
  }

  public Tipi getTipiByPath(String path) {
    return getTopScreen().getTipiByPath(path);
  }
  public TipiComponent getTipiComponentByPath(String path) {
    return getTopScreen().getTipiComponentByPath(path);
  }

  public Navajo doSimpleSend(String service, Navajo n) {
    Navajo reply;
    AdvancedNavajoClient.setServerUrl("dexels.durgerlan.nl/sport-tester/servlet/Postman");
    AdvancedNavajoClient.setUsername("ROOT");
    AdvancedNavajoClient.setPassword("");
    reply = AdvancedNavajoClient.doSimpleSend(n, service);
    System.err.println("Reply: " + reply.toXml().toString());
//    Thread.dumpStack();
    if(eHandler != null){
      if(eHandler.hasErrors(reply)){
        eHandler.showError();
        return null;
      }else{
        return reply;
      }
    }else{
      return reply;
    }
  }

  public void performTipiMethod(Tipi t, String method) throws TipiException {
    Navajo n = doSimpleSend(method, t.getNavajo());
    loadTipiMethod(n, method);
  }

  public void performMethod(String service) throws TipiException {
    System.err.println("Jojojojo!!!!!!!!!!!!11");
    Navajo reply = doSimpleSend(service,new Navajo());
    loadTipiMethod(reply,service);
  }

  public void loadTipiMethod(Navajo reply, String method) throws TipiException {
    System.err.println("LoadTPMethod");
    Tipi tt;
    ArrayList tipiList;
    try {
      tipiList = getTipiInstancesByService(method);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
      return;
    }
    if (tipiList==null) {
      System.err.println("Whoops! no tipi's");
      return;
    }
    System.err.println("Looking for tipi with method: "+method);
    System.err.println("# of entries in tipilist: "+tipiList.size());
    for (int i = 0; i < tipiList.size(); i++) {
      Tipi t = (Tipi)tipiList.get(i);
      t.loadData(reply, this);
    }
  }

  public ImageIcon getIcon(String name) {
   if (name != null) {
     ImageIcon i;
     try {
       URL iu = new URL(name);
       i = new ImageIcon(iu);
       return i;
     }
     catch (Exception e) {
       i = new ImageIcon(MainApplication.class.getResource(name));
     }
     if (i != null) {
       return i;
     }
   }
   return null;
  }
}