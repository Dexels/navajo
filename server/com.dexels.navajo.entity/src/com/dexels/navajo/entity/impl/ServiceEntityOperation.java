package com.dexels.navajo.entity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.EntityOperation;
import com.dexels.navajo.entity.Key;
import com.dexels.navajo.entity.adapters.EntityMap;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;

public class ServiceEntityOperation implements EntityOperation {
	private EntityManager manager;
	private DispatcherInterface dispatcher;
	private LocalClient client;
	private EntityMap myEntityMap;
	private Operation myOperation;
	private final Entity myEntity;
	private Key myKey;
	private final static Logger logger = LoggerFactory.getLogger(ServiceEntityOperation.class);
	String[] validMessages = null;

	
	public ServiceEntityOperation(EntityManager m, DispatcherInterface c, Operation o) throws EntityException {
		this.manager = m;
		this.dispatcher = c;
		this.myOperation = o;
		this.myEntity = manager.getEntity(myOperation.getEntityName());
		if ( myEntity == null ) {
			logger.error("ServiceEntityOperation could not find requested entity!");
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Could not find entity: " + myOperation.getEntityName());
		}
	}

	public ServiceEntityOperation(EntityManager m, EntityMap c, Operation o) throws EntityException {
		this.manager = m;
		this.myEntityMap = c;
		this.myOperation = o;
		this.myEntity = manager.getEntity(myOperation.getEntityName());
		if ( myEntity == null ) {
			logger.error("ServiceEntityOperation could not find requested entity!");
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Could not find entity: " + myOperation.getEntityName());
		}
	}

