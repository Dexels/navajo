package com.dexels.navajo.tipi.vaadin.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Property;

public class PropertyAspectProperty implements Property, Property.ValueChangeNotifier {

	private static final long serialVersionUID = 1L;
	private final com.dexels.navajo.document.Property src;
	private final String propertyAspect;
	private final Map<ValueChangeListener,PropertyChangeListener> listenerMap = new HashMap<ValueChangeListener,PropertyChangeListener>();

	
	public PropertyAspectProperty(com.dexels.navajo.document.Property src, String aspect) {
		this.src = src;
		this.propertyAspect = aspect;
	}
	
	public String toString() {
		return ""+getValue();
	}

	@Override
	public Object getValue() {
		if(com.dexels.navajo.document.Property.PROPERTY_TYPE.equals(propertyAspect)) {
			return src.getType();
		}
		if(com.dexels.navajo.document.Property.PROPERTY_DESCRIPTION.equals(propertyAspect)) {
			return src.getDescription();
		}
		if(com.dexels.navajo.document.Property.PROPERTY_CARDINALITY.equals(propertyAspect)) {
			return src.getCardinality();
		}
		if(com.dexels.navajo.document.Property.PROPERTY_DIRECTION.equals(propertyAspect)) {
			return src.getDirection();
		}
		if(com.dexels.navajo.document.Property.PROPERTY_LENGTH.equals(propertyAspect)) {
			return src.getLength();
		}
		if(com.dexels.navajo.document.Property.PROPERTY_VALUE.equals(propertyAspect)) {
			return src.getTypedValue();
		}

		return null;
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		if(com.dexels.navajo.document.Property.PROPERTY_TYPE.equals(propertyAspect)) {
			src.setType(""+newValue);
		}
		if(com.dexels.navajo.document.Property.PROPERTY_DESCRIPTION.equals(propertyAspect)) {
			src.setDescription(""+newValue);
		}
		if(com.dexels.navajo.document.Property.PROPERTY_CARDINALITY.equals(propertyAspect)) {
			src.setCardinality(""+newValue);
		}
		if(com.dexels.navajo.document.Property.PROPERTY_DIRECTION.equals(propertyAspect)) {
			src.setDirection(""+newValue);
		}
		if(com.dexels.navajo.document.Property.PROPERTY_LENGTH.equals(propertyAspect)) {
			src.setLength((Integer)newValue);
		}
		if(com.dexels.navajo.document.Property.PROPERTY_VALUE.equals(propertyAspect)) {
			src.setAnyValue(newValue);
		}

	}

	@Override
	public Class<?> getType() {
		return String.class;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public void setReadOnly(boolean newStatus) {
		// ignore
	}

	public void addListener(final ValueChangeListener listener) {
		PropertyChangeListener pcl = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.err.println("VAADIN-side property change: "+evt.getOldValue()+" to "+evt.getNewValue()+" property-property: "+evt.getPropertyName());
				if(evt.getPropertyName().equals(propertyAspect)) {
					listener.valueChange(new ValueChangeEvent() {
						
						private static final long serialVersionUID = 1L;
	
						@Override
						public Property getProperty() {
							return PropertyAspectProperty.this;
						}
					});
				}
			}
		};
		listenerMap.put(listener, pcl);
		src.addPropertyChangeListener(pcl);
	}
	
	
	@Override
	public void removeListener(ValueChangeListener listener) {
		PropertyChangeListener pcl = listenerMap.get(listener);
		listenerMap.remove(listener);
		src.removePropertyChangeListener(pcl);
	}




}
