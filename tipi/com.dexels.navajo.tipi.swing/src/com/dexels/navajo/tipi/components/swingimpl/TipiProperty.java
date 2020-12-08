/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.DocumentPropertyChangeEvent;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingPropertyComponent;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.swingclient.components.GenericPropertyComponent;
import com.dexels.navajo.tipi.swingclient.components.PropertyEventListener;

public class TipiProperty extends TipiSwingComponentImpl implements
		PropertyComponent, PropertyChangeListener, PropertyEventListener {
	
	private static final long serialVersionUID = 8034864905090272048L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiProperty.class);
	
	private Property myProperty = null;
	private Boolean myPropertyIsDirty = Boolean.FALSE;
	private Object myPropertyValue = null;
	private List<TipiEventListener> myListeners = new ArrayList<TipiEventListener>();

	TipiSwingPropertyComponent p = null;

    private Boolean handleValueChangeSync = true;

	public TipiProperty(Property p) {
		setProperty(p);
	}

	public TipiProperty() {
		// initContainer();
		super();
	}
	
	
	

	@Override
	public Object createContainer() {
		p = new TipiSwingPropertyComponent(this);
		p.addPropertyEventListener(this);

		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		addTipiEventListener(this);
		p.addPropertyKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "typed");
				
				
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
				

				// OS X seems to have problems with keyReleased, and at keyTyped
				// the keycode is lost. For OS X we thus handle simulate the 
				// 'released' event here
				if (System.getProperty("os.name").equals("Mac OS X")) {
					Map<String, Object> mosx = getEventMap(e);
					mosx.put("mode", "released");
					if (e.getKeyCode() == KeyEvent.VK_ENTER){ 
						// Os x gives a newline char as key for the enter key
						mosx.put("key", "Enter");
						
						try {
							performTipiEvent("onEnter", mosx, false);
						} catch (TipiException e1) {
							logger.error("Error detected",e1);
						}
					}
					try {
						performTipiEvent("onKey", mosx, false);
					} catch (TipiException e1) {
						logger.error("Error detected",e1);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			    // If OS X for whatever reason suddently does send the release event, ignore it 
			    // since we already processed this in the keyPressed
			    if (System.getProperty("os.name").equals("Mac OS X")) { 
			        return;
			    
			    }
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "released");
				
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			public Map<String, Object> getEventMap(KeyEvent e) {
				Map<String, Object> hm = new HashMap<String, Object>();
				hm.put("code", Integer.valueOf(e.getKeyCode()));
				hm.put("modifiers",
						InputEvent.getModifiersExText(e.getModifiersEx()));
				hm.put("key", KeyEvent.getKeyText(e.getKeyCode()));
				return hm;
			}
		});

		return p;
	}

	@Override
	public Property getProperty() {
		return myProperty;
	}

	public void setLabelWidth(final int width) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				((GenericPropertyComponent) getContainer())
						.setLabelIndent(width);
			}
		});
	}

	public void setValueWidth(final int width) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				((GenericPropertyComponent) getContainer())
						.setPropertyWidth(width);
			}
		});
	}
	
	public void setLabelColor(final Color c) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				((GenericPropertyComponent) getContainer())
						.setLabelColor(c);
			}
		});
	}

	
	public void setLabelFont(final Font f) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				((GenericPropertyComponent) getContainer())
						.setLabelFont(f);
			}
		});
	}
	@Override
	public void addTipiEvent(TipiEvent te) {
		myEventList.add(te);
	}

	public void setLabelVisible(final boolean state) {
		runSyncInEventThread(new Runnable() {
			@Override
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

	@Override
	protected Object getComponentValue(String name) {
		if (name.equals("propertyValue")) {
			// ----
			return myPropertyValue;
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("propertyValue")) {
			if (myProperty != null) {
				myProperty.setAnyValue(object);
			}
			myPropertyValue = object;
		}
		if (name.equals("labelColor")) {
			setLabelColor((Color)object);
		}
		if (name.equals("labelFont")) {
			setLabelFont((Font)object);
		}
		
		  if (name.equals("handleValueChangeSync")) {
		      setHandleValueChangeSync((Boolean)object);
	        }
		
		super.setComponentValue(name, object);
	}

	private void setHandleValueChangeSync(Boolean object) {
        this.handleValueChangeSync = object;
        
    }

    @Override
	public void setProperty(final Property p) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				if (myProperty != null) {
					myProperty.removePropertyChangeListener(TipiProperty.this);
				}
				// TODO: Not sure about this one:
				if (myProperty != null) {
					myContext.unlink(myProperty.getRootDoc(), myProperty);
				}
				if (p != null) {
					p.addPropertyChangeListener(TipiProperty.this);
				}
				myProperty = p;
				if (p == null) {
					return;
				}
				((GenericPropertyComponent) getContainer()).setProperty(p);
			}
		});
	}

	@Override
	public void addTipiEventListener(TipiEventListener listener) {
		if (listener == null) {
		}
		myListeners.add(listener);
	}

	@Override
	public String getPropertyName() {
		TipiSwingPropertyComponent tpc = (TipiSwingPropertyComponent) getContainer();
		String name = tpc.getPropertyName();
		if (name != null) {
			return name;
		}
		return getId();
	}

	@Override
	public void propertyEventFired(Property p, String eventType,
			boolean internal) {
		propertyEventFired(p, eventType, null, internal);
	}

	public void propertyEventFired(Property p, String eventType,
			Object oldValue, boolean internal) {
		if ("onValueChanged".equals(eventType)) {
			Object typedValue = p.getTypedValue();
			if ((typedValue != null && typedValue.equals(oldValue)) || (typedValue == null && oldValue == null)) {
                logger.warn("No real change on onValueChanged event. Old value : {} - New value {}", oldValue, typedValue);
			}
			else
			{
				setDirty(Boolean.TRUE);
			}
			try {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("propertyName", p.getFullPropertyName());
				m.put("propertyValue", typedValue);
				m.put("propertyType", p.getType());
				m.put("internalChange", internal);
				m.put("old", oldValue);
				m.put("propertyLength", Integer.valueOf(p.getLength()));
				if (!internal){
					for (int i = 0; i < myListeners.size(); i++) {
						TipiEventListener current = myListeners.get(i);
						current.performTipiEvent(eventType, m, handleValueChangeSync);
					}
				}
			} catch (Exception ex) {
				logger.error("Error detected",ex);
			}
		} else {
			try {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("propertyName", p.getFullPropertyName());
				m.put("propertyValue", p.getTypedValue());
				m.put("propertyType", p.getType());
				m.put("propertyLength", Integer.valueOf(p.getLength()));
				performTipiEvent(eventType, m, false);
			} catch (Exception ex) {
				logger.error("Error detected",ex);
			}

		}
		if (p == null) {
			logger.debug("Trying to fire event from null property!");
			return;
		}
	}

	public void updateProperty() {
		((GenericPropertyComponent) getContainer()).updateProperty();
	}

	@Override
	protected void performComponentMethod(final String name,
			final TipiComponentMethod compMeth, TipiEvent event) {
		if ("updateProperty".equals(name)) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					updateProperty();
				}
			});
		} else if ("requestFocus".equals(name)) {
			requestPropertyFocus();
		}
		if ("selectByName".equals(name)) {
			String selectionName = (String) compMeth.getEvaluatedParameter(
					"value", event).value;
			if (myProperty != null) {
				try {
					Selection s = myProperty.getSelection(selectionName);
					myProperty.setSelected(s);
					setProperty(myProperty);
				} catch (NavajoException e) {
					logger.error("Error detected",e);
				}
			}
		}
		if ("selectByValue".equals(name)) {
			String selectionValue = (String) compMeth.getEvaluatedParameter(
					"value", event).value;
			if (myProperty != null) {
				try {
					myProperty.setSelected(selectionValue);
					setProperty(myProperty);
				} catch (NavajoException e) {
					logger.error("Error detected",e);
				}
			}
		}

	}

	private void requestPropertyFocus() {
		((GenericPropertyComponent) getContainer()).requestPropertyFocus();
	}

	@Override
	public void propertyChange(final PropertyChangeEvent e) {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				if (e.getPropertyName().equals("value")
						|| e.getPropertyName().equals("selection")) {
					// if(e.getPropertyName().equals("selection")) {
					// logger.debug("Change: "+e.getPropertyName());
					// }
					// logger.debug("OLD: "+e.getOldValue());
					// logger.debug("NEW: "+e.getNewValue());
					// Thread.dumpStack();
					((GenericPropertyComponent) getContainer())
							.updatePropertyValue(e);
					Boolean internal = Boolean.FALSE;
					if (e instanceof DocumentPropertyChangeEvent)
					{
						internal = ((DocumentPropertyChangeEvent) e).getInternal();
					}
					propertyEventFired((Property) e.getSource(),
							"onValueChanged", e.getOldValue(), internal);
					myPropertyValue = ((Property) e.getSource())
							.getTypedValue();
				} else {
					// direction, cardinality, length, or description change:
					((GenericPropertyComponent) getContainer())
							.setProperty(getProperty());
					if (e.getPropertyName().equals("cardinality")) {
						propertyEventFired((Property) e.getSource(),
								"onCardinalityChanged", e.getOldValue(), false);
					}
					if (e.getPropertyName().equals("length")) {
						propertyEventFired((Property) e.getSource(),
								"onLengthChanged", e.getOldValue(), false);
					}
					if (e.getPropertyName().equals("description")) {
						propertyEventFired((Property) e.getSource(),
								"onDescriptionChanged", e.getOldValue(), false);
					}
					if (e.getPropertyName().equals("direction")) {
						propertyEventFired((Property) e.getSource(),
								"onDirectionChanged", e.getOldValue(), false);
					}
					if (e.getPropertyName().equals("type")) {
						propertyEventFired((Property) e.getSource(),
								"onTypeChanged", e.getOldValue(), false);
					}
				}
			}

		});

	}

	@Override
	public Boolean isDirty() {
		return myPropertyIsDirty;
	}

	@Override
	public void setDirty(Boolean b) {
		this.myPropertyIsDirty = b;
		
	}

}
