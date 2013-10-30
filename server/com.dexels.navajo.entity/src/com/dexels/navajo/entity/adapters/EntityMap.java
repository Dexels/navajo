package com.dexels.navajo.entity.adapters;

import java.util.Iterator;

import com.dexels.navajo.adapter.NavajoMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class EntityMap extends NavajoMap {

	private EntityManager myManager;
	private String entity;
	private String method;
	private Entity myEntity;
	
	private boolean templateMerged = false;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		super.load(access);
		myManager = EntityManager.getInstance();
	}

	private void mergeWithEntityTemplate(Message msg, Message templateMessage) {
		if ( templateMessage == null || ( templateMessage.isArrayMessage() && templateMessage.getDefinitionMessage() == null) ) {
			return;
		}
		// Set properties.
		Iterator<Property> allProperties = 
				( templateMessage.isArrayMessage() ? 
						templateMessage.getDefinitionMessage().getAllProperties().iterator() : 
							templateMessage.getAllProperties().iterator() );
		while ( allProperties.hasNext() ) {
			Property e_p = allProperties.next();
			// Find property in input message.
			Property p = msg.getProperty(e_p.getName());
			/**
			 * #TODO: Support for multiple selection properties...
			 */
			if ( e_p.getType().equals(Property.SELECTION_PROPERTY) && e_p.getCardinality().equals("1") ) {
				String selectedValue = "-1";
				if ( p != null ) {
					selectedValue = ( p.getType().equals(Property.SELECTION_PROPERTY) ? p.getSelected().getValue() : p.getValue() );
					msg.removeProperty(p);
				}
				p = e_p.copy(msg.getRootDoc());
				p.clearSelections();
				p.setSelected(selectedValue);
				msg.addProperty(p);
			} else {
				if ( p == null ) {
					p = e_p.copy(msg.getRootDoc());
					msg.addProperty(p);
				} else {
					p.setDescription(e_p.getDescription());
					p.setDirection(e_p.getDirection());
					p.setType(e_p.getType());
					p.setSubType(e_p.getSubType());
					p.setLength(e_p.getLength());
				} 
			}
		}
		// Iterate over messages.
		Iterator<Message> allMessages = templateMessage.getAllMessages().iterator();
		while ( allMessages.hasNext() ) {
			Message e_m = allMessages.next();
			if ( e_m.isArrayMessage() ) {
				Message m = msg.getMessage(e_m.getName());
				if ( m != null ) {
					for ( int i = 0; i < m.getArraySize(); i++ ) {
						mergeWithEntityTemplate(m.getMessage(i), e_m);
					}
				} else {
					m = NavajoFactory.getInstance().createMessage(msg.getRootDoc(), e_m.getName());
					m.setType(Message.MSG_TYPE_ARRAY);
					if ( e_m.getDefinitionMessage() != null ) {
						m.setDefinitionMessage(e_m.getDefinitionMessage().copy(msg.getRootDoc()));
					}
					msg.addMessage(m);
				}
			} else {
				Message m = msg.getMessage(e_m.getName());
				if ( m != null ) {
					mergeWithEntityTemplate(m, e_m);
				} else {
					m = NavajoFactory.getInstance().createMessage(msg.getRootDoc(), e_m.getName());
					msg.addMessage(m);
				}
			}
		}
	}
	
	@Override
	public void setDoSend(String s, Navajo n) throws UserException, ConditionErrorException, SystemException {
		templateMerged = false;
		serviceCalled = false;
		serviceFinished = false;
		super.setDoSend(s, n);
	}
	
	public void setCall(boolean v) throws Exception {
		
		if ( entity != null && method != null ) {
			Operation o = myManager.getOperation(entity, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, this, o);
			myEntity = seo.getMyEntity();
			Navajo result = seo.perform(prepareOutDoc());
			if ( result != null ) {
				this.serviceCalled = true;
				this.serviceFinished = true;
				this.inDoc = result;
			}
		}
		
	}
	
	@Override
	public void setPropertyName(String s) throws UserException {
		// Check if property name does not contain '/' character, i.e. it is a relative property name: append the entity name
		if ( s.indexOf("/") == -1 ) {
			s = "/" + entity + "/" + s; 
		}
		super.setPropertyName(s);
	}
	
	@Override 
	public synchronized void waitForResult() throws UserException {
		if (! templateMerged ) {
			super.waitForResult();
			if ( inDoc.getMessage(myEntity.getName()) != null ) {
				mergeWithEntityTemplate(inDoc.getMessage(myEntity.getName()), myEntity.getMessage());
				templateMerged = true;
			}
		}
	}
	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getMethod() {
		return method;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public void kill() {
	}

}
