package com.dexels.navajo.tipi.internal;

public interface Dirtyable {
	
	/**
	 * Indicates if the object has been modified.
	 * @return {@code TRUE} if the object has been modified.
	 */
	public Boolean isDirty();

	/**
	 * Sets the dirty state of an object.
	 * @param b A {@code Boolean} that indicates whether the state is dirty or not.
	 */
	public void setDirty(Boolean b);
}
