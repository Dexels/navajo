package com.dexels.navajo.parser;

/**
 * $Id$
 * $Log$
 * Revision 1.3  2002/06/11 15:16:03  arjen
 * *** empty log message ***
 *
 * Revision 1.2  2002/06/10 15:11:16  arjen
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2002/06/05 10:12:27  arjen
 * Navajo
 *
 * Revision 1.6  2002/05/17 15:15:12  arjen
 * *** empty log message ***
 *
 * Revision 1.5  2002/03/07 16:15:27  arjen
 * *** empty log message ***
 *
 * Revision 1.4  2002/03/01 09:48:28  arjen
 * <No Comment Entered>
 *
 */

import com.dexels.navajo.util.*;
import com.dexels.navajo.server.Dispatcher;

public class ASTFunctionNode extends SimpleNode {

  String functionName;
  int args = 0;

  public ASTFunctionNode(int id) {
    super(id);
  }

  public Object interpret() throws TMLExpressionException {
    Util.debugLog("function: " + functionName);
    Util.debugLog("args = " + args);

    try {
      //Class c = Dispatcher.getNavajoClassLoader().getClass("com.dexels.navajo.functions."+functionName);
      //Util.debugLog("c = " + c);
      //FunctionInterface  f = (FunctionInterface) c.newInstance();
      //Util.debugLog("f = " + f);
      FunctionInterface f = (FunctionInterface) Dispatcher.getNavajoClassLoader().getPooledObject("com.dexels.navajo.functions."+functionName);
      f.reset();

      for (int i = 0; i < args; i++) {
        Object a = (Object) jjtGetChild(i).interpret();
        System.out.println("operand " + i + ": " + a + "(" + a.getClass().getName() + ")");
        f.insertOperand(a);
        //Util.debugLog("argument " + i + ": " + ((Integer) a).intValue());
      }

      Object result = f.evaluate();
      return result;
    } catch (ClassNotFoundException cnfe) {
      throw new TMLExpressionException("Function not implemented: " + functionName);
    } catch (IllegalAccessException iae) {
      throw new TMLExpressionException(iae.getMessage());
    } catch (InstantiationException ie) {
      throw new TMLExpressionException(ie.getMessage());
    }
  }

}
