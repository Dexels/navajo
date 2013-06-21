package com.dexels.navajo.entity.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.UserException;

public class ServiceEntityOperation implements EntityOperation {

	private EntityManager manager;
	private DispatcherInterface dispatcher;
	private LocalClient client;
	private EntityMap myEntityMap;
	private final Operation myOperation;
	private final Entity myEntity;
	
	public ServiceEntityOperation(EntityManager m, DispatcherInterface c, Operation o) throws EntityException {
		this.manager = m;
		this.dispatcher = c;
		this.myOperation = o;
		this.myEntity = manager.getEntity(myOperation.getEntityName());
		if ( myEntity == null ) {
			throw new EntityException("Could not find entity: " + myOperation.getEntityName());
		}
	}


	public ServiceEntityOperation(EntityManager m, LocalClient c, Operation o)  throws EntityException {
		this.manager = m;
		this.client = c;
		this.myOperation = o;
		this.myEntity = manager.getEntity(myOperation.getEntityName());
		if ( myEntity == null ) {
			throw new EntityException("Could not find entity: " + myOperation.getEntityName());
		}
	}

	public Entity getMyEntity() {
		return myEntity;
	}

	public EntityMap getMyEntityMap() {
		return myEntityMap;
	}

	public void setMyEntityMap(EntityMap myEntityMap) {
		this.myEntityMap = myEntityMap;
	}

	private List<String> checkRequired(Message message, Message entityMessage) throws EntityException {
		List<Property> entityProperties = entityMessage.getAllProperties();
		List<String> missingProperties = new ArrayList<String>();
		for ( Property ep : entityProperties ) {
			if ( ep.getKey() != null  && ep.getKey().contains("required") && message.getProperty(ep.getFullPropertyName()) == null ) {
				missingProperties.add(ep.getFullPropertyName());
			}
		}
		return missingProperties;
	}
	
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
				Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(), "_id", Property.STRING_PROPERTY, "", 0, "", "out");
				m.addProperty(p);
			}
		} else {
			if ( m.isArrayMessage() && m.getDefinitionMessage() != null && m.getDefinitionMessage().getProperty("__id") == null ) {
				Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(), "__id", Property.STRING_PROPERTY, "", 0, "", "out");
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
		
		String [] validMessages = {"__parms__", "__globals__", myOperation.getEntityName(), ( myOperation.getExtraMessage() != null ? myOperation.getExtraMessage().getName() : "")};
		
		if(myOperation.getMethod().equals(Operation.HEAD)) {
			Navajo out = NavajoFactory.getInstance().createNavajo();
			out.addMessage(myEntity.getMessage().copy(out));
			return out;
		}
		
		Message inputEntity = input.getMessage(myEntity.getName());
		Key k = myEntity.getKey(inputEntity.getAllProperties());
		if(k==null) {
			// Check for _id property. If _id is present it is good as a key.
			if ( inputEntity.getProperty("_id") == null ) {
				throw new EntityException("Input is invalid: no valid entity key found.");
			}
		}
		cleanInput(input, validMessages);
		
		if( myOperation.getMethod().equals(Operation.GET) || myOperation.getMethod().equals(Operation.DELETE) ) {
			Navajo request = k.generateRequestMessage(input);
			if ( myOperation.getMethod().equals(Operation.DELETE) ) {
				try {
					checkForMongo(request.getMessage(myEntity.getName()), k);
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					request.write(sw);
					throw new EntityException("Could not peform delete, entity not found:\n" + sw);
				}
			}
			return callEntityService(request, myOperation.getService());
		}
	
		if(myOperation.getMethod().equals(Operation.PUT) || myOperation.getMethod().equals(Operation.POST)) {
			
			// Check for Mongo entity, if Mongo make sure that _id is added if it does not exist.
			try {
				if ( myOperation.getMethod().equals(Operation.POST) && checkForMongo(inputEntity, k) ) {
					addIdToArrayMessageDefinitions(myEntity.getMessage());
				}
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				inputEntity.write(sw);
				throw new EntityException("Could not perform update, entity not found:\n" + sw);
			}
			
			if ( myOperation.getMethod().equals(Operation.PUT) ) {
				List<String> missing = checkRequired(inputEntity, myEntity.getMessage());
				if ( missing.size() > 0  ) {
					throw new EntityException("Could not perform insert, missing properties:\n" + listToString(missing));
				}
			}
			inputEntity.maskMessage(myEntity.getMessage());
			List<String> invalidProperties = new ArrayList<String>();
			checkTypes(inputEntity, myEntity.getMessage(), myOperation.getMethod(), invalidProperties);
			if ( invalidProperties.size() > 0 ) {
				throw new EntityException("Could not perform operation, invalid property types:\n" + listToString(invalidProperties));
			}
			return callEntityService(input, myOperation.getService());
			
		}
		return null;
	}


	/**
	 * Checks whether this entity is backed by Mongo. If this is true it checks for existence of _id property.
	 * If _id is not present the entity is queried and the _id is put into the original input.
	 * 
	 * @param inputEntity
	 * @param k
	 * @throws EntityException
	 */
	private boolean checkForMongo(Message inputEntity, Key k) throws EntityException {
		if ( myOperation.getExtraMessage() != null && myOperation.getExtraMessage().getName().equals("__Mongo__") && inputEntity.getProperty("_id") == null ) {
			// Fetch _id
			Operation get = manager.getOperation(inputEntity.getName(), Operation.GET);
			
			// Construct request from key and input.
			Navajo request = k.generateRequestMessage(inputEntity.getRootDoc());
			Navajo result = callEntityService(request, get.getService());
			Property id = null;
			if ( result != null ) {
				id = result.getProperty(inputEntity.getName() + "/_id");
			} else if ( result == null && myEntityMap != null ) {
				try {
					id = myEntityMap.getPropertyObject(inputEntity.getName() + "/_id");
				} catch (UserException e) {
					throw new EntityException(e.getMessage(), e);
				}
			} else {
				throw new EntityException("Problem while trying to update Mongo backed entity: empty result from get.");
			}
			if ( id != null ) {
				inputEntity.addProperty(id.copy(inputEntity.getRootDoc()));
			} else {
				throw new EntityException("Could not fetch _id for Mongo backed entity.");
			}
		}
		return ( inputEntity.getProperty("_id") != null );
	}


	protected Navajo callEntityService(Navajo input, String service) throws EntityException {
		appendServiceName(input, service);
		final Message extraMessage = myOperation.getExtraMessage();
		if(extraMessage!=null) {
			input.addMessage(extraMessage);
		}
		try {
			if ( myEntityMap != null ) {
				try {
					myEntityMap.setDoSend(service, input);
				} catch (Exception e) {
					throw new EntityException(e.getMessage(), e);
				} 
				return null;
			}
			if ( dispatcher != null ) {
				return dispatcher.handle(input, true);
			} else
				if ( client != null ) {
					return client.call(input);
				} else {
					throw new EntityException("No Dispatcher or LocalClient present");
				}
		} catch (FatalException e1) {
			throw new EntityException("Error calling entity service: ",e1);
		}
	}


	protected void appendServiceName(Navajo input, String rpcName) {
		Header h = input.getHeader();
		if(h==null) {
			h = NavajoFactory.getInstance().createHeader(input, rpcName, "", "", -1);
			input.addHeader(h);
		} else {
			h.setRPCName(rpcName);
		}
		// Append additional entity meta data.
		h.setHeaderAttribute("entity_name", myOperation.getEntityName());
		h.setHeaderAttribute("entity_operation", myOperation.getMethod());
		h.setHeaderAttribute("entity_service", myOperation.getService());
	}

}
