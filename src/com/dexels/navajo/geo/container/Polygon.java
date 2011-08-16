package com.dexels.navajo.geo.container;

public class Polygon {
	StringBuffer coordinates;
	
	public Polygon(){
		coordinates = new StringBuffer();
	}
	
	public void appendCoordinates(String coords){
		coordinates.append(coords);
	}
	
	public String getCoordinates(){
		return coordinates.toString();
	}
}
