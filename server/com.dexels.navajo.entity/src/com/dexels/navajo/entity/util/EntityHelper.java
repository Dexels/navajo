package com.dexels.navajo.entity.util;

import java.util.Iterator;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class EntityHelper {

	public static void mergeWithEntityTemplate(Message msg, Message templateMessage) {
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
}
