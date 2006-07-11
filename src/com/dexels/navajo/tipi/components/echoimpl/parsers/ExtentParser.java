package com.dexels.navajo.tipi.components.echoimpl.parsers;

import nextapp.echo2.app.Extent;

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
public class ExtentParser extends TipiTypeParser {
    public Object parse(TipiComponent source, String expression, TipiEvent event) {
        return parseExtent(expression);
    }

    public static Extent parseExtent(String s) {
        if(s==null) {
            return null;
        }
        if (s.endsWith("mm")) {
            return parseMillis(s.substring(0, s.length() - 2));
        }
        if (s.endsWith("%")) {
            return parsePercent(s.substring(0, s.length() - 2));
        }
        return parsePixels(s);
        // StringTokenizer st = new StringTokenizer(s, "-");
        // String borderName = st.nextToken();
        // if ("etched".equals(borderName)) {
        // return BorderFactory.createEtchedBorder();
        // }
        // if ("raised".equals(borderName)) {
        // return BorderFactory.createRaisedBevelBorder();
        // }
        // if ("lowered".equals(borderName)) {
        // return BorderFactory.createLoweredBevelBorder();
        // }
        // if ("titled".equals(borderName)) {
        // String title = st.nextToken();
        // return BorderFactory.createTitledBorder(title);
        // }
        // if ("indent".equals(borderName)) {
        // try {
        // int top = Integer.parseInt(st.nextToken());
        // int left = Integer.parseInt(st.nextToken());
        // int bottom = Integer.parseInt(st.nextToken());
        // int right = Integer.parseInt(st.nextToken());
        // return BorderFactory.createEmptyBorder(top, left, bottom, right);
        // }
        // catch (Exception ex) {
        // System.err.println("Error while parsing border");
        // }
        // }
        // return BorderFactory.createEmptyBorder();
        // return null;
    }

    private static Extent parsePixels(String s) {
        int px = Integer.parseInt(s);
        // TODO Auto-generated method stub
        return new Extent(px, Extent.PX);
    }

    private static Extent parsePercent(String s) {
        int pc = Integer.parseInt(s);
        return new Extent(pc, Extent.PERCENT);
    }

    private static Extent parseMillis(String s) {
        int mm = Integer.parseInt(s);
        return new Extent(mm, Extent.MM);
    }
}
