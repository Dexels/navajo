package com.dexels.navajo.mapping.compiler.meta;

import java.io.File;

import com.dexels.navajo.server.DispatcherFactory;

public class IncludeDependency extends Dependency {

	public IncludeDependency(long timestamp, String id) {
		super(timestamp, id);
	}

	@Override
	public final boolean recompileOnDirty() {
		return true;
	}

	@Override
	public final long getCurrentTimeStamp() {
		return getScriptTimeStamp(getId());
	}

	public final static long getScriptTimeStamp(String id) {
		// Try to find included script.
		String scriptPath =  DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
		File f = new File(scriptPath, id + ".xml");
		if ( f.exists() ) {
			return f.lastModified();
		} else {
			return -1;
		}
	}
	
	public static void main(String [] args) {
		Long l = new Long("32323232");
		new IncludeDependency(l, "ssa");
	}
}
