package com.dexels.navajo.tipi.internal;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.tipi.TipiContext;

public abstract class BaseDescriptionProvider implements DescriptionProvider{

	protected final Map myDescriptionMap = new HashMap();
	protected final TipiContext myContext;
	
	public BaseDescriptionProvider(TipiContext myContext) {
		this.myContext = myContext;
	}
	
	public String getDescription(String id) {
		String desc = (String)myDescriptionMap.get(id);
		System.err.println("Retriqeving description: "+desc);
		return desc;
	}
	
	public void addDescription(String name, String description) {
		myDescriptionMap.put(name, description);
	}

	public void clearDescriptions() {
		myDescriptionMap.clear();
	}
}
