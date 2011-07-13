package com.dexels.navajo.tipi.vaadin.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.NavajoFactory;
import com.vaadin.data.Property;

public class ValuePropertyBridge implements Property, Property.ValueChangeNotifier {
	private static final long serialVersionUID = -5696589046516267159L;
	private final com.dexels.navajo.document.Property src;
	private final String originalType;
	private final Map<ValueChangeListener,PropertyChangeListener> listenerMap = new HashMap<ValueChangeListener,PropertyChangeListener>();
	
	public ValuePropertyBridge(com.dexels.navajo.document.Property src) {
		this.src = src;
		originalType = src.getType();
	}
	
	@Override
	public Object getValue() {
		return src.getTypedValue();
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		if(src.isDirOut()) {
			throw new ReadOnlyException();
		}
		String oldType = src.getType();
		src.setAnyValue(newValue);
		System.err.println("PROPCHANGE TO: "+newValue);
		String newType = src.getType();
		if(!oldType.equals(newType)) {
			System.err.println("TYPE CHANGED. BAD NEWS. OLD: "+oldType+" new: "+newType);
		}
	}

	@Override
	public Class<?> getType() {
//		Object val = src.getTypedValue();
//		if(val!=null) {
//			return val.getClass();
//		}
		return NavajoFactory.getInstance().getJavaType(src.getType());
	}

	@Override
	public boolean isReadOnly() {
		return src.isDirOut();
	}

	public String toString() {
		return ""+getValue();
	}
	
	@Override
	public void setReadOnly(boolean newStatus) {
		src.setDirection(newStatus?com.dexels.navajo.document.Property.DIR_OUT:com.dexels.navajo.document.Property.DIR_IN);
	}
	@Override
	public void addListener(final ValueChangeListener listener) {
		PropertyChangeListener pcl = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.err.println("VAADIN-side property change: "+evt.getOldValue()+" to "+evt.getNewValue()+" property-property: "+evt.getPropertyName());
				listener.valueChange(new ValueChangeEvent() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public Property getProperty() {
						return ValuePropertyBridge.this;
					}
				});
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
