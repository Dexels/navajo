package com.dexels.navajo.mapping.compiler.meta;

import java.io.File;

import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.DispatcherFactory;

public class IncludeDependency extends Dependency {
	private String scriptPath = null;
	
	public IncludeDependency(long timestamp, String id) {
		super(timestamp, id);
	}
	
	public IncludeDependency(long timestamp, String id, String path) {
		super(timestamp, id);
		this.scriptPath = path;
	}
	
	public String getScriptPath() {
		return scriptPath;
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
		return getFileTimeStamp(new File(scriptPath));
	}
	
	public final static long getFileTimeStamp(File f) {
        // Try to find included script.
        if ( f.exists() ) {
            return f.lastModified();
        } else {
            return -1;
        }
    }
	
	public boolean isTentantSpecificInclude() {
		return tenantFromScriptPath(scriptPath) != null;
	}
	
	public String getTentant() {
		return tenantFromScriptPath(scriptPath);
	}
	
	public static void main(String [] args) {
		Long l = new Long("32323232");
		new IncludeDependency(l, "ssa");
	}
	
	private String tenantFromScriptPath(String scriptPath) {
		int scoreIndex = scriptPath.lastIndexOf("_");
		int slashIndex = scriptPath.lastIndexOf("/");
		if(scoreIndex>=0 && slashIndex < scoreIndex) {
			return scriptPath.substring(scoreIndex+1, scriptPath.length());
		} else {
			return null;
		}
	}
}
