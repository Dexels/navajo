/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components.parsers;

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
public class BorderParser extends TipiTypeParser {
	private static final long serialVersionUID = 3542584650061038665L;

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return parseBorder(expression);
	}

	// {border:/boldtitled-tralalala}

	private String parseBorder(String s) {
		StringTokenizer st = new StringTokenizer(s, "-");
		String borderName = st.nextToken();

		if ("titled".equals(borderName)) {
			String title = st.nextToken();
			return title;
		}
		return null;
	}

	
}
