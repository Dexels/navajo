package com.dexels.navajo.document.nanoimpl;

import java.util.*;
import java.io.*;
import java.net.*;

import com.dexels.navajo.document.*;
import javax.swing.tree.TreeNode;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.*;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>c
 * @author Frank Lyaruu
 * @version 1.0
 */

public final class PropertyImpl
    extends BaseNode
    implements Property, Comparable, TreeNode {
  private String myName;
  private String myValue = null;
  private ArrayList selectionList = new ArrayList();
  private String type = null;
  private String cardinality = null;
  private String description = null;
  private String direction = Property.DIR_IN;
  private int length = -1;
  private Map subtypeMap = null;

  private Property definitionProperty = null;

//  private String myMessageName = null;
  private Message myParent = null;
  private Vector[] myPoints = null;

  private boolean isListType = false;

  public PropertyImpl(Navajo n, String name, String type, String value, int i,
                      String desc, String direction) {
    super(n);
    isListType = false;
    myName = name;
    myValue = value;
    this.type = type;
    this.length = i;
    this.description = desc;
    this.direction = direction;
    if (subType == null &&
        NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
      setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
    }

//    dateFormat.pa
  }

  public PropertyImpl(Navajo n, String name, String type, String value, int i,
                      String desc, String direction, String subType) {
    super(n);
    isListType = false;
    myName = name;
    myValue = value;
    this.type = type;
    this.length = i;
    this.description = desc;
    this.direction = direction;
    if (subType == null &&
        NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
      setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
    }else{
      setSubType(subType);
    }
  }


  public PropertyImpl(Navajo n, String name, String cardinality, String desc,
                      String direction) {
    super(n);
    isListType = true;
    myName = name;
    //myValue = "list";
    this.cardinality = cardinality;
    this.description = desc;
    this.direction = direction;
    this.type = "selection";
    if (subType == null &&
        NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
      setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
    }

  }

  private String subType = null;

  public void setSubType(String subType) {
    this.subType = subType;
    subtypeMap = NavajoFactory.getInstance().parseSubTypes(subType);
  }

  public String getSubType() {
    return subType;
  }

  public PropertyImpl(Navajo n, String name) {
    super(n);
    myName = name;

  }

  public final String getName() {
    return myName;
  }

  public final int getLength() {
    return length;
  }

  public final void setLength(int i) {
    length = i;
  }

  public final String getDescription() {
    return description;
  }

  public final void setDescription(String s) {
    description = s;
  }

  public final String getCardinality() {
    return cardinality;
  }

  public final void setCardinality(String c) {
    cardinality = c;
  }

  public final String getDirection() {
    return direction;
  }

  public final void setDirection(String s) {
    direction = s;
  }

  // This is NOT the FULL name!!
  public final String getFullPropertyName() {
    if (getParent() != null) {
      return getParentMessage().getFullMessageName() + "/" + getName();
    }
    else {
      return getName();
    }
  }

  public final String getValue() {
    return myValue;
  }

  public Object peekEvaluatedValue() {
    return evaluatedValue;
  }
  
  public void setAnyValue(Object o) {
      if (o==null) {
        setValue((String)null);
        return;
    }
      if (o instanceof Integer) {
        setValue((Integer)o);
        return;
    }
      if (o instanceof Double) {
          setValue((Double)o);
          return;
      }
      if (o instanceof Binary) {
          setValue((Binary)o);
          return;
      }
      if (o instanceof ClockTime) {
          setValue((ClockTime)o);
          return;
      }
      if (o instanceof Date) {
          setValue((Date)o);
          return;
      }
      if (o instanceof Long) {
          setValue(((Long)o).longValue());
          return;
      }
      if (o instanceof Money) {
          setValue((Money)o);
          return;
      }
      if (o instanceof Percentage) {
          setValue((Percentage)o);
          return;
      }
      if (o instanceof URL) {
          setValue((URL)o);
          return;
      }
      setValue(""+o);
  }
  
  public Object getEvaluatedValue() throws NavajoException {
//    System.err.println("Evaluating property: "+getValue());
    Operand o;
    try {
      try {
        if (!EXPRESSION_PROPERTY.equals(getType())) {
          throw NavajoFactory.getInstance().createNavajoException(
              "Can only evaluate expression type properties!");
        }
        o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(
            getValue(), getRootDoc(), null, getParentMessage());

        evaluatedType = o.type;
        return o.value;
      }
      catch (NavajoException ex) {
//      System.err.println("value problem: "+ex.getMessage());

// The expression could not be evaluated. This happens sometimes, but
// some ui components still want to know the type. This elaborate construction
// will try to retrieve the type from a definition message in an array message.
// This, of course, only works for array message with a definition message present.

        if (myParent != null) {
          Message pp = myParent.getParentMessage();
          if (pp != null && Message.MSG_TYPE_ARRAY.equals(pp.getType())) {
            Message def = pp.getDefinitionMessage();
            if (def != null) {
              Property ppp = def.getProperty(getName());
              if (ppp != null) {
                evaluatedType = ppp.getType();
                return null;
              }
            }
          }
        }
        evaluatedType = "string";
        return null;
      }
    }
    catch (Throwable ex1) {
//      System.err.println("trouble");
      evaluatedType = "string";
      return null;
    }
  }

  public String getEvaluatedType() throws NavajoException {
    if (evaluatedType == null) {
      refreshExpression();
    }
//    System.err.println("PropertyImpl. PatH: "+getFullPropertyName()+" TYPE: "+evaluatedType);
    return evaluatedType;
//    if (!EXPRESSION_PROPERTY.equals(getType())) {
//      throw NavajoFactory.getInstance().createNavajoException(
//          "Can only evaluate expression type properties!");
//    }
//    Operand o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(
//        getValue(), getRootDoc(), null, getParentMessage());
//    return o.type;
  }

  private Object evaluatedValue = null;
  private String evaluatedType = null;

  public void refreshExpression() throws NavajoException {
    if (getType().equals(Property.EXPRESSION_PROPERTY)) {
      // also sets evaluatedType
//      System.err.println("Entering eval..");
      try {
        evaluatedValue = getEvaluatedValue();
      }
//      catch (Error ex) {
//        System.err.println("Exception occured while refreshing. Not a problem");
//      }
//      catch (Exception ex) {
//        System.err.println("Exception occured while refreshing. Not a problem");
//      }
      catch (NullPointerException ex) {
        System.err.println("Exception occured while refreshing. Not a problem");
      }

    }
  }

  /**
   * Get the value of a property as a Java object.
   *
   * @return
   */
  public final Object getTypedValue() {

//    if (myValue == null && !SELECTION_PROPERTY.equals(getType())) {
//      return null;
//    }
//    System.err.println("MYVALUE: "+myValue);
    if (getType().equals(EXPRESSION_PROPERTY)) {
      try {
        if (evaluatedValue == null) {
          evaluatedValue = getEvaluatedValue();
          return evaluatedValue;
        }
        else {
          return evaluatedValue;
        }
      }
      catch (NavajoException ex1) {
        ex1.printStackTrace();
        return null;
      }
    }

    if (getType().equals(Property.BOOLEAN_PROPERTY)) {
      if (getValue() != null) {
        return new Boolean( ( (String) getValue()).equals("true"));
      }
      else {
        return null;
      }
    }
    else if (getType().equals(Property.STRING_PROPERTY)) {
      return getValue();
    }
    else if (getType().equals(Property.MONEY_PROPERTY)) {
      if (getValue() == null || "".equals(getValue())) {
        return new Money( (Double)null, getSubType());
      }
      return new Money(Double.parseDouble(getValue()), getSubType());
    }
    else if (getType().equals(Property.CLOCKTIME_PROPERTY)) {
      try {
        return new ClockTime(getValue(), getSubType());
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
      }
    }
    else if (getType().equals(Property.DATE_PROPERTY)) {
      if (getValue() == null || getValue().equals("")) {
        return null;
      }

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
      if (getValue() == null || getValue().equals("")) {
        return null;
      }
      try {
        // Added a trim. Frank.
        return new Integer(Integer.parseInt(getValue().trim()));
      }
      catch (NumberFormatException ex3) {
        System.err.println("Numberformat exception...");
        return null;
      }
    }
    else if (getType().equals(Property.FLOAT_PROPERTY)) {
      if (getValue() == null || getValue().equals("")) {
        return null;
      }
      String v = getValue();
      String w = v;
      // Sometimes the numberformatting creates
      if (v.indexOf(",") != -1) {
        w = v.replaceAll(",", "");
      }
      Double d;
      try {
        d = new Double(Double.parseDouble(w));
      }
      catch (NumberFormatException ex) {
        System.err.println("Can not format double with: " + w);
        return null;
      }
      return d;
    }
    else if (getType().equals(Property.BINARY_PROPERTY)) {
      try {
        byte[] data;
        if (getValue() == null) {
          return null;
        }
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        data = dec.decodeBuffer(getValue());
        return new Binary(data);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else if (getType().equals(Property.SELECTION_PROPERTY)) {

      Selection s = getSelected();
      if (s != null) {
        return s.getValue();
      }
      else {
        return null;
      }

    }

    return getValue();
  }

  public final void clearValue() {
    myValue = null;
  }

  public final void setValue(InputStream is) {

  }

  public final void setValue(Binary b) {
    try {
      byte[] data = b.getData();
      if (data != null && data.length > 0) {
        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        myValue = enc.encode(data);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setValue(URL url) {
    System.err.println("Setting value with URL");
    try {
      if (type.equals(BINARY_PROPERTY)) {
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
        setValue(new Binary(data));
      }
      else {
        System.err.println("-------> setValue(URL) not supported for other property types than BINARY_PROPERTY");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void setValue(java.util.Date value) {
    if (value != null) {
      setValue(dateFormat1.format(value));
    }
    else {
      myValue = null;
    }
  }

  public final void setValue(Boolean value) {
    if (value != null) {
      setValue( (value.booleanValue() ? "true" : "false"));
    }
    else {
      myValue = null;
    }
  }

  public final void setValue(Money value) {
    if (value != null) {
      setValue(value.toString());
    }
    else {
      myValue = null;
    }
  }

  public final void setValue(Percentage value) {
    if (value != null) {
      setValue(value.toString());
    }
    else {
      myValue = null;
    }
  }

  public final void setValue(ClockTime value) {
    if (value != null) {
      setValue(value.toString());
    }
    else {
      myValue = null;
    }
  }

  public final void setValue(Double value) {
    if (value != null) {
      setValue(value.doubleValue() + "");
    }
    else {
      myValue = null;
    }
  }

  public final void setValue(Integer value) {
    if (value != null) {
      setValue(value.intValue() + "");
    }
    else {
      myValue = null;
    }
  }

  public final void setValue(int value) {
    setValue(value + "");
  }

  public final void setValue(double value) {
    setValue(value + "");
  }

  public final void setValue(float value) {
    String floatString = "" + value;
    //System.err.println("FLOATSTRING: " + floatString);
    setValue(floatString);
  }

  public void setValue(boolean value) {
    setValue( (value ? "true" : "false"));
  }

  public final void setValue(long value) {
    setValue(value + "");
  }

  public final void setValue(String value) {
    setCheckedValue(value);
  }

  public final String getSubType(String key) {
//    return PropertyTypeChecker.getInstance().getSubType(getType(), getSubType(), key);
    if (subtypeMap != null) {
      return (String) subtypeMap.get(key);
    }
    return null;

  }

  private String serializeSubtypes() {
    if (subtypeMap == null) {
      return null;
    }
    System.err.println("Serializing subtypes: ");
    StringBuffer sb = new StringBuffer();
    if (definitionProperty == null) {
      for (Iterator iter = subtypeMap.keySet().iterator(); iter.hasNext(); ) {
        String item = (String) iter.next();
        String value = (String) subtypeMap.get(item);
        sb.append(item + "=" + value + (iter.hasNext() ? "," : ""));
      }
      System.err.println("Subtypes: " + sb.toString());
      return sb.toString();
    }
    else {
      for (Iterator iter = subtypeMap.keySet().iterator(); iter.hasNext(); ) {
        String item = (String) iter.next();
        String value = (String) subtypeMap.get(item);
        String defvalue = definitionProperty.getSubType(item);
        if (value == null) {
          sb.append(item + "=" + (iter.hasNext() ? "," : ""));
        }
        else {
          if (!value.equals(defvalue)) {
            sb.append(item + "=" + value + (iter.hasNext() ? "," : ""));
          }
        }
      }
      System.err.println("Subtypes: " + sb.toString());
      return sb.toString();
    }

  }

  public final String setCheckedValue(String v) {

//    if (EXPRESSION_PROPERTY.equals(getType())&& "Description".equals(getName())) {
//      System.err.println("SETTING VALUE: "+value);
//      Thread.dumpStack();
//    }
    String value = null;
    try {
      value = PropertyTypeChecker.getInstance().verify(this, v);
    }
    catch (PropertyTypeException ex1) {
      value = null;
    }

    if (value != null) {
      try {
        if (getType().equals(SELECTION_PROPERTY)) {
          //        System.err.println("Setting value of selection property");
          setSelectedByValue(value);
        }
        else {
          myValue = value;
        }
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
    }
    else {
      myValue = null;
    }
    return value;
  }

  public final void setName(String name) {
    myName = name;
  }

  public final String getType() {
    return (this.type);
  }

  public final void setType(String t) {
    type = t;
  }

  public final String toString() {
    if (getType().equals(Property.DATE_PROPERTY)) {
      return ( this.getTypedValue() != null ) ? dateFormat3.format( (Date)this.getTypedValue() ) : null;
    }
    else if (getType().equals(Property.SELECTION_PROPERTY)) {
      return this.getSelected().getName();
    }
    else if (getType().equals(Property.BOOLEAN_PROPERTY)) {
      if (Locale.getDefault().getLanguage().equals("nl")) {
        return (getValue().equals("true") ? "ja" : "nee");
      } else {
        return (getValue().equals("true") ? "yes" : "no");
      }
    }
    else {
      return getValue();
    }
  }

  public final ArrayList getAllSelections() {
    ArrayList l = new ArrayList(selectionList);
    return l;
  }

  public final void setSelected(Selection s) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection) selectionList.get(i);
      if (current == s) {
        current.setSelected(true);
      }
      else {
        current.setSelected(false);
      }
    }
  }

  public final void setAllSelected(boolean b) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection) selectionList.get(i);
      current.setSelected(b);
    }
  }

  public final void setSelectedByValue(Object value) throws NavajoException {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection) selectionList.get(i);
      if (current.getValue() == null) {
        continue;
      }
      if (current.getValue().equals(value)) {
        if (!getCardinality().equals("+")) {
          clearSelections();
        }

        current.setSelected(true);
      }
      else {
//        current.setSelected(false);
      }
    }
  }

  public final XMLElement toXml(XMLElement parent) {
  	return toXml(parent, false, null);
  }
  
  public final XMLElement toXml(XMLElement parent, boolean condense, String method) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("property");
    x.setAttribute("name", myName);
    
    // Check if cloned (if parent = null assume clone), if so copy length, description and cardinality.
    if (parent == null) {
    	x.setAttribute("length", length+"");
    	x.setAttribute("description", description);
    	x.setAttribute("cardinality", cardinality);
    }
    
    if (myValue != null) {
//      if (Date.class.isInstance(myValue)) {
//        x.setAttribute("value",dateFormat1.format((Date)myValue));
//      } else {
//      }
    }
    if (subType != null) {
      x.setAttribute(PROPERTY_SUBTYPE, subType);
    }

    /** @todo Refine this a bit. Should be checked for every attribute, with precedence to this one */
//     subType = serializeSubtypes();
//     if (subType != null) {
//       System.err.println("Subtype found!");
//       x.setAttribute("subtype", subType);
//     }

    try {
// Always set type:        
        x.setAttribute("type", type);
        if (myValue != null) {
            x.setAttribute("value", (String) myValue);
          }
       
    if (definitionProperty == null || definitionProperty.getAllSelections().size() == 0 ) {
 //      System.err.println("Serializing property. No definition");

      if (direction != null) {
        x.setAttribute("direction", direction);
      }
      else {
        x.setAttribute("direction", "in");
      }

      if (description != null && !condense) {
        x.setAttribute("description", description);
      }

      if (length != -1 && !condense) {
        x.setAttribute("length", length + "");

      }
 
      if (cardinality != null) {
        x.setAttribute("cardinality", cardinality);
      }

      for (int i = 0; i < selectionList.size(); i++) {
        SelectionImpl s = (SelectionImpl) selectionList.get(i);
        if (!condense || s.isSelected()) {
        	x.addChild(s.toXml(x));
        }
      }

    }
    else {

      /** @todo Watch out here. Just because there is a definition message,
       * it seems to assume that a definitionProperty is present. This is
       * very likely, but it results in a null pointer exception when it is
       * not. I am not sure if a silent ignore is wise, maybe just throw a
       * clearer runtime exception. */

//      System.err.println("Serializing property. With definition");
      if (myValue != null) {
        if (definitionProperty.getValue() != null &&
            !definitionProperty.getValue().equals(getValue())) {
          x.setAttribute("value", (String) myValue);

        }
        if (definitionProperty.getValue() == null) {
          x.setAttribute("value", (String) myValue);
        }

      }

        ArrayList al = getAllSelectedSelections();
        //System.err.println("# of selected selections: " + al.size());
        for (int i = 0; i < al.size(); i++) {

          SelectionImpl s = (SelectionImpl) al.get(i);
          //System.err.println("SELECTION: "+s.toXml(x).toString());
          s.setSelected(true);

          /** @todo Beware: Isn't this very strange?  */
          x.addChild(s.toXml(x));

        }
      }
    }
    catch (NavajoException ex) {
       ex.printStackTrace();
     }

    //System.err.println("Result: " + x.toString());
    return x;
  }

  public final void fromXml(XMLElement e) {
    fromXml(e, null);
  }

  public final void fromXml(XMLElement e, MessageImpl parentArrayMessage) {
    super.fromXml(e);
    String sLength = null;
    myName = (String) e.getAttribute(Property.PROPERTY_NAME);
    myValue = (String) e.getAttribute(Property.PROPERTY_VALUE);
    subType = (String) e.getAttribute(PROPERTY_SUBTYPE);
    description = (String) e.getAttribute(Property.PROPERTY_DESCRIPTION);
    direction = (String) e.getAttribute(Property.PROPERTY_DIRECTION);
    type = (String) e.getAttribute(Property.PROPERTY_TYPE);
    sLength = (String) e.getAttribute(Property.PROPERTY_LENGTH);
    Integer plength = null;
    try {
      if (sLength != null) {
        length = Integer.parseInt(sLength);
        plength = new Integer(length);
      }
    }
    catch (Exception e1) {
      System.err.println("ILLEGAL LENGTH IN PROPERTY " + myName + ": " +
                         sLength);
    }

    definitionProperty = null;

    if (parentArrayMessage != null) {

      definitionProperty = parentArrayMessage.getPropertyDefinition(myName);

      if (definitionProperty != null) {
        if (description == null || "".equals(description)) {
          description = definitionProperty.getDescription();
        }
        if (direction == null || "".equals(direction)) {
          direction = definitionProperty.getDirection();
        }
        if (type == null || "".equals(type)) {
          type = definitionProperty.getType();
        }
        if (plength == null) {
          length = definitionProperty.getLength();
        }
        if (subType == null) {
          if (definitionProperty.getSubType() != null) {
            setSubType(definitionProperty.getSubType());
          }
          else {
            subType = null;
          }
        }
        else {
          if (definitionProperty.getSubType() != null) {
            /**
                 * Concatenated subtypes. The if the same key of a subtype is present
             * in both the property and the definition property.
             */
            setSubType(definitionProperty.getSubType() + "," + subType);
          }
        }

        if (myValue == null || "".equals(myValue)) {
          myValue = definitionProperty.getValue();
        }
      }
    }

    if (subType == null &&
        NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
      setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
    }
    else {
      setSubType(subType);
    }

    if (type == null && parentArrayMessage != null) {
      System.err.println("Found undefined property: " + getName());
    }

    isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));
    if (isListType) {
      cardinality = (String) e.getAttribute(Property.PROPERTY_CARDINALITY);
      if (cardinality == null) {
        cardinality = definitionProperty.getCardinality();
      }
      type = Property.SELECTION_PROPERTY;
      try {
        if (definitionProperty == null || definitionProperty.getAllSelections().size() == 0) {
          cardinality = (String) e.getAttribute("cardinality");
          for (int i = 0; i < e.countChildren(); i++) {
            XMLElement child = (XMLElement) e.getChildren().elementAt(i);
            SelectionImpl s = (SelectionImpl) NavajoFactory.getInstance().createSelection(myDocRoot, "", "", false);
            s.fromXml(child);
            s.setParent(this);
            this.addSelection(s);
          }
        }
        else { // There is a definition property with defined selections(!)
          ArrayList l = definitionProperty.getAllSelections();
          for (int i = 0; i < l.size(); i++) {
            SelectionImpl s = (SelectionImpl) l.get(i);
            SelectionImpl s2 = (SelectionImpl) s.copy(getRootDoc());
            addSelection(s2);
          }
          for (int j = 0; j < e.countChildren(); j++) {
            XMLElement child = (XMLElement) e.getChildren().elementAt(j);
            String val = (String) child.getAttribute("value");
            //System.err.println(">>>>>>>>>>>>>>>>>>>>>> Attempting to select value: " + val);
            if (val != null) {
              setSelectedByValue(val);
            }
          }

        }

      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }

    }
    if (type == null) {
      type = Property.STRING_PROPERTY;
    }
    setValue(PropertyTypeChecker.getInstance().verify(this, myValue));
  }

