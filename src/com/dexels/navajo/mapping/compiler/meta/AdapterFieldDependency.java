package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.SystemException;

public class AdapterFieldDependency extends Dependency {

	private String type;
	private String adapterClass;
	
	public AdapterFieldDependency(long timestamp, String adapterClass, String type, String id) {
		super(timestamp, id);
		this.type = type;
		this.adapterClass = adapterClass;
		
	}
	
	@Override
	public long getCurrentTimeStamp() {
		return 0;
	}

	@Override
	public boolean recompileOnDirty() {
		return false;
	}

	public String getType() {
		return type;
	}

	/**
	 * The id is ALWAYS a Navajo Expression. Try to evaluate it in order to determine the id of the 'dependency'.
	 * @return
	 */
	public String getEvaluatedId() {
		try {
			Operand o = Expression.evaluate(getId(), NavajoFactory.getInstance().createNavajo());
			return o.value+"";
		} catch (Exception e) {
			return "[runtime dependency]";
		}
	}
	
	public String getJavaClass() {
		return adapterClass;
	}

}
