package com.dexels.navajo.tipi.internal;

import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

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
  public static final int PATH_TO_UNKNOWN = 3;
  public static final int PATH_TO_COMPONENT = 4;
  public static final int PATH_TO_ATTRIBUTE = 5;
  public static final int PATH_TO_PROPERTYREF = 6;
  public static final int PATH_TO_ATTRIBUTEREF = 7;
  public static final int RESOURCE_DEF = 8;
  public static final int BORDER_DEF = 9;
  public static final int COLOR_DEF = 10;
  public static final int FONT_DEF = 11;
  public static final int URL_DEF = 12;
  private int myType = 3;
  private String myPath = "";
  private Object myObject;
  private TipiComponent mySource;
  private TipiContext myContext;
//  private TipiComponent myTipi;
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
  private void parse(String path) {
    if (path.startsWith("tipi:/")) {
      myType = PATH_TO_TIPI;
    }
    else if (path.startsWith("message:/")) {
      myType = PATH_TO_MESSAGE;
    }
    else if (path.startsWith("property:/")) {
      myType = PATH_TO_PROPERTY;
    }
    else if (path.startsWith("propertyref:/")) {
      myType = PATH_TO_PROPERTYREF;
    }
    else if (path.startsWith("component:/")) {
      myType = PATH_TO_COMPONENT;
    }
    else if (path.startsWith("attribute:/")) {
      myType = PATH_TO_ATTRIBUTE;
    }
    else if (path.startsWith("attributeref:/")) {
      myType = PATH_TO_ATTRIBUTEREF;
    }
    else if (path.startsWith("resource:/")) {
      myType = RESOURCE_DEF;
    }
    else if (path.startsWith("font:/")) {
      myType = FONT_DEF;
    }
    else if (path.startsWith("border:/")) {
      myType = BORDER_DEF;
    }
    else if (path.startsWith("color:/")) {
      myType = COLOR_DEF;
    }
    else if (path.startsWith("url:/")) {
      myType = URL_DEF;
    }
    else {
      myType = PATH_TO_UNKNOWN; // assuming tipi
    }
//    myTipi = getTipiComponent(path);
    switch (myType) {
      case PATH_TO_TIPI:
        myObject = getTipiByPath(path);
        break;
      case PATH_TO_MESSAGE:
        myObject = getMessageByPath(path);
        break;
      case PATH_TO_PROPERTY:
        myObject = getPropertyByPath(path);
        break;
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
      case RESOURCE_DEF:
        myObject = getResource(path);
        break;
      case COLOR_DEF:
        myObject = getColor(path);
        break;
      case BORDER_DEF:
        myObject = getBorder(path);
        break;
      case FONT_DEF:
        myObject = getFont(path);
        break;
      case URL_DEF:
        myObject = getUrl(path);
        break;
    }
  }

  public Object getObject() {
    return myObject;
  }

  public int getPathType() {
    return myType;
  }

  private String getMessagePath(String path) {
    StringTokenizer tok = new StringTokenizer(path, ":");
    String typeId = tok.nextToken();
    String tipiPath = tok.nextToken();
    String messagePath = tok.nextToken();
    if (".".equals(messagePath) && myType == PATH_TO_MESSAGE) {
      messagePath = ".:" + tok.nextToken();
    }
    return messagePath;
  }

  private String getPropertyPath(String path) {
    if (myType == PATH_TO_PROPERTY || myType == PATH_TO_PROPERTYREF) {
      return path.substring(path.lastIndexOf(":") + 1);
    }
    else {
      //.err.println("ERROR: Requesting property path for a non-property containing path --> " + path);
      return null;
    }
  }

  private String getTipiPath(String path) {
    if (path.startsWith("tipi:/") || path.startsWith("property:/") || path.startsWith("propertyref:/") || path.startsWith("message:/") || path.startsWith("component:/") || path.startsWith("attribute:/") || path.startsWith("attributeref:/")) {
      String p = path.substring(path.indexOf(":") + 2);
      if (p.indexOf(":") > 0) {
        return p.substring(0, p.indexOf(":"));
      }
      else {
        return p;
      }
    }
    else {
      if (path.indexOf(":") > 0) {
        return path.substring(0, path.indexOf(":"));
      }
      else {
        return path;
      }
    }
  }

  private TipiDataComponent getTipiByPath(String path) {
    return (TipiDataComponent) getTipiComponent(path);
  }

  private TipiComponent getTipiComponent(String path) {
    String tipi_path = getTipiPath(path);
    if (tipi_path.startsWith(".")) { // Relative path
      return mySource.getTipiComponentByPath(tipi_path);
    }
    else { // Absolute path
      return myContext.getTipiComponentByPath(tipi_path);
    }
  }

  private Message getMessageByPath(String path) {
    TipiComponent myTipi = getTipiComponent(path);
    String message_path = getMessagePath(path);
    String first_bit;
    if (message_path.indexOf(":") > -1) {
      first_bit = message_path.substring(0, message_path.indexOf(":"));
    }
    else {
      first_bit = message_path;
    }
    if (first_bit.equals(".")) {
      String last_bit = message_path.substring(message_path.indexOf(":") + 1);
      return ( (Navajo) myTipi.getValue(first_bit)).getMessage(last_bit);
    }
    else {
      return (Message) myTipi.getValue(first_bit);
    }
  }

  private Property getPropertyByPath(String path) {
    TipiComponent myTipi = getTipiComponent(path);
    String property_path = getPropertyPath(path);
    Message m = getMessageByPath(path);
    if (m != null) {
      Property p = m.getPathProperty(property_path);
      return p;
    }
    else {
      Navajo myNavajo = myTipi.getNearestNavajo();
      Property p = myNavajo.getProperty(property_path);
      return p;
    }
  }

  private String getAttribute(String path) {
    if (myType == PATH_TO_ATTRIBUTE || myType == PATH_TO_ATTRIBUTEREF) {
      return path.substring(path.lastIndexOf(":") + 1);
    }
    else {
      return null;
    }
  }

  private Object getAttributeByPath(String path) {
    String attribute = getAttribute(path);
    TipiComponent tc = getTipiComponent(path);
    return tc.getValue(attribute);
  }

  private AttributeRef getAttributeRefByPath(String path) {
    String attribute = getAttribute(path);
    TipiComponent tc = getTipiComponent(path);
    return tc.getAttributeRef(attribute);
  }

  public String getAttributeName() {
    return getAttribute(myPath);
  }

  public Object getAttribute() {
    return getAttributeByPath(myPath);
  }

  public AttributeRef getAttributeRef() {
    return getAttributeRefByPath(myPath);
  }

  public TipiDataComponent getTipi() {
    return getTipiByPath(myPath);
  }

  public TipiComponent getComponent() {
    return getTipiComponent(myPath);
  }

  public Message getMessage() {
    return getMessageByPath(myPath);
  }

  public Property getProperty() {
    return getPropertyByPath(myPath);
  }

  private Object getPropertyValue(String path) {
    Property p = getPropertyByPath(path);
    if (p != null) {
      return p.getTypedValue();
    }
    return null;
  }

  public URL getResource() {
    return getResource(myPath);
  }

  private URL getResource(String path) {
    if (myType == RESOURCE_DEF) {
      int i = path.indexOf(":");
      String rpath = path.substring(i + 2);
      return myContext.getResourceURL(rpath);
    }
    throw new IllegalArgumentException("Invalid type: " + path + " is not not a resource!");
  }

  public boolean appliesTo(TipiComponent tc) {
//    System.err.println("Appliesto: " + myPath);
    TipiDataComponent t = getTipiByPath(myPath);
    if (t == tc) {
//      System.err.println("Yes, it is me!");
      return true;
    }
    else {
//      System.err.println("No, someone else");
      return false;
    }
  }

  private Color getColor(String path) {
    if (myType == COLOR_DEF) {
      return parseColor(path.substring(path.indexOf(":") + 2));
    }
    else {
      throw new IllegalArgumentException("Invalid type: " + path + " is not not a color!");
    }
  }

  private Border getBorder(String path) {
    if (myType == BORDER_DEF) {
      return parseBorder(path.substring(path.indexOf(":") + 2));
    }
    else {
      throw new IllegalArgumentException("Invalid type: " + path + " is not not a border!");
    }
  }

  private Font getFont(String path) {
    if (myType == FONT_DEF) {
      return parseFont(path.substring(path.indexOf(":") + 2));
    }
    else {
      throw new IllegalArgumentException("Invalid type: " + path + " is not not a font!");
    }
  }

  private URL getUrl(String path) {
    try {
      if (myType == URL_DEF) {
        int i = path.indexOf(":");
        String urlPath = path.substring(i + 2);
        return new URL(urlPath);
      }
      else {
        throw new IllegalArgumentException("Invalid type: " + path + " is not not an url!");
      }
    }
    catch (MalformedURLException ex) {
      throw new IllegalArgumentException("supplied url not valid for: " + path);
    }
  }

  private Border parseBorder(String s) {
    StringTokenizer st = new StringTokenizer(s, "-");
    String borderName = st.nextToken();
    if ("etched".equals(borderName)) {
      return BorderFactory.createEtchedBorder();
    }
    if ("raised".equals(borderName)) {
      return BorderFactory.createRaisedBevelBorder();
    }
    if ("lowered".equals(borderName)) {
      return BorderFactory.createLoweredBevelBorder();
    }
    if ("titled".equals(borderName)) {
      String title = st.nextToken();
      return BorderFactory.createTitledBorder(title);
    }
    if ("indent".equals(borderName)) {
      try {
        int top = Integer.parseInt(st.nextToken());
        int left = Integer.parseInt(st.nextToken());
        int bottom = Integer.parseInt(st.nextToken());
        int right = Integer.parseInt(st.nextToken());
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
      }
      catch (Exception ex) {
        System.err.println("Error while parsing border");
      }
    }
    return BorderFactory.createEmptyBorder();
  }

  private Color parseColor(String s) {
    if (!s.startsWith("#")) {
      throw new RuntimeException("BAD COLOR: " + s);
    }
    String st = s.substring(1);
    int in = Integer.parseInt(st, 16);
    return new Color(in);
  }

  private Font parseFont(String s) {
    StringTokenizer str = new StringTokenizer(s, "-");
    String name = str.nextToken();
    int size = Integer.parseInt(str.nextToken());
    int style = Integer.parseInt(str.nextToken());
    Font f = new Font(name, style, size);
    return f;
  }
}
