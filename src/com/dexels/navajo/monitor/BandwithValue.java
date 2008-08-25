package com.dexels.navajo.monitor;

public class BandwithValue {

	public double bandwidth;
	public String units;
	public String measure;
	
	public BandwithValue(double value, String units, String measure) {
		this.bandwidth = value;
		this.units = units;
		this.measure = measure;
	}
	
	public double getBandwidth() {
		return bandwidth;
	}
	
	public String getUnits() {
		return units;
	}
	
	public String getMeasure() {
		return measure;
	}
	
	public String toString() {
		return bandwidth + " " + units + "(" + measure + ")";
	}
}
