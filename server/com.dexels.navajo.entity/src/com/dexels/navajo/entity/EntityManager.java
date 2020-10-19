package com.dexels.navajo.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;


public class EntityManager implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EntityManager.class);

    private static final String ENTITY_FOLDER = "scripts" + File.separator + "entity" + File.separator;

    private static EntityManager instance;

    private Map<String, Entity> entityMap = new ConcurrentHashMap<String, Entity>();
    private Map<String, Map<String, Operation>> operationsMap = new ConcurrentHashMap<String, Map<String, Operation>>();
    private List<Future<?>> entityCompilations = new ArrayList<Future<?>>();
    private List<String> failedCompilations = Collections.synchronizedList(new ArrayList<>());

    private NavajoConfigInterface navajoConfig;
    private DispatcherInterface dispatcher;
    private BundleCreator bundleCreator;
    private BundleContext bundleContext;
    private ExecutorService executorService;

    public void activate(BundleContext bundleContext) throws Exception {

        EntityManager.instance = this;
        this.bundleContext = bundleContext;
        this.executorService = Executors.newFixedThreadPool(1); // TODO multi threading doesn't work yet

        buildAndLoadScripts();
    }

    void activateForTest() throws Exception {

        EntityManager.instance = this;
        this.executorService = Executors.newFixedThreadPool(1);
    }

    public void deactivate() {

        entityMap.clear();
        operationsMap.clear();
        executorService.shutdown();
        EntityManager.instance = null;
    }

    public static EntityManager getInstance() {
        return EntityManager.instance;
    }

    @Override
    public void handleEvent(Event e) {

        try {
            Set<String> changedScripts = new HashSet<String>(RepositoryEventParser.filterChanged(e, ENTITY_FOLDER));
            Set<String> deletedScripts = new HashSet<String>(RepositoryEventParser.filterDeleted(e, ENTITY_FOLDER));

            for (String changed : deletedScripts) {
                fireCompileEntity(changed, true);
            }

            for (String changed : changedScripts) {
                fireCompileEntity(changed, false);
            }
        } catch (Throwable t) {
            logger.error("Error while handling event!", t);
        }
    }

    public Entity getEntity(String name) {

        if (name == null) {
            return null;
        }
        Entity e = entityMap.get(name);
        if (e == null) {
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
        throw new EntityException(EntityException.OPERATION_NOT_SUPPORTED,
                "Operation " + method + " not supported for entity: " + entity);
    }

    private void buildAndLoadScripts() throws Exception {

        if ("true".equals(System.getenv("DEVELOP_MODE"))) {
            logger.warn("Lazy compilation of entities!");
            return;
        }
        String scriptPath = navajoConfig.getScriptPath();
        logger.info("Compiling and installing scripts in: {}", scriptPath + File.separator + "entity");
        File entityDir = new File(scriptPath + File.separator + "entity");
        if (!entityDir.exists()) {
            return;
        }

        buildAndLoadScript(entityDir);
    }

    public boolean isFinishedCompiling() {

        boolean allDone = true;
        for (Future<?> entityCompilation : entityCompilations) {
            allDone &= entityCompilation.isDone(); // check if future is done
        }
        return allDone && failedCompilations.isEmpty();
    }

    // Can be called on file or directory. If on directory, call recursively on
    // each file
    private void buildAndLoadScript(File file) throws Exception {

        if (file.isFile()) {
            String filename = file.toString();
            fireCompileEntity(filename, false);
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                buildAndLoadScript(f);
            }
        }
    }

    private void fireCompileEntity(String filename, boolean onlyUnregister) {

        if (!filename.endsWith(".xml")) {
            return;
        }
        if (filename.endsWith("entitymapping.xml")) {
            return;
        }
        String script = filename.substring(filename.indexOf("scripts" + File.separator + "entity"),
                filename.indexOf(".xml"));
        String stripped = script.substring("scripts".length() + 1);
        stripped = stripped.replace("\\", "/");
        entityCompilations.add(executorService.submit(new EntityCompiler(stripped, onlyUnregister)));
    }

    private Entity checkAndLoadScript(String entityName) {

        String scriptPath = navajoConfig.getScriptPath();
        String entityPath = entityName.replace(".", File.separator);
        String entity = "entity/" + entityPath.replace("\\", "/");

        File entityFile = new File(scriptPath + File.separator + "entity", entityPath + ".xml");
        if (!entityFile.exists()) {
            logger.error("File does not exist: " + entityFile);
            return null;
        }

        try {
            List<String> success = new ArrayList<>();
            List<String> failures = new ArrayList<>();
            List<String> skipped = new ArrayList<>();
            bundleCreator.createBundle(entity, failures, success, skipped, true, false);
            bundleCreator.installBundle(entity, failures, success, skipped, true);
            if (failures.size() > 0) {
                logger.error("Failure compiling entity: " + entity);
            }
        } catch (Throwable e) {
            logger.error("Error compiling entity: " + entity, e);
        }

        return getEntityService(entityName);
    }

    @SuppressWarnings("unchecked")
    private Entity getEntityService(String entityName) {

        String filter = "(entity.name=" + entityName + ")";
        ServiceReference<Entity>[] servicereferences;
        try {
            servicereferences = (ServiceReference<Entity>[]) bundleContext.getServiceReferences(Entity.class.getName(),
                    filter);
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

    public void clearBundleCreator(BundleCreator bundleCreator) {
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

        private String entity;
        private boolean onlyUnregister;

        public EntityCompiler(String entity, boolean onlyUnregister) {
            this.entity = entity;
            this.onlyUnregister = onlyUnregister;
        }

        @Override
        public void run() {

            boolean compilationSuccess = true;
            try {
                List<String> success = new ArrayList<>();
                List<String> failures = new ArrayList<>();
                List<String> skipped = new ArrayList<>();
                bundleCreator.uninstallBundle(entity);
                if (!onlyUnregister) {
                    bundleCreator.createBundle(entity, failures, success, skipped, true, false);
                    bundleCreator.installBundle(entity, failures, success, skipped, true);
                    if (failures.size() > 0) {
                        logger.error("Failure compiling entity: " + entity);
                        compilationSuccess = false;
                    }
                    if (skipped.size() > 0) {
                        logger.error("Skipping compiling entity: " + entity);
                        compilationSuccess = false;
                    }
                }
            } catch (Throwable e) {
                logger.error("Error compiling entity: " + entity, e);
                compilationSuccess = false;
            }
            // update our administration for the health status
            if (compilationSuccess) {
                failedCompilations.remove(entity);
            } else {
                failedCompilations.add(entity);
            }
        }
    }

}
