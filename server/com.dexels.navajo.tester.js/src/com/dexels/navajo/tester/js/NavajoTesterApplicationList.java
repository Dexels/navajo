/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tester.js;

import java.util.LinkedHashMap;
import java.util.Map;

public class NavajoTesterApplicationList {
	
	private Map<String, String> applicationMap = new LinkedHashMap<>(); 
	
	public void activate(Map<String,Object> settings) {
		applicationMap.clear();
		applicationMap.put("legacy","Default");
		if(settings!=null) {
			settings.entrySet()
			.stream()
			.filter(e->e.getKey().startsWith("application."))
			.forEach(e->{
				String key = e.getKey().substring("application.".length());
				String value = ""+e.getValue();
				applicationMap.put(key, value);
		});
		}
	}
	
	public Map<String,String> applications() {
		return applicationMap;
	}
}
