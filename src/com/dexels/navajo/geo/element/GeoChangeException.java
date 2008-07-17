package com.dexels.navajo.geo.element;

public class GeoChangeException extends Exception {
	public GeoChangeException(String s) {
		super(s);
	}
	public GeoChangeException(Throwable s) {
		super(s);
	}
	public GeoChangeException(String s,Throwable t) {
		super(s,t);
	}
}
