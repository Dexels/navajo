package com.dexels.navajo.tipi;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiValue {

  private String name = null;
  private String type = null;
  private String direction = null;
  private String value = null;
  private boolean required = false;

  private HashMap selectionMap;
  /** @todo Maybe add possibility of default value? */

  public TipiValue() {
  }

  public TipiValue(XMLElement xe) {
    load(xe);
  }

  public Object clone() {
    TipiValue tv = new TipiValue();
    tv.setName(name);
    tv.setType(type);
    tv.setRequired(required);
    tv.setValue(value);
    if (selectionMap!=null) {
      tv.setSelectionMap((HashMap)selectionMap.clone());
    }
    return tv;
  }

  public void load(XMLElement xe) {
    if (!xe.getName().equals("value") && !xe.getName().equals("param")) {
      System.err.println("A tipi value element is supposed to be called: 'value' or even 'param', but definitely not '" + xe.getName() + "' you wobbling chincilla");
    }
    this.name = xe.getStringAttribute("name");
    this.type = xe.getStringAttribute("type","string");
    this.direction = xe.getStringAttribute("direction","in");
    this.value = xe.getStringAttribute("value","");
    required = xe.getBooleanAttribute("required","true","false",false);
    if("selection".equals(this.type)){
      Vector options = xe.getChildren();
      if(options.size() > 0){
        selectionMap = new HashMap();
        for(int i=0;i<options.size();i++){
          XMLElement option = (XMLElement)options.get(i);
          String value = option.getStringAttribute("value");
          String description = option.getStringAttribute("description", value);

          selectionMap.put(value, description);
        }
      }else{
        throw new RuntimeException("One or more options expected for selection value [" + this.name + "]");
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String n) {
    name = n;
  }

  public void setType(String t) {
    type = t;
  }

  public void setDirection(String d) {
    direction = d;
  }
  public void setRequired(boolean r) {
    required = r;
  }

  public void setSelectionMap(HashMap m) {
    selectionMap = m;
  }
  public String getType() {
    return type;
  }

  public String getDirection(){
    return direction;
  }

  public void setValue(String value){
    this.value = value;
  }

  public boolean isRequired() {
    return required;
  }

  public boolean isValidSelectionValue(String value){
    if(selectionMap != null){
      if (selectionMap.get(value) != null) {
        return true;
      }
    }
    return false;
  }

  public String getSelectionDescription(String value){
    return (String)selectionMap.get(value);
  }

  public String getValidSelectionValues(){
    String values = "";
    if(selectionMap != null){
      Set keySet = selectionMap.keySet();
      Iterator it = keySet.iterator();
      while(it.hasNext()){
        values = values + ", " + (String)it.next();
      }
      return values.substring(2);
    }else{
      return null;
    }
  }

  public Vector getValidSelectionValuesAsVector(){  // Nice and easy for comboboxes

    Vector values = new Vector();
    if(selectionMap != null){
      Set keySet = selectionMap.keySet();
      Iterator it = keySet.iterator();
      while(it.hasNext()){
        values.add(it.next());
      }
      return values;
    }else{
      return null;
    }
  }


  public String getValue() {
    return value;
  }

  public void typeCheck(Object value) throws TipiException {
    /** @todo Implement this */
  }

}