package com.dexels.navajo.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.mapping.compiler.meta.ExtendDependency;
import com.dexels.navajo.script.api.CompiledScriptInterface;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.script.api.FatalException;
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
    private static EntityManager instance;

    private Map<String, Entity> entityMap = new ConcurrentHashMap<String, Entity>();
    private Map<String, Map<String, Operation>> operationsMap = new ConcurrentHashMap<String, Map<String, Operation>>();

    private NavajoConfigInterface navajoConfig;
    private DispatcherInterface dispatcher;
    private BundleCreator bundleCreator;
    private BundleContext bundleContext;
    private EntityCompiler entityCompiler;

    private boolean lazy;

    public void activate(BundleContext bundleContext) throws Exception {
        instance = this;
        this.bundleContext = bundleContext;
        this.entityCompiler = new EntityCompiler();
        buildAndLoadScripts();
    }

    public void deactivate() {
        entityMap.clear();
        operationsMap.clear();
        if (entityCompiler != null) {
            entityCompiler.stop();
        }
        instance = null;
    }

    public static EntityManager getInstance() {
        return instance;
    }

    public Entity getEntity(String name) {
        if (name == null) {
            return null;
        }
        Entity e = entityMap.get(name);
        if (e == null && lazy) {
            // Try lazy compilation
            e = checkAndLoadScript(name);
        }
        return e;
    }

    public Navajo getEntityNavajo(String serviceName) throws InterruptedException, FatalException {
        Navajo in = NavajoFactory.getInstance().createNavajo();
        Header h = NavajoFactory.getInstance().createHeader(in, serviceName, "_internal_", "", -1);
        in.addHeader(h);

        try {
            return dispatcher.handle(in, true);
        } catch (Exception e) {
            logger.error("Exception on getting the Entity Navajo. - cannot activate {}! {} ", serviceName, e);
            throw new FatalException("Exception on getting the Entity Navajo.");
        }
    }

    public Set<String> getRegisteredEntities(String packagePath) {
        Set<String> unsortedResult = new HashSet<String>();
        // List<String> result = ;

        for (String entity : entityMap.keySet()) {
            // Reconstruct entity from packagePath + entityName
            String testName = packagePath + "." + entity.substring(entity.lastIndexOf(".") + 1);
            if (testName.equals(entity)) {
                unsortedResult.add(entity);
            }
        }
        Set<String> treeSet = new TreeSet<String>();
        treeSet.addAll(unsortedResult);
        return treeSet;
    }

    public Map<String, Map<String, Operation>> getOperationsMap() {
        return operationsMap;
    }

    public Map<String, Operation> getOperations(String entityName) {
        return operationsMap.get(entityName);
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
        throw new EntityException(EntityException.OPERATION_NOT_SUPPORTED, "Operation " + method + " not supported for entity: " + entity);
    }

    private void buildAndLoadScripts() throws Exception {
        if ("true".equals(System.getenv("DEVELOP_MODE"))) {
            logger.warn("Lazy compliation of entities!");
            this.lazy = true;
            return;
        }
        String scriptPath = navajoConfig.getScriptPath();
        logger.info("Compiling and installing scripts in: {}", scriptPath + File.separator + "entity");
        File entityDir = new File(scriptPath + File.separator + "entity");
        if (!entityDir.exists()) {
            return;
        }

        buildAndLoadScript(entityDir);
        // Start compilation in a separate thread to not hold up the activator
        new Thread(entityCompiler).start();
    }
    
    public boolean isFinishedCompiling() {
        return !entityCompiler.isRunning();
    }

    // Can be called on file or directory. If on directory, call recursively on
    // each file
    private void buildAndLoadScript(File file) throws Exception {
        if (file.isFile()) {
            String filename = file.toString();
            if (!filename.endsWith(".xml")) {
                return;
            }
            if (filename.endsWith("entitymapping.xml")) {
                return;
            }
            String script = filename.substring(filename.indexOf("scripts" + File.separator + "entity"), filename.indexOf(".xml"));
            String stripped = script.substring("scripts".length() + 1);
            stripped = stripped.replace("\\", "/");
            entityCompiler.addEntityToCompile(stripped);;
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                buildAndLoadScript(f);
            }
        }
    }

    private Entity checkAndLoadScript(String entityName) {
        String scriptPath = navajoConfig.getScriptPath();
        String entityPath = entityName.replace(".", File.separator);
        String rpcName = "entity/" + entityName.replace('.', '/');

        File entityFile = new File(scriptPath + File.separator + "entity", entityPath + ".xml");
        if (!entityFile.exists()) {
            return null;
        }

        logger.info("getOnDemand of entity {}", entityPath);
        try {
            CompiledScriptInterface onDemandScriptService = bundleCreator.getOnDemandScriptService(rpcName, null, false, null);

            // Also compile all dependencies of this entity
            for (int i = 0; i < onDemandScriptService.getDependencies().length; i++) {
                Dependency d = onDemandScriptService.getDependencies()[i];
                if (d instanceof ExtendDependency) {
                    String extendedEntity = d.getId().replaceAll("/", ".");
                    checkAndLoadScript(extendedEntity);
                }
            }
        } catch (Exception e) {
            logger.error("Exception on getting compiled service {}", rpcName, e);
            return null;
        }

        return getEntityService(entityName);
    }

    @SuppressWarnings("unchecked")
    private Entity getEntityService(String entityName) {
        String filter = "(entity.name=" + entityName + ")";
        ServiceReference<Entity>[] servicereferences;
        try {
            servicereferences = (ServiceReference<Entity>[]) bundleContext.getServiceReferences(Entity.class.getName(), filter);
            if (servicereferences != null) {
                // First try to find one that matches our tenant
                for (ServiceReference<Entity> srinstance : servicereferences) {
                    Entity entityService = bundleContext.getService(srinstance);
                    if (entityService == null) {
                        logger.warn("Script with filter: " + filter + " found, but could not be resolved.");
                        return null;
                    }
                    return entityService;

                }
            }
        } catch (Exception e) {
            logger.error("Error resolving script service for: {} ", entityName, e);
        }
        return null;
    }

    public void setBundleCreator(BundleCreator bundleCreator) throws Exception {
        this.bundleCreator = bundleCreator;
    }

    public void clearBundleQueue(BundleCreator bundleCreator) {
        this.bundleCreator = null;
    }

    public void setDispatcher(DispatcherInterface di) {
        this.dispatcher = di;
    }

    public void clearDispatcher(DispatcherInterface di) {
        this.dispatcher = null;
    }

    public void setNavajoConfig(NavajoConfigInterface nci) {
        logger.info("Setting NavajoConfig");
        this.navajoConfig = nci;
    }

    public void clearNavajoConfig(NavajoConfigInterface nci) {
        logger.info("Clearing NavajoConfig");
        this.navajoConfig = null;
    }

    private class EntityCompiler implements Runnable {
        private List<String> success = new ArrayList<String>();
        private List<String> failures = new ArrayList<String>();
        private List<String> skipped = new ArrayList<String>();
        
        private Set<String> entitiesToCompile = new HashSet<>();
        private boolean running = false;
        
        protected void addEntityToCompile(String entity) {
            entitiesToCompile.add(entity);
        }
        
        protected boolean isRunning() {
            return running;
        }
        
        protected void stop() {
            running = false;
        }
        
        @Override
        public void run() {
            running = true;
            while (running == true) {
                for (String script : entitiesToCompile) {
                    try {
                        bundleCreator.createBundle(script, failures, success, skipped, true, false, null);
                        bundleCreator.installBundle(script, failures, success, skipped, true, null);
                        if (!skipped.isEmpty()) {
                            logger.info("Script compilation skipped: " + script);
                        }
                        if (!failures.isEmpty()) {
                            logger.info("Script compilation failed: " + script);
                        }

                    } catch (Throwable e) {
                        logger.error("Error: ", e);
                    }
                }
                entitiesToCompile.clear();
                running = false;
            }
        }
    }
}
