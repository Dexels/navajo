/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.Property;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

// -- this is a weird contraption

public class PropertyListRef implements TipiReference {
	private final List<Property> propertyList = new ArrayList<Property>();

	public PropertyListRef(List<Property> in) {
		propertyList.addAll(in);
	}

	@Override
	public void setValue(Object val) {
		for (Property p : propertyList) {
			p.setAnyValue(val);
		}
	}

	@Override
	public Object getValue() {
		return null;
	}

}
