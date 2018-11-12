package com.dexels.navajo.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.CompiledScriptFactory;

public class Entity {

    public static final String NAVAJO_URI = "navajo://";

    protected EntityManager entityManager = null;
    private boolean activated = false;
    private final static Logger logger = LoggerFactory.getLogger(Entity.class);
    protected String entityName = null;
    protected String messageName = null;
    private Navajo entityNavajo = null;

    // Keep track of entities that are derived from this entity.
    private Set<Entity> subEntities = new HashSet<Entity>();
    private Set<Entity> superEntities = new HashSet<Entity>();

    private Map<String, Set<Key>> myKeysMap = new HashMap<String, Set<Key>>();

    // Keep track of entity messages versions
    protected Map<String, Message> myMessageVersionMap = new HashMap<String, Message>();

    protected Map<String, Entity> superEntitiesMap = new HashMap<String, Entity>();

    protected Map<String, String> myValidations = new TreeMap<String, String>();
    protected Map<String, String> myCaching = new TreeMap<String, String>();

    private static final String VALIDATIONS = "__validations__";
    private static final String CACHING = "__caching__";
    public static final String[] VALID_CONFIGURATION_MESSAGES = { VALIDATIONS, CACHING };

    public static final String CACHING_MUST_REVALIDATE = "must-revalidate";
    public static final String CACHING_NO_CACHE = "no-cache";
    public static final String CACHING_NO_STORE = "no-store";
    public static final String CACHING_NO_TRANSFORM = "no-transform";
    public static final String CACHING_PUBLIC = "public";
    public static final String CACHING_PRIVATE = "private";
    public static final String CACHING_PROXY_REVALIDATE = "proxy-revalidate";
    public static final String CACHING_MAX_AGE = "max-age";
    public static final String CACHING_SMAX_AGE = "s-maxage";

    public static final String[] VALID_CACHING_PROPERTIES = { CACHING_MUST_REVALIDATE, CACHING_NO_CACHE, CACHING_NO_STORE,
            CACHING_NO_TRANSFORM, CACHING_PUBLIC, CACHING_PRIVATE, CACHING_PROXY_REVALIDATE, CACHING_MAX_AGE, CACHING_SMAX_AGE };

    public static final String DEFAULT_VERSION = "0";

    protected BundleContext bundleContext;

    public Entity() {

    }

    // Non-OSGi activation
    public Entity(Message msg, EntityManager m) {
        entityManager = m;
        myMessageVersionMap.put(DEFAULT_VERSION, msg);
    }

    public void activate(Map<String, Object> properties) throws Throwable {
        try {
            entityName = (String) properties.get("entity.name");
            messageName = (String) properties.get("entity.message");
            
            entityNavajo = entityManager.getEntityNavajo((String) properties.get("service.name"));
            activateMessage(entityNavajo);
            entityManager.registerEntity(this);
        } catch (Throwable t) {
            logger.error("Exception in activating entity {}!", entityName, t);
            throw t;
        }
    }

    /**
     * When entity de-activates make sure that sub entities are deactivated.
     * 
     * @throws Exception
     */
    protected synchronized void deactivate() throws EntityException {
        for (Entity sub : subEntities) {
            sub.deactivate();
        }
        if (!activated) {
            return;
        }
        logger.info("Deactivating entity: {}", this.getName());
        entityManager.removeEntity(this);
        activated = false;
        // Clear all superentities since extends may have changed.
        superEntities.clear();
    }

    public synchronized void startEntity() throws EntityException {
        logger.debug("Activating entity");

        if (activated) {
            logger.info("Re-activate of entity {}? Nothing to do", this.getName());
            return;
        }
        // Set activate flag immediately to prevent looping
        activated = true;

        // Find and register super entities
        for (Entry<String, Message> entry : myMessageVersionMap.entrySet()) {
            findSuperEntities(entry.getValue());
        }

        findKeys();

        for (Entity sub : subEntities) {
            sub.startEntity();
        }
        logger.info("Entity {} activated", this.getName());
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;

    }

    public void clearEntityManager(EntityManager em) {
        this.entityManager = null;
    }

