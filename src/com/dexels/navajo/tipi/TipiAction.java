package com.dexels.navajo.tipi;

import nanoxml.*;
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

public abstract class TipiAction {
  public final static int TYPE_LOAD = 1;
  public final static int TYPE_LOADCONTAINER = 2;
  public final static int TYPE_CALLSERVICE = 3;
  public final static int TYPE_SETPROPERTYVALUE = 4;
  public final static int TYPE_BREAK = 5;
  public final static int TYPE_INFO = 6;
  public final static int TYPE_SHOWQUESTION = 7;
  public final static int TYPE_PERFORMMETHOD = 8;

  protected int myType;
  protected String myAssign;
  protected String myCondition;
  protected Map myParams = new HashMap();

  public TipiAction() {
  }

  public TipiAction(XMLElement elm){
    fromXml(elm);
  }

  public void fromXml(XMLElement elm){
//    System.err.println("Loading action from: "+elm.toString());
    /** @todo Convert everything to lowercase */
    if(elm.getName().equals("action")){
      String stringType = (String)elm.getAttribute("type");
      if(stringType.equals("break")){
        myType = TYPE_BREAK;
      }else if(stringType.equals("load")){
        myType = TYPE_LOAD;
      }else if(stringType.equals("loadContainer")){
        myType = TYPE_LOADCONTAINER;
      }else if(stringType.equals("callService")){
        myType = TYPE_CALLSERVICE;
      }else if(stringType.equals("setPropertyValue")){
        myType = TYPE_SETPROPERTYVALUE;
      } else if(stringType.equals("showinfo")){
        myType = TYPE_INFO;
      }else if(stringType.equals("showquestion")){
        myType = TYPE_SHOWQUESTION;
      }else if(stringType.equals("performmethod")){
        myType = TYPE_PERFORMMETHOD;
      }



      myAssign = (String) elm.getAttribute("assign");
      myCondition = (String) elm.getAttribute("condition");
      Vector temp = elm.getChildren();
      for(int i=0;i<temp.size();i++){
        XMLElement current = (XMLElement)temp.elementAt(i);
        if(current.getName().equals("param")){
          String name = (String)current.getAttribute("name");
          String value = (String)current.getAttribute("value");
          System.err.println("Param: [" +stringType + "]: " + name + "=" + value);
          myParams.put(name, value);
        }
      }
    }
  }

  public abstract void execute(Navajo n, TipiContext context, Object source) throws TipiBreakException;


  public int getType(){
    return myType;
  }
  public String getCondition(){
    return myCondition;
  }
  public String getAssign(){
    return myAssign;
  }
  public Map getParams(){
    return myParams;
  }
}