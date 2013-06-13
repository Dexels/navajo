package com.dexels.navajo.server.resource;

import java.util.ArrayList;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.ExpressionValueDependency;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Dependency;

public class CompiledTestScript extends CompiledScript {

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

	public ArrayList<Dependency> getDependentObjects() {
		ArrayList<Dependency> deps = new ArrayList<Dependency>();
		deps.add(new ExpressionValueDependency(1, "mies", "noot"));
		deps.add(new AdapterFieldDependency(-1,
				"com.dexels.navajo.server.resource.ResourceTestAdapter",
				"whatever", "'id1'"));
		deps.add(new AdapterFieldDependency(-1,
				"com.dexels.navajo.server.resource.ResourceTestAdapter",
				"whatever", "'id2'"));
		deps.add(new AdapterFieldDependency(
				-1,
				"com.dexels.navajo.server.resource.ResourceTestDoesNotExistAdapter",
				"whatever", "'id3'"));
		deps.add(new ExpressionValueDependency(1, "aap", "noot"));
		deps.add(new AdapterFieldDependency(-1,
				"com.dexels.navajo.server.resource.ResourceTestAdapter2",
				"whatever", "'id4'"));
		return deps;
	}
}
