package com.dexels.navajo.tipi.context;

public interface ContextInstance {

	public abstract String getPath();

	public abstract String getDeployment();

	public abstract String getProfile();

	public abstract String getContext();

}