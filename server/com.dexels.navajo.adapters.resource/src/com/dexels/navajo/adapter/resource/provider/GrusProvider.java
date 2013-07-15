package com.dexels.navajo.adapter.resource.provider;

import org.dexels.grus.GrusConnection;

public interface GrusProvider {

	public GrusConnection requestConnection(String instance, String name);

}
