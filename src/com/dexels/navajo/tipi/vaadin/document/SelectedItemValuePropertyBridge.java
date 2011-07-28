package com.dexels.navajo.tipi.vaadin.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;
import com.vaadin.data.Property;

public class SelectedItemValuePropertyBridge implements Property, Property.ValueChangeNotifier {
	private static final long serialVersionUID = -5696589046516267159L;
	private final Map<ValueChangeListener,SerializablePropertyChangeListener> listenerMap = new HashMap<ValueChangeListener,SerializablePropertyChangeListener>();
	private final com.dexels.navajo.document.Property src;
	
	public SelectedItemValuePropertyBridge(com.dexels.navajo.document.Property src) {
		this.src = src;
		if(!com.dexels.navajo.document.Property.SELECTION_PROPERTY.equals(src.getType())) {
			throw new UnsupportedOperationException("Can not create a SelectedItemValuePropertyBridge with a non selection property.");
		}
	}
	
	@Override
	public Object getValue() {
		try {
			Selection selected = src.getSelected();
			if(selected!=null) {
				return selected.getValue();
			}
			return null;
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		if(src.isDirOut()) {
			throw new ReadOnlyException();
		}
		try {
			Selection found = src.getSelectionByValue((String) newValue);
			src.setSelected(found);
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new ConversionException("Problem resolving selected property.");
		}

	}

	@Override
	public Class<?> getType() {
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
		SerializablePropertyChangeListener pcl = new SerializablePropertyChangeListener() {
			private static final long serialVersionUID = -2846215480574110535L;

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.err.println("VAADIN-side property change: "+evt.getOldValue()+" to "+evt.getNewValue()+" property-property: "+evt.getPropertyName());
				listener.valueChange(new ValueChangeEvent() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public Property getProperty() {
						return SelectedItemValuePropertyBridge.this;
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
