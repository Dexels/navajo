package com.dexels.navajo.parser.compiled.api;

import java.util.Map;

import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public class CachedExpressionEvaluator extends DefaultExpressionEvaluator implements ExpressionEvaluator {

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent)
			throws NavajoException {
		ExpressionCache ce = ExpressionCache.getInstance();
		Object val =ce.evaluate(clause, inMessage, parent, null, null, null, (MappableTreeNode)mappableTreeNode, null,null);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent,
			Message currentParam, Selection selection, Object tipiLink, Map<String,Object> params) throws NavajoException {
		ExpressionCache ce = ExpressionCache.getInstance();
		Access access = params == null? null : (Access)params.get(Expression.ACCESS);
		Object val =ce.evaluate(clause, inMessage, parent, currentParam, selection, null, (MappableTreeNode)mappableTreeNode, (TipiLink) tipiLink, access);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage) throws NavajoException {
		ExpressionCache ce = ExpressionCache.getInstance();
		Object val =ce.evaluate(clause, inMessage, null, null, null, null, null, null, null);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

}
