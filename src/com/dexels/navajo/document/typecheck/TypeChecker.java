package com.dexels.navajo.document.typecheck;

import com.dexels.navajo.document.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TypeChecker {
  public abstract String verify(Property p, String value) throws PropertyTypeException;
  public abstract String getType();

  /** @todo Maybe it would be useful to do some caching here.
  * I think it would be better that the subtypes don't need to be parsed every time,
  * or, maybe better still, use a map as subtype, and parse in the property */

//  protected Map loadSubtypes(Property p) {
//    return parseSubTypes(p.getSubType());
//  }
//

//
//  public String getSubType(String subType, String key){
//    Map m = parseSubTypes(subType);
//    if(m != null){
//      return (String)m.get(key);
//    }
//    return null;
//  }
}
