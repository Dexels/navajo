package com.dexels.navajo.geo;

import java.awt.Color;

public interface GeoColorizer {
	public String createGeoColorString(double value, double min, double max);
	public Color createGeoColor(double value, double min, double max);
}
