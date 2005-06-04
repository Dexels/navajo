package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public interface ExpressionTag extends java.io.Serializable, Comparable, Cloneable {

  /**
   * Public constants for the property node.
   */
  public static final String EXPRESSION_DEFINITION = "expression";
  public static final String EXPRESSION_CONDITION = "condition";
  public static final String EXPRESSION_VALUE = "value";

  public String getValue();

  public void setValue(String s);

  public String getCondition();

  public void setCondition(String s);

   public Object getRef();

}