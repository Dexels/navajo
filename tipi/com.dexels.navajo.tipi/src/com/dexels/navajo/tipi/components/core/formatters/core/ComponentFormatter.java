/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.formatters.core;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.components.core.TipiFormatter;
@Deprecated
public class ComponentFormatter extends TipiFormatter {

	@Override
	public String format(Object o) {
		TipiComponent tc = (TipiComponent) o;

		return "{component://" + tc.getPath() + "}";
	}

	@Override
	public Class<?> getType() {
		return TipiComponent.class;
	}

}
