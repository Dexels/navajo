package com.dexels.navajo.tipi;

import nanoxml.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;

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
  private Map containerMap = new HashMap();
  private TipiScreen topLevel;

  public TipiContext() {
  }

  public static TipiContext getInstance(){
    if(instance != null){
      return instance;
    }else{
      instance = new TipiContext();
      return instance;
    }
  }

  public Tipi getTipi(String name){
    /** @todo implement */
    return null;
  }

  public TipiContainer getContainer(String name){
    /** @todo Implement */
    return null;
  }

  public void parseURL(URL location) throws IOException, XMLParseException, TipiException{
    parseStream(location.openStream());
  }

  public void parseStream(InputStream in) throws IOException, XMLParseException, TipiException{
    XMLElement doc = new CaseSensitiveXMLElement();
    doc.parseFromReader(new InputStreamReader(in));
    parseXMLElement(doc);
  }

  private void parseXMLElement(XMLElement elm) throws TipiException{
    String elmName = elm.getName();
    if(!elmName.equals("tid")){
      throw new TipiException("TID Rootnode not found!, found " + elmName + " instead.");
    }
    String startScreen = (String)elm.getAttribute("startscreen");
    Vector children = elm.getChildren();
    for(int i=0;i<children.size();i++){
      XMLElement child = (XMLElement)children.elementAt(i);
      String childName = child.getName();
      System.err.println("Parsing child: " + childName);
      if(childName.equals("tipi")){
        addTipiDefinition(child);
      }
      if(childName.equals("container")){
        addContainerDefinition(child);
      }
      if(childName.equals("screen")){
        addScreenDefinition(child);
      }
    }
    System.err.println("StartScreen: " + startScreen);
    topLevel = instantiateTipiScreen(startScreen);
  }

  private TipiScreen instantiateTipiScreen(String name) throws TipiException{
    TipiScreen s = createTipiScreen();
    XMLElement definition = getScreenDefinition(name);
    s.load(definition, this);
    Vector children = definition.getChildren();
    for(int i=0;i<children.size();i++){
      XMLElement child = (XMLElement)children.elementAt(i);
      if(child.getName().equals("table")){
        parseTable(child, s);
      }else{
        throw new TipiException("Unexpected element found [" + child.getName() + "]. Expected 'table'");
      }
    }
    return s;
  }

  private Tipi instantiateTipi(XMLElement reference) throws TipiException{
    Tipi s = createTipi();
    XMLElement definition = getTipiDefinition(reference);
    s.load(definition, this);
    Vector children = definition.getChildren();
    for(int i=0;i<children.size();i++){
      XMLElement child = (XMLElement)children.elementAt(i);
      if(child.getName().equals("table")){
        parseTable(child, s);
      }else{
        throw new TipiException("Unexpected element found [" + child.getName() + "]. Expected 'table'");
      }
    }
    return s;
  }

  private TipiContainer instantiateTipiContainer(XMLElement reference) throws TipiException{
    TipiContainer s = createTipiContainer();
    XMLElement definition = getContainerDefinition(reference);
    s.load(definition, this);
    Vector children = definition.getChildren();
    for(int i=0;i<children.size();i++){
      XMLElement child = (XMLElement)children.elementAt(i);
      if(child.getName().equals("table")){
        parseTable(child, s);
      }else{
        throw new TipiException("Unexpected element found [" + child.getName() + "]. Expected 'table'");
      }
    }
    return s;
  }

  public void parseTable(XMLElement table, TipiComponent comp) throws TipiException{
    TipiTableLayout layout = new TipiTableLayout();
    Container con = (Container)comp;
    con.setLayout(layout);

    Vector rows = table.getChildren();
    for(int r=0;r<rows.size();r++){
      XMLElement row = (XMLElement)rows.elementAt(r);
      TipiTableLayout l = (TipiTableLayout)con.getLayout();
      l.startRow();
      Vector columns = row.getChildren();
      for(int c=0;c<columns.size();c++){
        XMLElement column = (XMLElement)columns.elementAt(c);
        l.startColumn();
        if(column.countChildren() > 1 || column.countChildren() == 0){
          throw new TipiException("More then one, or no children found inside <td>");
        }else{
          XMLElement component = (XMLElement)column.getChildren().elementAt(0);
          String componentName = component.getName();
          if(componentName.equals("tipi-instance")){
            Tipi s = instantiateTipi(component);
            comp.addComponent(s);
          }
          if(componentName.equals("container-instance")){
             TipiContainer cn = instantiateTipiContainer(component);
             comp.addComponent(cn);
          }
        }
        l.endColumn();
      }
      l.endRow();
    }

  }

  private XMLElement getScreenDefinition(String name){
    return (XMLElement)screenMap.get(name);
  }

  private XMLElement getTipiDefinition(XMLElement reference){
    String screenName = (String)reference.getAttribute("name");
    return (XMLElement)tipiMap.get(screenName);
  }

  private XMLElement getContainerDefinition(XMLElement reference){
    String screenName = (String)reference.getAttribute("name");
    return (XMLElement)containerMap.get(screenName);
  }


  private void addScreenDefinition(XMLElement elm){
    String screenName = (String)elm.getAttribute("name");
    screenMap.put(screenName, elm);
  }

  private void addTipiDefinition(XMLElement elm){
    String tipiName = (String)elm.getAttribute("name");
    tipiMap.put(tipiName, elm);
  }

  private void addContainerDefinition(XMLElement elm){
    String containerName = (String)elm.getAttribute("name");
    containerMap.put(containerName, elm);
  }


  private TipiScreen createTipiScreen(){
    return new DefaultTipiScreen();
  }

  private TipiContainer createTipiContainer(){
    return new DefaultTipiContainer();
  }

  private Tipi createTipi(){
    return new DefaultTipi();
  }

  public TipiComponent getTopLevel(){
    return topLevel;
  }
}