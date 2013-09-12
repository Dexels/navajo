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
