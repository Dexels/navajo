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
//  private Map windowMap = new HashMap();
  private Map popupDefinitionMap = new HashMap();
  private Map menuDefinitionMap = new HashMap();
  private Map tipiClassMap = new HashMap();
  private Map tipiClassDefMap = new HashMap();
  private Tipi topScreen;
  private TopLevel myTopLevel = null;

  // Dirty hack, just for testing

  private Tipi currentTipi = null;

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
    String title = (String) elm.getAttribute("title");
    if(title != null){
      myTopLevel.setTitle(title);
    }
    Vector children = elm.getChildren();
    XMLElement startScreenDef = null;
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      String childName = child.getName();

      if (childName.equals("tipi")) {
        addTipiDefinition(child);
      }

      if (childName.equals("container")) {
        addContainerDefinition(child);
      }

      if (childName.equals("button")) {
        addButtonDefinition(child);
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
        startScreenDef = child;
      }
      if (childName.equals("tipi-include")) {
        System.err.println("Library found");
        parseLibrary(child);
      }

    }
//    if (startScreenDef == null) {
//      throw new TipiException("No start screen instance found.");
//    }
//    System.err.println("----------> Instantiating topscreen!");
    if(startScreenDef != null){
      topScreen = (Tipi) instantiateClass(null, startScreenDef);
      System.err.println("TopScreen instantiated");
    }else{
      System.err.println("Class definitions loaded");
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

  public TipiPopupMenu instantiateTipiPopupMenu(String name) throws TipiException {
    TipiPopupMenu tt = createTipiPopup();
    XMLElement xe = getPopupDefinition(name);
    tt.load(xe, this);
    return tt;
  }

  public TipiAction instantiateTipiAction(XMLElement definition) throws TipiException {
    TipiAction a = createTipiAction();
    a.fromXml(definition);
    return a;
  }
  public TipiLayout instantiateLayout(XMLElement definition) throws TipiException {
    String type = (String)definition.getAttribute("type");
    Class cc = getTipiClass(type);
    Object o;
    try {
      o = cc.newInstance();
    }
    catch (Exception ex) {
      throw new TipiException("Problems instantiating TipiLayout class");
    }

    if (!TipiLayout.class.isInstance(o)) {
      throw new TipiException("Requested layout class: "+cc+" is not a subclass of TipiLayout");
    }
    TipiLayout tt = (TipiLayout)o;
    return tt;
  }

  public Object instantiateClass(Tipi tipiParent, XMLElement instance) throws TipiException {
    String defname = (String) instance.getAttribute("name");
    String id = (String) instance.getAttribute("id");
//    String type = (String) instance.getAttribute("type");

    XMLElement tipiDefinition = null;
    tipiDefinition = getTipiDefinition(defname);

    System.err.println("Actual def: " + tipiDefinition);

    String name = (String) tipiDefinition.getAttribute("class");
    System.err.println("Looking for class: " + name);
    Class c = getTipiClass(name);
    System.err.println("Found: " + c);

    if (c == null) {
      throw new TipiException("Error retrieving class definition. Looking for class: " + name);
    }

    Object o;
    try {
      o = c.newInstance();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new TipiException("Error instantiating class. Class may not have a public default contructor, or be abstract, or an interface");
    }
    if (Tipi.class.isInstance(o)) {
//      throw new TipiException("Instantiating class does not implement TipiContainer");
      Tipi tc = (Tipi) o;
      tc.setId(id);
      System.err.println("----------> Loading: " + c + " with " + instance);
      tc.load(tipiDefinition,instance, this);
      if (tipiParent != null) {
//        tipiParent.addTipi(tc, this, null, instance);
      }

      return tc;
    }
    if(TipiLayout.class.isInstance(o)) {
      TipiLayout tl = (TipiLayout)o;
    }
    throw new TipiException("INSTANTIATING UNKOWN SORT OF CLASS THING.");
  }

  public Class getTipiClass(String name) throws TipiException {
//    String className = (String)definition.getAttribute("class");
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
      throw new TipiException("Trouble loading class. Name: " + name + " in package: " + pack);
    }
  }
  public void attachPopupMenu(String name, Container c) {
  }

  public void showPopup(MouseEvent e) {

  }

  public void addTipiInstance(String service, Tipi instance) {
    tipiInstanceMap.put(service, instance);
  }

  public TipiButton instantiateTipiButton(String name, XMLElement instance, Tipi myTipi) throws TipiException {
    TipiButton s = createTipiButton();
    s.setTipi(myTipi);
    XMLElement definition = getTipiButtonDefinition(name);
    s.load(definition,instance, this);
    return s;
  }
  private XMLElement getPopupDefinition(String name) throws TipiException {
    XMLElement xe = (XMLElement) popupDefinitionMap.get(name);
    if (xe == null) {
      throw new TipiException("Popup definition for: " + name + " not found!");
    }
    return xe;
  }
  private XMLElement getTipiDefinition(String name) throws TipiException {
//    String tipiName = (String) reference.getAttribute("name");
    XMLElement xe = (XMLElement) tipiMap.get(name);
    if (xe == null) {
      throw new TipiException("Tipi definition for: " + name + " not found!");
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

  private Tipi getTipiInstanceByService(String service) throws TipiException {
    Tipi t = (Tipi) tipiInstanceMap.get(service);
    if (t == null) {
      throw new TipiException("Tipi instance for service: " + service + " not found!");
    }
    return t;
  }

  private XMLElement getTipiButtonDefinition(String name) throws TipiException {
    XMLElement xe = (XMLElement) tipiButtonMap.get(name);
    if (xe == null) {
      throw new TipiException("Screen definition for: " + name + " not found!");
    }
    return xe;
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

  private void addButtonDefinition(XMLElement elm) {
    String buttonName = (String) elm.getAttribute("name");
    tipiButtonMap.put(buttonName, elm);
  }

//  private void addScreenDefinition(XMLElement elm) {
//    String screenName = (String) elm.getAttribute("name");
//    screenMap.put(screenName, elm);
//    addTipiDefinition(elm);
//  }

  private void addTipiDefinition(XMLElement elm) {
    String tipiName = (String) elm.getAttribute("name");
    String tipiService = (String) elm.getAttribute("service");
    tipiMap.put(tipiName, elm);
    tipiServiceMap.put(tipiService, elm);
  }

//  private void addWindowDefinition(XMLElement elm) {
//    String windowName = (String) elm.getAttribute("name");
//    windowMap.put(windowName, elm);
//  }
  private void addContainerDefinition(XMLElement elm) {
    String containerName = (String) elm.getAttribute("name");
    containerMap.put(containerName, elm);
  }

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

  public TopLevel getTopLevel() {
    return myTopLevel;
  }

  public Tipi getTopScreen() {
    return topScreen;
  }

  private TipiButton createTipiButton() {
    return new TipiButton();
  }

  public void callMethod(String service, Message required) {
  }

  public Navajo doSimpleSend(String service, Navajo n) {
    Navajo reply;
    AdvancedNavajoClient.setServerUrl("dexels.durgerlan.nl/sport-tester/servlet/Postman");
    AdvancedNavajoClient.setUsername("ROOT");
    AdvancedNavajoClient.setPassword("");
//    System.err.println("Service: " + service);
    reply = AdvancedNavajoClient.doSimpleSend(n, service);
//    System.err.println("Finished loading!");
//    System.err.println("RECEIVED FROM SERVICE: "+reply.toXml());
    return reply;
  }

  public void performTipiMethod(Tipi t, String method) throws TipiException {
    Navajo n = doSimpleSend(method, t.getNavajo());
//    System.err.println("MESSAGE RETURNED: "+n);
    loadTipiMethod(n, method);
  }

  public void performMethod(String service) throws TipiException {
    Navajo reply = doSimpleSend(service,new Navajo());
    loadTipiMethod(reply,service);
  }

  public void loadTipiMethod(Navajo reply, String method) throws TipiException {
    Tipi tt;
    try {
      tt = getTipiInstanceByService(method);
    }
    catch (TipiException ex) {
      System.err.println("No instance found of tipi for method: " + method);
      return;
    }

    if (tt != null) {
      System.err.println("Found tipi for method: "+method);
      tt.loadData(reply, this);
    }
  }

}