/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.typecheck;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.PropertyTypeException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TypeCheckInteger extends TypeChecker {
  public TypeCheckInteger() {
  }
  @Override
public String getType() {
    return Property.INTEGER_PROPERTY;
  }
  @Override
public String verify(Property p, String value) throws com.dexels.navajo.document.PropertyTypeException {
   if (value==null) {
      return null;
    }
    value = value.trim();

    if ( "".equals(value)) {
      return value;
    }


    try {
  int v = Integer.parseInt(value);

//    Map m = loadSubtypes(p);
    String max = p.getSubType("max");
    if (max!=null) {
      int mx = Integer.parseInt(max);
      if (v>mx) {
        throw new PropertyTypeException(p,"Integer larger than maximum. ("+mx+">"+v+")");
      }
    }
    String min = p.getSubType("min");
    if (min!=null) {
      int mn = Integer.parseInt(min);
      if (v<mn) {
        throw new PropertyTypeException(p,"Integer smaller than minimum. ("+v+"<"+mn+")");
      }
    }
    }
    catch (NumberFormatException ex) {
      // Only throw type exceptions when subtypes are defined.
      // This is to prevent breaking old code.
      if (p.getSubType()!=null) {
        throw new PropertyTypeException(ex,p,"Not a valid integer. Value: >"+value+"<");
      }
    }

    return value;
  }

}
