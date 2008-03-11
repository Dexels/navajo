package com.dexels.navajo.rich.components;

public interface LocationListener {
	public void locationRequested(String locationId, String description, String union, double lat, double lon);
}