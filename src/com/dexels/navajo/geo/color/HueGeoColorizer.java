package com.dexels.navajo.geo.color;

import java.awt.*;

import com.dexels.navajo.geo.*;

public class HueGeoColorizer implements GeoColorizer {

	public String createGeoColorString(double value, double min, double max) {
		Color c = createGeoColor(value, min, max);
		String opacity = "a0";
		return createKMLColor(c, opacity);
	}
	public Color createGeoColor(double value, double min, double max) {
		if(value > max) {
			value = max;
		}
		double offset = value - min;
		double scale = max - min;
		
		double d = (offset / scale);
		
		d = d /1.2+.3;
		d= 1-d;
		Color c = Color.getHSBColor((float) d, 1.0f, 0.5f);
	
		return c;
	}
	private String createKMLColor(Color c, String opacity) {
		String blueString = Integer.toHexString(c.getBlue());
		if (blueString.length()==1) {
			blueString = "0"+blueString;
		}
		String greenString = Integer.toHexString(c.getGreen());
		if (greenString.length()==1) {
			greenString = "0"+greenString;
		}
		String redString = Integer.toHexString(c.getRed());
		if (redString.length()==1) {
			redString = "0"+redString;
		}
		
		String color = opacity+blueString+greenString+redString;
		System.err.println("Color: "+color);
		return color;
	}
}
