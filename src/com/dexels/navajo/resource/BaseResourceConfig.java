package com.dexels.navajo.resource;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class BaseResourceConfig implements ResourceConfig {
	// implement requires / accepts methods
	protected void verifySettings(Map<String, Object> settings) throws Exception {
		Set<String> fail = new HashSet<String>();
		for (String req: requires()) {
			if(!settings.containsKey(req)) {
				fail.add("Missing required setter: "+req+" for resource type: "+getConfigName());
			}
		}
		for (Entry<String,Object> e : settings.entrySet()) {
			if(!accepts().contains(e.getKey())) {
				fail.add("Setter: "+e.getKey()+" is not accepted by "+getConfigName());
			}
		}
		if(!fail.isEmpty()) {
			throw new IllegalStateException(fail.toString());
		}
	}
}
