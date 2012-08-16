package com.dexels.navajo.tipi.vaadin.document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private final boolean editable;

	private final List<SelectionBridge> selections = new ArrayList<SelectionBridge>();
	private SelectionListBridge selectionListBridge;
	
	public SelectedItemValuePropertyBridge(com.dexels.navajo.document.Property src) {
		this(src,src.isDirIn());
	}
	
	public SelectedItemValuePropertyBridge(com.dexels.navajo.document.Property src, boolean editable) {
		this.src = src;
		this.editable = editable;
		if(!com.dexels.navajo.document.Property.SELECTION_PROPERTY.equals(src.getType())) {
			throw new UnsupportedOperationException("Can not create a SelectedItemValuePropertyBridge with a non selection property.");
		}
//		Selection s= src.getSelected();

		for (Selection sel : src.getAllSelections()) {
			SelectionBridge sb = new SelectionBridge( sel);
			selections.add(sb);
		}
	}
	
	
	public SelectionListBridge createListBridge() {
		selectionListBridge = new SelectionListBridge(src);
		return selectionListBridge;
	}
	@Override
	public Object getValue() {
		try {
//			Selection selected = src.getSelected();
//			return selected;
			if(selectionListBridge==null) {
				selectionListBridge = new SelectionListBridge(src);
			}
			SelectionBridge selectionBridge = selectionListBridge.getSelectionBridge();
			if(selectionBridge==null) {
				return null;
			}
			Property itemProperty = selectionBridge.getItemProperty("name");
			if(itemProperty==null) {
				return null;
			}
			return itemProperty.getValue();
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		if(src.isDirOut() || !editable) {
			throw new ReadOnlyException();
		}
		try {
//			selectionListBridge.select((String) newValue);
			Selection ss = src.getSelection((String)newValue);
			src.setSelected(ss);
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
		return src.isDirOut() || !editable;
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
