package com.dexels.navajo.geo.element;

public class GeoChangeException extends Exception {

	private static final long serialVersionUID = 4254758850208590620L;
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
