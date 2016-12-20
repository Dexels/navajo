package com.dexels.navajo.entity.util;

import java.util.Iterator;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.Entity;

public class EntityHelper {

	/** Similar to BaseMessageImpl->mask, but adds missing properties/messages 
	 * 
	 * @param msg
	 * @param templateMessage
	 * @param direction If defined, messages are only added when the direction matches
	 */
	public static void mergeWithEntityTemplate(Message msg, Message templateMessage, String direction) {
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
			if (! (e_p.getDirection().equals("")) || (e_p.getDirection().equals(direction))) {
				// not interested
				break;
			}
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
				if ( p == null) {
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
						mergeWithEntityTemplate(m.getMessage(i), e_m, direction);
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
					mergeWithEntityTemplate(m, e_m, direction);
				} else {
					m = NavajoFactory.getInstance().createMessage(msg.getRootDoc(), e_m.getName());
					msg.addMessage(m);
				}
			}
		}
	}
	
	public static Navajo deriveNavajoFromParameterMap(Entity entity, Map<String, String[]> parameters) {

		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, entity.getMessageName());
		n.addMessage(m);

		for (String key : parameters.keySet()) {

			String propertyName = key;
			if (!propertyName.startsWith("/" + entity.getMessageName() + "/")) {
				propertyName = "/" + entity.getMessageName() + "/" + propertyName;
			}
			Property prop = entity.getMessage().getProperty(propertyName);
			if (prop != null) {
				Property prop_copy = prop.copy(n);
				String propValue = parameters.get(key)[0];
				prop_copy.setUnCheckedStringAsValue(propValue);
				m.addProperty(prop_copy);
			}

		}

		return n;
	}

}
