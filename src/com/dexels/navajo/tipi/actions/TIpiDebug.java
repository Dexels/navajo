package com.dexels.navajo.tipi.actions;

import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiDebug
    extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    debug();
  }

  private void debug() {
    String type = (String) getParameter("type").getValue();
    String value = (String) getParameter("value").getValue();
    if (myComponent != null) {
      System.err.print("PATH: " + ( (TipiComponent) myComponent).getPath());
    }
    if ("object".equals(type)) {
      TipiPathParser pp = new TipiPathParser( (TipiComponent) myComponent, myContext, value);
      int object_type = pp.getPathType();
      switch (object_type) {
        case TipiPathParser.PATH_TO_COMPONENT:
          TipiComponent tp = pp.getComponent();
          Set valueSet = tp.getPossibleValues();
          Object[] values = valueSet.toArray();
          System.err.println(" ==> DEBUG, TipiComponent " + tp.getName() + " has values: ");
          for (int i = 0; i < values.length; i++) {
            String vv = (String) tp.getValue( (String) values[i]);
            System.err.println("  - " + values[i] + " = " + vv);
          }
          break;
        case TipiPathParser.PATH_TO_MESSAGE:
          Message m = pp.getMessage();
          System.err.println(" ==> DEBUG MESSAGE: ");
          m.write(System.err);
          break;
        case TipiPathParser.PATH_TO_PROPERTY:
          Property p = pp.getProperty();
          System.err.println(" ==> DEBUG, Property " + p.getName() + " has value " + p.getValue().toString() + " and is of type " + p.getType());
          break;
        case TipiPathParser.PATH_TO_ATTRIBUTE:
          Object attr = pp.getAttribute();
          System.err.println(" ==> DEBUG, Attribute " + attr.toString());
          break;
        case TipiPathParser.PATH_TO_TIPI:
          TipiComponent tc = pp.getComponent();
          Set valueSet2 = tc.getPossibleValues();
          Object[] values2 = valueSet2.toArray();
          System.err.println(" ==> DEBUG, TipiComponent " + tc.getName() + " has values: ");
          for (int i = 0; i < values2.length; i++) {
            String vv = (String) tc.getValue( (String) values2[i]);
            System.err.println("  - " + values2[i] + " = " + vv);
          }
          break;
        case TipiPathParser.PATH_TO_UNKNOWN:
          System.err.println("==> DEBUG: " + value);
          break;
        default:
          System.err.println("==> DEBUG: " + value);
          break;
      }
    }
    else {
      try {
        Operand o;
        myContext.setCurrentComponent( (TipiComponent) myComponent);
        o = Expression.evaluate(value, ( (TipiComponent) myComponent).getNearestNavajo(), null, null, null, myContext);
        if (o.value != null) {
          value = o.value.toString();
        }
        else {
          value = "ERROR: Expression returned NULL";
        }
      }
      catch (Exception ex) {
        System.err.println("Error evaluating[" + value + "] inserting as plain text only");
        ex.printStackTrace();
      }
      System.err.println("==> DEBUG: " + value);
    }
  }
}