/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.awt.Color;

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
public class ColorParser extends TipiTypeParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8612302861714882776L;

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return parseColor(expression);
	}

	private Color parseColor(String s) {
		if (s.length() == 0 || s.charAt(0) != '#') {
			throw new RuntimeException("BAD COLOR: " + s);
		}
		String st = s.substring(1);
		int in = Integer.parseInt(st, 16);
		return new Color(in);
	}
}
