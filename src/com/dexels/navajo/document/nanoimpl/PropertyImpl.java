package com.dexels.navajo.document.nanoimpl;

import java.util.*;
import java.text.*;

import com.dexels.navajo.document.*;
import javax.swing.tree.TreeNode;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public final class PropertyImpl extends BaseNode implements Property, Comparable, TreeNode {
  private String myName;
  private String myValue = null;
  private ArrayList selectionList = new ArrayList();
  private String type = null;
  private String cardinality = null;
  private String description = null;
  private String direction;
  private int length = -1;
//  private String myMessageName = null;
  private Message myParent = null;
  private Vector[] myPoints = null;

  private boolean isListType = false;

  public PropertyImpl(Navajo n,String name, String type, String value, int i, String desc, String direction) {
    super(n);
    isListType = false;
    myName = name;
    myValue = value;
    this.type = type;
    this.length = i;
    this.description = desc;
    this.direction = direction;
//    dateFormat.pa
  }

  public PropertyImpl(Navajo n, String name, String cardinality, String desc, String direction) {
    super(n);
    isListType = true;
    myName = name;
    myValue = "list";
    this.cardinality = cardinality;
    this.description = desc;
    this.direction = direction;
    this.type = "selection";
  }
//
//  public void setMessageName(String m){
//    myMessageName  = m;
//  }

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
   if (getParent()!=null) {
     return getParentMessage().getFullMessageName()+"/"+getName();
   } else {
     return getName();
   }
 }

  public final String getValue() {
    if (getType().equals(SELECTION_PROPERTY)) {
//      System.err.println("Getting value of selection property. Is this wise? Value = "+myValue);
//      Thread.currentThread().dumpStack();
      return myValue;
    } else {
      return myValue;
    }
  }

  public final Object getTypedValue() {

    if (myValue == null)
      return null;

    if (getType().equals(Property.BOOLEAN_PROPERTY)) {
        return new Boolean( ( (String) getValue()).equals("true"));
    } else if (getType().equals(Property.STRING_PROPERTY)) {
      return getValue();
    } else if (getType().equals(Property.DATE_PROPERTY)) {
      try {
        Date d = dateFormat1.parse(getValue().toString());
        return d;
      }
      catch (Exception ex) {
        try {
          Date d = dateFormat2.parse(getValue().toString());
          return d;
        }catch(Exception ex2){
          System.err.println("Sorry I really can't parse that date..");
          ex2.printStackTrace();
        }
      }
    } else if (getType().equals(Property.INTEGER_PROPERTY)) {
      return new Integer(Integer.parseInt(getValue()));
    } else if (getType().equals(Property.FLOAT_PROPERTY)) {
      return new Double(Double.parseDouble(getValue()));
    }

    return getValue();
  }

  public final void clearValue() {
    myValue = null;
 }

  public final void setValue(java.util.Date value) {
   if (value != null)
    setValue(dateFormat1.format(value));
  else
    myValue = null;
 }

 public final void setValue(Boolean value) {
  if (value != null)
   setValue((value.booleanValue() ? "true" : "false"));
   else
    myValue = null;
 }

 public final void setValue(Double value) {
  if (value != null)
   setValue(value.doubleValue()+"");
   else
    myValue = null;
 }

 public final void setValue(Integer value) {
  if (value != null)
   setValue(value.intValue()+"");
   else
    myValue = null;
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

 public void setValue(boolean value) {
   setValue((value ? "true" : "false"));
 }

 public final void setValue(long value) {
   setValue(value+"");
 }

  public final void setValue(String value) {
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
     else
    myValue = null;
  }

  public final void setName(String name) {
    myName = name;
  }

  public final String getType() {
    return ( this.type );
  }

  public final void setType(String t) {
    type = t;
  }

  public final String toString() {
    String s = getValue();
    if(this.getType() == Property.DATE_PROPERTY){
      return dateFormat3.format((Date)this.getTypedValue());
    }else{
      return s;
    }
  }

  public final ArrayList getAllSelections() {
    ArrayList l = new ArrayList(selectionList);
    return l;
  }

  public final void setSelected(Selection s) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection)selectionList.get(i);
      if (current==s) {
        current.setSelected(true);
      } else {
        current.setSelected(false);
      }
    }
  }
  public final void setAllSelected(boolean b) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection)selectionList.get(i);
      current.setSelected(b);
    }
  }

  public final void setSelectedByValue(Object value) throws NavajoException {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection)selectionList.get(i);
      if (current.getValue()==null) {
        continue;
      }
      if (current.getValue().equals(value)) {
        if (!getCardinality().equals("+")) {
          clearSelections();
        }

        current.setSelected(true);
      } else {
//        current.setSelected(false);
      }
    }
  }

  public final XMLElement toXml(XMLElement parent) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("property");
    x.setAttribute("name",myName);
    if (myValue!=null) {
//      if (Date.class.isInstance(myValue)) {
//        x.setAttribute("value",dateFormat1.format((Date)myValue));
//      } else {
        x.setAttribute("value",(String)myValue);
//      }
    }

    if(direction != null){
      x.setAttribute("direction", direction);
    }else{
       x.setAttribute("direction", "in");
    }

    if ( description != null ) {
       x.setAttribute( "description", description );
    }

    if (length != -1)
       x.setAttribute( "length", length+"" );

    x.setAttribute("type",type);

    if ( cardinality != null ) {
      x.setAttribute("cardinality",cardinality);
    }

    for (int i = 0; i < selectionList.size(); i++) {
      SelectionImpl s = (SelectionImpl)selectionList.get(i);
      x.addChild(s.toXml(x));
    }
    return x;
  }

  public final void fromXml(XMLElement e) {
    super.fromXml( e);
    myName = (String)e.getAttribute("name");
    myValue = (String)e.getAttribute("value");
    description = (String)e.getAttribute("description");
    direction = (String)e.getAttribute("direction");
    type = (String)e.getAttribute("type");
    String sLength = (String) e.getAttribute("length");
    try {
      length = Integer.parseInt(sLength);
    } catch (Exception e1) {
      //System.err.println("ILLEGAL LENGTH IN PROPERTY " + myName + ": " + sLength);
    }

    if(myValue == null && type.equals("boolean")){
    }
    isListType = (type!=null && type.equals("selection"));
    if(isListType) {
      type = "selection";
      cardinality = (String)e.getAttribute("cardinality");
    }
    if (type == null)
      type = Property.STRING_PROPERTY;

    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = (XMLElement)e.getChildren().elementAt(i);
      SelectionImpl s = (SelectionImpl)NavajoFactory.getInstance().createSelection(myDocRoot,"","",false);
      s.fromXml(child);
      s.setParent(this);
      this.addSelection(s);
    }
  }

  public final boolean isEditable() {
    return (Property.DIR_IN.equals(direction) || Property.DIR_INOUT.equals(direction));
  }

  public final void addSelection(Selection s) {
    int max = selectionList.size();
    for (int i = 0; i <max; i++) {
      Selection t = (Selection)selectionList.get(i);
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

  public final Selection getSelection(String name) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection)selectionList.get(i);
      if (current.getName().equals(name)) {
        return current;
      }
    }
    return NavajoFactory.getInstance().createDummySelection();
  }

  public final Selection getSelectionByValue(String value) {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection)selectionList.get(i);
      if (current.getValue().equals(value)) {
        return current;
      }
    }
    return NavajoFactory.getInstance().createDummySelection();
