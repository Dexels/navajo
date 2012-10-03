package com.dexels.navajo.plugin.workflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class TransitionElement extends WorkflowModelElement {
	/**
	 * Used for indicating that a Connection with solid line style should be
	 * created.
	 * 
	 * @see com.dexels.navajo.plugin.workflow.parts.StateEditPart#createEditPolicies()
	 */

	public static final Integer SOLID_CONNECTION = new Integer(
			Graphics.LINE_SOLID);
	/**
	 * Used for indicating that a Connection with dashed line style should be
	 * created.
	 * 
	 * @see com.dexels.navajo.plugin.workflow.parts.StateEditPart#createEditPolicies()
	 */
	public static final Integer DASHED_CONNECTION = new Integer(
			Graphics.LINE_DASH);

	/** Property ID to use when the line style of this connection is modified. */
	private static final IPropertyDescriptor[] descriptors = new IPropertyDescriptor[1];

	public static final String TRIGGER_PROPERTY = "Trigger";

	/** True, if the connection is attached to its endpoints. */
	private boolean isConnected;
	/** Line drawing style for this connection. */
	private int lineStyle = Graphics.LINE_SOLID;
	/** Connection's source endpoint. */
	private StateElement source;
	/** Connection's target endpoint. */
	private StateElement target;

	private String trigger = null;

	private List<ParamElement> params = new ArrayList<ParamElement>();

	public Image getIcon() {
		return ELLIPSE_ICON;
	}

	static {
		// descriptors[0] = new ComboBoxPropertyDescriptor(LINESTYLE_PROP,
		// LINESTYLE_PROP,
		// new String[] {SOLID_STR, DASHED_STR});
		descriptors[0] = new PropertyDescriptor(TRIGGER_PROPERTY,
				TRIGGER_PROPERTY);
	}

	public TransitionElement(StateElement source, StateElement target) {
		reconnect(source, target);
		trigger = "time:now";
	}

	/**
	 * Disconnect this connection from the shapes it is attached to.
	 */
	public void disconnect() {
		if (isConnected) {
			source.removeConnection(this);
			target.removeConnection(this);
			isConnected = false;
		}
	}

	/**
	 * Returns the line drawing style of this connection.
	 * 
	 * @return an int value (Graphics.LINE_DASH or Graphics.LINE_SOLID)
	 */
	public int getLineStyle() {
		return lineStyle;
	}

	/**
	 * Returns the descriptor for the lineStyle property
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	/**
	 * Returns the lineStyle as String for the Property Sheet
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(TRIGGER_PROPERTY)) {
			return trigger;
		}
		return super.getPropertyValue(id);
	}

	/**
	 * Returns the source endpoint of this connection.
	 * 
	 * @return a non-null Shape instance
	 */
	public StateElement getSource() {
		return source;
	}

	/**
	 * Returns the target endpoint of this connection.
	 * 
	 * @return a non-null Shape instance
	 */
	public StateElement getTarget() {
		return target;
	}

	/**
	 * Reconnect this connection. The connection will reconnect with the shapes
	 * it was previously attached to.
	 */
	public void reconnect() {
		if (!isConnected) {
			source.addConnection(this);
			target.addConnection(this);
			isConnected = true;
		}
	}

	/**
	 * Reconnect to a different source and/or target shape. The connection will
	 * disconnect from its current attachments and reconnect to the new source
	 * and target.
	 * 
	 * @param newSource
	 *            a new source endpoint for this connection (non null)
	 * @param newTarget
	 *            a new target endpoint for this connection (non null)
	 * @throws IllegalArgumentException
	 *             if any of the paramers are null or newSource == newTarget
	 */
	public void reconnect(StateElement newSource, StateElement newTarget) {
		if (newSource == null || newTarget == null || newSource == newTarget) {
			throw new IllegalArgumentException();
		}
		disconnect();
		this.source = newSource;
		this.target = newTarget;
		reconnect();
	}

	/**
	 * Sets the lineStyle based on the String provided by the PropertySheet
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id.equals(TRIGGER_PROPERTY)) {
			trigger = (String) value;
		} else {
			super.setPropertyValue(id, value);
		}
	}

	public void load(XMLElement transitionElement) {

		trigger = transitionElement.getStringAttribute("trigger");
		Vector<XMLElement> transitions = transitionElement.getChildren();
		for (int i = 0; i < transitions.size(); i++) {
			XMLElement currentChild = transitions.get(i);
			if (currentChild.getName().equals("param")) {
				ParamElement te = new ParamElement();
				te.load(currentChild);
//				te.setTransition(this);
				params.add(te);
			}

		}
	}

	public XMLElement toXml() {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("transition");
		xe.setAttribute("nextstate", target.getId());
		xe.setAttribute("trigger", trigger);
		for (int i = 0; i < params.size(); i++) {
			ParamElement st = params.get(i);
			xe.addChild(st.toXml());
		}

		return xe;
	}

}
