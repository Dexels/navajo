package com.dexels.navajo.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;

public class Entity  {

	public static final String NAVAJO_URI = "navajo://";

	private Message myMessage;
	private Set<Key> myKeys = new HashSet<Key>();
	protected EntityManager entityManager = null;
	private boolean activated = false;
	private final static Logger logger = LoggerFactory.getLogger(Entity.class);
	protected String entityName = null;
	protected String messageName = null;

	// Keep track of entities that are derived from this entity.
	private Set<Entity> subEntities = new HashSet<Entity>();
	private Set<Entity> superEntities = new HashSet<Entity>();

	protected Map<String, Entity> superEntitiesMap = new HashMap<String, Entity>();
	
	protected BundleContext bundleContext;

	public Entity() {
		
	}
	
	// Non-OSGi activation
	public Entity(Message msg, EntityManager m) {
		myMessage = msg;
		entityManager = m;
	}
	

	/**
	 * When entity de-activates make sure that sub entities are deactivated.
	 * 
	 * @throws Exception
	 */
	protected synchronized void deactivate() throws EntityException {
		for ( Entity sub : subEntities ) {
			sub.deactivate();
		}
		if (!activated) {
			return;
		}
		logger.info("Deactivating entity: {}", this.getName());
		activated = false;
		// Clear all superentities since extends may have changed.
		superEntities.clear();
	}
	
