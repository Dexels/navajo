/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.tipi.TipiContext;

public abstract class BaseDescriptionProvider implements DescriptionProvider {

	protected final Map<String, String> myDescriptionMap = new HashMap<String, String>();
	protected final TipiContext myContext;

	public BaseDescriptionProvider(TipiContext myContext) {
		this.myContext = myContext;
	}

	@Override
	public String getDescription(String id) {
		String desc = myDescriptionMap.get(id);
		return desc;
	}

	public void addDescription(String name, String description) {
		myDescriptionMap.put(name, description);
	}

	public void clearDescriptions() {
		myDescriptionMap.clear();
	}
}
