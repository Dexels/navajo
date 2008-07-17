package com.dexels.navajo.geo.color;

import java.awt.*;

import com.dexels.navajo.geo.*;

public class WhiteRedColorizer implements GeoColorizer {

	public String createGeoColorString(double value, double min, double max) {
		return createKMLColor(createGeoColor(value, min, max), "c0");
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
		
		return opacity+blueString+greenString+redString;
	}
	public Color createGeoColor(double value, double min, double max) {
		if(value>max) {
			value = max;
		}
		double diff = max - min;
		double norm = value - min;
		double scale = norm / diff; 
		Color c = new Color(1,1-(float)scale,1-(float)scale);
		return c;
	}
}
