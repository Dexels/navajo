/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.TipiComponent;

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
public class AttributeRef implements TipiReference {
	private String myName = null;
	private TipiComponent myComponent = null;

	public AttributeRef(TipiComponent tc, String name) {
		setTipiComponent(tc);
		setName(name);
	}

	@Override
	public Object getValue() {
		return myComponent.getValue(myName);
	}

	@Override
	public void setValue(Object val) {
		myComponent.setValue(myName, val);
	}

	public void setTipiComponent(TipiComponent tc) {
		myComponent = tc;
	}

	public TipiComponent getTipiComponent() {
		return myComponent;
	}

	public String getName() {
		return myName;
	}

	public void setName(String n) {
		myName = n;
	}

	@Override
	public String toString() {
		return "attributeref.[component:" + myComponent.getPath()
				+ ",attribute:" + myName + "]";
	}
}
