package com.dexels.navajo.document;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Coordinate;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.NavajoExpression;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface Property extends java.io.Serializable, Comparable<Property>, Cloneable {

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
  public static final String PROPERTY_KEY = "key";
  public static final String PROPERTY_REFERENCE = "reference";
  public static final String PROPERTY_EXTENDS = "extends";
  public static final String PROPERTY_BIND = "bind";
  public static final String PROPERTY_METHOD = "method";


  public static final String ANY_PROPERTY = "any";

  
  public static final String STRING_PROPERTY = "string";
  public static final String INTEGER_PROPERTY = "integer";
  public static final String LONG_PROPERTY = "long";
  public static final String DATE_PROPERTY = "date";
  public static final String TIMESTAMP_PROPERTY = "timestamp";

  public static final String FLOAT_PROPERTY = "float";
  public static final String MONEY_PROPERTY = "money";
  public static final String PERCENTAGE_PROPERTY = "percentage";
  public static final String CLOCKTIME_PROPERTY = "clocktime";
  public static final String EXPRESSION_PROPERTY = "expression";
  public static final String EXPRESSION_LITERAL_PROPERTY = "expressionliteral";
  public static final String URL_PROPERTY = "url";
  public static final String SELECTION_PROPERTY = "selection";
  public static final String LIST_PROPERTY = "list";
  public static final String MEMO_PROPERTY = "memo";
  public static final String BOOLEAN_PROPERTY = "boolean";
  public static final String POINTS_PROPERTY = "points";
  public static final String DATE_PATTERN_PROPERTY = "date_pattern";
  public static final String PASSWORD_PROPERTY = "password";
  public static final String TIPI_PROPERTY = "tipi";
  public static final String BINARY_PROPERTY = "binary";
  public static final String BINARY_DIGEST_PROPERTY = "binary_digest";
  public static final String STOPWATCHTIME_PROPERTY = "stopwatchtime";

  public static final String COORDINATE_PROPERTY = "coordinate";

  public static final String SUBTYPE_REQUIRED = "required";
  public static final String SUBTYPE_POSITIVE = "positive";
  public static final String SUBTYPE_NEGATIVE = "negative";

  // For ease of use in the studio.
  // Needless to say, if you add a type, add it here as well.
  public static final String[] VALID_DATA_TYPES = new String[] {
      STRING_PROPERTY, INTEGER_PROPERTY, LONG_PROPERTY, DATE_PROPERTY,
            FLOAT_PROPERTY, MONEY_PROPERTY, CLOCKTIME_PROPERTY, COORDINATE_PROPERTY,
      URL_PROPERTY, MEMO_PROPERTY, BOOLEAN_PROPERTY, POINTS_PROPERTY,
      DATE_PATTERN_PROPERTY, PASSWORD_PROPERTY,
      TIPI_PROPERTY, BINARY_PROPERTY, EXPRESSION_PROPERTY, PERCENTAGE_PROPERTY, STOPWATCHTIME_PROPERTY, LIST_PROPERTY
  };

  
  // maybe DATE_PATTERN_PROPERTY?
  public static final String[] STRING_DATA_TYPES = new String[] {
	  STRING_PROPERTY,MEMO_PROPERTY,PASSWORD_PROPERTY
  };
  
  //Binary data properties.
  //I am not sure what to do with these. Not supported by the studio
  /*
   * @deprecaded. Use binary.
   */
  public static final String GIF_PROPERTY = "image/gif";
  /*
   * @deprecaded. Use binary.
   */
 public static final String JPEG_PROPERTY = "image/jpeg";

  // Valid direction settings for Navajo properties.
  public static final String DIR_IN = "in";
  public static final String DIR_OUT = "out";

  // DIR_INOUT is deprecated, should NOT be used, use in instead.
  public static final String DIR_INOUT = "inout";

  public static final String[] VALID_DIRECTIONS = new String[] {
      DIR_IN, DIR_OUT, DIR_INOUT
  };

  public static final String CARDINALITY_SINGLE = "1";
  public static final String CARDINALITY_MULTIPLE = "+";

  public static final String[] VALID_CARDINALITIES = new String[] {
      CARDINALITY_SINGLE, CARDINALITY_MULTIPLE
  };

  public static final String TRUE = "true";
  public static final String FALSE = "false";

  public static final String DATE_FORMAT1 = "yyyy-MM-dd HH:mm:ss.SS";
  public static final String DATE_FORMAT2 = "yyyy-MM-dd";
  public static final String DATE_FORMAT3 = "dd-MM-yyyy";
  public static final String DATE_FORMAT4 = "yyyy-MM-dd HH:mm:ss:SS";
  
  public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

  public boolean isEqual(Property o);

  /**
   * See if a Selection with the given name exists in tis Property
   * @param name String
   * @throws NavajoException
   * @return Selection
   */
  public Selection existsSelection(String name);

  /**
   * Get a selection option with a specific name if it exists. If it does not exists, return null.
   * If the property is not of type "selection", a NavajoException is thrown.
   */
  public Selection getSelection(String name);

  /**
   * Get a selection option with a specific value if it exists. If it does not exists, return null.
   * If the property is not of type "selection", a NavajoException is thrown.
   */
  public Selection getSelectionByValue(String value);

  /**
   * Return all selection objects in the property. If the property object is not of a selection type,
   * a NavajoException is thrown.
   */
  public List<Selection> getAllSelections();

  /**
   * Get the full Property name
   * @throws NavajoException
   * @return String
   */
  public String getFullPropertyName();

  /**
   * Unsets all options for a selection property.
   */
  public void clearSelections();

  /**
   * Creates a new property, with the supplied Navajo as root doc
   */
  public Property copy(Navajo n);

  /**
   * Removes a selection from a selection property
   * @param Selection s
   */
  public void removeSelection(Selection s);

  /**
   * Removes all selections from a selection property
   * @throws NavajoException
   */

  public void removeAllSelections();

  /**
   * Return all selection objects in the property. If the property object is not of a selection type,
   * a NavajoException is thrown.
   */
  public List<Selection> getAllSelectedSelections();

  public void addExpression(ExpressionTag e);

  /**
   * Add a selection option to a "selection" property. If the option name already exists, replace
   * it with the new one. If the property is not a "selection" type, a NavajoException is thrown.
   *  @param Selection s
   * @throws NavajoException
   */
  public void addSelection(Selection s);

  /**
   * Add a selection option to a "selection" property. If the option name already exists, we DO NOT replace
   * it with the new one. If the property is not a "selection" type, a NavajoException is thrown.
   * @param s Selection
   * @throws NavajoException
   */
  public void addSelectionWithoutReplace(Selection s);

  /**
   * Get the property name.
   * @return String name
   */
  public String getName();

  /**
   * Set the property name (required). A property name must be unique within a message.
   * @param String name
   */
  public void setName(String name);

  /**
   * Get the type of a property (see PROPERTY_* constants).
   * @return String type
   */
  public String getType();

  /**
   * Get the complete subtype attribute of a property.
   *
   * @return String subType
   */
  public String getSubType();

  /**
   * Sets the complete subtype attribute of a property.
   *
   * @param subType the subtype key.
   */
  public void setSubType(String subType);

  /**
   * Get the value of subtype key/value pair.
   *
   * @param key the subtype key
   * @return the subtype value
   */
  public String getSubType(String key);

  /**
   * Sets the key attribute for this property. Key attributes are used for entity messages.
   * Example: key="true,auto,optional", if key has true as value the underlying property is marked as a 'key'.
   * The auto option defines that a property is automatically created by an insert operation, i.e. it should
   * not be passed when inserting a new entity.
   * 
   * @param key
   */
  public void setKey(String key);
  
  
  /**
   * Return the key attribute of a property.
   * 
   * @return
   */
  public String getKey();
  
  /**
   * Sets the extends attribute to extend a property from another entity.
   * 
   * @param s
   */
  public void setExtends(String s);
  
  public String getExtends();
  
  /**
   * Set the bind attribute to bind this property to another property (from another entity)
   * 
   * @param s
   */
  public void setBind(String s);
  
  public String getBind();
  
  
  /**
   * Set the method attribute to indicate whether this property is relevant for request, response, or both
   * 
   * @param s: "request", "response", ""
   */
  public void setMethod(String s);
  
  public String getMethod();
  
  
  /**
   * Sets the reference attribute for a property. Reference can be used to indicate that this property value can be used
   * to find a specific entity that is specified by the reference value.
   * 
   * @param ref
   * @return
   */
  public void setReference(String ref);
  
  /**
   * Get the reference attribute for a property.
   * 
   * @return
   */
  public String getReference();
  
  /**
   * Adds a single subtype pair. Syntax key=value
   * @param extra
   */
  public void addSubType(String extra);

  /**
   * Adds or changes a single subtype pair. 
   * @param key
   * @param value
   */
  public void addSubType(String key, String value);
  
  /**
	 * Gets all subtypes in a map
	 * This map is a copy, so there is no point in modifying.
	 * 
	 * @return
	 */
  
  public Map<String,String> getSubTypes();
	
  /**
   * Also gets the type of a property (see PROPERTY_* constants).
   * However, this type is based on the type returned by the expression.
   * Will throw a NavajoException when this is not of type EXPRESSION
   * @return String evaluated type
   */
  public String getEvaluatedType();

  /**
   * Refresh expression
   * @throws NavajoException
   */
  public void refreshExpression() throws ExpressionChangedException;

  /**
   * Set the type of a property (required).
   * @param String type
   */
  public void setType(String type);

    /**
     * Get the value of a (string, integer, float, boolean, date, clocktime, money,
     * coordinate or memo) property.
     * 
     * @return String representation of this Property's value object
     */
  public String getValue();

    /**
     * Get the typed value (String, Integer, Double, Boolean, Date, Money,
     * ClockTime, Coordinate byte [])
     * 
     * @return Object value
     */
  public Object getTypedValue();

  /**
   * Return the String representation of this Property
   * @return String
   */
  @Override
public String toString();

  /**
   * Reset the value
   */
  public void clearValue();

  /**
   * Does the same as setValue(String) however, it will return the value that
   * was actually set. This can be different than the original paramenter, because
   * the typechecker may have changed the case, truncated, or changed it somehow.
   * @param String value
   * @return String checkedValue
   */
  public String setCheckedValue(String v);

  /**
   * Set the value of a (string, integer, float, boolean, date or memo) property.
   * Try to use the setCheckedValue, if you need to know what the value is that was
   * actually set.
   */
  public void setValue(String value);

  public void setValue(java.util.Date value);

  public void setValue(Integer value);

  public void setValue(Double value);

  public void setValue(Boolean value);

  public void setValue(int value);

  public void setLongValue(long value);

  public void setValue(long value);

  
  public void setValue(double value);

  public void setValue(float value);

  public void setValue(boolean value);

  public void setValue(Binary data);

  public void setValue(URL url);

  public void setValue(Money m);

    public void setValue(Coordinate c);

  public void setValue(Percentage value);

  public void setValue(ClockTime ct);
  
  public void setValue(NavajoExpression ne);

  public void setValue(StopwatchTime swt);
  
  public void setValue(Selection [] l);

  /**
   * Sets the selected option for a selection type property.
   */
  public void setSelected(String value);

  /**
   * Set multiple selections for a selection type Property with a Cardinality '+'
   * The list should list the property VALUES!
   * @param keys ArrayList
   * @throws NavajoException
   */
  public void setSelected(List<String> keys);

  /**
   * Set multiple selections for a selection type Property with a Cardinality '+'
   * @param keys String[]
   * @throws NavajoException
   */
  public void setSelected(String[] keys);

  /**
   * Sets the selected option for a selection type property.
   * @param s Selection
   * @throws NavajoException
   */

  public void setSelected(Selection s);

  /**
   * Sets the selected option for a selection type property.
   * @param s Selection
   * @throws NavajoException
   */

  public void setSelected(Selection s, boolean selected);

  
  /**
   * Get the length attribute of a (string, integer or float) property. The length defines
   * the maximum number of posititions a property value may have.
   */
  public int getLength();

  /**
   * Set the length attribute of a (string, integer or float) property.
   * @param length int
   */
  public void setLength(int length);

  /**
   * Set the description attribute of a general property. This attribute has no functional
   * meaning, it is used merely for describing a property.
   * @return String description
   */
  public void setDescription(String description);

  /**
   * Get the description of a general property. This attribute is not required, hence
   * it could not be present.
   * @param String description
   */
  public String getDescription();

  /**
   * Get the direction of a general property. The direction defines whether the property
   * is input (in), output (out) or both (inout).
   * @return String direction
   */
  public String getDirection();

  /**
   * Set the direction of a general property.
   * @param String direction
   */
  public void setDirection(String dir);

  /**
   * Get the cardinality of a "selection" property. The cardinality defines the maximum number
   * of options that can be selected from a "selection" property. Currently to values are
   * supported: "+", for multiple selections and "1", for single selections.
   * @return String cardinality
   */
  public String getCardinality();

  /**
   * Set the cardinality of a "selection" property. If the property is not
   * of type "selection" the attribute is set anyway, however it is ignored.
   * @param String cardinality
   */
  public void setCardinality(String c);

  /**
   * Get the message object in which this property is located.
   *
   * @return Message parent
   */
  public Message getParentMessage();

  /**
   * Return the internal implementation specific representation of the Property.
   *
   * @return Object ref
   */
  public Object getRef();

  /**
   * Returns true if the type of this Property is DIR_IN or DIR_INOUT
   * @return boolean
   */

  public boolean isDirIn();

  /**
   * Returns true if the type of this Property is DIR_OUT or DIR_INOUT
   * @return boolean
   */

  public boolean isDirOut();

  /**
   * Return the Navajo doc this object is part of.
   * @return Navajo rootDoc
   */
  public Navajo getRootDoc();

  /**
   * Replace the Navajo doc this object is part of with this one.
   * @param Navajo rootDoc
   */
  public void setRootDoc(Navajo n);

  /**
   * Returns the selected selection. Will return a dummyselection when none is selected, and will throw a NavajoException
   * if more than one is Selected.
   * @return Selection selected
   */

  public Selection getSelected();

  /**
   * @return a cloned Property belonging to the same parent document
   * @return Object clone
   */

  public Object clone();

  /**
   * @return a cloned Property belonging to the same parent document, but giving
   * it a new name
   * @return Object clone
   */

  public Object clone(final String newName);

  /**
   * Looks for the appropriate setter for this object, and sets it.
   *  From now on, I think this is the safest accessor, if you are unsure about the type
   * (for example, the result from an operand)
   *  what type you are dealing with. Nano and jaxp are identical.
   * @param Object value
   */
  public void setAnyValue(Object o);
  
  /**
   * Looks for the appropriate setter for this object, and sets it.
   *  From now on, I think this is the safest accessor, if you are unsure about the type
   * (for example, the result from an operand)
   *  what type you are dealing with. Nano and jaxp are identical.
   * @param Object value
   * @param Boolean internal if the setValue is internal, no tipi event will be fired for "onValueChanged"
   */
  public void setAnyValue(Object o, Boolean internal);
  
  public void setUnCheckedStringAsValue(String s);
  
/**
 * Clones the Property, only it ignores the value
 * @return
 */
public Property cloneWithoutValue();

/**
 * Adding
 * @param p
 */
public void addPropertyChangeListener(PropertyChangeListener p);
public void removePropertyChangeListener(PropertyChangeListener p);

public void forcePropertyChange();

// for evaluating expressions
public Object peekEvaluatedValue();

public void printElement(final Writer sw, int indent) throws IOException;

public boolean printStartTag(final Writer sw, int indent,boolean forceDualTags) throws IOException ;
public void printBody(final Writer sw, int indent) throws IOException;
public void printCloseTag(final Writer sw, int indent) throws IOException;

}
