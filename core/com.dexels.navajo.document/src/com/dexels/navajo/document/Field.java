package com.dexels.navajo.document;

import java.io.Serializable;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public interface Field extends Property {
	/**
	 * Public constants for the property node.
	 */
	public static final String FIELD_DEFINITION = "field";
	public static final String FIELD_CONDITION = "condition";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_COMMENT = "comment";

	public String getName();

	public void setName(String s);

	public String getComment();

	public void setComment(String s);

	public void setParent(MapAdapter p);
	
	public String getCondition();

	public void setCondition(String s);

	public Object getRef();
	

}