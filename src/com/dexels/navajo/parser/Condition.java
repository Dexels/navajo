

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;


import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public final class Condition {

    public final static boolean evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent, 
    		Message paramParent
    		) throws TMLExpressionException, SystemException {

      //if (inMessage == null)
      //  throw new TMLExpressionException("Empty Navajo specified while evaluating condition");

      try {
            clause = clause.replace('\n', ' ');
            if (clause.trim().equals(""))
                return true;

            // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream(clause);
            java.io.StringReader input = new java.io.StringReader(clause);
            TMLParser parser = new TMLParser(input);

            parser.setNavajoDocument(inMessage);
            parser.setMappableObject(o);
            parser.setParentMsg(parent);
            parser.setParentParamMsg(paramParent);
            parser.Expression();
            Object aap = parser.jjtree.rootNode().interpret();

            if (aap instanceof Boolean)
                return ((Boolean) aap).booleanValue();
            else
                throw new TMLExpressionException("Expected boolean return value got: " + aap.getClass().getName());
        }  catch (ParseException ce) {
            throw new SystemException(SystemException.PARSE_ERROR, "Condition syntax error: " + clause + "\n" + "After token " + ce.currentToken.toString() + "\n" + ce.getMessage(), ce);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new TMLExpressionException("Invalid condition: " + clause + ".\nCause: " + t.getMessage());
        }
    }

    public final static boolean evaluate(String clause, Navajo inMessage) throws TMLExpressionException, SystemException {
        return evaluate(clause, inMessage, null, null, null);
    }

}
