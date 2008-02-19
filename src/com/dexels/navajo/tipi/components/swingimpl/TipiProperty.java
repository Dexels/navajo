package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.*;
import java.beans.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiProperty extends TipiSwingComponentImpl implements PropertyComponent, PropertyChangeListener, PropertyValidatable, PropertyEventListener {
	private Property myProperty = null;
	private List<TipiEventListener> myListeners = new ArrayList<TipiEventListener>();
	// private boolean myVisibleState = true;
	// private Boolean myEnableState = null;
	// private String selectionType = "default";
	// private String myCapitalization = "off";
	// private String vAlign = null;
	// private String hAlign = null;
	// private boolean isLoading = false;
	// private String currentType = "";
	// private boolean showDatePicker = false;
	// private boolean verticalScrolls = true;
	// private boolean horizontalScrolls = false;

	TipiSwingPropertyComponent p = null;

	public TipiProperty(Property p) {
		setProperty(p);
	}

	public TipiProperty() {
		// initContainer();
		super();
	}

	public Object createContainer() {
		p = new TipiSwingPropertyComponent(this);
		p.addPropertyEventListener(this);

		p.setFormatter(getContext());
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		addTipiEventListener(this);
		p.addPropertyKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				Map<String,Object> m = getEventMap(e);
				m.put("mode", "typed");
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						performTipiEvent("onEnter", m, true);
					} catch (TipiException e1) {
						e1.printStackTrace();
					}
				}
				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void keyPressed(KeyEvent e) {
				Map<String,Object> m = getEventMap(e);
				m.put("mode", "typed");
				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void keyReleased(KeyEvent e) {
				Map<String,Object> m = getEventMap(e);
				m.put("mode", "released");
				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public Map<String,Object> getEventMap(KeyEvent e) {
				Map<String,Object> hm = new HashMap<String,Object>();
				hm.put("code", new Integer(e.getKeyCode()));
				hm.put("modifiers", KeyEvent.getKeyModifiersText(e.getModifiers()));
				hm.put("key", KeyEvent.getKeyText(e.getKeyCode()));
				return hm;
			}
		});

		return p;
	}

	public Property getProperty() {
		return myProperty;
	}

	public void setLabelWidth(final int width) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				((GenericPropertyComponent) getContainer()).setLabelIndent(width);
			}
		});
	}

	@Override
	public void addTipiEvent(TipiEvent te) {
		myEventList.add(te);
	}

	public void setLabelVisible(final boolean state) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				if (state) {
					((GenericPropertyComponent) getContainer()).showLabel();
				} else {
					((GenericPropertyComponent) getContainer()).hideLabel();
				}
			}
		});
	}

	public boolean isLabelVisible() {
		return ((GenericPropertyComponent) getContainer()).isLabelVisible();
	}

	public void setProperty(final Property p) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				if (getAttributeProperty("propertyValue") != null) {
					getAttributeProperty("propertyValue").removePropertyChangeListener(TipiProperty.this);
				}
				if(myProperty!=null) {
					myContext.unlink(myProperty);
				}
//				if(p!=null) {
//					p.addPropertyChangeListener(TipiProperty.this);
//				}
				myProperty = p;
				myContext.link(myProperty, getAttributeProperty("propertyValue"));
//					System.err.println("My attributeListenerprop has: "+getAttributeProperty("propertyValue").l);
					// getAttributeProperty("propertyValue").setAnyValue(myProperty);

