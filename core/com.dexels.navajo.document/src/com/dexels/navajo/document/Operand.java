package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Operand {
	
  public final String type;
  public final String option;
  public final Object value;

  /**
   * Store a new Operand.
   * An operand is an internal Navajo representation of a value object.
   * Value contains the Java representation.
   * Type describes the Navajo type of the object.
   *
   * @param value
   * @param type
   * @param option
   */
  public Operand(Object value, String type, String option) {
      this.value = value;
      this.type = type;
      this.option = option;
  }

  public Operand(Object value, String type) {
      this.value = value;
      this.type = type;
      this.option = null;
  }

}
