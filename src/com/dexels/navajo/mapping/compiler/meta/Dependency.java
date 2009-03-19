package com.dexels.navajo.mapping.compiler.meta;


public abstract class Dependency {

	private long timestamp;
	private String id;
	
	/**
	 * Create a new dependent object.
	 * 
	 * @param timestamp 
	 * @param id unique id identifying the object
	 */
	public Dependency(long timestamp, String id) {
		this.timestamp = timestamp;
		this.id = id;
	}
	
	public abstract boolean recompileOnDirty();
	
	public abstract long getCurrentTimeStamp();
	
	public final boolean needsRecompile() {
		return ( recompileOnDirty() && getCurrentTimeStamp() > timestamp );
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getId() {
		return id;
	}
}
