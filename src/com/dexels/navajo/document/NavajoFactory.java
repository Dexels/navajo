package com.dexels.navajo.document;

import java.util.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public abstract class NavajoFactory {

  protected static NavajoFactory impl = null;

  protected Map defaultSubTypes = new HashMap();


  /**
   * Get the default NavajoFactory implementation.
   *
   * @return
   */
  public static NavajoFactory getInstance() {
    if(impl == null) {
      String name = System.getProperty("com.dexels.navajo.DocumentImplementation");
      //System.out.println(">>>>>>>>>>>>>>>>>USING AS com.dexels.navajo.DocumentImplementation = " + name);
      if(name == null) {
        name = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
      }
      try {
        //System.out.println("USING DOCUMENT IMPLEMENTATION: " + name);
        impl = (NavajoFactory)Class.forName(name).newInstance();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    return impl;
  }


  /**
   * Get a specific NavajoFactory implementation.
   *
   * @param className
   * @return
   */
  public static NavajoFactory getInstance(String className) {
    try {
      return(NavajoFactory)Class.forName(className).newInstance();
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public Map parseSubTypes(String subType) {
    Map m = new HashMap();
    if(subType == null || "".equals(subType)) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(subType, ",");
    while(st.hasMoreTokens()) {
      String next = st.nextToken();
      int i = next.indexOf("=");
      if(i >= 0) {
        String key = next.substring(0, i);
        String value = next.substring(i + 1, next.length());
        m.put(key, value);
      } else {
        System.err.println("--> WARNING: found '" + next + "' subtype, this does not appear to be a key=value pair");
      }
    }
    return m;
  }


  /**
   * Sets the subtype for all the new properties of this type,
   * unless defined otherwise.
   *
   * todo: Change this into something like addDefaultSubType(String type,String key, String value)
   */
  public void setDefaultSubtypeForType(String type, String subtype) {
    defaultSubTypes.put(type, subtype);
  }


  public String getDefaultSubtypeForType(String type) {
    return(String)defaultSubTypes.get(type);
  }


//    public static final String[] VALID_DATA_TYPES = new String[] {
//    STRING_PROPERTY,INTEGER_PROPERTY,LONG_PROPERTY,DATE_PROPERTY,FLOAT_PROPERTY,MONEY_PROPERTY,CLOCKTIME_PROPERTY,
//    URL_PROPERTY,MEMO_PROPERTY,BOOLEAN_PROPERTY,POINTS_PROPERTY,DATE_PATTERN_PROPERTY,PASSWORD_PROPERTY,
//    TIPI_PROPERTY,BINARY_PROPERTY
//};

  private ExpressionEvaluator myExpressionEvaluator = null;

  public ExpressionEvaluator getExpressionEvaluator() {
    return myExpressionEvaluator;
  }

  public void setExpressionEvaluator(ExpressionEvaluator e) {
    myExpressionEvaluator = e;
  }


  /**
   * Used to convert classnames to property types. E.g. java.lang.String -> Property.STRING_PROPERTY
   * It will not return all types. The following types will never be returned, because they do not
   * map 1-1 on java classes:
   * MEMO_PROPERTY, DATE_PATTERN_PROPERTY, PASSWORD_PROPERTY, TIPI_PROPERTY
   */
  public String getPropertyType(String className) {
    if(className.equals("java.lang.String")) {
      return Property.STRING_PROPERTY;
    }
    if(className.equals("java.lang.Integer") || className.equals("int")) {
      return Property.INTEGER_PROPERTY;
    }
    if(className.equals("java.lang.Long") || className.equals("long")) {
      return Property.LONG_PROPERTY;
    }
    if(className.equals("java.util.Date")) {
      return Property.DATE_PROPERTY;
    }
    if(className.equals("java.lang.Float") || className.equals("float") || className.equals("double")) {
      return Property.FLOAT_PROPERTY;
    }
    if(className.equals("com.dexels.navajo.document.types.ClockTime")) {
      return Property.CLOCKTIME_PROPERTY;
    }
    if(className.equals("java.net.URL")) {
      return Property.URL_PROPERTY;
    }
    if(className.equals("java.lang.Boolean") || className.equals("boolean")) {
      return Property.BOOLEAN_PROPERTY;
    }
    if(className.equals("com.dexels.navajo.document.Point")) {
      return Property.STRING_PROPERTY;
    }
    if(className.equals("byte[]")) {
      return Property.BINARY_PROPERTY;
    }
    // as a default...
    return Property.STRING_PROPERTY;
  }

  public abstract NavajoException createNavajoException(String message);

  public abstract NavajoException createNavajoException(Exception e);

  public abstract Navajo createNavajo(java.io.InputStream stream);

  public abstract Navajo createNavajo(Object representation);

  public abstract Navajo createNavajo();

  public abstract Navajo createNavaScript(java.io.InputStream stream);

  public abstract Navajo createNavaScript(Object representation);

  public abstract Navajo createNavaScript();

  public abstract Header createHeader(Navajo n, String rpcName, String rpcUser, String rpcPassword, long expiration_interval);

  public abstract Message createMessage(Object representation);

  public abstract Message createMessage(Navajo n, String name);

  public abstract Message createMessage(Navajo tb, String name, String type);

  public abstract Property createProperty(Object representation);

  public abstract Property createProperty(Navajo tb, String name, String cardinality, String description, String direction) throws NavajoException;

  public abstract Property createProperty(Navajo tb, String name, String type, String value, int length, String description, String direction) throws NavajoException;

  public abstract ExpressionTag createExpression(Navajo tb, String condition, String value) throws NavajoException;

  public abstract FieldTag createField(Navajo tb, String condition, String name) throws NavajoException;

  public abstract ParamTag createParam(Navajo tb, String condition, String name) throws NavajoException;

  public abstract MapTag createMapObject(Navajo tb, String object, String condition) throws NavajoException;

  public abstract MapTag createMapRef(Navajo tb, String ref, String condition, String filter) throws NavajoException;

  public abstract Selection createSelection(Navajo tb, String name, String value, boolean selected);

  public abstract Selection createDummySelection();

  public abstract Method createMethod(Navajo tb, String name, String server);

  public abstract Point createPoint(Property p) throws NavajoException;

  public abstract LazyMessagePath createLazyMessagePath(Navajo tb, String path, int startIndex, int endIndex, int total);

  public abstract LazyMessage createLazyMessage(Navajo tb, String name, int windowSize);

}
