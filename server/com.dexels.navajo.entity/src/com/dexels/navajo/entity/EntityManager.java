package com.dexels.navajo.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;

/**
 * TODO: NAVAJO CLUSTER ENABLING
 * 
 * @author arjenschoneveld
 *
 */
public class EntityManager {

	private Map<String,Entity> entityMap = new ConcurrentHashMap<String,Entity>();
	private Map<String,Map<String,Operation>> operationsMap = new ConcurrentHashMap<String,Map<String,Operation>>();

	private static EntityManager instance;
	
	public EntityManager() {
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
	
	public Navajo deriveNavajoFromParameterMap(Entity entity, Map<String, String []> parameters) {
		
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, entity.getName());
		n.addMessage(m);
		
		for ( String key : parameters.keySet() ) {
			
			String propertyName = key;
			if ( !propertyName.startsWith("/" + entity.getName() + "/" ) ) {
				propertyName = "/" + entity.getName() + "/" + propertyName;
			}
			Property prop = entity.getMessage().getProperty(propertyName);
			if ( prop != null ) {
				Property prop_copy = prop.copy(n);
				prop_copy.setUnCheckedStringAsValue(parameters.get(key)[0]);
				m.addProperty(prop_copy);
			}
			
		}
		
		return n;
	}
	
	public void addOperation(Operation o) {
		Map<String,Operation> operationEntry = null;
		if ( ( operationEntry = operationsMap.get(o.getEntityName())) == null ) {
			operationEntry = new HashMap<String, Operation>();
			operationsMap.put(o.getEntityName(), operationEntry);
		}
		operationEntry.put(o.getMethod(), o);
	}

	public void removeOperation(Operation o) {
		Map<String,Operation> operationEntry = null;
		if ( ( operationEntry = operationsMap.get(o.getEntityName())) != null ) {
			operationEntry.remove(o.getMethod());
		}
	}

	public Entity addEntity(Entity e) {
		entityMap.put(e.getName(), e);
		return e;
	}

	public void removeEntity(Entity e) {
		entityMap.remove(e.getName());
	}

	public Operation getOperation(String entity, String method) throws EntityException {
		if ( operationsMap.get(entity) != null && operationsMap.get(entity).get(method) != null ) {
			return operationsMap.get(entity).get(method);
		}
		if ( getEntity(entity) == null ) {
			throw new EntityException("Unknown entity: " + entity);
		}
		throw new EntityException("Operation " + method + " not supported for entity: " + entity);
	}

	public Entity getEntity(String name) {
		Entity e = entityMap.get(name);
		return e;
	}
	
	public Set<String> getRegisteredEntities() {
		return entityMap.keySet();
	}
	
}
