package com.dexels.navajo.tipi;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiValue {

  private String name;
  private String type;
  private String direction;

  public TipiValue(String name,String type,String direction) {
    this.name= name;
    this.type = type;
    this.direction = direction;
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