package com.dexels.navajo.tipi;

import nanoxml.*;
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

  private int myType;
  private String myCondition;
  private String mySource;
  private ArrayList myActions;
  private Navajo myNavajo;

  public TipiEvent() {
  }

  public void load(XMLElement elm, TipiContext context) throws TipiException{
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

      mySource = (String) elm.getAttribute("listen");
      myCondition = (String) elm.getAttribute("condition");
      Vector temp = elm.getChildren();
      for (int i = 0; i < temp.size(); i++) {
        XMLElement current = (XMLElement) temp.elementAt(i);
        if (current.getName().equals("action")) {
          TipiAction action = context.instantiateTipiAction(current);
          myActions.add(action);
        }
      }
    }
  }

  public void performAction(Navajo n, Object source, TipiContext context) throws TipiException {
        for (int i = 0; i < myActions.size(); i++) {
          TipiAction current = (TipiAction) myActions.get(i);
          try {
            current.execute(n, context, source);
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