/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.json;

import com.fasterxml.jackson.core.JsonToken;

public class JSONEvent {

	private final JsonToken token;
	private final Object value;
	private final String name;

	public JSONEvent(JsonToken token, String name, Object value) {
		this.token = token;
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Event: "+name+" "+token+" value: "+value;
	}
	
	public JsonToken token() {
		return this.token;
	}

	
}
