package com.dexels.navajo.document.json.conversion;

import com.dexels.navajo.document.json.conversion.impl.JsonTmlConverterImpl;

public class JsonTmlFactory {

	private static final JsonTmlConverter instance = new JsonTmlConverterImpl();
	
	public static JsonTmlConverter getInstance() {
		return instance;
	}
}
