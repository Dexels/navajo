package com.dexels.navajo.tipi.swingclient.components;

import java.util.*;
/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class ColumnAttribute {
  private Map<String,String> myParams;
  private String myType;
  private String myName;
  public final static String TYPE_ROWCOLOR = "rowColor";
  public final static String TYPE_FREEROWCOLOR = "freeRowColor";
  public final static String TYPE_CAPITALIZATION = "capitalization";
  public final static String TYPE_UNKNOWN = "unknown";

  public ColumnAttribute() {
  }

  public void setName(String name){
    myName = name;
  }

  public void setType(String type){
    myType = type;
  }

  public void setParams(Map<String,String> parms){
    myParams = parms;
  }

  public String getType(){
    return myType;
  }

  public String getParam(String name){
    return myParams.get(name);
  }

  public String getName(){
    return myName;
  }

  public Set<String> getParamKeys() {
    return myParams.keySet();
  }

}
