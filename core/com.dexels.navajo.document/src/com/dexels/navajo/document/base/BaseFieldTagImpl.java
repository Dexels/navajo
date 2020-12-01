package com.dexels.navajo.document.base;

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

	public BaseFieldTagImpl(Navajo n, String name, String condition) {
		super(n, condition, name);
		fieldName = name;
	}

	public void setParent(MapAdapter p) {
		this.parent = p;
	}

	@Override
	public String getTagName() {
		return parent.getObject() + "." + fieldName;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		if ( this.myExpressions.size() > 1 ) {
			List<BaseExpressionTagImpl> expressions = new ArrayList<>();
			for ( ExpressionTag et: this.myExpressions) {
				if ( et instanceof BaseExpressionTagImpl ) {
					expressions.add((BaseExpressionTagImpl) et);
				}
			}
			return expressions;
		} else {
			return null;
		}
	}

	@Override
	public Map<String,String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		if ( condition != null && !"".equals(condition) ) {
			m.put(Field.FIELD_CONDITION, condition);
		}
		if (this.myExpressions.size() == 1 ) {
			ExpressionTag et = myExpressions.get(0);
			if ( et.getCondition() != null && !"".equals(et.getCondition())) {
				m.put("condition", et.getCondition());
			}
			m.put("value", et.getValue());
		}
		return m;
	}

}
