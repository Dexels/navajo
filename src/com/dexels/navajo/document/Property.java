
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;

import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.xml.XMLutils;

/**
 * The property class defines property object which are used for defining several
 * types of variables within a message (see @Message.class) object. Supported types are: <BR>
 * string, integer, float, date, boolean, memo, selection and points. <BR>
 * The selection and points properties are complex properties. A selection property
 * contains selection objects (see @Selection.class). <BR>
 * A points property can store a list of carthesian coordinates.
 */
public class Property  {


  public Element ref;

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

  public Property(Element e) {
    this.ref = e;
  }

  private static boolean validDirection(String dir) {
    if (dir.equals(DIR_IN) ||
          dir.equals(DIR_OUT) ||
          dir.equals(DIR_INOUT))
      return true;
    else
      return false;
  }

  private static boolean validType(String type) {
    if (type.equals(STRING_PROPERTY) ||
        type.equals(INTEGER_PROPERTY) ||
        type.equals(DATE_PROPERTY) ||
        type.equals(FLOAT_PROPERTY) ||
        type.equals(URL_PROPERTY) ||
        type.equals(MEMO_PROPERTY) ||
        type.equals(BOOLEAN_PROPERTY) ||
        type.equals(POINTS_PROPERTY) ||
        type.equals(DATE_PATTERN_PROPERTY) ||
        type.equals(PASSWORD_PROPERTY))
      return true;
    else
      return false;
  }

  /**
   * Create a new property object for a specific Navajo object with given parameters.
   */
  public static Property create(Navajo tb, String name, String type, String value, int length,
                                String description, String direction) throws NavajoException {

    Property p = null;

    Document d = tb.getMessageBuffer();

    Element n = (Element) d.createElement(Property.PROPERTY_DEFINITION);
    p = new Property(n);
    p.setName(name);
    if (!validType(type))
      p.setType(Property.STRING_PROPERTY);
      //throw new NavajoException("Invalid property type specified: " + type);
    if (!validDirection(direction))
      p.setDirection(Property.DIR_OUT);
      //throw new NavajoException("Invalid direction indicator specified: " + direction);
    p.setType(type);
    if (!value.equals(""))
      p.setValue(value);
    if (length != -1)
      p.setLength(length);
    if (!description.equals(""))
      p.setDescription(description);
    p.setDirection(direction);

    return p;
  }

  /**
   * Create a selection property object.
   */
  public static Property create(Navajo tb, String name, String cardinality, String description, String direction) throws NavajoException {



    Property p = null;

    Document d = tb.getMessageBuffer();

    Element n = (Element) d.createElement(Property.PROPERTY_DEFINITION);
    p = new Property(n);
    if (!(cardinality.equals("+") || cardinality.equals("1")))
      throw new NavajoException("Invalid cardinality specified: " + cardinality);
    p.setCardinality(cardinality);
    p.setName(name);
    p.setType(Property.SELECTION_PROPERTY);
    if (!description.equals(""))
      p.setDescription(description);
    p.setDirection(direction);

    return p;
  }

