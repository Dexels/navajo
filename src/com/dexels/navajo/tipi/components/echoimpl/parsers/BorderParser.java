package com.dexels.navajo.tipi.components.echoimpl.parsers;

import java.util.StringTokenizer;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.able.Expandable;

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
//    private Object parseBorder(String s) {
//        return parseBorder(s);
//    }

    public static Object parseBorder(String s) {
        if(s==null) {
            return null;
        }
        Color c = new Color(50, 50, 50);
        // if (s.endsWith("mm")) {
        // return parseMillis(s.substring(0,s.length()-2));
        // }
        // if (s.endsWith("%")) {
        // return parsePercent(s.substring(0,s.length()-2));
        // }
        // return parsePixels(s);
//        System.err.println("PARSING BORDER:::: "+s);
        int size = 1;
        StringTokenizer st = new StringTokenizer(s, "-");
        String borderName = st.nextToken();
       
        String title = "";
        if (borderName.equals("titled")) {
        	title = st.nextToken();
		} else {
			 if (st.hasMoreTokens()) {
		            String sizeString = st.nextToken();
		            size = Integer.parseInt(sizeString);
		        } 			
		      if (st.hasMoreTokens()) {
		            String colorString = st.nextToken();
		            c = ColorParser.parseColor(colorString);
		        }

		}
  
        if ("etched".equals(borderName)) {
            return new Border(size, c, Border.STYLE_RIDGE);
        }
        if ("raised".equals(borderName)) {
            return new Border(size, c, Border.STYLE_GROOVE);
        }
        if ("lowered".equals(borderName)) {
            return new Border(size, c, Border.STYLE_INSET);
        }
        if ("titled".equals(borderName)) {
        	System.err.println("RETURNING TITLED BORDER: "+title);
            return title;
            
        }
        // return BorderFactory.createEmptyBorder();
        return null;
    }

}
