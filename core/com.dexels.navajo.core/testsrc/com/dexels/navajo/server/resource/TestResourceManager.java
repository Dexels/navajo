package com.dexels.navajo.server.resource;

public class TestResourceManager implements ResourceManager {

	public int getWaitingTime(String resourceId) {
		if (resourceId.equals("id1")) {
			return 1000;
		} else if (resourceId.equals("id2")) {
			return 2000;
		} else {
			return 100000;
		}
	}

	public boolean isAvailable(String resourceId) {
		if (resourceId.equals("id1")) {
			return true;
		} else {
			return false;
		}
	}

	public int getHealth(String resourceId) {
		if (resourceId.equals("id5")) {
			return ServiceAvailability.STATUS_DEAD;
		} else {
			return ServiceAvailability.STATUS_BUSY;
		}
	}

	public void setHealth(String resourceId, int h) {
		// TODO Auto-generated method stub

	}

}
