package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.server.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiAction
    extends TipiAction {
  public void execute(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException,TipiException {
    String path;
    Map params;
    switch (myType) {
      case TYPE_BREAK:
        throw new TipiBreakException(n, context);
      case TYPE_LOAD:
        throw new RuntimeException("Not yet implemented!");
      case TYPE_LOADCONTAINER:
        throw new RuntimeException("Not yet implemented!");
      case TYPE_PERFORMMETHOD:
        performMethod(n, context, source);
        break;
      case TYPE_CALLSERVICE:
        callService(context, source);
        break;
      case TYPE_SETPROPERTYVALUE:
        setPropertyValue(n, context, source);
        break;
      case TYPE_INFO:
        showInfo(n, context, source);
        break;
      case TYPE_SHOWQUESTION:
        showQuestion(n, context, source);
        break;
      case TYPE_EXIT:
        System.exit(0);
        break;
      case TYPE_SETVISIBLE:
        setVisible(context, source);
        break;
      case TYPE_SETENABLED:
        setEnabled(context, source);
        break;
      case TYPE_LOADUI:
        loadUI(context, source);
        break;
      case TYPE_SETVALUE:
        setValue(context, source);
        break;
      case TYPE_COPYVALUE:
        copyValue(context, source);
        break;
      case TYPE_INSTANTIATE:
        instantiateTipi(context, source);
        break;
      case TYPE_COPYVALUETOMESSAGE:
        copyValueToMessage(context, source);
        break;
     case TYPE_PERFORMTIPIMETHOD:
        performTipiMethod(context, source);
        break;
      case TYPE_EVALUATEEXPRESSION:
         evaluateExpression(context, source);
         break;

    }
  }
  private void evaluateExpression(TipiContext context, Object source) throws TipiException {
//from_path
    String from_path = (String)myParams.get("from_path");
    String to_path = (String)myParams.get("to_path");
    String to_name = (String)myParams.get("to_name");
    String from_name = (String)myParams.get("from_name");
    String expr = (String)getValueByPath(context,from_path,from_name);
    TipiComponent dest = getTipiComponentByPath(context, to_path);
//    (String)myParams.get("expression");
    String destname = (String)myParams.get("dest_value");
    TipiComponent destination = getTipiComponentByPath(context,to_path);
    Operand o;
    try {
      if (myComponent.getNavajo()==null) {
        System.err.println("Null navajo!");
      }

      o = Expression.evaluate(expr, myComponent.getNearestNavajo());
      System.err.println("Type: "+o.type);
      dest.setComponentValue(to_name, o.value.toString());

    }
    catch (TMLExpressionException ex) {
      ex.printStackTrace();
    }
    catch (SystemException ex) {
      ex.printStackTrace();
    }
  }

  private void instantiateTipi(TipiContext context, Object source) throws TipiException {
    String defname = (String)myParams.get("name");
    String id = (String)myParams.get("id");
    String location = (String)myParams.get("location");
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("component-instance");
    xe.setAttribute("name",defname);
    Iterator it = myParams.keySet().iterator();
    while (it.hasNext()) {
      String current = (String)it.next();
      xe.setAttribute(current,myParams.get(current));
    }
    TipiComponent inst = context.instantiateComponent(xe);
    inst.setId(id);
    TipiComponent dest = getTipiComponentByPath(context,location);
    inst.getContainer().setVisible(true);
    dest.addComponent(inst,context,null);
 }

  private void copyValueToMessage(TipiContext context, Object source){
    System.err.println("-------------------------------------------------------> CopyValueToMessage called: " + source);
    String from_path = (String)myParams.get("from_path");
    String to_path = (String)myParams.get("to_path");
    String from_name = (String)myParams.get("from_name");
    Object value = getValueByPath(context, from_path, from_name);
    if(to_path.indexOf(":")>-1){
      String path = to_path.substring(0, to_path.indexOf(":"));
      System.err.println("Destination: " + path);
      String property_path = to_path.substring(to_path.indexOf(":")+1);
      TipiComponent dest = getTipiComponentByPath(context, path);
      Navajo n = dest.getNavajo();
      Property p = n.getProperty(property_path);
//      System.err.println("Property: " + p + ", valueclass: " + value.getClass());
      p.setValue((String)value);
    }else{
      System.err.println("Incorrect to_path specified, could not find property!");
    }
  }

  private void copyValue(TipiContext context, Object source) throws TipiException {
    System.err.println("COPYING VALUE!!!!!!");
    String from_path = (String) myParams.get("from_path");
    String to_path = (String) myParams.get("to_path");
    String from_name = (String) myParams.get("from_name");
    String to_name = (String) myParams.get("to_name");
    Object value = getValueByPath(context, from_path, from_name);
    TipiComponent dest = getTipiComponentByPath(context, to_path);
    System.err.println("Value: " + value);
    System.err.println("to: " + to_path + " n: " + to_name);
    dest.setComponentValue(to_name, value);

  }

  private void performTipiMethod(TipiContext context, Object source) throws TipiException {
    String path = (String)myParams.get("path");
    String name = (String)myParams.get("name");
//    String value = (String)myParams.get("value");
//    System.err.println("Source: "+source.getClass());
    TipiComponent tc = getTipiComponentByPath(context,path);
    /**
     * Action Element inherited from TipiAction
     */

    tc.performMethod(name,actionElement);
    //    tc.setComponentValue(name,value);
  }


  private Object getValueByPath(TipiContext c, String path, String from_name){
    String first_bit;
    String last_bit;
    if(from_name.indexOf(":") > -1){
      first_bit = from_name.substring(0, from_name.indexOf(":"));
      last_bit = from_name.substring(from_name.indexOf(":")+1);
      TipiComponent src = getTipiComponentByPath(c,path);
      Object value = src.getComponentValue(first_bit);
      if(Message.class.isInstance(value)){
        Message m = (Message)value;
        Property p = m.getProperty(last_bit);
        System.err.println("Getting property: "+last_bit);
        if(p != null){
          return p.getValue();
        }else{
          return null;
        }
      } else{
        return value;
      }
    }else{
      TipiComponent src = getTipiComponentByPath(c,path);
      Object value = src.getComponentValue(from_name);
      return value;
    }
  }



  private void setValue(TipiContext context, Object source) throws TipiException {
    String path = (String)myParams.get("path");
    String name = (String)myParams.get("name");
    String value = (String)myParams.get("value");
    TipiComponent tc = getTipiComponentByPath(context,path);
    tc.setComponentValue(name,value);
  }

  private void setVisible(TipiContext context, Object source) {
    String componentPath = (String) myParams.get("tipipath");
    String vis = (String) myParams.get("value");
    boolean visible = true;
    if (vis != null) {
      if (vis.equals("false")) {
        visible = false;
      }
    }
    Tipi tscr = context.getTopScreen();
    System.err.println("PATH: " + componentPath);
    Tipi t = tscr.getTipiByPath(componentPath);
    t.getContainer().setVisible(visible);
  }

  private void loadUI(TipiContext context, Object source) {
    String file = (String) myParams.get("file");
    if (file != null) {
      /** @todo Fix this again. Remember to close all the toplevel screens. */
//      MainApplication.loadXML(file);
    }
  }

  private void setEnabled(TipiContext context, Object source) {
    String componentPath = (String) myParams.get("tipipath");
    String vis = (String) myParams.get("value");
    boolean enabled = true;
    if (vis != null) {
      if (vis.equals("false")) {
        enabled = false;
      }
    }
    Tipi tscr = context.getTopScreen();
    Tipi t = tscr.getTipiByPath(componentPath);
    Container c = t.getContainer();
    if (c != null) {
      System.err.println("This tipi has " + c.getComponentCount() + " subcomponents");
      for (int i = 0; i < c.getComponentCount(); i++) {
        Component current = c.getComponent(i);
        System.err.println("Current class: " + current.getClass());
        current.setEnabled(enabled);
      }
    }
    else {
      System.err.println("Cannot set a NULL container to visible");
    }
  }

  private TipiComponent getTipiComponentByPath(TipiContext context, String path) {
    if (path.startsWith("/")) {
      return context.getTipiComponentByPath(path);
    } else {
      return myComponent.getTipiComponentByPath(path);
    }

  }

  private void performMethod(Navajo n, TipiContext context, Object source) throws TipiBreakException {

    String componentPath = (String) myParams.get("tipipath");
    String method = (String) myParams.get("method");
    Tipi tscr = context.getTopScreen();

    Tipi t = tscr.getTipiByPath(componentPath);
    if (t == null) {
      System.err.println("Can not find tipi for: " + componentPath);
      return;
    }

    try {
      t.performService(context, method);
    }
    catch (TipiException ex) {
      System.err.println("Error preforming method!");
      ex.printStackTrace();
    }

  }

  private void callService(TipiContext context, Object source) throws TipiBreakException {
    String service = (String) myParams.get("service");
    if (service != null) {
      try {
        context.performMethod(service);
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
      Property prop = n.getProperty(path);
      prop.setValue(value);
    }
  }

  private void showInfo(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    System.err.println("showInfo!");
    String txt = (String) getParams().get("text");
    JOptionPane.showMessageDialog(context.getTopScreen().getContainer(), txt);
  }

  private void showQuestion(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    String txt = (String) getParams().get("text");
    int response = JOptionPane.showConfirmDialog(context.getTopScreen().getContainer(), txt);
    if (response != 0) {
      throw new TipiBreakException(n, source);
    }
  }
}