  private Selection existsSelection(String name) throws NavajoException {

    if (!this.getType().equals(Property.SELECTION_PROPERTY))
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

    NodeList list = ref.getChildNodes();

    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
        Element e = (Element) list.item(i);
        if (e.getAttribute(Selection.SELECTION_NAME).equals(name)) {
          return new Selection(e);
        }
      }
    }

    return null;
  }

  /**
   * Get a selection option with a specific name if it exists. If it does not exists, return null.
   * If the property is not of type "selection", a NavajoException is thrown.
   */
  public Selection getSelection(String name) throws NavajoException {

    if (!this.getType().equals(Property.SELECTION_PROPERTY))
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

    NodeList list = ref.getChildNodes();

    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
        Element e = (Element) list.item(i);
        if (e.getAttribute(Selection.SELECTION_NAME).equals(name)) {
          return new Selection(e);
        }
      }
    }
    // If selection name is not found return a dummy selection which is set to false.
    return Selection.createDummy();
  }

   /**
   * Get a selection option with a specific name if it exists. If it does not exists, return null.
   * If the property is not of type "selection", a NavajoException is thrown.
   */
  public Selection getSelectionByValue(String value) throws NavajoException {

    if (!this.getType().equals(Property.SELECTION_PROPERTY))
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

    NodeList list = ref.getChildNodes();

    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
        Element e = (Element) list.item(i);
        if (e.getAttribute(Selection.SELECTION_VALUE).equals(value)) {
          return new Selection(e);
        }
      }
    }
    // If selection name is not found return a dummy selection which is set to false.
    return Selection.createDummy();
  }

  /**
   * Return all selection objects in the property. If the property object is not of a selection type,
   * a NavajoException is thrown.
   */
  public ArrayList getAllSelections() throws NavajoException {
    ArrayList h = new ArrayList();

    if (!this.getType().equals(Property.SELECTION_PROPERTY))
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

    Selection p = null;
    NodeList list = ref.getChildNodes();

    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
        Element e = (Element) list.item(i);
        p = new Selection(e);
        h.add(p);
      }
    }

    return h;
  }

  public String getFullPropertyName() {

    Node n = ref.getParentNode();
    while (!n.getNodeName().equals("message")) {
      n = n.getParentNode();
    }
    Element parent = (Element) n;
    Message msg = new Message(parent);
    String msgName = msg.getFullMessageName();

    return msgName + Navajo.MESSAGE_SEPARATOR + this.getName();
  }

  /**
   * Unsets all options for a selection property.
   */
  public void clearSelections() throws NavajoException {
    if (!this.getType().equals(Property.SELECTION_PROPERTY))
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

    ArrayList list = getAllSelections();
    for (int i = 0; i < list.size(); i++) {
      Selection sel = (Selection) list.get(i);
      sel.setSelected(false);
    }
  }

  /**
   * Return all selection objects in the property. If the property object is not of a selection type,
   * a NavajoException is thrown.
   */
  public ArrayList getAllSelectedSelections() throws NavajoException {
    ArrayList h = new ArrayList();

    if (!this.getType().equals(Property.SELECTION_PROPERTY))
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

    Selection p = null;
    NodeList list = ref.getChildNodes();

    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
        Element e = (Element) list.item(i);
        //Util.debugLog(e.getAttribute(Selection.SELECTION_NAME));
        //Util.debugLog(e.getAttribute(Selection.SELECTION_SELECTED));
        p = new Selection(e);
        if (p.isSelected())
          h.add(p);
      }
    }

    return h;
  }

  /**
   * Add a selection option to a "selection" property. If the option name already exists, replace
   * it with the new one. If the property is not a "selection" type, a NavajoException is thrown.
   */
  public void addSelection(Selection s) throws NavajoException {
    if (this.getType().equals(Property.SELECTION_PROPERTY)) {
      Selection dummy = this.existsSelection(s.getName());
      if (dummy != null)
        ref.removeChild(dummy.ref);
      ref.appendChild(s.ref);
    }
    else
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);
  }

  public void addSelectionWithoutReplace(Selection s) throws NavajoException {
    if (this.getType().equals(Property.SELECTION_PROPERTY)) {
      ref.appendChild(s.ref);
    }
    else
      throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);
  }

  /**
   * Get the property name.
   */
  public String getName() {
    return ref.getAttribute(Property.PROPERTY_NAME);
  }

  /**
   * Set the property name (required). A property name must be unique within a message.
   */
  public void setName(String name) {
    ref.setAttribute(Property.PROPERTY_NAME, name);
  }

  /**
   * Get the type of a property (see PROPERTY_* constants).
   */
  public String getType() {
   return ref.getAttribute(Property.PROPERTY_TYPE);
  }

  /**
   * Set the type of a property (required).
   */
  public void setType(String type) {
    ref.setAttribute(Property.PROPERTY_TYPE, type);
  }

  /**
   * Get the value of a (string, integer, float, boolean, date or memo) property.
   */
  public String getValue() {
    return ref.getAttribute(Property.PROPERTY_VALUE);
  }

  public String toString() {
    return ref.getAttribute(Property.PROPERTY_VALUE);
  }

  /**
   * Set the value of a (string, integer, float, boolean, date or memo) property.
   */
  public void setValue(String value) {
    // TODO: typechecking (Optionally!)
    ref.setAttribute(Property.PROPERTY_VALUE, value); //XMLutils.string2unicode(value));
  }

  /**
   * Sets the selected option for a selection type property.
   */
  public void setSelected(String value) throws NavajoException {
     if (!this.getType().equals(Property.SELECTION_PROPERTY))
        throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

      ArrayList list = this.getAllSelections();
      for (int i = 0; i < list.size(); i++) {
         Selection sel = (Selection) list.get(i);
         if (sel.getValue().equals(value)) {
            sel.setSelected(true);
         }
         else {
            if (this.getCardinality().equals("1")) // If cardinality 1, unset all other options.
            sel.setSelected(false);
         }
      }
  }

  public void setSelected(ArrayList keys) throws NavajoException {
    if (!this.getType().equals(Property.SELECTION_PROPERTY))
        throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);

    ArrayList list = this.getAllSelections();
    for (int i = 0; i < list.size(); i++) {
       ((Selection) list.get(i)).setSelected(false);
    }

    for (int j = 0; j < list.size(); j++) {
      Selection sel = (Selection) list.get(j);
      if (keys.contains(sel.getValue()))
          sel.setSelected(true);
    }

  }

  public void setSelected(String [] keys) throws NavajoException {
    if (!this.getType().equals(Property.SELECTION_PROPERTY))
        throw new NavajoException(NavajoException.NOT_PROPERTY_SELECTION);
    ArrayList l = new ArrayList(keys.length);
    for (int i = 0; i < keys.length; i++)
      l.add(keys[i]);
    setSelected(l);
  }

  /**
   * Get the length attribute of a (string, integer or float) property. The length defines
   * the maximum number of posititions a property value may have.
   */
  public int getLength() {
    String length = ref.getAttribute(Property.PROPERTY_LENGTH);
    if (!length.equals(""))
      return Integer.parseInt(length);
    else
      return 0;
  }

  /**
   * Set the length attribute of a (string, integer or float) property.
   */
  public void setLength(int length) {
    ref.setAttribute(Property.PROPERTY_LENGTH, length+"");
  }

  /**
   * Set the description attribute of a general property. This attribute has no functional
   * meaning, it is used merely for describing a property.
   */
  public String getDescription() {
    return ref.getAttribute(Property.PROPERTY_DESCRIPTION);
  }

  /**
   * Get the description of a general property. This attribute is not required, hence
   * it could not be present.
   */
  public void setDescription(String description) {
    ref.setAttribute(Property.PROPERTY_DESCRIPTION, description);
  }

  /**
   * Get the direction of a general property. The direction defines whether the property
   * is input (in), output (out) or both (inout).
   */
  public String getDirection() {
    return ref.getAttribute(Property.PROPERTY_DIRECTION);
  }

  /**
   * Set the direction of a general property.
   */
  public void setDirection(String dir) {
    ref.setAttribute(Property.PROPERTY_DIRECTION, dir);
  }

  /**
   * Get the cardinality of a "selection" property. The cardinality defines the maximum number
   * of options that can be selected from a "selection" property. Currently to values are
   * supported: "+", for multiple selections and "1", for single selections.
   */
  public String getCardinality() {
   return ref.getAttribute(Property.PROPERTY_CARDINALITY);
  }

  /**
   * Set the cardinality of a "selection" property. If the property is not
   * of type "selection" the attribute is set anyway, however it is ignored.
   */
  public void setCardinality(String c) {
    ref.setAttribute(Property.PROPERTY_CARDINALITY, c);
  }

  /**
   * Set the cartesian coordinates of a "points" property. Points is an array of Vectors.
   * Each vector contains the "point" (of any dimensionality).
   */
  public void setPoints(Vector [] points) throws NavajoException {

    if (!ref.getAttribute(Property.PROPERTY_TYPE).equals(Property.POINTS_PROPERTY))
      throw new NavajoException("Only points properties support this method.");

    Document d = (Document) this.ref.getOwnerDocument();

    for (int i = 0; i < points.length; i++) {
      Element e = (Element) d.createElement("value");
      this.setLength(points[i].size());
      for (int j = 0; j < points[i].size(); j++) {
        String attrName = "x"+j;
        String value = (String) points[i].get(j);
        Util.debugLog("setPoints(): "+attrName+"="+value);
        e.setAttribute(attrName, value);
      }
      this.ref.appendChild(e);
    }
  }

  /**
   * Return the cartesian coordinates of a "points" property.
   * The return value is an array of Vectors (containing floats).
   */
  public Vector [] getPoints() throws NavajoException {

    if (!ref.getAttribute(Property.PROPERTY_TYPE).equals(Property.POINTS_PROPERTY))
      throw new NavajoException("Only points properties support this method.");

    Vector [] pointArray = null;

    int count = 0;

    NodeList list = ref.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i).getNodeName().equals("value")) {
        count++;
      }
    }

    pointArray = new Vector[count];

    count = 0;
    for (int i = 0; i < list.getLength(); i++) {
    if (list.item(i).getNodeName().equals("value")) {
        Element e = (Element) list.item(i);
        pointArray[count] = new Vector();
        for (int j = 0; j < this.getLength(); j++) {
          String attrName = "x"+j;
          String value = e.getAttribute(attrName);
          pointArray[count].add(value);
        }
        count++;
      }
    }
    return pointArray;
  }

}
