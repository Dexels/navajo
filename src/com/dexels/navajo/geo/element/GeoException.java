package com.dexels.navajo.geo.element;

public class GeoException extends Exception {
	public GeoException(String s) {
		super(s);
	}
	public GeoException(Throwable s) {
		super(s);
	}
	public GeoException(String s,Throwable t) {
		super(s,t);
	}
}
