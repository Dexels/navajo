/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.ExpressionTag;
import com.dexels.navajo.document.MapAdapter;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Param;
import com.dexels.navajo.document.Property;

public class BaseParamTagImpl extends BasePropertyImpl implements Param {

	BaseMapTagImpl myMap;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2611502074984039311L;
	
	String condition;
	String comment;
	String mode;
	
	public BaseParamTagImpl(Navajo n) {
		super(n);
	}
	
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
	public String toString() {
		return getName();
	}
	
	public void addMap(BaseMapTagImpl m) {
		myMap = m;
	}
	
	public BaseMapTagImpl getMap() {
		return myMap;
	}
	
	@Override
	public List<? extends BaseNode> getChildren() {
		if ( myMap != null ) {
			List<BaseMapTagImpl> map = new ArrayList<>();
			map.add(myMap);
			return map;
		}
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
		m.remove(Property.PROPERTY_DIRECTION);
		if ( condition != null && !"".equals(condition) ) {
			m.put(Param.PARAM_CONDITION, condition);
		}
		if ( comment != null && !"".equals(comment) ) {
			m.put(Param.PARAM_COMMENT, comment);
		}
		if ( mode != null && !"".equals(mode) ) {
			m.put(Param.PARAM_MODE, mode);
		}
		return m;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}
}
