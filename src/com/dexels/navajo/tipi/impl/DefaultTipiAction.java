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
    String path;
    Map params;
    switch (myType) {
      case TYPE_BREAK:
        throw new TipiBreakException(n,context);
      case TYPE_LOAD:
        throw new RuntimeException("Not yet implemented!");
      case TYPE_LOADCONTAINER:
        throw new RuntimeException("Not yet implemented!");
      case TYPE_PERFORMMETHOD:
        performMethod(n,context,source);
        break;
      case TYPE_CALLSERVICE:
        callService(n,context,source);
        break;
      case TYPE_SETPROPERTYVALUE:
        setPropertyValue(n,context,source);
        break;
      case TYPE_INFO:
        showInfo(n,context,source);
       break;
      case TYPE_SHOWQUESTION:
        showQuestion(n,context,source);
         break;
       case TYPE_EXIT:
         System.exit(0);
          break;
    }
   }

   private void performMethod(Navajo n, TipiContext context, Object source) throws TipiBreakException {
     String componentPath = (String)myParams.get("tipipath");
     String method = (String)myParams.get("method");
     TipiScreen tscr = (TipiScreen)context.getTopLevel();
     System.err.println("LOOKING FOR: "+componentPath);
     Tipi t = tscr.getTipiByPath(componentPath);
     try {
       t.performService(context,method);
    }
     catch (TipiException ex) {
       System.err.println("Error preforming method!");
       ex.printStackTrace();
     }

   }

   private void callService(Navajo n, TipiContext context, Object source) throws TipiBreakException {
     String service = (String) myParams.get("service");
     if (service != null) {
       try {
         context.performTipiMethod(n,service);
       }
       catch (TipiException ex) {
         System.err.println("Error executing call service:");
         ex.printStackTrace();
       }

     }
   }

   private void setPropertyValue(Navajo n, TipiContext context, Object source) throws TipiBreakException {
     String path = (String) myParams.get("path");
     String value = (String) myParams.get("value");
     if (path != null && value != null) {
       Property prop = n.getRootMessage().getPropertyByPath(path);
       prop.setValue(value);
     }
   }

   private void showInfo(Navajo n, TipiContext context, Object source) throws TipiBreakException {
     if (Component.class.isInstance(source)) {
       Component c = (Component)source;
       System.err.println("Params: "+getParams().toString());
       String txt = (String)getParams().get("value");
       System.err.println(txt);
       JOptionPane.showMessageDialog(c,txt);
     } else {
       System.err.println("hmmmmmm....Weird\n\n");
     }
   }

   private void showQuestion(Navajo n, TipiContext context, Object source) throws TipiBreakException {
     String txt = (String)getParams().get("value");
      Component c = (Component)source;
      int response = JOptionPane.showConfirmDialog(c,txt);
      System.err.println("Response: "+response);
      if (response!=0) {
        throw new TipiBreakException(n,source);
      }
   }
}