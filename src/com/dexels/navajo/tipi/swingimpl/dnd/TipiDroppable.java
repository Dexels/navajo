package com.dexels.navajo.tipi.swingimpl.dnd;

public interface TipiDroppable {
	public void fireDropEvent(Object o);
	public boolean acceptsDropCategory(String category);
}
