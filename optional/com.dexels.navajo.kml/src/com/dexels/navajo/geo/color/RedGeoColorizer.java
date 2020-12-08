/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.geo.color;

import java.awt.Color;

import com.dexels.navajo.geo.GeoColorizer;

public class RedGeoColorizer implements GeoColorizer {

	@Override
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
	@Override
	public Color createGeoColor(double value, double min, double max) {
		if(value>max) {
			value = max;
		}
		double diff = max - min;
		double norm = value - min;
		double scale = norm / diff; 
		Color c = new Color((float)scale,0,0);
		return c;
	}
}
