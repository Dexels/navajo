package com.dexels.navajo.geo;

public class LatLon {
	private Tools tools = new Tools();

	private double lat = Double.NaN;
	private double lon = Double.NaN;
	private double alt = Double.NaN;
	private double bearing = Double.NaN;

	public LatLon(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	
	public LatLon(String coordinates) {
		String[] cc = coordinates.split(",");
		this.lat = Double.parseDouble(cc[0]);
		this.lon = Double.parseDouble(cc[1]);
	}
	
	public LatLon(double[] coords) {
		this.lat = coords[0];
		this.lon = coords[1];
	}

	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	public double getBearing() {
		return bearing;
	}
	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public String getCoordinates() {
		return ""+lon+","+lat;
	}

	public LatLon moveTo(double distance, double bearing) {
		return tools.move(this, distance, bearing);
	}
}
