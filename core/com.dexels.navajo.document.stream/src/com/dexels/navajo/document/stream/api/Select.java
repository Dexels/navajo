/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.api;

public class Select {
	private final String name;
	private final String value;
	private final boolean selected;

	private Select(String name, String value, boolean selected) {
		this.name = name;
		this.value = value;
		this.selected = selected;
	}
	
	public static Select create(String name, String value, boolean selected) {
		return new Select(name,value,selected);
	}

	public String name() {
		return name;
	}

	public String value() {
		return value;
	}

	public boolean selected() {
		return selected;
	}
}
