package com.dexels.navajo.document.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.ExpressionTag;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Param;

public class BaseParamTagImpl extends BasePropertyImpl implements Param {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2611502074984039311L;
	
	String condition;
	String comment;
	
	public BaseParamTagImpl(Navajo n, String name) {
		super(n, name);
	}

	public BaseParamTagImpl(Navajo n, String condition, String name) {
		super(n,name);
		this.condition = condition;
	}

	@Override
	public  String getTagName() {
		return "param";
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public void setComment(String s) {
		this.comment = s;
	}

	@Override
	public String getCondition() {
		return condition;
	}

	@Override
	public void setCondition(String s) {
		condition = s;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		 List<BaseExpressionTagImpl> expressions = new ArrayList<>();
		 for ( ExpressionTag et: this.myExpressions) {
			 if ( et instanceof BaseExpressionTagImpl ) {
				 expressions.add((BaseExpressionTagImpl) et);
			 }
		 }
		 return expressions;
	}

	@Override
	public Map<String,String> getAttributes() {
		Map<String,String> m = super.getAttributes();
		if ( condition != null && !"".equals(condition) ) {
			m.put(Param.PARAM_CONDITION, condition);
		}
		if ( comment != null && !"".equals(comment) ) {
			m.put(Param.PARAM_COMMENT, comment);
		}
		return m;
	}

}