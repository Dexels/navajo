package com.dexels.navajo.document.typecheck;

import java.util.regex.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TypeCheckString extends TypeChecker {
  
	private final static Logger logger = LoggerFactory
		.getLogger(TypeCheckString.class);
  public TypeCheckString() {
  }
  public String getType() {
    return Property.STRING_PROPERTY;
  }

  /** @todo check number of invokations... Still seems to be called too often */

  public String verify(Property p, String value) throws com.dexels.navajo.document.PropertyTypeException {
//    logger.info("Entering string checker: "+value);
    if (value==null || "".equals(value)) {
      return value;
    }
    String cap = p.getSubType("capitalization");
    String regexp = p.getSubType("regexp");
    if (cap!=null) {
      if ("upper".equals(cap)) {
//        logger.info("Upper: "+value.toUpperCase());
        return value.toUpperCase();
      }
      if ("lower".equals(cap)) {
//        logger.info("Upper: "+value.toLowerCase());
        return value.toLowerCase();
      }
    }
    if (regexp!=null) {
        String message = p.getSubType("regexp_error");
        if (message==null) {
            message="String format error!";
        }
        if (!Pattern.matches(regexp, value)) {
            throw new PropertyTypeException(p,message);
        }
    }
    return value;
  }

public static void main(String[] args) {
    String regexp = "f*b";
    String value = "fb";
    String message ="aaaaap";
    if (!Pattern.matches(regexp, value)) {
        logger.info("Regexp: "+regexp+" failed on: "+value+" with message: "+message+" with oldvalue: -");
//            throw new PropertyTypeException(p,message);
    } else {
        logger.info("Regexp matches!");
    }

}

}
