package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiPathParser {

  public static final int PATH_TO_TIPI = 0;
  public static final int PATH_TO_MESSAGE = 1;
  public static final int PATH_TO_PROPERTY = 2;
  public static final int PATH_TO_COMPONENT = 4;
  public static final int PATH_TO_ATTRIBUTE= 5;
  public static final int PATH_TO_ATTRIBUTEREF= 7;
  public static final int PATH_TO_UNKNOWN = 3;
  public static final int PATH_TO_PROPERTYREF = 6;
  public static final int PATH_TO_RESOURCE = 8;
  private int myType = 3;
  private String myPath = "";
  private Object myObject;
  private TipiComponent mySource;
  private TipiContext myContext;
  private TipiComponent myTipi;

  public TipiPathParser(TipiComponent source, TipiContext context, String path) {
    mySource = source;
    myContext = context;
    myPath = path;
    parse(path);
  }

//  public void setPath(String path){
//    myPath = path;
//    parse(path);
//  }

  private void parse(String path){
    if(path.startsWith("tipi:/")){
      myType = PATH_TO_TIPI;
    }else if(path.startsWith("message:/")){
      myType = PATH_TO_MESSAGE;
    }else if(path.startsWith("property:/")){
      myType = PATH_TO_PROPERTY;
    }else if(path.startsWith("propertyref:/")){
      myType = PATH_TO_PROPERTYREF;
    }
    else if(path.startsWith("component:/")){
      myType = PATH_TO_COMPONENT;
    }else if(path.startsWith("attribute:/")){
      myType = PATH_TO_ATTRIBUTE;
    }else if(path.startsWith("attributeref:/")){
      myType = PATH_TO_ATTRIBUTEREF;
    }
    else if(path.startsWith("resource:/")){
          myType = PATH_TO_RESOURCE;
      }

    else{
      myType = PATH_TO_UNKNOWN; // assuming tipi
    }

    myTipi = getTipiComponent(path);

    switch(myType){
      case PATH_TO_TIPI:
        myObject = getTipiByPath(path);
        break;
      case PATH_TO_MESSAGE:
        myObject = getMessageByPath(path);
        break;
      case PATH_TO_PROPERTY:
      case PATH_TO_PROPERTYREF:
        myObject = getPropertyByPath(path);
        break;
      case PATH_TO_COMPONENT:
        myObject = getTipiComponent(path);
        break;
      case PATH_TO_UNKNOWN:
        myObject = getTipiByPath(path);
        break;
      case PATH_TO_ATTRIBUTE:
        myObject = getAttributeByPath(path);
        break;
      case PATH_TO_ATTRIBUTEREF:
        myObject = getAttributeRefByPath(path);
        break;
      case PATH_TO_RESOURCE:
        myObject = getResource(path);
        break;

    }
  }

//  public Object getObject(){
//    return new Object();
//  }

  public int getPathType(){
    return myType;
  }

  private String getMessagePath(String path){
    StringTokenizer tok = new StringTokenizer(path, ":");
    String typeId = tok.nextToken();
    String tipiPath = tok.nextToken();
    String messagePath = tok.nextToken();
    if(".".equals(messagePath) && myType == PATH_TO_MESSAGE){
      messagePath = ".:"+tok.nextToken();
    }
    return messagePath;
  }

  private String getPropertyPath(String path){
    if(myType == PATH_TO_PROPERTY || myType ==PATH_TO_PROPERTYREF){
      return path.substring(path.lastIndexOf(":") + 1);
    }else{
      //.err.println("ERROR: Requesting property path for a non-property containing path --> " + path);
      return null;
    }
  }

  private String getTipiPath(String path){
    if(path.startsWith("tipi:/") || path.startsWith("property:/") || path.startsWith("propertyref:/") || path.startsWith("message:/") || path.startsWith("component:/") || path.startsWith("attribute:/")|| path.startsWith("attributeref:/")){
      String p = path.substring(path.indexOf(":")+2);
      if(p.indexOf(":") > 0){
        return p.substring(0, p.indexOf(":"));
      }else{
        return p;
      }
    }else{
      if(path.indexOf(":") > 0){
        return path.substring(0, path.indexOf(":"));
      }else{
        return path;
      }
    }
  }

  private Tipi getTipiByPath(String path){
    return (Tipi)getTipiComponent(path);
  }

  private TipiComponent getTipiComponent(String path){
//    System.err.println("Looking for: "+path);
    String tipi_path = getTipiPath(path);
    if(tipi_path.startsWith(".")){                              // Relative path
      return mySource.getTipiComponentByPath(tipi_path);
    }else{                                                      // Absolute path
      return myContext.getTipiComponentByPath(tipi_path);
    }
  }

  private Message getMessageByPath(String path){
    String message_path = getMessagePath(path);

//    System.err.println("PathParser, getting message: " + message_path);

    String first_bit;
    if(message_path.indexOf(":") > -1){
      first_bit = message_path.substring(0, message_path.indexOf(":"));
    }else{
      first_bit = message_path;
    }
    if(first_bit.equals(".")){
      String last_bit = message_path.substring(message_path.indexOf(":")+1);
      return ((Navajo)myTipi.getValue(first_bit)).getMessage(last_bit);
    }else{
      return (Message)myTipi.getValue(first_bit);
    }
  }

  private Property getPropertyByPath(String path){
    String property_path = getPropertyPath(path);
//    System.err.println("PathParser, getting property: " + property_path);
    Message m = getMessageByPath(path);
    if(m != null){
      Property p = m.getPathProperty(property_path);
      //System.err.println("Property value: " + p.getValue());
      return p;
    }else{
//      System.err.println("My tipi path: "+myTipi.getPath());
      Navajo myNavajo = myTipi.getNearestNavajo();


      Property p = myNavajo.getProperty(property_path);
      //System.err.println("Property value (!.): " + p.getValue());
      return p;
    }
  }

  private String getAttribute(String path){
    if(myType == PATH_TO_ATTRIBUTE || myType == PATH_TO_ATTRIBUTEREF){
      return path.substring(path.lastIndexOf(":") + 1);
    }else{
      //System.err.println("ERROR: Requesting attribute for a non-attribute containing path --> " + path);
      return null;
    }

  }

  private Object getAttributeByPath(String path){
    String attribute = getAttribute(path);
    TipiComponent tc = getTipiComponent(path);
    /** @todo Replace by getValue? */
    return tc.getValue(attribute);
  }

  private AttributeRef getAttributeRefByPath(String path){
    String attribute = getAttribute(path);
    TipiComponent tc = getTipiComponent(path);
    return tc.getAttributeRef(attribute);
  }

  public String getAttributeName(){
    return getAttribute(myPath);
  }

  public Object getAttribute(){
    return getAttributeByPath(myPath);
  }

  public AttributeRef getAttributeRef(){
    return getAttributeRefByPath(myPath);
  }

  public Tipi getTipi(){
    return getTipiByPath(myPath);
  }

  public TipiComponent getComponent(){
    return getTipiComponent(myPath);
  }

  public Message getMessage(){
    return getMessageByPath(myPath);
  }

  public Property getProperty(){
    return getPropertyByPath(myPath);
  }

  public URL getResource(){
    return getResource(myPath);
  }

  private URL getResource(String path) {
    int i = path.indexOf(":");
    String rpath = path.substring(i+1);
    return myContext.getResourceURL(rpath);
  }

  public boolean appliesTo(TipiComponent tc) {
    System.err.println("Appliesto: "+myPath);
    Tipi t = getTipiByPath(myPath);
    if (t==tc) {
      System.err.println("Yes, it is me!");
      return true;
    } else {
      System.err.println("No, someone else");
      return false;
    }

  }
}