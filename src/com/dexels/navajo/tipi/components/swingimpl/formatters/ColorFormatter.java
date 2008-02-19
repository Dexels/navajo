package com.dexels.navajo.tipi.components.swingimpl.formatters;

import java.awt.*;

import com.dexels.navajo.tipi.components.core.*;

public class ColorFormatter extends TipiFormatter {

	@Override
	public String format(Object o) {
		Color tc = (Color)o;
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
		return "{color:/"+hex+"}";
	}

	@Override
	public Class<?> getType() {
		return Color.class;
	}
	
	public static void main(String[] args) {
		Color c = Color.lightGray;
		ColorFormatter cc = new ColorFormatter();
		System.err.println("Format: "+cc.format(c));
	}

}
