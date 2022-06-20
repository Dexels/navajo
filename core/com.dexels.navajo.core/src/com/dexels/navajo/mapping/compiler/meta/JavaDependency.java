/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.script.api.Dependency;

public final class JavaDependency extends Dependency {

	public JavaDependency(long timestamp, String id) {
		super(timestamp, id);
	}

	@Override
	public final boolean recompileOnDirty() {
		return false;
	}

	@Override
	public final long getCurrentTimeStamp() {
		return -1;
	}

}
