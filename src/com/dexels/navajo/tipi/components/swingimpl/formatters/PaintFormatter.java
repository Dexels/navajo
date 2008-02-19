package com.dexels.navajo.tipi.components.swingimpl.formatters;

import java.awt.*;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;

public class PaintFormatter extends TipiFormatter {

//	paint="{paint:/gradient-north-#FEED01-#FFFFFF}"
	
//	private ColorFormatter cf = new ColorFormatter();
	@Override
	public String format(Object o) {
		TipiGradientPaint tc = (TipiGradientPaint)o;
		Color c1 = tc.getColor1();
		Color c2 = tc.getColor2();
		
		String c1form = formatColor(c1);
		String c2form = formatColor(c2);
		return "{paint:/gradient-"+tc.getDirection()+"-" +c1form+"-"+c2form+"}";
	}

	private String formatColor(Color c1) {
		Color tc = c1;
		String red = Integer.toHexString(tc.getRed());
		if(red.length()==1) {
			red = "0"+red;
		}
		String green = Integer.toHexString(tc.getGreen());
		if(green.length()==1) {
			green = "0"+green;
		}
		String blue = Integer.toHexString(tc.getBlue());
		if(blue.length()==1) {
			blue = "0"+blue;
		}
		String hex = "#"+ red+green +blue;
		return hex;
	}

	@Override
	public Class<?> getType() {
		return TipiGradientPaint.class;
	}
	
	public static void main(String[] args) {
		Color c = Color.lightGray;
		PaintFormatter cc = new PaintFormatter();
		System.err.println("Format: "+cc.format(c));
	}

}
