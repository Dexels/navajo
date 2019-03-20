package com.dexels.navajo.geo.element;

public class GeoException extends Exception {
	
private static final long serialVersionUID = -6931691935372047818L;
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
