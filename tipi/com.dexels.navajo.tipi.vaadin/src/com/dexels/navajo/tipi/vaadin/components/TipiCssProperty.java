package com.dexels.navajo.tipi.vaadin.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.document.SelectionBridge;
import com.dexels.navajo.tipi.vaadin.document.SelectionListBridge;
import com.dexels.navajo.tipi.vaadin.document.StaticTypeValuePropertyBridge;
import com.dexels.navajo.tipi.vaadin.document.ValuePropertyBridge;
import com.vaadin.data.Container.PropertySetChangeEvent;
import com.vaadin.data.Container.PropertySetChangeListener;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class TipiCssProperty extends TipiVaadinComponentImpl implements PropertyComponent {

	private static final long serialVersionUID = 142570190396385078L;
	private Label description;
	private Component value;
	private com.dexels.navajo.document.Property property;
	private String propertyName;
	private CssLayout container;
	
	private PropertyChangeListener myChangeListener = null;
	private boolean showLabel = false;
	private boolean forceReadOnly;
	private String selectiontype = "combo";
	private int memoColumnCount = 40;
	private int memoRowCount = 5;
	private ValuePropertyBridge currentDataSource;
    private final List<TipiEventListener> myListeners = new ArrayList<TipiEventListener>();
	private final static Logger logger = LoggerFactory
			.getLogger(TipiCssProperty.class);
    
	@Override
	public Object createContainer() {
		container = new CssLayout();
		description = new Label();
		description.addStyleName("property-description");
		container.addStyleName("property-container");
		container.addComponent(description);
		return container;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public void addTipiEventListener(TipiEventListener listener) {
        myListeners.add(listener);
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
		this.refreshPropertyValue();
		value.addStyleName("tipi-property-direction-"+p.getDirection());
		value.addStyleName("tipi-property-type-"+p.getType());
		if(style!=null) {
			value.addStyleName(style);
		}
	}
	
    public void propertyEventFired(com.dexels.navajo.document.Property p, String eventType) {
        try {
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("propertyName", property.getFullPropertyName());
            m.put("propertyValue", property.getTypedValue());
            m.put("new", property.getTypedValue());
            m.put("propertyType", property.getType());
            m.put("propertyLength", new Integer(property.getLength()));
            
            for (int i = 0; i < myListeners.size(); i++) {
                TipiEventListener tel = myListeners.get(i);
                tel.performTipiEvent(eventType, m, false);
            }
//            logger.info("propertyEvent params: "+m, new Exception());
            if (p == null) {
                return;
            }
            if (p != property) {
                logger.warn("Mysterious anomaly: Property of event is not the loaded property");
                return;
            }
            performTipiEvent(eventType, m, false);
            // }
        } catch (Exception ex) {
            logger.error("Error: ", ex);
        }
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
			try {
				createSelectionProperty();		
				value.addStyleName("tipi-property-cardinality-"+this.property.getCardinality());

			} catch (NavajoException e) {
				logger.error("Error: ",e);
			}
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
		currentDataSource = new ValuePropertyBridge(property,!forceReadOnly);
		DateField df = new DateField(currentDataSource);
		df.setDateFormat("dd-MM-yyyy");
		df.setResolution(DateField.RESOLUTION_DAY);
		df.setImmediate(true);
		df.addListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 855107266996731677L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				propertyEventFired(property, "onValueChanged");
				
			}
		});

		value = df;
		addPropertyComponent(df);
		container.addComponent(df);
	}
	private void createBooleanProperty() {
		currentDataSource = new ValuePropertyBridge(property,!forceReadOnly);
		CheckBox df = new CheckBox("",currentDataSource);
		df.setImmediate(true);
		value = df;
		df.addListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 855107266996731677L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				propertyEventFired(property, "onValueChanged");
				
			}
		});
		addPropertyComponent(df);
		container.addComponent(df);
	}
	private void createSelectionProperty() throws NavajoException {
		if(com.dexels.navajo.document.Property.CARDINALITY_MULTIPLE.equals(property.getCardinality())) {
			createMultiSelect();
		} else {
			createSingleCardinality();
		}
	}

	protected void createMultiSelect() throws NavajoException {
		SelectionListBridge selectionListBridge = new SelectionListBridge(property);

		if("picklist".equals(selectiontype)) {
			value = new TwinColSelect("caption",property.getAllSelections());
		} else {
			if("list".equals(selectiontype)) {
				value = new ListSelect("",selectionListBridge);
				((ListSelect)value).setMultiSelect(true);
			} else {
				value = new OptionGroup("",selectionListBridge);
				((OptionGroup)value).setMultiSelect(true);
			}
		}
		final AbstractSelect t = (AbstractSelect)value;
//		SelectedItemValuePropertyBridge selectionValueBridge =  new SelectedItemValuePropertyBridge(property);
		Collection<Object> selectedCollection = selectionListBridge.getSelectedCollection();
		t.setValue(selectedCollection);
		t.setImmediate(true);
		t.addListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateProperty(t,property);
			}
		});
		addPropertyComponent(value);
		t.addListener(new PropertySetChangeListener() {
			

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void containerPropertySetChange(PropertySetChangeEvent event) {
				propertyEventFired(property, "onValueChanged");

			}
		});
	}



	protected void updateProperty(AbstractSelect t,
			com.dexels.navajo.document.Property pp) {
		Collection<?> ss = t.getItemIds();
		for (Object object : ss) {
			String id = (String)object;
			Selection sel = pp.getSelection(id);
			boolean selected = t.isSelected(object);
			sel.setSelected(selected);
		}
	}

	private void createSingleCardinality() {
			SelectionListBridge selectionListBridge = new SelectionListBridge(property);
			if("radio".equals(selectiontype)) {
				value = new OptionGroup("",selectionListBridge);
			} else {
				value = new NativeSelect("",selectionListBridge);
			}
			final AbstractSelect t = (AbstractSelect)value;
			t.setNewItemsAllowed(false);
			property.addPropertyChangeListener(new SerializablePropertyChangeListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void propertyChange(PropertyChangeEvent pce) {
					if(pce.getPropertyName().equals("selection")) {
						propertyEventFired(property, "onValueChanged");
					}
				}
			});
			