//    return null;
  }
  public final Selection getSelected() {
    for (int i = 0; i < selectionList.size(); i++) {
      Selection current = (Selection)selectionList.get(i);
      if (current.isSelected()) {
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

        cp = (PropertyImpl)NavajoFactory.getInstance().createProperty(n, getName(), getCardinality(), getDescription(), getDirection());
      }
      else {

        cp = (PropertyImpl)NavajoFactory.getInstance().createProperty(n, getName(), getType(), (String) getValue(), getLength(), getDescription(), getDirection());
      }
    }
    catch (NavajoException ex) {
    ex.printStackTrace();
      throw new RuntimeException(ex.toString());
    }
    cp.setRootDoc(n);
    ArrayList mySel = getAllSelections();
    for (int i = 0; i < mySel.size(); i++) {
      SelectionImpl current = (SelectionImpl)mySel.get(i);
      SelectionImpl cc = (SelectionImpl)current.copy((NavajoImpl)n);
      cp.addSelection(cc);
    }
    return cp;
  }

  public final void prune() {
    ArrayList mySel = getAllSelections();
    for (int i = 0; i < mySel.size(); i++) {
      Selection current = (Selection)mySel.get(i);
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
    if (myParent!=null) {
      return myParent.getFullMessageName()+"/"+getName();
    } else {
      return "/"+getName();
    }
  }

  // Filthy hack. I there are some probs with typed value, in respect to displaying a date.
  // I did not dare to change propertyimpl.
  // Maybe later, when I feel brave... Frank
  private final Object getAlternativeTypedValue() {
    if (getType().equals("boolean")) {
      if (getValue() == null)
        return "false";
      else
        return ((String)getValue()).equals("true")?"true":"false";
    }
    if (getType().equals("string")) {
      return (String)getValue();
    }
    if (getType().equals("date")) {
      try {
         if (Date.class.isInstance(getValue())) {
          return getValue();
        } else {
          try {
            Date d = dateFormat1.parse((String)getValue());
            return d;
          } catch (Exception ex0) {
            Date d = dateFormat2.parse((String)getValue());
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
    if (p==null) {
      return 0;
    }

    Comparable ob1 = (Comparable)getAlternativeTypedValue();
    Comparable ob2 = (Comparable)((PropertyImpl)p).getAlternativeTypedValue();
    if (ob1==null || ob2==null) {
      return 0;
    }

    if (ob1.getClass()!=ob2.getClass()) {
      //System.err.println("My name is: "+getName());
      //System.err.println("The other name is: "+((Property)p).getName()+" the type: "+getType()+" - "+((Property)p).getType());
      //System.err.println("Compared "+ob1+" with "+ob2+" class: "+ob1.getClass()+" - "+ob2.getClass());
    }

    if (!Property.class.isInstance(p)) {
      return 0;
    }

    int i =  ob1.compareTo(ob2);
    return i;
  }

  public final Object getRef() {
    return toXml(null);
  }

  public final void setPoints(Vector[] points)  throws NavajoException {
    myPoints = points;
  }

  public final Vector[] getPoints()  throws NavajoException {
    return myPoints;
  }
  public void setSelected(String[] s) throws com.dexels.navajo.document.NavajoException {
    if (!getType().equals(SELECTION_PROPERTY)) {
      throw NavajoFactory.getInstance().createNavajoException("Setting selected of non-selection property!");
    }

    setAllSelected(false);
    for (int i = 0; i < s.length; i++) {
      Selection st = getSelectionByValue(s[i]);
      st.setSelected(true);
    }

  }
  public final void addSelectionWithoutReplace(Selection s) throws com.dexels.navajo.document.NavajoException {
    selectionList.add(s);
  }
  public final void setSelected(ArrayList al) throws com.dexels.navajo.document.NavajoException {
    setAllSelected(false);
    for (int i = 0; i < al.size(); i++) {
      String s = (String)al.get(i);
      Selection sl = getSelectionByValue(s);
      sl.setSelected(true);
    }

  }
  public final ArrayList getAllSelectedSelections() throws com.dexels.navajo.document.NavajoException {
    ArrayList list = new ArrayList();
    ArrayList al = getAllSelections();
    for (int i = 0; i < al.size(); i++) {
      SelectionImpl s = (SelectionImpl)al.get(i);
      if (s.isSelected()) {
        list.add(s);
      }
    }
    return list;

  }
  public final void setSelected(String value) throws com.dexels.navajo.document.NavajoException {
//    Selection s = getSelection(value);
    if (!getCardinality().equals("+")) {
      clearSelections();
    }


    Selection s = getSelectionByValue(value);
    s.setSelected(true);
  }
  public final void clearSelections() throws com.dexels.navajo.document.NavajoException {
    ArrayList al = getAllSelections();
    for (int i = 0; i < al.size(); i++) {
      SelectionImpl s = (SelectionImpl)al.get(i);
      s.setSelected(false);
    }

  }
  public final Selection existsSelection(String name) throws com.dexels.navajo.document.NavajoException {
    return getSelection(name);
  }

//  public String getFullPropertyName()  throws NavajoException {
//    return getPath();
//  }
  public final boolean isDirIn() {
    return getDirection().equals(DIR_IN) || getDirection().equals(DIR_INOUT);
  }

  public final boolean isDirOut() {
    return getDirection().equals(DIR_OUT) || getDirection().equals(DIR_INOUT);
  }

  public static void main(String [] args) {
      PropertyImpl p = new PropertyImpl(null, "");
      Runtime rt = Runtime.getRuntime();
      long start = rt.totalMemory() - rt.freeMemory();
      System.out.println("Total memory: " + rt.totalMemory());
      System.out.println("Free memory: " + rt.freeMemory());
      ArrayList a = new ArrayList();
      for (int i = 0; i < 100000; i++) {
        a.add(new PropertyImpl(null, ""));
      }
      long end = rt.totalMemory() -rt.freeMemory();
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
    return (TreeNode)getParentMessage();
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
}
