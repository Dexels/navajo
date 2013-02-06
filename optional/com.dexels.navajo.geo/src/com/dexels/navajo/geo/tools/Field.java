package com.dexels.navajo.geo.tools;

public class Field {
	LatLon[] corners = null;
	private final double WIDTH = 0.068;
	private final double LENGTH = 0.105;
	private LatLon center;
	private double bearing;
	private String id;
	private boolean isHalfField = false;
	private boolean isQuarterField = false;
	
	public Field(LatLon center, double bearing) {
		this.bearing = bearing;
		this.center = center;
		LatLon topL = center.moveTo(LENGTH/2, bearing).moveTo(WIDTH / 2, bearing-90);		
		LatLon topR = topL.moveTo(WIDTH, bearing+90);
		LatLon bottomR = topR.moveTo(LENGTH, bearing+180);
		LatLon bottomL = bottomR.moveTo(WIDTH, bearing-90);
		corners = new LatLon[]{topL,topR,bottomR,bottomL};
	}

	public void setIsHalfField(boolean b){
		this.isHalfField = b;
	}
	
	public void setIsQuarterField(boolean b){
		this.isQuarterField = b;
	}
	
	public LatLon[] getCorners() {
		return corners;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LatLon getCenter() {
		return center;
	}

	public double getBearing() {
		return bearing;
	}
	
	public LatLon getRotationPoint() {
		//return corners[0];
		int divider = 2;
		if(isHalfField){
			divider = 4;
		}
		if(isQuarterField){
			divider = 8;
		}
		double lengthpos = LENGTH/divider;
		return center.moveTo(lengthpos, bearing);
	}	
	
	public LatLon getOppositeCorner() {
		return center.moveTo(LENGTH, bearing).moveTo(LENGTH, bearing+90);
	}
	
	public void setBearing(double bearing){
		this.bearing = bearing;
	}
}
