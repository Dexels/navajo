package com.dexels.navajo.mapping;

/**
 * $Id$
 * AM -> AO
 * AO -> AM
 * AO(x) -> AO(y)
 * AM(in) -> AM(out)
 *
 */


import com.dexels.navajo.document.*;

public final class XmlMapperInterpreter {

  
  public final static String getPropertyPart(String name) {
    int e = name.lastIndexOf(Navajo.MESSAGE_SEPARATOR);

    return name.substring(0, e);
  }

  public static void main(String[] args) {
    String s = "";
    System.out.println("isAssignable = " +
                       Object.class.isAssignableFrom(s.getClass()));
    System.out.println("isAssignable = " + s.getClass().isAssignableFrom(Object.class));
  }
}
