package com.dexels.navajo.tipi.context;


public interface ContextManager {

	public abstract void addContextInstance(ContextInstance ci);

	public abstract void removeContextInstance(ContextInstance ci);

	public abstract ContextInstance getContext(String context);

}