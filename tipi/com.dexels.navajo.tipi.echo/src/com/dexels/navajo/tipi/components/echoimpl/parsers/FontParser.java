package com.dexels.navajo.tipi.components.echoimpl.parsers;

import java.util.StringTokenizer;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.Font.Typeface;

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
	private static final long serialVersionUID = 5051820348208927410L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
        return parseFont(expression);
    }

    public static Font parseFont(String s) {
        if(s==null) {
            return null;
        }
        StringTokenizer str = new StringTokenizer(s, "-");
        String name = str.nextToken();
        int size = Integer.parseInt(str.nextToken());
        int style = Integer.parseInt(str.nextToken());
        // try logical:
       return new Font(new Typeface(name), style, new Extent(size));
    }

}
