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
  private TipiScreen topLevel;

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
    }
//    System.err.println("StartScreen: " + startScreen);
    topLevel = instantiateTipiScreen(startScreen);
  }

  private TipiScreen instantiateTipiScreen(String name) throws TipiException {
    TipiScreen s = createTipiScreen();
    XMLElement definition = getScreenDefinition(name);
    s.load(definition, this);
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("table")) {
        parseTable(child, s);
      }
      else {
        throw new TipiException("Unexpected element found [" + child.getName() +
                                "]. Expected 'table'");
      }
    }
    return s;
  }

  private TipiButton instantiateTipiButton(String name, Tipi myTipi) throws TipiException {
    TipiButton s = createTipiButton();
    s.setTipi(myTipi);
    XMLElement definition = getTipiButtonDefinition(name);
    System.err.println("CREATING A BUTTON WITH: "+definition);
    s.load(definition, this);
    return s;
  }

  private Tipi instantiateTipi(XMLElement reference) throws TipiException {
    boolean isDefault = false;
    XMLElement defaultElm = null;
    Tipi s = createTipi();
    XMLElement definition = getTipiDefinition(reference);
    //s.load(definition, this);
    s.load(definition, this);
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("table")) {
        parseTable(child, s);
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

  private TipiContainer instantiateTipiContainer(XMLElement reference) throws
      TipiException {
    TipiContainer s = createTipiContainer();
    XMLElement definition = getContainerDefinition(reference);
    //s.load(definition, this);
    s.load(reference, this); // We put container specific data in the reference
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("table")) {
        parseTable(child, s);
      }
      else {
        throw new TipiException("Unexpected element found [" + child.getName() +
                                "]. Expected 'table'");
      }
    }
    return s;
  }

  public void parseTable(XMLElement table, TipiComponent comp) throws TipiException {
    TipiTableLayout layout = new TipiTableLayout();
    Container con = (Container) comp;
    con.setLayout(layout);
    Map columnAttributes = new HashMap();
    Vector rows = table.getChildren();
    for (int r = 0; r < rows.size(); r++) {
      XMLElement row = (XMLElement) rows.elementAt(r);
      TipiTableLayout l = (TipiTableLayout) con.getLayout();
      l.startRow();
      Vector columns = row.getChildren();
      for (int c = 0; c < columns.size(); c++) {
        XMLElement column = (XMLElement) columns.elementAt(c);
        Enumeration attributes = column.enumerateAttributeNames();
        while (attributes.hasMoreElements()) {
          String attrName = (String) attributes.nextElement();
          columnAttributes.put(attrName, column.getStringAttribute(attrName));
        }
        l.startColumn();
        if (column.countChildren() > 1 || column.countChildren() == 0) {
          throw new TipiException(
              "More then one, or no children found inside <td>");
        }
        else {
          XMLElement component = (XMLElement) column.getChildren().elementAt(0);
          String componentName = component.getName();
          if (componentName.equals("tipi-instance")) {
            Tipi s = instantiateTipi(component);
            currentTipi = s;
            if (Tipi.class.isInstance(comp)) {
              ( (Tipi) comp).addTipi(s, this, columnAttributes);
            }
            else
            if (TipiScreen.class.isInstance(comp)) {
              ( (TipiScreen) comp).addTipi(s, this, columnAttributes);
            }
            else {
              throw new RuntimeException("Que?");
            }
          }
          if (componentName.equals("container-instance")) {
            TipiContainer cn = instantiateTipiContainer(component);
            if (Tipi.class.isInstance(comp)) {
              ( (Tipi) comp).addTipiContainer(cn, this, columnAttributes);
            }
            else
            if (TipiContainer.class.isInstance(comp)) {
              ( (TipiContainer) comp).addTipiContainer(cn, this,
                  columnAttributes);
            }
            else {
              throw new RuntimeException("Que?");
            }
         }
          if (componentName.equals("property")) {
            BasePropertyComponent pc = new BasePropertyComponent();
            String propertyName = (String) component.getAttribute("name");
            TipiContainer tc = (TipiContainer) comp;
            pc.load(component, this);
            tc.addProperty(propertyName, pc, this, columnAttributes);
          }
          if (componentName.equals("component")) {
            BaseComponent pc = new BaseComponent();
//            String propertyName = (String) component.getAttribute("name");
            TipiContainer tc = (TipiContainer) comp;
            pc.load(component, this);
            tc.addComponent(pc, this, columnAttributes);
          }
          if (componentName.equals("method")) {
            MethodComponent pc = new DefaultMethodComponent();
            pc.load(component, comp, this);
            comp.addComponent(pc, this, columnAttributes);
          }
          if (componentName.equals("button-instance")) {
            String buttonName = (String) component.getAttribute("name");
            TipiButton pc = instantiateTipiButton(buttonName,currentTipi);
            pc.load(component, this);
            comp.addComponent(pc, this, columnAttributes);
          }
        }
        columnAttributes.clear();
        l.endColumn();
      }
      l.endRow();
    }
  }

  private void makeDefaultTipi(XMLElement elm, Tipi t){
    int columns = 1;
    columns = elm.getIntAttribute("columns", columns);
    Navajo n = t.getNavajo();
    TipiContainer c = new DefaultTipiContainer();
    TipiTableLayout layout = new TipiTableLayout();
    Container con = (Container) c;
    con.setLayout(layout);
    Container conTipi = (Container) t;
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

  private XMLElement getScreenDefinition(String name) {
    return (XMLElement) screenMap.get(name);
  }

  private XMLElement getTipiDefinition(XMLElement reference) {
    String tipiName = (String) reference.getAttribute("name");
    return (XMLElement) tipiMap.get(tipiName);
  }

  private XMLElement getTipiDefinitionByService(XMLElement reference) {
    String serviceName = (String) reference.getAttribute("service");
    return (XMLElement) tipiServiceMap.get(serviceName);
  }

  private Tipi getTipiInstanceByService(String service) {
    return (Tipi) tipiInstanceMap.get(service);
  }
  private XMLElement getTipiButtonDefinition(String name) {
    return (XMLElement) tipiButtonMap.get(name);
  }


  private XMLElement getContainerDefinition(XMLElement reference) {
    String containerName = (String) reference.getAttribute("name");
    return (XMLElement) containerMap.get(containerName);
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

  private void addContainerDefinition(XMLElement elm) {
    String containerName = (String) elm.getAttribute("name");
    containerMap.put(containerName, elm);
  }

  private TipiScreen createTipiScreen() {
    return new DefaultTipiScreen();
  }

  private TipiContainer createTipiContainer() {
    return new DefaultTipiContainer();
  }

  private Tipi createTipi() {
    return new DefaultTipi();
  }

  public TipiEvent createTipiEvent() {
    return new TipiEvent();
  }

  public TipiAction createTipiAction() {
    return new DefaultTipiAction();
  }

  public TipiComponent getTopLevel() {
    return topLevel;
  }
  public TipiButton createTipiButton() {
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
    System.err.println("RECEIVED FROM SERVICE: "+reply.toXml());
    return reply;
  }

  public void performTipiMethod(Tipi t, String method) {
    Navajo n = doSimpleSend(method, t.getNavajo());
    performTipiMethod(n,method);
  }

  public void performTipiMethod(Navajo reply, String method) {
    Tipi tt = getTipiInstanceByService(method);
    if (tt != null) {
      tt.loadData(reply, this);
    }
    else {
      System.err.println("Oh dear");
    }
  }
}