	public void activate(Map<String, Object> properties)
            throws Exception {
        
        entityName = (String) properties.get("entity.name");
        messageName = (String) properties.get("entity.message");

        Navajo entityNavajo = entityManager.getEntityNavajo((String) properties.get("service.name"));
        activateMessage(entityNavajo);
        entityManager.registerEntity(this);
    }
	
	
	public synchronized void startEntity() throws EntityException {
		logger.debug("Activating entity");

		if ( activated ) {
			logger.info("Re-activate of entity {} - nothing to do", this.getName());
			return;
		}
		// Set activate flag immediately to prevent looping
		activated = true;
		
		findSuperEntities(myMessage);
		findKeys();

		for ( Entity sub : subEntities ) {
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
		if (n.getMessage(messageName) == null) {
			throw new Exception("unable to find entity in provided script!");
		}

		Message l = n.getAllMessages().iterator().next();
		setMessage(l);
		
		Operation head = new OperationComponent();
		head.setEntityName(getName());
		head.setMethod("HEAD");

		// Add operations defined in entity.
		List<Operation> allOps = n.getAllOperations();
		for (Operation o : allOps) {
			o.setEntityName(getName());
			entityManager.addOperation(o);

		}
	}


	public Set<Entity> getSubEntities() {
		return subEntities;
	}
	public Set<Entity> getSuperEntities() {
		return superEntities;
	}
	
	public void printKeys() {
		System.err.println(this +  ": In PRINTKEYS: " + myKeys.size());
		for ( Key k : myKeys ) {
			System.err.println("==================");
			k.generateRequestMessage().write(System.err);
		}
	}
	
	/**
	 * Inject a new entity message.
	 */
	public void setMessage(Message entity) throws Exception {

		// First deactivate.
		deactivate();
		myMessage = entity;
		startEntity();

	}
	
	private void addSubEntity(Entity sub) throws EntityException {
		if ( sub.equals(this) ) {
			Thread.dumpStack();
			throw new EntityException(EntityException.ENTITY_LOOP);
		}
		subEntities.add(sub);
	}
	
	public void registerSuperEntity(Entity sup) throws EntityException {
		logger.info("adding super entity: {}", sup.getName());
		if ( !containsSuperEntity(sup) ) {
			superEntities.add(sup);
			sup.addSubEntity(this);
		}
	}
	
	private boolean containsSuperEntity(Entity sup) {
		for ( Entity e : superEntities ) {
			if ( sup.getName().equals(e.getName() ) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the root parent of this entity. 
	 * If there is no root parent return entity itself.
	 * 
	 * @return
	 */
	public Entity getRootEntity() {
		Iterator<Entity> parents = getSuperEntities().iterator();
		Entity parent = this;
		while ( parents.hasNext()) {
			parent = parents.next();
		}
		return parent;
	}

	
	

	private void processExtendedEntity(Message m, String extendedEntity) throws EntityException {
		logger.info("Processing super entity {}", extendedEntity);
		Entity superEntity = getSuperEntity(extendedEntity);

		if (superEntity == null) {
			throw new EntityException(EntityException.UNKNOWN_PARENT_TYPE,
					"Could not find super entity: " + extendedEntity + " for entity: " + getName());
		}
	
		// Copy properties/messages from superEntity.
		m.merge(superEntity.getMessage().copy(m.getRootDoc()));
		registerSuperEntity(superEntity);
	}
    
    protected Entity getSuperEntity(String extendedEntity) {
        return superEntitiesMap.get(extendedEntity);
    }

	private void findSuperEntities(Message m) throws EntityException {

		if (m.isArrayMessage()) {
			m = m.getDefinitionMessage();
		}
		if ( m.getExtends() != null && ! "".equals(m.getExtends())) {	
			
			if ( m.getExtends().startsWith(NAVAJO_URI) ) {
				String ext = m.getExtends().substring(NAVAJO_URI.length());
				String [] superEntities = ext.split(",");
				for (String superEntity: superEntities) {
					superEntity = superEntity.replace("/", ".");
					processExtendedEntity(m, superEntity);
				}
			} else if (!"".equals(m.getExtends()) ){
				logger.error("Invalid extend message: {}", m.getExtends());
				throw new EntityException(EntityException.UNKNOWN_PARENT_TYPE, "Extension type not supported: " + myMessage.getExtends());
			}
		}
		
		for ( Message subm : m.getAllMessages() ) {
			findSuperEntities(subm);
		}
	}
	
	public void addSuperEntity(Entity e, Map<String,Object> settings) {
		logger.info("adding super entity: {}", e.getName());
		superEntitiesMap.put(e.getName(), e);
	}
	
	public void removeSuperEntity(Entity e, Map<String,Object> settings) {
		logger.info("removing super entity: {}", e.getName());
		superEntitiesMap.remove(e.getName());

	}
	

	private void findKeys() {
		
		myKeys.clear();
		HashMap<String,Key> foundKeys = new HashMap<String,Key>();
		
		List<Property> allProps = myMessage.getAllProperties();
		for (Message m : myMessage.getAllMessages()) {
			List<Property> newProps = m.getAllProperties();
			allProps.addAll(newProps);
		}
		int keySequence = 0;
		
		for ( Property p : allProps ) {
			if ( p.getKey() != null && p.getKey().indexOf("true") != -1 ) {
				String id = Key.getKeyId(p.getKey());
				if ( id == null ) {
					id = ""+(keySequence++);
				}
				Key key = null;
				if ( ( key = foundKeys.get(id) ) == null ) {
					key = new Key(id, this);
					foundKeys.put(id, key);
					myKeys.add(key);
				} 
				key.addProperty(p);
			}
		}

	}

	public Message getMessage() {
		return myMessage;
	}

	public String getName() {
		return entityName;
	}

	public String getMessageName() {
		return messageName;
	}


	public Key getKey(Set<Property > p) {
		for ( Key k: myKeys ) {
			if ( k.keyMatch(p) ) {
				return k;
			}
		}
		return null;
	}
	
	public Key getKey(List<Property > p) {
		return getKey(new HashSet<Property>(p));
	}

	public Key getKey(String id) {
		for ( Key k: myKeys ) {
			if ( k.getId().equals(id) ) {
				return k;
			}
		}
		return null;
	}

	public Set<Key> getKeys() {
		return myKeys;
	}

    public boolean debugInput() {
        return false;
    }

}
