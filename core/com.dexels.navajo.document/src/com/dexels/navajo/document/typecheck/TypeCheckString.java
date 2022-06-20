/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.typecheck;

import java.util.regex.Pattern;

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

public class TypeCheckString extends TypeChecker {
  
	private static final Logger logger = LoggerFactory
		.getLogger(TypeCheckString.class);
  public TypeCheckString() {
  }
  @Override
public String getType() {
    return Property.STRING_PROPERTY;
  }

  @Override
public String verify(Property p, String value) {
    if (value==null || "".equals(value)) {
      return value;
    }
    String cap = p.getSubType("capitalization");
    String regexp = p.getSubType("regexp");
    if (cap!=null) {
      if ("upper".equals(cap)) {
        return value.toUpperCase();
      }
      if ("lower".equals(cap)) {
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
    } else {
        logger.info("Regexp matches!");
    }

}

}
