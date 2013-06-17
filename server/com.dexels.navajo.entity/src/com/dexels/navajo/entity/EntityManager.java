package com.dexels.navajo.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {

	private Map<String,Entity> entityMap = new HashMap<String,Entity>();
	
	public void EntityManager() {
		
	}
	
	public void addEntity(Entity e) {
		entityMap.put(e.getName(), e);
	}

	public void removeEntity(Entity e) {
		entityMap.remove(e.getName());
	}

	
	public Entity getEntity(String name) throws EntityException {
		Entity e = entityMap.get(name);
		return e;
	}
	
}
