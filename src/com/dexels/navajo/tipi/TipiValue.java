package com.dexels.navajo.tipi;
import nanoxml.*;

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


  /** @todo Maybe add possibility of default value? */

  public TipiValue() {
  }

  public TipiValue(String name,String type,String direction) {
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
}