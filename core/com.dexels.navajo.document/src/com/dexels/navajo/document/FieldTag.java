/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
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