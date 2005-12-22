package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiEventListener;
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
		PropertyComponent {

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

	public Object createContainer() {
//		myContainer = new ContainerEx();
		myPropertyComponent = new EchoPropertyComponent();
		myPropertyComponent.setUseLabelForReadOnlyProperties(false);
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
		super.setComponentValue(name, object);
	}

	public void checkForConditionErrors(Message m) {
		// err.. implement?
	}

}
