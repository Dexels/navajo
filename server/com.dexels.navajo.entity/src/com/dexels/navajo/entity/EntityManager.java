package com.dexels.navajo.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Operation;

/**
 * TODO: NAVAJO CLUSTER ENABLING
 * 
 * @author arjenschoneveld
 *
 */
public class EntityManager {

	private Map<String,Entity> entityMap = new HashMap<String,Entity>();
	private Map<String,Map<String,Operation>> operationsMap = new HashMap<String,Map<String,Operation>>();

	private static EntityManager instance;
	
	public void EntityManager() {
	}

	public void activate() {
		instance = this;
	}
	
	public void deactivate() {
		entityMap.clear();
		operationsMap.clear();
		instance = null;
	}
	
	public static EntityManager getInstance() {
		return instance;
	}
	
	public void addOperation(Operation o) {
		Map<String,Operation> operationEntry = null;
		if ( ( operationEntry = operationsMap.get(o.getEntityName())) == null ) {
			operationEntry = new HashMap<String, Operation>();
			operationsMap.put(o.getEntityName(), operationEntry);
		}
		operationEntry.put(o.getMethod(), o);
		System.err.println("In addOperation(" + o.getEntityName() + "," + o.getMethod() + "," + o.getService() + ")");
	}

	public void removeOperation(Operation o) {
		Map<String,Operation> operationEntry = null;
		if ( ( operationEntry = operationsMap.get(o.getEntityName())) != null ) {
			operationEntry.remove(o.getMethod());
		}
	}

	public void addEntity(Entity e) {
		entityMap.put(e.getName(), e);
		System.err.println("In addEntity(" + e.getName() + ")");
	}

	public void removeEntity(Entity e) {
		entityMap.remove(e.getName());
	}

	public Operation getOperation(String entity, String method) throws EntityException{
		if ( operationsMap.get(entity) != null && operationsMap.get(entity).get(method) != null ) {
			return operationsMap.get(entity).get(method);
		}
		if ( getEntity(entity) == null ) {
			throw new EntityException("Unknown entity: " + entity);
		}
		throw new EntityException("Operation " + method + " not supported for entity: " + entity);
	}

	public Entity getEntity(String name) throws EntityException {
		Entity e = entityMap.get(name);
		return e;
	}
	
	public Set<String> getRegisteredEntities() {
		return entityMap.keySet();
	}
	
}
