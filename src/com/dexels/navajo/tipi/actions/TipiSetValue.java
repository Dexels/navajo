package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSetValue extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    String path = getParameter("to").getValue();
//    String name = getParameter("name").getValue();
    String value = getParameter("from").getValue();


//    TipiPathParser pp = new TipiPathParser((TipiComponent)source, context, path);
    Operand evaluatedValue = evaluate(value);
    Operand evaluated = evaluate(path);
    System.err.println("Evaluated path: "+evaluated.value);
    System.err.println("Evaluated value: "+evaluatedValue.value);

      if (evaluated.value instanceof Property) {
        Property p = (Property)evaluated.value;
        p.setValue((String)evaluated.value);
      }
      if (evaluated.value instanceof AttributeRef) {
          AttributeRef p = (AttributeRef) evaluated.value;
          p.setValue(evaluatedValue.value);
      }


//    if(pp.getPathType() == pp.PATH_TO_PROPERTY){
//      Operand o = evaluate(value);
//     Object sourceObject = o.value;
//      if (o.type.equals(Property.FLOAT_PROPERTY))
//        pp.getProperty().setValue( (Double) sourceObject);
//      else if (o.type.equals(Property.INTEGER_PROPERTY))
//        pp.getProperty().setValue( (Integer) sourceObject);
//      else if (o.type.equals(Property.DATE_PROPERTY))
//        pp.getProperty().setValue( (java.util.Date) sourceObject);
//      else if (o.type.equals(Property.BOOLEAN_PROPERTY))
//        pp.getProperty().setValue( (Double) sourceObject);
//      else {
//        pp.getProperty().setValue(sourceObject.toString());
//      }
//    }else{
//      if(pp.getPathType() == pp.PATH_TO_ATTRIBUTE){
//        TipiComponent tc = pp.getComponent();
//        tc.setValue(pp.getAttributeName(), path);
//        tc.setValue(name, value);
//      }
//
//    }
  }

  private void setValue(TipiContext context, Object source) throws TipiException {
  }

    private void copyValue(String from_path, String to_path) throws TipiException{
//      String from_path = (String)myParams.get("from_path");
//      String to_path = (String)myParams.get("to_path");

      TipiPathParser tp = new TipiPathParser(myComponent, myContext, to_path);
      TipiComponent targetComponent = tp.getComponent();

      if(tp.getPathType() == tp.PATH_TO_ATTRIBUTE){
        targetComponent.setValue(tp.getAttributeName(), from_path);
      }else if(tp.getPathType() == tp.PATH_TO_PROPERTY){
        Operand o = evaluate(from_path);
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
    private Object getValueByPath(TipiComponent source, TipiContext c, String path){
      TipiPathParser pp = new TipiPathParser(source, c, path);
      switch(pp.getPathType()){
        case TipiPathParser.PATH_TO_MESSAGE:
          return path;
//        throw new RuntimeException("ERROR: Cannot request value of a Message path!");
        case TipiPathParser.PATH_TO_PROPERTY:
          if (pp.getProperty()==null) {
            System.err.println("No such property...");
            return null;
          }
          return pp.getProperty().getValue();

        case TipiPathParser.PATH_TO_TIPI:
//        throw new RuntimeException("ERROR: Cannot request value of a Tipi path!");
          return path;
        case TipiPathParser.PATH_TO_UNKNOWN:
//        throw new RuntimeException("ERROR: Cannot request value of a Unknown-Tipi path!");
          return path;
        case TipiPathParser.PATH_TO_ATTRIBUTE:
          TipiComponent tc = pp.getComponent();
          System.err.println("Path to attribute");
          System.err.println(">>"+tc.getValue(pp.getAttributeName()));
          return tc.getValue(pp.getAttributeName());

      }
      return null;
    }

}