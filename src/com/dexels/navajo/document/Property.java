package com.dexels.navajo.document;
import nanoxml.*;
import java.util.*;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */


public interface Property {
  /**
   * Public constants for the property node.
   */
  public static final String PROPERTY_DEFINITION = "property";
  public static final String PROPERTY_NAME = "name";
  public static final String PROPERTY_VALUE = "value";
  public static final String PROPERTY_DESCRIPTION = "description";
  public static final String PROPERTY_LENGTH = "length";
  public static final String PROPERTY_CARDINALITY = "cardinality";
  public static final String PROPERTY_TYPE = "type";
  public static final String PROPERTY_DIRECTION = "direction";

  /**
   * Valid data types for Navajo properties.
   */
  public static final String UNKNOWN_PROPERTY = "unknown";
  public static final String STRING_PROPERTY = "string";
  public static final String INTEGER_PROPERTY = "integer";
  public static final String LONG_PROPERTY = "long";
  public static final String DATE_PROPERTY = "date";
  public static final String FLOAT_PROPERTY = "float";
  public static final String URL_PROPERTY = "url";
  public static final String SELECTION_PROPERTY = "selection";
  public static final String MEMO_PROPERTY = "memo";
  public static final String BOOLEAN_PROPERTY = "boolean";
  public static final String POINTS_PROPERTY = "points";
  public static final String DATE_PATTERN_PROPERTY = "date_pattern";
  public static final String PASSWORD_PROPERTY = "password";

  /**
   * Navajo Date Format
   */

  public static final String NAVAJO_DATEFORMAT = "yyyy-MM-dd HH:mm:ss.SS";


  /**
   * Valid direction settings for Navajo properties.
   */
  public static final String DIR_IN = "in";
  public static final String DIR_OUT = "out";
  public static final String DIR_INOUT = "inout";

  /**
   * !!
   */
  public static final String TRUE = "true";
  public static final String FALSE = "false";


  public Object getValue();
  public Object getTypedValue();
  public String getName();
  public String getType();
  public void setType(String t);

  public XMLElement toXml(XMLElement parent);
  public void fromXml(XMLElement e);
  public void addSelection(Selection s);
  public void removeSelection(Selection s);
  public void setAllSelected(boolean b);
  public void setSelected(Selection s);
  public void setSelectedByValue(Object value);
  public void setValue(Object value);
  public void setName(String name);
  public ArrayList getAllSelections();
  public Selection getSelection(String name);
  public Selection getSelectionByValue(String value);
  public Navajo getRootDoc();
  public Selection getSelected();
  public Property copy(Navajo n);
  public void setRootDoc(Navajo n);
  public void prune();
  public String getFullPropertyName();
  public void setMessageName(String m);
  public Message getParent();
  public void setParent(Message m);
  public String getPath();
  public String getDescription();
  public String getDirection();
  public boolean isEditable();
//  public int compare(Property p);
  public int compareTo(Object parm1);
  public Object getAlternativeTypedValue();

}