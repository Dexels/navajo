package com.dexels.navajo.tipi.studio;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public interface TipiDefinitionListener {
	// public void tipiDefinitionChanged();
	public void definitionSelected(String name);

	public void definitionAdded(String name);

	public void definitionCommitted(String name);

	public void definitionDeleted(String name);
}
