/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.TipiReference;

/**
 * s
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
public class CookieRef implements TipiReference {

	private final TipiContext myContext;
	private final String myKey;

	public CookieRef(String key, TipiContext myContext) {
		this.myContext = myContext;
		this.myKey = key;
	}

	@Override
	public void setValue(Object val) {
		myContext.setCookie(myKey, (String) val);
	}

	@Override
	public Object getValue() {
		return myContext.getCookie(myKey);
	}

}
