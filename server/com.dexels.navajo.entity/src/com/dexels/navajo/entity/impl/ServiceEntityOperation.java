package com.dexels.navajo.entity.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Coordinate;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.EntityOperation;
import com.dexels.navajo.entity.Key;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;

public class ServiceEntityOperation implements EntityOperation {
	private final static Logger logger = LoggerFactory.getLogger(ServiceEntityOperation.class);

	private EntityManager manager;
	private DispatcherInterface dispatcher;
	private Operation myOperation;
	private Entity myEntity;
	private Key myKey;
	private Set<String> validMessages = new HashSet<String>(Arrays.asList("__parms__", "__globals__", "__aaa__"));
    private String entityVersion = Entity.DEFAULT_VERSION;

    public ServiceEntityOperation(EntityManager m, Operation o, String version) throws EntityException {
		this.dispatcher = DispatcherFactory.getInstance();
        this.entityVersion = version;
		setup(m, o);
	}

    public ServiceEntityOperation(EntityManager m, DispatcherInterface c, Operation o, String version) throws EntityException {
		this.dispatcher = c;
        this.entityVersion = version;
		setup(m, o);
	}

    private void setup(EntityManager m, Operation o) throws EntityException {
		this.manager = m;
		this.myOperation = o;
		this.myEntity = manager.getEntity(myOperation.getEntityName());

		if (myEntity == null) {
			logger.error("ServiceEntityOperation could not find requested entity!");
			throw new EntityException(EntityException.ENTITY_NOT_FOUND,
					"Could not find entity: " + myOperation.getEntityName());
		}

		this.validMessages.add(myEntity.getMessageName());
		if (myOperation.getExtraMessage() != null) {
			validMessages.add(myOperation.getExtraMessage().getName());
		}
	}

	public ServiceEntityOperation cloneServiceEntityOperation(Operation o) throws EntityException {
		if (dispatcher != null) {
            return new ServiceEntityOperation(manager, dispatcher, o, entityVersion);
		} else {
			return null;
		}
	}

	public Key getMyKey() {
		return myKey;
	}

	public DispatcherInterface getDispatcher() {
		return dispatcher;
	}

	public Operation getMyOperation() {
		return myOperation;
	}

	public void setMyOperation(Operation o) {
		this.myOperation = o;
	}

	public Entity getMyEntity() {
		return myEntity;
	}

	/**
	 * Checks whether input message contains a certain property. If
	 * ignoreNonExistent is set it only checks for null valued properties, else it
	 * checks both for existence and null values.
	 * 
	 * @param input
	 * @param propertyName
	 * @param ignoreNonExistent
	 * @return
	 */
	private boolean missingProperty(Message input, String propertyName, boolean ignoreNonExistent) {
		if (ignoreNonExistent) {
			return (input.getProperty(propertyName) != null && input.getProperty(propertyName).getValue() == null);
		} else {
			return (input.getProperty(propertyName) == null || input.getProperty(propertyName).getValue() == null);
		}
	}

	/**
	 * Following method checks for required properties. It checks the existence of
	 * the properties and non-null values.
	 * 
	 * @param message
	 * @param entityMessage
	 * @param ignoreNonExistent,
	 *            set to true for checking updates, set to false for inserts, set to
	 *            true for updates (modifications)
	 * @return
	 * @throws EntityException
	 */
	private List<String> checkRequired(Message message, Message entityMessage, boolean ignoreNonExistent)
			throws EntityException {
		List<Property> entityProperties = entityMessage.getAllProperties();
		List<String> missingProperties = new ArrayList<String>();
		for (Property ep : entityProperties) {
			// Check non-auto, non-optional key properties.
			if (Key.isKey(ep.getKey()) && !Key.isAutoKey(ep.getKey()) && !Key.isOptionalKey(ep.getKey())
					&& missingProperty(message, ep.getFullPropertyName(), ignoreNonExistent)) {
				missingProperties.add(ep.getFullPropertyName() + ":key");
			} // Check required non-key properties.
			else if (Key.isRequiredKey(ep.getKey())
					&& missingProperty(message, ep.getFullPropertyName(), ignoreNonExistent)) {
				missingProperties.add(ep.getFullPropertyName());
			}
		}
		return missingProperties;
	}

