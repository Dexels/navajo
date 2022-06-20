/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.util.List;
import java.util.Map;

public class BasePiggybackImpl extends BaseNode {

	private static final long serialVersionUID = -4285472929344591538L;

	private final Map<String,String> piggyData;
	public BasePiggybackImpl(Map<String,String> init) {
		piggyData = init;
	}
	@Override
	public Map<String,String> getAttributes() {
		return piggyData;
	}

	@Override
	public List<BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "piggyback";
	}

}
