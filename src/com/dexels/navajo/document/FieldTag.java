package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface FieldTag {
  /**
  * Public constants for the property node.
  */
 public static final String FIELD_DEFINITION = "field";
 public static final String FIELD_CONDITION = "condition";
 public static final String FIELD_NAME = "name";
 public static final String FIELD_COMMENT = "comment";

 public void addExpression(ExpressionTag e);

 public String getName();

 public void setName(String s);

 public String getComment();

 public void setComment(String s);


 public String getCondition();

 public void setCondition(String s);

  public Object getRef();

}