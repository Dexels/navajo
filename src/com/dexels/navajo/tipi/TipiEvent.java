package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiEvent {

  public final static int TYPE_ONCHANGE = 0;
  public final static int TYPE_ONACTIONPERFORMED = 1;
  public final static int TYPE_ONLOAD = 2;
  public final static int TYPE_ONFOCUSLOST = 3;
  public final static int TYPE_ONFOCUSGAINED = 4;
  public final static int TYPE_ONSTATECHANGED = 5;
  public final static int TYPE_ONMOUSE_ENTERED = 6;
  public final static int TYPE_ONMOUSE_EXITED = 7;
  public final static int TYPE_ONWINDOWCLOSED = 8;
  public final static int TYPE_SELECTIONCHANGED = 9;
  public final static int TYPE_ONINSTANTIATE = 10;

  private int myType;
  //private String myCondition;
  private String mySource;
  private ArrayList myActions;
  private Navajo myNavajo;
  private TipiComponent myComponent;

  public TipiEvent() {
  }

  public void load(TipiComponent tc, XMLElement elm, TipiContext context) throws TipiException{
    myComponent = tc;
    myActions = new ArrayList();
    if (elm.getName().equals("event")) {
      String stringType = (String) elm.getAttribute("type");
      if (stringType.equals("onChange")) {
        myType = TYPE_ONCHANGE;
      }
      else if (stringType.equals("onLoad")) {
        myType = TYPE_ONLOAD;
      }
      else if (stringType.equals("onActionPerformed")) {
        myType = TYPE_ONACTIONPERFORMED;
      }
      else if (stringType.equals("onFocusGained")) {
        myType = TYPE_ONFOCUSGAINED;
      }
      else if (stringType.equals("onFocusLost")) {
        myType = TYPE_ONFOCUSLOST;
      }
      else if (stringType.equals("onMouseEntered")) {
        myType = TYPE_ONMOUSE_ENTERED;
      }
      else if (stringType.equals("onMouseExited")) {
        myType = TYPE_ONMOUSE_EXITED;
      }
      else if (stringType.equals("onWindowClosed")) {
        myType = TYPE_ONWINDOWCLOSED;
      }
      else if (stringType.equals("onSelectionChanged")) {
        myType = TYPE_SELECTIONCHANGED;
      }
      else if (stringType.equals("onInstantiate")) {
        myType = TYPE_ONINSTANTIATE;
      }

      mySource = (String) elm.getAttribute("listen");
      //myCondition = (String) elm.getAttribute("condition");
      Vector temp = elm.getChildren();
      parseActions(temp, context, null);
    }
  }

  private void parseActions(Vector v, TipiContext context, TipiCondition c){
    try{
      for (int i = 0; i < v.size(); i++) {
        XMLElement current = (XMLElement) v.elementAt(i);
        if (current.getName().equals("action")) {
          TipiAction action = context.instantiateTipiAction(current, myComponent, this);
          action.setCondition(c);
          myActions.add(action);
        }
        if (current.getName().equals("condition")) {
          System.err.println(" -------------------------------> Constructing condition");
          TipiCondition con = context.instantiateTipiCondition(current, myComponent, this);
          parseActions(current.getChildren(), context, con);
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }

  }

  public void performAction(Navajo n, Object source, TipiContext context, Object event) throws TipiException {
    if (source!=null) {
      System.err.println("Performing event. Source: "+source.toString()+" class: "+source.getClass());
    } else {
      System.err.println("Performing event. Called with null source!");
      Thread.currentThread().dumpStack();
    }

        for (int i = 0; i < myActions.size(); i++) {
          TipiAction current = (TipiAction) myActions.get(i);
          try {
            current.execute(n, context, source,event);
          }
          catch (TipiBreakException ex) {
            System.err.println("Break encountered!");
            return;
          }
    }
  }

  public void setNavajo(Navajo n) {
    myNavajo = n;
  }

  public int getType() {
    return myType;
  }

  public String getSource() {
    return mySource;
  }

}