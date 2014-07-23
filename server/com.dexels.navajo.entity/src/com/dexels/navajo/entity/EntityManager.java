package com.dexels.navajo.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.DispatcherInterface;

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
	private BundleCreator bundleCreator;
	private DispatcherInterface dispatcher;
	
	public EntityManager() {
	}

	public void activate() throws Exception {
		instance = this;
		buildAndLoadScripts();
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
				String propValue = parameters.get(key)[0];
				if (propValue.indexOf('.') > 0) {
					// Dots in property values are not supported since they can be used to indicate output format
					propValue = propValue.substring(0, propValue.indexOf('.'));
				}
				prop_copy.setUnCheckedStringAsValue(propValue);
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
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Unknown entity: " + entity);
		}
		throw new EntityException(EntityException.OPERATION_NOT_SUPPORTED, "Operation " + method + " not supported for entity: " + entity);
	}

	public Entity getEntity(String name) {
		Entity e = entityMap.get(name);
		return e;
	}
	
	public Set<String> getRegisteredEntities() {
		return entityMap.keySet();
	}
	


	

	private void buildAndLoadScripts() throws Exception {
		if (dispatcher == null || bundleCreator == null) {
			return;
		}
		
		String scriptPath =  dispatcher.getNavajoConfig().getScriptPath();
		File entityDir = new File(scriptPath + "/entity");
		for (File f : entityDir.listFiles()) {
			if (f.isFile()) {
				buildAndLoadScript(f.toString());
			}
		}
	}
	
	private void buildAndLoadScript(String fileName) throws Exception {
		List<String> success = new ArrayList<String>();
		List<String> failures = new ArrayList<String>();
		List<String> skipped = new ArrayList<String>();
		
		String script = fileName.substring(fileName.indexOf("entity"), fileName.indexOf(".xml"));
		
		bundleCreator.createBundle(script, new Date(), ".xml", failures, success, skipped, false, false);
		bundleCreator.installBundles(script, failures, success, skipped, true, ".xml");
	}


	
	public void setBundleCreator(BundleCreator bundleCreator) throws Exception {
		this.bundleCreator = bundleCreator;
		buildAndLoadScripts();
	}

	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}
	
	public void setDispatcher(DispatcherInterface dispatcher) throws Exception {
		this.dispatcher = dispatcher;
		buildAndLoadScripts();
	}

	public void clearDispatcher(DispatcherInterface dispatcher) {
		this.dispatcher = null;
	}


		
	
}
