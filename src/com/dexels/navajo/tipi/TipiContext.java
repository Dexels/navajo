package com.dexels.navajo.tipi;

import nanoxml.*;
import java.util.*;
import java.io.*;
import java.net.URL;
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
  private Map screenMap = new HashMap();
  private Map tipiMap = new HashMap();
  private Map tipiServiceMap = new HashMap();
  private Map tipiInstanceMap = new HashMap();
  private Map containerMap = new HashMap();
  private Map tipiButtonMap = new HashMap();
  private Map windowMap = new HashMap();
  private Map popupDefinitionMap = new HashMap();
  private Map menuDefinitionMap = new HashMap();
  private Map tipiClassMap = new HashMap();
  private TipiScreen topScreen;
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

  public Tipi getTipi(String name) {
    /** @todo implement */
    return null;
  }

  public TipiContainer getContainer(String name) {
    /** @todo Implement */
    return null;
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
    myTopLevel.setTitle(title);
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      String childName = child.getName();
//      System.err.println("Parsing child: " + childName);
      if (childName.equals("tipi")) {
        addTipiDefinition(child);
      }
      if (childName.equals("container")) {
        addContainerDefinition(child);
      }
      if (childName.equals("screen")) {
        addScreenDefinition(child);
      }
      if (childName.equals("button")) {
        addButtonDefinition(child);
      }
      if (childName.equals("menubar")) {
        addMenuDefinition(child);
      }
      if (childName.equals("window")) {
        addWindowDefinition(child);
      }
      if (childName.equals("popup")) {
        addPopupDefinition(child);
      }
      if (childName.equals("tipiclass")) {
        addTipiClassDefinition(child);
      }
    }
