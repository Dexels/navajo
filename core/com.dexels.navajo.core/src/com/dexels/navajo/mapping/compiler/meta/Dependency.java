package com.dexels.navajo.mapping.compiler.meta;


/**
 * Abstract class that is used for modelling Dependent artefacts in a script. 
 * A Dependency object is user wherever either one of the following situations hold:
 * 1. The script needs to be recompiled whenever a dependent artefacts is 'dirty'.
 * 2. To keep track of important dependent resources like database, mail servers etc.
 * 
 * Currently the following Dependency classes are defined:
 * - AdapterFieldDependency. To indicate a dependency on a specific field of an adapter. An adapter that contains dependent fields 
 *   should implement the HasDependentResource interface.
 * - IncludeDependency. To indicate a dependency on a script that is 'included' via the <include> tag.
 * - InheritDependency. To indicate a dependency on a script that is 'inherited' via the <inject> tag.
 * - JavaDependency. To indicate a dependency on a Java object/adapter.
 *   
 * @author arjen
 *
 */
public abstract class Dependency {

	protected long timestamp;
	protected String id;
	
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
	
	public String getType() {
		return this.getClass().getSimpleName();
	}

	public void setId(String id) {
		this.id = id;
	}
}