    /* OSGi activation */
    public void activateMessage(Navajo n) throws Exception {

        if (n.getAllMessages().stream().filter(m -> m.getName().contains(messageName)).count() == 0) {
            throw new Exception("unable to find entity in provided script!");
        }

        // Message l = n.getMessage(messageName);
        setVersionMessages(n);
        deactivate();
        startEntity();
        setMyConfigurations();

        // Add operations defined in entity.
        refreshEntityManagerOperationsFromNavajo(n);
    }

    private void setMyConfigurations() {
        // Validate Configuration names
        entityNavajo.getAllMessages().stream().filter(m -> m.getName().contains("__"))
                .filter(m -> !Arrays.asList(VALID_CONFIGURATION_MESSAGES).contains(m.getName())).forEach(m -> {
                    logger.error("Invalid config message found : {}. Skipping it...", m.getName());
                });
        setMyValidations(entityNavajo.getMessage("__validations__"));
        setMyCaching(entityNavajo.getMessage("__caching__"));
        // for future configuration messages::
    }

    public void refreshEntityManagerOperationsFromNavajo(Navajo n) {
        // Add operations defined in entity.
        List<Operation> allOps = n.getAllOperations();
        for (Operation o : allOps) {
            o.setEntityName(getName());
            entityManager.addOperation(o);
        }
    }

    public void refreshEntityManagerOperations() {
        // Add operations defined in entity.
        List<Operation> allOps = entityNavajo.getAllOperations();
        for (Operation o : allOps) {
            o.setEntityName(getName());
            entityManager.addOperation(o);
        }
        entityManager.registerEntity(this);
    }

    private void setVersionMessages(Navajo n) {
        n.getAllMessages().stream().filter(m -> m.getName().contains(messageName)).forEach(m -> {
            if (!m.getName().matches("^[a-zA-Z0-9.]*$")) {
                logger.error("Unsupported version name :: {}. Please use alphanumeric, -, _ or . characters. Version was not registered",
                        m.getName());
            } else {
                Message newMessage = m.copy();
                newMessage.setName(getMessageName());
                cleanMessageProperties(newMessage);
                myMessageVersionMap.put(m.getName().contains(".") ? m.getName().split("\\.")[1] : "0", newMessage);
            }
        });
    }

    public Set<String> getMyVersionKeys() {
        return myMessageVersionMap.keySet();
    }

    public Set<Entity> getSubEntities() {
        return subEntities;
    }

    public Set<Entity> getSuperEntities() {
        return superEntities;
    }

    public Map<String, String> getMyValidations() {
        return myValidations;
    }

    public Map<String, String> getMyCaching() {
        return myCaching;
    }
    
    public void cleanMessageProperties(Message m) {
    	// String properties should have null value by default and not empty string value. 
    	m.getAllProperties().stream().filter( p -> Property.STRING_PROPERTY.equals(p.getType())).forEach(p -> {
    		p.setAnyValue(null);
    	});
    	for(Message subM : m.getAllMessages()) {
    		cleanMessageProperties(subM);
    	}
    }

    public void setMyValidations(Message validationsMessage) {

        if (validationsMessage == null) {
            return;
        }

        // First check validations message
        if (!validationsMessage.getType().equals(Message.MSG_TYPE_SIMPLE)) {
            logger.error("Could not register entity validation codes. Message must be simple.");
            return;
        }

        if (validationsMessage.getAllProperties().stream().filter(prop -> !prop.getType().equals(Property.STRING_PROPERTY)).findFirst()
                .orElse(null) != null) {
            logger.error("Could not register entity validation codes. Properties of validation message must be of type string");
            return;
        }

        validationsMessage.getAllProperties().forEach(prop -> {
            myValidations.put(prop.getName(), prop.getValue());
        });

    }

    public void setMyCaching(Message cachingMessage) {

        if (cachingMessage == null) {
            return;
        }

        // First check cachingMessage message
        if (!cachingMessage.getType().equals(Message.MSG_TYPE_SIMPLE)) {
            logger.error("Could not register entity caching directions.");
            return;
        }

        if (cachingMessage.getAllProperties().stream().filter(prop -> !prop.getType().equals(Property.STRING_PROPERTY)).findFirst()
                .orElse(null) != null) {
            logger.error("Could not register caching directions. Properties of validation message must be of type string");
            return;
        }

        cachingMessage.getAllProperties().forEach(prop -> {
            if (Arrays.asList(VALID_CACHING_PROPERTIES).contains(prop.getName())) {
                myCaching.put(prop.getName(), prop.getValue());
            } else {
                logger.error("Skiping invalid caching property: " + prop.getName());
            }
        });

    }

