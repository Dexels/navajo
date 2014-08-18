package com.dexels.navajo.entity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.tsl.BundleQueue;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;

/**
 * TODO: NAVAJO CLUSTER ENABLING
 * 
 * @author arjenschoneveld / cbrouwer
 * 
 */
public class EntityManager {
	private Map<String, Entity> entityMap = new ConcurrentHashMap<String, Entity>();
	private Map<String, Map<String, Operation>> operationsMap = new ConcurrentHashMap<String, Map<String, Operation>>();
	private final static Logger logger = LoggerFactory.getLogger(EntityManager.class);

	private static EntityManager instance;
	private BundleQueue bundleQueue;
	private LocalClient myClient;

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

	public Navajo deriveNavajoFromParameterMap(Entity entity, Map<String, String[]> parameters) {

		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, entity.getName());
		n.addMessage(m);

		for (String key : parameters.keySet()) {

			String propertyName = key;
			if (!propertyName.startsWith("/" + entity.getName() + "/")) {
				propertyName = "/" + entity.getName() + "/" + propertyName;
			}
			Property prop = entity.getMessage().getProperty(propertyName);
			if (prop != null) {
				Property prop_copy = prop.copy(n);
				String propValue = parameters.get(key)[0];
				if (propValue.indexOf('.') > 0) {
					// Dots in property values are not supported since they can
					// be used to indicate output format
					propValue = propValue.substring(0, propValue.indexOf('.'));
				}
				prop_copy.setUnCheckedStringAsValue(propValue);
				m.addProperty(prop_copy);
			}

		}

		return n;
	}

	public void addOperation(Operation o) {
		Map<String, Operation> operationEntry = null;
		if ((operationEntry = operationsMap.get(o.getEntityName())) == null) {
			operationEntry = new HashMap<String, Operation>();
			operationsMap.put(o.getEntityName(), operationEntry);
		}
		operationEntry.put(o.getMethod(), o);
	}

	public void removeOperation(Operation o) {
		Map<String, Operation> operationEntry = null;
		if ((operationEntry = operationsMap.get(o.getEntityName())) != null) {
			operationEntry.remove(o.getMethod());
		}
	}
	

	public void registerEntity(Entity e) {
		entityMap.put(e.getName(), e);
	}

	public void removeEntity(Entity e) {
		entityMap.remove(e.getName());
	}

	public Operation getOperation(String entity, String method) throws EntityException {
		if (operationsMap.get(entity) != null && operationsMap.get(entity).get(method) != null) {
			return operationsMap.get(entity).get(method);
		}
		if (getEntity(entity) == null) {
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Unknown entity: " + entity);
		}
		throw new EntityException(EntityException.OPERATION_NOT_SUPPORTED, "Operation " + method
				+ " not supported for entity: " + entity);
	}

	public Entity getEntity(String name) {
		Entity e = entityMap.get(name);
		return e;
	}

	public Set<String> getRegisteredEntities() {
		return entityMap.keySet();
	}

	private void buildAndLoadScripts() throws Exception {
		String scriptPath = DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
		logger.info("Compiling and installing scripts in: {}", scriptPath + "/entity");
		File entityDir = new File(scriptPath + "/entity");
		for (File f : entityDir.listFiles()) {
			if (f.isFile()) {
				buildAndLoadScript(f.toString());
			}
		}
	}

	private void buildAndLoadScript(String fileName) throws Exception {
		String script = fileName.substring(fileName.indexOf("entity"), fileName.indexOf(".xml"));
		bundleQueue.enqueueScript(script, ".xml");
	}

	public void setBundleQueue(BundleQueue queue) throws Exception {
		this.bundleQueue = queue;
	}

	public void clearBundleQueue(BundleQueue queue) {
		this.bundleQueue = null;
	}

	public void setClient(LocalClient client) {
		this.myClient = client;
	}

	public void clearClient(LocalClient client) {
		this.myClient = null;
	}
	
	public Navajo getEntityNavajo(String serviceName) throws InterruptedException, FatalException {
		Navajo in = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(in, serviceName, "", "", -1);
		in.addHeader(h);
		try {
			return myClient.call(in);
		} catch (FatalException e) {
			logger.warn("Error in getting EntityNavajo - perhaps OSGi activation problem? Trying one last time in 5 seconds...");
			// OSGi activation - dispatcher might not be fully configured yet.
			// Sleep a bit to allow for activation, then retry one last time
			Thread.sleep(5000);
		}
		return myClient.call(in);
	}

}
