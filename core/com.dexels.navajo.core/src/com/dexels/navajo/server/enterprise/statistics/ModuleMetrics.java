/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.statistics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModuleMetrics {

	private String name;
	private HasMetrics myMetrics;
	
	public ModuleMetrics(String s, HasMetrics hasMetrics) {
		this.name = s;
		this.myMetrics = hasMetrics;
	}
	
	public Iterator<KeyValueMetric> getMetrics() {
		List<KeyValueMetric> metrics = new ArrayList<KeyValueMetric>();
		for ( String m : myMetrics.getMetrics().keySet() ) {
			metrics.add(new KeyValueMetric(m, myMetrics.getMetrics().get(m)));
		}
		return metrics.iterator();
	}
	
	public String getName() {
		return name;
	}

}
