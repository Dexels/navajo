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
		if (!s.startsWith("#")) {
			throw new RuntimeException("BAD COLOR: " + s);
		}
		String st = s.substring(1);
		int in = Integer.parseInt(st, 16);
		return new Color(in);
	}
}
