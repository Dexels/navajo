package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
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