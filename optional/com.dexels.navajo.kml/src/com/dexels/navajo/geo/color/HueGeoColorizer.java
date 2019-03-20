package com.dexels.navajo.geo.color;

import java.awt.Color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.geo.GeoColorizer;

public class HueGeoColorizer implements GeoColorizer {

	
	private final static Logger logger = LoggerFactory.getLogger(HueGeoColorizer.class);

	@Override
	public String createGeoColorString(double value, double min, double max) {
		Color c = createGeoColor(value, min, max);
		String opacity = "a0";
		return createKMLColor(c, opacity);
	}
	@Override
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
		logger.info("Color: "+color);
		return color;
	}
}
