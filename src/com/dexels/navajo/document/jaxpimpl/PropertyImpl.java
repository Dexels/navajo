

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
import java.util.Date;
import java.net.URL;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.ClockTime;

/**
 * The property class defines property object which are used for defining several
 * types of variables within a message (see @Message.class) object. Supp118orted types are: <BR>
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
public final class PropertyImpl implements Property, Comparable {

    public Element ref;
    private Navajo myRootDoc = null;

    public PropertyImpl(Element e) {
        this.ref = e;
    }

    private static final boolean validDirection(String dir) {
        if (dir.equals(DIR_IN) || dir.equals(DIR_OUT) || dir.equals(DIR_INOUT))
            return true;
        else
            return false;
    }

    private static final boolean validType(String type) {
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
    public static final Property create(Navajo tb, String name, String type, String value, int length,
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
        if (value != null) {
           if (type.equals(Property.STRING_PROPERTY) || !value.equals(""))
              p.setValue(value);
        }

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
    public static final Property create(Navajo tb, String name, String cardinality, String description, String direction) throws NavajoException {

        Property p = null;

        Document d = (Document) tb.getMessageBuffer();

        Element n = (Element) d.createElement(Property.PROPERTY_DEFINITION);

        p = new PropertyImpl(n);
        if (!(cardinality.equals("+") || cardinality.equals("1")))
            throw new NavajoExceptionImpl("Invalid cardinality specified: " + cardinality);
        p.setCardinality(cardinality);
        p.setName(name);
        p.setType(Property.SELECTION_PROPERTY);
        if (description != null && !description.equals(""))
            p.setDescription(description);
        p.setDirection(direction);

        return p;
    }

    public final Selection existsSelection(String name) throws NavajoException {

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
    public final Selection getSelection(String name) throws NavajoException {

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
    public final Selection getSelectionByValue(String value) throws NavajoException {

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
    public final ArrayList getAllSelections() throws NavajoException {
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

    public final String getFullPropertyName() {

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
    public final void clearSelections() throws NavajoException {
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
    public final ArrayList getAllSelectedSelections() throws NavajoException {
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
     * Add an expression to a property.
     */
    public final void addExpression(ExpressionTag e) throws NavajoException {
       ref.appendChild((Node) e.getRef());
    }

    /**
     * Add a selection option to a "selection" property. If the option name already exists, replace
     * it with the new one. If the property is not a "selection" type, a NavajoException is thrown.
     */
    public final void addSelection(Selection s) throws NavajoException {
        if (this.getType().equals(Property.SELECTION_PROPERTY)) {
            Selection dummy = this.existsSelection(s.getName());

            if (dummy != null)
                ref.removeChild((Node) dummy.getRef());
            ref.appendChild((Node) s.getRef());
        } else
            throw new NavajoExceptionImpl("addSelection(): Selection property required for this operation");
    }

    public final void addSelectionWithoutReplace(Selection s) throws NavajoException {
        if (this.getType().equals(Property.SELECTION_PROPERTY)) {
            ref.appendChild((Node) s.getRef());
        } else
            throw new NavajoExceptionImpl("addSelectionWithoutReplace(): Selection property required for this operation");
    }

    /**
     * Get the property name.
     */
    public final String getName() {
        return ref.getAttribute(Property.PROPERTY_NAME);
    }

    /**
     * Set the property name (required). A property name must be unique within a message.
     */
    public final void setName(String name) {
        ref.setAttribute(Property.PROPERTY_NAME, name);
    }

    /**
     * Get the type of a property (see PROPERTY_* constants).
     */
    public final String getType() {
        return ref.getAttribute(Property.PROPERTY_TYPE);
    }

    /**
     * Set the type of a property (required).
     */
    public final void setType(String type) {
        ref.setAttribute(Property.PROPERTY_TYPE, type);
    }

    public final Object getTypedValue() {

      if (!ref.hasAttribute(Property.PROPERTY_VALUE))
        return null;

      if (getType().equals(Property.BOOLEAN_PROPERTY)) {
        if (getValue() == null) {
          return new Boolean(false);
        }
        else {
          return new Boolean( ( (String) getValue()).equals("true"));
        }
      }
      else if (getType().equals(Property.STRING_PROPERTY)) {
        return (String) getValue();
      } else if (getType().equals(Property.MONEY_PROPERTY)) {
        return new Money(Double.parseDouble(getValue()));
      } else if (getType().equals(Property.CLOCKTIME_PROPERTY)) {
        try {
          return new ClockTime(getValue());
        } catch (Exception e) {
          e.printStackTrace(System.err);
        }
      }
      else if (getType().equals(Property.DATE_PROPERTY)) {

        if (getValue() == null || getValue().equals(""))
          return null;

        try {
          Date d = dateFormat1.parse(getValue().toString());
          return d;
        }
        catch (Exception ex) {
          try {
            Date d = dateFormat2.parse(getValue().toString());
            return d;
          }
          catch (Exception ex2) {
            System.err.println("Sorry I really can't parse that date..");
            ex2.printStackTrace();
          }
        }
      }
      else if (getType().equals(Property.INTEGER_PROPERTY)) {
        if (getValue().equals(""))
          return null;
        return new Integer(Integer.parseInt(getValue()));
      }
      else if (getType().equals(Property.FLOAT_PROPERTY)) {
        if (getValue().equals(""))
          return null;
        return new Double(Double.parseDouble(getValue()));
      }
      else if (getType().equals(Property.BINARY_PROPERTY)) {
        try {
          byte[] data;
          sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
          data = dec.decodeBuffer(getValue());
          return data;
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }

      return getValue();
 }

 public final void clearValue() {
    ref.removeAttribute(Property.PROPERTY_VALUE);
 }

 public final void setValue(byte[] data){
   try {
     if (data != null && data.length > 0) {
       sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
       setValue(enc.encode(data));
     }
   }
   catch (Exception e) {
     e.printStackTrace();
   }

 }

 public void setValue(URL url) {
   try{
      if(getType().equals(BINARY_PROPERTY)){
        InputStream in = url.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data;
        byte[] buffer = new byte[1024];
        int available;
        while ( (available = in.read(buffer)) > -1) {
          bos.write(buffer, 0, available);
        }
        bos.flush();
        data = bos.toByteArray();
        bos.close();
        in.close();
        setValue(data);
      }else{
        System.err.println("-------> setValue(URL) not supported for other property types than BINARY_PROPERTY");
      }
    }catch(Exception e){
      e.printStackTrace();
    }

 }

 public final void setValue(java.util.Date value) {
   if (value != null)
    setValue(dateFormat1.format(value));
 }

 public final void setValue(Boolean value) {
   if (value != null)
    setValue((value.booleanValue() ? "true" : "false"));
 }

 public final void setValue(Money value) {
    if (value != null)
      setValue(value.doubleValue()+"");
 }

 public final void setValue(ClockTime value) {
    if (value != null)
      setValue(value.toString());
 }

 public final void setValue(Double value) {
   if (value != null)
    setValue(value.doubleValue()+"");
 }

 public final void setValue(Integer value) {
   if (value != null)
    setValue(value.intValue()+"");
 }

 public final void setValue(int value) {
    setValue(value+"");
 }

 public final void setValue(double value) {
   setValue(value+"");
 }

 public final void setValue(float value) {
   setValue(value+"");
 }

 public final void setValue(boolean value) {
   setValue((value ? "true" : "false"));
 }

 public final void setValue(long value) {
   setValue(value+"");
 }

    /**
     * Get the value of a (string, integer, float, boolean, date or memo) property.
     */
    public final String getValue() {
      //if (getType().equals(Property.STRING_PROPERTY) || getType().equals(Property.MEMO_PROPERTY) || getType().equals(Property.PASSWORD_PROPERTY))
      //    return XMLutils.XMLUnescape(ref.getAttribute(Property.PROPERTY_VALUE));
      //else
      if (ref.hasAttribute(Property.PROPERTY_VALUE))
          return ref.getAttribute(Property.PROPERTY_VALUE);
      else
          return null;
    }

    public final String toString() {
      try{
        String s = ref.getAttribute(Property.PROPERTY_VALUE);
        if (this.getType() == Property.DATE_PROPERTY) {
          return dateFormat3.format( (Date)this.getTypedValue());
        }
        else if (this.getType() == Property.SELECTION_PROPERTY) {
          return this.getSelected().getValue();
        }
        else {
          return s;
        }
      }catch(NavajoException ne){
        ne.printStackTrace();
        return "?";
      }
    }

    /**
     * Set the value of a (string, integer, float, boolean, date or memo) property.
     */
    public final void setValue(String value) {
        // TODO: typechecking (Optionally!)
        //if (getType().equals(Property.STRING_PROPERTY) || getType().equals(Property.MEMO_PROPERTY) || getType().equals(Property.PASSWORD_PROPERTY))
        //  ref.setAttribute(Property.PROPERTY_VALUE, XMLutils.XMLEscape(value));
        //else
        if (value != null)
          ref.setAttribute(Property.PROPERTY_VALUE, value); // XMLutils.string2unicode(value));
    }

    /**
     * Sets the selected option for a selection type property.
     */
    public final void setSelected(String value) throws NavajoException {
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

    public void setSelected(Selection s) throws NavajoException {
      if (!this.getType().equals(Property.SELECTION_PROPERTY))
         throw new NavajoExceptionImpl("setSelected(): Selection property required for this operation");

     ArrayList list = this.getAllSelections();

     for (int i = 0; i < list.size(); i++) {
         Selection sel = (Selection) list.get(i);

         if (sel==s) {
             sel.setSelected(true);
         } else {
             if (this.getCardinality().equals("1")) // If cardinality 1, unset all other options.
                 sel.setSelected(false);
         }
     }

    }

    public final void setSelected(ArrayList keys) throws NavajoException {
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

    public final void setSelected(String[] keys) throws NavajoException {
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
    public final int getLength() {
        String length = ref.getAttribute(Property.PROPERTY_LENGTH);

        if (!length.equals(""))
            return Integer.parseInt(length);
        else
            return 0;
    }

    /**
     * Set the length attribute of a (string, integer or float) property.
     */
    public final void setLength(int length) {
        ref.setAttribute(Property.PROPERTY_LENGTH, length + "");
    }

    /**
     * Set the description attribute of a general property. This attribute has no functional
     * meaning, it is used merely for describing a property.
     */
    public final String getDescription() {
        return ref.getAttribute(Property.PROPERTY_DESCRIPTION);
    }

    /**
     * Get the description of a general property. This attribute is not required, hence
     * it could not be present.
     */
    public final void setDescription(String description) {
        ref.setAttribute(Property.PROPERTY_DESCRIPTION, description);
    }

    /**
     * Get the direction of a general property. The direction defines whether the property
     * is input (in), output (out) or both (inout).
     */
    public final String getDirection() {
        return ref.getAttribute(Property.PROPERTY_DIRECTION);
    }

    /**
     * Set the direction of a general property.
     */
    public final void setDirection(String dir) {
        ref.setAttribute(Property.PROPERTY_DIRECTION, dir);
    }

    /**
     * Get the cardinality of a "selection" property. The cardinality defines the maximum number
     * of options that can be selected from a "selection" property. Currently to values are
     * supported: "+", for multiple selections and "1", for single selections.
     */
    public final String getCardinality() {
        return ref.getAttribute(Property.PROPERTY_CARDINALITY);
    }

    /**
     * Set the cardinality of a "selection" property. If the property is not
     * of type "selection" the attribute is set anyway, however it is ignored.
     */
    public final void setCardinality(String c) {
        ref.setAttribute(Property.PROPERTY_CARDINALITY, c);
    }

    /**
     * Set the cartesian coordinates of a "points" property. Points is an array of Vectors.
     * Each vector contains the "point" (of any dimensionality).
     */
    public final void setPoints(Vector[] points) throws NavajoException {

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
    public final Vector[] getPoints() throws NavajoException {

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

    public final Message getParentMessage() {
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

    public final Object getRef() {
      return this.ref;
    }
  public final boolean isDirOut() {
    return DIR_OUT.equals(getDirection()) || DIR_INOUT.equals(getDirection());
  }
  public final boolean isDirIn() {
//    System.err.println("In dirin: "+DIR_IN+" / "+DIR_INOUT+" / "+getType());
    return DIR_IN.equals(getDirection()) || DIR_INOUT.equals(getDirection());
  }

  public final Navajo getRootDoc() {
    return myRootDoc;
  }

  public final void setRootDoc(Navajo n) {
    myRootDoc = n;
  }

/**
 * Added compareTo, to implement Comparable. (Copied it from the nanoimpl)
 * This interface is needed to be able to create sortable tables
 */

  public final int compareTo(Object p)  {
    if (p==null) {
      return 0;
    }

    // Scary bit.. added by Arnoud, not sure if thi s will work..
    if (getType().equals(Property.BOOLEAN_PROPERTY)) {
      Boolean bool1 = (Boolean) getTypedValue();
      boolean b1 = bool1.booleanValue();
      Boolean bool2 = (Boolean) ((Property)p).getTypedValue();
      boolean b2 = bool2.booleanValue();
      if (b1 == b2) {
        return 0;
      }
      else if (b1) { // Define false < true
        return 1;
      }
      else {
        return -1;
      }
    }
    // =================================================================


    Comparable ob1 = (Comparable)getValue();
    Comparable ob2 = (Comparable)((Property)p).getValue();
    if (ob1==null || ob2==null) {
      return 0;
    }

    ///if (ob1.getClass()!=ob2.getClass()) {
      //System.err.println("My name is: "+getName());
      //System.err.println("The other name is: "+((Property)p).getName()+" the type: "+getType()+" - "+((Property)p).getType());
      //System.err.println("Compared "+ob1+" with "+ob2+" class: "+ob1.getClass()+" - "+ob2.getClass());
//    }

    if (!Property.class.isInstance(p)) {
      return 0;
    }

    int i =  ob1.compareTo(ob2);
    return i;
  }

  public final Selection getSelected() throws NavajoException{
    ArrayList a = getAllSelectedSelections();
    if (a.size()==0) {
      return NavajoFactory.getInstance().createDummySelection();
    }
    if (a.size()>1) {
      throw new NavajoExceptionImpl("More than one selection selected. Change cardinality, or don't use getSelected()");
    }
    return (Selection)a.get(0);
  }

  public final boolean isEqual(Property p) {

     // If property names do not match, properties are not equal.
     if (!getName().equals(p.getName())) {
       return false;
     }

     // Check for date properties.
     if (p.getType().equals(Property.DATE_PROPERTY)) {

       // If both values are null they're equal.
       if (p.getTypedValue() == null && this.getTypedValue() == null) {
         return true;
       }

       // If only one of them is null they're not equal.
       if (p.getTypedValue() == null || this.getTypedValue() == null) {
         return false;
       }

       java.util.Date myDate = (java.util.Date) getTypedValue();
       java.util.Date otherDate = (java.util.Date) p.getTypedValue();
       if (dateFormat2.format(myDate).equals(dateFormat2.format(otherDate))) {
         return true;
       }
       else {
         return false;
       }
     }
     // Check for selection properties.
     else if (p.getType().equals(Property.SELECTION_PROPERTY)) {
       try {
         ArrayList l = p.getAllSelectedSelections();
         ArrayList me = this.getAllSelectedSelections();

         // If number of selected selections is not equal they're not equal.
         if (me.size() != l.size())
           return false;

         for (int j = 0; j < l.size(); j++) {
           Selection other = (Selection) l.get(j);
           boolean match = false;
           for (int k = 0; k < me.size(); k++) {
             Selection mysel = (Selection) me.get(k);
             if (mysel.getValue().equals(other.getValue())) {
               match = true;
               k = me.size() + 1;
             }
           }
           if (!match) {
             return false;
           }
         }
         return true;
       }
       catch (Exception e) {
         e.printStackTrace();
         return false;
       }
     }
     // Else I am some other property.
     else {

       // If both values are null they're equal.
       if (p.getTypedValue() == null && this.getTypedValue() == null) {
         return true;
       }

       // If only one of them is null they're not equal.
       if (p.getTypedValue() == null || this.getTypedValue() == null) {
         return false;
       }

       // We are only equal if our values match exactly.
       boolean result = p.getValue().equals(this.getValue());
       return result;
     }

   }


   public Object clone() {
     final Element cloned = (Element) this.ref.cloneNode(true);
     return ( new PropertyImpl(cloned) );
   }

   public Object clone( final String newName ) {
     final Element cloned = (Element) this.ref.cloneNode(true);
     final PropertyImpl newProp = new PropertyImpl(cloned);
     newProp.setName(newName);
     return ( newProp );
   }

}
