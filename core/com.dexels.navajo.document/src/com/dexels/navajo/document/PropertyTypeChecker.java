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

  private final Map<String,TypeChecker> propertyTypeCheckMap = new HashMap<String,TypeChecker>();

  private PropertyTypeChecker() {
  }

//  public String getSubType(String type, String subType, String key){
//    loadTypeChecker(type);
//    TypeChecker tc = (TypeChecker)propertyTypeCheckMap.get(type);
//    if(tc != null){
//      return tc.getSubType(subType, key);
//    }
//    return null;
//  }

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
    Class<?> c;
    try {
      c = Class.forName(classname);
    }
    catch (ClassNotFoundException ex) {
      propertyTypeCheckMap.put(type,null);
      return;
    }
    try {
      TypeChecker tc = (TypeChecker) c.newInstance();
      propertyTypeCheckMap.put(type,tc);
    }
    catch (IllegalAccessException ex1) {
      propertyTypeCheckMap.put(type,null);
      return;
    }
    catch (InstantiationException ex1) {
      propertyTypeCheckMap.put(type,null);
      return;
    }

  }

  private TypeChecker getTypeChecker(String type) {
    if (!propertyTypeCheckMap.containsKey(type)) {
      loadTypeChecker(type);
    }
    return propertyTypeCheckMap.get(type);
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
 }
}
