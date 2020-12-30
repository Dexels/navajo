/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.ExpressionTag;
import com.dexels.navajo.document.Field;
import com.dexels.navajo.document.MapAdapter;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class BaseFieldTagImpl extends BaseParamTagImpl implements Field {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9083129086534100907L;

	MapAdapter parent;
	String fieldName;
	String constant;
	private boolean oldSkool;
	List<BaseMapTagImpl> children = new ArrayList<>();
	Map<String,String> attributes = new HashMap<>();
	
	public BaseFieldTagImpl(Navajo n) {
		super(n);
	}
	
	public BaseFieldTagImpl(Navajo n, String name, String condition) {
		super(n, condition, name);
		fieldName = name;
	}
	
	public BaseFieldTagImpl(Navajo n, String name, String condition, boolean oldSkool) {
		super(n, condition, name);
		this.oldSkool = oldSkool;
		fieldName = name;
	}

	public void setParent(MapAdapter p) {
		this.parent = p;
	}
	
	public MapAdapter getParent() {
		return parent;
	}
	
	public void addMap(BaseMapTagImpl m) {
		children.add(m);
	}

	@Override
	public String getTagName() {
		if ( oldSkool ) {
			return "field";
		} else {
			return parent.getObject() + "." + fieldName;
		}
	}

	public void setConstant(String c) {
		constant = c;
	}
	
	public String getConstant() {
		return constant;
	}
	
	@Override
	public List<? extends BaseNode> getChildren() {
		
		if ( this.myExpressions.size() > 1 || ( oldSkool && myExpressions.size() > 0 ) ) {
			List<BaseExpressionTagImpl> expressions = new ArrayList<>();
			for ( ExpressionTag et: this.myExpressions) {
				if ( et instanceof BaseExpressionTagImpl ) {
					expressions.add((BaseExpressionTagImpl) et);
				}
			}
			return expressions;
		} else if ( children.size() >  0) {
			return children;
		} else {
			return null;
		}
	}

	public void setAddAttributes(Map<String,String> attr) {
		this.attributes = attr;
	}

	@Override
	public Map<String,String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		m.putAll(attributes);
		if ( !m.containsKey(Field.FIELD_CONDITION) && condition != null && !"".equals(condition) ) {
			m.put(Field.FIELD_CONDITION, condition);
		}
		if ( oldSkool ) {
			m.put("name", fieldName);
		}
		if ( !m.containsKey(Field.FIELD_CONDITION) && !oldSkool && this.myExpressions.size() == 1 ) {
			ExpressionTag et = myExpressions.get(0);
			if ( et.getCondition() != null && !"".equals(et.getCondition())) {
				m.put("condition", et.getCondition());
			}
			m.put("value", et.getValue());
		}
		return m;
	}

	public void addAttributeNameValue(String name, String expression) {
		attributes.put(name, expression);
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setOldSkool(boolean oldSkool) {
		this.oldSkool = oldSkool;
	}

	@Override
	public boolean hasTextNode() {
		return ( constant != null );
	}
	
	@Override
	public void writeText(Writer w) throws IOException  {
		if ( constant != null ) {
			w.write(constant); 
		}
	}
}
