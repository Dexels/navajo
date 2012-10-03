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
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

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
	
	public void setAggregates(Map<String,PropertyAggregate> agg) {
		this.aggregates = agg;
	}
	
	private Aggregate getAggregate(String name) {
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
	
	public int getCount(String name) {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getCount();
		} else {
			return 0;
		}
	}
	
	public double getAvg(String name) {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getAvg();
		} else {
			return 0;
		}
	}
	
	public double getSum(String name) {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getSum();
		} else {
			return 0;
		}
	}
	
	public Object getMin(String name) {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getMin();
		} else {
			return null;
		}
	}
	
	public Object getMax(String name) {
		Aggregate agg = getAggregate(name);
		if ( agg != null ) {
			return agg.getMax();
		} else {
			return null;
		}
	}
	
	public void setMessage(Message m, String suppressProperties) {
		this.msg = m;
		this.suppressProperties = suppressProperties;
	}
	
	private final boolean isPropertyInList(Property prop, String propertyStringList, boolean isArrayMessageElement) {
		if ( propertyStringList == null ) {
			return false;
		}
		String [] propertyList = propertyStringList.split(";");
		for (int i = 0; i < propertyList.length; i++) {
			if ( propertyList[i].equals(prop.getName())) {
				return true;
			}
		}
		return false;
	}
	
	private final void processSuppressedProperties(Message m) {
		Iterator<Property> allProps = new ArrayList<Property>(m.getAllProperties()).iterator();
		while ( allProps.hasNext() ) {
			Property p = allProps.next();
			if ( isPropertyInList(p, this.suppressProperties, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) ) {
				m.removeProperty(p);
			}
		}
		Iterator<Message> subMessages = m.getAllMessages().iterator();
		while ( subMessages.hasNext() ) {
			processSuppressedProperties(subMessages.next());
		}
	}
	
	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
		this.parentMsg = access.getCurrentOutMessage();
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
		if ( msg.getProperty(s) != null ) {
			return msg.getProperty(s).getTypedValue();
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

	public void store() throws MappableException, UserException {
		processSuppressedProperties(this.msg); 
	}

}
