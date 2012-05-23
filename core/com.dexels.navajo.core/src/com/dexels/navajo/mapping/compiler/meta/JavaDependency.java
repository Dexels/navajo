package com.dexels.navajo.mapping.compiler.meta;

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
