package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiCondition {

  protected TipiComponent myComponent = null;
  protected Map myParams = new HashMap();

  public TipiCondition() {
  }

  public void load(XMLElement elm, TipiComponent parent, TipiEvent event){
    System.err.println("Loading condition");
    myComponent = parent;
    if(elm.getName().equals("condition")){
      System.err.println("Looking for params");
      Vector temp = elm.getChildren();
      for(int i=0;i<temp.size();i++){
        XMLElement current = (XMLElement)temp.elementAt(i);
        if(current.getName().equals("param")){
          String name = (String)current.getAttribute("name");
          String value = (String)current.getAttribute("value");
           myParams.put(name, value);
        }
      }
    }
  }

  public abstract boolean evaluate(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException, TipiException;

}
