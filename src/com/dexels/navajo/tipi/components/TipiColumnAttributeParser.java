package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.document.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiColumnAttributeParser {
  private String name, type;
  private String myAttributeType = ColumnAttribute.TYPE_UNKNOWN;
  private Map paramMap = new HashMap();

  public TipiColumnAttributeParser() {
  }

  public TipiColumnAttributeParser(XMLElement elm){

  }

  public ColumnAttribute parseAttribute(XMLElement elm){
    ColumnAttribute ca = new ColumnAttribute();
    name = (String) elm.getAttribute("name");
    type = (String) elm.getAttribute("type");
    Vector kids = elm.getChildren();
    for(int i=0;i<kids.size();i++){
      XMLElement child = (XMLElement) kids.elementAt(i);
      if (child.getName().equals("param")) {
        String param_name = (String) child.getAttribute("name");
        String param_value = (String) child.getAttribute("value");
        paramMap.put(param_name, param_value);
      }
    }

    if(type.equals(ColumnAttribute.TYPE_ROWCOLOR)){
      myAttributeType = ColumnAttribute.TYPE_ROWCOLOR;
    }
    ca.setName(name);
    ca.setType(myAttributeType);
    ca.setParams(paramMap);
    return ca;
  }

  public String getType(){
    return myAttributeType;
  }

  public String getName(){
    return name;
  }

  public String getParam(String name){
    return (String)paramMap.get(name);
  }
}