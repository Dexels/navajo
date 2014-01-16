package com.dexels.navajo.repository.api.diff;

public enum EntryChangeType {
	/** Add a new file to the project */
	ADD,

	/** Modify an existing file in the project (content and/or mode) */
	MODIFY,

	/** Delete an existing file from the project */
	DELETE,

	/** Rename an existing file to a new location */
	RENAME,

	/** Copy an existing file to a new location, keeping the original */
	COPY;
}