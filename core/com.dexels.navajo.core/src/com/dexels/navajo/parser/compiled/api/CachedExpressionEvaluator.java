package com.dexels.navajo.parser.compiled.api;

import java.util.Map;
import java.util.Optional;

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
import com.dexels.replication.api.ReplicationMessage;

public class CachedExpressionEvaluator extends DefaultExpressionEvaluator implements ExpressionEvaluator {

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Optional<ReplicationMessage> immutableMessage, Optional<ReplicationMessage> paramMessage)
			throws NavajoException {
		ExpressionCache ce = ExpressionCache.getInstance();
		Object val =ce.evaluate(clause, inMessage, parent, null, null, (MappableTreeNode)mappableTreeNode, null,null,immutableMessage,paramMessage);
//		ce.evaluate(expression, doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage)
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent,
			Message currentParam, Selection selection, Object tipiLink, Map<String,Object> params, Optional<ReplicationMessage> immutableMessage, Optional<ReplicationMessage> paramMessage) throws NavajoException {
		ExpressionCache ce = ExpressionCache.getInstance();
		Access access = params == null? null : (Access)params.get(Expression.ACCESS);
		Object val =ce.evaluate(clause, inMessage, parent, currentParam, selection, (MappableTreeNode)mappableTreeNode, (TipiLink) tipiLink, access,immutableMessage,paramMessage);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Optional<ReplicationMessage> immutableMessage, Optional<ReplicationMessage> paramMessage) throws NavajoException {
		ExpressionCache ce = ExpressionCache.getInstance();
		Object val =ce.evaluate(clause, inMessage, null, null, null, null, null, null, immutableMessage,paramMessage);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

}
