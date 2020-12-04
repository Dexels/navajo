/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.parsers;

import java.util.StringTokenizer;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;

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

public class NavajoParser extends TipiTypeParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5653243583919826647L;

	public NavajoParser() {
	}

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		if (".".equals(expression)) {
			return source.getValue(expression);
		}
		if (expression.indexOf(':') != -1) {
			StringTokenizer st = new StringTokenizer(expression, ":");
			String componentPath = st.nextToken();
			String attribute = st.nextToken();
			TipiComponent tc = source.getTipiComponentByPath(componentPath);
			if (tc == null) {
				throw new IllegalArgumentException(
						"Error addressing navajo: Component not found: "
								+ componentPath + " original expression: "
								+ expression);
			}
			if (".".equals(attribute)) {
				return tc.getNavajo();
			}
			return tc.getValue(attribute);
		}
		return source.getContext().getNavajo(expression);
	}
}
