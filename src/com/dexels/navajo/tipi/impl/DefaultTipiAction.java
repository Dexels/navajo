package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiAction extends TipiAction {
  public void execute(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    System.err.println("Swinging tipi with: "+n.toXml().toString());
    System.err.println("My type: "+myType);
    String path;
    Map params;
    switch (myType) {
      case TYPE_BREAK:
        throw new TipiBreakException(n,context);
//        break;
      case TYPE_LOAD:
        break;
      case TYPE_LOADCONTAINER:
        break;
      case TYPE_CALLSERVICE:
        params = getParams();
        String service = (String) params.get("service");
        path = (String) params.get("inputpath");
        if (path != null && service != null) {
          Message required = n.getByPath(path);
          context.performTipiMethod(n,service);
        }
        break;
      case TYPE_SETPROPERTYVALUE:
        params = getParams();
        path = (String) params.get("path");
        String value = (String) params.get("value");
        if (path != null && value != null) {
          Property prop = n.getRootMessage().getPropertyByPath(path);
          prop.setValue(value);
          System.err.println("Property: " + prop.getName() + ", value: " + value);
        }
        break;
      case TYPE_INFO:
        if (Component.class.isInstance(source)) {
          Component c = (Component)source;
          System.err.println("Params: "+getParams().toString());
          String txt = (String)getParams().get("value");
          System.err.println(txt);
          JOptionPane.showMessageDialog(c,txt);
        } else {
          System.err.println("hmmmmmm....Weird\n\n");
        }

        break;
      case TYPE_SHOWQUESTION:
        String txt = (String)getParams().get("value");
        Component c = (Component)source;
        int response = JOptionPane.showConfirmDialog(c,txt);
        System.err.println("Response: "+response);
        if (response!=0) {
          throw new TipiBreakException(n,source);
        }


        break;
    }
   }
}