//				myProperty.addPropertyChangeListener(new PropertyChangeListener() {
//
//					public void propertyChange(PropertyChangeEvent arg0) {
//						GenericPropertyComponent gp = (GenericPropertyComponent) getContainer();
//						gp.setProperty(myProperty);
//					}
//				});

				if (p == null) {
					return;
				}
				
				if(getAttributeProperty("propertyValue")==null) {
					System.err.println("BAMMMM! BOMMM!");
				} else {
					getAttributeProperty("propertyValue").addPropertyChangeListener(TipiProperty.this);
					System.err.println("Linkt to prop!: "+TipiProperty.this);
				}				
				// currentType = p.getType();
				((GenericPropertyComponent) getContainer()).setProperty(p);

				// Based on 'legacy behaviour' Removed this behaviour from
				// swingclient, but would require
				// a lot of fixes in tipi code, so replaced it here.

				// Extra fix for binaries: getValue() causes the binary to
				// serialize to an in memory string
				// if (!Property.BINARY_PROPERTY.equals(p.getType()) &&
				// p.getValue()!=null) {
				// ( (GenericPropertyComponent)
				// getContainer()).setTextFieldColumns(p.getValue().length());
				// } else {
				// ( (GenericPropertyComponent)
				// getContainer()).setTextFieldColumns(3);
				// }
				if (getEnabledState() != null) {
					((GenericPropertyComponent) getContainer()).setHardEnabled(getEnabledState().booleanValue());
				}
				//((GenericPropertyComponent) getContainer()).addt
			}

			private Boolean getEnabledState() {
				return new Boolean(TipiProperty.this.p.isEnabled());
			}

		});
	}

	public void resetComponentValidationStateByRule(final String id) {
		GenericPropertyComponent tpp = (GenericPropertyComponent) getContainer();
		tpp.resetComponentValidationStateByRule(id);
	}

	public void checkForConditionErrors(Message msg) {
		GenericPropertyComponent tpp = (GenericPropertyComponent) getContainer();
		tpp.checkForConditionErrors(msg);
	}

	public void addTipiEventListener(TipiEventListener listener) {
		if (listener == null) {
		}
		myListeners.add(listener);
	}

	public String getPropertyName() {
		TipiSwingPropertyComponent tpc = (TipiSwingPropertyComponent) getContainer();
		String name = tpc.getPropertyName();
		if (name != null) {
			return name;
		}
		return getId();
	}

	
	public void propertyEventFired(Property p, String eventType, Validatable ve) {
		propertyEventFired(p, eventType, ve, null);
	}
	public void propertyEventFired(Property p, String eventType, Validatable v, Object oldValue) {
//		Thread.dumpStack();
		System.err.println("Entering propertyEventFired: old: "+oldValue+" new: "+p.getTypedValue()+" stringvalue: "+p.getValue());
		if (p == null) {
			System.err.println("Trying to fire event from null property!");
			return;
		}
		if (p != myProperty) {
			System.err.println("Mysterious anomaly: Property of event is not the loaded property");
			return;
		}
		if (eventType.equals("onFocusGained") && v != null) {
			// Validatable v = (Validatable) currentPropertyComponent;
			// ArrayList rules = v.getConditionRuleIds();
			// if (rules != null) {
			// for (int i = 0; i < rules.size(); i++) {
			// String id = (String) rules.get(i);
			// myContext.resetConditionRuleById(id);
			// }
			// }
		}
		try {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("propertyName", myProperty.getFullPropertyName());
			m.put("propertyValue", myProperty.getTypedValue());
			m.put("propertyType", myProperty.getType());
			m.put("propertyLength", new Integer(myProperty.getLength()));
			// PropertyImpl p = (PropertyImpl)myProperty;
			for (int i = 0; i < myListeners.size(); i++) {
				TipiEventListener current = myListeners.get(i);
				current.performTipiEvent(eventType, m, false);
	
			}
			System.err.println("Firing event type: "+eventType+" m: "+m);
			performTipiEvent(eventType, m, false);
	} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void updateProperty() {
		((GenericPropertyComponent) getContainer()).updateProperty();
	}

	protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
		if ("updateProperty".equals(name)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					updateProperty();
				}
			});
		} else if ("requestFocus".equals(name)) {
			requestPropertyFocus();
		}
		if ("selectByName".equals(name)) {
			String selectionName = (String) compMeth.getEvaluatedParameter("value", event).value;
			if (myProperty != null) {
				try {
					Selection s = myProperty.getSelection(selectionName);
					myProperty.setSelected(s);
					setProperty(myProperty);
				} catch (NavajoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ("selectByValue".equals(name)) {
			String selectionValue = (String) compMeth.getEvaluatedParameter("value", event).value;
			if (myProperty != null) {
				try {
					myProperty.setSelected(selectionValue);
					setProperty(myProperty);
				} catch (NavajoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void disposeComponent() {

		myContext.unlink(getAttributeProperty("propertyValue"));
	super.disposeComponent();
		
	}

	private void requestPropertyFocus() {
		((GenericPropertyComponent) getContainer()).requestPropertyFocus();
	}

	public void propertyChange(final PropertyChangeEvent e) {
		
		runSyncInEventThread(new Runnable(){

			public void run() {
				if(e.getPropertyName().equals("value")) {
					((GenericPropertyComponent) getContainer()).updatePropertyValue(e);
					propertyEventFired((Property)e.getSource(), "onValueChanged", p,e.getOldValue());
				} else {
					// direction, cardinality, length, or description change:
					((GenericPropertyComponent) getContainer()).setProperty(getProperty());
					if(e.getPropertyName().equals("cardinality")) {
						propertyEventFired((Property)e.getSource(), "onCardinalityChanged", p,e.getOldValue());
					}
					if(e.getPropertyName().equals("length")) {
						propertyEventFired((Property)e.getSource(), "onLengthChanged", p,e.getOldValue());
					}
					if(e.getPropertyName().equals("description")) {
						propertyEventFired((Property)e.getSource(), "onDescriptionChanged", p,e.getOldValue());
					}
					if(e.getPropertyName().equals("direction")) {
						propertyEventFired((Property)e.getSource(), "onDirectionChanged", p,e.getOldValue());
					}
					if(e.getPropertyName().equals("type")) {
						propertyEventFired((Property)e.getSource(), "onTypeChanged", p,e.getOldValue());
					}
				}			
			}
			
		});

	}


}