//			value = new OptionGroup("Combo:",new SelectionListBridge(property));
			t.setImmediate(true);
			addPropertyComponent(value);
			SelectionBridge selected = selectionListBridge.getSelected();
			if(selected!=null) {
				Property selectedName = selected.getItemProperty("name");
				t.setValue(selectedName.getValue());
			}
			
			t.addListener(new Property.ValueChangeListener() {
				
				private static final long serialVersionUID = 1696480526302969095L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					try {
						Selection ss = property.getSelection((String) event.getProperty().getValue());
						property.setSelected(ss);
						performTipiEvent("onValueChanged", null, true);
					} catch (TipiBreakException e) {
						logger.error("Error: ",e);
					} catch (TipiException e) {
						logger.error("Error: ",e);
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					}
				}
			});
	
	}

	private boolean isEditable() {
		return property.isDirIn() && !forceReadOnly;
	}
	private void createTextualProperty() {
		AbstractTextField p = null;
		currentDataSource = new StaticTypeValuePropertyBridge(property,!forceReadOnly);
		if(com.dexels.navajo.document.Property.PASSWORD_PROPERTY.equals(property.getType())) {
			p = new PasswordField(currentDataSource);
		} else {
			if(com.dexels.navajo.document.Property.MEMO_PROPERTY.equals(property.getType())) {
				p = new TextArea(currentDataSource);
				p.setColumns(this.memoColumnCount);
				((TextArea)p).setRows(this.memoRowCount);
			} else {
				p = new TextField(currentDataSource);
//				p.setSizeUndefined();
			}
		
		}
//		p.setImmediate(true);
		value = p;
		p.addListener(new TextChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void textChange(TextChangeEvent event) {
				
				logger.info("New: "+event.getText()+" old value: "+property.getTypedValue()+" eventval: "+event.getText());
				currentDataSource.setRespondToServerSideChanges(false);
				TipiCssProperty.this.property.setValue(event.getText());
				currentDataSource.setRespondToServerSideChanges(true);
				propertyEventFired(property, "onValueChanged");
			}
		});
		addPropertyComponent(value);
	}

	private void addPropertyComponent(Component value) {
		container.addComponent(value);
	}


	private void clearPropertyValue() {
		container.removeComponent(value);
		description.setVisible(false);
	}

	@SuppressWarnings("unused")
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

	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("propertyValue")) {
			// ----
			return this.property.getTypedValue();
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("propertyValue")) {
			if(property!=null) {
				property.setAnyValue(object);
			}
		}
		if(name.equals("alwaysUseLabel")) {
			this.forceReadOnly  = (Boolean)object;
			if(currentDataSource!=null) {
				currentDataSource.setReadOnly(this.forceReadOnly);
			}
		}		
		if(name.equals("selectiontype")) {
			this.selectiontype   = (String)object;
		}		
		if(name.equals("showlabel")) {
			this.showLabel  = (Boolean)object;
		}
		// historical hack
		if(name.toLowerCase().equals("propertyname")) {
			this.propertyName = (String) object;
		}

		if(name.equals("memoColumnCount")) {
			this.memoColumnCount = (Integer) object;
		}
		if(name.equals("memoRowCount")) {
			this.memoRowCount = (Integer) object;
		}

		if(name.equals("visible")) {
			this.container.setVisible((Boolean)object);
		}
		
		super.setComponentValue(name, object);
	}

	@Override
	public Boolean isDirty() {
		// always not dirty
		return Boolean.FALSE;
	}

	@Override
	public void setDirty(Boolean b) {
		// ignore dirty
		
	}
}