	public ServiceEntityOperation(EntityManager m, LocalClient c, Operation o)  throws EntityException {
		this.manager = m;
		this.client = c;
		this.myOperation = o;
		this.myEntity = manager.getEntity(myOperation.getEntityName());
		if ( myEntity == null ) {
			logger.error("ServiceEntityOperation could not find requested entity!");
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, "Could not find entity: " + myOperation.getEntityName());
		}
	}

	public ServiceEntityOperation cloneServiceEntityOperation(Operation o) throws EntityException {
		if ( myEntityMap != null ) {
			return new ServiceEntityOperation(manager, myEntityMap, o);
		} else if ( dispatcher != null ) {
			return new ServiceEntityOperation(manager, dispatcher, o);
		} else if ( client != null ) {
			return new ServiceEntityOperation(manager, client, o);
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


	public LocalClient getClient() {
		return client;
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

	public EntityMap getMyEntityMap() {
		return myEntityMap;
	}

	/**
	 * Checks whether input message contains a certain property.
	 * If ignoreNonExistent is set it only checks for null valued properties, else it checks both for existence
	 * and null values.
	 * 
	 * @param input
	 * @param propertyName
	 * @param ignoreNonExistent
	 * @return
	 */
	private boolean missingProperty(Message input, String propertyName, boolean ignoreNonExistent) {
		if ( ignoreNonExistent ) {
			return ( input.getProperty(propertyName) != null && input.getProperty(propertyName).getValue() == null );
		} else {
			return ( input.getProperty(propertyName) == null || input.getProperty(propertyName).getValue() == null );
		}
	}
	
	/**
	 * Following method checks for required properties. It checks the existence of the properties and non-null values.
	 * 
	 * @param message
	 * @param entityMessage
	 * @param ignoreNonExistent, set to true for checking updates, set to false for inserts, set to true for updates (modifications)
	 * @return
	 * @throws EntityException
	 */
	private List<String> checkRequired(Message message, Message entityMessage, boolean ignoreNonExistent) throws EntityException {
		List<Property> entityProperties = entityMessage.getAllProperties();
		List<String> missingProperties = new ArrayList<String>();
		for ( Property ep : entityProperties ) {
			// Check non-auto, non-optional key properties.
			if ( Key.isKey(ep.getKey()) && !Key.isAutoKey(ep.getKey()) && !Key.isOptionalKey(ep.getKey()) && missingProperty(message, ep.getFullPropertyName(), ignoreNonExistent) ) {
				missingProperties.add(ep.getFullPropertyName() + ":key");
			}// Check required non-key properties.
			else if ( Key.isRequiredKey(ep.getKey()) && missingProperty(message, ep.getFullPropertyName(), ignoreNonExistent) ) {
				missingProperties.add(ep.getFullPropertyName());
			} 
		}
		return missingProperties;
	}
	
	/**
	 * Following method checks whether the types in the input message match the types in the entity message
	 * 
	 * @param message
	 * @param entityMessage
	 * @param method
	 * @param invalidProperties
	 * @throws EntityException
	 */
	private void checkTypes(Message message, Message entityMessage, String method, List<String> invalidProperties) throws EntityException {
		 
		Iterator<Property> inputProperties = message.getAllProperties().iterator();
		while ( inputProperties.hasNext() ) {
			Property inputP = inputProperties.next();
			Property entityP = entityMessage.getProperty(inputP.getName());
			if ( entityP != null && ( entityP.getBind() == null || entityP.getBind().equals("") ) ) {
				if ( entityP.getType().equals(Property.SELECTION_PROPERTY) ) {
					if ( inputP.getValue() != null && !inputP.getValue().equals("") ) {
						boolean found = false;
						for (Selection entityS : entityP.getAllSelections()) {
							if ( entityS.getValue().equals(inputP.getValue() ) ) {
								found = true;
							}
						}
						if (!found) {
							invalidProperties.add(inputP.getFullPropertyName() + ":" + inputP.getValue() + "<-" + "invalid selection");
							//throw new EntityException("Invalid selection option encountered in " + method + " operation for entityservice {" + message.getName() +  "}: selection property [" + inputP.getFullPropertyName() + "]: " + inputP.getValue());
						}
					}
				} else if ( !inputP.getType().equals(entityP.getType()) ) {
					invalidProperties.add(inputP.getFullPropertyName() + ":" + inputP.getType() + "<-" + entityP.getType());
					//throw new EntityException("Invalid type encountered in " + method + " operation for entityservice {" + message.getName() +  "}: [" + inputP.getFullPropertyName() + "]: " + inputP.getType() + ", expected: " + entityP.getType());
				} else if (entityP.getType().equals(Property.DATE_PROPERTY)
						&& inputP.getValue() != null && !"".equals(inputP.getValue())) {
					// BasePropertyImpl does not throw exception on invalid date format. Thus we perform an extra check here on that
					if (!(inputP.getTypedValue() instanceof Date)) {
						invalidProperties.add(inputP.getFullPropertyName() + ":" + inputP.getType() + "<-" + entityP.getType());

					}
				}
			}
		}
		Iterator<Message> inputMessages = message.getAllMessages().iterator();
		while ( inputMessages.hasNext() ) {
			Message inputM = inputMessages.next();
			Message entityM = entityMessage.getMessage(inputM.getName());
			if (inputM.isArrayMessage() && entityM.isArrayMessage() && entityM.getDefinitionMessage() != null ) {
				Iterator<Message> children = inputM.getElements().iterator();
				while ( children.hasNext() ) {
					checkTypes(children.next(), entityM.getDefinitionMessage(), method, invalidProperties);
				}
			}
		}
	}
	
	private final boolean isInArray(String [] arr, String s) {
		for ( int i = 0; i < arr.length; i++ ) {
			if ( arr[i].equals(s) ) {
				return true;
			}
		}
		return false;
	}
	
	private HashMap<String,Navajo> getInputNavajosForReferencedEntities(Navajo n) {
		List<Property> allProps = myEntity.getMessage().getAllProperties();
		HashMap<String, Navajo> referencedEntities = new HashMap<String, Navajo>();
		for ( Property p : allProps ) {
			// Fetch property from entity definition
			Property e_p = myEntity.getMessage().getProperty(p.getName());
			if ( e_p.getReference() != null && e_p.getDirection().indexOf("in") != -1 ) {
				String entityName = getEntityFromReference(e_p.getReference());
				if ( ! referencedEntities.containsKey(entityName) ) {
					Navajo input = createInputNavajoForReferencedEntity(entityName, n);
					Header h = NavajoFactory.getInstance().createHeader(input, "", n.getHeader().getRPCUser(), n.getHeader().getRPCPassword(), (long) -1);
					input.addHeader(h);
					referencedEntities.put(entityName, input);
				}
			}
		}
		return referencedEntities;
	}
	
	private void updateReferencedEntities(Navajo n) {
		HashMap<String,Navajo> referenced = getInputNavajosForReferencedEntities(n);
		List<Property> allProps = n.getMessage(myEntity.getMessageName()).getAllProperties();
		HashSet<String> updateableEntities = new HashSet<String>();
		
		for ( Property p : allProps ) {
			// Fetch property from entity definition
			Property e_p = myEntity.getMessage().getProperty(p.getName());
			if ( e_p.getBind() != null ) {
				String entityName = getEntityFromReference(e_p.getBind());
				String propertyName = getPropertyFromReference(e_p.getBind());
				Navajo entityObj = referenced.get(entityName);
				Property r_p = NavajoFactory.getInstance().createProperty(entityObj, propertyName, "", null, 0, "", "in");
				//System.err.println("Creating property " + r_p.getName() + " with value " + p.getTypedValue());
				r_p.setAnyValue(p.getTypedValue());
				entityObj.getMessage(entityName).addProperty(r_p);
				updateableEntities.add(entityName);
			}
		}

		for ( String r_s : updateableEntities ) {
			try {
				Navajo r_n = referenced.get(r_s);
				Operation o = EntityManager.getInstance().getOperation(r_n.getAllMessages().get(0).getName(), myOperation.getMethod());
				ServiceEntityOperation seo = new ServiceEntityOperation(EntityManager.getInstance(), DispatcherFactory.getInstance(), o);
				seo.perform(r_n);
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}
	}
	
	/**
	 * Clean a Navajo document: only valid messages are kept, missing properties 
	 * and messages are added, and filter properties on the right direction
	 * 
	 * @param validMessages
	 * @return
	 */
	private void clean(Navajo n, String [] validMessages, String method, boolean resolveLinks) {
		
		List<Message> all = n.getAllMessages();
		for ( Message m : all ) {
			if (!isInArray(validMessages, m.getName() ) ) {
				n.removeMessage(m);
			}
		}
		if ( n.getMessage(myEntity.getMessageName()) != null )  {
			n.getMessage(myEntity.getMessageName()).merge(myEntity.getMessage(), true );
			n.getMessage(myEntity.getMessageName()).maskMessage(myEntity.getMessage(), method);

			if ( resolveLinks ) {

				//myEntity.getMessage().write(System.err);
				
				// Add properties that refer to other entities.
				List<Property> allProps = myEntity.getMessage().getAllProperties();
				HashMap<String, Navajo> referencedEntities = getInputNavajosForReferencedEntities(n);
				// Populate property values that bind to external entities.
				HashMap<String, Navajo> cachedEntities = new HashMap<String, Navajo >();
				for ( Property p : allProps  ) {
					if ( p.getBind() != null ) {
						String entityName = getEntityFromReference(p.getBind());
						String propertyName = getPropertyFromReference(p.getBind());
						Navajo entityObj = null;
						if ( !cachedEntities.containsKey(entityName)) {
							try {
								Operation o = EntityManager.getInstance().getOperation(entityName, "GET");
								ServiceEntityOperation seo = new ServiceEntityOperation(EntityManager.getInstance(), DispatcherFactory.getInstance(), o);
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
		
		List<Property> props= input.getMessage(myEntity.getMessageName()).getAllProperties();
		for (Message m : input.getMessage(myEntity.getMessageName()).getAllMessages()) {
			props.addAll(m.getAllProperties());
		}
		
		Key entityKey = entityObj.getKey(props);
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
	
	
	/**
	 * Check for missing _id/__id properties
	 * 
	 * @param m
	 */
	private void addIdToArrayMessageDefinitions(Message m) {
		if ( !m.isArrayMessage() ) {
			if ( m.getProperty("_id") == null ) {
				// NOT use direction="in" for _id, since direction="in" is also default in Mongo/Navajo driver.
				Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(), "_id", Property.STRING_PROPERTY, "", 0, "", Property.DIR_IN);
				m.addProperty(p);
			}
		} else {
			if ( m.isArrayMessage() && m.getDefinitionMessage() != null && m.getDefinitionMessage().getProperty("__id") == null ) {
				Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(), "__id", Property.STRING_PROPERTY, "", 0, "", Property.DIR_IN);
				m.getDefinitionMessage().addProperty(p);
			}
			List<Message> children = m.getAllMessages();
			for ( int i = 0; i < children.size(); i++ ) {
				addIdToArrayMessageDefinitions(children.get(i));
			}
		}
	}
	
	private String listToString(List<String> l) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for ( String s : l ) {
			if ( index == 0 ) {
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
		String postedEtag;
		
		validMessages = new String[] {
				"__parms__",
				"__globals__",
				myEntity.getMessageName(),
				(myOperation.getExtraMessage() != null ? myOperation.getExtraMessage().getName()
						: "") };
		
		if(myOperation.getMethod().equals(Operation.HEAD)) {
			Navajo out = NavajoFactory.getInstance().createNavajo();
			out.addMessage(myEntity.getMessage().copy(out));
			return out;
		}
	
		Message inputEntity = input.getMessage(myEntity.getMessageName());
		
		if (inputEntity == null) {
			throw new EntityException(EntityException.BAD_REQUEST, "No valid entity found.");
		}
		
		List<Property> props= inputEntity.getAllProperties();
		for (Message m : inputEntity.getAllMessages()) {
			props.addAll(m.getAllProperties());
		}
		
		myKey = myEntity.getKey(props);
		if(myKey==null) {
			// Check for _id property. If _id is present it is good as a key.  
			// It's also possible our entity has no keys defined. In that case accept input
			if ( inputEntity.getProperty("_id") == null && myEntity.getKeys().size() > 0 ) {
				throw new EntityException(EntityException.MISSING_ID, "Input is invalid: no valid entity key found.");
			} else {
				myKey = new Key("", myEntity);
			}
		}
		clean(input, validMessages, "request", false);
		
		// Perform validation method if defined
		if ((myOperation.getValidationService()) != null) {
			Navajo validationResult = callEntityValidationService(input);
	
			Message validationErrors;
			if ((validationErrors = validationResult.getMessage("ConditionErrors")) != null ) {
				throw new EntityException(EntityException.FAILURE, validationErrors.getMessage(0)
						.getProperty("Id").toString());
			}
		}
		
		if( myOperation.getMethod().equals(Operation.GET) ) {
			if ((postedEtag = inputEntity.getEtag()) != null) {
				Navajo entity = getCurrentEntity(input);
				if (entity != null && entity.getMessage(myEntity.getMessageName()) != null) {
					if (postedEtag.equals(entity.getMessage(myEntity.getMessageName()).generateEtag())) {
						throw new EntityException(EntityException.NOT_MODIFIED);
					}
				}
				
			}
			
			return callEntityService(input);

		}
		
		if (myOperation.getMethod().equals(Operation.DELETE)) {
			validateEtag(input, inputEntity, null);
			Navajo request = myKey.generateRequestMessage(input);
			try {
				checkForMongo(request.getMessage(myEntity.getMessageName()), null);
			} catch (Exception e) {
				throw new EntityException(EntityException.ENTITY_NOT_FOUND,
						"Could not peform delete, entity not found");
			}

			return callEntityService(input);
		}
	
		if (myOperation.getMethod().equals(Operation.PUT) || myOperation.getMethod().equals(Operation.POST)) {

			// PUT == upsert: update or insert
			// Must be idem-potent!
			if (myOperation.getMethod().equals(Operation.PUT)) {

				// Required properties check.
				List<String> missing = checkRequired(inputEntity, myEntity.getMessage(), false);
				if (missing.size() > 0) {
					throw new EntityException(EntityException.BAD_REQUEST,
							"Could not perform insert, missing required properties: "
									+ listToString(missing));
				}
				
				Navajo currentEntity = getCurrentEntity(input);
				validateEtag(input, inputEntity, currentEntity);
				
				// Duplicate entry check.
				if (currentEntity != null) {
					try {
						if (checkForMongo(inputEntity, currentEntity)) {
							addIdToArrayMessageDefinitions(myEntity.getMessage());
						}
					} catch (EntityException e) {
						// TODO: Support PUT for create
						// Currently the Mongo impl. does not support this due to missing _id. 
						// Thus, we throw exception
						throw new EntityException(EntityException.ENTITY_NOT_FOUND,
								"Could not perform update, entity not found");
					}
				}
			}

			// POST == insert
			if (myOperation.getMethod().equals(Operation.POST)) {

				List<String> missing = checkRequired(inputEntity, myEntity.getMessage(), true);
				if (missing.size() > 0) {
					throw new EntityException(EntityException.BAD_REQUEST,
							"Could not perform update, missing required properties:\n"
									+ listToString(missing));
				}
				// Duplicate entry check.
				if ( getCurrentEntity(input) != null ) {
					// TODO: Support POST for updates
					// Right now we cannot detect whether the POST will cause a conflict (e.g.
					// a duplicate key) or not. Therefore simply give CONFLICT error right away
					throw new EntityException(EntityException.CONFLICT, "Could not perform insert, duplicate entry");
				}
			}
			
			// Check property types.
			List<String> invalidProperties = new ArrayList<String>();
			checkTypes(inputEntity, myEntity.getMessage(), myOperation.getMethod(),
					invalidProperties);
			if (invalidProperties.size() > 0) {
				throw new EntityException(EntityException.BAD_REQUEST,
						"Could not perform operation, invalid property types: "+ listToString(invalidProperties));
			}

			Navajo result = callEntityService(input);
			
			// Update referenced entities as well...
			updateReferencedEntities(input);
			
			// After a POST or PUT, return the full new object resulting from the previous operation
			// effectively this is a GET. However, if this fails (e.g. no GET operation is defined
			// for this entity), we return the original result
			try { 
				return getEntity(input); 
			} catch (EntityException e) {
				return result;
			} 
		}
		return null;
	}

	private void validateEtag(Navajo input, Message inputEntity, Navajo current) throws EntityException {
		String postedEtag;
		// Check Etag.
		if ((postedEtag = inputEntity.getEtag()) != null) {
			if ( current == null ) {
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
	 * Checks whether this entity is backed by Mongo. If this is true it checks for existence of _id property.
	 * If _id is not present or empty the entity is queried and the _id is put into the original input.
	 * 
	 * @param inputEntity
	 * @param currentEntity (optionally pass current entity instance)
	 * @param k
	 * @throws EntityException
	 */
	private boolean checkForMongo(Message inputEntity, Navajo currentEntity) throws EntityException {
		if ( myOperation.getExtraMessage() != null && myOperation.getExtraMessage().getName().equals("__Mongo__") && 
				( inputEntity.getProperty("_id") == null || inputEntity.getProperty("_id").getValue().equals("") ) ) {
			// Fetch _id
			Navajo result = null;
			if ( currentEntity == null ) {
				result = getCurrentEntity(inputEntity.getRootDoc());
			} else {
				result = currentEntity;
			}
			//System.err.println("In checkForMongo, current entity:");
			Property id = null;
			if ( result != null ) {
				id = result.getProperty(inputEntity.getName() + "/_id");
			} else if ( result == null && myEntityMap != null ) {
				try {
					id = myEntityMap.getPropertyObject(inputEntity.getName() + "/_id");
				} catch (UserException e) {
					throw new EntityException(EntityException.SERVER_ERROR, e.getMessage());
				}
			} else {
				throw new EntityException(EntityException.SERVER_ERROR, "Problem while trying to update Mongo backed entity: empty result from get.");
			}
			if ( id != null ) {
				inputEntity.addProperty(id.copy(inputEntity.getRootDoc()));
			} else {
				throw new EntityException(EntityException.SERVER_ERROR, "Could not fetch _id for Mongo backed entity.");
			}
		}
		return ( inputEntity.getProperty("_id") != null );
	}


	
	private Navajo getEntity(Navajo input) throws EntityException {
		Navajo result = getCurrentEntity(input);
		if ( result == null ) {
			throw new EntityException(EntityException.ENTITY_NOT_FOUND, myEntity.getName());
		}

		return result;
	}


	/**
	 * Perform a GET operation on the entity instance based on the input Navajo. Check
	 * the result for errors (Server errors or authentication).
	 * 
	 * @param input
	 * @return Returns the result of the GET operation. If no message named entity.getName()
	 * is found, return null.
	 * @throws EntityException Throws EntityException when the GET operation results in a 
	 * error.
	 */
	private Navajo getCurrentEntity(Navajo input) throws EntityException {
		Operation getop  = null;
		try{ 
			getop = manager.getOperation(myEntity.getName(), Operation.GET);
		} catch (EntityException e) {
			if (e.getCode() == EntityException.OPERATION_NOT_SUPPORTED) {
				// No GET operation defined - no biggie
				return null;
			}
			// Other exceptions are passed on
			throw e;
		}
		
		ServiceEntityOperation get = this.cloneServiceEntityOperation(getop);
		Navajo request = myKey.generateRequestMessage(input);
		if ( getop.getExtraMessage() != null ) {
			request.addMessage(getop.getExtraMessage().copy(request));
		}
		prepareServiceRequestHeader(input.copy(), request, getop);


		Navajo result = get.commitOperation(request, getop);
		if (result.getMessage("error") != null) {
			throw new EntityException(EntityException.SERVER_ERROR);
		}
		if (result.getMessage("AuthenticationError") != null) {
			throw new EntityException(EntityException.UNAUTHORIZED);
		}

		if ( result.getMessage(myEntity.getMessageName()) == null ) {
			return null;
		}
		
		return result;
	}
	
	private Navajo callEntityValidationService(Navajo input) throws EntityException {
		Navajo request = input.copy();
		if ( myOperation.getExtraMessage() != null ) {
			input.addMessage(myOperation.getExtraMessage().copy(request));
		}
		prepareValidationServiceRequestHeader(request,request, myOperation);

		// No transaction support yet
		Navajo result =  commitOperation(request, myOperation);
		
		if (result.getMessage("error") != null) {
			throw new EntityException(EntityException.SERVER_ERROR);
		}
		return result;
	}	
	
	/**
	 * If This method is called in the context of a Transaction, depending on the isolation level
	 * use real or proxy service calls.
	 * 
	 * @param input
	 * @param service
	 * @return
	 * @throws EntityException
	 */
	private Navajo callEntityService(Navajo input) throws EntityException {
		if ( myOperation.getExtraMessage() != null ) {
			input.addMessage(myOperation.getExtraMessage().copy(input));
		}
		prepareServiceRequestHeader(input,input, myOperation);

		// No transaction support yet
		Navajo result =  commitOperation(input, myOperation);
		if ( result != null ) {
			if (result.getMessage("error") != null) {
				throw new EntityException(EntityException.SERVER_ERROR, result.getMessage("error").getProperty("message").getValue());
			}
			if (result.getMessage("AuthenticationError") != null) {
				throw new EntityException(EntityException.UNAUTHORIZED);
			}
			if (result.getMessage("AuthorizationError") != null) {
				throw new EntityException(EntityException.UNAUTHORIZED);
			}
			
			Message validationErrors;
			if ((validationErrors = result.getMessage("ConditionErrors")) != null ) {
				throw new EntityException(EntityException.FAILURE, validationErrors.getMessage(0).getProperty("Id").toString());
			}
			
		}
		clean(result, new String[]{myEntity.getMessageName()}, "response", true);	
		return result;
		
		// Check for transaction.
//		try {
//			NavajoTransaction nt = (NavajoTransaction) NavajoTransactionManager.getInstance().getTransaction();
//			if ( nt != null && !myOperation.getMethod().equals(Operation.GET) ) {
//				Navajo original = null;
//				// fetch original Navajo (before PUT or DELETE).
//				if ( myOperation.getMethod().equals(Operation.PUT) || myOperation.getMethod().equals(Operation.DELETE)) {
//					original = getCurrentEntity(input);
//				}
//				// Perform operation (blocking!)
//				Navajo result = commitOperation(input, myOperation, true);
//				// Log when successful.
//				nt.addNonTransactionalEntityResource(input, original, this);
//				return result;
//			} else {
//				return commitOperation(input, myOperation, false);
//			}
//		} catch (Exception se) {
//			throw new EntityException(EntityException.SERVER_ERROR, se.getMessage(), se);
//		}

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
		for ( Property p : list ) {
			// Check if this is a bind property.
			Property e_p = myEntity.getMessage().getProperty(p.getName());
			if ( e_p.getBind() != null && !e_p.getBind().equals("") ) {
				result.getMessage(myEntity.getMessageName()).removeProperty(p);
			}
		}
		return result;
	}
	
	/**
	 * Actually performs a Navajo service call.
	 * Either of the three following methods is used:
	 * (1) Use an EntityMap (derived from NavajoMap) if set
	 * (2) Use Dispatcher if set
	 * (3) Use NavajoClient if set
	 * 
	 * @param input, the request Navajo
	 * @param block, for EntityMap only: perform service call blocking (true) or non-blocking (false)
	 * @return
	 * @throws EntityException
	 */
	private Navajo commitOperation(Navajo input, Operation o) throws EntityException {
		// Remove bind properties, these properties do not belong to this entity
		Navajo cleaned = removeBindProperties(input);
		try {
			if ( myEntityMap != null ) {
				try {
					myEntityMap.setDoSend(o.getService(), cleaned);
					myEntityMap.waitForResult();
					Navajo n = myEntityMap.getResponseNavajo();
					return n;
				} catch (Exception e) {
					throw new EntityException(EntityException.SERVER_ERROR, e.getMessage(), e);
				} 
			}
			if ( dispatcher != null ) {
				return dispatcher.handle(cleaned, false);
			} else
				if ( client != null ) {
					return client.call(cleaned);
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
		if(h==null) {
			h = NavajoFactory.getInstance().createHeader(request, service, "", "", -1);
			request.addHeader(h);
		} else {
			h.setRPCName(service);
		}
		// Append additional entity meta data.
		h.setHeaderAttribute("root_entity_name", myEntity.getRootEntity().getName());
		h.setHeaderAttribute("entity_name", myEntity.getName());
		h.setHeaderAttribute("entity_operation", o.getMethod());
		h.setHeaderAttribute("entity_service", o.getService());
		
		h.setRPCUser(input.getHeader().getRPCUser());
		h.setRPCPassword(input.getHeader().getRPCPassword());
	}

}
