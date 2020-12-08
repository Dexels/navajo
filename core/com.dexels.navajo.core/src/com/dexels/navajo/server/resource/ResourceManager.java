/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.resource;

public interface ResourceManager {

	public boolean isAvailable(String resourceId);
	public int getWaitingTime(String resourceId);
	public int getHealth(String resourceId);
	public void setHealth(String resourceId, int h);
	
}
