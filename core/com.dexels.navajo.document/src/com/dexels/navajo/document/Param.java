/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface Param extends Property {
	/**
	 * Public constants for the param node.
	 */
	public static final String PARAM_DEFINITION = "param";
	public static final String PARAM_CONDITION = "condition";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_TYPE = "type";
	public static final String PARAM_COMMENT = "comment";
	public static final String PARAM_MODE = "mode";

	public String getName();

	public void setName(String s);

	public String getComment();
	
	public String getMode();

	public void setComment(String s);

	public String getCondition();

	public void setCondition(String s);

	public Object getRef();
	

}