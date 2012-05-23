package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.awt.Font;
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
public class FontParser extends TipiTypeParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1017456764895180192L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return parseFont(expression);
	}

	private Font parseFont(String s) {
		StringTokenizer str = new StringTokenizer(s, "-");
		String name = str.nextToken();
		int size = Integer.parseInt(str.nextToken());
		int style = Integer.parseInt(str.nextToken());
		// try logical:
		Font f = new Font(name, style, size);
		return f;

	}

}
