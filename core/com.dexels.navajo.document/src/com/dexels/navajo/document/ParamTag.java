package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ParamTag {
  /**
  * Public constants for the param node.
  */
 public static final String PARAM_DEFINITION = "param";
 public static final String PARAM_CONDITION = "condition";
 public static final String PARAM_NAME = "name";
 public static final String PARAM_COMMENT = "comment";

 public void addExpression(ExpressionTag e);

 public String getName();

 public void setName(String s);

 public String getComment();

 public void setComment(String s);

 public String getCondition();

 public void setCondition(String s);

  public Object getRef();

}