

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.*;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;


/**
 * The property class defines property object which are used for defining several
 * types of variables within a message (see @Message.class) object. Supported types are: <BR>
 * string, integer, float, date, boolean, memo, selection and points. <BR>
 * The selection and points properties are complex properties. A selection property
 * contains selection objects (see @Selection.class). <BR>
 * A points property can store a list of carthesian coordinates.
 *
 * TODO:
 * Add support for binary data properties:
 *
 * - gif/jpeg
 * - word
 * - binary
 * - pdf
 *
 */
public class PropertyImpl implements Property, Comparable {

    public Element ref;
    private Navajo myRootDoc = null;

    public PropertyImpl(Element e) {
        this.ref = e;
    }

    private static boolean validDirection(String dir) {
        if (dir.equals(DIR_IN) || dir.equals(DIR_OUT) || dir.equals(DIR_INOUT))
            return true;
        else
            return false;
    }

    private static boolean validType(String type) {
        if (type.equals(STRING_PROPERTY) || type.equals(INTEGER_PROPERTY)
                || type.equals(DATE_PROPERTY) || type.equals(FLOAT_PROPERTY)
                || type.equals(URL_PROPERTY) || type.equals(MEMO_PROPERTY)
                || type.equals(BOOLEAN_PROPERTY) || type.equals(POINTS_PROPERTY)
                || type.equals(DATE_PATTERN_PROPERTY)
                || type.equals(PASSWORD_PROPERTY))
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

        Document d = (Document) tb.getMessageBuffer();

        Element n = (Element) d.createElement(Property.PROPERTY_DEFINITION);

        p = new PropertyImpl(n);
        p.setName(name);
        if (!validType(type))
            p.setType(Property.STRING_PROPERTY);
        // throw new NavajoException("Invalid property type specified: " + type);
        if (!validDirection(direction))
            p.setDirection(Property.DIR_OUT);
        // throw new NavajoException("Invalid direction indicator specified: " + direction);
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

        Document d = (Document) tb.getMessageBuffer();

        Element n = (Element) d.createElement(Property.PROPERTY_DEFINITION);

        p = new PropertyImpl(n);
        if (!(cardinality.equals("+") || cardinality.equals("1")))
            throw new NavajoExceptionImpl("Invalid cardinality specified: " + cardinality);
        p.setCardinality(cardinality);
        p.setName(name);
        p.setType(Property.SELECTION_PROPERTY);
        if (!description.equals(""))
            p.setDescription(description);
        p.setDirection(direction);

        return p;
    }

    public Selection existsSelection(String name) throws NavajoException {

        if (!this.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoExceptionImpl("existsSelection(): Selection property required for this operation");

        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
                Element e = (Element) list.item(i);

                if (e.getAttribute(Selection.SELECTION_NAME).equals(name)) {
                    return new SelectionImpl(e);
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
            throw new NavajoExceptionImpl("getSelection(): Selection property required for this operation");

        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
                Element e = (Element) list.item(i);

                if (e.getAttribute(Selection.SELECTION_NAME).equals(name)) {
                    return new SelectionImpl(e);
                }
            }
        }
        // If selection name is not found return a dummy selection which is set to false.
        return SelectionImpl.createDummy();
    }

