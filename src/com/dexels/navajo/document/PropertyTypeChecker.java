package com.dexels.navajo.document;

import java.util.*;
import com.dexels.navajo.document.typecheck.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyTypeChecker {

  private static PropertyTypeChecker instance = null;

  private final Map propertyTypeCheckMap = new HashMap();

  private PropertyTypeChecker() {
  }

  public static PropertyTypeChecker getInstance() {
    if (instance==null) {
      instance = new PropertyTypeChecker();
    }
    return instance;
  }

  private void loadTypeChecker(String type) {

    if (type == null || type.equals("")) {
      propertyTypeCheckMap.put("",null);
      return;
    }

    String s = type.substring(1);
    String classname = "com.dexels.navajo.document.typecheck.TypeCheck"+type.substring(0,1).toUpperCase()+s;
    Class c;
    try {
      System.err.println("~~~~~~ Looking for typechecker: "+classname);
      c = Class.forName(classname);
    }
    catch (ClassNotFoundException ex) {
      System.err.println("~~~~~~ Never typechecking again for: "+type+" because: "+ex.getMessage());
//      ex.printStackTrace();
      propertyTypeCheckMap.put(type,null);
      return;
    }
    try {
      TypeChecker tc = (TypeChecker) c.newInstance();
      propertyTypeCheckMap.put(type,tc);
    }
    catch (IllegalAccessException ex1) {
      System.err.println("~~~~~~ Never typechecking again for: "+type+" because: "+ex1.getMessage());
      propertyTypeCheckMap.put(type,null);
//      ex1.printStackTrace();
      return;
    }
    catch (InstantiationException ex1) {
      System.err.println("~~~~~~ Never typechecking again for: "+type+" because: "+ex1.getMessage());
      propertyTypeCheckMap.put(type,null);
//      ex1.printStackTrace();
      return;
    }

  }

  private TypeChecker getTypeChecker(String type) {
//    System.err.println("~~~~~~ Loading typechecker.. for: "+type);
    if (!propertyTypeCheckMap.containsKey(type)) {
      loadTypeChecker(type);
    }
    return (TypeChecker)propertyTypeCheckMap.get(type);
  }


/** @todo For now, only implemented checking for integer properties. May need to refactor a bit
   * to accommodate many propertytypes*/
  public String verify(Property p, String value) throws PropertyTypeException {
    if (p==null) {
     return value;
    }
    if (p.getType()==null) {
      return value;
    }
    TypeChecker tc = getTypeChecker(p.getType());
    if (tc==null) {
      return value;
    }
    return tc.verify(p,value);
//
//    if (p.getType().equals(Property.INTEGER_PROPERTY)) {
//      if (value==null || "".equals(value)) {
//        return;
//      }
//      try {
//        System.err.println("Entering typechecker: "+p.getValue()+" type: "+p.getType()+" new value: "+value+" subtype: "+p.getSubType());
//        int v = Integer.parseInt(value);
//        if (p.getSubType()!=null) {
//          if (Property.SUBTYPE_POSITIVE.equals(p.getSubType())) {
//            if (v<0) {
//              p.setValue((String)null);
//              throw new PropertyTypeException(p,"Not a positive integer.");
//            }
//          }
//          if (Property.SUBTYPE_NEGATIVE.equals(p.getSubType())) {
//            if (v>=0) {
//              p.setValue((String)null);
//              throw new PropertyTypeException(p,"Not a negative integer.");
//            }
//          }
//        }
//      }
//      catch (NumberFormatException ex) {
//        if (p.getSubType()!=null) {
//          p.setValue((String)null);
//          throw new PropertyTypeException(ex,p,"Not a valid integer!");
//        } else {
//          System.err.println("Warning. Ignoring invalid integer: "+value+" for property: "+p.getName());
//        }
//      }
//    }
  }
}