//  public final void fromXml(XMLElement e, MessageImpl parentArrayMessage) {
//    super.fromXml(e);
//    String sLength = null;
//    myName = (String) e.getAttribute("name");
//    myValue = (String) e.getAttribute("value");
//    subType = (String)e.getAttribute(PROPERTY_SUBTYPE);
//    definitionProperty = null;
//
//
//    if (parentArrayMessage != null) {
//      definitionPresent = true;
//      definitionProperty = parentArrayMessage.getPropertyDefinition(myName);
//
//      if (definitionProperty != null) {
//        description = definitionProperty.getDescription();
//        direction = definitionProperty.getDirection();
//        type = definitionProperty.getType();
//        length = definitionProperty.getLength();
//        if (myValue == null) {
//          myValue = definitionProperty.getValue();
//        }
//      }
//      else {
//        definitionPresent = false;
//        description = (String) e.getAttribute("description");
//        direction = (String) e.getAttribute("direction");
//        type = (String) e.getAttribute("type");
//        sLength = (String) e.getAttribute("length");
//        if (BINARY_PROPERTY.equals(type)) {
//          System.err.println("Found property of binary type! size: "+(myValue!=null?myValue.length():-1));
//        }
//        try {
//          if (sLength != null) {
//            length = Integer.parseInt(sLength);
//          }
//        }
//        catch (Exception e1) {
//          //System.err.println("ILLEGAL LENGTH IN PROPERTY " + myName + ": " + sLength);
//        }
//      }
//    }
//    else {
//      description = (String) e.getAttribute("description");
//      direction = (String) e.getAttribute("direction");
//      type = (String) e.getAttribute("type");
//      sLength = (String) e.getAttribute("length");
//      try {
//        if (sLength != null) {
//          length = Integer.parseInt(sLength);
//        }
//      }
//      catch (Exception e1) {
//        //System.err.println("ILLEGAL LENGTH IN PROPERTY " + myName + ": " + sLength);
//      }
//    }
//
//    if (type == null && parentArrayMessage != null) {
//      System.err.println("Found undefined property: " + getName());
//    }
//
//    isListType = (type != null && type.equals("selection"));
//    if (isListType) {
//      type = "selection";
//      if (parentArrayMessage == null) {
//        cardinality = (String) e.getAttribute("cardinality");
//        for (int i = 0; i < e.countChildren(); i++) {
//          XMLElement child = (XMLElement) e.getChildren().elementAt(i);
//          SelectionImpl s = (SelectionImpl) NavajoFactory.getInstance().
//              createSelection(myDocRoot, "", "", false);
//          s.fromXml(child);
//          s.setParent(this);
//          this.addSelection(s);
//        }
//      }
//      else {
//        try {
//          ArrayList l = definitionProperty.getAllSelections();
//          for (int i = 0; i < l.size(); i++) {
//            SelectionImpl s = (SelectionImpl) l.get(i);
//            SelectionImpl s2 = (SelectionImpl) s.copy(getRootDoc());
//            addSelection(s2);
//          }
//          for (int j = 0; j < e.countChildren(); j++) {
//            XMLElement child = (XMLElement) e.getChildren().elementAt(j);
//            String val = (String) child.getAttribute("value");
//            //System.err.println("Attempting to select value: " + val);
//            if (val != null) {
//              setSelectedByValue(val);
//            }
//          }
//
//        }
//        catch (NavajoException ex) {
//          ex.printStackTrace();
//        }
//      }
//    }
//    if (type == null) {
//      type = Property.STRING_PROPERTY;
//    }
//  }

  public final boolean isEditable() {
    return (Property.DIR_IN.equals(direction) ||
            Property.DIR_INOUT.equals(direction));
  }

  public final void addSelection(Selection s) {
    int max = selectionList.size();
    for (int i = 0; i < max; i++) {
      Selection t = (Selection) selectionList.get(i);
      if (t.getName().equals(s.getName())) {
        //System.err.println("REMOVING SELECTION!");
        selectionList.remove(i);
        max--;
      }

    }

    selectionList.add(s);
//    s.setParent(this);
  }

  public final void removeSelection(Selection s) {
    selectionList.remove(s);
  }

  public final void removeAllSelections() throws NavajoException {
    selectionList = new ArrayList();
  }

  public final Selection getSelection(String name) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection) selectionList.get(i);
      if (current.getName().equals(name)) {
        return current;
      }
    }
    return NavajoFactory.getInstance().createDummySelection();
  }

  public final Selection getSelectionByValue(String value) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection) selectionList.get(i);
      if (current != null && current.getValue().equals(value)) {
        return current;
      }
    }
    return NavajoFactory.getInstance().createDummySelection();