    /**
     * Get a selection option with a specific name if it exists. If it does not exists, return null.
     * If the property is not of type "selection", a NavajoException is thrown.
     */
    public Selection getSelectionByValue(String value) throws NavajoException {

        if (!this.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoExceptionImpl("getSelectionByValue(): Selection property required for this operation");

        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
                Element e = (Element) list.item(i);

                if (e.getAttribute(Selection.SELECTION_VALUE).equals(value)) {
                    return new SelectionImpl(e);
                }
            }
        }
        // If selection name is not found return a dummy selection which is set to false.
        return SelectionImpl.createDummy();
    }

    /**
     * Return all selection objects in the property. If the property object is not of a selection type,
     * a NavajoException is thrown.
     */
    public ArrayList getAllSelections() throws NavajoException {
        ArrayList h = new ArrayList();

        if (!this.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoExceptionImpl("getAllSelections(): Selection property required for this operation");

        Selection p = null;
        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
                Element e = (Element) list.item(i);

                p = new SelectionImpl(e);
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
        Message msg = new MessageImpl(parent);
        String msgName = msg.getFullMessageName();

        return msgName + Navajo.MESSAGE_SEPARATOR + this.getName();
    }

    /**
     * Unsets all options for a selection property.
     */
    public void clearSelections() throws NavajoException {
        if (!this.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoExceptionImpl("clearSelections(): Selection property required for this operation");

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
            throw new NavajoExceptionImpl("getAllSelectedSelections(): Selection property required for this operation");

        Selection p = null;
        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Selection.SELECTION_DEFINITION)) {
                Element e = (Element) list.item(i);

                p = new SelectionImpl(e);
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
                ref.removeChild((Node) dummy.getRef());
            ref.appendChild((Node) s.getRef());
        } else
            throw new NavajoExceptionImpl("addSelection(): Selection property required for this operation");
    }

    public void addSelectionWithoutReplace(Selection s) throws NavajoException {
        if (this.getType().equals(Property.SELECTION_PROPERTY)) {
            ref.appendChild((Node) s.getRef());
        } else
            throw new NavajoExceptionImpl("addSelectionWithoutReplace(): Selection property required for this operation");
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
      if (getType().equals(Property.STRING_PROPERTY) || getType().equals(Property.MEMO_PROPERTY) || getType().equals(Property.PASSWORD_PROPERTY))
          return XMLutils.XMLUnescape(ref.getAttribute(Property.PROPERTY_VALUE));
      else
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
        if (getType().equals(Property.STRING_PROPERTY) || getType().equals(Property.MEMO_PROPERTY) || getType().equals(Property.PASSWORD_PROPERTY))
          ref.setAttribute(Property.PROPERTY_VALUE, XMLutils.XMLEscape(value));
        else
          ref.setAttribute(Property.PROPERTY_VALUE, value); // XMLutils.string2unicode(value));
    }

    /**
     * Sets the selected option for a selection type property.
     */
    public void setSelected(String value) throws NavajoException {
        if (!this.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoExceptionImpl("setSelected(): Selection property required for this operation");

        ArrayList list = this.getAllSelections();

        for (int i = 0; i < list.size(); i++) {
            Selection sel = (Selection) list.get(i);

            if (sel.getValue().equals(value)) {
                sel.setSelected(true);
            } else {
                if (this.getCardinality().equals("1")) // If cardinality 1, unset all other options.
                    sel.setSelected(false);
            }
        }
    }

    public void setSelected(ArrayList keys) throws NavajoException {
        if (!this.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoExceptionImpl("setSelected(): Selection property required for this operation");

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

    public void setSelected(String[] keys) throws NavajoException {
        if (!this.getType().equals(Property.SELECTION_PROPERTY))
            throw new NavajoExceptionImpl("setSelected(): Selection property required for this operation");
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
        ref.setAttribute(Property.PROPERTY_LENGTH, length + "");
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
    public void setPoints(Vector[] points) throws NavajoException {

        if (!ref.getAttribute(Property.PROPERTY_TYPE).equals(Property.POINTS_PROPERTY))
            throw new NavajoExceptionImpl("Only points properties support this method.");

        Document d = (Document) this.ref.getOwnerDocument();

        for (int i = 0; i < points.length; i++) {
            Element e = (Element) d.createElement("value");

            this.setLength(points[i].size());
            for (int j = 0; j < points[i].size(); j++) {
                String attrName = "x" + j;
                String value = (String) points[i].get(j);


                e.setAttribute(attrName, value);
            }
            this.ref.appendChild(e);
        }
    }

    /**
     * Return the cartesian coordinates of a "points" property.
     * The return value is an array of Vectors (containing floats).
     */
    public Vector[] getPoints() throws NavajoException {

        if (!ref.getAttribute(Property.PROPERTY_TYPE).equals(Property.POINTS_PROPERTY))
            throw new NavajoExceptionImpl("Only points properties support this method.");

        Vector[] pointArray = null;

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
                    String attrName = "x" + j;
                    String value = e.getAttribute(attrName);

                    pointArray[count].add(value);
                }
                count++;
            }
        }
        return pointArray;
    }

    public Message getParentMessage() {
        Node n = ref.getParentNode();
        if (n instanceof Element) {
          Element e = (Element) n;
          if (e.getTagName().equals("message"))
            return new MessageImpl(e);
          else
            return null;
        } else
          return null;
    }

    public Object getRef() {
      return this.ref;
    }
  public boolean isDirOut() {
    return DIR_OUT.equals(getDirection()) || DIR_INOUT.equals(getDirection());
  }
  public boolean isDirIn() {
//    System.err.println("In dirin: "+DIR_IN+" / "+DIR_INOUT+" / "+getType());
    return DIR_IN.equals(getDirection()) || DIR_INOUT.equals(getDirection());
  }

  public Navajo getRootDoc() {
    return myRootDoc;
  }

  public void setRootDoc(Navajo n) {
    myRootDoc = n;
  }

/**
 * Added compareTo, to implement Comparable. (Copied it from the nanoimpl)
 * This interface is needed to be able to create sortable tables
 */

  public int compareTo(Object p) {
    if (p==null) {
      return 0;
    }

    Comparable ob1 = (Comparable)getValue();
    Comparable ob2 = (Comparable)((Property)p).getValue();
    if (ob1==null || ob2==null) {
      return 0;
    }

    if (ob1.getClass()!=ob2.getClass()) {
      System.err.println("My name is: "+getName());
      System.err.println("The other name is: "+((Property)p).getName()+" the type: "+getType()+" - "+((Property)p).getType());
      System.err.println("Compared "+ob1+" with "+ob2+" class: "+ob1.getClass()+" - "+ob2.getClass());
    }

    if (!Property.class.isInstance(p)) {
      return 0;
    }

    int i =  ob1.compareTo(ob2);
    return i;
  }


}
