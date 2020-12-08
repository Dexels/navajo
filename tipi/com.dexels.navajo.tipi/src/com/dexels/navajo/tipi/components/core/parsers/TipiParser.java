/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiParser extends BaseTipiParser {
	private static final long serialVersionUID = -8448638491930265661L;

	public TipiParser() {
	}

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return getTipiByPath(source, expression);
	}

}
