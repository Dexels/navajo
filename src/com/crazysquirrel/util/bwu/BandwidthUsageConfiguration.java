/*
 * BandwidthUsageConfiguration.java
 *
 * Created on February 3, 2006, 5:30 PM
 * Copyright (C) 2006 Graham Smith
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.crazysquirrel.util.bwu;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author doozer
 */
public class BandwidthUsageConfiguration {
	
	private String interfaceName;
	private long delay;
	
	/** Creates a new instance of BandwidthUsageConfiguration */
	public BandwidthUsageConfiguration() {}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
	
}
