package com.dexels.navajo.tipi;
import com.dexels.navajo.tipi.tipixml.*;

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

  /** @todo Maybe add possibility of default value? */

  public TipiValue() {
  }

  public TipiValue(String name,String type,String direction, String value) {
    this.name= name;
    this.type = type;
    this.direction = direction;
  }

  public void load(XMLElement xe) {
//    if (!xe.getName().equals("value")) {
//      System.err.println("A tipi value element is supposed to be called: 'value'");
//    }
    System.err.println("LOADING VALUE: "+xe.toString());
    this.name = xe.getStringAttribute("name");
    this.type = xe.getStringAttribute("type","string");
    this.direction = xe.getStringAttribute("direction","in");
    this.value = xe.getStringAttribute("value","");
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

  public String getValue() {
    return value;
  }
}