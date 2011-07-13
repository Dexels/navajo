package com.dexels.navajo.tipi.vaadin.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.document.SelectionListBridge;
import com.dexels.navajo.tipi.vaadin.document.ValuePropertyBridge;
import com.vaadin.data.Container.PropertySetChangeEvent;
import com.vaadin.data.Container.PropertySetChangeListener;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class TipiProperty extends TipiVaadinComponentImpl implements PropertyComponent {

	private Label description;
	private Component value;
	private com.dexels.navajo.document.Property property;
	private String propertyName;
	private int label_indent;
	private HorizontalLayout container;
	
	private PropertyChangeListener myChangeListener = null;
	private boolean showLabel = false;
	private boolean forceReadOnly;
	
	@Override
	public Object createContainer() {
		container = new HorizontalLayout();
		container.setMargin(false);
		description = new Label();
//		description.setHeight("12px");
		container.addComponent(description);
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
	public com.dexels.navajo.document.Property getProperty() {
		return this.property;
	}

	@Override
	public void setProperty(com.dexels.navajo.document.Property p) {
		if(this.property!=null && myChangeListener!=null) {
			this.property.removePropertyChangeListener(myChangeListener);
			myChangeListener = null;

		}
		this.property = p;

		myChangeListener = new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				refreshPropertyValue();
			}
		};

		this.property.addPropertyChangeListener(myChangeListener);
		
		this.refreshPropertyValue();
	
			//		value.setCaption("Caption: "+p.getDescription());
	}
	protected void refreshPropertyValue() {
		if(this.property==null) {
			clearPropertyValue();
			return;
		}
		description.setVisible(showLabel);
		String descr = property.getDescription();
		if("".equals(descr)) {
			descr = null;
		}
		description.setValue(descr);
		
		if(value!=null) {
			container.removeComponent(value);
			value=null;
		}
		if(com.dexels.navajo.document.Property.SELECTION_PROPERTY.equals(property.getType())) {
			// create selection property
			createSelectionProperty();
		}
		if(com.dexels.navajo.document.Property.DATE_PROPERTY.equals(property.getType())) {
			// create date property
			createDateProperty();
		}
//		if(com.dexels.navajo.document.Property.CLOCKTIME_PROPERTY.equals(property.getType())) {
			// create date property
//			createClockTimeProperty();
//		}

		if(com.dexels.navajo.document.Property.BOOLEAN_PROPERTY.equals(property.getType())) {
			// create date property
			createBooleanProperty();
		}

		if (value==null) {

			createTextualProperty();
		}
		value.setCaption(null);
		value.setEnabled(isEditable());

	}

	private void createDateProperty() {
		DateField df = new DateField(new ValuePropertyBridge(property));
		df.setDateFormat("dd-MM-yyyy");
		df.setImmediate(true);
		value = df;
		addPropertyComponent(df);
		container.addComponent(df);
	}
	private void createBooleanProperty() {
		CheckBox df = new CheckBox("",new ValuePropertyBridge(property));
		df.setImmediate(true);
		value = df;
		addPropertyComponent(df);
		container.addComponent(df);
	}
	private void createSelectionProperty() {
		if(com.dexels.navajo.document.Property.CARDINALITY_MULTIPLE.equals(property.getCardinality())) {
			// TODO Add list selection and checkboxes
			try {
				value = new TwinColSelect("caption",property.getAllSelections());
				addPropertyComponent(value);
				TwinColSelect t = (TwinColSelect)value;
				t.addListener(new PropertySetChangeListener() {
					
		
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void containerPropertySetChange(PropertySetChangeEvent event) {
						System.err.println("CHAAAANGE");
					}
				});
				//				value.set;

			} catch (NavajoException e) {
				e.printStackTrace();
			}
			
			return;
		}
		createComboBox();
	}


	private void createRadioButtonGroup() {
		try {
			OptionGroup o = new OptionGroup("", property.getAllSelections());
			value = o;
			container.addComponent(o);
//			o.addListener()
		} catch (NavajoException e) {
			e.printStackTrace();
		}

	}
	
	private void createComboBox() {
			value = new ComboBox("",new SelectionListBridge(property));
//			value = new OptionGroup("Combo:",new SelectionListBridge(property));
			final ComboBox t = (ComboBox)value;
			t.setImmediate(true);
			addPropertyComponent(value);
			t.addListener(new Property.ValueChangeListener() {
				
				private static final long serialVersionUID = 1696480526302969095L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						System.err.println("Value: "+event.getProperty().getValue());
						property.setSelected(property.getSelection((String) (event.getProperty().getValue())));
						performTipiEvent("onValueChanged", null, true);
					} catch (TipiBreakException e) {
						e.printStackTrace();
					} catch (TipiException e) {
						e.printStackTrace();
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
			});
	
	}

	private boolean isEditable() {
		return property.isDirIn() && !forceReadOnly;
	}
	private void createTextualProperty() {
		AbstractTextField p = null;
		if(com.dexels.navajo.document.Property.PASSWORD_PROPERTY.equals(property.getType())) {
			p = new PasswordField(new ValuePropertyBridge(property));
		} else {
			if(com.dexels.navajo.document.Property.MEMO_PROPERTY.equals(property.getType())) {
				// TODO Test
				p = new TextArea(new ValuePropertyBridge(property));
			} else {
				p = new TextField(new ValuePropertyBridge(property));
							
			}
		
		}
		p.setImmediate(true);
		value = p;
		final AbstractTextField q = p;
		p.addListener(new TextChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void textChange(TextChangeEvent event) {
				TipiProperty.this.property.setAnyValue(q.getValue());
				System.err.println("Property value changed: " + q.getValue());
			}
		});
		addPropertyComponent(value);
	}

	private void addPropertyComponent(Component value) {
		container.addComponent(value);
		container.setExpandRatio(value, 1);
	}


	private void clearPropertyValue() {
		container.removeComponent(value);
		description.setVisible(false);
	}

	private boolean isTextualProperty(com.dexels.navajo.document.Property p) {
		if(com.dexels.navajo.document.Property.SELECTION_PROPERTY.equals(p.getType())) {
			return false;
		}
		if(com.dexels.navajo.document.Property.BOOLEAN_PROPERTY.equals(p.getType())) {
			return false;
		}
		if(com.dexels.navajo.document.Property.DATE_PROPERTY.equals(p.getType())) {
			return false;
		}
		return true;
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
		if(name.equals("alwaysUseLabel")) {
			this.forceReadOnly  = (Boolean)object;
		}		
		if(name.equals("showlabel")) {
			this.showLabel  = (Boolean)object;
		}
		// historical hack
		if(name.toLowerCase().equals("propertyname")) {
			this.propertyName = (String) object;
		}
		if(name.toLowerCase().equals("label_indent")) {
			this.label_indent = (Integer) object;
			description.setWidth(this.label_indent, Sizeable.UNITS_PIXELS);
		}

		if(name.equals("visible")) {
			this.container.setVisible((Boolean)object);
		}
		
		super.setComponentValue(name, object);
	}

}