	/**
	 * Following method checks whether the types in the input message match the
	 * types in the entity message
	 * 
	 * @param message
	 * @param entityMessage
	 * @param method
	 * @param invalidProperties
	 * @throws EntityException
	 */
	private void checkTypes(Message message, Message entityMessage, String method, List<String> invalidProperties)
			throws EntityException {

		Iterator<Property> inputProperties = message.getAllProperties().iterator();
		while (inputProperties.hasNext()) {
			Property inputP = inputProperties.next();
			Property entityP = entityMessage.getProperty(inputP.getName());
			if (entityP != null && (entityP.getBind() == null || entityP.getBind().equals(""))) {
				if (entityP.getType().equals(Property.SELECTION_PROPERTY)) {
					if (inputP.getValue() != null && !inputP.getValue().equals("")) {
						boolean found = false;
						for (Selection entityS : entityP.getAllSelections()) {
							if (entityS.getValue().equals(inputP.getValue())) {
								found = true;
							}
						}
						if (!found) {
							invalidProperties.add(inputP.getFullPropertyName() + ":" + inputP.getValue() + "<-"
									+ "invalid selection");
							// throw new EntityException("Invalid selection option encountered in " + method
							// + " operation for entityservice {" + message.getName() + "}: selection
							// property [" + inputP.getFullPropertyName() + "]: " + inputP.getValue());
						}
					}
				} else if (!inputP.getType().equals(entityP.getType())) {
					invalidProperties
							.add(inputP.getFullPropertyName() + ":" + inputP.getType() + "<-" + entityP.getType());
					// throw new EntityException("Invalid type encountered in " + method + "
					// operation for entityservice {" + message.getName() + "}: [" +
					// inputP.getFullPropertyName() + "]: " + inputP.getType() + ", expected: " +
					// entityP.getType());
				} else if (entityP.getType().equals(Property.DATE_PROPERTY) && inputP.getValue() != null
						&& !"".equals(inputP.getValue())) {
					// BasePropertyImpl does not throw exception on invalid date format. Thus we
					// perform an extra check here on that
					if (!(inputP.getTypedValue() instanceof Date)) {
						invalidProperties
								.add(inputP.getFullPropertyName() + ":" + inputP.getType() + "<-" + entityP.getType());

					}
				}
			}
		}
		Iterator<Message> inputMessages = message.getAllMessages().iterator();
		while (inputMessages.hasNext()) {
			Message inputM = inputMessages.next();
			Message entityM = entityMessage.getMessage(inputM.getName());
			if (inputM.isArrayMessage() && entityM.isArrayMessage() && entityM.getDefinitionMessage() != null) {
				Iterator<Message> children = inputM.getElements().iterator();
				while (children.hasNext()) {
					checkTypes(children.next(), entityM.getDefinitionMessage(), method, invalidProperties);
				}
			}
		}
	}

	private HashMap<String, Navajo> getInputNavajosForReferencedEntities(Navajo n) {
        List<Property> allProps = myEntity.getMessage(entityVersion).getAllProperties();
		HashMap<String, Navajo> referencedEntities = new HashMap<String, Navajo>();
		for (Property p : allProps) {
			// Fetch property from entity definition
            Property e_p = myEntity.getMessage(entityVersion).getProperty(p.getName());
			if (e_p.getReference() != null && e_p.getDirection().indexOf("in") != -1) {
				String entityName = getEntityFromReference(e_p.getReference());
				if (!referencedEntities.containsKey(entityName)) {
					Navajo input = createInputNavajoForReferencedEntity(entityName, n);
					Header h = NavajoFactory.getInstance().createHeader(input, "", n.getHeader().getRPCUser(),
							n.getHeader().getRPCPassword(), (long) -1);
					input.addHeader(h);
					referencedEntities.put(entityName, input);
				}
			}
		}
		return referencedEntities;
	}

