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
  private Map selectionMap;
  /** @todo Maybe add possibility of default value? */

  public TipiValue() {
  }

  public TipiValue(String name,String type,String direction, String value) {
    this.name= name;
    this.type = type;
    this.direction = direction;
  }

  public void load(XMLElement xe) {
    if (!xe.getName().equals("value")) {
      System.err.println("A tipi value element is supposed to be called: 'value'");
    }
    this.name = xe.getStringAttribute("name");
    this.type = xe.getStringAttribute("type","string");
    this.direction = xe.getStringAttribute("direction","in");
    this.value = xe.getStringAttribute("value","");
    if("selection".equals(this.type)){
      Vector options = xe.getChildren();
      if(options.size() > 0){
        selectionMap = new HashMap();
        for(int i=0;i<options.size();i++){
          XMLElement option = (XMLElement)options.get(i);
          String value = option.getStringAttribute("name");
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

  public String getType() {
    return type;
  }

  public String getDirection(){
    return direction;
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

  public String getValue() {
    return value;
  }
}