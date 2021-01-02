package com.dexels.navajo.compiler.navascript;

public class ConditionalExpressionFragment extends NavascriptFragment {

	private ConditionFragment condition;
	private ExpressionFragment expression;
	
	public void consumeToken(String content) {
		//expressionStr.append(content + " ");
	}
	
	public void addConditionFragment(ConditionFragment cf) {
		this.condition = cf;
	}
	
	public void addExpressionFragment(ExpressionFragment cf) {
		this.expression = cf;
	}

	public ConditionFragment getCondition() {
		return condition;
	}
	
	public ExpressionFragment getExpression() {
		return expression;
	}
	
	@Override
	public String consumedFragment() {
		return null;
	}
	
	public void finalize() {
		
	}
}
