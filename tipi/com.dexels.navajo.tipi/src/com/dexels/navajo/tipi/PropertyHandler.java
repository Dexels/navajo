package com.dexels.navajo.tipi;

import java.beans.PropertyChangeEvent;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;

public class PropertyHandler implements SerializablePropertyChangeListener {

	private static final long serialVersionUID = -5924246809125982153L;
	private Property myProperty;
	private Set<PropertyLinkRequest> myMapping;
	private final TipiComponent myComponent;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyHandler.class);
	
	public PropertyHandler(TipiComponent tc, Property p) {
		this(tc, p, null);
	}

	public PropertyHandler(TipiComponent tc, Property p,
			Set<PropertyLinkRequest> mapping) {
		myMapping = mapping;
		myComponent = tc;
		setProperty(p);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		logger.debug("EVENT: " + evt.getPropertyName() + "val: "
				+ evt.getNewValue());
		updateAspect(evt.getPropertyName(), evt.getNewValue());

	}

	/**
	 * @param evt
	 */
	private void updateAspect(String aspect, Object value) {
		if (myMapping == null) {
			return;
		}
		for (PropertyLinkRequest r : myMapping) {
			if (myComponent == null) {
				throw new RuntimeException("wtf?");
			}
			if (r.getAspect().equals(aspect)) {
				myComponent.setValue(r.getAttributeName(), value);
			}
		}
	}

	private void initProperty(Property p) {
		updateAspect("description", p.getTypedValue());
		updateAspect("value", p.getTypedValue());
		updateAspect("length", p.getLength());
		updateAspect("direction", p.getDirection());
		updateAspect("type", p.getType());
		updateAspect("cardinality", p.getCardinality());
		updateAspect("subtype", p.getSubType());
	}

	// public void addMapping(String key, String value) {
	// if(myMapping==null) {
	// myMapping = new HashMap<String, String>();
	// }
	// myMapping.put(key, value);
	// }

	public void setProperty(Property p) {
		if (p == myProperty) {
			return;
		}
		if (myProperty != null) {
			myProperty.removePropertyChangeListener(this);
		}
		myProperty = p;
		p.addPropertyChangeListener(this);
		initProperty(p);
	}
}
