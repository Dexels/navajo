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
public abstract class TipiAction {
  public final static int TYPE_LOAD = 1;
  public final static int TYPE_LOADCONTAINER = 2;
  public final static int TYPE_CALLSERVICE = 3;
  public final static int TYPE_SETPROPERTYVALUE = 4;
  public final static int TYPE_BREAK = 5;
  public final static int TYPE_INFO = 6;
  public final static int TYPE_SHOWQUESTION = 7;
  public final static int TYPE_PERFORMMETHOD = 8;
  public final static int TYPE_EXIT = 9;
  public final static int TYPE_SETVISIBLE = 10;
  public final static int TYPE_SETENABLED = 11;
  public final static int TYPE_LOADUI = 12;
  public final static int TYPE_SETVALUE = 13;
  public final static int TYPE_COPYVALUE = 14;
  public final static int TYPE_INSTANTIATE = 15;
  public final static int TYPE_COPYVALUETOMESSAGE = 16;
  public final static int TYPE_PERFORMTIPIMETHOD = 17;
  public final static int TYPE_EVALUATEEXPRESSION = 18;
  public final static int TYPE_DISPOSE = 19;
  public final static int TYPE_DEBUG = 20;
  public final static int TYPE_INSTANTIATE_BY_CLASS = 21;
  protected int myType;
  private String myStringType;
  protected String myAssign;
  protected TipiCondition myCondition;
  protected Map myParams = new HashMap();
  protected TipiComponent myComponent = null;
  protected TipiEvent myEvent = null;
  protected XMLElement actionElement = null;
  public TipiAction() {
  }

  public void load(XMLElement elm, TipiComponent parent, TipiEvent event) {
    myEvent = event;
    myComponent = parent;
    /** @todo Convert everything to lowercase */
    if (elm.getName().equals("action")) {
      String stringType = (String) elm.getAttribute("type");
      myStringType = stringType;
      if (stringType.equals("break")) {
        myType = TYPE_BREAK;
      }
      else if (stringType.equals("callService")) {
        myType = TYPE_CALLSERVICE;
      }
      else if (stringType.equals("setPropertyValue")) {
        myType = TYPE_SETPROPERTYVALUE;
      }
      else if (stringType.equals("showInfo")) {
        myType = TYPE_INFO;
      }
      else if (stringType.equals("showQuestion")) {
        myType = TYPE_SHOWQUESTION;
      }
      else if (stringType.equals("performMethod")) {
        myType = TYPE_PERFORMMETHOD;
      }
      else if (stringType.equals("exit")) {
        myType = TYPE_EXIT;
      }
      else if (stringType.equals("loadUI")) {
        myType = TYPE_LOADUI;
      }
      else if (stringType.equals("setValue")) {
        myType = TYPE_SETVALUE;
      }
      else if (stringType.equals("copyValue")) {
        myType = TYPE_COPYVALUE;
      }
      else if (stringType.equals("instantiate")) {
        myType = TYPE_INSTANTIATE;
      }
      else if (stringType.equals("copyValueToMessage")) {
        myType = TYPE_COPYVALUETOMESSAGE;
      }
      else if (stringType.equals("performTipiMethod")) {
        myType = TYPE_PERFORMTIPIMETHOD;
      }
      else if (stringType.equals("evaluate")) {
        myType = TYPE_EVALUATEEXPRESSION;
      }
      else if (stringType.equals("dispose")) {
        myType = TYPE_DISPOSE;
      }
      else if (stringType.equals("debug")) {
        myType = TYPE_DEBUG;
      }
      else if (stringType.equals("instantiateClass")) {
        myType = TYPE_INSTANTIATE_BY_CLASS;
      }
      actionElement = elm;
      //myAssign = (String) elm.getAttribute("assign");
      //myCondition = (String) elm.getAttribute("condition");
      Vector temp = elm.getChildren();
      for (int i = 0; i < temp.size(); i++) {
        XMLElement current = (XMLElement) temp.elementAt(i);
        if (current.getName().equals("param")) {
          String name = (String) current.getAttribute("name");
          String value = (String) current.getAttribute("value");
          myParams.put(name, value);
        }
      }
    }
  }

  public abstract void execute(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException, TipiException;

  public TipiCondition getCondition() {
    return myCondition;
  }

  public void setCondition(TipiCondition tc) {
    myCondition = tc;
  }

  public XMLElement store(){
    XMLElement s = new CaseSensitiveXMLElement();
    s.setName("action");
    if(myStringType != null){
      s.setAttribute("type", myStringType);
    }
    Iterator it = myParams.keySet().iterator();
    while(it.hasNext()){
      XMLElement parm = new CaseSensitiveXMLElement();
      parm.setName("param");
      String name = (String)it.next();
      String value = (String)myParams.get(name);
      if(name != null){
        parm.setAttribute("name", name);
      }
      if(value != null){
        parm.setAttribute("value", value);
      }
      s.addChild(parm);
    }

    return s;
  }

}