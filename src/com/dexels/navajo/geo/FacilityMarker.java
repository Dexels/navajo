package com.dexels.navajo.geo;

public class FacilityMarker {
	private double distance = -1;
	private int index = -1;
	private int maxCount = -1;
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private LatLon current = null;
	private LatLon currentSubfacility = null;
	private Field currentField;
	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
		double bearing = (double)getIndex() / (double)getMaxCount() * 360+1;
		currentSubfacility = current.moveTo( getDistance(), bearing);
		currentField = new Field(currentSubfacility,bearing);
		currentField.setId(getId()+"/"+index);
	}


	public void setCurrentPosition(String coordinates) {
		current = new LatLon(coordinates);		
	}

	// account for message, if present.
	public LatLon getCurrentSubFacility() {
		return currentSubfacility;
	}

	public Field getCurrentField() {
		return currentField;
	}
	
	
}
