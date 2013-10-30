package com.dexels.navajo.server.resource;

import java.util.ArrayList;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.server.Access;

public class CompiledTestScript4 extends CompiledScript {

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
				"com.dexels.navajo.server.resource.ResourceTestAdapter",
				"whatever", "'id5'"));
		// Deliberately create circular dependency
		deps.add(new AdapterFieldDependency(-1,
				"com.dexels.navajo.adapter.NavajoMap",
				GenericDependentResource.SERVICE_DEPENDENCY,
				"'CompiledTestScript3'"));
		return deps;
	}
}