//    System.err.println("StartScreen: " + startScreen);
    topScreen = instantiateTipiScreen(startScreen);
  }
  public TipiWindow instantiateTipiWindow(String name) throws TipiException {
    TipiWindow tc = createTipiWindow();
    XMLElement definition = getWindowDefinition(name);
    tc.load(definition, this);
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("table")) {
        tc.parseTable(this,tc,child);
      }
      else {
        throw new TipiException("Unexpected element found [" + child.getName() +
                                "]. Expected 'table'");
      }
    }
    return tc;
  }
  public TipiPopupMenu instantiateTipiPopupMenu(String name) throws TipiException {
    TipiPopupMenu tt = createTipiPopup();
    XMLElement xe = getPopupDefinition(name);
    tt.load(xe,this);
    return tt;
  }

  public TipiTable instantiateTipiTable(String name) throws TipiException {
    XMLElement xe = getTipiDefinition(name);
    TipiTable tt = createTipiTable();
    tt.load(xe,this);
    tipiInstanceMap.put(tt.getService(), tt);
    return tt;
  }

  public TipiAction instantiateTipiAction(XMLElement definition) throws TipiException {
    TipiAction a = createTipiAction();
    a.fromXml(definition);
    return a;
  }

  public TipiContainer instantiateClass(Tipi tipiParent, TipiContainer parent, XMLElement definition) throws TipiException {
    String name = (String)definition.getAttribute("class");

    Class c = getTipiClass(name);
    if (c==null) {
      throw new TipiException("Error retrieving class definition. Looking for class: "+name);
    }

    Object o;
    try {
      o = c.newInstance();
    }
    catch (Exception ex) {
      throw new TipiException("Error instantiating class. Class may not have a public default contructor, or be abstract, or an interface");
    }
    if (!TipiContainer.class.isInstance(o)) {
      throw new TipiException("Instantiating class does not implement TipiContainer");
    }
    if (Tipi.class.isInstance(o)) {
      tipiParent.addTipi((Tipi)o,this,null);
    } else {
      parent.addTipiContainer((TipiContainer)o,this,null);
    }

    return (TipiContainer)o;
  }
  public Class getTipiClass(String name) throws TipiException {
//    String className = (String)definition.getAttribute("class");
    return (Class)tipiClassMap.get(name);
  }

  public void addTipiClassDefinition(XMLElement xe) throws TipiException {
    String pack = (String)xe.getAttribute("package");
    String name = (String)xe.getAttribute("name");
    String clas = (String)xe.getAttribute("class");

    String fullDef = pack+"."+clas;
    try {
      Class c = Class.forName(fullDef);
      tipiClassMap.put(name,c);
    }
    catch (ClassNotFoundException ex) {
      throw new TipiException("Trouble loading class. Name: "+name+" in package: "+pack);
    }

  }

  public TipiScreen instantiateTipiScreen(String name) throws TipiException {
    TipiScreen s = createTipiScreen();
    XMLElement definition = getScreenDefinition(name);
    s.load(definition, this);
    return s;
  }

  public void attachPopupMenu(String name, Container c) {
  }

  public void showPopup(MouseEvent e) {

  }

  public TipiButton instantiateTipiButton(String name, Tipi myTipi) throws TipiException {
    TipiButton s = createTipiButton();
    s.setTipi(myTipi);
    XMLElement definition = getTipiButtonDefinition(name);
    s.load(definition, this);
    return s;
  }


  public Tipi instantiateTipi(XMLElement reference) throws TipiException {
    boolean isDefault = false;
    XMLElement defaultElm = null;
    Tipi s = createTipi();
    String name = (String)reference.getAttribute("name");
    XMLElement definition = getTipiDefinition(name);
    s.load(definition, this);
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("table")) {
        s.parseTable(this,s,child);
      }
      else if (child.getName().equals("default")) {
        //parseTable(child, s);
        System.err.println("Default tipi found!");
        isDefault = true;
        defaultElm = child;
      }else {
        throw new TipiException("Unexpected element found [" + child.getName() +
                                "]. Expected 'table'");
      }
    }
    String tipiMethod = (String) definition.getAttribute("service");
    tipiInstanceMap.put(tipiMethod, s);
    s.performService(this);
    if(isDefault){
      makeDefaultTipi(defaultElm, s);
    }
    return s;
  }

  public TipiContainer instantiateTipiContainer(Tipi tipiParent, XMLElement reference) throws
      TipiException {
    TipiContainer s = createTipiContainer();
    String name = (String )reference.getAttribute("name");
    XMLElement definition = getContainerDefinition(name);
    //s.load(definition, this);
    s.load(reference, this); // We put container specific data in the reference

    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("table")) {
        s.parseTable(this,tipiParent,child);
      }
      else {
        throw new TipiException("Unexpected element found [" + child.getName() +
                                "]. Expected 'table'");
      }
    }
    return s;
  }


  private void makeDefaultTipi(XMLElement elm, Tipi t){
    int columns = 1;
    columns = elm.getIntAttribute("columns", columns);
    Navajo n = t.getNavajo();
    TipiContainer c = new DefaultTipiContainer();
    try {
      c.load(elm,this);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    TipiTableLayout layout = new TipiTableLayout();
    Container con = c.getContainer();
    con.setLayout(layout);
    Container conTipi = t.getContainer();
    conTipi.setLayout(new TipiTableLayout());
    TipiTableLayout l = (TipiTableLayout)con.getLayout();
    int current_column = 0;

    ArrayList msgs = n.getAllMessages();
    for(int i=0;i<msgs.size();i++){
      Message current = (Message)msgs.get(i);
      ArrayList props = current.getAllProperties();
      l.startRow();
      for(int j=0;j<props.size();j++){
        l.startColumn();
        current_column++;
        Property p = (Property) props.get(j);
        BasePropertyComponent bpc = new BasePropertyComponent(p);
        c.addProperty(p.getName(), bpc, this, null);
        l.endColumn();
        if(current_column > columns-1){
          current_column=0;
          l.endRow();
          l.startRow();
        }
      }
    }
    t.addTipiContainer(c, this, null);
  }

  private XMLElement getScreenDefinition(String name) throws TipiException {
    XMLElement xe = (XMLElement) screenMap.get(name);
    if (xe==null) {
      throw new TipiException("Screen definition for: "+name+" not found!");
    }
    return xe;
  }
  private XMLElement getPopupDefinition(String name)  throws TipiException {
    XMLElement xe = (XMLElement) popupDefinitionMap.get(name);
    if (xe==null) {
      throw new TipiException("Popup definition for: "+name+" not found!");
    }
    return xe;
  }
  private XMLElement getWindowDefinition(String name)  throws TipiException {
    XMLElement xe =  (XMLElement) windowMap.get(name);
    if (xe==null) {
      throw new TipiException("Window definition for: "+name+" not found!");
    }
    return xe;
  }

  private XMLElement getTipiDefinition(String name) throws TipiException  {
//    String tipiName = (String) reference.getAttribute("name");
    XMLElement xe =  (XMLElement) tipiMap.get(name);
    if (xe==null) {
      throw new TipiException("Tipi definition for: "+name+" not found!");
    }
    return xe;
  }

  private XMLElement getTipiDefinitionByService(String service) throws TipiException  {
    XMLElement xe =  (XMLElement) tipiServiceMap.get(service);
    if (xe==null) {
      throw new TipiException("Tipi definition mapping to service: "+service+" not found!");
    }
    return xe;
  }

  private Tipi getTipiInstanceByService(String service)  throws TipiException {
    Tipi t =  (Tipi) tipiInstanceMap.get(service);
    if (t==null) {
      throw new TipiException("Tipi instance for service: "+service+" not found!");
    }
    return t;
  }
  private XMLElement getTipiButtonDefinition(String name)  throws TipiException {
    XMLElement xe =  (XMLElement) tipiButtonMap.get(name);
    if (xe==null) {
      throw new TipiException("Screen definition for: "+name+" not found!");
    }
    return xe;
  }

  public XMLElement getTipiMenubarDefinition(String name)  throws TipiException {
    XMLElement xe =  (XMLElement) menuDefinitionMap.get(name);
    if (xe==null) {
      throw new TipiException("Screen definition for: "+name+" not found!");
    }
    return xe;
  }

  private XMLElement getContainerDefinition(String containerName)  throws TipiException {
    XMLElement xe =  (XMLElement) containerMap.get(containerName);
    if (xe==null) {
      throw new TipiException("Screen definition for: "+containerName+" not found!");
    }
    return xe;
  }

  private void addButtonDefinition(XMLElement elm) {
    String buttonName = (String) elm.getAttribute("name");
    tipiButtonMap.put(buttonName, elm);
  }
  private void addScreenDefinition(XMLElement elm) {
    String screenName = (String) elm.getAttribute("name");
    screenMap.put(screenName, elm);
  }

  private void addTipiDefinition(XMLElement elm) {
    String tipiName = (String) elm.getAttribute("name");
    String tipiService = (String) elm.getAttribute("service");
    tipiMap.put(tipiName, elm);
    tipiServiceMap.put(tipiService, elm);
  }

  private void addWindowDefinition(XMLElement elm) {
    String windowName = (String) elm.getAttribute("name");
    windowMap.put(windowName, elm);
  }
  private void addContainerDefinition(XMLElement elm) {
    String containerName = (String) elm.getAttribute("name");
    containerMap.put(containerName, elm);
  }
  private void addMenuDefinition(XMLElement elm) {
    String name = (String)elm.getAttribute("name");
    menuDefinitionMap.put(name,elm);
  }
  private void addPopupDefinition(XMLElement elm) {
    String name = (String)elm.getAttribute("name");
    popupDefinitionMap.put(name,elm);
  }
  public TipiScreen createTipiScreen() {
    return new DefaultTipiScreen();
  }

  private TipiWindow createTipiWindow() {
    return new DefaultTipiWindow();
  }

  private TipiContainer createTipiContainer() {
    return new DefaultTipiContainer();
  }
  public TipiMenubar createTipiMenubar() {
    return new TipiMenubar();
  }
  private TipiPopupMenu createTipiPopup() {
    return new TipiPopupMenu();
  }

  private TipiTable createTipiTable() {
    return new DefaultTipiTable();
  }
  private Tipi createTipi() {
    return new DefaultTipi();
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
  public TipiScreen getTopScreen() {
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
    System.err.println("Service: " + service);
    reply = AdvancedNavajoClient.doSimpleSend(n, service);
//    System.err.println("Finished loading!");
//    System.err.println("RECEIVED FROM SERVICE: "+reply.toXml());
    return reply;
  }

  public void performTipiMethod(Tipi t, String method) throws TipiException {
    Navajo n = doSimpleSend(method, t.getNavajo());
    performTipiMethod(n,method);
  }

  public void performTipiMethod(Navajo reply, String method) throws TipiException {
    Tipi tt;
    try {
      tt = getTipiInstanceByService(method);
    }
    catch (TipiException ex) {
      System.err.println("No instance found of tipi for method: "+method);
      return;
    }

    if (tt != null) {
      tt.loadData(reply, this);
    }
  }

  public void instantiateCustomClass(String name) {
    // get class name;
  }
}