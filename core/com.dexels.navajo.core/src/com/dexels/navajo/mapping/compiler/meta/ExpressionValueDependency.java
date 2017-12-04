package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.script.api.Dependency;

/**
 * This class is used to find dependencies within Navajo expressions.
 * Currently it only supports finding SQL dependencies (typically in expressions containing SingleValueQuery function calls).
 * 
 * @author arjen
 *
 */
public class ExpressionValueDependency extends Dependency {

	public String type;
	
	public ExpressionValueDependency(long timestamp, String id, String type) {
		super(timestamp, id);
		this.type = type;
	}

	public static Dependency [] getDependencies(String rawExpressionValue) {
		SQLFieldDependency sql = new SQLFieldDependency(-1, null, "sql", rawExpressionValue);
		return sql.getMultipleDependencies();
	}
	
	@Override
	public long getCurrentTimeStamp() {
		return 0;
	}

	@Override
	public boolean recompileOnDirty() {
		return false;
	}

	@Override
	public String getType() {
		return type;
	}
}
