package com.dexels.navajo.compiler.navascript;

import java.util.ArrayList;
import java.util.List;

public class ParamFragment extends NavascriptFragment {

	public String name;
	public String condition;
	public List<ConditionalExpressionFragment> conditionalExpressions = new ArrayList<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void consumeToken(String s) {
	}

	@Override
	public String consumedFragment() {
		return null;
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void finalize() {
		System.err.println("Finalize ParamFragment: " + getName() + ", condition: " + getCondition());
	}

}
