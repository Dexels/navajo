package com.dexels.navajo.geo.tools;

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

	public void setHalffield(boolean b){
		if(currentField != null){
			currentField.setIsHalfField(b);
			System.err.println("Half field set: "  + currentField.getId());
		}
	}
	
	public void setQuarterfield(boolean b){
		if(currentField != null){
			currentField.setIsQuarterField(b);
			System.err.println("Querter field set: "  + currentField.getId());
		}
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		System.err.println("Set index called with index: " + index + ", doing magic ======================================");
		this.index = index;
		double bearing = (double)getIndex() / (double)getMaxCount() * 360+1;
		currentSubfacility = current.moveTo( getDistance(), bearing);
		System.err.println("MoveTo done, creating field object");
		currentField = new Field(currentSubfacility,bearing);
		currentField.setId(getId()+"/"+index);
		System.err.println("Set index: " + index + " pos: " + currentSubfacility.getCoordinates() + ", done ===============");
	}


	public void setCurrentPosition(String coordinates) {
		if(coordinates != null && coordinates.trim().length() > 1){
			current = new LatLon(coordinates);		
			currentField = new Field(current,0);
			currentField.setId(getId()+"/"+index);
			System.err.println("Field:  " + currentField.getId() + " pos: " + current.getCoordinates());
		}
	}
	
	public void setBearing(String bearing){
		if(bearing != null && !"".equals(bearing) && currentField != null){
			currentField.setBearing(Double.parseDouble(bearing));
			System.err.println("Bearing set: "  + bearing);
		}
	}

	// account for message, if present.
	public LatLon getCurrentSubFacility() {
		return currentSubfacility;
	}

	public Field getCurrentField() {
		return currentField;
	}
	
	
}
