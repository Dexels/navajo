package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyTypeException extends RuntimeException {
  public PropertyTypeException(Property p, String message) {
    super("Property type exception: "+message+" property: "+p.getName()+" type: "+p.getType()+" subtype: "+p.getSubType()+" value: "+p.getValue());
  }
  public PropertyTypeException(Throwable cause, Property p, String message) {
    super("Property type exception: "+message+" property: "+p.getName()+" type: "+p.getType()+" subtype: "+p.getSubType()+" value: "+p.getValue(),cause);

  }
}
