/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
