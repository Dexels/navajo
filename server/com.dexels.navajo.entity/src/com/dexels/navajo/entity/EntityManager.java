package com.dexels.navajo.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {

	private Map<String,Entity> entityMap = new HashMap<String,Entity>();
	
	public void addEntity(Entity e) {
		entityMap.put(e.getName(), e);
	}
	
	public Entity getEntity(String name) throws Exception {
		Entity e = entityMap.get(name);
		e.activate();
		return e;
	}
	
}
