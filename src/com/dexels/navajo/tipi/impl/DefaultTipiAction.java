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

public class DefaultTipiAction extends TipiAction {

  public void execute(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException,TipiException {
    boolean validCondition = false;
    if(myCondition != null){
      validCondition = myCondition.evaluate(n, context, source, event);
    }else{
      validCondition = true;
    }
    if(validCondition){
      String path;
      Map params;
      switch (myType) {
        case TYPE_BREAK:
          throw new TipiBreakException(n, context);
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
//      case TYPE_SETVISIBLE:
//        setVisible(context, source);
//        break;
//      case TYPE_SETENABLED:
//        setEnabled(context, source);
//        break;
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
        case TYPE_DISPOSE:
          disposeTipiComponent(context, source);
          break;
        case TYPE_DEBUG:
          debug(context, source);
          break;
      }
    }else{
      System.err.println("Condition returned false, not performing this action");
    }
  }


  private void evaluateExpression(TipiContext context, Object source) throws TipiException {
//from_path

    // Change the urls to modern ones, from_path, to_path

    String from_path = (String)myParams.get("from_path");
    String to_path = (String)myParams.get("to_path");
    String to_name = (String)myParams.get("to_name");
    String from_name = (String)myParams.get("from_name");
    String expr = (String)getValueByPath(context,from_path);
    TipiComponent dest = getTipiComponentByPath(source,context, to_path);
//    (String)myParams.get("expression");
    String destname = (String)myParams.get("dest_value");
    TipiComponent destination = getTipiComponentByPath(source,context,to_path);
    Operand o;
    try {
      if (myComponent.getNavajo()==null) {
        System.err.println("Null navajo!");
      }

      o = Expression.evaluate(expr, myComponent.getNearestNavajo());
      //System.err.println("Type: "+o.type);
      dest.setValue(to_name, o.value.toString());

    }
    catch (TMLExpressionException ex) {
      ex.printStackTrace();
    }
    catch (SystemException ex) {
      ex.printStackTrace();
    }
  }

  private void debug(TipiContext context, Object source){
    String type = (String)myParams.get("type");
    String value = (String)myParams.get("value");
    if("object".equals(type)){
      TipiPathParser pp = new TipiPathParser((TipiComponent)source, context, value);
      int object_type = pp.getPathType();
      switch(object_type){
        case TipiPathParser.PATH_TO_COMPONENT:
          TipiComponent tp = pp.getComponent();
          Set valueSet = tp.getPossibleValues();
          Object[] values = valueSet.toArray();
          System.err.println(" ==> DEBUG, TipiComponent " + tp.getName() + " has values: ");
          for(int i=0;i<values.length;i++){
            String vv = (String)tp.getValue((String)values[i]);
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
        case TipiPathParser.PATH_TO_TIPI:
          TipiComponent tc = pp.getComponent();
          Set valueSet2 = tc.getPossibleValues();
          Object[] values2 = valueSet2.toArray();
          System.err.println(" ==> DEBUG, TipiComponent " + tc.getName() + " has values: ");
          for(int i=0;i<values2.length;i++){
            String vv = (String)tc.getValue((String)values2[i]);
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
    }else{
      System.err.println("==> DEBUG: " + value);
    }
  }

  private void instantiateTipi(TipiContext context, Object source) throws TipiException {
    String defname = (String)myParams.get("name");
    String id = (String)myParams.get("id");
    String location = (String)myParams.get("location");
    String forceString = (String)myParams.get("force");
    //System.err.println("force string: "+forceString);
    boolean force;
    if (forceString==null) {
      force = false;
    } else {
      force = forceString.equals("true");
    }

    String componentPath = location + "/"+id;
    //System.err.println("Looking for comp: "+componentPath);
    TipiPathParser tp = new TipiPathParser((TipiComponent)source,context,componentPath);
    TipiComponent comp =  (TipiComponent)tp.getTipi(); // context.getTipiComponentByPath(componentPath);
    if (comp!=null) {
      //System.err.println("FOUND AN INSTANCE ALREADY!!");
      if (force) {
        context.disposeTipi(comp);
      } else {
        //System.err.println("TIPI PRESENT, and no force flag, so ignoring instantiatetipi action");
        comp.reUse();
        return;
      }
    } else {
      //System.err.println("Not found...");
    }

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
    TipiComponent dest = getTipiComponentByPath(source,context,location);
    dest.addComponent(inst,context,null);
 }

  private void copyValueToMessage(TipiContext context, Object source){
    String from_path = (String)myParams.get("from_path");
    String to_path = (String)myParams.get("to_path");
    Object value = getValueByPath(context, from_path);
    System.err.println("Value: " + value.toString());
    TipiPathParser tp = new TipiPathParser(null, context, to_path);
    tp.getProperty().setValue((String)value);
  }

  private void copyValue(TipiContext context, Object source) throws TipiException{
    String from_path = (String)myParams.get("from_path");
    String to_path = (String)myParams.get("to_path");

    TipiPathParser tp = new TipiPathParser((TipiComponent)source, context, to_path);
    TipiComponent targetComponent = tp.getComponent();

    if(tp.getPathType() == tp.PATH_TO_ATTRIBUTE){
      targetComponent.setValue(tp.getAttributeName(), from_path);
    }else if(tp.getPathType() == tp.PATH_TO_PROPERTY){
      Operand o = null;
      try {
        context.setCurrentComponent((TipiComponent)source);
        o = Expression.evaluate(from_path, ((TipiComponent) source).getNearestNavajo(), null, null, null, context);
      }
      catch (Exception ex) {
        throw new RuntimeException("Cannot evaluate inputPath: " + from_path);
      }
      Object sourceObject = o.value;
      if (o.type.equals(Property.FLOAT_PROPERTY))
        tp.getProperty().setValue((Double) sourceObject);
      else if (o.type.equals(Property.INTEGER_PROPERTY))
        tp.getProperty().setValue((Integer) sourceObject);
      else if (o.type.equals(Property.DATE_PROPERTY))
        tp.getProperty().setValue((java.util.Date) sourceObject);
      else if (o.type.equals(Property.BOOLEAN_PROPERTY))
        tp.getProperty().setValue((Double) sourceObject);
      else {
        tp.getProperty().setValue(sourceObject.toString());
      }
    }else{
      throw new TipiException("Illegal copy operation: target should either be a property or an attribute");
    }
  }

  private void setValue(TipiContext context, Object source) throws TipiException {
    String path = (String)myParams.get("path");
    String name = (String)myParams.get("name");
    String value = (String)myParams.get("value");
    TipiPathParser pp = new TipiPathParser((TipiComponent)source, context, path);
    if(pp.getPathType() == pp.PATH_TO_PROPERTY){
      Operand o = null;
      try {
        context.setCurrentComponent((TipiComponent) source);
        o = Expression.evaluate(value, ((TipiComponent) source).getNearestNavajo(), null, null, null, context);
      }
      catch (Exception ex) {
        throw new RuntimeException("Cannot evaluate inputPath: " + value);
      }
      Object sourceObject = o.value;
      if (o.type.equals(Property.FLOAT_PROPERTY))
        pp.getProperty().setValue( (Double) sourceObject);
      else if (o.type.equals(Property.INTEGER_PROPERTY))
        pp.getProperty().setValue( (Integer) sourceObject);
      else if (o.type.equals(Property.DATE_PROPERTY))
        pp.getProperty().setValue( (java.util.Date) sourceObject);
      else if (o.type.equals(Property.BOOLEAN_PROPERTY))
        pp.getProperty().setValue( (Double) sourceObject);
      else {
        pp.getProperty().setValue(sourceObject.toString());
      }
    }else{
      TipiComponent tc = pp.getComponent();
      tc.setValue(name, value);
    }
  }

  private void performTipiMethod(TipiContext context, Object source) throws TipiException {
    String path = (String)myParams.get("path");
    String name = (String)myParams.get("name");
    TipiComponent tc = getTipiComponentByPath(source,context,path);
    tc.performMethod(name,actionElement);
  }

  private Object getValueByPath(TipiContext c, String path){
    TipiPathParser pp = new TipiPathParser(null, c, path);
    switch(pp.getPathType()){
      case TipiPathParser.PATH_TO_MESSAGE:
        throw new RuntimeException("ERROR: Cannot request value of a Message path!");
      case TipiPathParser.PATH_TO_PROPERTY:
        if (pp.getProperty()==null) {
          System.err.println("No such property...");
          return null;
        }
        return pp.getProperty().getValue();

      case TipiPathParser.PATH_TO_TIPI:
        throw new RuntimeException("ERROR: Cannot request value of a Tipi path!");
      case TipiPathParser.PATH_TO_UNKNOWN:
        throw new RuntimeException("ERROR: Cannot request value of a Unknown-Tipi path!");
    }
    return null;
  }

  private void loadUI(TipiContext context, Object source) {
    System.err.println("loadUI called: " + source);
    String file = (String) myParams.get("file");
    if (file != null) {
      /** @todo Fix this again. Remember to close all the toplevel screens. */
      MainApplication.loadXML(file);
    }else{
      System.err.println("WARNING! File is NULL");
    }
  }

  private TipiComponent getTipiComponentByPath(Object source, TipiContext context, String path) {
//    System.err.println("Looking for component (path): "+path);
    TipiPathParser pp = new TipiPathParser((TipiComponent)source, context, path);
    return pp.getComponent();
  }

  private void performMethod(Navajo n, TipiContext context, Object source) throws TipiBreakException {

    String componentPath = (String) myParams.get("tipipath");
    String method = (String) myParams.get("method");
    TipiPathParser pp = new TipiPathParser((TipiComponent)source, context, componentPath);
    Tipi t = pp.getTipi();
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
      Property prop = context.getPropertyByPath(path);
//      Property prop = n.getProperty(path);
      prop.setValue(value);
    }
  }

  private void showInfo(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    System.err.println("showInfo!");
    String txt = (String)myParams.get("text");
    // JOptionPane.showMessageDialog(context.getTopScreen().getContainer(), txt);
    // Watch it!!!
    //Object[] options = {"Ok"};
    JOptionPane.showMessageDialog((Component)context.getTopLevel(), txt, "Info", JOptionPane.PLAIN_MESSAGE);
   //JOptionPane.showOptionDialog((Component)context.getTopLevel(), txt, "Info", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
  }

  private void showQuestion(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    String txt = (String)myParams.get("text");
    Object[] options = {"Ja", "Nee"};
    int response = JOptionPane.showOptionDialog((Component)context.getTopLevel(), txt, "Vraag", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    //int response = JOptionPane.showConfirmDialog((Component)context.getTopLevel(), txt);
    if (response != 0) {
      throw new TipiBreakException(n, source);
    }
  }

  private void disposeTipiComponent(TipiContext context, Object source) throws TipiBreakException {
    String path = (String) myParams.get("path");
    TipiPathParser tp = new TipiPathParser((TipiComponent)source,context,path);
    context.disposeTipi((TipiComponent)(tp.getTipi()));
  }
}