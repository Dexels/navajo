/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.script.api.SystemException;

public final class Expression {
	public static final String ACCESS = "ACCESS";

	private static CachedExpressionEvaluator evaluator = new CachedExpressionEvaluator();
	public static boolean compileExpressions = true; // Enabled by default
	
	private Expression() {
		// no instances
	}
	public static final Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Selection sel, TipiLink tl, Map<String, Object> params, Optional<ImmutableMessage> immutableMessage) {
		return evaluate(clause, inMessage, o, null, null, sel, tl, params, immutableMessage, Optional.empty());
	}

	public static final Operand evaluateImmutable(String clause, Navajo in, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
		return evaluate(clause, in, null, null, null, null, null, null, immutableMessage, paramMessage);
	}
	
	
	public static final Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message paramParent, Selection sel, TipiLink tl, Map<String, Object> params, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
		if (clause.trim().equals("")) {
			return new Operand(null, "", "");
		}
		return evaluator.evaluate(clause, inMessage, o,  parent, paramParent,sel,tl,params,immutableMessage,paramMessage);
	}

	public static final Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message paramParent, Selection sel, TipiLink tl, Map<String, Object> params) {
		return evaluate(clause, inMessage, o, parent, paramParent, sel, tl, params, Optional.empty(),Optional.empty());
	}
	@Deprecated
	public static final Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message paramParent, Selection sel, TipiLink tl) {
		return evaluate(clause, inMessage, o, parent, paramParent, sel, tl, null);
	}

	@Deprecated
	public static final Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Selection sel, TipiLink tl) {
		return evaluate(clause, inMessage, o, parent, null, sel, tl, null);
	}

	public static final Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent) {
		return evaluate(clause, inMessage, o, parent, null, null, null, null);
	}

	public static final Operand evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
			Message parentParam) {
		return evaluate(clause, inMessage, o, parent, parentParam, null, null, null);
	}

	public static final Operand evaluate(String clause, Navajo inMessage) throws SystemException {
		return evaluate(clause, inMessage, null, null, null, null, null, null,Optional.empty(),Optional.empty());
	}

	public static final Message match(String matchString, Navajo inMessage, MappableTreeNode o, Message parent)
			throws SystemException {

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

	public static final String replacePropertyValues(String clause, Navajo inMessage) {
		// Find all property references in clause.
		StringBuilder result = new StringBuilder();
		int begin = clause.indexOf('[');

		if (begin == -1) // Clause does not contain properties.
			return clause;

		result.append(clause.substring(0, begin));
		while (begin >= 0) {
			int end = clause.indexOf(']');
			String propertyRef = clause.substring(begin + 1, end);
			Property prop = inMessage.getProperty(propertyRef);
			String value = "null";

			if (prop != null) {
				String type = prop.getType();
	            if (type.equals(Property.SELECTION_PROPERTY)) {
	                if (!prop.getCardinality().equals("+")) { // Uni-selection property.
	                    try {
	                        List<Selection> list = prop.getAllSelectedSelections();

	                        if (!list.isEmpty()) {
	                            Selection sel = list.get(0);
	                            value = sel.getValue();
	                        } 
	                    } catch (com.dexels.navajo.document.NavajoException te) {
	                        throw new TMLExpressionException(te.getMessage());
	                    }
	                } else { // Multi-selection property.
	                    try {
	                        List<Selection> list = prop.getAllSelectedSelections();
	                        List<String> selectedValues = new ArrayList<>();
	                        for (int i = 0; i < list.size(); i++) {
	                            Selection sel = list.get(i);
	                            String o = sel.getValue();
	                            selectedValues.add(o);
	                        }
	                        value = String.join(";", selectedValues);
	                    } catch (NavajoException te) {
	                        throw new TMLExpressionException(te.getMessage(),te);
	                    }
	                }
	            } 
	            else if (type.equals(Property.STRING_PROPERTY))
	            {
					value = "\"" + prop.getValue() + "\"";
	            }
				else
				{
					value = prop.getValue();
				}
			}
			result.append("{" + value + "}");
			clause = clause.substring(end + 1, clause.length());
			begin = clause.indexOf('[');
			if (begin >= 0)
				result.append(clause.substring(0, begin));
			else
				result.append(clause.substring(0, clause.length()));
		}
		return result.toString();
	}
}