    public void printKeys(String version) {
        Set<Key> myKeys = myKeysMap.get(version);
        String output = "";
        for (Key k : myKeys) {
            output += ("==================\n");
            output += k.generateRequestMessage() + "\n";
        }
        logger.info("{}: PRINTKEYS ({}): {}", this, myKeys.size(), output);
    }

    /**
     * Inject a new entity message.
     */
    public void setMessage(Message entity) throws Exception {

        // First deactivate.
        deactivate();
        startEntity();

    }
    
    public void setCompiledScript(CompiledScriptFactory c1) {

    }
    public void clearCompiledScript(CompiledScriptFactory c1) {
        
    }       

    private void addSubEntity(Entity sub) throws EntityException {
        if (sub.equals(this)) {
            Thread.dumpStack();
            throw new EntityException(EntityException.ENTITY_LOOP);
        }
        subEntities.add(sub);
    }

    public void registerSuperEntity(Entity sup) throws EntityException {
        logger.info("adding super entity: {}", sup.getName());
        if (!containsSuperEntity(sup)) {
            superEntities.add(sup);
            sup.addSubEntity(this);
        }
    }

    private boolean containsSuperEntity(Entity sup) {
        for (Entity e : superEntities) {
            if (sup.getName().equals(e.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the root parent of this entity. If there is no root parent return entity itself.
     * 
     * @return
     */
    public Entity getRootEntity() {
        Iterator<Entity> parents = getSuperEntities().iterator();
        Entity parent = this;
        while (parents.hasNext()) {
            parent = parents.next();
        }
        return parent;
    }

    private void processExtendedEntity(Message m, String extendedEntity, String optionsString, String version) throws EntityException {
        logger.info("Processing super entity {} ", extendedEntity);
        Entity superEntity = getSuperEntity(extendedEntity);

        if (superEntity == null) {
            throw new EntityException(EntityException.UNKNOWN_PARENT_TYPE, "Could not find super entity: " + extendedEntity + " for entity: " + getName());
        }
        
      
        boolean ignoreKeys = false;
        if (optionsString != null) {
            String[] options = optionsString.split("&");
            for (String op : options) {
                String[] splitted = op.split("=");
                if (splitted[0].equals("ignoreKeys")) {
                    ignoreKeys = Boolean.valueOf(splitted[1]);
                }
            }
        }
        
        Message incoming = superEntity.getMessage(version).copy(m.getRootDoc());
        if (ignoreKeys) {
            for (Property p : incoming.getAllProperties()) {
                if (p.getKey() != null) {
                    p.setKey(null);
                }
            }
        }
        // Copy properties/messages from superEntity.
		// at registration time of entities apply sub type is false: otherwise, if an entity extends another entity which has a nullable submessage, this submessage gets lost, because the source entity doesn't have the submessage and it is marked as nullable.
        m.merge(incoming, true, false);
        registerSuperEntity(superEntity);
    }

    /** Follows the hierarchy to find your entity **/
    protected Entity getSuperEntity(String extendedEntity) {
        Entity superEntity = superEntitiesMap.get(extendedEntity);
        if (superEntity != null) {
            return superEntity;
        }

        for (Entity aSuper : superEntitiesMap.values()) {
            superEntity = aSuper.getSuperEntity(extendedEntity);
            if (superEntity != null) {
                return superEntity;
            }
        }
        return null;
    }

    private void findSuperEntities(Message m) throws EntityException {
        if (m.isArrayMessage()) {
            if (m.getDefinitionMessage() == null) {
                logger.warn("Array message {} in entity without definition!", m.getName());
                throw new EntityException(EntityException.PARSE_ERROR, "Definition message is mandatory in array message");
            }
            m = m.getDefinitionMessage();
        }
        if (m.getExtends() != null && !"".equals(m.getExtends())) {
            if (!(m.getExtends().startsWith(NAVAJO_URI))) {
                logger.warn("Invalid extend message: {}", m.getExtends());
                throw new EntityException(EntityException.UNKNOWN_PARENT_TYPE,
                        "Extension type not supported: " + getMessage(DEFAULT_VERSION).getExtends());
            }
            
            String ext = m.getExtends().substring(NAVAJO_URI.length());

            String version = Entity.DEFAULT_VERSION;
            if (ext.indexOf(".") != -1) {
                version = ext.substring(ext.indexOf(".") + 1, ext.indexOf("?") == -1 ? ext.length() : ext.indexOf("?"));
            }
            String rep = "." + version;
            ext = ext.replace(rep, "");

            String[] superEntities = ext.split(",");
            for (String superEntity : superEntities) {
                superEntity = superEntity.replace("/", ".");
                String options = null;
                if (superEntity.indexOf('?') > 0) {
                    options = superEntity.split("\\?")[1];
                    superEntity = superEntity.split("\\?")[0];
                }
                processExtendedEntity(m, superEntity, options, version);
            }
        }

        for (Message subm : m.getAllMessages()) {
            findSuperEntities(subm);
        }
    }

    public void addSuperEntity(Entity e, Map<String, Object> settings) {
        logger.info("adding super entity: {}", e.getName());
        superEntitiesMap.put(e.getName(), e);
    }

    public void removeSuperEntity(Entity e, Map<String, Object> settings) {
        logger.info("removing super entity: {}", e.getName());
        superEntitiesMap.remove(e.getName());

    }

    private void findKeys() {
        Set<Key> myKeys = new HashSet<Key>();
        HashMap<String, Key> foundKeys = new HashMap<String, Key>();
        List<Property> allProps = null; // = getMessage(entityVersion).getAllProperties();

        for (Entry<String, Message> entry : myMessageVersionMap.entrySet()) {
            allProps = entry.getValue().getAllProperties();
            for (Message m : entry.getValue().getAllMessages()) {
                List<Property> newProps = m.getAllProperties();
                allProps.addAll(newProps);
            }
            int keySequence = 0;
            for (Property p : allProps) {
                if (Key.isKey(p.getKey())) {
                    String id = Key.getKeyId(p.getKey());
                    if (id == null) {
                        id = "" + (keySequence++);
                    }
                    Key key = null;
                    if ((key = foundKeys.get(id)) == null) {
                        key = new Key(id, this);
                        foundKeys.put(id, key);
                        myKeys.add(key);
                    }
                    key.addProperty(p);
                }
            }

            myKeysMap.put(entry.getKey(), new HashSet<>(myKeys));

            myKeys.clear();
            foundKeys.clear();
            allProps.clear();
        }

    }

    public Message getMessage(String version) {
        return myMessageVersionMap.get(version);
    }

    public String getName() {
        return entityName;
    }

    public String getMessageName() {
        return messageName;
    }

    public Key getKey(Set<Property> p, String version) {
        Set<Key> myKeys = myKeysMap.get(version);
        for (Key k : myKeys) {
            if (k.keyMatch(p)) {
                return k;
            }
        }
        return null;
    }

    public Key getKey(List<Property> p, String version) {
        return getKey(new HashSet<Property>(p), version);
    }

    public Key getKey(String id, String version) {
        Set<Key> myKeys = myKeysMap.get(version);
        for (Key k : myKeys) {
            if (k.getId().equals(id)) {
                return k;
            }
        }
        return null;
    }

    public Set<Key> getKeys(String version) {
        return myKeysMap.get(version);
    }
    
    public Key getAutoKey(String version) {
        Set<Key> myKeys = myKeysMap.get(version);
        for (Key key : myKeys) {
            for (Property p : key.getKeyProperties()) {
                if (Key.isAutoKey(p.getKey())) {
                   return key;
                }
            }
        }
        return null;
    }

    public Set<Key> getRequiredKeys(String version) {
        Set<Key> required = new HashSet<>();
        Set<Key> myKeys = myKeysMap.get(version);
        for (Key key : myKeys) {
            Set<Property> properties = key.getKeyProperties();
            for (Property p : properties) {
                if (!Key.isOptionalKey(p.getKey())) {
                    required.add(key);
                }
            }

        }
        return required;
    }

    public Navajo getMyNavajo() {
        return entityNavajo;
    }
}