	/**
     * Clean a Navajo document: only valid messages are kept, missing properties and
     * messages are added, and filter properties on the right direction
     * 
     * @param entityVersion2
     * *
     * 
     * @return
     */
    private void clean(Navajo n, String method, boolean resolveLinks, boolean merge, String entityVersion) {

		List<Message> all = n.getAllMessages();
		for (Message m : all) {
			if (!validMessages.contains(m.getName())) {
				n.removeMessage(m);
			}
		}
		if (n.getMessage(myEntity.getMessageName()) != null) {
			if (merge) {
                n.getMessage(myEntity.getMessageName()).merge(myEntity.getMessage(entityVersion), true);
			}
            n.getMessage(myEntity.getMessageName()).maskMessage(myEntity.getMessage(entityVersion), method);

			if (method.equals("request")) {
				// Do we have a auto key as input, and an extra Mongo message?
				// If so, replace the auto key with a _id property
				for (Property p : n.getMessage(myEntity.getMessageName()).getAllProperties()) {
                    Property entityp = myEntity.getMessage(entityVersion).getProperty(p.getFullPropertyName());
					if (entityp.getKey() != null && Key.isAutoKey(entityp.getKey()) && hasExtraMessageMongo()) {
						Message parentMsg = p.getParentMessage();
						parentMsg.removeProperty(p);

						if (!p.getValue().equals("")) {
							p.setName("_id");
							parentMsg.addProperty(p);
						}
					}
				}
			}

			if (resolveLinks) {
				// myEntity.getMessage().write(System.err);

				// Add properties that refer to other entities.
                List<Property> allProps = myEntity.getMessage(entityVersion).getAllProperties();
				HashMap<String, Navajo> referencedEntities = getInputNavajosForReferencedEntities(n);
				// Populate property values that bind to external entities.
				HashMap<String, Navajo> cachedEntities = new HashMap<String, Navajo>();
				for (Property p : allProps) {
					if (p.getBind() != null) {
						String entityName = getEntityFromReference(p.getBind());
						String propertyName = getPropertyFromReference(p.getBind());
						Navajo entityObj = null;
						if (!cachedEntities.containsKey(entityName)) {
							try {
								Operation o = EntityManager.getInstance().getOperation(entityName, "GET");
								ServiceEntityOperation seo = new ServiceEntityOperation(EntityManager.getInstance(),
                                        DispatcherFactory.getInstance(), o, entityVersion);
								entityObj = seo.perform(referencedEntities.get(entityName));
								cachedEntities.put(entityName, entityObj);
							} catch (Exception e) {
								logger.error("Error: ", e);
							}
						} else {
							entityObj = cachedEntities.get(entityName);
						}
						Object propVal = entityObj.getMessage(entityName).getProperty(propertyName).getTypedValue();
						n.getMessage(myEntity.getName()).getProperty(p.getName()).setAnyValue(propVal, true);

					}
				}
			}
		}
	}

	private Navajo createInputNavajoForReferencedEntity(String entityName, Navajo input) {

		Entity entityObj = EntityManager.getInstance().getEntity(entityName);

		List<Property> props = input.getMessage(myEntity.getMessageName()).getAllProperties();
		for (Message m : input.getMessage(myEntity.getMessageName()).getAllMessages()) {
			props.addAll(m.getAllProperties());
		}

        Key entityKey = entityObj.getKey(props, entityVersion);
		Navajo inputC = input.copy();
		// Correct message name of copy of input.
		inputC.getMessage(myEntity.getMessageName()).setName(entityName);
		Navajo n = entityKey.generateRequestMessage(inputC);

		return n;

	}

	private String getEntityFromReference(String ref) {
		String n = ref.replaceAll("navajo://", "");
		n = n.substring(0, n.indexOf("/"));
		return n;
	}

	private String getPropertyFromReference(String ref) {
		String n = ref.substring(ref.lastIndexOf("/") + 1);
		return n;
	}

