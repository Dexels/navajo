package com.dexels.navajo.parser;


/**
 * $Id$
 * $Log$
 * Revision 1.11  2003/06/02 11:53:37  aphilip
 * Watch it!
 *
 * Revision 1.10  2003/05/16 08:28:31  arjen
 * *** empty log message ***
 *
 * Revision 1.9  2003/05/08 15:02:48  frank
 * <No Comment Entered>
 *
 * Revision 1.8  2002/11/26 17:03:28  arjen
 * <No Comment Entered>
 *
 * Revision 1.7  2002/11/09 13:26:41  arjen
 * Removed use of objectpool  for function: possible race-condition problem!
 *
 * Revision 1.6  2002/11/08 16:28:44  arjen
 * <No Comment Entered>
 *
 * Revision 1.5  2002/11/06 09:33:47  arjen
 * Used Jacobe code beautifier over all source files.
 * Added log4j support.
 *
 * Revision 1.4  2002/09/18 16:03:41  matthijs
 * <No Comment Entered>
 *
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

        try {

          Class c;
          if (Dispatcher.getNavajoClassLoader()==null) {
            c = Class.forName("com.dexels.navajo.functions."+functionName);
          } else {
            c = Dispatcher.getNavajoClassLoader().getClass("com.dexels.navajo.functions."+functionName);
          }

            FunctionInterface  f = (FunctionInterface) c.newInstance();
            f.reset();

            for (int i = 0; i < args; i++) {
                Object a = (Object) jjtGetChild(i).interpret();
                System.out.println("IN ASTFUNCTIONODE(), A = " + a);
                f.insertOperand(a);
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
