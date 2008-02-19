package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.tipi.*;

public abstract class BaseDescriptionProvider implements DescriptionProvider {

	protected final Map<String,String> myDescriptionMap = new HashMap<String,String>();
	protected final TipiContext myContext;

	public BaseDescriptionProvider(TipiContext myContext) {
		this.myContext = myContext;
	}

	public String getDescription(String id) {
		String desc = myDescriptionMap.get(id);
		return desc;
	}

	public void addDescription(String name, String description) {
		myDescriptionMap.put(name, description);
	}

	public void clearDescriptions() {
		myDescriptionMap.clear();
	}
}
