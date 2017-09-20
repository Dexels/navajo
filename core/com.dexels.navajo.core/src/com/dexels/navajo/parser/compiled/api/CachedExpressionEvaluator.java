package com.dexels.navajo.parser.compiled.api;

import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.script.api.MappableTreeNode;

public class CachedExpressionEvaluator extends DefaultExpressionEvaluator implements ExpressionEvaluator {

	CachedExpression ce = new CachedExpression();
	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent)
			throws NavajoException {
		Object val =ce.evaluate(clause, inMessage, parent, null, null, null, null, (MappableTreeNode)mappableTreeNode, null);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent,
			Message currentParam) throws NavajoException {
		Object val =ce.evaluate(clause, inMessage, parent, currentParam, null, null, null, (MappableTreeNode)mappableTreeNode, null);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage) throws NavajoException {
		Object val =ce.evaluate(clause, inMessage, null, null, null, null, null, null, null);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}

//	@Override
//	public Message match(String matchString, Navajo inMessage, Object mappableTreeNode, Message parent)
//			throws NavajoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Map<Property, List<Property>> createDependencyMap(Navajo n) throws NavajoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Property> processRefreshQueue(Map<Property, List<Property>> depMap) throws NavajoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ClassLoader getScriptClassLoader() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Comparator<Message> getComparator(String compareFunction) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//


}
