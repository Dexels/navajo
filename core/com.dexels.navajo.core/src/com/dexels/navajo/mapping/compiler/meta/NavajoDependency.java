package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.script.api.Dependency;

@Deprecated
public final class NavajoDependency extends Dependency {

	public NavajoDependency(long timestamp, String id) {
		super(timestamp, id);
	}

	@Override
	public final long getCurrentTimeStamp() {
		// Not important.
		return -1;
	}

	@Override
	public final boolean recompileOnDirty() {
		return false;
	}

}
