

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;


import java.util.Hashtable;
import com.dexels.navajo.document.*;
import java.util.*;
import com.dexels.navajo.parser.*;


public class Parameters extends java.util.Hashtable implements java.io.Serializable {

    public Parameters() {
        super();
    }

    public void store(String name, String value, String type, String condition, Navajo doc) throws SystemException {

        /**
         * First check if parameter already exists.
         * If so, skip this one. Note the order in which the parameters's condition are evaluated
         * is IMPORTANT!
         */

        if (!this.isValueStored(name).equals(""))
            return;

        // First check condition.
        boolean eval = false;

        try {
            if (condition.trim().equals(""))
                eval = true;
            else
                eval = Condition.evaluate(condition, doc);
        } catch (TMLExpressionException ce) {
            // ce.printStackTrace();
            eval = false;
            // throw new SystemException(SystemException.PARSE_ERROR, "Invalid condition expression: " + value + "\n"+ce.getMessage());
        }

        // If condition succeeds evaluate parameter expression.
        if (eval) {
            Parameter p = new Parameter();

            p.name = name;
            try {
                Operand op = Expression.evaluate(value, doc, null, null, null);

                p.value = op.value;
            } catch (TMLExpressionException tmle) {// throw new SystemException(SystemException.PARSE_ERROR, "Invalid parameter expression: " + value + "\n"+tmle.getMessage());
            }
            p.type = type;
            p.condition = condition;
            super.put(name, p);
        }
    }

    public Enumeration getAllValues() {
        return this.keys();
    }

    private String isValueStored(String name) {

        Parameter p = (Parameter) super.get(name);

        if (p == null)
            return "";

        String value = p.value;

        return value;
    }

    public String getValue(String name) throws NavajoException {

        Parameter p = (Parameter) super.get(name);

        if (p == null)
            throw new NavajoException("No such parameter: " + name);

        String value = p.value;

        return value;
    }

    public String getType(String name) {
        Parameter p = (Parameter) super.get(name);

        if (p == null)
            return "";
        String type = p.type;

        return type;
    }
}
