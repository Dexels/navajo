package com.dexels.navajo.tipi.components.echoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.actions.PropertyEventListener;
import com.dexels.navajo.tipi.internal.PropertyComponent;

import echopointng.ContainerEx;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiProperty extends TipiEchoComponentImpl implements
		PropertyComponent, PropertyEventListener {

	private Property myProperty = null;

	private String myPropertyName = null;

	private EchoPropertyComponent myPropertyComponent;

//	private ContainerEx myContainer;

	public TipiProperty() {
	}

	public Property getProperty() {
		return myProperty;
	}

	public void setProperty(Property p) {
//		try {
//			System.err.println("Setting property: " + p.getFullPropertyName());
//		} catch (NavajoException ex1) {
//			ex1.printStackTrace();
//		}
		myProperty = p;
		try {
			((EchoPropertyComponent) getContainer()).setProperty(p);
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}
	}

	public void propertyEventFired(Property p, String eventType) {
	
		    if (p == null) {
		      System.err.println("Trying to fire event from null property!");
		      return;
		    }
		    if (p != myProperty) {
		      System.err.println("Mysterious anomaly: Property of event is not the loaded property");
		      return;
		    }
		    try {
		      Map m = new HashMap();
		      m.put("propertyName", myProperty.getFullPropertyName());
		      m.put("propertyValue", myProperty.getTypedValue());
		      m.put("propertyType", myProperty.getType());
		      m.put("propertyLength", new Integer(myProperty.getLength()));
//		      PropertyImpl p = (PropertyImpl)myProperty;
//		      for (int i = 0; i < myListeners.size(); i++) {
//		        TipiEventListener current = (TipiEventListener) myListeners.get(i);
		       performTipiEvent(eventType, m, false);
//		      }
		    }
		    catch (Exception ex) {
		      ex.printStackTrace();
		    }
		  }
	
	
	public Object createContainer() {
//		myContainer = new ContainerEx();
		myPropertyComponent = new EchoPropertyComponent();
		myPropertyComponent.setUseLabelForReadOnlyProperties(false);
		myPropertyComponent.addPropertyEventListener(this);
		//		myContainer.add(myPropertyComponent);
		return myPropertyComponent;
	}

	public Object getActualComponent() {
		return myPropertyComponent;
	}

	/**
	 * getPropertyName
	 * 
	 * @return String
	 * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
	 *       method
	 */
	public String getPropertyName() {
		return myPropertyName;
	}

	/**
	 * addTipiEventListener
	 * 
	 * @param listener
	 *            TipiEventListener
	 * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
	 *       method
	 */
	public void addTipiEventListener(TipiEventListener listener) {
	}

	/**
	 * addTipiEvent
	 * 
	 * @param te
	 *            TipiEvent
	 * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
	 *       method
	 */
	// public void addTipiEvent(TipiEvent te) {
	// }
	protected void setComponentValue(String name, Object object) {
		if ("propertyname".equals(name)) {
			System.err.println("Setting propname to: " + object.toString());
			myPropertyName = object.toString();
		}
		if ("label_indent".equals(name)) {
			EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
			Integer ii = (Integer)object;
			me.setLabelIndent(ii.intValue());
		}
		if ("width".equals(name)) {
			EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
			Integer ii = (Integer)object;
			me.setValueSize(ii.intValue());
		}
		super.setComponentValue(name, object);
	}

	public void checkForConditionErrors(Message m) {
		// err.. implement?
	}


}
