package com.dexels.navajo.tipi.components.echoimpl.parsers;

import java.util.StringTokenizer;

import nextapp.echo2.app.Border;
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
public class BorderParser extends TipiTypeParser {
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return parseBorder(expression);
	}

	private Object parseBorder(String s) {
//		 if (s.endsWith("mm")) {
//		 return parseMillis(s.substring(0,s.length()-2));
//		 }
//		 if (s.endsWith("%")) {
//		 return parsePercent(s.substring(0,s.length()-2));
//		 }
//		 return parsePixels(s);
		 StringTokenizer st = new StringTokenizer(s, "-");
		 String borderName = st.nextToken();
		 if ("etched".equals(borderName)) {
			 return new Border(2,new Color(0,0,0),Border.STYLE_INSET);
		 }
		 if ("raised".equals(borderName)) {
			 return new Border(2,new Color(0,0,0),Border.STYLE_GROOVE);
		 }
		 if ("lowered".equals(borderName)) {
			 return new Border(2,new Color(0,0,0),Border.STYLE_INSET);
		 }
		 if ("titled".equals(borderName)) {
			 return new Border(2,new Color(0,0,0),Border.STYLE_DOUBLE);
		 }
//			 return BorderFactory.createEmptyBorder();
		return null;
	}

}
