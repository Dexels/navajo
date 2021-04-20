/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.messagemap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dexels.navajo.adapter.messagemap.PropertyAggregate.Aggregate;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ResultMessage implements Mappable {

	private Message msg;
	private Message parentMsg;
	private Navajo myNavajo;
	private Map<String,PropertyAggregate> aggregates = null;
	private boolean remove = false;
	
	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public Message getMsg() {
		return msg;
	}

	private String suppressProperties = null;
	private Message definitionMessage = null;
	
	public Message getDefinitionMessage() {
		return definitionMessage;
	}

	public void setAggregates(Map<String,PropertyAggregate> agg) {
		this.aggregates = agg;
	}
	
	private Aggregate getAggregate(String name) throws UserException {
		
		if ( aggregates == null || aggregates.size() == 0 ) {
			throw new UserException(-1, "No groupBy defined");
		}
		
		PropertyAggregate propAg = aggregates.get(name);
		
		if ( propAg != null ) {
			List<Property> properties = msg.getAllProperties();
			Map<String,Object> group = new TreeMap<String,Object>();
			for ( int i = 0; i < msg.getAllProperties().size(); i++ ) {
				group.put(properties.get(i).getName(), properties.get(i).getTypedValue());
			}
			Aggregate agg = propAg.getAggregate(group);
			return agg;
		} else {
			return null;
		}
	}

	public int getCount() throws UserException {
		if ( aggregates == null || aggregates.size() == 0 ) {
			throw new UserException(-1, "No groupBy defined");
		}
		String first = aggregates.keySet().iterator().next();
		return getCount(first);
	}
	public int getCount(String name) throws UserException {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getCount();
		} else {
			return 0;
		}
	}
	
	public double getAvg(String name) throws UserException {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getAvg();
		} else {
			return 0;
		}
	}
	
	public double getSum(String name) throws UserException {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getSum();
		} else {
			return 0;
		}
	}
	
	public Object getMin(String name) throws UserException {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getMin();
		} else {
			return null;
		}
	}
	
	public Object getMax(String name) throws UserException {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getMax();
		} else {
			return null;
		}
	}

    public String getConcatenated(String name) throws UserException {
        Aggregate agg = getAggregate(name);
        if ( agg != null ) {
            return agg.getConcatenated();
        } else {
            return null;
        }
    }

    public Object getFirst(String name) throws UserException  {
        Aggregate agg = getAggregate(name);
        if ( agg != null ) {
            return agg.getFirst();
        } else {
            return null;
        }
    }

	public void setMessage(Message definitionMessage, Message m, String suppressProperties) {
		this.msg = m;
		this.definitionMessage = definitionMessage;
		this.suppressProperties = suppressProperties;
	}
	
	private final boolean isPropertyInList(Property prop, String propertyStringList) {
		
		if ( propertyStringList == null ) {
			return false;
		}
		
		if (propertyStringList.equals("*") ) {
			return true;
		}
		
		String [] propertyList = propertyStringList.split(";");
		for (int i = 0; i < propertyList.length; i++) {
			if ( propertyList[i].equals(prop.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public final void processSuppressedProperties(Message m) {
		
		if ( m.isArrayMessage() && m.getDefinitionMessage() != null ) {
			processSuppressedProperties(m.getDefinitionMessage());
		}
		
		Iterator<Property> allProps = new ArrayList<Property>(m.getAllProperties()).iterator();
		while ( allProps.hasNext() ) {
			Property p = allProps.next();
			if ( isPropertyInList(p, this.suppressProperties) ) {
				m.removeProperty(p);
			}
		}
		Iterator<Message> subMessages = m.getAllMessages().iterator();
		while ( subMessages.hasNext() ) {
			processSuppressedProperties(subMessages.next());
		}
	}
	
	@Override
	public void kill() {
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		this.parentMsg = access.getCurrentOutMessage();
		
		if (  this.parentMsg.getArrayParentMessage() != null && definitionMessage != null ) {
			processSuppressedProperties(definitionMessage);
			Message copy = definitionMessage.copy(myNavajo);
			copy.setName(parentMsg.getName());
			this.parentMsg.getArrayParentMessage().setDefinitionMessage(copy);
		}
		this.myNavajo = access.getOutputDoc();
		Message copy = msg.copy(myNavajo);
		processSuppressedProperties(copy);
		parentMsg.merge(copy);
	}

	public boolean getExists(String s) {
		try {
			getProperty(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Object getProperty(String s) throws UserException {
		return getPropertyValue(s);
	}
	
	public Object getPropertyValue(String s) throws UserException {
		Property p = msg.getProperty(s);
		if ( p != null ) {
			if ( p.getType().equals(Property.SELECTION_PROPERTY) ) {
				return ( p.getSelected() != null ? p.getSelected().getValue() : (String) null );
			} else {
				return p.getTypedValue();
			}
		} else {
			throw new UserException(-1, "Exception in getting propertyValue for property: " + s);
		}
	}
	
	public Property getPropertyObject(String s) throws UserException {
		if ( msg.getProperty(s) != null ) {
			return msg.getProperty(s);
		} else {
			throw new UserException(-1, "Exception in getting property: " + s);
		}
	}
	
	public Object getPropertyOrElse(String fullName, Object elseValue) {
        Property p = msg.getProperty(fullName);
        if (p == null) {
            return elseValue;
        }
        if (p.getType().equals(Property.SELECTION_PROPERTY)) {
            if (p.getSelected() != null) {
                return p.getSelected().getValue();
            }
            return null;
        }
        return p.getTypedValue();
    }

	@Override
	public void store() throws MappableException, UserException {
	}

}
