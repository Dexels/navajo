

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;

import com.dexels.navajo.document.*;


import java.util.*;
import com.dexels.navajo.parser.*;


public class Parameters extends java.util.Hashtable<String,Parameter> implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9089048153976022369L;

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
                Operand op = Expression.evaluate(value, doc, null, null, null, null);
                p.value = op.value;
            } catch (TMLExpressionException tmle) {// throw new SystemException(SystemException.PARSE_ERROR, "Invalid parameter expression: " + value + "\n"+tmle.getMessage());
            }
            p.type = type;
            p.condition = condition;
            super.put(name, p);
        }
    }

    public Enumeration<String> getAllValues() {
        return this.keys();
    }

    private Object isValueStored(String name) {

        Parameter p = (Parameter) super.get(name);

        if (p == null)
            return "";

        Object value = p.value;

        return value;
    }

    public Object getValue(String name) throws NavajoException {

        Parameter p = (Parameter) super.get(name);

        if (p == null)
            throw NavajoFactory.getInstance().createNavajoException("No such parameter: " + name);

        Object value = p.value;

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
