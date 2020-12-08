/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.bean;

import java.util.List;

public class Relation {

	private String id;
	private String selection;
	private List<String> multiple;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSelection(String s) {
		this.selection = s;
	}

	public String getSelection() {
		return selection;
	}

	public List<String> getMultiple() {
		return multiple;
	}

	public void setMultiple(List<String> multiple) {
		this.multiple = multiple;
	}

}
