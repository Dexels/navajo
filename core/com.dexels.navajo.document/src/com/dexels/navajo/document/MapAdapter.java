/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

import com.dexels.navajo.document.navascript.tags.MapTag;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public interface MapAdapter extends java.io.Serializable {

  /**
   * Public constants for the map node.
   */
  public static final String MAP_DEFINITION = "map";
  public static final String MAP_CONDITION = "condition";
  public static final String MAP_OBJECT = "object";
  public static final String MAP_REF = "ref";
  public static final String MAP_FILTER = "filter";

  public void addField(Field f);

  public void addParam(Param p);

  public void setParent(MapAdapter m);
  
  public void addMessage(Message m);
  
  public void addMap(MapAdapter m);
  
  public void addProperty(Property p);  // in case of a ref map, properties can be added

  public String getObject();

  public void setObject(String s);

  public String getCondition();

  public void setCondition(String s);

  public String getRefAttribute();

  public void setRefAttribute(String s);

  public String getFilter();

  public void setFilter(String s);

  public Object getRef();
  
  public void addAttributeNameValue(String name, String expression);

  public void addBreak(Break b);
 

}