//    return null;
  }

  public final Selection getSelected() {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection) selectionList.get(i);
//      System.err.println("CHECKING:::: "+current);
      if (current != null && current.isSelected()) {
        return current;
      }
    }
//    return null;
    return NavajoFactory.getInstance().createDummySelection();
  }

  public final Property copy(Navajo n) {
    PropertyImpl cp;
    try {
      if (isListType) {

        cp = (PropertyImpl) NavajoFactory.getInstance().createProperty(n,
            getName(), getCardinality(), getDescription(), getDirection());
      }
      else {

        cp = (PropertyImpl) NavajoFactory.getInstance().createProperty(n,
            getName(), getType(), (String) getValue(), getLength(),
            getDescription(), getDirection(), getSubType());
      }
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex.toString());
    }
    cp.setRootDoc(n);
    ArrayList mySel = getAllSelections();
    for (int i = 0; i < mySel.size(); i++) {
      SelectionImpl current = (SelectionImpl) mySel.get(i);
      SelectionImpl cc = (SelectionImpl) current.copy( (NavajoImpl) n);
      cp.addSelection(cc);
    }
    return cp;
  }

  public final void prune() {
    ArrayList mySel = getAllSelections();
    for (int i = 0; i < mySel.size(); i++) {
      Selection current = (Selection) mySel.get(i);
      if (!current.isSelected()) {
        removeSelection(current);
      }
    }
  }

  public final void setParent(Message m) {
    myParent = m;
  }

  public final void setParentMessage(Message m) {
    setParent(m);
  }

  public final Message getParentMessage() {
    return myParent;
  }

  public final String getPath() {
    if (myParent != null) {
      return myParent.getFullMessageName() + "/" + getName();
    }
    else {
      return "/" + getName();
    }
  }

  // Filthy hack. I there are some probs with typed value, in respect to displaying a date.
  // I did not dare to change propertyimpl.
  // Maybe later, when I feel brave... Frank
  private final Object getAlternativeTypedValue() {
    if (getType().equals("boolean")) {
      if (getValue() == null) {
        return "false";
      }
      else {
        return ( (String) getValue()).equals("true") ? "true" : "false";
      }
    }
    if (getType().equals("string")) {
      return (String) getValue();
    }
    if (getType().equals("date")) {
      try {
        if (Date.class.isInstance(getValue())) {
          return getValue();
        }
        else {
          try {
            Date d = dateFormat1.parse( (String) getValue());
            return d;
          }
          catch (Exception ex0) {
            Date d = dateFormat2.parse( (String) getValue());
            return d;
          }
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return getValue();
  }

//  public int compare(Property p) {
//    Comparable ob1 = (Comparable)getAlternativeTypedValue();
//    Comparable ob2 = (Comparable)p.getAlternativeTypedValue();
//    return ob1.compareTo(ob2);
//  }
  public final int compareTo(Object p) {
    if (p == null) {
      return 0;
    }
    Comparable ob1;
    Comparable ob2;

    if (getType().equals(Property.BOOLEAN_PROPERTY)) {
      Boolean bool1 = (Boolean) getTypedValue();
      boolean b1 = bool1.booleanValue();
      Boolean bool2 = (Boolean) ( (Property) p).getTypedValue();
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

    // Get first argument.
    if (getType().equals(Property.SELECTION_PROPERTY)) {
      if (getSubType("name") != null && getSubType("name").equals("integer")) {
        ob1 = (Comparable) new Integer(getSelected().getName());
      } else {
        ob1 = (Comparable) getSelected().getName();
      }
    }
    else {
      ob1 = (Comparable) getTypedValue();
    }

    // Get second argument.
    if ( ( (PropertyImpl) p).getType().equals(Property.SELECTION_PROPERTY)) {
      PropertyImpl cp = (PropertyImpl) p;
      if (getSubType("name") != null && cp.getSubType("name").equals("integer")) {
        ob2 = (Comparable) new Integer(cp.getSelected().getName());
      } else {
        ob2 = (Comparable) ( (PropertyImpl) p).getSelected().getName();
      }
    }
    else {
      ob2 = (Comparable) ( (PropertyImpl) p).getTypedValue();
    }

//    Comparable ob1 = (Comparable)getAlternativeTypedValue();
//    Comparable ob2 = (Comparable)((PropertyImpl)p).getAlternativeTypedValue();

//    System.err.println("Comparing: " + ob1 + ", " + ob2);


    // now null values will be compared too.
    if (ob1 == null && ob2 == null) {
      return 0;
    }

    if(ob1 == null && ob2 != null){
      return -1;
    }

    if(ob2 == null && ob1 != null){
      return 1;
    }



    if (ob1.getClass() != ob2.getClass()) {
      //System.err.println("My name is: "+getName());
      //System.err.println("The other name is: "+((Property)p).getName()+" the type: "+getType()+" - "+((Property)p).getType());
      //System.err.println("Compared "+ob1+" with "+ob2+" class: "+ob1.getClass()+" - "+ob2.getClass());
    }

    if (!Property.class.isInstance(p)) {
      return 0;
    }

    try {
      int i = ob1.compareTo(ob2);
      return i;
    }
    catch (Throwable t) {
      return 0;
    }

  }

  public final Object getRef() {
    return toXml(null);
  }

  public final void setPoints(Vector[] points) throws NavajoException {
    myPoints = points;
  }

  public final Vector[] getPoints() throws NavajoException {
    return myPoints;
  }

  public void setSelected(String[] s) throws com.dexels.navajo.document.
      NavajoException {
    if (!getType().equals(SELECTION_PROPERTY)) {
      throw NavajoFactory.getInstance().createNavajoException(
          "Setting selected of non-selection property!");
    }

    setAllSelected(false);
    for (int i = 0; i < s.length; i++) {
      Selection st = getSelectionByValue(s[i]);
      st.setSelected(true);
    }

  }

  public final void addSelectionWithoutReplace(Selection s) throws com.dexels.
      navajo.document.NavajoException {
    selectionList.add(s);
  }

  public final void setSelected(ArrayList al) throws com.dexels.navajo.document.
      NavajoException {
    setAllSelected(false);
    for (int i = 0; i < al.size(); i++) {
      String s = (String) al.get(i);
      Selection sl = getSelectionByValue(s);
      sl.setSelected(true);
    }

  }

  public final ArrayList getAllSelectedSelections() throws com.dexels.navajo.
      document.NavajoException {
    ArrayList list = new ArrayList();
    ArrayList al = getAllSelections();
    for (int i = 0; i < al.size(); i++) {
      SelectionImpl s = (SelectionImpl) al.get(i);
      //System.err.println("Allselected. Looking at: "+s.toXml(null).toString());
      if (s.isSelected()) {
        //System.err.println("adding");
        list.add(s);
      }
    }
    return list;

  }

  public final void setSelected(String value) throws com.dexels.navajo.document.
      NavajoException {
    // System.err.println("============================\nSetting selection: "+value);
//    Selection s = getSelection(value);
    if (!"+".equals(getCardinality())) {
      clearSelections();
    }

    Selection s = getSelectionByValue(value);
    s.setSelected(true);
  }

  public final void clearSelections() throws com.dexels.navajo.document.
      NavajoException {
    ArrayList al = getAllSelections();
    for (int i = 0; i < al.size(); i++) {
      SelectionImpl s = (SelectionImpl) al.get(i);
      s.setSelected(false);
    }

  }

  public final Selection existsSelection(String name) throws com.dexels.navajo.
      document.NavajoException {
    return getSelection(name);
  }

//  public String getFullPropertyName()  throws NavajoException {
//    return getPath();
//  }
  public final boolean isDirIn() {
    if (getDirection() == null) {
      return false;
    }
    return getDirection().equals(DIR_IN) || getDirection().equals(DIR_INOUT);
  }

  public final boolean isDirOut() {
    if (getDirection() == null) {
      return false;
    }
    return getDirection().equals(DIR_OUT) || getDirection().equals(DIR_INOUT);
  }

  public static void main(String[] args) {
    PropertyImpl p = new PropertyImpl(null, "");
    Runtime rt = Runtime.getRuntime();
    long start = rt.totalMemory() - rt.freeMemory();
    System.out.println("Total memory: " + rt.totalMemory());
    System.out.println("Free memory: " + rt.freeMemory());
    ArrayList a = new ArrayList();
    for (int i = 0; i < 100000; i++) {
      a.add(new PropertyImpl(null, ""));
    }
    long end = rt.totalMemory() - rt.freeMemory();
    System.out.println("Free memory: " + rt.freeMemory());
    System.out.println("Memory usage: " + (end - start));
  }

  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  public int getChildCount() {
    return 0;
  }

  public TreeNode getParent() {
    return (TreeNode) getParentMessage();
  }

  public int getIndex(TreeNode node) {
    return 0;
  }

  public boolean getAllowsChildren() {
    return false;
  }

  public boolean isLeaf() {
    return true;
  }

  public Enumeration children() {
    return null;
  }

  public final boolean isEqual(Property p) {

    //System.err.println("Checking isEqual(me = " + this.getName() + " AND other = " + p.getName() + ")" );
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
        if (me.size() != l.size()) {
          return false;
        }

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
    PropertyImpl pi = new PropertyImpl(myDocRoot, getName());
    pi.fromXml(toXml(null));
    return pi;
  }

  public Object clone(final String newName) {
    throw new java.lang.UnsupportedOperationException(
        "Method clone( String ) not yet implemented.");
  }

  public void addExpression(ExpressionTag e) {
    throw new java.lang.UnsupportedOperationException(
        "Method addExpression() not yet implemented.");
  }

}
