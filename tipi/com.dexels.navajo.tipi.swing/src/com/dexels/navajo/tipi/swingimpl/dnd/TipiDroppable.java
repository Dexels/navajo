package com.dexels.navajo.tipi.swingimpl.dnd;

import java.util.List;

public interface TipiDroppable {
	public void fireDropEvent(Object o);

	public boolean acceptsDropCategory(List<String> categories);
}