	private String listToString(List<String> l) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (String s : l) {
			if (index == 0) {
				sb.append("[" + s + "]");
			} else {
				sb.append(", [" + s + "]");
			}
			index++;
		}
		return sb.toString();
	}

	@Override
    public Navajo perform(Navajo input) throws EntityException {
		if (myOperation.getMethod().equals(Operation.HEAD)) {
			Navajo out = NavajoFactory.getInstance().createNavajo();
            out.addMessage(myEntity.getMessage(entityVersion).copy(out));
			return out;
		}

		Message inputEntity = input.getMessage(myEntity.getMessageName());

		if (inputEntity == null) {
			throw new EntityException(EntityException.BAD_REQUEST, "No valid entity found.");
		}

		List<Property> props = inputEntity.getAllProperties();
		for (Message m : inputEntity.getAllMessages()) {
			props.addAll(m.getAllProperties());
		}

        // Check if version exists

        if (!myEntity.getMyVersionKeys().contains(entityVersion)) {
            logger.error("Request on unknown entity {} version {}", myEntity.getName(), entityVersion);
            throw new EntityException(EntityException.UNKNOWN_VERSION);
        }

        myKey = myEntity.getKey(props, entityVersion);
		if (myKey == null) {
			// Check for _id property. If _id is present it is good as a key.
			// It's also possible our entity has no keys defined. In that case accept input
            if (inputEntity.getProperty("_id") == null && myEntity.getRequiredKeys(entityVersion).size() > 0) {
				throw new EntityException(EntityException.MISSING_ID, "Input is invalid: no valid entity key found.");
			} else {
				myKey = new Key("", myEntity);
			}
		}

		// Merge input, except when modifying existing entry to prevent clearing
		// existing fields
		boolean merge = true;
		if (myOperation.getMethod().equals(Operation.PUT)) {
			// Don't merge input on PUT (update) operation to prevent overwriting missing
			// attributes that
			// are already present in the backend with empty values
			merge = false;
		}
        clean(input, "request", false, merge, entityVersion);

        // Add the entity input message
        Message entityInfo = NavajoFactory.getInstance().createMessage(input, "__entity__");
        Property entityVersion = NavajoFactory.getInstance().createProperty(input, "version", "string", myEntity.getMyVersion(), 0, "",
                Property.DIR_OUT);
        entityInfo.addProperty(entityVersion);
        input.addMessage(entityInfo);

		// Perform validation method if defined
		if ((myOperation.getValidationService()) != null) {
			Navajo validationResult = callEntityValidationService(input);

			Message validationErrors;
			if ((validationErrors = validationResult.getMessage("ConditionErrors")) != null) {
				throw new EntityException(EntityException.VALIDATION_ERROR,
						validationErrors.getMessage(0).getProperty("Id").toString(), validationResult);
			}
		}

		// Now we are ready to handle the operations
		String method = myOperation.getMethod();
		if (method.equals(Operation.GET)) {
            return handleGet(input, inputEntity);
		}
		if (method.equals(Operation.DELETE)) {
			return handleDelete(input, inputEntity);
		}
		if (method.equals(Operation.PUT) || method.equals(Operation.POST)) {
			return handlePutPost(input, inputEntity);
		}

		throw new EntityException(EntityException.OPERATION_NOT_SUPPORTED);
	}

	private Navajo handlePutPost(Navajo input, Message inputEntity) throws EntityException {
		// PUT == upsert: update or insert. Must be idempotent!
		// POST == create

		// property type validation
		// validateInputMessage(input.getMessage(myEntity.getMessage().getName()));

		if (myOperation.getMethod().equals(Operation.PUT)) {
			performPutValidation(input, inputEntity);
		} else {
			performPostValidation(input, inputEntity);
		}

		// Check property types.
		List<String> invalidProperties = new ArrayList<String>();
        checkTypes(inputEntity, myEntity.getMessage(entityVersion), myOperation.getMethod(), invalidProperties);
		if (invalidProperties.size() > 0) {
			throw new EntityException(EntityException.BAD_REQUEST,
					"Could not perform operation, invalid property types: " + listToString(invalidProperties));
		}

		Navajo result = callEntityService(input);

		// Update referenced entities as well...
		// updateReferencedEntities(input);

		// If the entity has a mongo backend, we added the _id property.
		// Remove this now to ensure a proper mask operation can take place.
		if (hasExtraMessageMongo()) {
            Property id = myEntity.getMessage(entityVersion).getProperty("_id");
			// Only remove if it's not a key
			if (id != null && id.getKey() == null) {
                myEntity.getMessage(entityVersion).removeProperty(id);
			}
		}

		// After a POST or PUT, return the full new object resulting from the previous
		// operation
		// effectively this is a GET. However, if this fails (e.g. no GET operation is
		// defined
		// for this entity), we return the original result
		// If the output contains the entity name, prefer this!
		if (result.getMessage(myEntity.getMessageName()) != null) {
			return result;
		}
		try {
			return getEntity(input);
		} catch (EntityException e) {
			return result;
		}
	}

	private void performPutValidation(Navajo input, Message inputEntity) throws EntityException {

		Navajo currentEntity = getCurrentEntity(input);
		validateEtag(input, inputEntity, currentEntity);

		if (hasExtraMessageMongo()) {
			if (currentEntity == null || currentEntity.getMessage(myEntity.getMessageName()) == null) {
				// TODO: Monogo backend does not support PUT for inserts. Thus we give an
				// exception
				throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Entity not found - use POST for insert");
			}
		}
	}

	private void performPostValidation(Navajo input, Message inputEntity) throws EntityException {
		// Duplicate entry check.
		// Required properties check.
        List<String> missing = checkRequired(inputEntity, myEntity.getMessage(entityVersion), false);
		if (missing.size() > 0) {
			throw new EntityException(EntityException.BAD_REQUEST,
					"Could not perform insert, missing required properties: " + listToString(missing));
		}

		if (getCurrentEntity(input) != null) {
			// TODO: Support POST for updates
			// Right now we cannot detect whether the POST will cause a conflict (e.g.
			// a duplicate key) or not. Therefore simply give CONFLICT error right away
			throw new EntityException(EntityException.CONFLICT, "Could not perform insert, duplicate entry");
		}
	}

	private Navajo handleGet(Navajo input, Message inputEntity) throws EntityException {

		// property type validation
        validateInputMessage(input.getMessage(myEntity.getMessage(entityVersion).getName()));

		String postedEtag = inputEntity.getEtag();
		Navajo result = callEntityService(input);

		if (postedEtag != null) {
			if (result != null && result.getMessage(myEntity.getMessageName()) != null) {
				if (postedEtag.equals(result.getMessage(myEntity.getMessageName()).generateEtag())) {
					throw new EntityException(EntityException.NOT_MODIFIED);
				}
			}
		}

		return result;
	}

	private void validateInputMessage(Message message) throws EntityException {
		for (Property x : message.getAllProperties()) {
			// Check all non "" (empty) values.
			// surround with try catch so that other errors are avoided ::
            if (x != null && x.getValue() != null && !"".equals(x.getValue())) {
                // If it's null, it could not be converted, which means that wrong input was
                // provided
                if (x.getTypedValue() == null) {
                    throw new EntityException(EntityException.BAD_REQUEST,
                            "Invalid input value for " + x.getName() + ". Please provide a valid " + x.getType());
                }
                // If it's not null, we need to compare the returned type with the type that the
                // message demands.
                if (x.getTypedValue() != null) {
                    // Special types check

                    // Coordinate input check
                    if (x.getType().equalsIgnoreCase(Property.COORDINATE_PROPERTY) && !(x.getTypedValue() instanceof Coordinate)) {
                        throw new EntityException(EntityException.BAD_REQUEST,
                                "Invalid input value for " + x.getName() + ". Expected 'x,y' where x in [-180,180], y in [-90,90]");
                    }

                    // Date check
                    if (x.getType().equalsIgnoreCase(Property.DATE_PROPERTY) && !(x.getTypedValue() instanceof Date)) {
						throw new EntityException(EntityException.BAD_REQUEST,
                                "Invalid input value for " + x.getName() + ".  Expected: " + x.getType() + " Provided: "
                                        + x.getTypedValue().getClass().toString());
                    }

                    // True/False check
                    // We check whether the parsed value is the same as the one that returns from
                    // the function getTypedValue (true or false string values)
                    if (x.getType().equalsIgnoreCase(Property.BOOLEAN_PROPERTY)
                            && (x.getTypedValue() instanceof Boolean)
                            && !x.getTypedValue().toString().equalsIgnoreCase(x.getValue())) {
                        throw new EntityException(EntityException.BAD_REQUEST,
                                "Invalid input value for " + x.getName() + ". Expected true or false using lower case");
                    }
                    

				}
            }
		}
	}

	private Navajo handleDelete(Navajo input, Message inputEntity) throws EntityException {

		// property type validation
		// validateInputMessage(input.getMessage(myEntity.getMessage().getName()));

		validateEtag(input, inputEntity, null);

		Navajo currentEntity = getCurrentEntity(input);
		if (currentEntity != null && currentEntity.getMessage(myEntity.getMessageName()) == null) {
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Could not peform delete, entity not found");
		}

		return callEntityService(input);
	}

	private void validateEtag(Navajo input, Message inputEntity, Navajo current) throws EntityException {
		String postedEtag;
		// Check Etag.
		if ((postedEtag = inputEntity.getEtag()) != null) {
			if (current == null) {
				current = getCurrentEntity(input);
			}
			if (current == null) {
				return;
			}
			Message entityMessage = current.getMessage(myEntity.getMessageName());
			if (entityMessage == null) {
				return;
			}
			if (!(postedEtag.equals(entityMessage.generateEtag()))) {
				throw new EntityException(EntityException.ETAG_ERROR);
			}
		}
	}

	/**
	 * Checks whether this entity is backed by Mongo. If this is true it checks for
	 * existence of _id property. If _id is not present or empty the entity is
	 * queried and the _id is put into the original input.
	 * 
	 * @param inputEntity
	 * @param currentEntity
	 *            (optionally pass current entity instance)
	 * @param k
	 * @throws EntityException
	 */

	private boolean hasExtraMessageMongo() {
		return myOperation.getExtraMessage() != null && myOperation.getExtraMessage().getName().equals("__Mongo__");
	}

	private Navajo getEntity(Navajo input) throws EntityException {
		Navajo result = getCurrentEntity(input);
		if (result == null) {
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, myEntity.getName());
		}

		return result;
	}

	/**
	 * Perform a GET operation on the entity instance based on the input Navajo.
	 * Check the result for errors (Server errors or authentication).
	 * 
	 * @param input
	 * @return Returns the result of the GET operation. If no message named
	 *         entity.getName() is found, return null.
	 * @throws EntityException
	 *             Throws EntityException when the GET operation results in a error.
	 */
	private Navajo getCurrentEntity(Navajo input) throws EntityException {
		Operation getop = null;
		try {
			getop = manager.getOperation(myEntity.getName(), Operation.GET);
		} catch (EntityException e) {
			if (e.getCode() == EntityException.OPERATION_NOT_SUPPORTED) {
				// No GET operation defined - no biggie
				return null;
			}
			// Other exceptions are passed on
			throw e;
		}
		getop.setTenant(myOperation.getTenant());
		ServiceEntityOperation get = this.cloneServiceEntityOperation(getop);
		Navajo request = myKey.generateRequestMessage(input);
		if (input.getMessage("__parms__") != null) {
			request.addMessage(input.getMessage("__parms__").copy(request));
		}
		if (input.getMessage("__aaa__") != null) {
			request.addMessage(input.getMessage("__aaa__").copy(request));
		}
		if (getop.getExtraMessage() != null) {
			request.addMessage(getop.getExtraMessage().copy(request));
		}
		prepareServiceRequestHeader(request, request, getop);
		Navajo result = get.commitOperation(request, getop);
		if (result.getMessage("error") != null) {
			throw new EntityException(EntityException.SERVER_ERROR);
		}
		if (result.getMessage("AuthenticationError") != null) {
			throw new EntityException(EntityException.UNAUTHORIZED);
		}

		if (result.getMessage(myEntity.getMessageName()) == null) {
			return null;
		}
		Property id = null;
		if (hasExtraMessageMongo()) {
			id = result.getProperty(myEntity.getMessageName() + "/_id");
		}

        clean(result, "response", true, true, entityVersion);

		if (id != null && id.getValue() != null) {
			input.getMessage(myEntity.getMessageName()).addProperty(id.copy(input));
			input.getRootMessage().addProperty(id.copy(input));

            if (myEntity.getAutoKey(entityVersion) != null) {
                for (Property p : myEntity.getAutoKey(entityVersion).getKeyProperties()) {
					result.getProperty(p.getFullPropertyName()).setAnyValue(id.getValue());
				}
			}
		}
		return result;
	}

	private Navajo callEntityValidationService(Navajo input) throws EntityException {
		Navajo request = input.copy();
		if (myOperation.getExtraMessage() != null) {
			input.addMessage(myOperation.getExtraMessage().copy(request));
		}
		prepareValidationServiceRequestHeader(request, request, myOperation);

		// No transaction support yet
		Navajo result = commitOperation(request, myOperation);

		if (result.getMessage("error") != null) {
			throw new EntityException(EntityException.SERVER_ERROR);
		}
		return result;
	}

	/**
	 * If This method is called in the context of a Transaction, depending on the
	 * isolation level use real or proxy service calls.
	 * 
	 * @param input
	 * @param service
	 * @return
	 * @throws EntityException
	 */
	private Navajo callEntityService(Navajo input) throws EntityException {
		if (myOperation.getExtraMessage() != null) {
			input.addMessage(myOperation.getExtraMessage().copy(input));
		}
		prepareServiceRequestHeader(input, input, myOperation);

		// No transaction support yet
		Navajo result = commitOperation(input, myOperation);
		if (result != null) {
			if (result.getMessage("error") != null) {
				throw new EntityException(EntityException.SERVER_ERROR,
						result.getMessage("error").getProperty("message").getValue());
			}
			if (result.getMessage("AuthenticationError") != null) {
				throw new EntityException(EntityException.UNAUTHORIZED);
			}
			if (result.getMessage("AuthorizationError") != null) {
				throw new EntityException(EntityException.UNAUTHORIZED);
			}

			Message validationErrors;
			if ((validationErrors = result.getMessage("ConditionErrors")) != null) {
				throw new EntityException(EntityException.VALIDATION_ERROR,
						validationErrors.getMessage(0).getProperty("Id").toString(), result);
			}

		}
		// Check for auto key and bind _id value to it
		Property id = null;
		if (hasExtraMessageMongo()) {
			id = result.getProperty(myEntity.getMessageName() + "/_id");
		}

        clean(result, "response", true, true, entityVersion);

		if (id != null && id.getValue() != null) {
            Key autoKey = myEntity.getAutoKey(entityVersion);
			if (autoKey != null) {
				for (Property p : autoKey.getKeyProperties()) {
					result.getProperty(p.getFullPropertyName()).setAnyValue(id.getValue());
				}
			}
		}

		return result;

	}

	/**
	 * Removes all the properties with bind atrribute set.
	 * 
	 * @param input
	 * @return
	 */
	private Navajo removeBindProperties(Navajo input) {
		Navajo result = input.copy();
		List<Property> list = result.getMessage(myEntity.getMessageName()).getAllProperties();
		for (Property p : list) {
			// Check if this is a bind property.
            Property e_p = myEntity.getMessage(entityVersion).getProperty(p.getName());
			if (e_p != null && e_p.getBind() != null && !e_p.getBind().equals("")) {
				result.getMessage(myEntity.getMessageName()).removeProperty(p);
			}
		}
		return result;
	}

	/**
	 * Actually performs a Navajo service call. Either of the three following
	 * methods is used: (1) Use an EntityMap (derived from NavajoMap) if set (2) Use
	 * Dispatcher if set (3) Use NavajoClient if set
	 * 
	 * @param input,
	 *            the request Navajo
	 * @param block,
	 *            for EntityMap only: perform service call blocking (true) or
	 *            non-blocking (false)
	 * @return
	 * @throws EntityException
	 */
	private Navajo commitOperation(Navajo input, Operation o) throws EntityException {
		// Remove bind properties, these properties do not belong to this entity
		Navajo cleaned = removeBindProperties(input);
		try {
			if (dispatcher != null) {
				return dispatcher.handle(cleaned, myOperation.getTenant(), true);
			} else {
				throw new EntityException(EntityException.SERVER_ERROR, "No Dispatcher or LocalClient present");
			}
		} catch (FatalException e1) {
			throw new EntityException(EntityException.SERVER_ERROR, "Error calling entity service: ", e1);
		}

	}

	protected void prepareServiceRequestHeader(Navajo input, Navajo request, Operation o) {
		prepareRequestHeader(input, request, o, o.getService());
	}

	protected void prepareValidationServiceRequestHeader(Navajo input, Navajo request, Operation o) {
		prepareRequestHeader(input, request, o, o.getValidationService());
	}

	protected void prepareRequestHeader(Navajo input, Navajo request, Operation o, String service) {
		Header h = request.getHeader();
		if (h == null) {
			h = NavajoFactory.getInstance().createHeader(request, service, "_internal_", "", -1);
			request.addHeader(h);
		} else {
			h.setRPCName(service);
		}
		// Append additional entity meta data.
		h.setHeaderAttribute("root_entity_name", myEntity.getRootEntity().getName());
		h.setHeaderAttribute("entity_name", myEntity.getName());
		h.setHeaderAttribute("entity_operation", o.getMethod());
		h.setHeaderAttribute("entity_service", o.getService());
		h.setHeaderAttribute("application", "entity");

		h.setRPCUser(input.getHeader().getRPCUser());
		h.setRPCPassword(input.getHeader().getRPCPassword());
	}

}
