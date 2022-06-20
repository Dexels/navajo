/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.resource;

import java.util.ArrayList;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Dependency;

public class CompiledTestScript2 extends CompiledScript {

	@Override
	public void execute(Access access) throws Exception {

	}

	@Override
	public void finalBlock(Access access) throws Exception {

	}

	@Override
	public void setValidations() {

	}

	@Override
	public ArrayList<Dependency> getDependentObjects() {
		ArrayList<Dependency> deps = new ArrayList<Dependency>();
		deps.add(new AdapterFieldDependency(-1,
				"com.dexels.navajo.server.resource.ResourceTestAdapter3",
				"whatever", "id2"));
		return deps;
	}
}
