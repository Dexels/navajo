/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;
import com.dexels.navajo.parser.internal.ParseException;
import com.dexels.navajo.parser.internal.TMLParser;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.script.api.SystemException;
//import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.tipilink.TipiLink;

public final class Expression {

	public static String ACCESS = "ACCESS";

	private static CachedExpressionEvaluator evaluator = new CachedExpressionEvaluator();
	
	public final static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message paramParent, Selection sel, TipiLink tl, Map<String, Object> params)
			throws TMLExpressionException, SystemException {

		if(true) {
			return evaluator.evaluate(clause, inMessage, o,  parent, paramParent);
		}
		
		Object aap = null;

		if (clause.trim().equals("")) {
			return new Operand(null, "", "");
		}
		if ((clause.length() > 0 && clause.charAt(0) == '=') && clause.endsWith(";")) {

			clause = clause.substring(1, clause.length() - 1);
		}
		try {

			TMLParser parser = null;

			java.io.StringReader input = new java.io.StringReader(clause);
			parser = new TMLParser(input);
			if (params != null) {
				Access a = (Access) params.get(ACCESS);
				if (a != null) {
					parser.setAccess(a);
				}
			}
			parser.setNavajoDocument(inMessage);
			parser.setMappableObject(o);
			parser.setParentMsg(parent);
			parser.setParentParamMsg(paramParent);
			parser.setParentSel(sel);
			parser.setTipiLink(tl);
			parser.Expression();

			aap = parser.jjtree.rootNode().interpret();

		} catch (ParseException ce) {
			throw new SystemException(SystemException.PARSE_ERROR, "Expression syntax error: " + clause + "\n"
					+ "After token " + ce.currentToken.toString() + "\n" + ce.getMessage(), ce);
		} catch (Throwable t) {
			throw new TMLExpressionException("Invalid expression: " + clause + ".\nCause: " + t.getMessage(), t);
		}

		if (aap == null)
			return new Operand(null, "", "");

		String type = MappingUtils.determineNavajoType(aap);
		return new Operand(aap, type, "");

	}

	@Deprecated
	public final static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message paramParent, Selection sel, TipiLink tl) throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, o, parent, paramParent, sel, tl, null);
	}

	@Deprecated
	public final static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Selection sel, TipiLink tl) throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, o, parent, null, sel, tl, null);
	}

	public final static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent)
			throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, o, parent, null, null, null, null);
	}

	@Deprecated
	public final static Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message parentParam) throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, o, parent, parentParam, null, null, null);
	}

	public final static Operand evaluate(String clause, Navajo inMessage)
			throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, null, null, null, null, null, null);
	}

	public final static Message match(String matchString, Navajo inMessage, MappableTreeNode o, Message parent)
			throws TMLExpressionException, SystemException {

		try {
			StringTokenizer tokens = new StringTokenizer(matchString, ";");
			String matchSet = tokens.nextToken();

			if (matchSet == null)
				throw new TMLExpressionException("Invalid usage of match: match=\"[match set];[match value]\"");
			String matchValue = tokens.nextToken();

			if (matchValue == null)
				throw new TMLExpressionException("Invalid usage of match: match=\"[match set];[match value]\"");

			Operand value = evaluate(matchValue, inMessage, o, parent, null, null, null, null);

			List<Property> properties;

			if (parent == null)
				properties = inMessage.getProperties(matchSet);
			else
				properties = parent.getProperties(matchSet);
			for (int i = 0; i < properties.size(); i++) {
				Property prop = properties.get(i);
				Message parentMsg = prop.getParentMessage();

				if (prop.getValue().equals(value.value))
					return parentMsg;
			}
		} catch (NavajoException e) {
			throw new SystemException(-1, e.getMessage(), e);
		}
		return null;
	}

	public final static String replacePropertyValues(String clause, Navajo inMessage) {
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

			if (prop != null) {
				if (prop.getType().equals(Property.STRING_PROPERTY))
					value = "\"" + prop.getValue() + "\"";
				else
					value = prop.getValue();
			}
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
