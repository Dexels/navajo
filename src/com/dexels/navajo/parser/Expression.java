

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
import javax.xml.transform.stream.StreamResult;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.xml.*;

import org.w3c.dom.*;


public class Expression {

    public static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent, Selection sel) throws TMLExpressionException, SystemException {

        Object aap = null;

        try {
            if (clause.trim().equals(""))
                return new Operand("", Property.STRING_PROPERTY, "");

            // java.io.StringBufferInputStream input = new java.io.StringBufferInputStream(clause);
            java.io.StringReader input = new java.io.StringReader(clause);
            TMLParser parser = new TMLParser(input);

            parser.setNavajoDocument(inMessage);
            parser.setMappableObject(o);
            parser.setParentMsg(parent);
            parser.setParentSel(sel);
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
        else if (aap instanceof Integer)
            return new Operand(aap, Property.INTEGER_PROPERTY, "");
        else if (aap instanceof String)
            return new Operand(aap, Property.STRING_PROPERTY, "");
        else if (aap instanceof Date)
            return new Operand(aap, Property.DATE_PROPERTY, "");
        else if (aap instanceof Double) {
            return new Operand(aap, Property.FLOAT_PROPERTY, "");
        } else if (aap instanceof ArrayList)
            return new Operand(aap, Property.SELECTION_PROPERTY, "");
        else if (aap instanceof Boolean)
            return new Operand(aap, Property.BOOLEAN_PROPERTY, "");
        else if (aap.getClass().getName().startsWith("[Ljava.util.Vector")) {
            return new Operand(aap, Property.POINTS_PROPERTY, "");
        } else
            throw new TMLExpressionException("Invalid return type for expression, " + clause + ": " + aap.getClass().getName());

    }

    public static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent) throws TMLExpressionException, SystemException {
        return evaluate(clause, inMessage, o, parent, null);
    }

    public static Operand evaluate(String clause, Navajo inMessage) throws TMLExpressionException, SystemException {
        return evaluate(clause, inMessage, null, null, null);
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
            Util.debugLog("matchSet = " + matchSet + ", matchValue = " + matchValue);
            Operand value = evaluate(matchValue, inMessage, o, parent);

            Util.debugLog("value = " + value.value);

            ArrayList properties;

            if (parent == null)
                properties = inMessage.getProperties(matchSet);
            else
                properties = parent.getProperties(matchSet);
            for (int i = 0; i < properties.size(); i++) {
                Property prop = (Property) properties.get(i);
                Element parentnode = (Element) ((org.w3c.dom.Element) prop.getRef()).getParentNode();

                Util.debugLog("prop = " + prop + ", parent = " + parentnode);
                if (prop.getValue().equals(value.value))
                    return NavajoFactory.getInstance().createMessage(parentnode);
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

}
