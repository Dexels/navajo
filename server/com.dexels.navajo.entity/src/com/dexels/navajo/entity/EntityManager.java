package com.dexels.navajo.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;

/**
 * TODO: NAVAJO CLUSTER ENABLING
 * 
 * @author arjenschoneveld / cbrouwer
 * 
 */
public class EntityManager {
    private final static Logger logger = LoggerFactory.getLogger(EntityManager.class);

    private Map<String, Entity> entityMap = new ConcurrentHashMap<String, Entity>();
    private Map<String, Map<String, Operation>> operationsMap = new ConcurrentHashMap<String, Map<String, Operation>>();

    private static EntityManager instance;
    private BundleQueue bundleQueue;

    private NavajoConfigInterface navajoConfig;

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

    public void setNavajoConfig(NavajoConfigInterface nci) {
        logger.info("Setting NavajoConfig");
        this.navajoConfig = nci;
    }

    public void clearNavajoConfig(NavajoConfigInterface nci) {
        logger.info("Clearing NavajoConfig");
        this.navajoConfig = null;
    }

    public Navajo deriveNavajoFromParameterMap(Entity entity, Map<String, String[]> parameters) {

        Navajo n = NavajoFactory.getInstance().createNavajo();
        Message m = NavajoFactory.getInstance().createMessage(n, entity.getMessageName());
        n.addMessage(m);

        for (String key : parameters.keySet()) {

            String propertyName = key;
            if (!propertyName.startsWith("/" + entity.getMessageName() + "/")) {
                propertyName = "/" + entity.getMessageName() + "/" + propertyName;
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
        if (operationsMap.get(e.getName()) == null) {
            operationsMap.put(e.getName(), new HashMap<String, Operation>());
        }
    }

    public void removeEntity(Entity e) {
        entityMap.remove(e.getName());
        operationsMap.remove(e.getName());
    }

    public Operation getOperation(String entity, String method) throws EntityException {
        if (operationsMap.get(entity) != null && operationsMap.get(entity).get(method) != null) {
            return operationsMap.get(entity).get(method);
        }
        if (getEntity(entity) == null) {
            throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Unknown entity: " + entity);
        }
        throw new EntityException(EntityException.OPERATION_NOT_SUPPORTED, "Operation " + method + " not supported for entity: "
                + entity);
    }

    public Entity getEntity(String name) {
        Entity e = entityMap.get(name);
        return e;
    }

    public List<String> getRegisteredEntities(String packagePath) {
        Set<String> unsortedResult = new HashSet<String>();
        // List<String> result = ;

        for (String entity : entityMap.keySet()) {
            // Reconstruct entity from packagePath + entityName
            String testName = packagePath + entity.substring(entity.lastIndexOf(".") + 1);
            if (testName.equals(entity)) {
                unsortedResult.add(entity);
            }
        }
        List<String> result = new ArrayList<String>(unsortedResult);
        java.util.Collections.sort(result);
        return result;
    }

    private void buildAndLoadScripts() throws Exception {
        String scriptPath = navajoConfig.getScriptPath();
        logger.info("Compiling and installing scripts in: {}", scriptPath + File.separator + "entity");
        File entityDir = new File(scriptPath + File.separator + "entity");
        if (!entityDir.exists()) {
            return;
        }

        buildAndLoadScript(entityDir);
    }

    // Can be called on file or directory. If on directory, call recursively on
    // each file
    private void buildAndLoadScript(File file) throws Exception {
        if (file.isFile()) {
            String filename = file.toString();
            if (!filename.endsWith(".xml")) {
                return;
            }
            String script = filename.substring(filename.indexOf("scripts" + File.separator + "entity"), filename.indexOf(".xml"));
            String stripped = script.substring("scripts/".length());
            stripped = stripped.replace("\\", "/");
            bundleQueue.enqueueScript(stripped, ".xml");
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                buildAndLoadScript(f);
            }
        }

    }

    public void setBundleQueue(BundleQueue queue) throws Exception {
        this.bundleQueue = queue;
    }

    public void clearBundleQueue(BundleQueue queue) {
        this.bundleQueue = null;
    }

    public void setDispatcher(DispatcherInterface di) {
    	this.dispatcher = di;
    }
    
    public void clearDispatcher(DispatcherInterface di) {
    	this.dispatcher = null;
    }
    public Navajo getEntityNavajo(String serviceName) throws InterruptedException, FatalException {
        Navajo in = NavajoFactory.getInstance().createNavajo();
        Header h = NavajoFactory.getInstance().createHeader(in, serviceName, "", "", -1);
        in.addHeader(h);

        try {
            return dispatcher.handle(in, true);
        } catch (Exception e) {
            logger.error("Exception on getting the Entity Navajo. - cannot activate {}! {} ", serviceName, e);
            throw new FatalException("Exception on getting the Entity Navajo.");
        }
    }

    public Map<String, Map<String, Operation>> getOperationsMap() {
        return operationsMap;
    }

    public Map<String, Operation> getOperations(String entityName) {
        return operationsMap.get(entityName);
    }

}
