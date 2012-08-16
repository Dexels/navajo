package com.dexels.navajo.tipi.vaadin.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;
import com.vaadin.data.Property;

public class ValuePropertyBridge implements Property, Property.ValueChangeNotifier {
	private static final long serialVersionUID = -5696589046516267159L;
	private final Map<ValueChangeListener,SerializablePropertyChangeListener> listenerMap = new HashMap<ValueChangeListener,SerializablePropertyChangeListener>();
	protected final com.dexels.navajo.document.Property src;
	protected final boolean valueEditable;
	protected Object value;
	private boolean respondToServerSideChanges = true;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ValuePropertyBridge.class);
	
	public ValuePropertyBridge(com.dexels.navajo.document.Property src, boolean editable) {
		this.src = src;
		this.value = src.getTypedValue();
		this.valueEditable = editable;
	}
	
	public boolean isRespondToServerSideChanges() {
		return respondToServerSideChanges;
	}

	public void setRespondToServerSideChanges(boolean respondToServerSideChanges) {
		this.respondToServerSideChanges = respondToServerSideChanges;
	}

	@Override
	public Object getValue() {
		return value;
//		return src.getTypedValue();
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		if(src.isDirOut() || !valueEditable) {
			throw new ReadOnlyException();
		}
		String oldType = src.getType();
		src.setAnyValue(newValue);
		String newType = src.getType();
		if(!oldType.equals(newType)) {
			logger.warn("TYPE CHANGED. BAD NEWS. OLD: "+oldType+" new: "+newType);
		}
		// refresh
		this.value = src.getTypedValue();
	}

	@Override
	public Class<?> getType() {
		return NavajoFactory.getInstance().getJavaType(src.getType());
	}

	@Override
	public boolean isReadOnly() {
		return src.isDirOut() || !valueEditable;
	}

	public String toString() {
		Object o = getValue();
		if(o==null) {
			return "";
		}
		return ""+ o;
	}
	
	@Override
	public void setReadOnly(boolean newStatus) {
		logger.warn("Beware: changing readonly status?!");
		src.setDirection(newStatus?com.dexels.navajo.document.Property.DIR_OUT:com.dexels.navajo.document.Property.DIR_IN);
	}
	
	@Override
	public void addListener(final ValueChangeListener listener) {
		SerializablePropertyChangeListener pcl = new SerializablePropertyChangeListener() {
			private static final long serialVersionUID = -2846215480574110535L;

			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(!isRespondToServerSideChanges()) {
					logger.debug("Ignoring server side event!");
					return;
				}
				if(com.dexels.navajo.document.Property.PROPERTY_VALUE.equals(evt.getPropertyName())) {
					
					ValuePropertyBridge.this.value = evt.getNewValue();

					listener.valueChange(new ValueChangeEvent() {
						
						private static final long serialVersionUID = 1L;

						@Override
						public Property getProperty() {
							return ValuePropertyBridge.this;
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
//vaadin.document.ValuePropertyBridge.toString(ValuePropertyBridge.java:50)
//at com.vaadin.ui.AbstractField.setPrope
