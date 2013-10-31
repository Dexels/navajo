package com.dexels.navajo.server.resource;

import java.util.ArrayList;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Dependency;

public class CompiledTestScript2 extends CompiledScript {

	@Override
	public void execute(Access access) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void finalBlock(Access access) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValidations() {
		// TODO Auto-generated method stub

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
