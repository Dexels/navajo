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
import com.dexels.navajo.tipi.*;

public class Expression {

    public static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent, Selection sel, TipiLink tl) throws TMLExpressionException, SystemException {

        Object aap = null;

        if (clause.trim().equals(""))
          return new Operand(null, "", "");

        try {
            // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream(clause);
            java.io.StringReader input = new java.io.StringReader(clause);
            TMLParser parser = new TMLParser(input);

            parser.setNavajoDocument(inMessage);
            parser.setMappableObject(o);
            parser.setParentMsg(parent);
            parser.setParentSel(sel);
            parser.setTipiLink(tl);
            parser.Expression();
            aap = parser.jjtree.rootNode().interpret();

        } catch (ParseException ce) {
            ce.printStackTrace();
            throw new SystemException(SystemException.PARSE_ERROR, "Expression syntax error: " + clause + "\n" + "After token " + ce.currentToken.toString() + "\n" + ce.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
            throw new TMLExpressionException("Invalid expression: " + clause + ".\nCause: " + t.getMessage());
        }

        // System.out.println("aap = " + aap);

        if (aap == null)
            return new Operand(null, "", "");

        try {
        String type = MappingUtils.determineNavajoType(aap);
        return new Operand(aap, type, "");
        } catch (TMLExpressionException tmle) {
          throw new TMLExpressionException("Invalid return type for expression, " + clause + ": " + tmle.getMessage());
        }

    }

    public static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent) throws TMLExpressionException, SystemException {
        return evaluate(clause, inMessage, o, parent, null, null);
    }

    public static Operand evaluate(String clause, Navajo inMessage) throws TMLExpressionException, SystemException {
        return evaluate(clause, inMessage, null, null, null, null);
    }

    public static Message match(String matchString, Navajo inMessage, MappableTreeNode o, Message parent) throws TMLExpressionException, SystemException {

        try {
            StringTokenizer tokens = new StringTokenizer(matchString, ";");
            String matchSet = tokens.nextToken();

            if (matchSet == null)
                throw new TMLExpressionException("Invalid usage of match: match=\"[match set];[match value]\"");
            String matchValue = tokens.nextToken();

            if (matchValue == null)
                throw new TMLExpressionException("Invalid usage of match: match=\"[match set];[match value]\"");

            Operand value = evaluate(matchValue, inMessage, o, parent, null, null);


            ArrayList properties;

            if (parent == null)
                properties = inMessage.getProperties(matchSet);
            else
                properties = parent.getProperties(matchSet);
            for (int i = 0; i < properties.size(); i++) {
                Property prop = (Property) properties.get(i);
                Message parentMsg = prop.getParentMessage();

                if (prop.getValue().equals(value.value))
                    return parentMsg;
            }
        } catch (NavajoException e) {
            throw new SystemException(-1, e.getMessage());
        }
        return null;
    }

    public static String replacePropertyValues(String clause, Navajo inMessage) {
        // Find all property references in clause.
        StringBuffer result = new StringBuffer();
        int begin = clause.indexOf("[");

        if (begin == -1) // Clause does not contain properties.
          return clause;

        result.append(clause.substring(0, begin));
        while (begin >= 0) {
            int end = clause.indexOf("]");
            String propertyRef = clause.substring(begin + 1, end);
            Property prop = inMessage.getProperty(propertyRef);
            String value = "null";

            if (prop != null)
                value = prop.getValue();
            result.append("{" + value + "}");
            clause = clause.substring(end + 1, clause.length());
            begin = clause.indexOf("[");
            if (begin >= 0)
                result.append(clause.substring(0, begin));
            else
                result.append(clause.substring(0, clause.length()));
        }
        return result.toString();
    }

    public static void main(String [] args) throws Exception {
      Navajo n = NavajoFactory.getInstance().createNavajo();
      Message m = NavajoFactory.getInstance().createMessage(n, "Bliep");
      n.addMessage(m);
      Property p = NavajoFactory.getInstance().createProperty(n, "Noot", "string", "3214234", 10, "", Property.DIR_OUT);
      m.addProperty(p);
      Message m2 = NavajoFactory.getInstance().createMessage(n, "Bliep2");
      m.addMessage(m2);
      Property p2 = NavajoFactory.getInstance().createProperty(n, "Aap", "string", "IETS ANDERS", 10, "", Property.DIR_OUT);
      m2.addProperty(p2);

      Operand o = Expression.evaluate("[/.*/.*/Aap]", n);
      System.err.println("o =" + o.value);
    }
}
