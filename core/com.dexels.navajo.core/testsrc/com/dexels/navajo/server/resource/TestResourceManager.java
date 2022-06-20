/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.resource;

public class TestResourceManager implements ResourceManager {

	@Override
	public int getWaitingTime(String resourceId) {
		if (resourceId.equals("id1")) {
			return 1000;
		} else if (resourceId.equals("id2")) {
			return 2000;
		} else {
			return 100000;
		}
	}

	@Override
	public boolean isAvailable(String resourceId) {
		return resourceId.equals("id1");
	}

	@Override
	public int getHealth(String resourceId) {
		if (resourceId.equals("id5")) {
			return ServiceAvailability.STATUS_DEAD;
		} else {
			return ServiceAvailability.STATUS_BUSY;
		}
	}

	@Override
	public void setHealth(String resourceId, int h) {

	}

}
