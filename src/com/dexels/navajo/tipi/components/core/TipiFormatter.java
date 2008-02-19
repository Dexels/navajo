package com.dexels.navajo.tipi.components.core;

public abstract class TipiFormatter {
	public abstract Class<?> getType();
	
	public abstract String format(Object o);
}
