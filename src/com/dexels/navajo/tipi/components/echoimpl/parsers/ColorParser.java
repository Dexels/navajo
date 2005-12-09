package com.dexels.navajo.tipi.components.echoimpl.parsers;

import nextapp.echo2.app.Color;

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
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		System.err.println("Hatsa...");
		return parseColor(expression);
	}

	private Color parseColor(String s) {
		// System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><><><><OUWE>>
		// PWPPPPWOOOAAARSSE COLLER: " + s);
		if (!s.startsWith("#")) {
			System.err.println("Eating runtime..don't snap this color: " + s);
			throw new RuntimeException("BAD COLOR: " + s);
		}
		String st = s.substring(1);
		int in = Integer.parseInt(st, 16);
		return new Color(in);
	}
}
