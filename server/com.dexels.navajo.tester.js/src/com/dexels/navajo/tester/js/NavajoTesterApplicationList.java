package com.dexels.navajo.tester.js;

import java.util.LinkedHashMap;
import java.util.Map;

public class NavajoTesterApplicationList {
	
	private Map<String, String> applicationMap = new LinkedHashMap<>(); 
	
	public void activate(Map<String,Object> settings) {
		applicationMap.clear();
		applicationMap.put("legacy","Default");
		if(settings==null) {
			return;
		}
		settings.entrySet()
			.stream()
			.filter(e->e.getKey().startsWith("application."))
			.forEach(e->{
				String key = e.getKey().substring("application.".length());
				String value = ""+e.getValue();
				applicationMap.put(key, value);
		});
	}

	public void deactivate() {
		applicationMap.clear();
	}
	
	public Map<String,String> applications() {
		return applicationMap;
	}
}
