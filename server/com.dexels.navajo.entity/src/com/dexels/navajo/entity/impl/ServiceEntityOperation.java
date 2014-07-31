package com.dexels.navajo.entity.impl;

import java.util.ArrayList;
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
import com.dexels.navajo.server.DispatcherInterface;

public class ServiceEntityOperation implements EntityOperation {

	private EntityManager manager;
	private DispatcherInterface dispatcher;
	private LocalClient client;
	private EntityMap myEntityMap;
	private Operation myOperation;
	private final Entity myEntity;
	private Key myKey;
	private static final String ENTITY_META_MSG = "__Entity__";
	private static final String ETAG = "ETag";
	private static final String STATUS = "Status";
	private final static Logger logger = LoggerFactory.getLogger(ServiceEntityOperation.class);

	
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
			if ( entityP != null ) {
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
	
	/**
	 * Clean a Navajo input: only valid messages are kept.
	 * 
	 * @param validMessages
	 * @return
	 */
	private void cleanInput(Navajo input, String [] validMessages) {
		List<Message> all = input.getAllMessages();
		for ( Message m : all ) {
			if (!isInArray(validMessages, m.getName() ) ) {
				input.removeMessage(m);
			}
		}
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
	
	private String getMetaProperty(Navajo n, String name) {
		Message em = null;
		if ( ( em = n.getMessage(ServiceEntityOperation.ENTITY_META_MSG) ) != null ) {
			Property p = em.getProperty(name);
			if ( p != null ) {
				return p.getValue();
			}
		}
		return null;
	}
	
	private void setMetaProperty(Navajo n, String name, String value) {
		Message em = null;
		if ( ( em = n.getMessage(ServiceEntityOperation.ENTITY_META_MSG) ) == null ) {
			em = NavajoFactory.getInstance().createMessage(n, ServiceEntityOperation.ENTITY_META_MSG);
			n.addMessage(em);
		}
		Property et = em.getProperty(name);
		if ( et == null ) {
			et = NavajoFactory.getInstance().createProperty(n, name, Property.STRING_PROPERTY, value, 0, "", Property.DIR_OUT, null);
			em.addProperty(et);
		} else {
			et.setValue(value);
		}
	}
	
	private void setEtag(Navajo n) {
		Message m = n.getMessage(myEntity.getName());
		String etag = m.generateEtag();
		setMetaProperty(n, ETAG, etag);
	}
	
	@Override
	public Navajo perform(Navajo input) throws EntityException {
		String postedEtag;
		
		String[] validMessages = {
				"__parms__",
				"__globals__",
				ServiceEntityOperation.ENTITY_META_MSG,
				myOperation.getEntityName(),
				(myOperation.getExtraMessage() != null ? myOperation.getExtraMessage().getName()
						: "") };
		
		if(myOperation.getMethod().equals(Operation.HEAD)) {
			Navajo out = NavajoFactory.getInstance().createNavajo();
			out.addMessage(myEntity.getMessage().copy(out));
			return out;
		}
		
		Message inputEntity = input.getMessage(myEntity.getName());
		if (inputEntity == null) {
			throw new EntityException(EntityException.BAD_REQUEST, "No valid entity found.");
		}
		
		myKey = myEntity.getKey(inputEntity.getAllProperties());
		if(myKey==null) {
			// Check for _id property. If _id is present it is good as a key.  
			// It's also possible our entity has no keys defined. In that case accept input
			if ( inputEntity.getProperty("_id") == null && myEntity.getKeys().size() > 0 ) {
				throw new EntityException(EntityException.MISSING_ID, "Input is invalid: no valid entity key found.");
			} else {
				myKey = new Key("", myEntity);
			}
		}
		cleanInput(input, validMessages);
		
		// Perform validation method if defined
		if ((myOperation.getValidationService()) != null) {
			Navajo request = myKey.generateRequestMessage(input);
			Navajo validationResult = callEntityValidationService(input, request);
	
			Message validationErrors;
			if ((validationErrors = validationResult.getMessage("ConditionErrors")) != null ) {
				throw new EntityException(EntityException.FAILURE, validationErrors.getMessage(0)
						.getProperty("Id").toString());
			}
		}
		
		if( myOperation.getMethod().equals(Operation.GET) ) {
			if ((postedEtag = inputEntity.getEtag()) != null) {
				Navajo entity = getCurrentEntity(input);
				if (entity != null && entity.getMessage(myEntity.getName()) != null) {
					if (postedEtag.equals(entity.getMessage(myEntity.getName()).generateEtag())) {
						throw new EntityException(EntityException.NOT_MODIFIED);
					}
				}
				
			}
			
			return getEntity(input);
		}
		
		if (myOperation.getMethod().equals(Operation.DELETE)) {
			validateEtag(input, inputEntity);
			Navajo request = myKey.generateRequestMessage(input);
			try {
				checkForMongo(request.getMessage(myEntity.getName()));
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
				validateEtag(input, inputEntity);
				
				// Duplicate entry check.
				if (currentEntity != null) {
					try {
						if (checkForMongo(inputEntity)) {
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

			// Mask message, i.e. populate selection properties and add missing
			// properties.
			inputEntity.maskMessage(myEntity.getMessage());
			// Check property types.
			List<String> invalidProperties = new ArrayList<String>();
			checkTypes(inputEntity, myEntity.getMessage(), myOperation.getMethod(),
					invalidProperties);
			if (invalidProperties.size() > 0) {
				throw new EntityException(EntityException.BAD_REQUEST,
						"Could not perform operation, invalid property types:\n"
								+ listToString(invalidProperties));
			}

			Navajo result = callEntityService(input);
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

	private void validateEtag(Navajo input, Message inputEntity) throws EntityException {
		String postedEtag;
		// Check Etag.
		if ((postedEtag = inputEntity.getEtag()) != null) {
			String currentEtag = getCurrentEntity(input).getMessage(myEntity.getName()).generateEtag();
			if (!postedEtag.equals(currentEtag)) {
				throw new EntityException(EntityException.ETAG_ERROR);
			}
		}
	}


	/**
	 * Checks whether this entity is backed by Mongo. If this is true it checks for existence of _id property.
	 * If _id is not present the entity is queried and the _id is put into the original input.
	 * 
	 * @param inputEntity
	 * @param k
	 * @throws EntityException
	 */
	private boolean checkForMongo(Message inputEntity) throws EntityException {
		if ( myOperation.getExtraMessage() != null && myOperation.getExtraMessage().getName().equals("__Mongo__") && inputEntity.getProperty("_id") == null ) {
			// Fetch _id
			Navajo result = getCurrentEntity(inputEntity.getRootDoc());
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
		result.getMessage(myEntity.getName()).maskMessage(myEntity.getMessage());
		setEtag(result);
		setMetaProperty(result, STATUS, EntityException.OK+"");
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
		prepareServiceRequestHeader(input, request, getop);


		Navajo result = get.commitOperation(request, getop, true);
		if (result.getMessage("error") != null) {
			throw new EntityException(EntityException.SERVER_ERROR);
		}
		if (result.getMessage("AuthenticationError") != null) {
			throw new EntityException(EntityException.UNAUTHORIZED);
		}

		if ( result.getMessage(myEntity.getName()) == null ) {
			return null;
		}
		
		return result;
	}
	
	private Navajo callEntityValidationService(Navajo input, Navajo request) throws EntityException {
		prepareValidationServiceRequestHeader(input, request, myOperation);
		Navajo result =  commitOperation( request, myOperation, false);
		if (result.getMessage("errors") != null) {
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
		Navajo request = myKey.generateRequestMessage(input);
		if ( myOperation.getExtraMessage() != null ) {
			request.addMessage(myOperation.getExtraMessage().copy(request));
		}
		prepareServiceRequestHeader(input,request, myOperation);

		// No transaction support yet
		Navajo result =  commitOperation(request, myOperation, false);
		if (result.getMessage("error") != null) {
			throw new EntityException(EntityException.SERVER_ERROR);
		}
		if (result.getMessage("AuthenticationError") != null) {
			throw new EntityException(EntityException.UNAUTHORIZED);
		}
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
	private Navajo commitOperation(Navajo input, Operation o, boolean block) throws EntityException {
		try {
			if ( myEntityMap != null ) {
				try {
					myEntityMap.setDoSend(o.getService(), input);
					if ( block ) {
						myEntityMap.waitForResult();
						Navajo n = myEntityMap.getResponseNavajo();
						return n;
					}
				} catch (Exception e) {
					throw new EntityException(EntityException.SERVER_ERROR, e.getMessage(), e);
				} 
				return null;
			}
			if ( dispatcher != null ) {
				return dispatcher.handle(input, true);
			} else
				if ( client != null ) {
					return client.call(input);
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
		h.setHeaderAttribute("entity_name", myEntity.getName());
		h.setHeaderAttribute("entity_operation", o.getMethod());
		h.setHeaderAttribute("entity_service", o.getService());
		
		h.setRPCUser(input.getHeader().getRPCUser());
		h.setRPCPassword(input.getHeader().getRPCPassword());
	}

}
