package com.dexels.navajo.document.typecheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class TypeCheckMoney extends TypeChecker {
	
	private static final Logger logger = LoggerFactory
			.getLogger(TypeCheckMoney.class);
	
  public TypeCheckMoney() {
  }
  @Override
public String getType() {
    return Property.MONEY_PROPERTY;
  }
  @Override
public String verify(Property p, String value) throws com.dexels.navajo.document.PropertyTypeException {
    if (value==null) {
         return null;
       }
      value = value.trim();
    if ("".equals(value)) {
      return value;
    }

    try {
  double v = Double.parseDouble(value.replace( ',', '.'));

//    Map m = loadSubtypes(p);
    String max = p.getSubType("max");
    if (max!=null) {
      int mx = Integer.parseInt(max);
      if (v>mx) {
        throw new PropertyTypeException(p,"Money larger than maximum. ("+mx+">"+v+")");
      }
    }
    String min = p.getSubType("min");
    if (min!=null) {
      int mn = Integer.parseInt(min);
      if (v<mn) {
        throw new PropertyTypeException(p,"Money smaller than minimum. ("+v+"<"+mn+")");
      }
    }
    }
    catch (NumberFormatException ex) {
      // Only throw type exceptions when subtypes are defined.
      // This is to prevent breaking old code.
      if (p.getSubType()!=null) {
        throw new PropertyTypeException(ex,p,"Not a valid money property!");
      } else {
        logger.info("Warning. Ignoring invalid money: "+value+" for property: "+p.getName());
      }
    }

    return value;
  }

}
