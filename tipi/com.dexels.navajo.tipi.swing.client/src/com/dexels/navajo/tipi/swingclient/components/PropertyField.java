package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.dexels.navajo.document.Property;

/**
 * <p>
 * Title: SportLink Client:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class PropertyField extends JTextField implements PropertyControlled,
		FocusListener {

	private static final long serialVersionUID = -3840882138547155549L;
	protected String textValue;
	protected Property initProperty = null;
	private String forcedAlignment = null;

	public PropertyField() {
		this.addFocusListener(this);
	}

	@Override
	public boolean isManagingFocus() {
		return false;
	}

	@Override
	public Property getProperty() {
		return initProperty;
	}

	public void resetProperty() {
		setProperty(getProperty());
	}

	public String getForcedAlignment() {
		return forcedAlignment;
	}

	public void setForcedAlignment(String align) {
		String old = forcedAlignment;
		forcedAlignment = align;
		if ("left".equals(align)) {
			setHorizontalAlignment(SwingConstants.LEFT);
		}
		if ("right".equals(align)) {
			setHorizontalAlignment(SwingConstants.RIGHT);
		}
		if ("center".equals(align)) {
			setHorizontalAlignment(SwingConstants.CENTER);
		}
		if (align != null) {
			if (!align.equals(old)) {
				firePropertyChange("forcedAlignment", old, align);
			}
		}
	}

	@Override
	public void setProperty(Property p) {
		if (p == null) {
			return;
		}
		initProperty = p;
		setEditable(p.isDirIn());
	}

	@Override
	public void focusLost(FocusEvent e) {
		textValue = getText();
		if (initProperty != null
				&& !initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
			initProperty.setValue(textValue);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		selectAll();
	}

	@Override
	public void update() {
		if (initProperty == null) {
			return;
		}
		textValue = getText();
		if (textValue != null
				&& !initProperty.getType().equals(Property.EXPRESSION_PROPERTY)) {
			initProperty.setValue(textValue);
		}
	}

}
