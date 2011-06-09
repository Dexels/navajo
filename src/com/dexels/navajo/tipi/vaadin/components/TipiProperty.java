package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class TipiProperty extends TipiVaadinComponentImpl implements PropertyComponent {

	private Label description;
	private TextField value;
	private Property property;
	private String propertyName;
	private int label_indent;
	
	@Override
	public Object createContainer() {
		HorizontalLayout container = new HorizontalLayout();
		description = new Label();
		value = new TextField();
		container.addComponent(description);
		container.addComponent(value);
		
		return container;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public void addTipiEventListener(TipiEventListener listener) {
		
	}

	@Override
	public Property getProperty() {
		return this.property;
	}

	@SuppressWarnings("serial")
	@Override
	public void setProperty(Property p) {
		this.property = p;
		description.setValue(p.getDescription());
		value.setValue(p.getTypedValue());
		value.addListener(new TextChangeListener() {
			
			@Override
			public void textChange(TextChangeEvent event) {
				TipiProperty.this.property.setAnyValue(value.getValue());
				System.err.println("Property value changed: "+value.getValue());
			}
		});
		value.addListener(new BlurListener() {
			
			@Override
			public void blur(BlurEvent event) {
				TipiProperty.this.property.setAnyValue(value.getValue());
				System.err.println("Property value changed: "+value.getValue());
			
			}
		});
		//		value.setCaption("Caption: "+p.getDescription());
	}
	protected Object getComponentValue(String name) {
		if(name.equals("propertyValue")) {
			// ----
			return this.property.getTypedValue();
		}
		return super.getComponentValue(name);
	}

	protected void setComponentValue(String name, Object object) {
		if(name.equals("propertyValue")) {
			if(property!=null) {
				property.setAnyValue(object);
			}
		}
		// historical hack
		if(name.toLowerCase().equals("propertyname")) {
			this.propertyName = (String) object;
		}
		if(name.toLowerCase().equals("label_indent")) {
			this.label_indent = (Integer) object;
			description.setWidth(this.label_indent, Sizeable.UNITS_PIXELS);
		}

		
		super.setComponentValue(name, object);
	}

}
