package com.dexels.navajo.document.json;

import com.dexels.navajo.document.json.impl.JSONTMLImpl;

public class JSONTMLFactory {

	public static JSONTML getInstance() {
		return new JSONTMLImpl();
	}
}
