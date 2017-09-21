
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.parser.internal.ParseException;
import com.dexels.navajo.parser.internal.TMLParser;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.script.api.SystemException;

public final class Condition {

	public final static boolean evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message paramParent, Access access) throws TMLExpressionException, SystemException {

		// if (inMessage == null)
		// throw new TMLExpressionException("Empty Navajo specified while evaluating
		// condition");
		Map<String,Object> params = new HashMap<>();
		params.put(Expression.ACCESS, access);
		Operand evaluate = Expression.evaluate(clause, inMessage, o, parent, paramParent, null, null, params); //valuate(clause, inMessage, o, parent, paramParent);
		if(true) {
			return (boolean) evaluate.value;
		}
		
		try {
			clause = clause.replace('\n', ' ');
			if (clause.trim().equals(""))
				return true;

			// java.io.StringBufferInputStream input = new
			// java.io.StringBufferInputStream(clause);
			java.io.StringReader input = new java.io.StringReader(clause);
			TMLParser parser = new TMLParser(input);

			parser.setNavajoDocument(inMessage);
			parser.setMappableObject(o);
			parser.setParentMsg(parent);
			parser.setParentParamMsg(paramParent);
			parser.setAccess(access);
			parser.Expression();
			Object aap = parser.jjtree.rootNode().interpret();

			if (aap instanceof Boolean)
				return ((Boolean) aap).booleanValue();
			else
				throw new TMLExpressionException("Expected boolean return value got: " + aap.getClass().getName());
		} catch (ParseException ce) {
			throw new SystemException(SystemException.PARSE_ERROR, "Condition syntax error: " + clause + "\n"
					+ "After token " + ce.currentToken.toString() + "\n" + ce.getMessage(), ce);
		} catch (Throwable t) {
			throw new TMLExpressionException("Invalid condition: " + clause + ".\nCause: " + t.getMessage(), t);
		}
	}

	public final static boolean evaluate(String clause, Navajo inMessage, Access a)
			throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, null, null, null, a);
	}

	public final static boolean evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent, Access a)
			throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, o, parent, null, a);
	}
}
