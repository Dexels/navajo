package com.dexels.navajo.document;

import java.util.*;
import java.text.SimpleDateFormat;
import java.net.URL;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface Property extends java.io.Serializable, Comparable, Cloneable {

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
  public static final String PROPERTY_MIMETYPE = "mimetype";
  public static final String PROPERTY_SUBTYPE = "subtype";

  /**
   * Valid data types for Navajo properties.
   */
  public static final String STRING_PROPERTY = "string";
  public static final String INTEGER_PROPERTY = "integer";
  public static final String LONG_PROPERTY = "long";
  public static final String DATE_PROPERTY = "date";
  public static final String FLOAT_PROPERTY = "float";
  public static final String MONEY_PROPERTY = "money";
  public static final String PERCENTAGE_PROPERTY = "percentage";
  public static final String CLOCKTIME_PROPERTY = "clocktime";
  public static final String EXPRESSION_PROPERTY = "expression";
  public static final String URL_PROPERTY = "url";
  public static final String SELECTION_PROPERTY = "selection";
  public static final String MEMO_PROPERTY = "memo";
  public static final String BOOLEAN_PROPERTY = "boolean";
  public static final String POINTS_PROPERTY = "points";
  public static final String DATE_PATTERN_PROPERTY = "date_pattern";
  public static final String PASSWORD_PROPERTY = "password";
  public static final String TIPI_PROPERTY = "tipi";
  public static final String BINARY_PROPERTY = "binary";

  /**
   * Valid data types for Navajo properties.
   */
  public static final String SUBTYPE_POSITIVE = "positive";
  public static final String SUBTYPE_NEGATIVE = "negative";


  // For ease of use in the studio.
  // Needless to say, if you add a type, add it here as well.
  public static final String[] VALID_DATA_TYPES = new String[] {
      STRING_PROPERTY,INTEGER_PROPERTY,LONG_PROPERTY,DATE_PROPERTY,FLOAT_PROPERTY,MONEY_PROPERTY,CLOCKTIME_PROPERTY,
      URL_PROPERTY,MEMO_PROPERTY,BOOLEAN_PROPERTY,POINTS_PROPERTY,DATE_PATTERN_PROPERTY,PASSWORD_PROPERTY,
      TIPI_PROPERTY,BINARY_PROPERTY,EXPRESSION_PROPERTY,PERCENTAGE_PROPERTY
  };
  // Added, as general p
//  public static final String UNKNOWN_PROPERTY = "unknown";

  /**
   * Binary data properties.
   * I am not sure what to do with these. Not supported by the studio
   */
  public static final String GIF_PROPERTY = "image/gif";
  public static final String JPEG_PROPERTY = "image/jpeg";

  /**
   * Valid direction settings for Navajo properties.
   */
  public static final String DIR_IN = "in";
  public static final String DIR_OUT = "out";
  /**
   * DIR_INOUT is deprecated, should NOT be used, use in instead.
   */
  public static final String DIR_INOUT = "inout";


  public static final String[] VALID_DIRECTIONS = new String[] {
      DIR_IN,DIR_OUT,DIR_INOUT
  };

  public static final String CARDINALITY_SINGLE = "1";
  public static final String CARDINALITY_MULTIPLE = "+";

  public static final String[] VALID_CARDINALITIES = new String[] {
      CARDINALITY_SINGLE,CARDINALITY_MULTIPLE
  };

  /**
   * !!
   */
  public static final String TRUE = "true";
  public static final String FALSE = "false";

  public static final SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
  public static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd-MM-yyyy");

  public boolean isEqual(Property o);

  public Selection existsSelection(String name) throws NavajoException;

  /**
   * Get a selection option with a specific name if it exists. If it does not exists, return null.
   * If the property is not of type "selection", a NavajoException is thrown.
   */
  public Selection getSelection(String name) throws NavajoException;

  /**
   * Get a selection option with a specific name if it exists. If it does not exists, return null.
   * If the property is not of type "selection", a NavajoException is thrown.
   */
  public Selection getSelectionByValue(String value) throws NavajoException;

  /**
   * Return all selection objects in the property. If the property object is not of a selection type,
   * a NavajoException is thrown.
   */
  public ArrayList getAllSelections() throws NavajoException;

  public String getFullPropertyName() throws NavajoException;

  /**
   * Unsets all options for a selection property.
   */
  public void clearSelections() throws NavajoException;

  /**
   * Creates a new property, with the supplied Navajo as root doc
   */
  public Property copy(Navajo n);


  /**
   * Removes a selection from a selection property
   */

  public void removeSelection(Selection s) throws NavajoException;

  /**
   * Removes all selections from a selection property
   */

  public void removeAllSelections() throws NavajoException;


  /**
   * Return all selection objects in the property. If the property object is not of a selection type,
   * a NavajoException is thrown.
   */
  public ArrayList getAllSelectedSelections() throws NavajoException;

  public void addExpression(ExpressionTag e) throws NavajoException;

  /**
   * Add a selection option to a "selection" property. If the option name already exists, replace
   * it with the new one. If the property is not a "selection" type, a NavajoException is thrown.
   */
  public void addSelection(Selection s) throws NavajoException;

  public void addSelectionWithoutReplace(Selection s) throws NavajoException;

  /**
   * Get the property name.
   */
  public String getName();

  /**
   * Set the property name (required). A property name must be unique within a message.
   */
  public void setName(String name);

  /**
   * Get the type of a property (see PROPERTY_* constants).
   */
  public String getType();


  public String getSubType();

  /**
   * Also gets the type of a property (see PROPERTY_* constants).
  * However, this type is based on the type returned by the expression.
  * Will throw a NavajoException when this is not of type EXPRESSIoN
   */
  public String getEvaluatedType() throws NavajoException;


  public void refreshExpression() throws NavajoException;

  /**
   * Set the type of a property (required).
   */
  public void setType(String type);

  /**
       * Get the value of a (string, integer, float, boolean, date, clocktime, money or memo) property.
   */
  public String getValue();

  /**
   * Get the typed value (String, Integer, Double, Boolean, Date, Money, ClockTime, byte [])
   * @return
   */
  public Object getTypedValue();

  public String toString();

  /**
   * Reset the value
   */
  public void clearValue();

  /**
       * Set the value of a (string, integer, float, boolean, date or memo) property.
   */
  public void setValue(String value);
  public void setValue(java.util.Date value);
  public void setValue(Integer value);
  public void setValue(Double value);
  public void setValue(Boolean value);
  public void setValue(int value);
  public void setValue(long value);
  public void setValue(double value);
  public void setValue(float value);
  public void setValue(boolean value);
  public void setValue(Binary data);
  //public void setValue(File f);
  //public void setValue(byte [] data);
  public void setValue(URL url);
  public void setValue(Money m);
  public void setValue(Percentage value);
   public void setValue(ClockTime ct);

  /**
   * Sets the selected option for a selection type property.
   */
  public void setSelected(String value) throws NavajoException;

  public void setSelected(ArrayList keys) throws NavajoException;

  public void setSelected(String[] keys) throws NavajoException;

  public void setSelected(Selection s) throws NavajoException;

  /**
   * Get the length attribute of a (string, integer or float) property. The length defines
   * the maximum number of posititions a property value may have.
   */
  public int getLength();

  /**
   * Set the length attribute of a (string, integer or float) property.
   */
  public void setLength(int length);

  /**
   * Set the description attribute of a general property. This attribute has no functional
   * meaning, it is used merely for describing a property.
   */
  public String getDescription();

  /**
   * Get the description of a general property. This attribute is not required, hence
   * it could not be present.
   */
  public void setDescription(String description);

  /**
   * Get the direction of a general property. The direction defines whether the property
   * is input (in), output (out) or both (inout).
   */
  public String getDirection();

  /**
   * Set the direction of a general property.
   */
  public void setDirection(String dir);

  /**
   * Get the cardinality of a "selection" property. The cardinality defines the maximum number
   * of options that can be selected from a "selection" property. Currently to values are
   * supported: "+", for multiple selections and "1", for single selections.
   */
  public String getCardinality();

  /**
   * Set the cardinality of a "selection" property. If the property is not
   * of type "selection" the attribute is set anyway, however it is ignored.
   */
  public void setCardinality(String c);

  /**
   * Get the message object in which this property is located.
   *
   * @return
   */
  public Message getParentMessage();

  /**
   * Set the cartesian coordinates of a "points" property. Points is an array of Vectors.
   * Each vector contains the "point" (of any dimensionality).
   */
  public void setPoints(Vector[] points) throws NavajoException;

  /**
   * Return the cartesian coordinates of a "points" property.
   * The return value is an array of Vectors (containing floats).
   */
  public Vector[] getPoints() throws NavajoException;

  /**
   * Return the internal implementation specific representation of the Message.
   *
   * @return
   */
  public Object getRef();

  /**
   * Returns true if the type of this Property is DIR_IN or DIR_INOUT
   */

  public boolean isDirIn();

  /**
   * Returns true if the type of this Property is DIR_OUT or DIR_INOUT
   */

  public boolean isDirOut();

  /**
   * Return the Navajo doc this object is part of.
   */
  public Navajo getRootDoc();

  /**
   * Replace the Navajo doc this object is part of with this one.
   */
  public void setRootDoc(Navajo n);

  /**
   * Returns the selected selection. Will return a dummyselection when none is selected, and will throw a NavajoException
   * if more than one is Selected.
   */

  public Selection getSelected() throws NavajoException;

  /**
   * @return a cloned Property belonging to the same parent document
   */

  public Object clone();

  /**
   * @return a cloned Property belonging to the same parent document, but giving
   * it a new name
   */

  public Object clone( final String newName );

}
